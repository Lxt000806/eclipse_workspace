package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tCustComplaint")
public class CustComplaint extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Source")
	private String source;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "CrtCZY")
	private String crtCZY;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ComplType")
	private String complType;
	
	@Transient
	private String detailJson;
	@Transient
	private String address;
	@Transient
	private String department2;
	@Transient
	private String promDept1Code;
	@Transient
	private String promDept2Code;
	@Transient
	private String promType1;
	@Transient
	private String promType2;
	@Transient
	private String promRsn;
	@Transient
	private String supplCode;
	@Transient
	private String mobile1;
	@Transient
	private String mobile2;
	
	public String getSupplCode() {
		return supplCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	public String getPromType1() {
		return promType1;
	}
	public void setPromType1(String promType1) {
		this.promType1 = promType1;
	}
	public String getPromType2() {
		return promType2;
	}
	public void setPromType2(String promType2) {
		this.promType2 = promType2;
	}
	public String getPromRsn() {
		return promRsn;
	}
	public void setPromRsn(String promRsn) {
		this.promRsn = promRsn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getPromDept1Code() {
		return promDept1Code;
	}
	public void setPromDept1Code(String promDept1Code) {
		this.promDept1Code = promDept1Code;
	}
	public String getPromDept2Code() {
		return promDept2Code;
	}
	public void setPromDept2Code(String promDept2Code) {
		this.promDept2Code = promDept2Code;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getCrtCZY() {
		return crtCZY;
	}
	public void setCrtCZY(String crtCZY) {
		this.crtCZY = crtCZY;
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
	public String getGiftAppDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getComplType() {
		return complType;
	}
	public void setComplType(String complType) {
		this.complType = complType;
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
	
}
