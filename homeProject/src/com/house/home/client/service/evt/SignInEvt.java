package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class SignInEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="操作员不能为空")
	private String signCzy;
	@NotNull(message="经度不能为空")
	private Double longitude;
	@NotNull(message="纬度不能为空")
	private Double latitude;
	private String address;
	private String errPosi;
	private String isPass;
	private String remarks;
	private String prjItem;
	
	private String signInType2;
	private String photoString;//多个逗号隔开
	private String techCode; //工艺编号
	private Integer signPlacePK;//公司打卡地点PK
	private String dockRemark;
	private String workType12;
	private Integer infoPk;
	private String drawChange;
	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getSignCzy() {
		return signCzy;
	}
	public void setSignCzy(String signCzy) {
		this.signCzy = signCzy;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	public String getSignInType2() {
		return signInType2;
	}
	public void setSignInType2(String signInType2) {
		this.signInType2 = signInType2;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getTechCode() {
		return techCode;
	}
	public void setTechCode(String techCode) {
		this.techCode = techCode;
	}
	public Integer getSignPlacePK() {
		return signPlacePK;
	}
	public void setSignPlacePK(Integer signPlacePK) {
		this.signPlacePK = signPlacePK;
	}
	public String getDockRemark() {
		return dockRemark;
	}
	public void setDockRemark(String dockRemark) {
		this.dockRemark = dockRemark;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public Integer getInfoPk() {
		return infoPk;
	}
	public void setInfoPk(Integer infoPk) {
		this.infoPk = infoPk;
	}
	public String getDrawChange() {
		return drawChange;
	}
	public void setDrawChange(String drawChange) {
		this.drawChange = drawChange;
	}

}
