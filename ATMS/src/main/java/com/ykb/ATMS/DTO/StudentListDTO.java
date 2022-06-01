package com.ykb.ATMS.DTO;

import java.util.List;

import com.ykb.ATMS.entity.Intake;

public class StudentListDTO {
	private List<SearchStudentDTO> students;
	
	private List<Intake> intakes;

	public List<SearchStudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<SearchStudentDTO> students) {
		this.students = students;
	}

	public List<Intake> getIntakes() {
		return intakes;
	}

	public void setIntakes(List<Intake> intakes) {
		this.intakes = intakes;
	}
	
}
