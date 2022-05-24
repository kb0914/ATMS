package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.entity.Intake;

public interface IIntakeService {
	public List<Intake> findAll();
	
	public Intake findById(long id);
	
	public void save(Intake assignment);
	
	public void deleteById(long id);

	List<Intake> findByCode(String keyword);
}
