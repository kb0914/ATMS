package com.ykb.ATMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>{
	
	@Query("Select s from Lecturer s where s.firstName like %:name%")
	public List<Lecturer> findByFirstName(String name);
}
