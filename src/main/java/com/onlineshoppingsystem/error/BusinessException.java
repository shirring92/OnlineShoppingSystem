package com.onlineshoppingsystem.error;

public class BusinessException extends Exception implements CommonError {

	private CommonError commonError;
	
	public BusinessException(CommonError commonError) {
		// TODO Auto-generated constructor stub
		super();
		this.commonError = commonError;
	}
	
	public BusinessException(CommonError commonError, String errMsg) {
		// TODO Auto-generated constructor stub
		super();
		this.commonError = commonError;
		this.commonError.setErrMsg(errMsg);
	}

	@Override
	public int getErrCode() {
		// TODO Auto-generated method stub
		return this.commonError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		// TODO Auto-generated method stub
		return this.commonError.getErrMsg();
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		// TODO Auto-generated method stub
		this.commonError.setErrMsg(errMsg);
		return this;
	}
}
