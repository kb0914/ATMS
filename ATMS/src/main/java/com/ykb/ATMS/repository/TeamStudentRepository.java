package com.ykb.ATMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.TeamStudent;
import com.ykb.ATMS.entity.TeamStudentID;

public interface TeamStudentRepository extends JpaRepository<TeamStudent, TeamStudentID>{
	
	@Query("DELETE FROM TeamStudent s where s.team.id=:tid and s.student.id=:sid")
	public void remove(long tid, long sid);

}
