package com.house.home.client.service.resp;

import java.util.Date;

public class WaterMarkInfoResp extends BaseResponse{
	private String prjCheckCzyDescr;
	private String projectManDescr;
	private Date takePhotoDate;
	private String builderDescr;
	
	public String getPrjCheckCzyDescr() {
		return prjCheckCzyDescr;
	}
	public void setPrjCheckCzyDescr(String prjCheckCzyDescr) {
		this.prjCheckCzyDescr = prjCheckCzyDescr;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public Date getTakePhotoDate() {
		return takePhotoDate;
	}
	public void setTakePhotoDate(Date takePhotoDate) {
		this.takePhotoDate = takePhotoDate;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}

	
}
