package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class PositionEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotNull(message="经度不能为空")
	private Double longitude;
	@NotNull(message="纬度不能为空")
	private Double latitude;

	
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
}
