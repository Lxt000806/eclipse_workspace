package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * 项目大类信息
 */
@Entity
@Table(name = "tBuilderGroup")
public class BuilderGroup extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

}
