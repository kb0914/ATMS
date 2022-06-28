package com.ykb.ATMS.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TeamStudentID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "team_id")
    private Long teamId;

    @Column(name = "STUDENT_ID")
    private Long studentId;
    
    public TeamStudentID() {
	}

	public TeamStudentID(Long teamId, Long studentID) {
		this.teamId = teamId;
		this.studentId = studentID;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getStudentID() {
		return studentId;
	}

	public void setStudentID(Long studentID) {
		this.studentId = studentID;
	}

//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
// 
//        if (o == null || getClass() != o.getClass())
//            return false;
// 
//        TeamStudentID that = (TeamStudentID) o;
//        return Objects.equals(team_id, that.team_id) &&
//               Objects.equals(student_id, that.student_id);
//    }
// 
//    @Override
//    public int hashCode() {
//        return Objects.hash(team_id, student_id);
//    }
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamStudentID that = (TeamStudentID) o;

        if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (teamId != null ? teamId.hashCode() : 0);
        result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
        return result;
    }
}
