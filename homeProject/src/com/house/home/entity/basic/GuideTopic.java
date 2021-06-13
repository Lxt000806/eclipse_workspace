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
@Entity
@Table(name = "tGuideTopic")
public class GuideTopic extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "Topic")
	private String topic;
	@Column(name = "Content")
	private String content;
	@Column(name = "FolderPK")
	private Integer folderPK;
	@Column(name = "KeyWords")
	private String keyWords;
	@Column(name = "VisitCnt")
	private Integer visitCnt;
	@Column(name = "CreateDate")
	private Date createDate;
	@Column(name = "CreateCZY")
	private String createCZY;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String dateStr;
	@Transient
	private String czybh;
	@Transient
	private String subdirectory;
	@Transient
	private String authNode;
	@Transient
	private String dateType;
	@Transient
	private String queryCondition;
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	public String getAuthNode() {
		return authNode;
	}
	public void setAuthNode(String authNode) {
		this.authNode = authNode;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getSubdirectory() {
		return subdirectory;
	}
	public void setSubdirectory(String subdirectory) {
		this.subdirectory = subdirectory;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getFolderPK() {
		return folderPK;
	}
	public void setFolderPK(Integer folderPK) {
		this.folderPK = folderPK;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public Integer getVisitCnt() {
		return visitCnt;
	}
	public void setVisitCnt(Integer visitCnt) {
		this.visitCnt = visitCnt;
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
