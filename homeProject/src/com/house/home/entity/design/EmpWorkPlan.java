package com.house.home.entity.design;

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
 * EmpWorkPlan信息
 */
@Entity
@Table(name = "tEmpWorkPlan")
public class EmpWorkPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PlanBeginDate")
	private Date planBeginDate;
	@Column(name = "PlanCZYType")
	private String planCzyType;
	@Column(name = "PlanCZY")
	private String planCzy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String weekType;
	@Transient
	private String empWorkPlanDetailJson;
	@Transient
	private String planCzyDescr;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPlanBeginDate(Date planBeginDate) {
		this.planBeginDate = planBeginDate;
	}
	
	public Date getPlanBeginDate() {
		return this.planBeginDate;
	}
	
	public String getPlanCzyType() {
		return planCzyType;
	}

	public void setPlanCzyType(String planCzyType) {
		this.planCzyType = planCzyType;
	}

	public void setPlanCzy(String planCzy) {
		this.planCzy = planCzy;
	}
	
	public String getPlanCzy() {
		return this.planCzy;
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

	public String getWeekType() {
		return weekType;
	}

	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}

	public String getEmpWorkPlanDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(empWorkPlanDetailJson);
    	return xml;
	}

	public void setEmpWorkPlanDetailJson(String empWorkPlanDetailJson) {
		this.empWorkPlanDetailJson = empWorkPlanDetailJson;
	}

	public String getPlanCzyDescr() {
		return planCzyDescr;
	}

	public void setPlanCzyDescr(String planCzyDescr) {
		this.planCzyDescr = planCzyDescr;
	}


}
