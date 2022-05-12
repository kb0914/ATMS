package com.ykb.ATMS.service;

import java.util.List;

import com.ykb.ATMS.entity.Student;

public interface IStudentService {

	public List<Student> findAll();
	
	public Student findById(int id);
	
	public void save(Student employee);
	
	public void deleteById(int id);
}
