package com.house.home.entity.oa;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import com.house.framework.commons.orm.BaseEntity;

/**
 * Leave信息
 */
@Entity
@Table(name = "OA_LEAVE")
public class Leave extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "START_TIME")
	private Date startTime;
	@Column(name = "END_TIME")
	private Date endTime;
	@Column(name = "LEAVE_TYPE")
	private String leaveType;
	@Column(name = "REASON")
	private String reason;
	@Column(name = "APPLY_TIME")
	private Date applyTime;
	@Column(name = "REALITY_START_TIME")
	private Date realityStartTime;
	@Column(name = "REALITY_END_TIME")
	private Date realityEndTime;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "DAY_NUM")
	private Double dayNum;
	@Column(name = "FINISH_TIME")
	private Date finishTime;
	
	// 流程任务
	@Transient
	private Task task;
	@Transient
	private Map<String, Object> variables;
	// 运行中的流程实例
	@Transient
	private ProcessInstance processInstance;
	// 历史的流程实例
	@Transient
	private HistoricProcessInstance historicProcessInstance;
	// 流程定义
	@Transient
	private ProcessDefinition processDefinition;
	@Transient
	private String userName;
	@Transient
	private String leaderId;
	@Transient
	private String leaderBackReason;

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	public String getLeaveType() {
		return this.leaveType;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Date getApplyTime() {
		return this.applyTime;
	}
	public void setRealityStartTime(Date realityStartTime) {
		this.realityStartTime = realityStartTime;
	}
	
	public Date getRealityStartTime() {
		return this.realityStartTime;
	}
	public void setRealityEndTime(Date realityEndTime) {
		this.realityEndTime = realityEndTime;
	}
	
	public Date getRealityEndTime() {
		return this.realityEndTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getDayNum() {
		return dayNum;
	}

	public void setDayNum(Double dayNum) {
		this.dayNum = dayNum;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderBackReason() {
		return leaderBackReason;
	}

	public void setLeaderBackReason(String leaderBackReason) {
		this.leaderBackReason = leaderBackReason;
	}

}
