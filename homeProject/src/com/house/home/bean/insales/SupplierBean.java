package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Supplier信息bean
 */
public class SupplierBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="Code", order=1)
	private String code;
	@ExcelAnnotation(exportName="名称", order=2)
	private String descr;
	@ExcelAnnotation(exportName="地址", order=3)
	private String address;
	@ExcelAnnotation(exportName="联系人", order=4)
	private String contact;
	@ExcelAnnotation(exportName="电话1", order=5)
	private String phone1;
	@ExcelAnnotation(exportName="Phone2", order=6)
	private String phone2;
	@ExcelAnnotation(exportName="传真1", order=7)
	private String fax1;
	@ExcelAnnotation(exportName="Fax2", order=8)
	private String fax2;
	@ExcelAnnotation(exportName="手机", order=9)
	private String mobile1;
	@ExcelAnnotation(exportName="Mobile2", order=10)
	private String mobile2;
	@ExcelAnnotation(exportName="邮件地址1", order=11)
	private String email1;
	@ExcelAnnotation(exportName="Email2", order=12)
	private String email2;
	@ExcelAnnotation(exportName="是否指定结算日期", order=13)
	private String isSpecDay;
	@ExcelAnnotation(exportName="指定结算日期", order=14)
	private Integer specDay;
	@ExcelAnnotation(exportName="结算周期", order=15)
	private Integer billCycle;
    	@ExcelAnnotation(exportName="LastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=16)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="LastUpdatedBy", order=17)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="Expired", order=18)
	private String expired;
	@ExcelAnnotation(exportName="ActionLog", order=19)
	private String actionLog;
	@ExcelAnnotation(exportName="材料类型1", order=20)
	private String itemType1;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	public String getPhone1() {
		return this.phone1;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public String getPhone2() {
		return this.phone2;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	
	public String getFax1() {
		return this.fax1;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	
	public String getFax2() {
		return this.fax2;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	
	public String getMobile1() {
		return this.mobile1;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	
	public String getMobile2() {
		return this.mobile2;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	public String getEmail1() {
		return this.email1;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	public String getEmail2() {
		return this.email2;
	}
	public void setIsSpecDay(String isSpecDay) {
		this.isSpecDay = isSpecDay;
	}
	
	public String getIsSpecDay() {
		return this.isSpecDay;
	}
	public void setSpecDay(Integer specDay) {
		this.specDay = specDay;
	}
	
	public Integer getSpecDay() {
		return this.specDay;
	}
	public void setBillCycle(Integer billCycle) {
		this.billCycle = billCycle;
	}
	
	public Integer getBillCycle() {
		return this.billCycle;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}

}
