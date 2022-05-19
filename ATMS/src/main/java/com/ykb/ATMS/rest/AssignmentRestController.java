package com.ykb.ATMS.rest;

import java.util.Date;
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

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.ILecturerService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class AssignmentRestController {

	private IAssignmentService assignmentService;
	
	@Autowired
	public AssignmentRestController(IAssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}
	
	@GetMapping("/assignments")
	public List<Assignment>findAll(){
		return assignmentService.findAll();
	}
	
	@GetMapping("/assignments/{id}")
	public Assignment findById(@PathVariable long id){
		
		Assignment assignment = assignmentService.findById(id);
		if(assignment==null)
			throw new RuntimeException("students id not found - " + id);
		
		return assignment;
	}
	
	@PostMapping("/assignments/{lid}")
	public Assignment addAssignment(@RequestBody Assignment assignment, @PathVariable long lid){
		//assignment.setAssignDate(new Date());
		assignmentService.create(assignment, lid);
		
		return assignment;
	}
	
	@PutMapping("/assignments")
	public Assignment updateAssignment(@RequestBody Assignment assignment){
		
		assignmentService.update(assignment);
		System.out.println(assignment.getTeam());
		
		return assignment;
	}
	
	@DeleteMapping("/assignments/{id}")
	public Assignment deleteById(@PathVariable int id){
		
		Assignment assignemnt = assignmentService.findById(id);
		
		if(assignemnt==null)
			throw new RuntimeException("Lecturer id not found - " + id);
		
		assignmentService.deleteById(id);
		
		return assignemnt;
	}
}
