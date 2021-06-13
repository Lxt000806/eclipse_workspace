package com.house.home.client.service.resp;

public class PrjProDateResp extends BaseResponse {
	private Integer delay;
	private Integer remainDate;
	private Integer progressDate;
	private Integer constructDate;
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Integer getRemainDate() {
		return remainDate;
	}
	public void setRemainDate(Integer remainDate) {
		this.remainDate = remainDate;
	}
	public Integer getProgressDate() {
		return progressDate;
	}
	public void setProgressDate(Integer progressDate) {
		this.progressDate = progressDate;
	}
	public Integer getConstructDate() {
		return constructDate;
	}
	public void setConstructDate(Integer constructDate) {
		this.constructDate = constructDate;
	}
	
}
