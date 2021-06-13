package com.house.home.client.service.resp;

import java.util.Date;

public class ConfirmCustomFiledResp extends BaseResponse {
	
	private String code;
	private String descr;
	private String type;
	private String options;
	private String value;
	private Integer confirmCustomFiledValuePk;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getConfirmCustomFiledValuePk() {
		return confirmCustomFiledValuePk;
	}
	public void setConfirmCustomFiledValuePk(Integer confirmCustomFiledValuePk) {
		this.confirmCustomFiledValuePk = confirmCustomFiledValuePk;
	}
	
	
}
