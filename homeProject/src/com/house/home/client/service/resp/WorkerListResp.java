package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerListResp {
	
	private String nameChi;
	private String phone;
	private String workType12Descr;
	private String prjRole;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date comeDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private int consDay;
	private int relConsDay;
	private String custCode;
	private String workerCode;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date minCrtDate;
	private Integer PK;
	private Integer score;
	
	
	
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getConsDay() {
		return consDay;
	}
	public void setConsDay(int consDay) {
		this.consDay = consDay;
	}
	public int getRelConsDay() {
		return relConsDay;
	}
	public void setRelConsDay(int relConsDay) {
		this.relConsDay = relConsDay;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getPrjRole() {
		return prjRole;
	}
	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
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
	public Date getMinCrtDate() {
		return minCrtDate;
	}
	public void setMinCrtDate(Date minCrtDate) {
		this.minCrtDate = minCrtDate;
	}
	public Integer getPK() {
		return PK;
	}
	public void setPK(Integer pK) {
		PK = pK;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
