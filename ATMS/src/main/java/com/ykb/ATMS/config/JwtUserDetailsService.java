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
import com.ykb.ATMS.entity.User;
import com.ykb.ATMS.repository.UserRepository;
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
		return loadUser(username);
	}
	
	private CustomUserDetails  loadUser(String username) throws UsernameNotFoundException {

		User userAccount=studentService.findByUsername(username);
		if(userAccount!=null) {
			Collection<GrantedAuthority> gas = new HashSet<>();
	        gas.add(new SimpleGrantedAuthority("STUDENT"));
	        
	        return new CustomUserDetails(userAccount.getId(),userAccount.getUsername(), userAccount.getPassword(), gas);
		}else {
			userAccount=lecturerService.findByUsername(username);
			Collection<GrantedAuthority> gas = new HashSet<>();
			gas.add(new SimpleGrantedAuthority("LECTURER"));
	        
	        return new CustomUserDetails(userAccount.getId(),userAccount.getUsername(), userAccount.getPassword(), gas);	
		}
		
    }

}
