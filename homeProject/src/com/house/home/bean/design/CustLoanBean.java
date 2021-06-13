package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * custLoan信息bean
 */
public class CustLoanBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@ExcelAnnotation(exportName="客户编号", order=1)
	private String custCode;
	@ExcelAnnotation(exportName="协议提交日期",  order=2)
	private String agreeDate;
	@ExcelAnnotation(exportName="贷款银行", order=3)
	private String bank;
	@ExcelAnnotation(exportName="贷款总额/万元", order=4)
	private Double amount;
	@ExcelAnnotation(exportName="首次放款金额/万元", order=5)
	private Double firstAmount;
	@ExcelAnnotation(exportName="首次放款时间",  order=6)
	private String firstDate;
	@ExcelAnnotation(exportName="二次放款金额/万元",  order=7)
	private Double secondAmount;
	@ExcelAnnotation(exportName="二次放款时间",   order=8)
	private String secondDate;
	@ExcelAnnotation(exportName="已签约待放款", order=9)
	private String signRemark;
	@ExcelAnnotation(exportName="退件拒批", order=10)
	private String confuseRemark;
	@ExcelAnnotation(exportName="需跟踪", order=11)
	private String followRemark;
	@ExcelAnnotation(exportName="备注", order=12)
	private String remark;
	@ExcelAnnotation(exportName="错误信息", order=13)
	private String error;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getFirstAmount() {
		return firstAmount;
	}
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public Double getSecondAmount() {
		return secondAmount;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
	
	public String getSignRemark() {
		return signRemark;
	}
	public void setSignRemark(String signRemark) {
		this.signRemark = signRemark;
	}
	public String getConfuseRemark() {
		return confuseRemark;
	}
	public void setConfuseRemark(String confuseRemark) {
		this.confuseRemark = confuseRemark;
	}
	public String getFollowRemark() {
		return followRemark;
	}
	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAgreeDate() {
		return agreeDate;
	}
	public void setAgreeDate(String agreeDate) {
		this.agreeDate = agreeDate;
	}
	public String getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}
	public String getSecondDate() {
		return secondDate;
	}
	public void setSecondDate(String secondDate) {
		this.secondDate = secondDate;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	

}
