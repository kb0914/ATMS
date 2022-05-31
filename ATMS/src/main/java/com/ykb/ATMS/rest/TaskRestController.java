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

import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.enums.Priority;
import com.ykb.ATMS.service.Interface.ITaskService;


@RestController
@RequestMapping("api/")
@CrossOrigin
public class TaskRestController {
	
	private ITaskService taskService;
	
	@Autowired
	public TaskRestController(ITaskService taskService) {
		this.taskService=taskService;
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
