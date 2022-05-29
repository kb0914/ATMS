package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.entity.Task;

public interface ITaskService {

	List<Task> findAll();

	Task findById(long id);

	void create(Task task, long tid);

	void deleteById(long id);

	void update(Task assignment);

	List<Task> findByTeam(long id);

}
