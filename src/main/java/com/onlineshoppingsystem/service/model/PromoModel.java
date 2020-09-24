package com.onlineshoppingsystem.service.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class PromoModel {
	
	private Integer id;
	
	// 1 is not start, 2 is on going, 3 is already end
	private Integer status;
	
	private String promoName;
	
	private DateTime startDate;
	
	private DateTime endDate;
	
	private Integer productId;
	
	private BigDecimal promoProductPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getPromoProductPrice() {
		return promoProductPrice;
	}

	public void setPromoProductPrice(BigDecimal promoProductPrice) {
		this.promoProductPrice = promoProductPrice;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
