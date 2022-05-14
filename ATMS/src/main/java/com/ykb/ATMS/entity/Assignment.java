package com.ykb.ATMS.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="assignemnt")
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="assingnemnt_name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="assign_date")
	private Date assignDate;
	
	@Column(name="due_date")
	private String dueDate;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="lecturer_id")
	private Lecturer lecturer;
	
	@OneToMany(mappedBy="assignment",
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Team> team;

	public Assignment() {}
	
	public Assignment(String name, String description, Date assignDate, String dueDate, Lecturer lecturer) {
		this.name = name;
		this.description = description;
		this.assignDate = assignDate;
		this.dueDate = dueDate;
		this.lecturer = lecturer;
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

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	

	public List<Team> getGroupings() {
		return team;
	}

	public void setGroupings(List<Team> groupings) {
		this.team = groupings;
	}
	
	public void addTeam(Team grouping) {
		if(this.team == null) {
			this.team=new ArrayList<>();
		}
		
		this.team.add(grouping);
		
		grouping.setAssingment(this);
	}

	@Override
	public String toString() {
		return "Assigment [id=" + id + ", name=" + name + ", description=" + description + ", assignDate=" + assignDate
				+ ", dueDate=" + dueDate + ", lecturer=" + lecturer + "]";
	}
	
	
}
