package com.house.home.client.service.resp;

import java.util.List;

public class ItemPreAppPhotoResp extends BaseResponse{
	private List<String> photoList;
	private List<String> photoNameList;

	public List<String> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public List<String> getPhotoNameList() {
		return photoNameList;
	}

	public void setPhotoNameList(List<String> photoNameList) {
		this.photoNameList = photoNameList;
	}
	

}
