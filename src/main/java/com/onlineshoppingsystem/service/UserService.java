package com.onlineshoppingsystem.service;

import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.service.model.UserModel;

public interface UserService {
	
	UserModel getUserById(Integer id);
	void register(UserModel userModel) throws BusinessException;
	
	/*
	 * email: user registration email address
	 * password: encrypted password
	 */
	UserModel validateLogin(String email, String encrptPassword) throws BusinessException;
}
