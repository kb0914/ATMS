package com.ykb.ATMS.service.Implementation;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ykb.ATMS.DTO.FileReceiveDTO;
import com.ykb.ATMS.DTO.FileRespondDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.repository.FileDBRepository;
import com.ykb.ATMS.service.Interface.IFileDBService;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITaskService;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class FileDBService implements IFileDBService{

	private FileDBRepository fileDBRepository;
	private IStudentService studentService;
	private ITeamService teamService;
	private ITaskService taskService;
	
	@Autowired
	public FileDBService(FileDBRepository fileDBRepository, IStudentService studentService, ITeamService teamService, ITaskService taskService) {
		this.fileDBRepository=fileDBRepository;
		this.studentService=studentService;
		this.teamService=teamService;
		this.taskService=taskService;
	}
	
	@Override
	public FileDB store(MultipartFile file, long tid, long sid) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    Student student =studentService.findById(sid);
	    Team team =teamService.findById(tid);
	    FileDB fileDB = new FileDB(fileName, file.getContentType(),file.getBytes(), new Date(System.currentTimeMillis()), student, team);

	    return fileDBRepository.save(fileDB);
	  }
	
	@Override
	public void storeMainFile(MultipartFile file, long tid, long sid) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    Team team =teamService.findById(tid);
	    if(team.getMainFile()!=null) {
		    long fid=team.getMainFile().getId();
		    team.setMainFile(null);
		    fileDBRepository.deleteById(fid);
	    }
	    FileDB fileDB = new FileDB(fileName, file.getContentType(),file.getBytes(), new Date(System.currentTimeMillis()), team.getTeamLead(), team);
	    team.setMainFile(fileDB);
	    teamService.save(team);
	  }
	
	@Override
	public void linkTaskToFile(long id, List<Task> tasks) {
		FileDB file=getFile(id);
		tasks.forEach(i->{
			Task task=taskService.findById(i.getId());
			task.setStatus(AssignmentStatus.COMPLETED);
			file.addTask(task);
		});
		
		fileDBRepository.save(file);
	}
	
	@Override
	public void unlinkTaskToFile(long id, Task task) {
		FileDB file=getFile(id);
		Task temp=taskService.findById(task.getId());
		temp.setStatus(AssignmentStatus.STARTED);
		file.removeTask(taskService.findById(task.getId()));
		fileDBRepository.save(file);
	}
	
	@Override
	  public FileDB getFile(long id) {
		  Optional<FileDB> result = fileDBRepository.findById(id);
			
		  FileDB fileDb=null;
			
			if(result!=null)
				fileDb=result.get();
			else
				throw new RuntimeException("File ID not found - "+id);
			
	    return fileDb;
	  }
	  
	@Override
	  public List<FileDB> getAllFiles() {
	    return fileDBRepository.findAll();
	  }
	
	@Override
	public List<FileRespondDTO> getFileList() {
		return getAllFiles().stream()
	    		.map(dbFile -> convertFileToFileRespond(dbFile)).collect(Collectors.toList());
	}
	
	@Override
	public TeamDTO getFileListByTeamId(long tid) {
		Team team=teamService.findById(tid);
		if(team.getMainFile()!=null) {
			TeamDTO dto= new TeamDTO(team.getId(), team.getAssignment(), teamService.findAllTeamMemberByTeamID(tid),
					FileDBService.convertFileToFileRespond(team.getMainFile()), studentService.convertToDto(team.getTeamLead()));
			dto.setFiles(team.getFiles().stream()
					.map(i->convertFileToFileRespond(i))
					.filter(i->i.getId()!=team.getMainFile().getId())
					.toList());
			return dto;
		}else {
			TeamDTO dto= new TeamDTO(team.getId(), team.getAssignment(), teamService.findAllTeamMemberByTeamID(tid),
					null, studentService.convertToDto(team.getTeamLead()));
			dto.setFiles(team.getFiles().stream()
					.map(i->convertFileToFileRespond(i))
					.toList());
			return dto;
		}
	}
	
	@Override
	public FileRespondDTO getTaskProveFile(long id) {
		
		return convertFileToFileRespond(getFile(id));
	}
	
	@Override
	public void deleteFile(long id) {
		getFile(id).getTasks().forEach(i->{
			i.setFile(null);
			i.setStatus(AssignmentStatus.STARTED);
			});
	    fileDBRepository.deleteById(id);
	}
	
	public static FileRespondDTO convertFileToFileRespond(FileDB file) {
		String fileDownloadUri = ServletUriComponentsBuilder
	    		 .fromCurrentContextPath()
	    		 .path("/api/files/")
	    		 .path(Long.toString(file.getId()))
	    		 .toUriString();
		
		return new FileRespondDTO(
				file.getId(),
				file.getName(),
	            fileDownloadUri,
	            file.getType(),
	            file.getData().length,
	            file.getDate());
	}
	
}
