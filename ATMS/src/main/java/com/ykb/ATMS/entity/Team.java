package com.ykb.ATMS.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="assingment_id")
	private Assignment assignment;
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(
			name="team_student",
			joinColumns = @JoinColumn(name="team_id"),
			inverseJoinColumns = @JoinColumn(name="student_id")
			)
	private List<Student> students;
	
	@OneToMany(mappedBy="team",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Task> tasks;

	public Team() {
		
	}
	
	public Team(Assignment assingment) {
		this.assignment = assingment;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addStudent(Student student) {
		if(this.students ==  null) {
			this.students = new ArrayList<>();
		}
		
		this.students.add(student);
	}
	
	public void addTask(Task task) {
		if(this.tasks ==  null) {
			this.tasks = new ArrayList<>();
		}
		
		this.tasks.add(task);
	}
	
}
