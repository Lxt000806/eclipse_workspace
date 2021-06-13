package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * 职位信息
 */
@Entity
@Table(name = "tPosition")
public class Position extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Desc2")
	private String desc2;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "type")
	private String type;
	
	@Column(name = "isSign")
	private String isSign;

	// Constructors

	/** default constructor */
	public Position() {
	}

	/** minimal constructor */
	public Position(String code) {
		this.code = code;
	}

	/** full constructor */
	public Position(String code, String desc1, String desc2,
			Date lastUpdate, String lastUpdatedBy, String expired,
			String actionLog) {
		this.code = code;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc1() {
		return this.desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return this.desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
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

	public String getActionLog() {
		return this.actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
    
}
