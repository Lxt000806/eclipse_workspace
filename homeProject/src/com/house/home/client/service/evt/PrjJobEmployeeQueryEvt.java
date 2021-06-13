package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjJobEmployeeQueryEvt extends BaseQueryEvt{
	
	@NotEmpty(message="项目类型编号不能为空")
	private String code;
	private String nameChi;
	private String projectMan;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	
}
