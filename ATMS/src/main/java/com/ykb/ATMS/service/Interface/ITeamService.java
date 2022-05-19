package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;

public interface ITeamService {

	public List<Team> findAll();
	
	public Team findById(long id);
	
	public void save(Team team, long aid );
	
	public void deleteById(long id);
	
	public void addTeamMember(Team team, Student student);

	void deleteTeamMember(Team team, Student student);
}
