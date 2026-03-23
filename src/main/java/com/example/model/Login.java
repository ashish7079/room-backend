package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login")
public class Login {

	 @Column(unique = true)
	private String userName;
	private String password;
	private String role;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Login(String userName, String password,String role, Long id) {
		
		this.userName = userName;
		this.password = password;
		  this.role = role;
	        this.id = id;
	    }

	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
