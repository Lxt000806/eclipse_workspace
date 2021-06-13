package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class UploadCustCallRecordResp extends BaseResponse{
	
	private List<String> recordPathList;
	private List<String> recordNameList;
	private List<Map<String, String>> recordList;
	public List<String> getRecordPathList() {
		return recordPathList;
	}
	public void setRecordPathList(List<String> recordPathList) {
		this.recordPathList = recordPathList;
	}
	public List<String> getRecordNameList() {
		return recordNameList;
	}
	public void setRecordNameList(List<String> recordNameList) {
		this.recordNameList = recordNameList;
	}
	public List<Map<String, String>> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<Map<String, String>> recordList) {
		this.recordList = recordList;
	}
	
}
