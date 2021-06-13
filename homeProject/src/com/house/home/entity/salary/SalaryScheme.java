package com.house.home.entity.salary;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tSalaryScheme")
public class SalaryScheme extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "SalarySchemeType")
	private String salarySchemeType;
	@Column(name = "CmpCode")
	private String cmpCode;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "BeginMon")
	private Integer beginMon;
	@Column(name = "EndMon")
	private Integer endMon;
	@Column(name = "Status")
	private String status;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String detailJson;
	@Transient
	private String salaryItemCodes;
	@Transient
	private String selItemCodes;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String department3;
	@Transient
	private String queryCondition;
	
	
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getDepartment3() {
		return department3;
	}
	public void setDepartment3(String department3) {
		this.department3 = department3;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getSelItemCodes() {
		return selItemCodes;
	}
	public void setSelItemCodes(String selItemCodes) {
		this.selItemCodes = selItemCodes;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public String getSalaryItemCodes() {
		return salaryItemCodes;
	}
	public void setSalaryItemCodes(String salaryItemCodes) {
		this.salaryItemCodes = salaryItemCodes;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getSalarySchemeType() {
		return salarySchemeType;
	}
	public void setSalarySchemeType(String salarySchemeType) {
		this.salarySchemeType = salarySchemeType;
	}
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getBeginMon() {
		return beginMon;
	}
	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}
	public Integer getEndMon() {
		return endMon;
	}
	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getSalaryItemXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}


}
