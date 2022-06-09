package com.ykb.ATMS.DTO;

import java.sql.Date;
import java.util.List;

import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.enums.Priority;

public class TaskUpdateDTO {

	private long id;
	
	private String tittle;
	
	private String description;
	
	private Date assignDate;
	
	private Date estimatedDueDate;
	
	private Priority priority;
	
	private AssignmentStatus status;
	
	private float weightage;
	
	private FileRespondDTO fileRespondDTO;
	
	private	SearchStudentDTO student;
	
	private List<SearchStudentDTO> members;
	
	public TaskUpdateDTO() {
	}
	
	public TaskUpdateDTO(long id, String tittle, String description, Date assignDate, Date estimatedDueDate,
			Priority priority, AssignmentStatus status, float weightage, SearchStudentDTO student, List<SearchStudentDTO> members) {
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.assignDate = assignDate;
		this.estimatedDueDate = estimatedDueDate;
		this.priority = priority;
		this.status = status;
		this.weightage = weightage;
		this.student = student;
		this.members = members;
	}

	public TaskUpdateDTO(long id, String tittle, String description, Date assignDate, Date estimatedDueDate,
			Priority priority, AssignmentStatus status, float weightage, SearchStudentDTO student, FileRespondDTO fileRespondDTO, 
			List<SearchStudentDTO> members) {
		this.id = id;
		this.tittle = tittle;
		this.description = description;
		this.assignDate = assignDate;
		this.estimatedDueDate = estimatedDueDate;
		this.priority = priority;
		this.status = status;
		this.weightage = weightage;
		this.fileRespondDTO = fileRespondDTO;
		this.student = student;
		this.members = members;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getEstimatedDueDate() {
		return estimatedDueDate;
	}

	public void setEstimatedDueDate(Date estimatedDueDate) {
		this.estimatedDueDate = estimatedDueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public AssignmentStatus getStatus() {
		return status;
	}

	public void setStatus(AssignmentStatus status) {
		this.status = status;
	}

	public float getWeightage() {
		return weightage;
	}

	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}

	public FileRespondDTO getFileRespondDTO() {
		return fileRespondDTO;
	}

	public void setFileRespondDTO(FileRespondDTO fileRespondDTO) {
		this.fileRespondDTO = fileRespondDTO;
	}

	public SearchStudentDTO getStudent() {
		return student;
	}

	public void setStudent(SearchStudentDTO student) {
		this.student = student;
	}

	public List<SearchStudentDTO> getMembers() {
		return members;
	}

	public void setMembers(List<SearchStudentDTO> members) {
		this.members = members;
	}
	
	
}
