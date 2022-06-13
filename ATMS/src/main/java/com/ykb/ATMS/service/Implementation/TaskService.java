package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.DTO.DistributeTasksDTO;
import com.ykb.ATMS.DTO.TaskUpdateDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.repository.StudentRepository;
import com.ykb.ATMS.repository.TaskRepository;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.service.Interface.IFileDBService;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITaskService;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TaskService implements ITaskService{

	private TaskRepository taskRepository;
	private TeamRepository teamRepository;
	private IStudentService studentService;
	private ITeamService teamService;
	
	@Autowired
	public TaskService(TaskRepository taskRepository, TeamRepository teamRepository, IStudentService studentService,
			ITeamService teamService) {
		this.taskRepository=taskRepository;
		this.teamRepository=teamRepository;
		this.studentService=studentService;
		this.teamService=teamService;
	}
	
	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(long id) {
		Optional<Task> result = taskRepository.findById(id);
		
		Task team=null;
		
		if(result!=null)
			team=result.get();
		else
			throw new RuntimeException("Team ID not found - "+id);
		
		return team;
	}
	
	@Override
	public List<Task> findByTeam(long id){
		return taskRepository.findByTeam(id);
	}

	@Override
	public void create(Task task, long tid) {
		Optional<Team> result=teamRepository.findById(tid);
		Team team=null;
		if(result!=null) {
			team=result.get();
			team.addTask(task);
			teamRepository.save(team);
		}else
			throw new RuntimeException("Team ID not found - "+tid);
		
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
	public void update(Task task) {
		taskRepository.save(task);
	}
	
	@Override
	public void updateAssignTo(List<Task> tasks) {
		tasks.forEach(i->{
			Task t=findById(i.getId());
			t.setStatus(AssignmentStatus.PENDING);
			Student s=studentService.findById(i.getStudent().getId());
			s.addTask(t);
			studentService.save(s);
		});
	}
	
	@Override
	public List<Task> getTasksByStudentAdnTeamID(long sid, long tid){
		return taskRepository.findByStudentAndTeam(sid, tid);
	}
	
	@Override
	public DistributeTasksDTO distributeTasks(long teamId, List<Task> tasks){
		
		List<Student> members=teamService.findById(teamId).getStudents();
		
		List<Integer> weightages = new ArrayList<Integer>();
		tasks.sort((o1, o2) -> o2.getWeightage()-o1.getWeightage());
		System.out.println(tasks);
		for(int i=0;i<tasks.size();i++) {
			members=members.stream()
					.sorted((s1, s2) -> {
	                    int list1Sum = s1.getTasks().stream()
	                    		.filter(team->team.getTeam().getId()==teamId)
	                    		.mapToInt(t->t.getWeightage())
	                    		.sum();
	                    int list2Sum = s2.getTasks().stream()
	                    		.filter(team->team.getTeam().getId()==teamId)
	                    		.mapToInt(t->t.getWeightage())
	                    		.sum();
	                    return list1Sum - list2Sum;
	                }).collect(Collectors.toList());
			members.get(0).addTask(tasks.get(i));
		}
		
		for(Student i:members) {
			System.out.println(i.getFirstName()+" :"+sumOfList(i.getTasks()));
			System.out.println(i.getFirstName()+" :");
			i.getTasks().forEach(a->System.out.println(a.getWeightage()));
			weightages.add(sumOfList(i.getTasks()));
		}
		
		
		return new DistributeTasksDTO(tasks, weightages);
	}
	
	private static int sumOfList(List<Task> list) {
	    return list.stream().mapToInt(i -> i.getWeightage()).sum();
	}
}
