package com.ykb.ATMS.service.Implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ykb.ATMS.DTO.DistributeTasksDTO;
import com.ykb.ATMS.DTO.LinkTaskDTO;
import com.ykb.ATMS.DTO.MarkAssignmentInfoDTO;
import com.ykb.ATMS.DTO.StudentTasksDTO;
import com.ykb.ATMS.DTO.TeamStudentDTO;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.repository.TaskRepository;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITaskService;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TaskService implements ITaskService {

	private TaskRepository taskRepository;
	private IStudentService studentService;
	private ITeamService teamService;

	@Autowired
	public TaskService(TaskRepository taskRepository, IStudentService studentService, ITeamService teamService) {
		this.taskRepository = taskRepository;
		this.studentService = studentService;
		this.teamService = teamService;
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(long id) {
		Optional<Task> result = taskRepository.findById(id);

		Task team = null;

		if (result != null)
			team = result.get();
		else
			throw new RuntimeException("Team ID not found - " + id);

		return team;
	}

	@Override
	public List<Task> findByTeam(long id) {
		return taskRepository.findByTeam(id);
	}

	@Override
	public void create(Task task, long tid) {
		Team team = teamService.findById(tid);

		if (team != null) {
			if (task.getStudent() != null) {
				System.out.println(task.getStudent().toString());
				task.setStudent(studentService.findById(task.getStudent().getId()));
			}
			team.addTask(task);
			teamService.save(team);
		}

//		Optional<Student> result1=studentRepository.findById(sid);
//		Student student=null;
//		if(result1!=null) {
//			student=result1.get();
//			student.addTask(task);
//			studentRepository.save(student);
//		}else
//			throw new RuntimeException("Student ID not found - "+tid);
	}

	@Override
	public void deleteById(long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public void update(Task dto) {
		Task task = findById(dto.getId());
		task.setTittle(dto.getTittle());
		task.setAssignDate(dto.getAssignDate());
		task.setEstimatedDueDate(dto.getEstimatedDueDate());
		task.setDescription(dto.getDescription());
		task.setStatus(dto.getStatus());
		task.setWeightage(dto.getWeightage());
		if (dto.getStudent() == null || dto.getStudent().getId() == 0) {
			task.setStudent(null);
			task.setStatus(AssignmentStatus.UNASSIGNED);
		} else
			task.setStudent(studentService.findById(dto.getStudent().getId()));
		taskRepository.save(task);
	}

	@Override
	public void updateAssignTo(List<Task> tasks) {
		tasks.forEach(i -> {
			Task t = findById(i.getId());
			t.setStatus(AssignmentStatus.PENDING);
			Student s = studentService.findById(i.getStudent().getId());
			s.addTask(t);
			studentService.save(s);
		});
	}

	@Override
	public void updateAssignTo(List<Task> tasks, long sid) {
		Student s = studentService.findById(sid);
		tasks.forEach(i -> {
			Task t = findById(i.getId());
			t.setStatus(AssignmentStatus.PENDING);
			s.addTask(t);
			studentService.save(s);
		});
	}

	@Override
	public List<Task> getTasksByStudentAdnTeamID(long sid, long tid) {
		return taskRepository.findByStudentAndTeam(sid, tid);
	}
	
	@Override
	public DistributeTasksDTO distributeTasks(long teamId, List<Task> tasks) {

		List<Student> members = teamService.findById(teamId).getStudents().stream()
				.map(i -> i.getStudent())
				.toList();

		List<Integer> weightages = new ArrayList<Integer>();
		tasks.sort((o1, o2) -> o2.getWeightage() - o1.getWeightage());
		
		for (int i = 0; i < tasks.size(); i++) {
			
			members = members.stream().sorted((s1, s2) -> {
				
				int list1Sum = s1.getTasks().stream()
						.filter(team -> team.getTeam().getId() == teamId)
						.mapToInt(t -> t.getWeightage())
						.sum();
				
				int list2Sum = s2.getTasks().stream()
						.filter(team -> team.getTeam().getId() == teamId)
						.mapToInt(t -> t.getWeightage())
						.sum();
				
				return list1Sum - list2Sum;
			}).collect(Collectors.toList());
			
			members.get(0).addTask(tasks.get(i));
		}

		for (Student i : members) {
			weightages.add(sumOfList(i.getTasks().stream()
					.filter(task->task.getTeam().getId()==teamId)
					.toList()));
		}

		return new DistributeTasksDTO(tasks, weightages);
	}

	@Override
	public MarkAssignmentInfoDTO getStudentTaskStatusNumber(long id) {
		List<TeamStudentDTO> allStudent = teamService.findTeamStudentByTeamID(id);
		List<StudentTasksDTO> dto = new ArrayList<StudentTasksDTO>();
		int totalWeigtage = 0;
		Team team = teamService.findById(id);
		for (TeamStudentDTO i : allStudent) {
			for (Task t : getTasksByStudentAdnTeamID(i.getId(), id)) {
				if (t.getStatus() == AssignmentStatus.COMPLETED) {
					totalWeigtage = totalWeigtage + t.getWeightage();
				}
			}
		}
		for (TeamStudentDTO i : allStudent) {
			StudentTasksDTO temp = new StudentTasksDTO();
			temp.setStudent(i);
			for (Task t : getTasksByStudentAdnTeamID(i.getId(), id)) {
				if (t.getStatus() == AssignmentStatus.COMPLETED) {
					temp.setCompletedTaskNum(temp.getCompletedTaskNum() + t.getWeightage());
				}
				if (t.getStatus() == AssignmentStatus.PENDING)
					temp.setPendingTaskNum(temp.getPendingTaskNum() + t.getWeightage());
				if (t.getStatus() == AssignmentStatus.STARTED)
					temp.setStartedTaskNum(temp.getStartedTaskNum() + t.getWeightage());
			}
			if(temp.getCompletedTaskNum()!=0) {
			BigDecimal percentage = new BigDecimal(((double) temp.getCompletedTaskNum() / totalWeigtage) * 100)
					.setScale(2, RoundingMode.HALF_UP);
			temp.setCompletedTaskPercentage(percentage.doubleValue());
			}
			dto.add(temp);
		}
		MarkAssignmentInfoDTO maiDto=new MarkAssignmentInfoDTO();
		FileDB file=team.getMainFile();
		if(file!=null) {
			maiDto.setMainFile(FileDBService.convertFileToFileRespond(team.getMainFile()));
		}
		maiDto.setStudentTasks(dto);
		maiDto.setTotalWeigthage(totalWeigtage);
		if(team.getMark()!=null) {
			maiDto.setTeamMark(team.getMark());
		}
		return maiDto;
	}
	
	@Override
	public LinkTaskDTO getTasksByStudentAndTeamID(long sid, long tid, long fid){
		LinkTaskDTO dto = new LinkTaskDTO();
		List<Task> tasks=getTasksByStudentAdnTeamID(sid, tid);
		//List<Task> tasks=taskService.findByTeam(tid);
		dto.setAllTask(tasks.stream().filter(i->i.getFile()==null).toList());
		dto.setLinkedTask(tasks.stream()
				.filter(i->i.getFile()!=null)
				.filter(i->i.getFile().getId()==fid)
				.toList());
		return dto;
	}
	
	@Override
	public LinkTaskDTO getTasksByTeamID(long tid, long fid){
		LinkTaskDTO dto = new LinkTaskDTO();
		//List<Task> tasks=getTasksByStudentAdnTeamID(sid, tid);
		List<Task> tasks=findByTeam(tid);
		dto.setAllTask(tasks.stream().filter(i->i.getFile()==null).toList());
		dto.setLinkedTask(tasks.stream()
				.filter(i->i.getFile()!=null)
				.filter(i->i.getFile().getId()==fid)
				.toList());
		return dto;
	}

	private static int sumOfList(List<Task> list) {
		return list.stream().mapToInt(i -> i.getWeightage()).sum();
	}
}
