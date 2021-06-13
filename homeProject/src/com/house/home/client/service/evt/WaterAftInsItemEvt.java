package com.house.home.client.service.evt;

public class WaterAftInsItemEvt extends BaseQueryEvt{
	
	private String no;
	private String descr;
	private String custCode;
	private String workerCode;
	private String waterAftInsItemAppData;
	
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getWaterAftInsItemAppData() {
		return waterAftInsItemAppData;
	}

	public void setWaterAftInsItemAppData(String waterAftInsItemAppData) {
		this.waterAftInsItemAppData = waterAftInsItemAppData;
	}
	
	
}
