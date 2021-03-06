package com.ykb.ATMS.entity;


import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="assignment")
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="assignment_name", nullable=false, length=50)
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="assign_date")
	private Date assignDate;
	
	@Column(name="due_date")
	private Date dueDate;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="lecturer_id")
	@JsonIgnore
	private Lecturer lecturer;
	
	@ManyToOne(fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="intake_id")
	private Intake intake;
	
	@OneToMany(mappedBy="assignment",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Team> team;
	
	@Column(name="max_team_member")
	private int maxTeamMember;

	public Assignment() {}
	
	public Assignment(String name, String description, Date assignDate, Date dueDate, Lecturer lecturer, int maxTeamMember) {
		this.name = name;
		this.description = description;
		this.assignDate = assignDate;
		this.dueDate = dueDate;
		this.lecturer = lecturer;
		this.maxTeamMember=maxTeamMember;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Team> getTeam() {
		return team;
	}

	public void setTeam(List<Team> team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	
	public Intake getIntake() {
		return intake;
	}

	public void setIntake(Intake intake) {
		this.intake = intake;
	}

	public int getMaxTeamMember() {
		return maxTeamMember;
	}

	public void setMaxTeamMember(int maxTeamMember) {
		this.maxTeamMember = maxTeamMember;
	}
	
	public void addTeam(Team team) {
		if(this.team == null) {
			this.team=new ArrayList<>();
		}
		
		this.team.add(team);
		
		team.setAssignment(this);
	}
	
	public void removeTeam(Team team) {
		
		this.team.remove(team);
		
		team.setAssignment(null);
	}

	@Override
	public String toString() {
		return "Assigment [id=" + id + ", name=" + name + ", description=" + description + ", assignDate=" + assignDate
				+ ", dueDate=" + dueDate + ", lecturer=" + lecturer + "]";
	}
	
	
}
