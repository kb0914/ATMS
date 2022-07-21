package com.ykb.ATMS.DTO;

import java.util.List;

import com.ykb.ATMS.entity.Assignment;

public class TeamListDTO {
	private Assignment assignment;
	private List<TeamDTO> assignmentTeam;
	private List<SearchStudentDTO> teamedStudent;
	private List<SearchStudentDTO> options;
	
	public TeamListDTO() {
	}
	
	public TeamListDTO(Assignment assignment, List<TeamDTO> assignmentTeam, List<SearchStudentDTO> teamedStudent,
			List<SearchStudentDTO> options) {
		this.assignment = assignment;
		this.assignmentTeam = assignmentTeam;
		this.teamedStudent = teamedStudent;
		this.options = options;
	}
	
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public List<TeamDTO> getAssignmentTeam() {
		return assignmentTeam;
	}
	public void setAssignmentTeam(List<TeamDTO> assignmentTeam) {
		this.assignmentTeam = assignmentTeam;
	}
	public List<SearchStudentDTO> getTeamedStudent() {
		return teamedStudent;
	}
	public void setTeamedStudent(List<SearchStudentDTO> teamedStudent) {
		this.teamedStudent = teamedStudent;
	}
	public List<SearchStudentDTO> getOptions() {
		return options;
	}
	public void setOptions(List<SearchStudentDTO> options) {
		this.options = options;
	}
	
	
}
