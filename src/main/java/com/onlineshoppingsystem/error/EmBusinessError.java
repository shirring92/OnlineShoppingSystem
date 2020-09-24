package com.onlineshoppingsystem.error;

public enum EmBusinessError implements CommonError {
	//common error 10000
	PARAMETER_VALIDATION_ERROR(10001, "parameter is not allowed"),
	UNKNOWN_ERROR(10002, "unknown error"),
	
	// 20000 code is user defined error message
	USER_NOT_EXIST(20001, "user not exist"),
	USER_LOGIN_FAIL(20002, "user email or password is not correct"),
	USER_NOT_LOGIN(20003, "user is not logged in"),
	
	// 30000 code is order info error
	STOCK_NOT_ENOUGH(30001, "stock is not enough")
	;

	private EmBusinessError(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	private int errCode;
	private String errMsg;
	
	@Override
	public int getErrCode() {
		// TODO Auto-generated method stub
		return this.errCode;
	}

	@Override
	public String getErrMsg() {
		// TODO Auto-generated method stub
		return this.errMsg;
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		// TODO Auto-generated method stub
		this.errMsg = errMsg;
		return this;
	}
}
