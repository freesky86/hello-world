package com.freesky.dto;

import java.util.HashMap;
import java.util.Map;

import com.freesky.utils.JsonParser;

public class V2RayAccountBean {
	private String port, password, id, addr, errMsg;
	
	public V2RayAccountBean() {
		clear();
	}
	
	public void clear() {
		port = "";
		password = "";
		id = "";
		addr = "";
		errMsg = "";
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
