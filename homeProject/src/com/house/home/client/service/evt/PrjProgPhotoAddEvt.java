package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjProgPhotoAddEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="项目编号不能为空")
	private String prjItem;
	@NotEmpty(message="相片文件名不能为空")
	private String photoName;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	

}
