package com.house.home.client.service.evt;


public class QualityCheckBoardEvt extends BaseQueryEvt{

	private String workType12;
	private String workType12Dept;
	private String workerClassify;
	private String sourceType;
	
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getWorkType12Dept() {
		return workType12Dept;
	}
	public void setWorkType12Dept(String workType12Dept) {
		this.workType12Dept = workType12Dept;
	}
	public String getWorkerClassify() {
		return workerClassify;
	}
	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	
}
