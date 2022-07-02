package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.DTO.LecturerInfoDTO;
import com.ykb.ATMS.DTO.SearchLecturerDTO;
import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.repository.LecturerRepository;
import com.ykb.ATMS.service.Interface.ILecturerService;

@Service
public class LecturerService implements ILecturerService{

	private LecturerRepository lecturerRepository;
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	public LecturerService(LecturerRepository lecturerRepository, PasswordEncoder bcryptEncoder) {
		this.lecturerRepository=lecturerRepository;
		this.bcryptEncoder=bcryptEncoder;
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
	public Lecturer findByUsername(String username) {
		return lecturerRepository.findByUsername(username);
	}
	
	@Override
	public List<SearchLecturerDTO> findByFirstName(String keyword){
		
		return convertLecturerTOSeachDTO(lecturerRepository.findByFirstName(keyword));
	}

	@Override
	public void save(Lecturer lecturer) {
		lecturerRepository.save(lecturer);
	}
	
	@Override
	public Lecturer create(LecturerInfoDTO dto) {
		Lecturer lec=new Lecturer();
		lec.setId(0);
		lec.setUsername(dto.getUsername());
		lec.setFirstName(dto.getFirstName());
		lec.setLastName(dto.getLastName());
		lec.setEmail(dto.getEmail());
		lec.setPassword(bcryptEncoder.encode(dto.getPassword()));
		
		return lecturerRepository.save(lec);
	}

	@Override
	public Lecturer update(LecturerInfoDTO dto) {
		Lecturer lec=findById(dto.getId());
		lec.setFirstName(dto.getFirstName());
		lec.setLastName(dto.getLastName());
		lec.setEmail(dto.getEmail());
		//lec.setPassword(bcryptEncoder.encode(dto.getPassword()));
		
		return lecturerRepository.save(lec);
	}

	@Override
	public void deleteById(long id) {
		lecturerRepository.deleteById(id);
	}

	private List<SearchLecturerDTO> convertLecturerTOSeachDTO(List<Lecturer> students) {
		List<SearchLecturerDTO> dto = new ArrayList<>();
		students.stream()
		.forEach(i->dto.add(
				new SearchLecturerDTO(i.getId(), i.getUsername(), i.getFirstName(), i.getLastName(), 
						i.getEmail())));
		
		return dto;
	}
}
