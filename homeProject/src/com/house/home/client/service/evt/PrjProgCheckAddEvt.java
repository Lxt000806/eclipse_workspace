package com.house.home.client.service.evt;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PrjProgCheckAddEvt extends BaseEvt{
	
	@NotEmpty(message="客户编号不能为空")
	private String custCode;
	@NotEmpty(message="项目编号不能为空")
	private String prjItem;
	private String remarks;
	private String address;
	@NotEmpty(message="图片不能为空")
	private String photoString;//多个逗号隔开
	private String isModify;
	private Integer modifyTime;
	private String buildStatus;
	private String safeProm;
	private String visualProm;
	private String artProm;
	private Double longitude;
	private Double latitude;
	private String errPosi;
    private String isExecute;
    private String status;
    private String appCZY;
    private int pk;
    private String isUpPrjProg;
    private String prjItemDescr;

	private Integer toiletNum;
	private Integer bedroomNum;
	private String kitchDoorType;
	private Integer balconyNum;
	private String balconyTitle;
	private String isWood;
	private String isBuildWall;
	private String isPushCust;
	private Integer cigaNum;
	
	
	
	public Integer getCigaNum() {
		return cigaNum;
	}
	public void setCigaNum(Integer cigaNum) {
		this.cigaNum = cigaNum;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public String getIsModify() {
		return isModify;
	}
	public void setIsModify(String isModify) {
		this.isModify = isModify;
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
	public String getIsExecute() {
		return isExecute;
	}
	public void setIsExecute(String isExecute) {
		this.isExecute = isExecute;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public String getIsUpPrjProg() {
		return isUpPrjProg;
	}
	public void setIsUpPrjProg(String isUpPrjProg) {
		this.isUpPrjProg = isUpPrjProg;
	}
	public String getPrjItemDescr() {
		return prjItemDescr;
	}
	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
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
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}
	
}
