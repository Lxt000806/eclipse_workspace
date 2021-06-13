package com.house.home.client.service.resp;

public class GetDeviceCallRecordInfoResp extends BaseResponse {
	
	private String callRecordPath;
	private long beginDate;
	public String getCallRecordPath() {
		return callRecordPath;
	}
	public void setCallRecordPath(String callRecordPath) {
		this.callRecordPath = callRecordPath;
	}
	public long getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(long beginDate) {
		this.beginDate = beginDate;
	}
	
}
