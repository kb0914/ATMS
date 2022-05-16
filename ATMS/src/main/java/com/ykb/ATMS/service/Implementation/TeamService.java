package com.ykb.ATMS.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.repository.AssignmentRepository;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TeamService implements ITeamService{

	private TeamRepository teamRepository;
	private AssignmentService assignmentService;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, AssignmentService assignmentService) {
		this.teamRepository=teamRepository;
		this.assignmentService=assignmentService;
	}
	
	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	public Team findById(long id) {
		Optional<Team> result = teamRepository.findById(id);
		
		Team team=null;
		
		if(result!=null)
			team=result.get();
		else
			throw new RuntimeException("Assignment ID not found - "+id);
		
		return team;
	}

	@Override
	public void save(Team team, long aid) {
		
		Assignment assignment=assignmentService.findById(aid);
		assignment.addTeam(team);
		assignmentService.update(assignment);
	}

	@Override
	public void deleteById(long id) {
		teamRepository.deleteById(id);
	}

	@Override
	public void addTeamMember(Team team, Student student) {
		team.addStudent(student);
		teamRepository.save(team);
	}

}
