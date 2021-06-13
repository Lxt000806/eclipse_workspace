package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SiteConfirmDetailResp extends BaseResponse {
	private String no;
	private String address;
	private String note;
	private Date date;
	private String remarks;
	private String isPass;
	private String house;
	private String custCode;
	private String confirmCzyDescr;
	private String prjLevelDescr;
	private String prjItem;
	private String appCheck;
	private Integer score;
	private String workerName;
	private String isPushCust;
	private String prjWorkable;
	
	
	
	
	public String getPrjWorkable() {
		return prjWorkable;
	}
	public void setPrjWorkable(String prjWorkable) {
		this.prjWorkable = prjWorkable;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public String getPrjLevelDescr() {
		return prjLevelDescr;
	}
	public void setPrjLevelDescr(String prjLevelDescr) {
		this.prjLevelDescr = prjLevelDescr;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getAppCheck() {
		return appCheck;
	}
	public void setAppCheck(String appCheck) {
		this.appCheck = appCheck;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

	
}
