package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetIntProduceResp extends BaseResponse{
	private String address;
	private String supplDescr;
	private String isCupboardDescr;
	
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date setBoardDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date arrBoardDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date openMaterialDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date sealingSideDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date exHoleDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date packDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date inWHDate;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSupplDescr() {
		return supplDescr;
	}
	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}
	public String getIsCupboardDescr() {
		return isCupboardDescr;
	}
	public void setIsCupboardDescr(String isCupboardDescr) {
		this.isCupboardDescr = isCupboardDescr;
	}
	public Date getSetBoardDate() {
		return setBoardDate;
	}
	public void setSetBoardDate(Date setBoardDate) {
		this.setBoardDate = setBoardDate;
	}
	public Date getArrBoardDate() {
		return arrBoardDate;
	}
	public void setArrBoardDate(Date arrBoardDate) {
		this.arrBoardDate = arrBoardDate;
	}
	public Date getOpenMaterialDate() {
		return openMaterialDate;
	}
	public void setOpenMaterialDate(Date openMaterialDate) {
		this.openMaterialDate = openMaterialDate;
	}
	public Date getSealingSideDate() {
		return sealingSideDate;
	}
	public void setSealingSideDate(Date sealingSideDate) {
		this.sealingSideDate = sealingSideDate;
	}
	public Date getExHoleDate() {
		return exHoleDate;
	}
	public void setExHoleDate(Date exHoleDate) {
		this.exHoleDate = exHoleDate;
	}
	public Date getPackDate() {
		return packDate;
	}
	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}
	public Date getInWHDate() {
		return inWHDate;
	}
	public void setInWHDate(Date inWHDate) {
		this.inWHDate = inWHDate;
	}
	
	
}
