package com.onlineshoppingsystem.service;

import com.onlineshoppingsystem.service.model.PromoModel;

public interface PromoService {
	
	// get promotion which is on going or is going to happen
	PromoModel getPromoByProductId(Integer ProductId);
}
