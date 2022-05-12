package com.ykb.ATMS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.dao.StudentRepository;
import com.ykb.ATMS.entity.Student;

@Service
public class StudentService implements IStudentService {

	private StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student findById(int id) {
		Optional<Student> result = studentRepository.findById(id);
		
		Student student=null;
		
		if(result!=null)
			student=result.get();
		else
			throw new RuntimeException("Student ID not found - "+id);
		
		return student;
	}

	@Override
	public void save(Student student) {

		studentRepository.save(student);
	}

	@Override
	public void deleteById(int id) {
		
		studentRepository.deleteById(id);
	}

}
