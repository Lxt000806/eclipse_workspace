package com.house.home.entity.query;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * tWorkSignPic信息
 */
@Entity
@Table(name = "tWorkSignPic")
public class WorkSignPic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	
	@Column(name = "No")
	private String no;
	
	@Column(name = "PhotoName")
	private String photoName;
	
	@Column(name = "LastUpdateBy")
	private String lastUpdateBy;
	
	@Column(name = "Expired")
	private String expired;
	
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Column(name = "LastUpdate")
	private Date lastUpdate;

	@Column(name = "IsSendYun")
	private String isSendYun;
	
	@Column(name = "SendDate")
	private Date sendDate;
	
	@Column(name = "IsPushCust")
	private String isPushCust;
	
	@Column(name = "TechCode")
	private String techCode;

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

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
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

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getIsSendYun() {
		return isSendYun;
	}

	public void setIsSendYun(String isSendYun) {
		this.isSendYun = isSendYun;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsPushCust() {
		return isPushCust;
	}

	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}

	public String getTechCode() {
		return techCode;
	}

	public void setTechCode(String techCode) {
		this.techCode = techCode;
	}
	
	
}
