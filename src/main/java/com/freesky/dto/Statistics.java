/**
 * 
 */
package com.freesky.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Freesky
 *
 */
public class Statistics {
	
	private String phone = null;
	
	private String code = null;
	
	private String addr = null;
	
	private String errMsg = null;
	
	private String content = null;
	
	private String title = null;
	
	private List<Stats> list = new ArrayList<Stats>();
	
	
	public void clear() {
		phone = null;
		code = null;
		addr = null;
		errMsg = null;
		content = null;
		title = null;
		
		this.list.clear();
	}

	/**
	 * @return the list
	 */
	public List<Stats> getList() {
		return list;
	}


	/**
	 * @param list the list to set
	 */
	public void setList(List<Stats> list) {
		this.list = list;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}


	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}



	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
}
