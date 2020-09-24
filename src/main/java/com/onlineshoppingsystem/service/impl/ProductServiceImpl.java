package com.onlineshoppingsystem.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshoppingsystem.dao.ProductDOMapper;
import com.onlineshoppingsystem.dao.ProductStockDOMapper;
import com.onlineshoppingsystem.dataobject.ProductDO;
import com.onlineshoppingsystem.dataobject.ProductStockDO;
import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.error.EmBusinessError;
import com.onlineshoppingsystem.service.ProductService;
import com.onlineshoppingsystem.service.PromoService;
import com.onlineshoppingsystem.service.model.ProductModel;
import com.onlineshoppingsystem.service.model.PromoModel;
import com.onlineshoppingsystem.validator.ValidationResult;
import com.onlineshoppingsystem.validator.ValidatorImpl;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ValidatorImpl validator;
	
	@Autowired
	private ProductDOMapper productDOMapper;
	
	@Autowired
	private PromoService promoService;
	
	@Autowired
	private ProductStockDOMapper productStockDOMapper;
	
	private ProductDO convertProductDOFromProductModel(ProductModel productModel) {
		if (productModel == null) {
			return null;
		}
		ProductDO productDO = new ProductDO();
		BeanUtils.copyProperties(productModel, productDO);
		productDO.setPrice(productModel.getPrice().doubleValue());
		
		return productDO; 
	}
	
	private ProductStockDO convertProductStockDOFromProductModel(ProductModel productModel) {
		if (productModel == null) {
			return null;
		}
		ProductStockDO productStockDO = new ProductStockDO();
		productStockDO.setProductId(productModel.getId());
		productStockDO.setStock(productModel.getStock());
		
		return productStockDO;
	}
	
	@Override
	@Transactional
	public ProductModel createProduct(ProductModel productModel) throws BusinessException {
		// TODO Auto-generated method stub
		// validate
		ValidationResult result = validator.validate(productModel);
		if (result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
		}
		
		// convert product model to data object
		ProductDO productDO = this.convertProductDOFromProductModel(productModel);
		
		// write into database
		productDOMapper.insertSelective(productDO);
		productModel.setId(productDO.getId());
		
		ProductStockDO productStockDO = this.convertProductStockDOFromProductModel(productModel);
		
		productStockDOMapper.insertSelective(productStockDO);
		//return new product model
		return this.getProductById(productModel.getId());
	}

	@Override
	public List<ProductModel> listProduct() {
		// TODO Auto-generated method stub
		List<ProductDO> productDOList = productDOMapper.listProduct();
		List<ProductModel> productModelList = productDOList.stream().map(productDO -> {
			ProductStockDO productStockDO = productStockDOMapper.selectByProductId(productDO.getId());
			ProductModel productModel = this.convertModelFromDataobject(productDO, productStockDO);
			return productModel;
		}).collect(Collectors.toList());
		return productModelList;
	}

	@Override
	public ProductModel getProductById(Integer id) {
		// TODO Auto-generated method stub
		ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
		if (productDO == null) {
			return null;
		}
		
		// get product stock
		ProductStockDO productStockDO = productStockDOMapper.selectByProductId(productDO.getId());
		
		//convert data object to product model
		ProductModel productModel = convertModelFromDataobject(productDO, productStockDO);
		
		// get promotion info
		PromoModel promoModel = promoService.getPromoByProductId(productModel.getId());
		if (promoModel != null && promoModel.getStatus().intValue() != 3) {
			productModel.setPromoModel(promoModel);
		}
		
		return productModel;
	}
	
	private ProductModel convertModelFromDataobject(ProductDO productDO, ProductStockDO productStockDO) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDO, productModel);
		productModel.setPrice(new BigDecimal(productDO.getPrice()));
		productModel.setStock(productStockDO.getStock());
		
		return productModel;
	}

	@Override
	@Transactional
	public boolean decreaseStock(Integer productId, Integer amount) throws BusinessException {
		// TODO Auto-generated method stub
		int affectedRow = productStockDOMapper.decreaseStock(productId, amount);
		if (affectedRow > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	@Transactional
	public void increaseSales(Integer productId, Integer amount) throws BusinessException {
		// TODO Auto-generated method stub
		productDOMapper.increaseSales(productId, amount);
	}
}
