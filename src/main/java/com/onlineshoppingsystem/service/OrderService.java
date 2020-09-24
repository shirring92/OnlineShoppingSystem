package com.onlineshoppingsystem.service;

import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.service.model.OrderModel;

public interface OrderService {

	OrderModel createOrder(Integer userId, Integer productId, Integer promoId, Integer amount) throws BusinessException;
}
