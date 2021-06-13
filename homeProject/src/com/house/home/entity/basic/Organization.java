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

/**
 * Organization信息
 */
@Entity
@Table(name = "tOrganization")
public class Organization extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "OrgId")
	private String orgId;
	@Column(name = "FlowId")
	private String flowId;
	@Column(name = "IdentifyUrl")
	private String identifyUrl;
	@Column(name = "IsIdentified")
	private String isIdentified;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsSilenceSign")
	private String isSilenceSign;
	@Column(name = "ThirdPartyUserId")
	private String thirdPartyUserId;
	@Column(name = "CreatorId")
	private String creatorId;
	@Column(name = "IdType")
	private String idType;
	@Column(name = "IdNumber")
	private String idNumber;
	@Column(name = "Name")
	private String name;
	@Column(name = "OrgLegalIdNumber")
	private String orgLegalIdNumber;
	@Column(name = "OrgLegalName")
	private String orgLegalName;
	
	@Transient
	private String htext;
	@Transient
	private String qtext;
	@Transient
	private String color;
	@Transient
	private String type;
	@Transient
	private String central;
	@Transient
	private String sealId;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgId() {
		return this.orgId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	
	public String getFlowId() {
		return this.flowId;
	}
	public void setIdentifyUrl(String identifyUrl) {
		this.identifyUrl = identifyUrl;
	}
	
	public String getIdentifyUrl() {
		return this.identifyUrl;
	}
	public void setIsIdentified(String isIdentified) {
		this.isIdentified = isIdentified;
	}
	
	public String getIsIdentified() {
		return this.isIdentified;
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
	public void setIsSilenceSign(String isSilenceSign) {
		this.isSilenceSign = isSilenceSign;
	}
	
	public String getIsSilenceSign() {
		return this.isSilenceSign;
	}

	public String getThirdPartyUserId() {
		return thirdPartyUserId;
	}

	public void setThirdPartyUserId(String thirdPartyUserId) {
		this.thirdPartyUserId = thirdPartyUserId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgLegalIdNumber() {
		return orgLegalIdNumber;
	}

	public void setOrgLegalIdNumber(String orgLegalIdNumber) {
		this.orgLegalIdNumber = orgLegalIdNumber;
	}

	public String getOrgLegalName() {
		return orgLegalName;
	}

	public void setOrgLegalName(String orgLegalName) {
		this.orgLegalName = orgLegalName;
	}

	public String getHtext() {
		return htext;
	}

	public void setHtext(String htext) {
		this.htext = htext;
	}

	public String getQtext() {
		return qtext;
	}

	public void setQtext(String qtext) {
		this.qtext = qtext;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCentral() {
		return central;
	}

	public void setCentral(String central) {
		this.central = central;
	}

	public String getSealId() {
		return sealId;
	}

	public void setSealId(String sealId) {
		this.sealId = sealId;
	}
	
}
