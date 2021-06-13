package com.house.home.client.service.evt;

/**
 * 签到地点Evt
 * @author created by zb
 * @date   2019-5-20--下午3:35:22
 */
public class SignPlaceEvt extends BaseEvt {
	
	private String cmpCode;
	private Double longitudetppc;
	private Double latitudetppc;

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public Double getLongitudetppc() {
		return longitudetppc;
	}

	public void setLongitudetppc(Double longitudetppc) {
		this.longitudetppc = longitudetppc;
	}

	public Double getLatitudetppc() {
		return latitudetppc;
	}

	public void setLatitudetppc(Double latitudetppc) {
		this.latitudetppc = latitudetppc;
	}


}
