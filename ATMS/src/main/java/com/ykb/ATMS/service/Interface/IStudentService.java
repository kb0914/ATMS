package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.entity.Student;

public interface IStudentService {

	public List<Student> findAll();
	
	public Student findById(long id);
	
	public void save(Student student);
	
	public void deleteById(long id);

	List<Student> findByFirstName(String name);

	List<SearchStudentDTO> getFirstNameAndID();

	List<SearchStudentDTO> getFirstNameAndIdByIntake(long id);
}
