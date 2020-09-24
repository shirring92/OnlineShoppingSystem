package com.onlineshoppingsystem.validator;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
	
	// if true, validation result has errors
	private boolean hasErrors = false;

	// store error message
	private Map<String, String> errorMsgMap = new HashMap<>();
	
	
	
	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Map<String, String> getErrorMsgMap() {
		return errorMsgMap;
	}

	public void setErrorMsgMap(Map<String, String> errorMsgMap) {
		this.errorMsgMap = errorMsgMap;
	}

	// generalized get error message function by formatted string
	public String getErrMsg() {
		return org.apache.commons.lang3.StringUtils.join(errorMsgMap.values().toArray(), ",");
	}
}
