package com.house.home.client.service.resp;

public class GetCustomerResp {
	private String address;
	private String code;
	private String custType;
	//APP基装增减用 add by zb on 20190301
	private String custTypeType; //客户类型类型
	private String canUseComBaseItem; //是否可报公用基础项目
	private String isInitSign;//草签标记
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustTypeType() {
		return custTypeType;
	}
	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}
	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}
	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}
	public String getIsInitSign() {
		return isInitSign;
	}
	public void setIsInitSign(String isInitSign) {
		this.isInitSign = isInitSign;
	}
	
	
}
