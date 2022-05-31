package com.ykb.ATMS.DTO;


import com.ykb.ATMS.entity.Intake;

public class SearchStudentDTO {

	private long id;
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Intake intake;

	public SearchStudentDTO() {
	}

	public SearchStudentDTO(long id, String username, String firstName, String lastName, String email, Intake intake) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.intake = intake;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Intake getIntake() {
		return intake;
	}

	public void setIntake(Intake intake) {
		this.intake = intake;
	}
	
	
	
}
