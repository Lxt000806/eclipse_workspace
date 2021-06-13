package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActModel信息
 */
@Entity
@Table(name = "ACT_RE_MODEL")
public class ActModel extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "KEY_")
	private String key;
	@Column(name = "CATEGORY_")
	private String category;
	@Column(name = "CREATE_TIME_")
	private Date createTime;
	@Column(name = "LAST_UPDATE_TIME_")
	private Date lastUpdateTime;
	@Column(name = "VERSION_")
	private Integer version;
	@Column(name = "META_INFO_")
	private String metaInfo;
	@Column(name = "DEPLOYMENT_ID_")
	private String deploymentId;
	@Column(name = "EDITOR_SOURCE_VALUE_ID_")
	private String editorSourceValueId;
	@Column(name = "EDITOR_SOURCE_EXTRA_VALUE_ID_")
	private String editorSourceExtraValueId;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getVersion() {
		return this.version;
	}
	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}
	
	public String getMetaInfo() {
		return this.metaInfo;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	public String getDeploymentId() {
		return this.deploymentId;
	}
	public void setEditorSourceValueId(String editorSourceValueId) {
		this.editorSourceValueId = editorSourceValueId;
	}
	
	public String getEditorSourceValueId() {
		return this.editorSourceValueId;
	}
	public void setEditorSourceExtraValueId(String editorSourceExtraValueId) {
		this.editorSourceExtraValueId = editorSourceExtraValueId;
	}
	
	public String getEditorSourceExtraValueId() {
		return this.editorSourceExtraValueId;
	}

}
