package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActIdentityLink信息
 */
@Entity
@Table(name = "ACT_RU_IDENTITYLINK")
public class ActIdentityLink extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "GROUP_ID_")
	private String groupId;
	@Column(name = "TYPE_")
	private String type;
	@Column(name = "USER_ID_")
	private String userId;
	@Column(name = "TASK_ID_")
	private String taskId;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "PROC_DEF_ID_")
	private String procDefId;

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
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupId() {
		return this.groupId;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId() {
		return this.procDefId;
	}

}
