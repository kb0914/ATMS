package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.LecturerInfoDTO;
import com.ykb.ATMS.DTO.SearchLecturerDTO;
import com.ykb.ATMS.entity.Lecturer;

public interface ILecturerService {

	public List<Lecturer> findAll();
	
	public Lecturer findById(long id);
	
	public void save(Lecturer lecturer);
	
	public void deleteById(long id);

	List<SearchLecturerDTO> findByFirstName(String keyword);

	Lecturer findByUsername(String username);

	Lecturer create(LecturerInfoDTO dto);

	Lecturer update(LecturerInfoDTO dto);
}
