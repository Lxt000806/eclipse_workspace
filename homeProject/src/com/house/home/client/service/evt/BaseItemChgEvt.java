package com.house.home.client.service.evt;

/**
 * 基装增减接收bean
 * @author created by zb
 * @date   2019-2-25--下午5:03:59
 */
public class BaseItemChgEvt extends BaseQueryEvt {
	private String no;
    private String address;
    private String status;
    private String projectMan;
    private String custCode;
    private String custTypeType;
    private String prjStatus; //项目经理提交状态
    
    private String unSelected;//不显示已添加项
    private String showOutSet;//1：显示套餐内项目
    private Integer fixAreaPk;//区域pk
    private String baseItemDescr;//项目名称
    private String appCzyDescr;//申请操作员名字
	private String confirmCzyDescr;//审核人名字
    
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

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustTypeType() {
		return custTypeType;
	}

	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}

	public String getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getShowOutSet() {
		return showOutSet;
	}

	public void setShowOutSet(String showOutSet) {
		this.showOutSet = showOutSet;
	}

	public Integer getFixAreaPk() {
		return fixAreaPk;
	}

	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}

	public String getBaseItemDescr() {
		return baseItemDescr;
	}

	public void setBaseItemDescr(String baseItemDescr) {
		this.baseItemDescr = baseItemDescr;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	
}
