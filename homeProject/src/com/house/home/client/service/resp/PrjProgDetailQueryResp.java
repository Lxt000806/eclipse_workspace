package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PrjProgDetailQueryResp {
	
	private String address;
	private Integer pk;
	private String custCode;
	private String prjItem;
	private String prjItemDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date planBegin;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date planEnd;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String prjStatus;
	private String prjLevel;
	private String prjLevelDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date comeDate;
	private Integer constructDay;
	private String workerName;
	private String workerCode;
	private Integer prjPhotoNum;
	private Integer custWkPk;
	private Integer score;
	private Integer healthScore;
	private Integer toolScore;
	private String remark;
	private Integer starPk;
	
	
	
	public Integer getStarPk() {
		return starPk;
	}
	public void setStarPk(Integer starPk) {
		this.starPk = starPk;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getHealthScore() {
		return healthScore;
	}
	public void setHealthScore(Integer healthScore) {
		this.healthScore = healthScore;
	}
	public Integer getToolScore() {
		return toolScore;
	}
	public void setToolScore(Integer toolScore) {
		this.toolScore = toolScore;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Integer getPrjPhotoNum() {
		return prjPhotoNum;
	}
	public void setPrjPhotoNum(Integer prjPhotoNum) {
		this.prjPhotoNum = prjPhotoNum;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
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
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public Date getPlanBegin() {
		return planBegin;
	}
	public void setPlanBegin(Date planBegin) {
		this.planBegin = planBegin;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPrjStatus() {
		return prjStatus;
	}
	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}
	public String getPrjLevel() {
		return prjLevel;
	}
	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}
	public String getPrjLevelDescr() {
		return prjLevelDescr;
	}
	public void setPrjLevelDescr(String prjLevelDescr) {
		this.prjLevelDescr = prjLevelDescr;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public Integer getConstructDay() {
		return constructDay;
	}
	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	

}
