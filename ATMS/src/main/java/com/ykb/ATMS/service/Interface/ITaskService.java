package com.ykb.ATMS.service.Interface;

import java.util.List;

import com.ykb.ATMS.DTO.DistributeTasksDTO;
import com.ykb.ATMS.DTO.LinkTaskDTO;
import com.ykb.ATMS.DTO.MarkAssignmentInfoDTO;
import com.ykb.ATMS.DTO.StudentTasksDTO;
import com.ykb.ATMS.DTO.TaskUpdateDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;

public interface ITaskService {

	List<Task> findAll();

	Task findById(long id);

	void create(Task task, long tid);

	void deleteById(long id);

	void update(Task assignment);

	List<Task> findByTeam(long id);

	List<Task> getTasksByStudentAdnTeamID(long sid, long tid);

	DistributeTasksDTO distributeTasks(long teamId, List<Task> tasks);

	void updateAssignTo(List<Task> tasks);

	void updateAssignTo(List<Task> tasks, long sid);

	MarkAssignmentInfoDTO getStudentTaskStatusNumber(long id);

	LinkTaskDTO getTasksByStudentAndTeamID(long sid, long tid, long fid);

	LinkTaskDTO getTasksByTeamID(long tid, long fid);

}
