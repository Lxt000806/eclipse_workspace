package com.house.home.client.service.evt;

import org.hibernate.validator.constraints.NotEmpty;

public class SaveSiteModifyEvt extends BaseEvt {
	@NotEmpty(message="编号不能为空")
	private String no;
	private String compRemark;
	private String photoNameList;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCompRemark() {
		return compRemark;
	}
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	public String getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(String photoNameList) {
		this.photoNameList = photoNameList;
	}
	
}
