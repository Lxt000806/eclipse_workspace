package com.house.home.client.service.evt;

public class CustomerEvt extends BaseGetEvt{
	
	private String code;
	private String status;
	private String address;
	private String wxcode;
	private int custWkPk;

	
	
	public String getWxcode() {
		return wxcode;
	}

	public void setWxcode(String wxcode) {
		this.wxcode = wxcode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(int custWkPk) {
		this.custWkPk = custWkPk;
	}

	
}
