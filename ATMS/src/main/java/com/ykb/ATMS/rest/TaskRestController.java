package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.DistributeTasksDTO;
import com.ykb.ATMS.DTO.LinkTaskDTO;
import com.ykb.ATMS.DTO.MarkAssignmentInfoDTO;
import com.ykb.ATMS.DTO.TaskUpdateDTO;
import com.ykb.ATMS.entity.Task;
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
		
		return taskService.findById(id);
	}
	
	@GetMapping("/tasks/team/{id}")
	public List<Task> findByTeamId(@PathVariable long id){
		return taskService.findByTeam(id);
	}
	
	@GetMapping("/tasks/{sid}/{tid}/{fid}")
	public LinkTaskDTO getTasksByStudentAdnTeamID(@PathVariable long sid, @PathVariable long tid, @PathVariable long fid){
		
		return taskService.getTasksByStudentAndTeamID(sid, tid, fid);
	}
	
	@GetMapping("/tasks/{tid}/{fid}")
	public LinkTaskDTO getTasksByTeamID(@PathVariable long tid, @PathVariable long fid){
		
		return taskService.getTasksByTeamID(tid, fid);
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
			if(t.getStudent()!=null)
				return new TaskUpdateDTO(t.getId(), t.getTittle(), t.getDescription(), t.getAssignDate(),
						t.getEstimatedDueDate(), t.getPriority(), t.getStatus(), 
						t.getWeightage(), studentService.getSearchStudentByI(t.getStudent().getId()), 
						teamService.findAllTeamMemberByTeamID(t.getTeam().getId()));
			else
				return new TaskUpdateDTO(t.getId(), t.getTittle(), t.getDescription(), t.getAssignDate(),
						t.getEstimatedDueDate(), t.getPriority(), t.getStatus(), 
						t.getWeightage(), null, 
						teamService.findAllTeamMemberByTeamID(t.getTeam().getId()));
	}
	
	@GetMapping("/tasks/getStudentTaskStatusNumber/{tid}")
	public MarkAssignmentInfoDTO getStudentTaskStatusNumber(@PathVariable long tid){
		
		return taskService.getStudentTaskStatusNumber(tid);
	}
	
	@PostMapping("/tasks")
	public Task addTask(@RequestBody Task task){
		taskService.create(task, task.getTeam().getId());
		
		return task;
	}
	
	@PostMapping("/tasks/getdistributedtasks/{tid}")
	public DistributeTasksDTO distributeTask(@PathVariable long tid, @RequestBody List<Task> task){
		
		return taskService.distributeTasks(tid, task);
	}
	
	@PutMapping("/tasks")
	public Task updateTask(@RequestBody Task task){
		taskService.update(task);
		
		return task;
	}
	
	@PutMapping("/tasks/updateAssignTo")
	public ResponseEntity<String> updateAssignTo(@RequestBody List<Task> task){
		taskService.updateAssignTo(task);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/tasks/updateAssignToWithID/{sid}")
	public ResponseEntity<String> updateAssignToWithStudentId( @PathVariable long sid, @RequestBody List<Task> task){
		taskService.updateAssignTo(task, sid);
		
		return new ResponseEntity<>(HttpStatus.OK);
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
