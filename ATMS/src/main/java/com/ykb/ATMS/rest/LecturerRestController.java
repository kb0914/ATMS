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
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.LecturerInfoDTO;
import com.ykb.ATMS.DTO.SearchLecturerDTO;
import com.ykb.ATMS.entity.Intake;
import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.service.Interface.ILecturerService;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class LecturerRestController {

	private ILecturerService lecturerService;
	
	@Autowired
	public LecturerRestController(ILecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}
	
	@GetMapping("/lecturers")
	public List<Lecturer>findAll(){
		return lecturerService.findAll();
	}
	
	@GetMapping("/lecturers/{id}")
	public Lecturer findById(@PathVariable long id){
		
		Lecturer lecturer = lecturerService.findById(id);
		if(lecturer==null)
			throw new RuntimeException("lecturer id not found - " + id);
		
		return lecturer;
	}
	
	@GetMapping("/lecturers/search/{keyword}")
	public List<SearchLecturerDTO> findByCode(@PathVariable String keyword){
		
		return lecturerService.findByFirstName(keyword);
	}
	
	@PostMapping("/lecturers")
	public ResponseEntity<?> addLecturer(@RequestBody LecturerInfoDTO lecturer){
		
		return ResponseEntity.ok(lecturerService.create(lecturer));
	}
	
	
	@PutMapping("/lecturers")
	public ResponseEntity<?> updateLecturer(@RequestBody LecturerInfoDTO lecturer){
		System.out.println(lecturer.getPassword());
		return ResponseEntity.ok(lecturerService.update(lecturer));
	}
	
	@DeleteMapping("/lecturers/{id}")
	public Lecturer deleteById(@PathVariable int id){
		
		Lecturer lecturer = lecturerService.findById(id);
		
		if(lecturer==null)
			throw new RuntimeException("Lecturer id not found - " + id);
		
		lecturerService.deleteById(id);
		
		return lecturer;
	}
}
