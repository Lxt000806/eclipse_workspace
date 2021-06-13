package com.house.home.client.service.resp;

public class FixDutyDetailResp extends BaseResponse{
	
	private String baseCheckItemCode;
	private String baseCheckItemDescr;
	private double qty;
	private double cfmQty;
	private double offerPri;
	private double material;
	private String  remark;
	private String custCode;
	private String fixAreaDescr;
	private String remarks;
	private Integer pk;
	private String workType12Descr;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getBaseCheckItemCode() {
		return baseCheckItemCode;
	}
	public void setBaseCheckItemCode(String baseCheckItemCode) {
		this.baseCheckItemCode = baseCheckItemCode;
	}
	public String getBaseCheckItemDescr() {
		return baseCheckItemDescr;
	}
	public void setBaseCheckItemDescr(String baseCheckItemDescr) {
		this.baseCheckItemDescr = baseCheckItemDescr;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getCfmQty() {
		return cfmQty;
	}
	public void setCfmQty(double cfmQty) {
		this.cfmQty = cfmQty;
	}
	public double getOfferPri() {
		return offerPri;
	}
	public void setOfferPri(double offerPri) {
		this.offerPri = offerPri;
	}
	public double getMaterial() {
		return material;
	}
	public void setMaterial(double material) {
		this.material = material;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	
	
	
}
