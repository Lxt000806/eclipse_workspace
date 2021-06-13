package com.house.home.entity.workflow;

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActComment信息
 */
@Entity
@Table(name = "ACT_HI_COMMENT")
public class ActComment extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "TYPE_")
	private String type;
	@Column(name = "TIME_")
	private Date time;
	@Column(name = "USER_ID_")
	private String userId;
	@Column(name = "TASK_ID_")
	private String taskId;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "ACTION_")
	private String action;
	@Column(name = "MESSAGE_")
	private String message;
	@Column(name = "FULL_MSG_")
	private Blob fullMsg;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Date getTime() {
		return this.time;
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
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return this.action;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	public void setFullMsg(Blob fullMsg) {
		this.fullMsg = fullMsg;
	}
	
	public Blob getFullMsg() {
		return this.fullMsg;
	}

}
