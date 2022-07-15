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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="intake")
public class Intake {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="code",
			unique = true,nullable=false, length=20)
	private String code;
	
	@OneToMany(mappedBy="intake",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Assignment> assignments;
	
	@OneToMany(mappedBy="intake",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Student> students;
	
	public Intake() {
	}

	public Intake(String code) {
		this.code = code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void addAssignment(Assignment assignment) {
		if(this.assignments == null) {
			this.assignments=new ArrayList<>();
		}
		
		this.assignments.add(assignment);
		
		assignment.setIntake(this);
	}
	
	public void addStudent(Student student) {
		if(this.students == null) {
			this.students=new ArrayList<>();
		}
		
		this.students.add(student);
		
		student.setIntake(this);
	}
	
}
