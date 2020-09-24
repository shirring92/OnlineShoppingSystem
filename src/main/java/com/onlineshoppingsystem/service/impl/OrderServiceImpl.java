package com.onlineshoppingsystem.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshoppingsystem.dao.OrderDOMapper;
import com.onlineshoppingsystem.dao.SequenceDOMapper;
import com.onlineshoppingsystem.dataobject.OrderDO;
import com.onlineshoppingsystem.dataobject.SequenceDO;
import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.error.EmBusinessError;
import com.onlineshoppingsystem.service.OrderService;
import com.onlineshoppingsystem.service.ProductService;
import com.onlineshoppingsystem.service.UserService;
import com.onlineshoppingsystem.service.model.OrderModel;
import com.onlineshoppingsystem.service.model.ProductModel;
import com.onlineshoppingsystem.service.model.UserModel;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private SequenceDOMapper sequenceDOMapper;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderDOMapper orderDOMapper;
	
	@Override
	@Transactional
	public OrderModel createOrder(Integer userId, Integer productId, Integer promoId, Integer amount) throws BusinessException {
		// TODO Auto-generated method stub
		
		// verify order status:
		// product exist
		// user is valid
		// amount is correct
		ProductModel productModel = productService.getProductById(productId);
		if (productModel == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "product does not exist");
		}
		
		UserModel userModel = userService.getUserById(userId);
		if (userModel == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "user does not exit");
		}
		
		if (amount <= 0 || amount > 99) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "amount is not valid");
		}
		
		if (promoId != null) {
			if (promoId.intValue() != productModel.getPromoModel().getId()) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "promotion is not valid for this product");
			}
			else if (productModel.getPromoModel().getStatus().intValue() != 2) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "promotion is not start");
			}
		}
		
		// decrease amount from stock when order
		boolean result = productService.decreaseStock(productId, amount);
		if (!result) {
			throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
		}
		
		// generate order model
		OrderModel orderModel = new OrderModel();
		orderModel.setUserId(userId);
		orderModel.setProductId(productId);
		orderModel.setAmount(amount);
		if (promoId != null) {
			orderModel.setProductPrice(productModel.getPromoModel().getPromoProductPrice());
			orderModel.setOrderPrice(productModel.getPromoModel().getPromoProductPrice().multiply(new BigDecimal(amount)));
		}
		else {
			orderModel.setProductPrice(productModel.getPrice());
			orderModel.setOrderPrice(productModel.getPrice().multiply(new BigDecimal(amount)));
		}
		orderModel.setPromoId(promoId);
		
		
		// generate order id
		orderModel.setId(generateOrderNo());
		OrderDO orderDO = convertFromOrderModel(orderModel);
		orderDOMapper.insertSelective(orderDO);
		
		// increase product sales amount
		productService.increaseSales(productId, amount);
		
		// return to frontend
		return orderModel;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private String generateOrderNo() {
		// 16 digit
		StringBuilder stringBuilder = new StringBuilder();
		// 8 - 15 digit for timestamp, yyyymmdd
		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
		stringBuilder.append(nowDate);
		
		// 2 - 7 digit is auto increment id
		int sequence = 0;
		SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
		
		sequence = sequenceDO.getCurrentValue();
		sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
		
		sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
		String sequenceStr = String.valueOf(sequence);
		for (int i = 0; i < 6 - sequenceStr.length(); i++) {
			stringBuilder.append(0);
		}
		stringBuilder.append(sequenceStr);
		
		// 0 - 1 is sharding key, 00 for now
		stringBuilder.append("00");
		
		return stringBuilder.toString();
	}
	
	private OrderDO convertFromOrderModel(OrderModel orderModel) {
		if (orderModel == null) {
			return null;
		}
		OrderDO orderDO = new OrderDO();
		BeanUtils.copyProperties(orderModel, orderDO);
		orderDO.setProductPrice(orderModel.getProductPrice().doubleValue());
		orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
		return orderDO;
	}
}
