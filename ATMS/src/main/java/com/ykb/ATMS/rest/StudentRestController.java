package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.service.Implementation.StudentService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class StudentRestController {

	private StudentService studentService;
	
	@Autowired
	public StudentRestController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("/students")
	public List<Student>findAll(){
		return studentService.findAll();
	}
	
	@GetMapping("/students/{studentId}")
	public Student findById(@PathVariable long studentId){
		
		Student student = studentService.findById(studentId);
		if(student==null)
			throw new RuntimeException("Student id not found - " + studentId);
		
		return student;
	}
	
	@PostMapping("/students")
	public Student addEmployee(@RequestBody Student student){
		
		student.setId(0);
		studentService.save(student);
		
		return student;
	}
	
	
	@PutMapping("/students")
	public Student updateEmployee(@RequestBody Student student){
		
		studentService.save(student);
		
		return student;
	}
	
	@DeleteMapping("/students/{studentId}")
	public Student deleteById(@PathVariable long studentId){
		
		Student student = studentService.findById(studentId);
		
		if(student==null)
			throw new RuntimeException("Student id not found - " + studentId);
		
		studentService.deleteById(studentId);
		
		return student;
	}
}
