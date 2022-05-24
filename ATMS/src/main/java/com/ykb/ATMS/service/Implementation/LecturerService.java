package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.DTO.SearchLecturerDTO;
import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.repository.LecturerRepository;
import com.ykb.ATMS.service.Interface.ILecturerService;

@Service
public class LecturerService implements ILecturerService{

	private LecturerRepository lecturerRepository;
	
	@Autowired
	public LecturerService(LecturerRepository lecturerRepository) {
		this.lecturerRepository=lecturerRepository;
	}
	
	@Override
	public List<Lecturer> findAll() {
		return lecturerRepository.findAll();
	}

	@Override
	public Lecturer findById(long id) {
		Optional<Lecturer> result = lecturerRepository.findById(id);
		
		Lecturer lecturer=null;
		
		if(result!=null)
			lecturer=result.get();
		else
			throw new RuntimeException("Student ID not found - "+id);
		
		return lecturer;
	}
	
	@Override
	public List<SearchLecturerDTO> findByFirstName(String keyword){
		
		List<SearchLecturerDTO> students = new ArrayList<>();
		lecturerRepository.findByFirstName(keyword).stream().forEach(i->students.add(new SearchLecturerDTO(i.getId(), i.getFirstName())));
		
		return students;
	}

	@Override
	public void save(Lecturer lecturer) {
		lecturerRepository.save(lecturer);
	}

	@Override
	public void deleteById(long id) {
		lecturerRepository.deleteById(id);
	}

}
