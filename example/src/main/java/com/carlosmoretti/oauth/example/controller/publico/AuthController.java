package com.carlosmoretti.oauth.example.controller.publico;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlosmoretti.oauth.example.domain.User;

@RestController
@RequestMapping("/publico/auth")
public class AuthController {
	
	@GetMapping()
	public User user() {
		return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
	}
	
}
