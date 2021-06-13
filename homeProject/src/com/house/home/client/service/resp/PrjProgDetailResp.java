package com.house.home.client.service.resp;

import java.awt.Image;
import java.util.Date;
import java.util.List;

public class PrjProgDetailResp extends BaseResponse{
	
	private Integer pk;
	private String custCode;
	private String prjItem;
	private String prjItemDescr;
	private Date planBegin;
	private Date beginDate;
	private Date planEnd;
	private Date endDate;
	private Date lastUpdate;
	private String lastUpdatedBy;
	private String expired;
	private String actionLog;
	private String prjStatus;
	private String confirmDesc;
	private List<String> photoList;
	private List<Image> imageList;
	private List<String> photoNameList;
	private Integer prjphotoNum;
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
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
	public Date getPlanBegin() {
		return planBegin;
	}
	public void setPlanBegin(Date planBegin) {
		this.planBegin = planBegin;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public List<String> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}
	public List<Image> getImageList() {
		return imageList;
	}
	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
	public String getPrjStatus() {
		return prjStatus;
	}
	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}
	public List<String> getPhotoNameList() {
		return photoNameList;
	}
	public void setPhotoNameList(List<String> photoNameList) {
		this.photoNameList = photoNameList;
	}
	public String getConfirmDesc() {
		return confirmDesc;
	}
	public void setConfirmDesc(String confirmDesc) {
		this.confirmDesc = confirmDesc;
	}
	public Integer getPrjphotoNum() {
		return prjphotoNum;
	}
	public void setPrjphotoNum(Integer prjphotoNum) {
		this.prjphotoNum = prjphotoNum;
	}
    

}
