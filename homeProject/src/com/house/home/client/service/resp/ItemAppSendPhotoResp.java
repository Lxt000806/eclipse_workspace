package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class ItemAppSendPhotoResp extends BaseResponse {
	private List<Map<String,Object>> photoList;

	public List<Map<String, Object>> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Map<String, Object>> photoList) {
		this.photoList = photoList;
	}




}
