package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.repository.StudentRepository;
import com.ykb.ATMS.service.Interface.IStudentService;

@Service
public class StudentService implements IStudentService {

	private StudentRepository studentRepository;
	private IntakeService intakeService;
	
	@Autowired
	public StudentService(StudentRepository studentRepository, IntakeService intakeService) {
		this.studentRepository = studentRepository;
		this.intakeService=intakeService;
	}
	
	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	@Override
	public StudentListDTO getStudentList(){
		StudentListDTO dto=new StudentListDTO();
		dto.setStudents(SearconvertStudentTOSeachDTO(findAll()));
		dto.setIntakes(intakeService.findAll());
		return dto;
	}

	@Override
	public Student findById(long id) {
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
	public void deleteById(long id) {
		
		studentRepository.deleteById(id);
	}
	
	@Override
	public List<SearchStudentDTO> searchStudent(String username, String intakeCode) {
		List<SearchStudentDTO> dto=SearconvertStudentTOSeachDTO(findAll());
		if(username!=null) {
			dto=dto.stream().filter(i->i.getUsername().contains(username)).toList();
		}
		if(intakeCode==null || intakeCode.isEmpty()|| intakeCode.isBlank()) {
			
		}else {
			System.out.println(intakeCode);
			dto=dto.stream().filter(i->i.getIntake().getCode().equals(intakeCode)).toList();
		}
		return dto;
	}

	@Override
	public List<SearchStudentDTO> getFirstNameAndID(){
		
		return SearconvertStudentTOSeachDTO(findAll());
	}
	
	@Override
	public List<SearchStudentDTO> getFirstNameAndIdByIntake(long id){
		
		List<SearchStudentDTO> students = new ArrayList<>();
		studentRepository.findByIntake(id).stream().forEach(i->students.add(
				new SearchStudentDTO(i.getId(), i.getUsername(), i.getFirstName(), i.getLastName(), 
						i.getEmail(), i.getIntake())));
		return students;
	}
	
	private List<SearchStudentDTO> SearconvertStudentTOSeachDTO(List<Student> students) {
		List<SearchStudentDTO> dto = new ArrayList<>();
		students.stream()
		.forEach(i->dto.add(
				new SearchStudentDTO(i.getId(), i.getUsername(), i.getFirstName(), i.getLastName(), 
						i.getEmail(), i.getIntake())));
		
		return dto;
	}
}
