package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * SignIn信息bean
 */
public class SignInBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="signCzy", order=3)
	private String signCzy;
    	@ExcelAnnotation(exportName="crtDate", pattern="yyyy-MM-dd HH:mm:ss", order=4)
	private Date crtDate;
	@ExcelAnnotation(exportName="longitude", order=5)
	private Double longitude;
	@ExcelAnnotation(exportName="latitude", order=6)
	private Double latitude;
	@ExcelAnnotation(exportName="address", order=7)
	private String address;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setSignCzy(String signCzy) {
		this.signCzy = signCzy;
	}
	
	public String getSignCzy() {
		return this.signCzy;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	
	public Date getCrtDate() {
		return this.crtDate;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}

}
