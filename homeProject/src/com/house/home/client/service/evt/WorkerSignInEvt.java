package com.house.home.client.service.evt;


public class WorkerSignInEvt extends BaseEvt{
	private String custCode;
	private String workerCode;
	private String prjItem2;
	private String address;
	private Double longitude;
	private Double latitude;
	private int custWkPk;
	private String status;
	private String photoNameList;
	private Double cupDownHigh;
	private Double cupUpHigh;
	private String photoCodeList;
	private String isLeaveProblem;
	private String leaveProblemRemark;
	private Double toiletQty; //马桶数量
	private Double cabinetQty; //浴室柜数量
	private Double waterArea;
	
	public String getIsLeaveProblem() {
		return isLeaveProblem;
	}
	public void setIsLeaveProblem(String isLeaveProblem) {
		this.isLeaveProblem = isLeaveProblem;
	}

	public String getLeaveProblemRemark() {
		return leaveProblemRemark;
	}
	public void setLeaveProblemRemark(String leaveProblemRemark) {
		this.leaveProblemRemark = leaveProblemRemark;
	}
	public String getPhotoCodeList() {
		return photoCodeList;
	}
	public void setPhotoCodeList(String photoCodeList) {
		this.photoCodeList = photoCodeList;
	}
	public Double getCupDownHigh() {
		return cupDownHigh;
	}
	public void setCupDownHigh(Double cupDownHigh) {
		this.cupDownHigh = cupDownHigh;
	}
	public Double getCupUpHigh() {
		return cupUpHigh;
	}
	public void setCupUpHigh(Double cupUpHigh) {
		this.cupUpHigh = cupUpHigh;
	}
	public String getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(String photoNameList) {
		this.photoNameList = photoNameList;
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
	public String getPrjItem2() {
		return prjItem2;
	}
	public void setPrjItem2(String prjItem2) {
		this.prjItem2 = prjItem2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public int getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(int custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getToiletQty() {
		return toiletQty;
	}
	public void setToiletQty(Double toiletQty) {
		this.toiletQty = toiletQty;
	}
	public Double getCabinetQty() {
		return cabinetQty;
	}
	public void setCabinetQty(Double cabinetQty) {
		this.cabinetQty = cabinetQty;
	}
	public Double getWaterArea() {
		return waterArea;
	}
	public void setWaterArea(Double waterArea) {
		this.waterArea = waterArea;
	}
	
}
