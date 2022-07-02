 package com.ykb.ATMS.service.Implementation;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.helper.ExcelHelper;
import com.ykb.ATMS.repository.AssignmentRepository;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.ILecturerService;

@Service
public class AssignmentService implements IAssignmentService{

	private AssignmentRepository assignmentRepository;
	private ILecturerService lecturerService;
	
	@Autowired
	public AssignmentService(AssignmentRepository assignmentService, ILecturerService lecturerService) {
		this.assignmentRepository = assignmentService;
		this.lecturerService=lecturerService;
	}
	
	@Override
	public List<Assignment> findAll() {
		return assignmentRepository.findAll();
	}

	@Override
	public Assignment findById(long id) {
		Optional<Assignment> result = assignmentRepository.findById(id);
		
		Assignment assignment=null;
		
		if(result!=null)
			assignment=result.get();
		else
			throw new RuntimeException("Assignment ID not found - "+id);
		
		return assignment;
	}
	
	@Override
	public List<Assignment> getAssignmentsByLEcturerId(long id) {
		
		return lecturerService.findById(id).getAssignments();
	}

	@Override
	public void create(Assignment assignment, long lid) {
		Lecturer lecturer=lecturerService.findById(lid);
		lecturer.add(assignment);
		lecturerService.save(lecturer);
	}

	@Override
	public void deleteById(long id) {
		assignmentRepository.deleteById(id);
	}

	@Override
	public void update(Assignment assignment) {
		assignmentRepository.save(assignment);
	}
	
	@Override
	public ByteArrayInputStream load(long aid) {
	    List<Team> team = findById(aid).getTeam();
	    ByteArrayInputStream in = ExcelHelper.teamStudentToExcel(team);
	    return in;
	  }
	
	
}
