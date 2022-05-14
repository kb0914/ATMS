package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.service.Interface.ITeamService;

@RestController
@RequestMapping("api/")
public class GroupRestController {

	private ITeamService teamService;
	
	@Autowired
	public GroupRestController(ITeamService teamService) {
		this.teamService = teamService;
	}
	
	@GetMapping("/group")
	public List<Team>findAll(){
		return teamService.findAll();
	}
	
	@GetMapping("/group/{id}")
	public Team findById(@PathVariable long id){
		
		Team team = teamService.findById(id);
		if(team==null)
			throw new RuntimeException("Team id not found - " + id);
		
		return team;
	}
	
	@PostMapping("/group/{lid}")
	public Team addAssignment(@RequestBody Team team, @PathVariable long lid){
		
		teamService.save(team, lid);
		
		return team;
	}
	
	
	@PutMapping("/group")
	public Team updateAssignment(@RequestBody Team team){
		
		teamService.save(team, team.getId());
		
		return team;
	}
	
	@DeleteMapping("/group/{id}")
	public Team deleteById(@PathVariable int id){
		
		Team team = teamService.findById(id);
		
		if(team==null)
			throw new RuntimeException("Lecturer id not found - " + id);
		
		teamService.deleteById(id);
		
		return team;
	}
}
