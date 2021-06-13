package com.house.home.client.service.resp;
/**
 * 二维码resp
 * @author created by zb
 * @date   2019-5-20--下午3:40:02
 */
public class FarbellResp extends BaseResponse {
	
	private String qrCode; //二维码数据
	private Integer durationTime; //过期时间（秒单位）

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Integer getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}

}
