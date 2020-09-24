package com.onlineshoppingsystem.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements InitializingBean {
	
	private Validator validator;
	
	//
	public ValidationResult validate(Object bean) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
		if (constraintViolationSet.size() > 0) {
			result.setHasErrors(true);
			constraintViolationSet.forEach(constraintViolation -> {
				String errMsg = constraintViolation.getMessage();
				String propertyName = constraintViolation.getPropertyPath().toString();
				result.getErrorMsgMap().put(propertyName, errMsg);
			});
		}
		return result;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// build validator instance by calling hibernate validator factory build function
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
}
