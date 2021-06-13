package com.house.home.entity.basic;

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

/**
 * CustContractTemp信息
 */
@Entity
@Table(name = "tCustContractTemp")
public class CustContractTemp extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Version")
	private String version;
	@Column(name = "Type")
	private String type;
	@Column(name = "FileName")
	private String fileName;
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
	@Column(name = "Status")
	private String status;
	@Column(name = "BuilderCode")
	private String builderCode;

	@Transient
	private String custContractTempFieldJson;
	@Transient
	private String code;
	@Transient
	private String expression;
	@Transient
	private String fieldRemarks;
	@Transient
	private String dispSeq;
	@Transient
	private String conTempPK;
	@Transient
	private String fieldExpired;
	@Transient
	private String builderDescr;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getPk() {
		return this.pk;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustType() {
		return this.custType;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDescr() {
		return this.descr;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return this.version;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return this.fileName;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getCustContractTempFieldJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(custContractTempFieldJson);
    	return xml;
	}
	public void setCustContractTempFieldJson(String custContractTempFieldJson) {
		this.custContractTempFieldJson = custContractTempFieldJson;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getFieldRemarks() {
		return fieldRemarks;
	}
	public void setFieldRemarks(String fieldRemarks) {
		this.fieldRemarks = fieldRemarks;
	}
	public String getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(String dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getConTempPK() {
		return conTempPK;
	}
	public void setConTempPK(String conTempPK) {
		this.conTempPK = conTempPK;
	}
	public String getFieldExpired() {
		return fieldExpired;
	}
	public void setFieldExpired(String fieldExpired) {
		this.fieldExpired = fieldExpired;
	}
	public String getBuilderCode() {
		return builderCode;
	}
	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}
	
}
