package com.house.home.client.service.resp;

import java.util.Date;

public class SignInQueryResp extends BaseResponse {

	private String custCode;
	private String signCzy;
	private Date crtDate;
	private Double longitude;
	private Double latitude;
	private String address;
	private String custAddress;
	private String signInType2; //服务类型
	private String signInType2Descr;
	private String errPosition;
	private String signCzyDescr;
	private String prjItemDescr;
	
	public String getSignCzyDescr() {
		return signCzyDescr;
	}
	public void setSignCzyDescr(String signCzyDescr) {
		this.signCzyDescr = signCzyDescr;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
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
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getSignInType2() {
		return signInType2;
	}
	public void setSignInType2(String signInType2) {
		this.signInType2 = signInType2;
	}
	public String getSignInType2Descr() {
		return signInType2Descr;
	}
	public void setSignInType2Descr(String signInType2Descr) {
		this.signInType2Descr = signInType2Descr;
	}
	public String getErrPosition() {
		return errPosition;
	}
	public void setErrPosition(String errPosition) {
		this.errPosition = errPosition;
	}
	
}
