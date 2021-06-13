package com.house.home.client.service.evt;

import java.util.Date;

public class WorkerProblemConfirmEvt extends BaseQueryEvt{

	private String workType12;
	private String no;
	private String custCode;
	private String status;
	private String confirmRemark;
	private String confirmCZY;
	private Date confirmDate;
	private String isCupboard;
	private String wkpbNo;//工人问题编号
	private String source;//补货来源
	private String address;
	private String czybh;
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getWkpbNo() {
		return wkpbNo;
	}
	public void setWkpbNo(String wkpbNo) {
		this.wkpbNo = wkpbNo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
}
