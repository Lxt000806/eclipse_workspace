package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class VersionEvt extends BaseEvt{
	
	@NotEmpty(message="版本号不能为空")
	private String versionNo;
	private String name;
	private String autoCheckFlag;
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

	public String getAutoCheckFlag() {
		return autoCheckFlag;
	}

	public void setAutoCheckFlag(String autoCheckFlag) {
		this.autoCheckFlag = autoCheckFlag;
	}

	

	

	

	

}
