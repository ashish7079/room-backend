package com.example.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repos.AuthRepos;
import com.example.model.Login;
import com.example.model.Registers;
import com.example.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	JwtUtil utils;	
	@Autowired
	AuthRepos repo;

	@Autowired
	PasswordEncoder encoder;
	
	   @PostMapping("/loginsk")
	    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {

	        String userName = loginData.get("userName");
	        String password = loginData.get("password");

	        Login user = repo.findByUserName(userName).orElse(null);

	        if (user == null) {
	            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
	        }

	        if (!encoder.matches(password, user.getPassword())) {
	            return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
	        }

	        String token = utils.generateToken(userName, user.getRole());

	        return ResponseEntity.ok(Map.of(
	        		"token", token,
	        		"role",user.getRole()
	        		));
	    }
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Login user){

	    if(repo.existsByUserName(user.getUserName())){
	        return ResponseEntity.badRequest().body("User already exists");
	    }

	    user.setPassword(encoder.encode(user.getPassword()));

	    if(user.getRole() == null){
	        user.setRole("USER");   // default role
	    }

	    repo.save(user);

	    return ResponseEntity.ok("Registration success");
	}
	@GetMapping("/check")
	public String checks() {
		return "jwt verified successfully";
	}
	

}
