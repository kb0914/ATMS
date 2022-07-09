package com.ykb.ATMS.DTO;

import java.util.List;

public class MarkAssignmentInfoDTO {
	private FileRespondDTO mainFile;
	private List<StudentTasksDTO> studentTasks;
	private int totalWeigthage;
	private double teamMark;
	
	public MarkAssignmentInfoDTO(FileRespondDTO mainFile, List<StudentTasksDTO> studentTasks, int totalWeigthage) {
		super();
		this.mainFile = mainFile;
		this.studentTasks = studentTasks;
		this.totalWeigthage = totalWeigthage;
	}
	
	public MarkAssignmentInfoDTO(FileRespondDTO mainFile, List<StudentTasksDTO> studentTasks, int totalWeigthage, double teamMark) {
		super();
		this.mainFile = mainFile;
		this.studentTasks = studentTasks;
		this.totalWeigthage = totalWeigthage;
		this.teamMark=teamMark;
	}
	
	public FileRespondDTO getMainFile() {
		return mainFile;
	}
	public void setMainFile(FileRespondDTO mainFile) {
		this.mainFile = mainFile;
	}
	public List<StudentTasksDTO> getStudentTasks() {
		return studentTasks;
	}
	public void setStudentTasks(List<StudentTasksDTO> studentTasks) {
		this.studentTasks = studentTasks;
	}
	public int getTotalWeigthage() {
		return totalWeigthage;
	}
	public void setTotalWeigthage(int totalWeigthage) {
		this.totalWeigthage = totalWeigthage;
	}
	public double getTeamMark() {
		return teamMark;
	}
	public void setTeamMark(double teamMark) {
		this.teamMark = teamMark;
	}
	
	
}
