package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class GetIntProgressResp extends BaseResponse{
	private String cupSplDescr;
	private String intSplDescr;
	private String doorSplDescr;
	private String cgSupplDescr;
	private String ygSupplDescr;
	private String tlmSupplDescr;
	private String tmSupplDescr;
	private String cgSupplCode;
	private String ygSupplCode;
	
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupAppDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intAppDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date doorAppDate;
	private String tableSplDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date tableAppDate;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupPlanSendDate;
	private String cupMaterialDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupSendDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupInstallDate;

	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intPlanSendDate;
	private String intMaterialDescr;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intSendDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intInstallDate;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intJobAppDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date intJobDealDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupJobAppDate;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date cupJobDealDate;
	
	
	
	
	public String getCgSupplDescr() {
		return cgSupplDescr;
	}
	public void setCgSupplDescr(String cgSupplDescr) {
		this.cgSupplDescr = cgSupplDescr;
	}
	public String getYgSupplDescr() {
		return ygSupplDescr;
	}
	public void setYgSupplDescr(String ygSupplDescr) {
		this.ygSupplDescr = ygSupplDescr;
	}
	public String getTlmSupplDescr() {
		return tlmSupplDescr;
	}
	public void setTlmSupplDescr(String tlmSupplDescr) {
		this.tlmSupplDescr = tlmSupplDescr;
	}
	public String getTmSupplDescr() {
		return tmSupplDescr;
	}
	public void setTmSupplDescr(String tmSupplDescr) {
		this.tmSupplDescr = tmSupplDescr;
	}
	public String getCupSplDescr() {
		return cupSplDescr;
	}
	public void setCupSplDescr(String cupSplDescr) {
		this.cupSplDescr = cupSplDescr;
	}
	public String getIntSplDescr() {
		return intSplDescr;
	}
	public void setIntSplDescr(String intSplDescr) {
		this.intSplDescr = intSplDescr;
	}
	public String getDoorSplDescr() {
		return doorSplDescr;
	}
	public void setDoorSplDescr(String doorSplDescr) {
		this.doorSplDescr = doorSplDescr;
	}
	public Date getCupAppDate() {
		return cupAppDate;
	}
	public void setCupAppDate(Date cupAppDate) {
		this.cupAppDate = cupAppDate;
	}
	public Date getIntAppDate() {
		return intAppDate;
	}
	public void setIntAppDate(Date intAppDate) {
		this.intAppDate = intAppDate;
	}
	public Date getDoorAppDate() {
		return doorAppDate;
	}
	public void setDoorAppDate(Date doorAppDate) {
		this.doorAppDate = doorAppDate;
	}
	public Date getTableAppDate() {
		return tableAppDate;
	}
	public void setTableAppDate(Date tableAppDate) {
		this.tableAppDate = tableAppDate;
	}
	public String getTableSplDescr() {
		return tableSplDescr;
	}
	public void setTableSplDescr(String tableSplDescr) {
		this.tableSplDescr = tableSplDescr;
	}
	public Date getCupPlanSendDate() {
		return cupPlanSendDate;
	}
	public void setCupPlanSendDate(Date cupPlanSendDate) {
		this.cupPlanSendDate = cupPlanSendDate;
	}
	public String getCupMaterialDescr() {
		return cupMaterialDescr;
	}
	public void setCupMaterialDescr(String cupMaterialDescr) {
		this.cupMaterialDescr = cupMaterialDescr;
	}
	public Date getCupSendDate() {
		return cupSendDate;
	}
	public void setCupSendDate(Date cupSendDate) {
		this.cupSendDate = cupSendDate;
	}
	public Date getCupInstallDate() {
		return cupInstallDate;
	}
	public void setCupInstallDate(Date cupInstallDate) {
		this.cupInstallDate = cupInstallDate;
	}
	public Date getIntPlanSendDate() {
		return intPlanSendDate;
	}
	public void setIntPlanSendDate(Date intPlanSendDate) {
		this.intPlanSendDate = intPlanSendDate;
	}
	public String getIntMaterialDescr() {
		return intMaterialDescr;
	}
	public void setIntMaterialDescr(String intMaterialDescr) {
		this.intMaterialDescr = intMaterialDescr;
	}
	public Date getIntSendDate() {
		return intSendDate;
	}
	public void setIntSendDate(Date intSendDate) {
		this.intSendDate = intSendDate;
	}
	public Date getIntInstallDate() {
		return intInstallDate;
	}
	public void setIntInstallDate(Date intInstallDate) {
		this.intInstallDate = intInstallDate;
	}
	public Date getIntJobAppDate() {
		return intJobAppDate;
	}
	public void setIntJobAppDate(Date intJobAppDate) {
		this.intJobAppDate = intJobAppDate;
	}
	public Date getIntJobDealDate() {
		return intJobDealDate;
	}
	public void setIntJobDealDate(Date intJobDealDate) {
		this.intJobDealDate = intJobDealDate;
	}
	public Date getCupJobAppDate() {
		return cupJobAppDate;
	}
	public void setCupJobAppDate(Date cupJobAppDate) {
		this.cupJobAppDate = cupJobAppDate;
	}
	public Date getCupJobDealDate() {
		return cupJobDealDate;
	}
	public void setCupJobDealDate(Date cupJobDealDate) {
		this.cupJobDealDate = cupJobDealDate;
	}
	public String getCgSupplCode() {
		return cgSupplCode;
	}
	public void setCgSupplCode(String cgSupplCode) {
		this.cgSupplCode = cgSupplCode;
	}
	public String getYgSupplCode() {
		return ygSupplCode;
	}
	public void setYgSupplCode(String ygSupplCode) {
		this.ygSupplCode = ygSupplCode;
	}
	
}
