package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;

public class PrjProgCheckResp extends BaseResponse{
	
	private String no;
	private String custCode;
	private String address;
	private String prjItem;
	private String prjItemDescr;
	private Date date;
	private String mapAddress;
	private String remarks;
	private List<String> photoList;
	private List<String> photoNameList;
	
	private String isModify;
	private String isModifyDescr;
	private String isPushCust;
	private String isPushCustDescr;
	private Integer modifyTime;
	private String modifyCompleteDescr;
	private String compRemark;
	private Date compDate;
	private List<String> modifyPhotoList;
	private List<String> modifyPhotoNameList;
	private String buildStatus;
	private String buildStatusDescr;
	private Integer remainDate;
	private String safeProm;
	private String visualProm;
	private String artProm;
	private String projectManDescr;
	private String safeBuildStatus;
	private String isSafePreparation;
	private String stopBuildStatus;
	
	private Integer toiletNum;
	private Integer bedroomNum;
	private String kitchDoorType;
	private Integer balconyNum;
	private String balconyTitle;
	private String isWood;
	private String isBuildWall;
	private String isCheckCtType;
	private String isCheckDept;
	
	private String isShowToiletNum;
	private String isShowBedroomNum;
	private String isShowKitchDoorType;
	private String isShowBalconyNum;
	private String isShowBalconyTitle;
	private String isShowIsWood;
	private String isShowIsBuildWall;
	private Integer cigaNum;
	
	
	public Integer getCigaNum() {
		return cigaNum;
	}
	public void setCigaNum(Integer cigaNum) {
		this.cigaNum = cigaNum;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<String> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}
	public String getMapAddress() {
		return mapAddress;
	}
	public void setMapAddress(String mapAddress) {
		this.mapAddress = mapAddress;
	}
	public List<String> getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(List<String> photoNameList) {
		this.photoNameList = photoNameList;
	}
	public String getModifyCompleteDescr() {
		return modifyCompleteDescr;
	}
	public void setModifyCompleteDescr(String modifyCompleteDescr) {
		this.modifyCompleteDescr = modifyCompleteDescr;
	}
	public String getCompRemark() {
		return compRemark;
	}
	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}
	public Date getCompDate() {
		return compDate;
	}
	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}
	public List<String> getModifyPhotoList() {
		return modifyPhotoList;
	}
	public void setModifyPhotoList(List<String> modifyPhotoList) {
		this.modifyPhotoList = modifyPhotoList;
	}
	public List<String> getModifyPhotoNameList() {
		return modifyPhotoNameList;
	}
	public void setModifyPhotoNameList(List<String> modifyPhotoNameList) {
		this.modifyPhotoNameList = modifyPhotoNameList;
	}
	public String getIsModify() {
		return isModify;
	}
	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}
	public String getIsModifyDescr() {
		return isModifyDescr;
	}
	public void setIsModifyDescr(String isModifyDescr) {
		this.isModifyDescr = isModifyDescr;
	}
	public Integer getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getBuildStatusDescr() {
		return buildStatusDescr;
	}
	public void setBuildStatusDescr(String buildStatusDescr) {
		this.buildStatusDescr = buildStatusDescr;
	}
	public Integer getRemainDate() {
		return remainDate;
	}
	public void setRemainDate(Integer remainDate) {
		this.remainDate = remainDate;
	}
	public String getSafeProm() {
		return safeProm;
	}
	public void setSafeProm(String safeProm) {
		this.safeProm = safeProm;
	}
	public String getVisualProm() {
		return visualProm;
	}
	public void setVisualProm(String visualProm) {
		this.visualProm = visualProm;
	}
	public String getArtProm() {
		return artProm;
	}
	public void setArtProm(String artProm) {
		this.artProm = artProm;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getSafeBuildStatus() {
		return safeBuildStatus;
	}
	public void setSafeBuildStatus(String safeBuildStatus) {
		this.safeBuildStatus = safeBuildStatus;
	}
	public String getIsSafePreparation() {
		return isSafePreparation;
	}
	public void setIsSafePreparation(String isSafePreparation) {
		this.isSafePreparation = isSafePreparation;
	}
	public String getStopBuildStatus() {
		return stopBuildStatus;
	}
	public void setStopBuildStatus(String stopBuildStatus) {
		this.stopBuildStatus = stopBuildStatus;
	}
	public Integer getToiletNum() {
		return toiletNum;
	}
	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}
	public Integer getBedroomNum() {
		return bedroomNum;
	}
	public void setBedroomNum(Integer bedroomNum) {
		this.bedroomNum = bedroomNum;
	}
	public String getKitchDoorType() {
		return kitchDoorType;
	}
	public void setKitchDoorType(String kitchDoorType) {
		this.kitchDoorType = kitchDoorType;
	}
	public Integer getBalconyNum() {
		return balconyNum;
	}
	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}
	public String getBalconyTitle() {
		return balconyTitle;
	}
	public void setBalconyTitle(String balconyTitle) {
		this.balconyTitle = balconyTitle;
	}
	public String getIsWood() {
		return isWood;
	}
	public void setIsWood(String isWood) {
		this.isWood = isWood;
	}
	public String getIsBuildWall() {
		return isBuildWall;
	}
	public void setIsBuildWall(String isBuildWall) {
		this.isBuildWall = isBuildWall;
	}
	public String getIsCheckCtType() {
		return isCheckCtType;
	}
	public void setIsCheckCtType(String isCheckCtType) {
		this.isCheckCtType = isCheckCtType;
	}
	public String getIsCheckDept() {
		return isCheckDept;
	}
	public void setIsCheckDept(String isCheckDept) {
		this.isCheckDept = isCheckDept;
	}
	public String getIsShowToiletNum() {
		return isShowToiletNum;
	}
	public void setIsShowToiletNum(String isShowToiletNum) {
		this.isShowToiletNum = isShowToiletNum;
	}
	public String getIsShowBedroomNum() {
		return isShowBedroomNum;
	}
	public void setIsShowBedroomNum(String isShowBedroomNum) {
		this.isShowBedroomNum = isShowBedroomNum;
	}
	public String getIsShowKitchDoorType() {
		return isShowKitchDoorType;
	}
	public void setIsShowKitchDoorType(String isShowKitchDoorType) {
		this.isShowKitchDoorType = isShowKitchDoorType;
	}
	public String getIsShowBalconyNum() {
		return isShowBalconyNum;
	}
	public void setIsShowBalconyNum(String isShowBalconyNum) {
		this.isShowBalconyNum = isShowBalconyNum;
	}
	public String getIsShowBalconyTitle() {
		return isShowBalconyTitle;
	}
	public void setIsShowBalconyTitle(String isShowBalconyTitle) {
		this.isShowBalconyTitle = isShowBalconyTitle;
	}
	public String getIsShowIsWood() {
		return isShowIsWood;
	}
	public void setIsShowIsWood(String isShowIsWood) {
		this.isShowIsWood = isShowIsWood;
	}
	public String getIsShowIsBuildWall() {
		return isShowIsBuildWall;
	}
	public void setIsShowIsBuildWall(String isShowIsBuildWall) {
		this.isShowIsBuildWall = isShowIsBuildWall;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}
	public String getIsPushCustDescr() {
		return isPushCustDescr;
	}
	public void setIsPushCustDescr(String isPushCustDescr) {
		this.isPushCustDescr = isPushCustDescr;
	}
	
}
