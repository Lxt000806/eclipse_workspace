package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * tPrjRole信息
 */
@Entity
@Table(name = "tPrjRoleWorkType12")
public class PrjRoleWorkType12 extends BaseEntity {

	private static final long serialVersionUID = 1L;
    	@Id
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PrjRole")
	private String prjRole;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String prjRoleDescr;
	@Transient
	private String workType12Descr;
	@Transient
	private String unSelected;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getPrjRole() {
		return prjRole;
	}
	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
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
	public String getPrjRoleDescr() {
		return prjRoleDescr;
	}
	public void setPrjRoleDescr(String prjRoleDescr) {
		this.prjRoleDescr = prjRoleDescr;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getUnSelected() {
		return unSelected;
	}
	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}
	
	

}
