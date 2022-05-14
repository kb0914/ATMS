package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.entity.Lecturer;

public interface ILecturerService {

	public List<Lecturer> findAll();
	
	public Lecturer findById(long id);
	
	public void save(Lecturer lecturer);
	
	public void deleteById(long id);
}
