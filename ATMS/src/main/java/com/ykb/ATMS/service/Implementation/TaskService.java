package com.ykb.ATMS.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.repository.StudentRepository;
import com.ykb.ATMS.repository.TaskRepository;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.service.Interface.ITaskService;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TaskService implements ITaskService{

	private TaskRepository taskRepository;
	private TeamRepository teamRepository;
	private StudentRepository studentRepository;
	private ITeamService teamService;
	
	@Autowired
	public TaskService(TaskRepository taskRepository, TeamRepository teamRepository, StudentRepository studentRepository, ITeamService teamService) {
		this.taskRepository=taskRepository;
		this.teamRepository=teamRepository;
		this.studentRepository=studentRepository;
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
	public void update(Task assignment) {
		taskRepository.save(assignment);
	}
	
	@Override
	public List<Task> getTasksByStudentAdnTeamID(long sid, long tid){
		return taskRepository.findByStudentAndTeam(sid, tid);
	}
}
