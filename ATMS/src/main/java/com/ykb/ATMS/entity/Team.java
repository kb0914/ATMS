package com.ykb.ATMS.entity;

import java.util.ArrayList;
import java.util.Iterator;
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
import javax.persistence.OneToOne;
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
	@JoinColumn(name="assignment_id", nullable=false)
	private Assignment assignment;
	
	@OneToMany(
		fetch = FetchType.LAZY, 
        mappedBy = "team",
        cascade = {CascadeType.ALL},
        orphanRemoval = true
    )
    private List<TeamStudent> students;
	
	@OneToMany(mappedBy="team",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Task> tasks;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="team_lead_id")
	private Student teamLead;
	
	@OneToMany(mappedBy="team",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
	@JsonIgnore
	private List<FileDB> files;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "TeamMainFile",
		joinColumns = @JoinColumn(name = "team_id"),
		inverseJoinColumns = @JoinColumn(name = "file_id"))
	@JsonIgnore
	private FileDB mainFile;
	
	@Column(name="mark")
	private Double mark;

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
	
	public List<TeamStudent> getStudents() {
		return students;
	}

	public void setStudents(List<TeamStudent> students) {
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

	public Student getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(Student teamLead) {
		this.teamLead = teamLead;
	}

	public List<FileDB> getFiles() {
		return files;
	}

	public void setFiles(List<FileDB> files) {
		this.files = files;
	}

	public FileDB getMainFile() {
		return mainFile;
	}

	public void setMainFile(FileDB mainFile) {
		this.mainFile = mainFile;
	}

	public Double getMark() {
		return mark;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

	public void addStudent(Student student) {
		if(this.students ==  null) {
			this.students = new ArrayList<>();
		}
		TeamStudent ts=new TeamStudent(this, student);
		this.students.add(ts);
		student.getTeams().add(ts);
	}
	
	public void removeStudent(Student student) {
        for (Iterator<TeamStudent> iterator = students.iterator();
             iterator.hasNext(); ) {
        	TeamStudent ts = iterator.next();
 
            if (ts.getTeam().equals(this) &&
                    ts.getStudent().equals(student)) {
                iterator.remove();
                ts.getStudent().getTeams().remove(ts);
                ts.setStudent(null);
                ts.setTeam(null);
            }
        }
//		List<TeamStudent> temp=this.students;
//		for(int i=0; i<temp.size();i++) {
//			if(students.get(i).getTeam().getId()==this.id&&students.get(i).getStudent().getId()==student.getId()) {
//				students.get(i).getStudent().setTeams(null);
//				students.get(i).getTeam().setStudents(null);
//				students.remove(students.get(i));
//			}
//		}
    }
	
	public void addFile(FileDB file) {
		if(this.files ==  null) {
			this.files = new ArrayList<>();
		}
		
		this.files.add(file);
	}
	
	public void addTask(Task task) {
		if(this.tasks ==  null) {
			this.tasks = new ArrayList<>();
		}
		
		this.tasks.add(task);
	}
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
// 
//        if (o == null || getClass() != o.getClass())
//            return false;
// 
//        Team team = (Team) o;
//        return Objects.equals(id, team.id);
//    }
// 
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
