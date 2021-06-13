package com.house.home.entity.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ActVarinst信息
 */
@Entity
@Table(name = "ACT_HI_VARINST")
public class ActVarinst extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "ID_")
	private String id;
	@Column(name = "PROC_INST_ID_")
	private String procInstId;
	@Column(name = "EXECUTION_ID_")
	private String executionId;
	@Column(name = "TASK_ID_")
	private String taskId;
	@Column(name = "NAME_")
	private String name;
	@Column(name = "VAR_TYPE_")
	private String varType;
	@Column(name = "REV_")
	private Integer rev;
	@Column(name = "BYTEARRAY_ID_")
	private String bytearrayId;
	@Column(name = "DOUBLE_")
	private Double double_;
	@Column(name = "LONG_")
	private Double long_;
	@Column(name = "TEXT_")
	private String text;
	@Column(name = "TEXT2_")
	private String text2;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public String getProcInstId() {
		return this.procInstId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	public String getExecutionId() {
		return this.executionId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setVarType(String varType) {
		this.varType = varType;
	}
	
	public String getVarType() {
		return this.varType;
	}
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	public Integer getRev() {
		return this.rev;
	}
	public void setBytearrayId(String bytearrayId) {
		this.bytearrayId = bytearrayId;
	}
	
	public String getBytearrayId() {
		return this.bytearrayId;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	public String getText2() {
		return this.text2;
	}

	public Double getDouble_() {
		return double_;
	}

	public void setDouble_(Double double_) {
		this.double_ = double_;
	}

	public Double getLong_() {
		return long_;
	}

	public void setLong_(Double long_) {
		this.long_ = long_;
	}

}
