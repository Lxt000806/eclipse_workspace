package com.house.home.client.service.resp;

import java.util.Date;

public class GojqGridForOAResp {
	
	private String code;
	private String address;
	private String status;
	private String descr;
	private String statusDescr;
	private String designManDescr;
	private String businessManDescr;
	private Date setDate;
	private String company;
	private String companyDescr;
	private double designFee;
	private double realDesignFee;
	private double contractFee;
	private Date confirmBegin;
	private String documentNo;
	private String custTypeDescr;
	private Double tax;
	private String isInitSign;
	private String custPayee;
	private String custPayeeDescr;
	
	public String getCustPayee() {
		return custPayee;
	}
	public void setCustPayee(String custPayee) {
		this.custPayee = custPayee;
	}
	public String getCustPayeeDescr() {
		return custPayeeDescr;
	}
	public void setCustPayeeDescr(String custPayeeDescr) {
		this.custPayeeDescr = custPayeeDescr;
	}
	public String getIsInitSign() {
		return isInitSign;
	}
	public void setIsInitSign(String isInitSign) {
		this.isInitSign = isInitSign;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(double designFee) {
		this.designFee = designFee;
	}
	public double getRealDesignFee() {
		return realDesignFee;
	}
	public void setRealDesignFee(double realDesignFee) {
		this.realDesignFee = realDesignFee;
	}
	public double getContractFee() {
		return contractFee;
	}
	public void setContractFee(double contractFee) {
		this.contractFee = contractFee;
	}
	public Date getConfirmBegin() {
		return confirmBegin;
	}
	public void setConfirmBegin(Date confirmBegin) {
		this.confirmBegin = confirmBegin;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyDescr() {
		return companyDescr;
	}
	public void setCompanyDescr(String companyDescr) {
		this.companyDescr = companyDescr;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public Date getSetDate() {
		return setDate;
	}
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
	public String getCustTypeDescr() {
		return custTypeDescr;
	}
	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}
}
