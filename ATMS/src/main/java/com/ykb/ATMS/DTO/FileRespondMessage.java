package com.ykb.ATMS.DTO;

public class FileRespondMessage {
	private String message;
	
	public FileRespondMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
