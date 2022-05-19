package com.ykb.ATMS.rest;

import java.util.ArrayList;
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
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.ITeamService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class TeamRestController {

	private ITeamService teamService;
	private IAssignmentService assignmentService;
	
	@Autowired
	public TeamRestController(ITeamService teamService, IAssignmentService assignmentService) {
		this.teamService = teamService;
		this.assignmentService = assignmentService;
	}
	
	@GetMapping("/teams")
	public List<Team>findAll(){
		return teamService.findAll();
	}
	
	@GetMapping("/teams/{id}")
	public Team findById(@PathVariable long id){
		
		Team team = teamService.findById(id);
		if(team==null)
			throw new RuntimeException("Team id not found - " + id);
		
		return team;
	}
	
	@GetMapping("/teams/assignment/{id}")
	public List<Team> findTeamsByAssignemntId(@PathVariable long id){
		
		Assignment assignment = assignmentService.findById(id);
		if(assignment==null)
			throw new RuntimeException("Team id not found - " + id);
		
		return assignment.getTeam();
	}
	
	@GetMapping("/teams/teamedstudent/{id}")
	public List<Student> findTeamedStudentByAssignemntId(@PathVariable long id){
		List<Student> students =new ArrayList<>();
		Assignment assignment = assignmentService.findById(id);
		if(assignment==null)
			throw new RuntimeException("Team id not found - " + id);
		
		assignment.getTeam().stream()
				.map(team->team.getStudents())
				.forEach(s->s.forEach(i->students.add(i)));
		
		return students;
	}
	
	@PostMapping("/teams/{aid}")
	public Team addAssignment(@RequestBody Team team, @PathVariable("aid") long aid){
		
		teamService.save(team, aid);
		
		return team;
	}
	
	
	@PutMapping("/teams")
	public Team updateAssignment(@RequestBody Team team){
		
		teamService.save(team, team.getAssingment().getId());
		
		return team;
	}
	
	@DeleteMapping("/teams/{id}")
	public Team deleteById(@PathVariable int id){
		
		Team team = teamService.findById(id);
		
		if(team==null)
			throw new RuntimeException("Lecturer id not found - " + id);
		
		teamService.deleteById(id);
		
		return team;
	}
}
