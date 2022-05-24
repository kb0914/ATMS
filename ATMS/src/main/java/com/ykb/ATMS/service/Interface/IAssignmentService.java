package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Lecturer;

public interface IAssignmentService {

	public List<Assignment> findAll();
	
	public Assignment findById(long id);
	
	public void create(Assignment assignment, long lid);
	
	public void deleteById(long id);
	
	public void update(Assignment assignment);
}
