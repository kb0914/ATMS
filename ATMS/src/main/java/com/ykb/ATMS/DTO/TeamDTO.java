package com.ykb.ATMS.DTO;

import java.util.List;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Task;

public class TeamDTO {
	private long id;
	private Assignment assignment;
	private List<SearchStudentDTO> studentsDTO;
	private List<Task> tasks;
	private SearchStudentDTO teamLead;
	private FileRespondDTO mainFile;
	private List<FileRespondDTO> files;
	
	public TeamDTO() {
	}
	
	public TeamDTO(long id, Assignment assignment, List<SearchStudentDTO> students, List<Task> tasks) {
		this.id = id;
		this.assignment = assignment;
		this.studentsDTO = students;
		this.tasks = tasks;
	}
	
	public TeamDTO(long id, Assignment assignment, List<SearchStudentDTO> students, List<Task> tasks, FileRespondDTO mainFile) {
		this.id = id;
		this.assignment = assignment;
		this.studentsDTO = students;
		this.tasks = tasks;
		this.mainFile=mainFile;
	}
	
	public TeamDTO(long id, Assignment assignment, List<SearchStudentDTO> students, 
			FileRespondDTO mainFile, SearchStudentDTO teamLead) {
		this.id = id;
		this.assignment = assignment;
		this.studentsDTO = students;
		this.mainFile=mainFile;
		this.teamLead=teamLead;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public List<SearchStudentDTO> getStudents() {
		return studentsDTO;
	}

	public void setStudents(List<SearchStudentDTO> students) {
		this.studentsDTO = students;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public SearchStudentDTO getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(SearchStudentDTO teamLead) {
		this.teamLead = teamLead;
	}

	public FileRespondDTO getMainFile() {
		return mainFile;
	}

	public void setMainFile(FileRespondDTO mainFile) {
		this.mainFile = mainFile;
	}

	public List<FileRespondDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileRespondDTO> files) {
		this.files = files;
	}
	
	
	
}