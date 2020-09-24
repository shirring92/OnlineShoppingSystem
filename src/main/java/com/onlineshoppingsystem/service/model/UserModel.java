package com.onlineshoppingsystem.service.model;

import javax.validation.constraints.NotBlank;

public class UserModel {
	
	private Integer id;
	
	@NotBlank(message = "username cannot be empty")
	private String username;
	
	@NotBlank(message = "first name cannot be empty ")
	private String firstname;
	
	@NotBlank(message = "last name cannot be empty")
	private String lastname;
	
	@NotBlank(message = "email address cannot be empty")
	private String email;
	
	@NotBlank(message = "city address cannot be empty")
	private String city;
	
	@NotBlank(message = "state address cannot be empty")
	private String state;
	
	@NotBlank(message = "zip address cannot be empty")
	private String zip;
	
	@NotBlank(message = "password cannot be empty")
	private String encrptPassword;
	
	public String getEncrptPassword() {
		return encrptPassword;
	}
	public void setEncrptPassword(String encrptPassword) {
		this.encrptPassword = encrptPassword;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
