package com.ykb.ATMS.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.enums.Priority;

@Entity
@Table(name="task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="tittle")
	private String tittle;
	
	@Column(name="description")
	private String description;
	
	@Column(name="assign_date")
	private Date assignDate;
	
	@Column(name="estimated_due_date")
	private Date estimatedDueDate;
	
	@Column(name = "priority")
	private Priority priority;
	
	@Column(name = "status")
	private AssignmentStatus status;
	
	@Column(name="weightage")
	private float weightage;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="team_id")
	private Team team;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="prove_file_id")
	private FileDB file;

	public Task() {
	}

	public Task(String tittle, String description, Date assignDate, Date estimatedDueDate, Priority priority,
			AssignmentStatus status, float weightage, Team team) {
		this.tittle=tittle;
		this.description = description;
		this.assignDate = assignDate;
		this.estimatedDueDate = estimatedDueDate;
		this.priority = priority;
		this.status = status;
		this.weightage = weightage;
		this.team = team;
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

	public void setTittle(String title) {
		this.tittle = title;
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public FileDB getFile() {
		return file;
	}

	public void setFile(FileDB file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", assignDate=" + assignDate + ", estimatedDueDate="
				+ estimatedDueDate + ", priority=" + priority + ", status=" + status + ", weightage=" + weightage
				+ ", team=" + team + ", student=" + student + "]";
	}
	
	
}
