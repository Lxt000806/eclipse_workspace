package com.house.home.client.service.resp;

public class GetAssetListResp {

	private String code;
	private String assetCode;
	private String descr;
	private String address;
	private Double qty;
	private Double originalValue;
	private String model;
	private String useMan;
	private String useManDescr;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(Double originalValue) {
		this.originalValue = originalValue;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUseMan() {
		return useMan;
	}
	public void setUseMan(String useMan) {
		this.useMan = useMan;
	}
	public String getUseManDescr() {
		return useManDescr;
	}
	public void setUseManDescr(String useManDescr) {
		this.useManDescr = useManDescr;
	}
}
