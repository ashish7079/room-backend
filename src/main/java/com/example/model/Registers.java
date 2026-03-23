package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "registers")
public class Registers {

	private String userName;
	private String email;
	private Integer mobiliNo;
	private String password;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	public Registers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMobiliNo() {
		return mobiliNo;
	}

	public void setMobiliNo(Integer mobiliNo) {
		this.mobiliNo = mobiliNo;
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
