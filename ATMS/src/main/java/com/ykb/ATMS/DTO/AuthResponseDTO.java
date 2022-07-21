package com.ykb.ATMS.DTO;

public class AuthResponseDTO {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private long id;
	private String username;
	private String role;

	public AuthResponseDTO(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public AuthResponseDTO(String jwttoken, long id, String username, String role) {
		super();
		this.jwttoken = jwttoken;
		this.id = id;
		this.username = username;
		this.role = role;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
