package com.ykb.ATMS.DTO;

import java.sql.Date;

public class FileRespondDTO {
	
	private long id;

	private String name;
	
	private String url;
	
	private String type;
	  
	private long size;
	
	private Date uploadedDate;
	  
	public FileRespondDTO(long id, String name, String url, String type, long size, Date uploadedDate) {
		this.id=id;
	    this.name = name;
	    this.url = url;
	    this.type = type;
	    this.size = size;
	    this.uploadedDate=uploadedDate;
	  }
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
}
