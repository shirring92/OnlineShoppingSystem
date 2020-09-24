package com.onlineshoppingsystem.service.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductModel {
	
	private Integer id;
	
	@NotBlank(message = "title cannot be empty")
	private String title;
	
	@NotBlank(message = "description cannot be empty")
	private String description;
	
	@NotNull(message = "price cannot be empty")
	@Min(value = 0, message = "price must be larger than 0")
	private BigDecimal price;
	
	private Integer sales;
	
	@NotBlank(message = "image cannot be empty")
	private String imgUrl;
	
	@NotNull(message = "stock cannot be empty")
	private Integer stock;
	
	// aggregation model
	// if promoModel is not null, means this product has a promotion on going or not start
	private PromoModel promoModel;
	
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
	public PromoModel getPromoModel() {
		return promoModel;
	}
	public void setPromoModel(PromoModel promoModel) {
		this.promoModel = promoModel;
	}
}
