package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.DTO.SearchLecturerDTO;
import com.ykb.ATMS.entity.Intake;
import com.ykb.ATMS.repository.IntakeRepository;
import com.ykb.ATMS.service.Interface.IIntakeService;

@Service
public class IntakeService implements IIntakeService {

	private IntakeRepository intakeRepository;
	
	@Autowired
	public IntakeService(IntakeRepository intakeRepository) {
		this.intakeRepository=intakeRepository;
	}
	
	@Override
	public List<Intake> findAll() {
		return intakeRepository.findAll();
	}

	@Override
	public Intake findById(long id) {
		Optional<Intake> result = intakeRepository.findById(id);
		
		Intake intake=null;
		
		if(result!=null)
			intake=result.get();
		else
			throw new RuntimeException("Intake ID not found - "+id);
		
		return intake;
	}

	@Override
	public void save(Intake intake) {
		intakeRepository.save(intake);

	}

	@Override
	public void deleteById(long id) {
		intakeRepository.deleteById(id);

	}
	
	@Override
	public List<Intake> findByCode(String keyword){
		
		return intakeRepository.findByCode(keyword);
	}

}
