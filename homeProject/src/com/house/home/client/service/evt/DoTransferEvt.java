package com.house.home.client.service.evt;

public class DoTransferEvt extends BaseEvt{

	private String taskId;
	private String processInstId;
	private String newOperator;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getProcessInstId() {
		return processInstId;
	}
	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}
	public String getNewOperator() {
		return newOperator;
	}
	public void setNewOperator(String newOperator) {
		this.newOperator = newOperator;
	}
	
}
