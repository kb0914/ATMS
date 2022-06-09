package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.LinkTaskDTO;
import com.ykb.ATMS.DTO.TaskUpdateDTO;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.enums.Priority;
import com.ykb.ATMS.service.Interface.IFileDBService;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITaskService;
import com.ykb.ATMS.service.Interface.ITeamService;


@RestController
@RequestMapping("api/")
@CrossOrigin
public class TaskRestController {
	
	private ITaskService taskService;
	private IFileDBService fileDBService;
	private IStudentService studentService;
	private ITeamService teamService;
	
	@Autowired
	public TaskRestController(ITaskService taskService, IFileDBService fileDBService, IStudentService studentService,
			ITeamService teamService) {
		this.taskService=taskService;
		this.fileDBService=fileDBService;
		this.studentService=studentService;
		this.teamService=teamService;
	}

	@GetMapping("/tasks")
	public List<Task>findAll(){
		return taskService.findAll();
	}
	
	@GetMapping("/tasks/{id}")
	public Task findById(@PathVariable long id){
		
		Task task = taskService.findById(id);
		if(task==null)
			throw new RuntimeException("Task id not found - " + id);
		
		return task;
	}
	
	@GetMapping("/tasks/team/{id}")
	public List<Task> findByTeamId(@PathVariable long id){
		return taskService.findByTeam(id);
	}
	
	@GetMapping("/tasks/{sid}/{tid}/{fid}")
	public LinkTaskDTO getTasksByStudentAdnTeamID(@PathVariable long sid, @PathVariable long tid, @PathVariable long fid){
		LinkTaskDTO dto = new LinkTaskDTO();
		List<Task> tasks=taskService.getTasksByStudentAdnTeamID(sid, tid);
		dto.setAllTask(tasks.stream().filter(i->i.getFile()==null).toList());
		dto.setLinkedTask(tasks.stream()
				.filter(i->i.getFile()!=null)
				.filter(i->i.getFile().getId()==fid)
				.toList());
		return dto;
	}
	
	@GetMapping("/tasks/TaskUpdateDTO/{id}")
	public TaskUpdateDTO getTaskUpdateElement(@PathVariable long id) {
		Task t=findById(id);
		if(t.getFile()!=null)
			return new TaskUpdateDTO(t.getId(), t.getTittle(), t.getDescription(), t.getAssignDate(),
					t.getEstimatedDueDate(), t.getPriority(), t.getStatus(), 
					t.getWeightage(),studentService.getSearchStudentByI(t.getStudent().getId()), 
					fileDBService.getTaskProveFile(t.getFile().getId()), teamService.findAllTeamMemberByTeamID(t.getTeam().getId()));
		else
			return new TaskUpdateDTO(t.getId(), t.getTittle(), t.getDescription(), t.getAssignDate(),
					t.getEstimatedDueDate(), t.getPriority(), t.getStatus(), 
					t.getWeightage(), studentService.getSearchStudentByI(t.getStudent().getId()), 
					teamService.findAllTeamMemberByTeamID(t.getTeam().getId()));
	}
	
	@PostMapping("/tasks")
	public Task addTask(@RequestBody Task task){
		taskService.create(task, task.getTeam().getId());
		
		return task;
	}
	
	@PutMapping("/tasks")
	public Task updateTask(@RequestBody Task task){
		taskService.update(task);
		
		return task;
	}
	
	@DeleteMapping("/tasks/{id}")
	public Task deleteById(@PathVariable int id){
		
		Task task = taskService.findById(id);
		
		if(task==null)
			throw new RuntimeException("Task id not found - " + id);
		
		taskService.deleteById(id);
		
		return task;
	}
}
