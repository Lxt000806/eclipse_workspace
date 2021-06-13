package com.house.framework.web.login;

import java.io.Serializable;
import java.util.List;

public class UserContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private String czybh;
	private String zwxm;
	private String emnum;
	private String phone;
	private String czylb;
	private String custRight;
	private String costRight;
	private String itemRight;
	private String projectCostRight;
	private String ip;
	private String cityCode;
	private String jslx; //操作员类型
	//开始查询时间
	private String beginTime;
	//结束查询时间
	private String endTime;
	//是否是超级管理员
	private boolean isSuperAdmin;
	//是否是一级管理员
	private boolean isOneAdmin;
	//是否是二级管理员
	private boolean isTwoAdmin;

	//禁止访问的类
	private List<String> forbidUrls;
	//权限编码集合
	private String authStr;
	
	//app类型:1 客户app; 2 项目经理app; 3 易居通app
	private String appType;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}
	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public String getAuthStr() {
		return authStr;
	}
	public void setAuthStr(String authStr) {
		this.authStr = authStr;
	}
	public List<String> getForbidUrls() {
		return forbidUrls;
	}
	public void setForbidUrls(List<String> forbidUrls) {
		this.forbidUrls = forbidUrls;
	}
	public boolean isOneAdmin() {
		return isOneAdmin;
	}
	public void setOneAdmin(boolean isOneAdmin) {
		this.isOneAdmin = isOneAdmin;
	}
	public boolean isTwoAdmin() {
		return isTwoAdmin;
	}
	public void setTwoAdmin(boolean isTwoAdmin) {
		this.isTwoAdmin = isTwoAdmin;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getZwxm() {
		return zwxm;
	}
	public void setZwxm(String zwxm) {
		this.zwxm = zwxm;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getEmnum() {
		return emnum;
	}
	public void setEmnum(String emnum) {
		this.emnum = emnum;
	}
	public String getCustRight() {
		return custRight;
	}
	public void setCustRight(String custRight) {
		this.custRight = custRight;
	}
	public String getCostRight() {
		return costRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	public String getItemRight() {
		return itemRight;
	}
	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCzylb() {
		return czylb;
	}
	public void setCzylb(String czylb) {
		this.czylb = czylb;
	}
	public String getProjectCostRight() {
		return projectCostRight;
	}
	public void setProjectCostRight(String projectCostRight) {
		this.projectCostRight = projectCostRight;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getJslx() {
		return jslx;
	}
	public void setJslx(String jslx) {
		this.jslx = jslx;
	}
	
	
}
