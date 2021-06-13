package com.house.home.client.service.resp;

public class ItemPreAppDetailResp extends BaseResponse{
	private Integer pk;
	private String no;
	private String itemCode;
	private String itemCodeDescr;
	private Double qty;
	private Integer reqPk;
	private String uom;
	private Integer bdpk;
	private Double xtQty;
	private String xtStatus;
	
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
	public Integer getReqPk() {
		return reqPk;
	}
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Integer getBdpk() {
		return bdpk;
	}
	public void setBdpk(Integer bdpk) {
		this.bdpk = bdpk;
	}
	public Double getXtQty() {
		return xtQty;
	}
	public void setXtQty(Double xtQty) {
		this.xtQty = xtQty;
	}
	public String getXtStatus() {
		return xtStatus;
	}
	public void setXtStatus(String xtStatus) {
		this.xtStatus = xtStatus;
	}
	
}
