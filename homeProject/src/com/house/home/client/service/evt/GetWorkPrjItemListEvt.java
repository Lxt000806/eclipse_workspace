package com.house.home.client.service.evt;

/**
 * 
 *功能说明:获取工人节点列表
 *
 */
public class GetWorkPrjItemListEvt extends BaseEvt{
	
	private Integer custWkPk;
	private String SourceType;

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getSourceType() {
		return SourceType;
	}

	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}
	
}
