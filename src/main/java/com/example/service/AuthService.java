package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.example.Repos.AuthRepos;
import com.example.model.Login;

@Service
public class AuthService implements UserDetailsService{
	
	@Autowired
	private final AuthRepos repo;
	
	public AuthService(AuthRepos repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

	    Login user = repo.findByUserName(userName)
	            .orElseThrow(() -> new UsernameNotFoundException("user not found"));

	    return User.builder()
	    	    .username(user.getUserName())
	    	    .password(user.getPassword())
	    	    .authorities("ROLE_" + user.getRole()) // 🔥 correct
	    	    .build();
	}
 
}
