package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentTasksDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.DTO.TeamListDTO;
import com.ykb.ATMS.DTO.TeamStudentDTO;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;

public interface ITeamService {

	public List<Team> findAll();
	
	public Team findById(long id);
	
	public void createTeam(Team team, long aid );
	
	public void deleteById(long id);
	
	public void addTeamMember(long team, long student);

	void deleteTeamMember(long team, long student);

	List<SearchStudentDTO> findAllTeamMemberByTeamID(long id);

	TeamListDTO getTeamListItem(long id);

	void updateTeam(TeamDTO team, long aid);

	TeamDTO getTeamDTOById(long id);

	void save(Team team);

	void updateMemberMarks(long tid, TeamStudentDTO dto);

	List<TeamStudentDTO> findTeamStudentByTeamID(long id);

	List<SearchStudentDTO> getUnTeanedStudentByAssignemntId(long aid);

	void assignTeamLead(TeamDTO dto);
}
