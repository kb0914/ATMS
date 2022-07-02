package com.ykb.ATMS.DTO;

public class TeamStudentDTO {
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private double mark;
	public TeamStudentDTO() {}

	public TeamStudentDTO(long id, String username, String firstName, String lastName, double mark) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mark = mark;
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
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
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

	@Override
	public String toString() {
		return "TeamStudentDTO [id=" + id + ", username=" + username + ", mark=" + mark + "]";
	}
	
	
}
