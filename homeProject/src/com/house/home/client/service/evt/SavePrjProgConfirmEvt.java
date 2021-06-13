package com.house.home.client.service.evt;

import java.util.Date;

import net.sf.json.JSONArray;

import org.hibernate.validator.constraints.NotEmpty;
public class SavePrjProgConfirmEvt extends BaseEvt {
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="施工项目不能为空")
	private String prjItem;
	private String remarks;
	private String address;
	private String isPass;
	private String photoNameList;
	private String prjLevel;
	private Date endDate;
	private String no;
	private String lastUpdatedBy;
	private String appCheck;
	private String fromPageFlag;
	private String fromCzy;
	private String isPushCust;
	private String errPosi;
	private Double longitude;
	private Double latitude;
	private String prjWorkable;
	private JSONArray confirmCustomFiledValue;
	private String type;
	
	
	
	public JSONArray getConfirmCustomFiledValue() {
		return confirmCustomFiledValue;
	}
	public void setConfirmCustomFiledValue(JSONArray confirmCustomFiledValue) {
		this.confirmCustomFiledValue = confirmCustomFiledValue;
	}
	public String getPrjWorkable() {
		return prjWorkable;
	}
	public void setPrjWorkable(String prjWorkable) {
		this.prjWorkable = prjWorkable;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}
	public String getFromCzy() {
		return fromCzy;
	}
	public void setFromCzy(String fromCzy) {
		this.fromCzy = fromCzy;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(String photoNameList) {
		this.photoNameList = photoNameList;
	}
	public String getPrjLevel() {
		return prjLevel;
	}
	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getAppCheck() {
		return appCheck;
	}
	public void setAppCheck(String appCheck) {
		this.appCheck = appCheck;
	}
	public String getFromPageFlag() {
		return fromPageFlag;
	}
	public void setFromPageFlag(String fromPageFlag) {
		this.fromPageFlag = fromPageFlag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
