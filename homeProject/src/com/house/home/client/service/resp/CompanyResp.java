package com.house.home.client.service.resp;
/**
 * 公司resp
 * @author created by zb
 * @date   2019-5-20--下午3:40:02
 */
public class CompanyResp extends BaseResponse {
	
	private String code;
	private String desc1;
	private String desc2;
	private String cmpnyName;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getCmpnyName() {
		return cmpnyName;
	}
	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}
	
}
