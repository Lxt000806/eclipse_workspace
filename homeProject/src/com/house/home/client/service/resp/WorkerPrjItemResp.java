package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerPrjItemResp extends BaseResponse {
	private int pk;
	private String workerCode;
	private String custCode;
	private String workType12;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date comeDate;
	private String wt12Descr;
	private String pi2Descr;
	private String code;
	private int minDay;
	private int seq;
	private String isAppWork;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date crtDate;
	private String address;
	private String canSign;
	private String signDescr;
	private int max;
	private int canApplyCount;
	private Integer needDays;
	private boolean signInCmpFlag;
	
	private String tipDescr;
	private boolean buttonDisable;
	private Double costCount;
	private String isTechnology;
	private Integer endDays;
	private String isSpecReq;
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	public String getWt12Descr() {
		return wt12Descr;
	}
	public void setWt12Descr(String wt12Descr) {
		this.wt12Descr = wt12Descr;
	}
	public String getPi2Descr() {
		return pi2Descr;
	}
	public void setPi2Descr(String pi2Descr) {
		this.pi2Descr = pi2Descr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getMinDay() {
		return minDay;
	}
	public void setMinDay(int minDay) {
		this.minDay = minDay;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getIsAppWork() {
		return isAppWork;
	}
	public void setIsAppWork(String isAppWork) {
		this.isAppWork = isAppWork;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCanSign() {
		return canSign;
	}
	public void setCanSign(String canSign) {
		this.canSign = canSign;
	}
	public String getSignDescr() {
		return signDescr;
	}
	public void setSignDescr(String signDescr) {
		this.signDescr = signDescr;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getCanApplyCount() {
		return canApplyCount;
	}
	public void setCanApplyCount(int canApplyCount) {
		this.canApplyCount = canApplyCount;
	}
	public Integer getNeedDays() {
		return needDays;
	}
	public void setNeedDays(Integer needDays) {
		this.needDays = needDays;
	}
	public boolean isSignInCmpFlag() {
		return signInCmpFlag;
	}
	public void setSignInCmpFlag(boolean signInCmpFlag) {
		this.signInCmpFlag = signInCmpFlag;
	}
	public String getTipDescr() {
		return tipDescr;
	}
	public void setTipDescr(String tipDescr) {
		this.tipDescr = tipDescr;
	}
	public boolean isButtonDisable() {
		return buttonDisable;
	}
	public void setButtonDisable(boolean buttonDisable) {
		this.buttonDisable = buttonDisable;
	}
	public Double getCostCount() {
		return costCount;
	}
	public void setCostCount(Double costCount) {
		this.costCount = costCount;
	}
	public String getIsTechnology() {
		return isTechnology;
	}
	public void setIsTechnology(String isTechnology) {
		this.isTechnology = isTechnology;
	}
	public Integer getEndDays() {
		return endDays;
	}
	public void setEndDays(Integer endDays) {
		this.endDays = endDays;
	}

	public String getIsSpecReq() {
		return isSpecReq;
	}

	public void setIsSpecReq(String isSpecReq) {
		this.isSpecReq = isSpecReq;
	}
}
