package com.ykb.ATMS.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.service.Interface.IStudentService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class StudentRestController {

	private IStudentService studentService;
	
	@Autowired
	public StudentRestController(IStudentService studentService) {
		this.studentService = studentService;
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
		return studentService.findById(studentId);
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
	
	@GetMapping("/students/getteams/{id}")
	public List<TeamDTO> getTeamsById(@PathVariable long id){
		return studentService.getTeamById(id);
	}
	
	@GetMapping("/students/getAssignemntWithoutTeamById/{id}")
	public List<Assignment> getAssignemntWithoutTeamById(@PathVariable long id){
		return studentService.getAssignemntWithoutTeamById(id);
	}
	
	@PostMapping("/students")
	public ResponseEntity<?>  addStudent(@RequestBody StudentInfoDTO student){
		
		return ResponseEntity.ok(studentService.create(student));
	}
	
	@PutMapping("/students")
	public ResponseEntity<?> updateStudent(@RequestBody StudentInfoDTO studentDto){
		
		return ResponseEntity.ok(studentService.update(studentDto));
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
