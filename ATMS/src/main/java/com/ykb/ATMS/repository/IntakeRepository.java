package com.ykb.ATMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.Intake;

public interface IntakeRepository extends JpaRepository<Intake, Long>{

	@Query("Select s from Intake s where s.code like %:keyword%")
	public List<Intake> findByCode(String keyword);
}
