package com.freesky.dto;

public class AccountBean {
	private String port, phone, code, password, addr, errMsg, protocol, obfs, method;
	
	public AccountBean() {
		clear();
	}
	
	public void clear() {
		port = "";
		phone = "";
		code = "";
		password = "";
		addr = "";
		errMsg = "";
		
		protocol = "";
		obfs = "";
		method = "";
	}

	
	public String getErrMsg() {
		return errMsg;
	}



	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}



	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getObfs() {
		return obfs;
	}

	public void setObfs(String obfs) {
		this.obfs = obfs;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
