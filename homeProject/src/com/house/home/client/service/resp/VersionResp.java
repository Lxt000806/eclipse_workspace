package com.house.home.client.service.resp;

public class VersionResp extends BaseResponse{
	
	private Boolean existNew;
	
	private String downLoadUrl;
	
	private String downLoadRemark;
	
	private Boolean isForce;
	
	private String versionNo;
	private Boolean hasNotRead;
	public Boolean getExistNew() {
		return existNew;
	}

	public void setExistNew(Boolean existNew) {
		this.existNew = existNew;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

	public String getDownLoadRemark() {
		return downLoadRemark;
	}

	public void setDownLoadRemark(String downLoadRemark) {
		this.downLoadRemark = downLoadRemark;
	}

	public Boolean getIsForce() {
		return isForce;
	}

	public void setIsForce(Boolean isForce) {
		this.isForce = isForce;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Boolean getHasNotRead() {
		return hasNotRead;
	}

	public void setHasNotRead(Boolean hasNotRead) {
		this.hasNotRead = hasNotRead;
	}
	
}
