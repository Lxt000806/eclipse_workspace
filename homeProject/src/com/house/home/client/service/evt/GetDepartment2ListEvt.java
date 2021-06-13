package com.house.home.client.service.evt;

public class GetDepartment2ListEvt extends BaseQueryEvt {
    private String desc1;
    private String depType;
    private String selfDept;

    
    
	public String getSelfDept() {
		return selfDept;
	}

	public void setSelfDept(String selfDept) {
		this.selfDept = selfDept;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}

}
