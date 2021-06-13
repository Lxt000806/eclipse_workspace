package com.house.home.client.service.resp;

import java.util.Date;

public class CustCheckResp extends BaseResponse {
	private Date appDate;
	
	private String mainStatusDescr;
	private String mainRemark;

	private String softStatusDescr;
	private String softRemark;

	private String intStatusDescr;
	private String intRemark;

	private String finStatusDescr;
	private String finRemark;
	
	private Date confirmDate;
	private String checkSalaryConfStatus;
	
	public String getCheckSalaryConfStatus() {
		return checkSalaryConfStatus;
	}

	public void setCheckSalaryConfStatus(String checkSalaryConfStatus) {
		this.checkSalaryConfStatus = checkSalaryConfStatus;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getMainStatusDescr() {
		return mainStatusDescr;
	}

	public void setMainStatusDescr(String mainStatusDescr) {
		this.mainStatusDescr = mainStatusDescr;
	}

	public String getMainRemark() {
		return mainRemark;
	}

	public void setMainRemark(String mainRemark) {
		this.mainRemark = mainRemark;
	}

	public String getSoftStatusDescr() {
		return softStatusDescr;
	}

	public void setSoftStatusDescr(String softStatusDescr) {
		this.softStatusDescr = softStatusDescr;
	}

	public String getSoftRemark() {
		return softRemark;
	}

	public void setSoftRemark(String softRemark) {
		this.softRemark = softRemark;
	}

	public String getIntStatusDescr() {
		return intStatusDescr;
	}

	public void setIntStatusDescr(String intStatusDescr) {
		this.intStatusDescr = intStatusDescr;
	}

	public String getIntRemark() {
		return intRemark;
	}

	public void setIntRemark(String intRemark) {
		this.intRemark = intRemark;
	}

	public String getFinStatusDescr() {
		return finStatusDescr;
	}

	public void setFinStatusDescr(String finStatusDescr) {
		this.finStatusDescr = finStatusDescr;
	}

	public String getFinRemark() {
		return finRemark;
	}

	public void setFinRemark(String finRemark) {
		this.finRemark = finRemark;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
}
