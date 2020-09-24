package com.onlineshoppingsystem.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.onlineshoppingsystem.controller.viewobject.UserVO;
import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.error.EmBusinessError;
import com.onlineshoppingsystem.response.CommonReturnType;
import com.onlineshoppingsystem.service.UserService;
import com.onlineshoppingsystem.service.model.UserModel;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	// user registration interface
	@RequestMapping(value="/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType login(@RequestParam(name="email")String email,
			@RequestParam(name="password")String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		
		// user login service. validate user login.
		UserModel userModel = userService.validateLogin(email, this.EncodeByMd5(password));
		
		// add login success certificate to user login session
		this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
		this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
		
		return CommonReturnType.create(null);
	}
	
	
//	user registration interface
	@RequestMapping(value="/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType register(@RequestParam(name="email")String email, 
			@RequestParam(name="verifCode")String verifCode, 
			@RequestParam(name="username")String username, 
			@RequestParam(name="firstName")String firstname, 
			@RequestParam(name="lastName")String lastname,
			@RequestParam(name="city")String city,
			@RequestParam(name="state")String state,
			@RequestParam(name="zip")String zip,
			@RequestParam(name="password")String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		// verify email address match verification code
		String inSessionVerifCode = (String)this.httpServletRequest.getSession().getAttribute(email);
		
		if (!com.alibaba.druid.util.StringUtils.equals(verifCode, inSessionVerifCode)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "verification code is not valid");
		}
		
		// registration
		UserModel userModel = new UserModel();
		userModel.setUsername(username);
		userModel.setFirstname(firstname);
		userModel.setLastname(lastname);
		userModel.setEmail(email);
		userModel.setCity(city);
		userModel.setState(state);
		userModel.setZip(zip);
		userModel.setEncrptPassword(this.EncodeByMd5(password));
		
		userService.register(userModel);
		return CommonReturnType.create(null);
	}
	
	public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		Base64.Encoder base64en = Base64.getEncoder().withoutPadding();
		
		String newstr = base64en.encodeToString(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}
	
	// user get verification code interface
	@RequestMapping(value="/getVerifCode", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType getOtp(@RequestParam(name="email")String email) {
//		generate verification code
		Random random = new Random();
		int randomInt = random.nextInt(99999);
		randomInt += 10000;
		String verifCode = String.valueOf(randomInt);
		
//		attach verification code to user's email address
//		usually use Radis, key-value
//		here use httpsession to connect verification code with email address
		httpServletRequest.getSession().setAttribute(email, verifCode);
		
//		send verification code to user
		System.out.println("email = " + email + " & verifCode = " + verifCode);
		
		return CommonReturnType.create(null);
	}
	
	
	
	@RequestMapping("/get")
	@ResponseBody
	public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
		// get user model by id through user service
		UserModel userModel = userService.getUserById(id);
		
		if (userModel == null) {
//			userModel.setEncrptPassword("123");
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		}
		
		// convert user model to view object which can be provided to UI
		UserVO userVO = convertFromModel(userModel);
		return CommonReturnType.create(userVO);
	}
	
	private UserVO convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(userModel, userVO);
		return userVO;
	}
}
