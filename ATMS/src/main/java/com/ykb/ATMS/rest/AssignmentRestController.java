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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.IIntakeService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class AssignmentRestController {

	private IAssignmentService assignmentService;
	private IIntakeService intakeService;
	
	@Autowired
	public AssignmentRestController(IAssignmentService assignmentService, IIntakeService intakeService) {
		this.assignmentService = assignmentService;
		this.intakeService=intakeService;
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
	
	@GetMapping("/assignments/getassignmentsbylecturerid/{lid}")
	public List<Assignment> getAssignmentsByLEcturerId(@PathVariable long lid){
		
		return assignmentService.getAssignmentsByLEcturerId(lid);
	}
	
	@GetMapping("/assignments/downloadmarks/{aid}")
	  public ResponseEntity<Resource> getFile(@PathVariable long aid) {
	    String filename = "Assignment_"+aid+".xlsx";
	    InputStreamResource file = new InputStreamResource(assignmentService.load(aid));
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	
	@PostMapping("/assignments/{lid}")
	public Assignment addAssignment(@RequestBody Assignment assignment, @PathVariable long lid){

		intakeService.findById(assignment.getIntake().getId()).addAssignment(assignment);
		assignmentService.create(assignment, lid);
		
		return assignment;
	}
	
	@PutMapping("/assignments")
	public Assignment updateAssignment(@RequestBody Assignment assignment){
		intakeService.findById(assignment.getIntake().getId()).addAssignment(assignment);
		assignmentService.update(assignment);
		
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
