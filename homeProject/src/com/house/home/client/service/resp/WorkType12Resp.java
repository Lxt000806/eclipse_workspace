package com.house.home.client.service.resp;

public class WorkType12Resp extends BaseResponse {
	
	private String code;
	private String descr;
	private String isTechnology; //是否上传图片模板
	
	private Integer minPhotoNum;//最小图片数
	private Integer maxPhotoNum;//最大图片数
	private Integer gotPicNum;//已上传图片数
	private String isPrjApp;
	
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
	public String getIsTechnology() {
		return isTechnology;
	}
	public void setIsTechnology(String isTechnology) {
		this.isTechnology = isTechnology;
	}
	public Integer getMinPhotoNum() {
		return minPhotoNum;
	}
	public void setMinPhotoNum(Integer minPhotoNum) {
		this.minPhotoNum = minPhotoNum;
	}
	public Integer getMaxPhotoNum() {
		return maxPhotoNum;
	}
	public void setMaxPhotoNum(Integer maxPhotoNum) {
		this.maxPhotoNum = maxPhotoNum;
	}
	public Integer getGotPicNum() {
		return gotPicNum;
	}
	public void setGotPicNum(Integer gotPicNum) {
		this.gotPicNum = gotPicNum;
	}
	public String getIsPrjApp() {
		return isPrjApp;
	}
	public void setIsPrjApp(String isPrjApp) {
		this.isPrjApp = isPrjApp;
	}
	
}
