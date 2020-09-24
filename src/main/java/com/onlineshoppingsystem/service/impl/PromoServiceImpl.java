package com.onlineshoppingsystem.service.impl;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineshoppingsystem.dao.PromoDOMapper;
import com.onlineshoppingsystem.dataobject.PromoDO;
import com.onlineshoppingsystem.service.PromoService;
import com.onlineshoppingsystem.service.model.PromoModel;

@Service
public class PromoServiceImpl implements PromoService {
	
	@Autowired
	private PromoDOMapper promoDOMapper;

	@Override
	public PromoModel getPromoByProductId(Integer productId) {
		// TODO Auto-generated method stub
		// get product promotion info
		PromoDO promoDO = promoDOMapper.selectByProductId(productId);
		
		// data object -> model
		PromoModel promoModel = convertFromDataObject(promoDO);
		if (promoModel == null) {
			return null;
		}
		
		// check if promotion is on going or is about to start now
		if (promoModel.getStartDate().isAfterNow()) {
			promoModel.setStatus(1);
		}
		else if (promoModel.getEndDate().isBeforeNow()) {
			promoModel.setStatus(3);
		}
		else {
			promoModel.setStatus(2);
		}
		
		return promoModel;
	}
	
	private PromoModel convertFromDataObject(PromoDO promoDO) {
		if (promoDO == null) {
			return null;
		}
		
		PromoModel promoModel = new PromoModel();
		BeanUtils.copyProperties(promoDO, promoModel);
		promoModel.setPromoProductPrice(new BigDecimal(promoDO.getPromoProductPrice()));
		promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
		promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
		
		return promoModel;
	}
}
