package com.house.home.client.service.resp;

public class ItemReqResp {
	private Integer pk;
	private String itemCode;
	private String itemCodeDescr;
	private String fixAreaDescr;
	private Double qty;
	private Double sendQty;
	private Double remainQty;
	private String itemType1;
	private String itemType2;
	private String uom;
	private String remark;
	private String remarks;
	private Integer hasChange;//同区域相同材料存在申请状态的材料增减项单
	private Double applyQty;
	private String canOrderFlag;
	private String isConfirm;

	
	public String getCanOrderFlag() {
		return canOrderFlag;
	}
	public void setCanOrderFlag(String canOrderFlag) {
		this.canOrderFlag = canOrderFlag;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItemCodeDescr() {
		return itemCodeDescr;
	}
	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}
	public String getFixAreaDescr() {
		return fixAreaDescr;
	}
	public void setFixAreaDescr(String fixAreaDescr) {
		this.fixAreaDescr = fixAreaDescr;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getSendQty() {
		return sendQty;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	public Double getRemainQty() {
		return remainQty;
	}
	public void setRemainQty(Double remainQty) {
		this.remainQty = remainQty;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getHasChange() {
		return hasChange;
	}
	public void setHasChange(Integer hasChange) {
		this.hasChange = hasChange;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getApplyQty() {
		return applyQty;
	}
	public void setApplyQty(Double applyQty) {
		this.applyQty = applyQty;
	}
	public String getIsConfirm() {
		return isConfirm;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	

}
