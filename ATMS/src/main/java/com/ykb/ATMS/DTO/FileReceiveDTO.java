package com.ykb.ATMS.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ykb.ATMS.entity.Task;

public class FileReceiveDTO {
	private MultipartFile file;
	private List<Task> tasks;
	
	public FileReceiveDTO() {
	}
	
	public FileReceiveDTO(MultipartFile file, List<Task> tasks) {
		this.file = file;
		this.tasks = tasks;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	

}
