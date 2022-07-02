package com.ykb.ATMS.service.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ykb.ATMS.DTO.SearchStudentDTO;
import com.ykb.ATMS.DTO.StudentTasksDTO;
import com.ykb.ATMS.DTO.TeamDTO;
import com.ykb.ATMS.DTO.TeamListDTO;
import com.ykb.ATMS.DTO.TeamStudentDTO;
import com.ykb.ATMS.entity.Assignment;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.entity.TeamStudent;
import com.ykb.ATMS.enums.AssignmentStatus;
import com.ykb.ATMS.repository.AssignmentRepository;
import com.ykb.ATMS.repository.TaskRepository;
import com.ykb.ATMS.repository.TeamRepository;
import com.ykb.ATMS.repository.TeamStudentRepository;
import com.ykb.ATMS.service.Interface.IAssignmentService;
import com.ykb.ATMS.service.Interface.IStudentService;
import com.ykb.ATMS.service.Interface.ITaskService;
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
		Team team=findById(dto.getId());	
//		team.setTeamLead(studentService.findById(dto.getTeamLead().getId()));
//		
//		List<Student> newList=dto.getStudents().stream().map(i->studentService.findById(i.getId())).toList();
//		List<Student> defaultList=team.getStudents().stream().map(i->i.getStudent()).toList();
//		
//		boolean newStudent=false;
//		
//		for(Student n:newList) {
//			for(Student d:defaultList) {
//				if(n.getId()!=d.getId()) {
//					newStudent=true;
//					System.out.println(n.toString());
//				}
//			}
//			if(newStudent)
//				addTeamMember(team,n);
//			newStudent=false;
//		}
//		teamRepository.saveAndFlush(team);
//		boolean deleteStudent=false;
//		
//		for(Student n:defaultList) {
//			for(Student d:newList) {
//				if(n.getId()!=d.getId()) {
//					deleteStudent=true;
//					System.out.println(n.toString());
//				}
//			}
//			if(deleteStudent)
//				deleteTeamMember(team, n);
//			deleteStudent=false;
//		}
//		
//		teamRepository.saveAndFlush(team);
		//deleteTeamMember(team, studentService.convertToDto(studentService.findById(19)));
	}
	
	@Override
	public void updateMemberMarks(long tid, TeamStudentDTO dto) {
		Team team=findById(tid);
		//team.getStudents().stream().filter(i->i.getStudent().getId()==dto.getId()).findFirst().orElse(null).setMark(dto.getMark());
		//System.out.println(team.getStudents().stream().filter(i->i.getStudent().getId()==dto.getId()).findFirst().orElse(null).getStudent().toString());
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
		team.addStudent(studentService.findById(sid));
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
			t.setTeamLead(modelMapper.map(i.getTeamLead(), SearchStudentDTO.class));
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
}
