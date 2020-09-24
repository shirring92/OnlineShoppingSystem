package com.onlineshoppingsystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.error.EmBusinessError;
import com.onlineshoppingsystem.response.CommonReturnType;
import com.onlineshoppingsystem.service.OrderService;
import com.onlineshoppingsystem.service.model.OrderModel;
import com.onlineshoppingsystem.service.model.UserModel;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController extends BaseController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@RequestMapping(value="/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType createOrder(@RequestParam(name="productId")Integer productId,
			@RequestParam(name="amount")Integer amount,
			@RequestParam(name="promoId", required=false)Integer promoId) throws BusinessException {
		
		Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
		if (isLogin == null || !isLogin.booleanValue()) {
			throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "user is not logged in");
		}
		
		// get user id
		UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
		
		OrderModel orderModel = orderService.createOrder(userModel.getId(), productId, promoId, amount);
		
		return CommonReturnType.create(orderModel);
	}
}
