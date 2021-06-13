package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * GiftStakeholder信息
 */
@Entity
@Table(name = "tGiftStakeholder")
public class GiftStakeholder extends BaseEntity {

	private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "Role")
	private String role;
	@Column(name = "EmpCode")
	private String empCode;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "sharePer")
	private Double sharePer;
	
	@Transient
	private String detailJson;
	@Transient
	private String empDescr;
	@Transient
	private String roleDescr;
	@Transient
	private String department1Descr;
	@Transient
	private String department2Descr;
	@Transient
	private String m_umState;
	
	public String getGiftStakeholderXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

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

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getEmpDescr() {
		return empDescr;
	}

	public void setEmpDescr(String empDescr) {
		this.empDescr = empDescr;
	}

	public String getRoleDescr() {
		return roleDescr;
	}

	public void setRoleDescr(String roleDescr) {
		this.roleDescr = roleDescr;
	}

	public String getDepartment1Descr() {
		return department1Descr;
	}

	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	@Override
	public String getM_umState() {
		return m_umState;
	}

	@Override
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	public Double getSharePer() {
		return sharePer;
	}

	public void setSharePer(Double sharePer) {
		this.sharePer = sharePer;
	}
	
	
	
}
