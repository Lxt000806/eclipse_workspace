package com.house.home.client.service.resp;

import java.util.Date;

public class WorkerArrangeResp extends BaseResponse {
	
	private Date date;
	private String dayType;
	private String dayTypeDescr;
	private Integer canOrderNum;
	private Integer no;
	private String noDescr;
	private String code;
	private String address;
	private Integer pk;
	private String projectManDescr;
	private String workerDescr;
	private String workType12Descr;
	private String phone;
	private Integer diffDays;
	
	public Integer getDiffDays() {
		return diffDays;
	}
	public void setDiffDays(Integer diffDays) {
		this.diffDays = diffDays;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public String getDayTypeDescr() {
		return dayTypeDescr;
	}
	public void setDayTypeDescr(String dayTypeDescr) {
		this.dayTypeDescr = dayTypeDescr;
	}
	public Integer getCanOrderNum() {
		return canOrderNum;
	}
	public void setCanOrderNum(Integer canOrderNum) {
		this.canOrderNum = canOrderNum;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getNoDescr() {
		return noDescr;
	}
	public void setNoDescr(String noDescr) {
		this.noDescr = noDescr;
	}
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
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getWorkerDescr() {
		return workerDescr;
	}
	public void setWorkerDescr(String workerDescr) {
		this.workerDescr = workerDescr;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
