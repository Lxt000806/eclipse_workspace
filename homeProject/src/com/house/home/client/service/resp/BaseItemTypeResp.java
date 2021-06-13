package com.house.home.client.service.resp;

/**
 * 基装类型12——app
 * @author created by zb
 * @date   2019-2-28--下午5:55:08
 */
public class BaseItemTypeResp extends BaseResponse {
	private String code; //类型1
	private String descr;
	private String code2; //类型2
	private String code2Descr;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCode2() {
		return code2;
	}
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	public String getCode2Descr() {
		return code2Descr;
	}
	public void setCode2Descr(String code2Descr) {
		this.code2Descr = code2Descr;
	}

}
