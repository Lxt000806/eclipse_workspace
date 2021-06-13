package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateVersionEvt extends BaseEvt{
	
	@NotEmpty(message="应用名不能为空")
	private String name;
	private String versionNo;
	private String compatible;
	
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}

}
