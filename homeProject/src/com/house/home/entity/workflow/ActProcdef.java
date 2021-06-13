package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ActProcdef信息
 */
@Entity
@Table(name = "ACT_RE_PROCDEF")
public class ActProcdef extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "CATEGORY_")
	private String category;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "KEY_")
	private String key;
	@Column(name = "VERSION_")
	private Integer version;
	@Column(name = "DEPLOYMENT_ID_")
	private String deploymentId;
	@Column(name = "RESOURCE_NAME_")
	private String resourceName;
	@Column(name = "DGRM_RESOURCE_NAME_")
	private String dgrmResourceName;
	@Column(name = "DESCRIPTION_")
	private String description;
	@Column(name = "HAS_START_FORM_KEY_")
	private Boolean hasStartFormKey;
	@Column(name = "SUSPENSION_STATE_")
	private Integer suspensionState;
	
	@Transient
	private Boolean onlyLatestVersion; // 同一个流程标识，只显示最后一个版本

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
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
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
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getVersion() {
		return this.version;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	public String getDeploymentId() {
		return this.deploymentId;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}
	public void setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
	}
	
	public String getDgrmResourceName() {
		return this.dgrmResourceName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setHasStartFormKey(Boolean hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}
	
	public Boolean getHasStartFormKey() {
		return this.hasStartFormKey;
	}
	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}
	
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public Boolean getOnlyLatestVersion() {
		return onlyLatestVersion;
	}

	public void setOnlyLatestVersion(Boolean onlyLatestVersion) {
		this.onlyLatestVersion = onlyLatestVersion;
	}
}
