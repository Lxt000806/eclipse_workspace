package com.house.home.entity.basic;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tDoc")
public class Doc extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "DocName")
	private String docName;
	@Column(name = "DocCode")
	private String docCode;
	@Column(name = "DocVersion")
	private String docVersion;
	@Column(name = "FolderPK")
	private Integer folderPK;
	@Column(name = "EnableDate")
	private Date enableDate;
	@Column(name = "ExpiredDate")
	private Date expiredDate;
	@Column(name = "DrawUpCZY")
	private String drawUpCZY;
	@Column(name = "DrawUpDate")
	private Date drawUpDate;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ApproveCZY")
	private String approveCZY;
	@Column(name = "ApproveDate")
	private Date approveDate;
	@Column(name = "BriefDesc")
	private String briefDesc;
	@Column(name = "KeyWords")
	private String keyWords;
	@Column(name = "IsForRegular")
	private String isForRegular;
	@Column(name = "IsNeedNotice")
	private String isNeedNotice;
	@Column(name = "CreateDate")
	private Date createDate;
	@Column(name = "CreateCZY")
	private String createCZY;
	@Column(name = "Status")
	private String status;
	@Column(name = "FiledDate")
	private Date filedDate;
	@Column(name = "FiledCZY")
	private String filedCZY;
	@Column(name = "DownloadCnt")
	private Integer downloadCnt;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;

	@Transient
	private String subdirectory;
	@Transient
	private String path;
	@Transient
	private String dateStr;
	@Transient
	private String dateType;
	@Transient
	private String queryCondition;
	@Transient
	private String fullNames; // 文档相关文件全名 ','分隔
	@Transient
	private String authNode;
	@Transient
	private String czybh;
	@Transient
	private List<Map<String, Object>> attrDataList;
	@Transient
	private String delAttPk;
	@Transient
	private boolean hasAuthByCzybh;
	
	public boolean isHasAuthByCzybh() {
		return hasAuthByCzybh;
	}
	public void setHasAuthByCzybh(boolean hasAuthByCzybh) {
		this.hasAuthByCzybh = hasAuthByCzybh;
	}
	public String getDelAttPk() {
		return delAttPk;
	}
	public void setDelAttPk(String delAttPk) {
		this.delAttPk = delAttPk;
	}
	public List<Map<String, Object>> getAttrDataList() {
		return attrDataList;
	}
	public void setAttrDataList(List<Map<String, Object>> attrDataList) {
		this.attrDataList = attrDataList;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getAuthNode() {
		return authNode;
	}
	public void setAuthNode(String authNode) {
		this.authNode = authNode;
	}
	public String getFullNames() {
		return fullNames;
	}
	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSubdirectory() {
		return subdirectory;
	}
	public void setSubdirectory(String subdirectory) {
		this.subdirectory = subdirectory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public Integer getFolderPK() {
		return folderPK;
	}
	public void setFolderPK(Integer folderPK) {
		this.folderPK = folderPK;
	}
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getDrawUpCZY() {
		return drawUpCZY;
	}
	public void setDrawUpCZY(String drawUpCZY) {
		this.drawUpCZY = drawUpCZY;
	}
	public Date getDrawUpDate() {
		return drawUpDate;
	}
	public void setDrawUpDate(Date drawUpDate) {
		this.drawUpDate = drawUpDate;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getApproveCZY() {
		return approveCZY;
	}
	public void setApproveCZY(String approveCZY) {
		this.approveCZY = approveCZY;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getBriefDesc() {
		return briefDesc;
	}
	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getIsForRegular() {
		return isForRegular;
	}
	public void setIsForRegular(String isForRegular) {
		this.isForRegular = isForRegular;
	}
	public String getIsNeedNotice() {
		return isNeedNotice;
	}
	public void setIsNeedNotice(String isNeedNotice) {
		this.isNeedNotice = isNeedNotice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateCZY() {
		return createCZY;
	}
	public void setCreateCZY(String createCZY) {
		this.createCZY = createCZY;
	}
	public Date getFiledDate() {
		return filedDate;
	}
	public void setFiledDate(Date filedDate) {
		this.filedDate = filedDate;
	}
	public String getFiledCZY() {
		return filedCZY;
	}
	public void setFiledCZY(String filedCZY) {
		this.filedCZY = filedCZY;
	}
	public Integer getDownloadCnt() {
		return downloadCnt;
	}
	public void setDownloadCnt(Integer downloadCnt) {
		this.downloadCnt = downloadCnt;
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
