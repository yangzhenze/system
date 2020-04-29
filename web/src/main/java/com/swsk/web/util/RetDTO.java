package com.swsk.web.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RetDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer code = -1;
	private String msg = "error";
	private Map<Object,Object> data = new HashMap<Object,Object>();

	public RetDTO() {
	}

	public RetDTO(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
		if(1 == code && msg.equalsIgnoreCase("error")) {
			this.msg = "sucess";
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public void put(String key, Object value) {
		getData().put(key, value);
	}

	public Object get(String key) {
		return getData().get(key);
	}
}
