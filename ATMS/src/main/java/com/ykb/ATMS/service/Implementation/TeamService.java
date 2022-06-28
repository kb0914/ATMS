package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.DTO.TeamListDTO;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.repository.AssignmentRepository;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.service.Interface.ITeamService;

@Service
public class TeamService implements ITeamService{

	private TeamRepository teamRepository;
	private AssignmentService assignmentService;
	private StudentService studentService;
	private ModelMapper modelMapper;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, AssignmentService assignmentService, StudentService studentService, ModelMapper modelMapper) {
		this.teamRepository=teamRepository;
		this.assignmentService=assignmentService;
		this.studentService=studentService;
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
	public TeamDTO getDTOById(long id) {
		
		Team i=findById(id);
		
		return new TeamDTO(i.getId(), i.getAssignment(), findAllTeamMemberByTeamID(id), null);
	}

	@Override
	public void createTeam(Team team, long aid) {
		Assignment assignment=assignmentService.findById(aid);
		Student student=studentService.findById(team.getTeamLead().getId());
		System.out.println(student);
		Team tempTeam =new Team();
		tempTeam.setId(0);
		tempTeam.setTeamLead(student);
		tempTeam=teamRepository.save(tempTeam);
		System.out.println(tempTeam.getId());
		tempTeam.addStudent(student);
		assignment.addTeam(tempTeam);
		assignmentService.update(assignment);
	}
	
	@Override
	public void updateTeam(TeamDTO dto, long aid) {
		Assignment assignment=assignmentService.findById(aid);
		Team team=findById(dto.getId());		
		team.setAssignment(assignment);
		team.setTeamLead(studentService.findById(dto.getTeamLead().getId()));
		if(team.getStudents()!=null) {
			List<Student> studentToBeRemove=team.getStudents().stream().map(i->i.getStudent()).toList();
			for(Student s:studentToBeRemove) {
				team.removeStudent(s);
			}
			team=teamRepository.save(team);
		}
		for(SearchStudentDTO s:dto.getStudents()) {
			team.addStudent(studentService.findById(s.getId()));
		}
//		dto.getStudents().forEach(
//				i->team.addStudent(studentService.findById(i.getId())));
		assignment.addTeam(team);
		assignmentService.update(assignment);
		//teamRepository.save(team);
	}

	@Override
	public void deleteById(long id) {
		teamRepository.deleteById(id);
	}

	@Override
	public void addTeamMember(Team team, Student student) {
		team.addStudent(studentService.findById(student.getId()));
		teamRepository.save(team);
	}
	
	@Override
	public void deleteTeamMember(Team team, Student student) {
		List<Student> students=team.getStudents().stream().map(i->i.getStudent()).toList();
		students.removeIf(i->student.getId()==i.getId());
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
	public TeamListDTO getTeamListItem(long id) {
		TeamListDTO dto=new TeamListDTO();
		List<TeamDTO> teamdto=new ArrayList<>();
		Assignment assignment=assignmentService.findById(id);
		dto.setAssignment(assignment);
		assignment.getTeam().forEach(i->{
			TeamDTO t=new TeamDTO(i.getId(), assignment, null, null);
			t.setStudents(findAllTeamMemberByTeamID(t.getId()));
			t.setTeamLead(modelMapper.map(i.getTeamLead(), SearchStudentDTO.class));
			teamdto.add(t);
		});
		dto.setAssignmentTeam(teamdto);
		dto.setTeamedStudent(findTeamedStudentByAssignemntId(id));
		dto.setOptions(studentService.getFirstNameAndIdByIntake(assignment.getIntake().getId()));
		
		return dto;
	}

}
