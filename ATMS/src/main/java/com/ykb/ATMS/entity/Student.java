package com.ykb.ATMS.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
@Table(name="student")
public class Student extends User{

	@Column(name="first_name", nullable=false, length=50)
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=50)
	private String lastName;
	
	@Column(name="email", nullable=false, length=50)
	private String email;
	
	@OneToMany(
		fetch = FetchType.LAZY, 
        mappedBy = "student",
        cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
        orphanRemoval = true
    )
	@JsonIgnore
    private List<TeamStudent> teams;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="intake_id", nullable=false)
	private Intake intake;
	
	@OneToMany(mappedBy="student",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Task> tasks;
	
	@OneToMany(mappedBy="teamLead",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<Team> manageTeam;
	
	@OneToMany(mappedBy="student",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonIgnore
	private List<FileDB> files;

	public Student() {
	}

	public Student(String username, String firstName, String lastName, String email, String password) {
		this.username=username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TeamStudent> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamStudent> teams) {
		this.teams = teams;
	}

	public Intake getIntake() {
		return intake;
	}

	public void setIntake(Intake intake) {
		this.intake = intake;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<FileDB> getFiles() {
		return files;
	}

	public void setFiles(List<FileDB> files) {
		this.files = files;
	}

	public void addTask(Task task) {
		if(this.tasks ==  null) {
			this.tasks = new ArrayList<>();
		}
		
		this.tasks.add(task);
		task.setStudent(this);
	}
	
	public List<Team> getManageTeam() {
		return manageTeam;
	}

	public void setManageTeam(List<Team> manageTeam) {
		this.manageTeam = manageTeam;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password +"]";
	}
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
// 
//        if (o == null || getClass() != o.getClass())
//            return false;
// 
//        Student student = (Student) o;
//        return Objects.equals(username, student.username);
//    }
// 
//    @Override
//    public int hashCode() {
//        return Objects.hash(username);
//    }
	
}
