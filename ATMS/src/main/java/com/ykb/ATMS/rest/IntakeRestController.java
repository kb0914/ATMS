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

import com.ykb.ATMS.entity.Intake;
import com.ykb.ATMS.service.Interface.IIntakeService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class IntakeRestController {

	private IIntakeService intakeService;
	
	@Autowired
	public IntakeRestController(IIntakeService intakeService) {
		this.intakeService=intakeService;
	}
	
	@GetMapping("/intakes")
	public List<Intake>findAll(){
		return intakeService.findAll();
	}
	
	@GetMapping("/intakes/{id}")
	public Intake findById(@PathVariable long id){
		
		Intake intake = intakeService.findById(id);
		if(intake==null)
			throw new RuntimeException("lecturer id not found - " + id);
		
		return intake;
	}
	
	@GetMapping("/intakes/search/{keyword}")
	public List<Intake> findByCode(@PathVariable String keyword){
		
		return intakeService.findByCode(keyword);
	}
	
	@PostMapping("/intakes")
	public Intake addIntake(@RequestBody Intake intake){
		
		intake.setId(0);
		intakeService.save(intake);
		
		return intake;
	}
	
	
	@PutMapping("/intakes")
	public Intake updateIntake(@RequestBody Intake intake){
		
		intakeService.save(intake);
		
		return intake;
	}
	
	@DeleteMapping("/intakes/{id}")
	public Intake deleteById(@PathVariable int id){
		
		Intake intake = intakeService.findById(id);
		
		if(intake==null)
			throw new RuntimeException("Intake id not found - " + id);
		
		intakeService.deleteById(id);
		
		return intake;
	}
}
