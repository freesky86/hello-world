package com.freesky.dto;

public class UserBean {

	private String operation;
	private String port;
	private String password;
	private String limit;
	
	private String info;
	
	private String errMsg;
	
	public UserBean() {
		clear();
	}
	
	public void clear() {
		operation = "";
		port = "";
		password = "";
		limit = "";
		
		info = "";
		
		errMsg = "";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
