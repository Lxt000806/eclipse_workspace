package com.house.home.bean.basic;

import java.util.Date;

import com.house.framework.commons.excel.ExcelAnnotation;

public class SaleCustBean  implements java.io.Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@ExcelAnnotation (exportName="销售客户编号",order=1)
	private String Code;
	@ExcelAnnotation (exportName="销售客户名称1",order=2)
	private String	desc1;
	@ExcelAnnotation (exportName="销售客户名称2",order=3)
	private String	desc2;
	@ExcelAnnotation (exportName="联系人",order=4)
	private String	contact;
	@ExcelAnnotation (exportName="地址",order=5)
	private String address;
	@ExcelAnnotation (exportName="手机1",order=6)
	private String mobile1;
	@ExcelAnnotation (exportName="手机2",order=7)
	private String mobile2;
	@ExcelAnnotation (exportName="传真1",order=8)
	private String fax1;	
	@ExcelAnnotation (exportName="传真2",order=9)
	private String fax2;
	@ExcelAnnotation (exportName="电话1",order=10)
	private String phone1;
	@ExcelAnnotation (exportName="电话2",order=11)
	private String phone2;
	@ExcelAnnotation (exportName="邮件1",order=12)
	private String email1;
	@ExcelAnnotation (exportName="邮件2",order=13)
	private String email2;
	@ExcelAnnotation (exportName="QQ",order=14)
	private String qq;
	@ExcelAnnotation (exportName="MSN",order=15)
	private String msn;
	@ExcelAnnotation (exportName="纪念日1",pattern="yyyy-MM-dd HH:mm:ss",order=16)
	private Date remDate1;
	@ExcelAnnotation (exportName="纪念日2",pattern="yyyy-MM-dd HH:mm:ss",order=17)
	private Date remDate2;
	@ExcelAnnotation (exportName="备注",order=18)
	private String remarks;
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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

	public Date getRemDate1() {
		return remDate1;
	}
	public void setRemDate1(Date remDate1) {
		this.remDate1 = remDate1;
	}
	public Date getRemDate2() {
		return remDate2;
	}
	public void setRemDate2(Date remDate2) {
		this.remDate2 = remDate2;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
