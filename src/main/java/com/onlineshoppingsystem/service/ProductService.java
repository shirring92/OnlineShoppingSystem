package com.onlineshoppingsystem.service;

import java.util.List;

import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.service.model.ProductModel;

public interface ProductService {
	
	// create a new product
	ProductModel createProduct(ProductModel productModel) throws BusinessException;
		
	// view product list
	List<ProductModel> listProduct();
		
	// view product details
	ProductModel getProductById(Integer id);
		
	// decrease stock
	boolean decreaseStock(Integer productId, Integer amount) throws BusinessException;
		
	// increase product sales
	void increaseSales(Integer productId, Integer amount) throws BusinessException;
}
