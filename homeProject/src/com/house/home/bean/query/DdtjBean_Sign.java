package com.house.home.bean.query;

import java.io.Serializable;
import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

public class DdtjBean_Sign implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="楼盘", order=1)
	private String address;
	@ExcelAnnotation(exportName="设计师", order=2)
	private String designManDescr;
	@ExcelAnnotation(exportName="业务员", order=3)
	private String businessManDescr;
	@ExcelAnnotation(exportName="下定时间",pattern="yyyy-MM-dd", order=4)
	private Date setDate;
	@ExcelAnnotation(exportName="合同签订时间",pattern="yyyy-MM-dd", order=5)
	private Date signDate;
	@ExcelAnnotation(exportName="分摊签单金额", order=6)
	private Double contractFee_num;
	@ExcelAnnotation(exportName="分摊业绩金额", order=7)
	private Double achievFee;
	@ExcelAnnotation(exportName="首款到账率", order=8)
	private Double payPer;
	@ExcelAnnotation(exportName="已付款", order=9)
	private Double payAmount;
	@ExcelAnnotation(exportName="签单金额", order=10)
	private Double contractFee;
	@ExcelAnnotation(exportName="分摊业绩基数", order=11)
	private Integer num;
	@ExcelAnnotation(exportName="设计费", order=12)
	private Double designFee;
	@ExcelAnnotation(exportName="面积", order=13)
	private Integer area;
	@ExcelAnnotation(exportName="管理费", order=14)
	private Double manageFee;
	@ExcelAnnotation(exportName="服务性产品费", order=15)
	private Double mainservFee;
	@ExcelAnnotation(exportName="业绩调整数", order=16)
	private Double achievDed;
	@ExcelAnnotation(exportName="首付款", order=17)
	private Double firstPay;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public Date getSetDate() {
		return setDate;
	}
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Double getContractFee_num() {
		return contractFee_num;
	}
	public void setContractFee_num(Double contractFee_num) {
		this.contractFee_num = contractFee_num;
	}
	public Double getAchievFee() {
		return achievFee;
	}
	public void setAchievFee(Double achievFee) {
		this.achievFee = achievFee;
	}
	public Double getPayPer() {
		return payPer;
	}
	public void setPayPer(Double payPer) {
		this.payPer = payPer;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getMainservFee() {
		return mainservFee;
	}
	public void setMainservFee(Double mainservFee) {
		this.mainservFee = mainservFee;
	}
	public Double getAchievDed() {
		return achievDed;
	}
	public void setAchievDed(Double achievDed) {
		this.achievDed = achievDed;
	}
	public Double getFirstPay() {
		return firstPay;
	}
	public void setFirstPay(Double firstPay) {
		this.firstPay = firstPay;
	}
	
}
