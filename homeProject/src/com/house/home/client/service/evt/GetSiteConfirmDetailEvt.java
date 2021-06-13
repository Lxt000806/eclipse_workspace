package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 *功能说明:获取工地验收详情
 *
 */
public class GetSiteConfirmDetailEvt extends BaseEvt{

	@NotEmpty(message="ID不能为空")
	public String id;

	private Integer custWkPk;
	private String platform;

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
