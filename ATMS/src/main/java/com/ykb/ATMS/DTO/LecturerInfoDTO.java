package com.ykb.ATMS.DTO;

import com.ykb.ATMS.entity.Intake;

public class LecturerInfoDTO {

	private long id;
	private String username;
	
	private String role;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;

	public LecturerInfoDTO() {
	}
	
	public LecturerInfoDTO(long id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role=role;
	}

	public LecturerInfoDTO(long id, String username, String firstName, String lastName, String email, Intake intake,
			String password) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password=password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
