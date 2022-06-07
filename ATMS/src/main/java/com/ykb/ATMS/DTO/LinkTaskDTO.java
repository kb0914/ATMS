package com.ykb.ATMS.DTO;

import java.util.List;

import com.ykb.ATMS.entity.Task;

public class LinkTaskDTO {
	private List<Task> allTask;
	private List<Task> linkedTask;
	
	
	public LinkTaskDTO() {
	}
	
	public LinkTaskDTO(List<Task> allTask, List<Task> linkedTask) {
		this.allTask = allTask;
		this.linkedTask = linkedTask;
	}
	
	public List<Task> getAllTask() {
		return allTask;
	}
	public void setAllTask(List<Task> allTask) {
		this.allTask = allTask;
	}
	public List<Task> getLinkedTask() {
		return linkedTask;
	}
	public void setLinkedTask(List<Task> linkedTask) {
		this.linkedTask = linkedTask;
	}
	
}
