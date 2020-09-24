package com.onlineshoppingsystem.response;

public class CommonReturnType {
	// response type: success or fail
	private String status;
	
	// if status = success, data is info to UI in json
	// if status = fail, data is http status code
	private Object data;
	
	public static CommonReturnType create(Object result) {
		return CommonReturnType.create(result, "success");
	}
	
	public static CommonReturnType create(Object result, String success) {
		CommonReturnType type = new CommonReturnType();
		type.setStatus(success);
		type.setData(result);
		return type;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
