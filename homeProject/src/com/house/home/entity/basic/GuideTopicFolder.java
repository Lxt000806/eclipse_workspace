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
 * GuideTopicFolder信息
 */
@Entity
@Table(name = "tGuideTopicFolder")
public class GuideTopicFolder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ParentPK")
	private Integer parentPk;
	@Column(name = "FolderName")
	private String folderName;
	@Column(name = "FolderCode")
	private String folderCode;
	@Column(name = "Path")
	private String path;
	@Column(name = "AdminCZY")
	private String adminCzy;
	@Column(name = "AuthType")
	private String authType;
	@Column(name = "IsAuthWorker")
	private String isAuthWorker;
	@Column(name = "AuthWorkerTypes")
	private String authWorkerTypes;
	@Column(name = "CreateDate")
	private Date createDate;
	@Column(name = "CreateCZY")
	private String createCzy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsAuthPM")
	private String isAuthPM;
	
	@Transient
	private String topicFolderViewRole;
	@Transient
	private String topicFolderViewRoleDescr;
	@Transient
	private String adminCzyDescr;
	@Transient
	private String createCzyDescr;
	@Transient
	private String czybm;
	@Transient
	private boolean hasAuthByCzybh;
	
	
	public String getIsAuthPM() {
		return isAuthPM;
	}

	public void setIsAuthPM(String isAuthPM) {
		this.isAuthPM = isAuthPM;
	}

	public boolean isHasAuthByCzybh() {
		return hasAuthByCzybh;
	}

	public void setHasAuthByCzybh(boolean hasAuthByCzybh) {
		this.hasAuthByCzybh = hasAuthByCzybh;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setParentPk(Integer parentPk) {
		this.parentPk = parentPk;
	}
	
	public Integer getParentPk() {
		return this.parentPk;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getFolderName() {
		return this.folderName;
	}
	public void setFolderCode(String folderCode) {
		this.folderCode = folderCode;
	}
	
	public String getFolderCode() {
		return this.folderCode;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setAdminCzy(String adminCzy) {
		this.adminCzy = adminCzy;
	}
	
	public String getAdminCzy() {
		return this.adminCzy;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	public String getAuthType() {
		return this.authType;
	}
	public void setIsAuthWorker(String isAuthWorker) {
		this.isAuthWorker = isAuthWorker;
	}
	
	public String getIsAuthWorker() {
		return this.isAuthWorker;
	}
	public void setAuthWorkerTypes(String authWorkerTypes) {
		this.authWorkerTypes = authWorkerTypes;
	}
	
	public String getAuthWorkerTypes() {
		return this.authWorkerTypes;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateCzy(String createCzy) {
		this.createCzy = createCzy;
	}
	
	public String getCreateCzy() {
		return this.createCzy;
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

	
	public String getTopicFolderViewRole() {
		return topicFolderViewRole;
	}

	public void setTopicFolderViewRole(String topicFolderViewRole) {
		this.topicFolderViewRole = topicFolderViewRole;
	}

	public String getTopicFolderViewRoleDescr() {
		return topicFolderViewRoleDescr;
	}

	public void setTopicFolderViewRoleDescr(String topicFolderViewRoleDescr) {
		this.topicFolderViewRoleDescr = topicFolderViewRoleDescr;
	}

	public String getAdminCzyDescr() {
		return adminCzyDescr;
	}

	public void setAdminCzyDescr(String adminCzyDescr) {
		this.adminCzyDescr = adminCzyDescr;
	}

	public String getCreateCzyDescr() {
		return createCzyDescr;
	}

	public void setCreateCzyDescr(String createCzyDescr) {
		this.createCzyDescr = createCzyDescr;
	}

	public String getCzybm() {
		return czybm;
	}

	public void setCzybm(String czybm) {
		this.czybm = czybm;
	}
	
	
	
}
