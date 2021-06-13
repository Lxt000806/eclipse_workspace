package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CzyDept信息
 */
@Entity
@Table(name = "tCZYDept")
public class CzyDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "Czybh")
	private String czybh;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Department3")
	private String department3;
	@Column(name = "LastUpdate")
	@JSONField (format="yyyy-MM-dd HH:mm:ss") 
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
	public String getCzybh() {
		return this.czybh;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	public String getDepartment1() {
		return this.department1;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	
	public String getDepartment2() {
		return this.department2;
	}
	public void setDepartment3(String department3) {
		this.department3 = department3;
	}
	
	public String getDepartment3() {
		return this.department3;
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

}
