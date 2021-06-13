package com.house.home.client.service.resp;

import java.util.Date;

public class GetWorkPrjItemListResp extends BaseResponse{
	
	private String prjItem2Descr;
	private Integer signCount;
	private Date beginDate;
	private Date endDate;
	private Double contructDay;
	private Integer allSign;
	private Integer minPhotoNum;
	private Integer maxPhotoNum;
	private Integer techPhotoNum;
	private String techCode;
	private String techDescr;
	private Integer photoNum;
	
	
	
	public Integer getPhotoNum() {
		return photoNum;
	}
	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}
	public String getTechCode() {
		return techCode;
	}
	public void setTechCode(String techCode) {
		this.techCode = techCode;
	}
	public String getTechDescr() {
		return techDescr;
	}
	public void setTechDescr(String techDescr) {
		this.techDescr = techDescr;
	}
	public Integer getTechPhotoNum() {
		return techPhotoNum;
	}
	public void setTechPhotoNum(Integer techPhotoNum) {
		this.techPhotoNum = techPhotoNum;
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
	public String getPrjItem2Descr() {
		return prjItem2Descr;
	}
	public void setPrjItem2Descr(String prjItem2Descr) {
		this.prjItem2Descr = prjItem2Descr;
	}
	public Integer getSignCount() {
		return signCount;
	}
	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getContructDay() {
		return contructDay;
	}
	public void setContructDay(Double contructDay) {
		this.contructDay = contructDay;
	}
	public Integer getAllSign() {
		return allSign;
	}
	public void setAllSign(Integer allSign) {
		this.allSign = allSign;
	}
	
}
