package com.house.home.entity.commi;

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
 * CommiCustStakeholderProvide信息
 */
@Entity
@Table(name = "tCommiCustStakeholderProvide")
public class CommiCustStakeholderProvide extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CommiNo")
	private String commiNo;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Role")
	private String role;
	@Column(name = "CommiProvidePer")
	private Double commiProvidePer;
	@Column(name = "SubsidyProvidePer")
	private Double subsidyProvidePer;
	@Column(name = "MultipleProvidePer")
	private Double multipleProvidePer;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String roleDescr;
	@Transient
	private String custDescr;
	@Transient
	private String address;
	@Transient
	private Integer mon;
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCommiNo(String commiNo) {
		this.commiNo = commiNo;
	}
	
	public String getCommiNo() {
		return this.commiNo;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setCommiProvidePer(Double commiProvidePer) {
		this.commiProvidePer = commiProvidePer;
	}
	
	public Double getCommiProvidePer() {
		return this.commiProvidePer;
	}
	public void setSubsidyProvidePer(Double subsidyProvidePer) {
		this.subsidyProvidePer = subsidyProvidePer;
	}
	
	public Double getSubsidyProvidePer() {
		return this.subsidyProvidePer;
	}
	public void setMultipleProvidePer(Double multipleProvidePer) {
		this.multipleProvidePer = multipleProvidePer;
	}
	
	public Double getMultipleProvidePer() {
		return this.multipleProvidePer;
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

	public String getRoleDescr() {
		return roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}
	
}
