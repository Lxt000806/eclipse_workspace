package com.house.home.client.service.evt;

public class GoJqGridForOAEvt extends BaseEvt{
	
	private String address;
	private String status;
	private String empCode; //操作员编号
	private String authorityCtrl;
	private String designMan;
	private String codes;
	private String endCode; //结束代码 add by zb on 20200319
	private String isAddAllInfo;
	
	
	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}

	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}

	public String getAuthorityCtrl() {
		return authorityCtrl;
	}

	public void setAuthorityCtrl(String authorityCtrl) {
		this.authorityCtrl = authorityCtrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	
}
