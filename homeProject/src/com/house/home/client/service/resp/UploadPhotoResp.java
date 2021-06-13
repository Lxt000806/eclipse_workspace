package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class UploadPhotoResp extends BaseResponse{
	
	private List<String> photoPathList;
	private List<String> photoNameList;
	private List<Map<String, String>> photoList;
	private List<Map<String, Object>> respPhotoList;
	
	public List<Map<String, Object>> getRespPhotoList() {
		return respPhotoList;
	}
	public void setRespPhotoList(List<Map<String, Object>> respPhotoList) {
		this.respPhotoList = respPhotoList;
	}
	public List<String> getPhotoPathList() {
		return photoPathList;
	}
	public void setPhotoPathList(List<String> photoPathList) {
		this.photoPathList = photoPathList;
	}
	public List<String> getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(List<String> photoNameList) {
		this.photoNameList = photoNameList;
	}
	public List<Map<String, String>> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<Map<String, String>> photoList) {
		this.photoList = photoList;
	}
	
	
	
}
