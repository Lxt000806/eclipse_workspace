package com.house.home.client.service.resp;

public class PrjProgAlarmResp extends BaseResponse{
	
	private Integer pk;
	private String tempNo;
	private String prjItem;
	private String dayType;
	private String dayTypeDescr;
	private Integer addDay;
	private String msgTextTemp;
	private String address;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDayType() {
		return dayType;
	}
	public void setDayType(String dayType) {
		this.dayType = dayType;
	}
	public Integer getAddDay() {
		return addDay;
	}
	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
	}
	public String getMsgTextTemp() {
		return msgTextTemp;
	}
	public void setMsgTextTemp(String msgTextTemp) {
		this.msgTextTemp = msgTextTemp;
	}
	public String getDayTypeDescr() {
		return dayTypeDescr;
	}
	public void setDayTypeDescr(String dayTypeDescr) {
		this.dayTypeDescr = dayTypeDescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	
}
