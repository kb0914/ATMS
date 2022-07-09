package com.ykb.ATMS.service.Implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ykb.ATMS.DTO.MarkAssignmentInfoDTO;
import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentTasksDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.DTO.TeamListDTO;
import com.ykb.ATMS.DTO.TeamStudentDTO;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.FileDB;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Task;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.entity.TeamStudent;
import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.repository.TeamStudentRepository;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TeamService implements ITeamService{

	private TeamRepository teamRepository;
	private IAssignmentService assignmentService;
	private IStudentService studentService;
	//private ITaskService taskService;
	private ModelMapper modelMapper;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, IAssignmentService assignmentService, IStudentService studentService
			, ModelMapper modelMapper, TeamStudentRepository teamStudentRepository) {
		this.teamRepository=teamRepository;
		this.assignmentService=assignmentService;
		this.studentService=studentService;
		//this.taskService=taskService;
		this.modelMapper=modelMapper;
	}
	
	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	public Team findById(long id) {
		Optional<Team> result = teamRepository.findById(id);
		
		Team team=null;
		
		if(result!=null)
			team=result.get();
		else
			throw new RuntimeException("Assignment ID not found - "+id);
		
		return team;
	}
	
	@Override
	public TeamDTO getTeamDTOById(long id) {
		
		Team i=findById(id);
		if(i.getMainFile()!=null) {
			return new TeamDTO(i.getId(), i.getAssignment(), findAllTeamMemberByTeamID(id),
					FileDBService.convertFileToFileRespond(i.getMainFile()), studentService.convertToDto(i.getTeamLead()));
		}else {
			return new TeamDTO(i.getId(), i.getAssignment(), findAllTeamMemberByTeamID(id),null,
				studentService.convertToDto(i.getTeamLead()));
		}
	}
	
	@Override
	public void save(Team team) {
		teamRepository.save(team);
	}

	@Override
	public void createTeam(Team team, long aid) {
		Assignment assignment=assignmentService.findById(aid);
		Student student=studentService.findById(team.getTeamLead().getId());
		Team tempTeam =new Team();
		tempTeam.setId(0);
		tempTeam.setTeamLead(student);
		tempTeam=teamRepository.save(tempTeam);
		tempTeam.addStudent(student);
		assignment.addTeam(tempTeam);
		assignmentService.update(assignment);
	}
	
	@Override
	public void createMultipleTeam(long aid, int num) {
		Assignment assignment=assignmentService.findById(aid);
		for(int i =0;i<num;i++) {
			Team tempTeam =new Team();
			tempTeam.setId(0);
			assignment.addTeam(tempTeam);
			assignmentService.update(assignment);
		}
	}
	
	@Override
	public void updateMemberMarks(long tid, TeamStudentDTO dto) {
		Team team=findById(tid);
		team.getStudents().forEach(i->{
			if(i.getStudent().getId()==dto.getId()) {
				i.setMark(dto.getMark());
				System.out.println(i.getMark());
			}
		});
		save(team);
	}

	@Override
	public void deleteById(long id) {
		teamRepository.deleteById(id);
	}

	@Override
	public void assignTeamLead(TeamDTO dto) {
		Team team=findById(dto.getId());
		team.setTeamLead(studentService.findById(dto.getTeamLead().getId()));
		teamRepository.save(team);
	}
	
	@Override
	public void addTeamMember(long id, long sid) {
		Team team=findById(id);
		Student student=studentService.findById(sid);
		team.addStudent(student);
		Student lead=team.getTeamLead();
		if(lead==null) {
			team.setTeamLead(student);
		}
		teamRepository.save(team);
	}
	
	@Override
	public void deleteTeamMember(long id, long sid) {
		Team team=findById(id);
		team.removeStudent(studentService.findById(sid));
		teamRepository.save(team);
	}
	
	public List<Team> findTeamsByAssignemntId(@PathVariable long id){
		
		Assignment assignment = assignmentService.findById(id);
		if(assignment==null)
			throw new RuntimeException("Team id not found - " + id);
		
		return assignment.getTeam();
	}
	
	public List<SearchStudentDTO> findTeamedStudentByAssignemntId(long id){
		List<Student> students =new ArrayList<>();
		Assignment assignment = assignmentService.findById(id);
		if(assignment==null)
			throw new RuntimeException("Assignment id not found - " + id);
		
		assignment.getTeam().stream()
				.map(team->team.getStudents())
				.forEach(s->s.forEach(i->students.add(i.getStudent())));
		
		List<SearchStudentDTO> studentDto = new ArrayList<>();
		students.stream().forEach(i->studentDto.add(
				new SearchStudentDTO(i.getId(), i.getUsername(), i.getFirstName(), i.getLastName(), 
						i.getEmail(), i.getIntake())));
		
		return studentDto;
	}
	
	@Override
	public List<SearchStudentDTO> findAllTeamMemberByTeamID(long id){
		
		List<SearchStudentDTO> studentDto = new ArrayList<>();
		findById(id).getStudents().stream().map(i->i.getStudent()).forEach(i->studentDto.add(
				new SearchStudentDTO(i.getId(), i.getUsername(), i.getFirstName(), i.getLastName(), 
						i.getEmail(), i.getIntake())));
		
		return studentDto;
	}
	
	@Override
	public List<TeamStudentDTO> findTeamStudentByTeamID(long id){
		
		List<TeamStudentDTO> studentDto = new ArrayList<>();
		findById(id).getStudents().stream().forEach(i->studentDto.add(
				new TeamStudentDTO(i.getStudent().getId(), i.getStudent().getUsername(),i.getStudent().getFirstName(),
						i.getStudent().getLastName(),i.getMark())));
		
		return studentDto;
	}
	
	@Override
	public TeamListDTO getTeamListItem(long id) {
		TeamListDTO dto=new TeamListDTO();
		List<TeamDTO> teamdto=new ArrayList<>();
		Assignment assignment=assignmentService.findById(id);
		dto.setAssignment(assignment);
		assignment.getTeam().forEach(i->{
			TeamDTO t=new TeamDTO(i.getId(), assignment, null, null);
			t.setStudents(findAllTeamMemberByTeamID(t.getId()));
			Student teamLead=i.getTeamLead();
			if(teamLead != null){
				t.setTeamLead(modelMapper.map(i.getTeamLead(), SearchStudentDTO.class));
			}
			teamdto.add(t);
		});
		dto.setAssignmentTeam(teamdto);
		dto.setTeamedStudent(findTeamedStudentByAssignemntId(id));
		dto.setOptions(studentService.getFirstNameAndIdByIntake(assignment.getIntake().getId()));
		
		return dto;
	}
	
	@Override
	public List<SearchStudentDTO> getUnTeanedStudentByAssignemntId(long aid){
		List<SearchStudentDTO> allStudent=assignmentService.findById(aid).getIntake().getStudents().stream()
				.map(i->studentService.convertToDto(i)).toList();
		
		List<SearchStudentDTO> unteamedStudent=new ArrayList<SearchStudentDTO>();
		boolean flag=false;
		for(SearchStudentDTO i:allStudent) {
			for(SearchStudentDTO a:findTeamedStudentByAssignemntId(aid)) {
				if(i.getId()==a.getId()) {
					flag=true;
				}
			}
			if(!flag) {
				unteamedStudent.add(i);
			}
			flag=false;
		}
		return unteamedStudent;
	}
	
	@Override
	public void updateTeamMark(long id, double mark) {
		Team team=findById(id);
		team.setMark(mark);
		int totalWeigtage = 0;
			for (Task t : team.getTasks()) {
				if (t.getStatus() == AssignmentStatus.COMPLETED) {
					totalWeigtage = totalWeigtage + t.getWeightage();
				}
			}
		
		double avgPercentage=100/team.getStudents().size();
		System.out.println(avgPercentage);
		for (TeamStudent i : team.getStudents()) {
			int weightage=0;
			for (Task t : i.getStudent().getTasks()) {
				if (t.getStatus() == AssignmentStatus.COMPLETED) {
					weightage=weightage+t.getWeightage();
				}
			}
			BigDecimal percentage = new BigDecimal(((double)weightage/(double)totalWeigtage)*100).setScale(2, RoundingMode.HALF_UP);
			System.out.println(percentage);
			if(percentage.doubleValue()>=avgPercentage) {
				System.out.println("goog");
				i.setMark(mark);
			}else {
				System.out.println("asd");
				i.setMark(new BigDecimal(mark*((percentage.doubleValue()/avgPercentage))).setScale(2, RoundingMode.HALF_UP).doubleValue());
			}
		}
	
		
		save(team);
	}
}
