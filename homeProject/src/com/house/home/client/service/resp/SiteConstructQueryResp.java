package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SiteConstructQueryResp extends BaseResponse {
	private String code;
	private String address;
	private String projectMan;
	private String nameChi;
	private String phone;
	private String workerCode;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date comeDate;
	private String workType12;
	private Integer custWkPk;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date confirmDate;
	private String canCall;
	private Integer contructDay;
	private Double gzl;
	private String workerClassifyDescr;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
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
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getCanCall() {
		return canCall;
	}
	public void setCanCall(String canCall) {
		this.canCall = canCall;
	}
	public Integer getContructDay() {
		return contructDay;
	}
	public void setContructDay(Integer contructDay) {
		this.contructDay = contructDay;
	}
	public Double getGzl() {
		return gzl;
	}
	public void setGzl(Double gzl) {
		this.gzl = gzl;
	}
	public String getWorkerClassifyDescr() {
		return workerClassifyDescr;
	}
	public void setWorkerClassifyDescr(String workerClassifyDescr) {
		this.workerClassifyDescr = workerClassifyDescr;
	}

	
}
