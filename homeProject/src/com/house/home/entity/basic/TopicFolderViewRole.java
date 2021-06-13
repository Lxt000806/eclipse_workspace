package com.house.home.entity.basic;

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
 * TopicFolderViewRole信息
 */
@Entity
@Table(name = "tTopicFolderViewRole")
public class TopicFolderViewRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "FolderPK")
	private Integer folderPk;
	@Column(name = "RolePK")
	private Integer rolePk;
	@Column(name = "AuthDate")
	private Date authDate;
	@Column(name = "AuthCZY")
	private String authCzy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Transient
	private String unSelected;
	@Transient
	private String selected;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setFolderPk(Integer folderPk) {
		this.folderPk = folderPk;
	}
	
	public Integer getFolderPk() {
		return this.folderPk;
	}
	public void setRolePk(Integer rolePk) {
		this.rolePk = rolePk;
	}
	
	public Integer getRolePk() {
		return this.rolePk;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	
	public Date getAuthDate() {
		return this.authDate;
	}
	public void setAuthCzy(String authCzy) {
		this.authCzy = authCzy;
	}
	
	public String getAuthCzy() {
		return this.authCzy;
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

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	

}
