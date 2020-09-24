package com.onlineshoppingsystem.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineshoppingsystem.controller.viewobject.ProductVO;
import com.onlineshoppingsystem.error.BusinessException;
import com.onlineshoppingsystem.response.CommonReturnType;
import com.onlineshoppingsystem.service.ProductService;
import com.onlineshoppingsystem.service.model.ProductModel;

@Controller("/product")
@RequestMapping("/product")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService productService;
	
	// create product controller
	@RequestMapping(value="/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType createProduct(@RequestParam(name = "title")String title,
			@RequestParam(name = "description")String description,
			@RequestParam(name = "price")BigDecimal price,
			@RequestParam(name = "stock")Integer stock,
			@RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
		
		ProductModel productModel = new ProductModel();
		productModel.setTitle(title);
		productModel.setDescription(description);
		productModel.setPrice(price);
		productModel.setStock(stock);
		productModel.setImgUrl(imgUrl);
		
		ProductModel productModelForReturn = productService.createProduct(productModel);
		ProductVO productVO = convertVOFromModel(productModelForReturn);
		
		return CommonReturnType.create(productVO);
	}
	
	// view product details
	@RequestMapping(value="/get", method = {RequestMethod.GET})
	@ResponseBody
	public CommonReturnType getProduct(@RequestParam(name = "id")Integer id) {
		ProductModel productModel = productService.getProductById(id);
		
		ProductVO productVO = convertVOFromModel(productModel);
		
		return CommonReturnType.create(productVO);
	}
	
	@RequestMapping(value="/list", method = {RequestMethod.GET})
	@ResponseBody
	public CommonReturnType listProduct() {
		List<ProductModel> productModelList = productService.listProduct();
		
		// convert product model list to product vo list through stream api
		List<ProductVO> productVOList = productModelList.stream().map(productModel -> {
			ProductVO productVO = this.convertVOFromModel(productModel);
			return productVO;
		}).collect(Collectors.toList());
		
		return CommonReturnType.create(productVOList);
	}
	
	private ProductVO convertVOFromModel(ProductModel productModel) {
		if (productModel == null) {
			return null;
		}
		
		ProductVO productVO = new ProductVO();
		BeanUtils.copyProperties(productModel, productVO);
		
		if (productModel.getPromoModel() != null) {
			productVO.setPromoStatus(productModel.getPromoModel().getStatus());
			productVO.setPromoId(productModel.getPromoModel().getId());
			productVO.setStartDate(productModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
			productVO.setPromoPrice(productModel.getPromoModel().getPromoProductPrice());
		}
		else {
			productVO.setPromoStatus(0);
		}
		
		return productVO;
	}
}
