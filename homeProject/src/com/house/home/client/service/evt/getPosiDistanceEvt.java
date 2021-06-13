package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;



public class getPosiDistanceEvt extends BaseEvt {
	@NotNull(message="初始纬度不能为空")
	private Double startLat;
	@NotNull(message="初始经度不能为空")
	private Double startLng;
	@NotNull(message="结束纬度不能为空")
	private Double endLat;
	@NotNull(message="结束经度不能为空")
	private Double endLng;
	public Double getStartLat() {
		return startLat;
	}
	public void setStartLat(Double startLat) {
		this.startLat = startLat;
	}
	public Double getStartLng() {
		return startLng;
	}
	public void setStartLng(Double startLng) {
		this.startLng = startLng;
	}
	public Double getEndLat() {
		return endLat;
	}
	public void setEndLat(Double endLat) {
		this.endLat = endLat;
	}
	public Double getEndLng() {
		return endLng;
	}
	public void setEndLng(Double endLng) {
		this.endLng = endLng;
	}
	
	
}
