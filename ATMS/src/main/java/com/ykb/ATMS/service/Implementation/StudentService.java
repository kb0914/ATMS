package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.repository.StudentRepository;
import com.ykb.ATMS.service.Interface.IStudentService;

@Service
public class StudentService implements IStudentService {

	private StudentRepository studentRepository;
	private IntakeService intakeService;
	private ModelMapper modelMapper;
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	public StudentService(StudentRepository studentRepository, IntakeService intakeService, ModelMapper modelMapper, PasswordEncoder bcryptEncoder) {
		this.studentRepository = studentRepository;
		this.intakeService=intakeService;
		this.modelMapper=modelMapper;
		this.bcryptEncoder=bcryptEncoder;
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
	public SearchStudentDTO getSearchStudentByI(long id){
		Student student =findById(id);
		return new SearchStudentDTO(student.getId(), student.getUsername(), 
				student.getFirstName(), student.getLastName(), 
				student.getEmail(), student.getIntake());
	}

	@Override
	public Student findById(long id) {
		Optional<Student> result = studentRepository.findById(id);
		
		Student student=null;
		
		if(result!=null) {
			student=result.get();
		}
		else
			throw new RuntimeException("Student ID not found - "+id);
		
		return student;
	}
	
	@Override
	public Student findByUsername(String username) {
		return studentRepository.findByUsername(username);
	}
	
	@Override
	public Student create(StudentInfoDTO dto) {
		Student student=new Student();
		student.setId(0);
		student.setUsername(dto.getUsername());
		intakeService.findById(dto.getIntake().getId()).addStudent(student);
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setPassword(bcryptEncoder.encode(dto.getPassword()));
		
		return studentRepository.save(student);
	}

	@Override
	public Student update(StudentInfoDTO dto) {
		Student student=findById(dto.getId());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setIntake(dto.getIntake());
		//student.setPassword(bcryptEncoder.encode(dto.getPassword()));
		
		return studentRepository.save(student);
	}
	
	@Override
	public void save(Student student) {

		studentRepository.save(student);
	}

	@Override
	public void deleteById(long id) {
		
		findById(id).getManageTeam().forEach(i->i.setTeamLead(null));
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
	
	@Override
	public List<TeamDTO> getTeamById(long id){
		List<TeamDTO> teamdto=new ArrayList<>();
		
		findById(id).getTeams().forEach(i->{
			TeamDTO t=new TeamDTO(i.getTeam().getId(), i.getTeam().getAssignment(), null, null);
			teamdto.add(t);
		});
		return teamdto;
	}
	
	private List<SearchStudentDTO> SearconvertStudentTOSeachDTO(List<Student> students) {
		List<SearchStudentDTO> dto = new ArrayList<>();
		students.stream().forEach(i->dto.add(convertToDto(i)));
		
		return dto;
	}
	
	@Override
	public SearchStudentDTO convertToDto(Student post) {
		
		SearchStudentDTO postDto = modelMapper.map(post, SearchStudentDTO.class);
		
	    return postDto;
	}
}
