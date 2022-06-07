package com.ykb.ATMS.service.Implementation;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ykb.ATMS.DTO.FileReceiveDTO;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
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
	public void linkTaskToFile(long id, List<Task> tasks) {
		FileDB file=getFile(id);
		tasks.forEach(i->file.addTask(taskService.findById(i.getId())));
		fileDBRepository.save(file);
	}
	
	@Override
	public void unlinkTaskToFile(long id, Task task) {
		FileDB file=getFile(id);
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
				throw new RuntimeException("Student ID not found - "+id);
			
	    return fileDb;
	  }
	  
	@Override
	  public List<FileDB> getAllFiles() {
	    return fileDBRepository.findAll();
	  }
}
