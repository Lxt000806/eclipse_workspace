package com.house.home.client.service.evt;

public class CustomerQueryEvt extends BaseQueryEvt{
	
	private String address;
	private String status;
	private String czybh;
	private String type;
	private String constructStatus;
	private String prjItem;
	private String prjRole;
	private String projectManDescr;
	private Double distance;
    private Double lontitude;
    private Double latitude;
    private String regionCode;
    private String saleIndependence;
    private String isLeaveProblem;
    private String excludeComplete; //不包含完工
    
    
    
	public String getExcludeComplete() {
		return excludeComplete;
	}

	public void setExcludeComplete(String excludeComplete) {
		this.excludeComplete = excludeComplete;
	}

	public String getIsLeaveProblem() {
		return isLeaveProblem;
	}

	public void setIsLeaveProblem(String isLeaveProblem) {
		this.isLeaveProblem = isLeaveProblem;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getLontitude() {
		return lontitude;
	}

	public void setLontitude(Double lontitude) {
		this.lontitude = lontitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getProjectManDescr() {
		return projectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConstructStatus() {
		return constructStatus;
	}

	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getPrjRole() {
		return prjRole;
	}

	public void setPrjRole(String prjRole) {
		this.prjRole = prjRole;
	}

	public String getSaleIndependence() {
		return saleIndependence;
	}

	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}

}
