package com.house.home.client.service.evt;

public class WarehouseSendEvt extends BaseQueryEvt {
	private String address;
	private String itemType1;
	private String iaNo; //领料单号
	private String desc1; //仓库名称
	private String no; //送货批次号
	private String driverCodeDescr; //司机
	private String whcode; //仓库编号

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getIaNo() {
		return iaNo;
	}

	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDriverCodeDescr() {
		return driverCodeDescr;
	}

	public void setDriverCodeDescr(String driverCodeDescr) {
		this.driverCodeDescr = driverCodeDescr;
	}

	public String getWhcode() {
		return whcode;
	}

	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

}
