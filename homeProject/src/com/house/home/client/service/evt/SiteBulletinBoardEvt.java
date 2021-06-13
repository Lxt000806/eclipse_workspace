package com.house.home.client.service.evt;

public class SiteBulletinBoardEvt extends BaseQueryEvt {
	
	private String countType;
	private String department2;
	private String number;
	private String prjProblemType;
	private String pageName;
	private String workerClassify;
	private String stage;
	private String dayRange;
	private String dayRangeArr;
	private String dayRangeCpl;
	private String custType;

	
	public String getDayRangeArr() {
		return dayRangeArr;
	}

	public void setDayRangeArr(String dayRangeArr) {
		this.dayRangeArr = dayRangeArr;
	}

	public String getDayRangeCpl() {
		return dayRangeCpl;
	}

	public void setDayRangeCpl(String dayRangeCpl) {
		this.dayRangeCpl = dayRangeCpl;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDayRange() {
		return dayRange;
	}

	public void setDayRange(String dayRange) {
		this.dayRange = dayRange;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPrjProblemType() {
		return prjProblemType;
	}

	public void setPrjProblemType(String prjProblemType) {
		this.prjProblemType = prjProblemType;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}
	
	
}
