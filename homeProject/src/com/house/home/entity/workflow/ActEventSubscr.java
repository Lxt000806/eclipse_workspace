package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActEventSubscr信息
 */
@Entity
@Table(name = "ACT_RU_EVENT_SUBSCR")
public class ActEventSubscr extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "EVENT_TYPE_")
	private String eventType;
	@Column(name = "EVENT_NAME_")
	private String eventName;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "ACTIVITY_ID_")
	private String activityId;
	@Column(name = "CONFIGURATION_")
	private String configuration;
	@Column(name = "CREATED_")
	private Date created;

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
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getEventType() {
		return this.eventType;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getActivityId() {
		return this.activityId;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	public String getConfiguration() {
		return this.configuration;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return this.created;
	}

}
