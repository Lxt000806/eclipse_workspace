package com.house.home.client.service.evt;

/**
 * 
 *功能说明:获取工人节点列表
 *
 */
public class GetSiteConfirmStatusEvt extends BaseEvt{
	
	private Integer custWkPk;
	
	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	
}
