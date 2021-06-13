package com.house.home.client.service.resp;

import java.util.Date;

public class CompletionReportResp extends BaseResponse{
	
	private String address;
	private String workType12;
	private String workType12Descr;
	private Date planEnd;
	private Date conPlanEnd;
	private String nowProgress;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getConPlanEnd() {
		return conPlanEnd;
	}
	public void setConPlanEnd(Date conPlanEnd) {
		this.conPlanEnd = conPlanEnd;
	}
	public String getNowProgress() {
		return nowProgress;
	}
	public void setNowProgress(String nowProgress) {
		this.nowProgress = nowProgress;
	}
	
	
}
