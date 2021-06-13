package com.house.home.client.service.evt;

public class DoCompleteWfProcInstEvt extends BaseQueryEvt{
	
	private String taskId;
	private String comment;
	private String wfProcInstNo;
	private String processInstId;
	private String returnNode;
	private String detailJson;
	private String photoUrlList;
	private Double confAmount;
	
	public Double getConfAmount() {
		return confAmount;
	}
	public void setConfAmount(Double confAmount) {
		this.confAmount = confAmount;
	}
	public String getPhotoUrlList() {
		return photoUrlList;
	}
	public void setPhotoUrlList(String photoUrlList) {
		this.photoUrlList = photoUrlList;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getReturnNode() {
		return returnNode;
	}
	public void setReturnNode(String returnNode) {
		this.returnNode = returnNode;
	}
	public String getProcessInstId() {
		return processInstId;
	}
	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}
	public String getWfProcInstNo() {
		return wfProcInstNo;
	}
	public void setWfProcInstNo(String wfProcInstNo) {
		this.wfProcInstNo = wfProcInstNo;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
