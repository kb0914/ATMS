package com.ykb.ATMS.DTO;

public class StudentTasksDTO {
	
	private TeamStudentDTO student;
	private int completedTaskNum;
	private int pendingTaskNum;
	private int startedTaskNum;
	private double completedTaskPercentage;
	
	
	public StudentTasksDTO() {
		this.completedTaskNum=0;
		this.pendingTaskNum=0;
		this.startedTaskNum=0;
	}
	public StudentTasksDTO(TeamStudentDTO student, int completedTaskNum, int pendingTaskNum, int startedTaskNum) {
		this.student = student;
		this.completedTaskNum = completedTaskNum;
		this.pendingTaskNum = pendingTaskNum;
		this.startedTaskNum = startedTaskNum;
	}
	
	public TeamStudentDTO getStudent() {
		return student;
	}
	public void setStudent(TeamStudentDTO student) {
		this.student = student;
	}
	public int getCompletedTaskNum() {
		return completedTaskNum;
	}
	public void setCompletedTaskNum(int completedTaskNum) {
		this.completedTaskNum = completedTaskNum;
	}
	public int getPendingTaskNum() {
		return pendingTaskNum;
	}
	public void setPendingTaskNum(int pendingTaskNum) {
		this.pendingTaskNum = pendingTaskNum;
	}
	public int getStartedTaskNum() {
		return startedTaskNum;
	}
	public void setStartedTaskNum(int startedTaskNum) {
		this.startedTaskNum = startedTaskNum;
	}
	public double getCompletedTaskPercentage() {
		return completedTaskPercentage;
	}
	public void setCompletedTaskPercentage(double completedTaskPercentage) {
		this.completedTaskPercentage = completedTaskPercentage;
	}
	
	
}
