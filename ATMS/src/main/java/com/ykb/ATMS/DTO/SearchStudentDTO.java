package com.ykb.ATMS.DTO;


public class SearchStudentDTO {

	private long id;
	
	private String firstName;

	public long getId() {
		return id;
	}

	public SearchStudentDTO(long id, String firstName) {
		this.id = id;
		this.firstName = firstName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
}
