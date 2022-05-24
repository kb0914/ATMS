package com.ykb.ATMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("Select s from Student s where s.firstName like %:name%")
	public List<Student> findByFirstName(String name);
	
	@Query("Select s from Student s where s.intake.id=:id")
	public List<Student> findByIntake(long id);
}
