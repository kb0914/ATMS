package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.DTO.StudentListDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;

public interface IStudentService {

	public List<Student> findAll();
	
	public Student findById(long id);
	
	public void deleteById(long id);

	List<SearchStudentDTO> searchStudent(String username, String intakeCode);

	List<SearchStudentDTO> getFirstNameAndID();

	List<SearchStudentDTO> getFirstNameAndIdByIntake(long id);

	StudentListDTO getStudentList();

	SearchStudentDTO getSearchStudentByI(long id);

	List<TeamDTO> getTeamById(long id);

	Student findByUsername(String username);

	Student update(StudentInfoDTO dto);

	Student create(StudentInfoDTO dto);

	void save(Student student);

	SearchStudentDTO convertToDto(Student post);
}
