package com.ykb.ATMS.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.entity.Student;
import com.ykb.ATMS.service.Implementation.StudentService;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loadUser(username);
	}
	
	private CustomUserDetails  loadUser(String username) throws UsernameNotFoundException {

		Student userAccount=studentService.findByUsername(username);
		Collection<GrantedAuthority> gas = new HashSet<>();
        gas.add(new SimpleGrantedAuthority("STUDENT"));
        
        return new CustomUserDetails(userAccount.getId(),userAccount.getUsername(), userAccount.getPassword(), gas);


    }

}
