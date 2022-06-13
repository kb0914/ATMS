package com.ykb.ATMS.DTO;

import java.util.List;

import com.ykb.ATMS.entity.Task;

public class DistributeTasksDTO {

	private List<Task> unassignedTask;
	
	private List<Integer> weightages;
	
	public DistributeTasksDTO() {
		super();
	}

	public DistributeTasksDTO(List<Task> unassignedTask, List<Integer> weightages) {
		super();
		this.unassignedTask = unassignedTask;
		this.weightages = weightages;
	}

	public List<Task> getUnassignedTask() {
		return unassignedTask;
	}

	public void setUnassignedTask(List<Task> unassignedTask) {
		this.unassignedTask = unassignedTask;
	}

	public List<Integer> getWeightages() {
		return weightages;
	}

	public void setWeightages(List<Integer> weightages) {
		this.weightages = weightages;
	}
	
	
}
