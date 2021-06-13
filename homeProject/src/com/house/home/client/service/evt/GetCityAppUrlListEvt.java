package com.house.home.client.service.evt;

public class GetCityAppUrlListEvt extends BaseEvt {
	
	private Double longitude;
	private Double latitude;
	
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
