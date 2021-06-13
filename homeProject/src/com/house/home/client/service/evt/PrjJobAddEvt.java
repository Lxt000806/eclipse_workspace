package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjJobAddEvt extends BaseEvt{
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="材料类型1不能为空")
	private String itemType1;
	@NotEmpty(message="任务类型不能为空")
	private String jobType;
	@NotEmpty(message="app操作员不能为空")
	private String appCzy;
	private String dealCzy;
	private String remarks;
	private String status;
	private String photoString;//多个逗号隔开
	private String warBrand;
	private String cupBrand;
	private String isNeedReq;
    
    private String fromInfoAdd;
    private Integer infoPk;
	
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getDealCzy() {
		return dealCzy;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	public String getWarBrand() {
		return warBrand;
	}
	public void setWarBrand(String warBrand) {
		this.warBrand = warBrand;
	}
	public String getCupBrand() {
		return cupBrand;
	}
	public void setCupBrand(String cupBrand) {
		this.cupBrand = cupBrand;
	}
	public String getIsNeedReq() {
		return isNeedReq;
	}
	public void setIsNeedReq(String isNeedReq) {
		this.isNeedReq = isNeedReq;
	}
	public String getFromInfoAdd() {
		return fromInfoAdd;
	}
	public void setFromInfoAdd(String fromInfoAdd) {
		this.fromInfoAdd = fromInfoAdd;
	}
	public Integer getInfoPk() {
		return infoPk;
	}
	public void setInfoPk(Integer infoPk) {
		this.infoPk = infoPk;
	}

}
