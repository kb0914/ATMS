package com.ykb.ATMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	@Query("Select t from Task t where t.team.id=:id")
	public List<Task> findByTeam(long id);
	
	@Query("Select t from Task t where t.student.id=:sid and t.team.id=:tid")
	public List<Task> findByStudentAndTeam(long sid, long tid);
}
