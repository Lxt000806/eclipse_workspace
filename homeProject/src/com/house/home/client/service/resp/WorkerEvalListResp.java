package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerEvalListResp extends BaseResponse {

	private String custCode;
	private String workerCode;
	private Integer custWkPk;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date dateTime;
	private String type;
	private String evaMan;
	private Integer score;
	private Integer healthScore;
	private Integer toolScore;
	private String remark;
	private String nameChi; 
	private Integer maxPk;
	private String status;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date appDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;
	private String prjItem;
	private String prjItemDescr;
	private String workType12;
	private String workType12Descr;
	private String address;
	private Integer workerEvalId;
	private String projectMan;
	private String isPass;
	private Integer pk;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public Integer getMaxPk() {
		return maxPk;
	}
	public void setMaxPk(Integer maxPk) {
		this.maxPk = maxPk;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEvaMan() {
		return evaMan;
	}
	public void setEvaMan(String evaMan) {
		this.evaMan = evaMan;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getWorkerEvalId() {
		return workerEvalId;
	}
	public void setWorkerEvalId(Integer workerEvalId) {
		this.workerEvalId = workerEvalId;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}

	
}
