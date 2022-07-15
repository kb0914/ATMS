package com.ykb.ATMS.entity;

import java.sql.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "files")
public class FileDB {
	
	@Id
  	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="name", nullable=false, length=100)
	private String name;
	
	@Column(name="type", nullable=false, length=100)
	private String type;
	
	@Lob
	@Column(name="data_byte")
	private byte[] data;
	
	@Column(name="upload_date")
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="uploaded_by")
	private Student student;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="team_id")
	private Team team;
	
	@OneToMany(mappedBy="file",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Task> tasks;
	
	public FileDB() {
	}
	
	public FileDB(String name, String type, byte[] data, Date date, Student student, Team team) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
		this.date = date;
		this.student = student;
		this.team = team;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		if(this.tasks ==  null) {
			this.tasks = new ArrayList<>();
		}
		
		this.tasks.add(task);
		task.setFile(this);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
		task.setFile(null);
	}
}
