package com.house.home.client.service.resp;
/**
 * 签到地点resp
 * @author created by zb
 * @date   2019-5-20--下午3:40:02
 */
public class SignPlaceResp extends BaseResponse {
	
	private int pk;
	private String descr;
	private String cmpCode;
	private Double longitudetppc;
	private Double latitudetppc;
	private Double limitDistance;
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
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
	public Double getLimitDistance() {
		return limitDistance;
	}
	public void setLimitDistance(Double limitDistance) {
		this.limitDistance = limitDistance;
	}
	
}
