package com.ykb.ATMS.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "TeamStudent")
@Table(name = "team_student")
public class TeamStudent {

	@EmbeddedId
    private TeamStudentID id;
 
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @MapsId("teamId")
    @JoinColumn(name = "TEAM_ID")
    @JsonIgnore
    private Team team;
 
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @MapsId("studentId")
    @JoinColumn(name = "STUDENT_ID")
    @JsonIgnore
    private Student student;
 
    @Column(name = "mark")
    private double mark;
 
    public TeamStudent() {}
    
    public TeamStudent(Team team, Student student) {
    	this.id=new TeamStudentID(team.getId(), student.getId());
		this.team = team;
		this.student = student;
	}
    
    public TeamStudent(Team team, Student student, double mark) {
		this.team = team;
		this.student = student;
		this.mark = mark;
	}
	
    public TeamStudentID getId() {
		return id;
	}

	public void setId(TeamStudentID id) {
		this.id = id;
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

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}
	
//	@Override
//    public boolean equals(Object o) {
//		System.out.println(o);
//        if (this == o) return true;
// 
//        if (o == null || getClass() != o.getClass())
//            return false;
// 
//        TeamStudent that = (TeamStudent) o;
//        return Objects.equals(team, that.team) &&
//               Objects.equals(student, that.student);
//    }
// 
//
//	@Override
//    public int hashCode() {
//        return Objects.hash(team, student);
//    }
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TeamStudent that = (TeamStudent) o;

		if (getId() != null ? !getId().equals(that.getId())
				: that.getId() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getId() != null ? getId().hashCode() : 0);
	}
}
