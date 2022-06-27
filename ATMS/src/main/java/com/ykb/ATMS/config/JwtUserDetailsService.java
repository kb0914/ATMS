package com.ykb.ATMS.config;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.entity.Lecturer;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.service.Interface.ILecturerService;
import com.ykb.ATMS.service.Interface.IStudentService;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private ILecturerService lecturerService;
	
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadStudentUser(username);
	}
	
	private CustomUserDetails  loadStudentUser(String username) throws UsernameNotFoundException {

		Student userAccount=studentService.findByUsername(username);
		Collection<GrantedAuthority> gas = new HashSet<>();
        gas.add(new SimpleGrantedAuthority("STUDENT"));
        
        return new CustomUserDetails(userAccount.getId(),userAccount.getUsername(), userAccount.getPassword(), gas);
    }
	
	public CustomUserDetails  loadLecturerUser(String username) throws UsernameNotFoundException {

		Lecturer userAccount=lecturerService.findByUsername(username);
		Collection<GrantedAuthority> gas = new HashSet<>();
        gas.add(new SimpleGrantedAuthority("Lecturer"));
        
        return new CustomUserDetails(userAccount.getId(),userAccount.getUsername(), userAccount.getPassword(), gas);
    }

}
