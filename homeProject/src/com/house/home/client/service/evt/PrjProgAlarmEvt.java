package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjProgAlarmEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String code;
	@NotEmpty(message="项目编号不能为空")
	private String prjItem;
	@NotEmpty(message="提醒日期类型不能为空")
	private String dayType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

}
