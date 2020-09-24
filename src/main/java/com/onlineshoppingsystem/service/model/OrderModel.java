package com.onlineshoppingsystem.service.model;

import java.math.BigDecimal;

public class OrderModel {
		
	// id format:
	// timestamp + order number
	// 2018102100012828
	private String id;
	
	private Integer userId;
	
	private Integer productId;
	
	// price when product is ordered
	// if promoId is not null, productPrice is promo price
	private BigDecimal productPrice;
	
	private Integer amount;
	
	private BigDecimal orderPrice;
	
	// if promo id is not null, order by promo price
	private Integer promoId;

	public String getId() {
		return id;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPromoId() {
		return promoId;
	}

	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
}
