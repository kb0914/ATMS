package com.ykb.ATMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ykb.ATMS.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
