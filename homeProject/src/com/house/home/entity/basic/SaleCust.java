package com.house.home.entity.basic;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;


@Entity
@Table(name = "tSaleCust")
public class SaleCust extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Desc2")
	private String desc2;
	@Column(name = "Country")
	private String country;
	@Column(name = "Address")
	private String address;
	@Column(name = "Contact")
	private String contact;
	@Column(name = "Phone1")
	private String phone1;
	@Column(name = "Phone2")
	private String phone2;
	@Column(name = "Fax1")
	private String fax1;
	@Column(name = "Fax2")
	private String fax2;
	@Column(name = "Mobile1")
	private String mobile1;
	@Column(name = "Mobile2")
	private String mobile2;
	@Column(name = "Email1")
	private String email1;
	@Column(name = "Email2")
	private String email2;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "QQ")
	private String qq;
	@Column(name = "MSN")
	private String msn;
	@Column(name = "RemDate1")
	private String remDate1;
	@Column(name = "RemDate2")
	private String remDate2;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
		
	@Transient
	private String returnCount;//客户记录数
	@Transient
	private String mobileHidden;//隐藏电话

	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	public String getFax2() {
		return fax2;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getRemDate1() {
		return remDate1;
	}
	public void setRemDate1(String remDate1) {
		this.remDate1 = remDate1;
	}
	public String getRemDate2() {
		return remDate2;
	}
	public void setRemDate2(String remDate2) {
		this.remDate2 = remDate2;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(String returnCount) {
		this.returnCount = returnCount;
	}
	public String getMobileHidden() {
		return mobileHidden;
	}
	public void setMobileHidden(String mobileHidden) {
		this.mobileHidden = mobileHidden;
	}
	
	
}
