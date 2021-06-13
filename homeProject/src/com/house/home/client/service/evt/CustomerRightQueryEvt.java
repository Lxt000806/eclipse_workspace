package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class CustomerRightQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="操作员编号不能为空")
	private String czybh;
	private String address;
	private String stakeholder;
	private String haveGd;//包含归档客户（默认不包含）
	private Double distance;
    private Double lontitude;
    private Double latitude;
    private String prjItem;
    private String saleIndependence;
    private String stakeholderRoll;
    private String department2Descr;
    private String pageOrderBy; //指定排序 add by zb on 20190619
    private String workType12;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(String stakeholder) {
		this.stakeholder = stakeholder;
	}

	public String getHaveGd() {
		return haveGd;
	}

	public void setHaveGd(String haveGd) {
		this.haveGd = haveGd;
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

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getSaleIndependence() {
		return saleIndependence;
	}

	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}

	public String getStakeholderRoll() {
		return stakeholderRoll;
	}

	public void setStakeholderRoll(String stakeholderRoll) {
		this.stakeholderRoll = stakeholderRoll;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getPageOrderBy() {
		return pageOrderBy;
	}

	public void setPageOrderBy(String pageOrderBy) {
		this.pageOrderBy = pageOrderBy;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
}
