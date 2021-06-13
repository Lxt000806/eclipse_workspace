package com.house.home.entity.design;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tCustEval")
public class CustEval extends BaseEntity{

	private static final long serialVersionUID = 1L;
@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "PrjScore")
	private Integer prjScore;
	@Column(name = "DesignScore")
	private Integer designScore;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String projectMan;
	@Transient
	private String designMan;
	
	
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getPrjScore() {
		return prjScore;
	}
	public void setPrjScore(Integer prjScore) {
		this.prjScore = prjScore;
	}
	public Integer getDesignScore() {
		return designScore;
	}
	public void setDesignScore(Integer designScore) {
		this.designScore = designScore;
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


}
