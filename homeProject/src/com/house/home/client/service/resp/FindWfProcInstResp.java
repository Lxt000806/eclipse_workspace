package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class FindWfProcInstResp {
	
	private String title;
	private String summary;
	private String taskName;
	private String taskId;
	private String actProcInstId;
	@JSONField (format="yyyy-MM-dd")
	private Date startTime;
	private String procDefName;
	private String procKey;
	private String wfProcNo;
	private String procId;
	private String no;
	private String statusDescr;
	private String isEnd;
	@JSONField (format="yyyy-MM-dd")
	private Date orderTime;
	private String rcvStatus;
	private String groupCode;
	
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActProcInstId() {
		return actProcInstId;
	}
	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getProcDefName() {
		return procDefName;
	}
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	public String getWfProcNo() {
		return wfProcNo;
	}
	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getRcvStatus() {
		return rcvStatus;
	}
	public void setRcvStatus(String rcvStatus) {
		this.rcvStatus = rcvStatus;
	}
	
}
