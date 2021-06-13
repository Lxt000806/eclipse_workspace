package com.house.home.client.service.resp;

public class PrjJobDetailResp extends BaseResponse{
	private Integer pk;
	private String no;
	private String itemCode;
	private String itemCodeDescr;
	private Double qty;
	private Integer appDtpk;
	private String uom;
	private String arriveNo;
	private String remarks;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemCodeDescr() {
		return itemCodeDescr;
	}
	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Integer getAppDtpk() {
		return appDtpk;
	}
	public void setAppDtpk(Integer appDtpk) {
		this.appDtpk = appDtpk;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getArriveNo() {
		return arriveNo;
	}
	public void setArriveNo(String arriveNo) {
		this.arriveNo = arriveNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
