package com.onlineshoppingsystem.controller.viewobject;

import java.math.BigDecimal;

public class ProductVO {
private Integer id;
	
	private String title;
	
	private BigDecimal price;
	
	private Integer stock;
	
	private String description;
	
	private Integer sales;
	
	private String imgUrl;
	
	// promotion status:
	// 0: no promotion
	// 1: promotion is not start
	// 2: promotion is on going
	private Integer promoStatus;
	
	private BigDecimal promoPrice;
	
	private Integer promoId;
	
	// for count down
	private String startDate;
	
	public Integer getPromoStatus() {
		return promoStatus;
	}

	public void setPromoStatus(Integer promoStatus) {
		this.promoStatus = promoStatus;
	}

	public BigDecimal getPromoPrice() {
		return promoPrice;
	}

	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}

	public Integer getPromoId() {
		return promoId;
	}

	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
