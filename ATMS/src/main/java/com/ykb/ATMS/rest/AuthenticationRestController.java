package com.ykb.ATMS.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ykb.ATMS.DTO.AuthenticationDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthenticationRestController {

	@GetMapping(path = "/basicauth")
    public AuthenticationDTO basicauth() {
        return new AuthenticationDTO("You are authenticated");
    }
}
