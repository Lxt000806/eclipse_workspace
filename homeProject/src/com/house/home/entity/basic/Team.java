package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * 二级部门
 */
@Entity
@Table(name = "tTeam")
public class Team extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "IsCalcPerf")
	private String isCalcPerf;
	// Constructors

	/** default constructor */
	public Team() {
	}

	/** minimal constructor */
	public Team(String code, String desc1) {
		this.code = code;
		this.desc1 = desc1;
	}

	/** full constructor */
	public Team(String code, String desc1, Date lastUpdate,
			String lastUpdatedBy, String expired, String actionLog) {
		super();
		this.code = code;
		this.desc1 = desc1;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getIsCalcPerf() {
		return isCalcPerf;
	}

	public void setIsCalcPerf(String isCalcPerf) {
		this.isCalcPerf = isCalcPerf;
	}
    
}
