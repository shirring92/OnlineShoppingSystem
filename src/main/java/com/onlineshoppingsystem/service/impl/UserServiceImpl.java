package com.onlineshoppingsystem.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.onlineshoppingsystem.dao.UserDOMapper;
import com.onlineshoppingsystem.dao.UserPasswordDOMapper;
import com.onlineshoppingsystem.dataobject.UserDO;
import com.onlineshoppingsystem.dataobject.UserPasswordDO;
import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.error.EmBusinessError;
import com.onlineshoppingsystem.service.UserService;
import com.onlineshoppingsystem.service.model.UserModel;
import com.onlineshoppingsystem.validator.ValidationResult;
import com.onlineshoppingsystem.validator.ValidatorImpl;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDOMapper userDOMapper;
	
	@Autowired
	private UserPasswordDOMapper userPasswordDOMapper;
	
	@Autowired
	private ValidatorImpl validator;
	
	@Override
	public UserModel getUserById(Integer id) {
		// TODO Auto-generated method stub
		// get user data object by userdomapper
		UserDO userDO = userDOMapper.selectByPrimaryKey(id);
		
		if (userDO == null) {
			return null;
		}
		
		// get userpassworddo by user id
		UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
		
		return convertFromDataObject(userDO, userPasswordDO);
	}
	
	private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
		if (userDO == null) {
			return null;
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userDO, userModel);
		
		if (userPasswordDO != null) {
			userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
		}
		
		return userModel;
	}

	@Override
	@Transactional
	public void register(UserModel userModel) throws BusinessException {
		// TODO Auto-generated method stub
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
//		if (StringUtils.isEmpty(userModel.getName())
//				|| userModel.getGender() == null 
//				|| userModel.getAge() == null 
//				|| StringUtils.isEmpty(userModel.getTelphone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
		ValidationResult result = validator.validate(userModel);
		if (result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
		}
		
		UserDO userDO = convertFromModel(userModel);
		try {
			userDOMapper.insertSelective(userDO);
		} 
		catch (DuplicateKeyException ex){
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "this email address is already registered with us");
		}
		
		
		userModel.setId(userDO.getId());
		
		UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
		userPasswordDOMapper.insertSelective(userPasswordDO);
		
		return;
	}
	
	private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserPasswordDO userPasswordDO = new UserPasswordDO();
		userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
		userPasswordDO.setUserId(userModel.getId());
		return userPasswordDO;
	}
	private UserDO convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userModel, userDO);
		
		return userDO;
	}

	@Override
	public UserModel validateLogin(String email, String encrptPassword) throws BusinessException {
		// TODO Auto-generated method stub
		// get user info by email address
		UserDO userDO = userDOMapper.selectByEmail(email);
		if (userDO == null) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		
		UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
		UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
		
		// compare user password with input password
		if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		return userModel;
	}
}
