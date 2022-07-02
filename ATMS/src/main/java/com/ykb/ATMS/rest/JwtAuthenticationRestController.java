package com.ykb.ATMS.rest;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.AuthRequestDTO;
import com.ykb.ATMS.DTO.AuthResponseDTO;
import com.ykb.ATMS.DTO.StudentInfoDTO;
import com.ykb.ATMS.config.CustomUserDetails;
import com.ykb.ATMS.config.JwtTokenUtil;
import com.ykb.ATMS.config.JwtUserDetailsService;
import com.ykb.ATMS.entity.User;

@CrossOrigin
@RestController
public class JwtAuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody AuthRequestDTO authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		CustomUserDetails userDetails=new CustomUserDetails();
		userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponseDTO(token, userDetails.getId(), userDetails.getUsername(), userDetails.getAuthorities().toArray()[0].toString()));
	}
	
	@RequestMapping(value = "/verifyPassword", method = RequestMethod.POST)
	public ResponseEntity<?> verifyPassword(@RequestBody AuthRequestDTO authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		return ResponseEntity.ok(true);
	}
	
	@RequestMapping(value = "/changePassword/{uid}", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody String password, @PathVariable long uid)
			throws Exception {
		System.out.println(password+".");
		userDetailsService.changePassword(uid, password);
		return ResponseEntity.ok(true);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
