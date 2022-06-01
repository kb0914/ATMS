package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.entity.Student;

public interface IStudentService {

	public List<Student> findAll();
	
	public Student findById(long id);
	
	public void save(Student student);
	
	public void deleteById(long id);

	List<SearchStudentDTO> searchStudent(String username, String intakeCode);

	List<SearchStudentDTO> getFirstNameAndID();

	List<SearchStudentDTO> getFirstNameAndIdByIntake(long id);

	StudentListDTO getStudentList();
}
