package com.house.home.client.service.evt;

import java.util.Date;

import javax.validation.constraints.NotNull;
public class PrjProgUpdateEvt extends BaseEvt{
	
	@NotNull(message="pk不能为空")
	private Integer pk;
	private Date beginDate;

	private Date endDate;
	
	private String custCode;
	
	private String prjItem;
	
	private boolean firstFlag;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public boolean isFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(boolean firstFlag) {
		this.firstFlag = firstFlag;
	}
	
	

}
