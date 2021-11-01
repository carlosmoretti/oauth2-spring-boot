package com.carlosmoretti.oauth.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carlosmoretti.oauth.example.domain.User;
import com.carlosmoretti.oauth.example.domain.UserApplicationDetail;
import com.carlosmoretti.oauth.example.repository.UserRepository;

@Service
public class AuthenticationDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		
		if(user == null)
			throw new UsernameNotFoundException("Usuário não encontrado.");
		
		return new UserApplicationDetail(user);
	}

}
