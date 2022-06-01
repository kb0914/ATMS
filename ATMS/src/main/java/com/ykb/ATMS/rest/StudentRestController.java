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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.service.Interface.IIntakeService;
import com.ykb.ATMS.service.Interface.IStudentService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class StudentRestController {

	private IStudentService studentService;
	private IIntakeService intakeService;
	
	@Autowired
	public StudentRestController(IStudentService studentService, IIntakeService intakeService) {
		this.studentService = studentService;
		this.intakeService=intakeService;
	}
	
	@GetMapping("/students")
	public List<Student>findAll(){
		return studentService.findAll();
	}
	
	@GetMapping("/students/list")
	public StudentListDTO getStudentList(){
		StudentListDTO dto=studentService.getStudentList();
		return dto;
	}
	
	@GetMapping("/students/{studentId}")
	public Student findById(@PathVariable long studentId){
		
		Student student = studentService.findById(studentId);
		if(student==null)
			throw new RuntimeException("Student id not found - " + studentId);
		
		return student;
	}
	
	@GetMapping("/students/getfnid")
	public List<SearchStudentDTO> getFirstNameAndID(){
		return studentService.getFirstNameAndID();
	}
	
	@GetMapping("/students/getfnid/{id}")
	public List<SearchStudentDTO> getFirstNameAndIdByIntake(@PathVariable long id){
		return studentService.getFirstNameAndIdByIntake(id);
	}
	
	@GetMapping("/students/search")
	public List<SearchStudentDTO> searchStudent(@RequestParam(name="username",required = false) String username, 
			@RequestParam(name="intake",required = false) String intakeName){
		return studentService.searchStudent(username, intakeName);
	}
	
	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student){
		student.setId(0);
		intakeService.findById(student.getIntake().getId()).addStudent(student);
		studentService.save(student);
		
		return student;
	}
	
	
	@PutMapping("/students")
	public Student updateStudent(@RequestBody Student student){
		
		intakeService.findById(student.getIntake().getId()).addStudent(student);
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
