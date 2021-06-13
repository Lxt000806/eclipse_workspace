package com.house.home.client.service.resp;

import java.util.List;

import com.house.home.client.service.bean.DoSaveCallListBean;

public class DoSaveCallListResp extends BaseResponse {

	private List<DoSaveCallListBean> unUploadRecordList;

	public List<DoSaveCallListBean> getUnUploadRecordList() {
		return unUploadRecordList;
	}

	public void setUnUploadRecordList(List<DoSaveCallListBean> unUploadRecordList) {
		this.unUploadRecordList = unUploadRecordList;
	}
	
}
