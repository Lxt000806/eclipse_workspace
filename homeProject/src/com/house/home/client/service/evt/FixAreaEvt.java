package com.house.home.client.service.evt;

/**
 * 装修区域app接收
 * @author created by zb
 * @date   2019-2-27--下午4:40:10
 */
public class FixAreaEvt extends BaseQueryEvt{
	private String custCode;
	private String itemType1;
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

}
