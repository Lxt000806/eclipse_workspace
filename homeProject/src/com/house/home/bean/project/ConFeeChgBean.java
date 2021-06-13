package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ConFeeChg信息bean
 */
public class ConFeeChgBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="chgType", order=3)
	private String chgType;
	@ExcelAnnotation(exportName="chgAmount", order=4)
	private Double chgAmount;
	@ExcelAnnotation(exportName="remarks", order=5)
	private String remarks;
	@ExcelAnnotation(exportName="status", order=6)
	private String status;
	@ExcelAnnotation(exportName="appCzy", order=7)
	private String appCzy;
    	@ExcelAnnotation(exportName="date", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date date;
	@ExcelAnnotation(exportName="confirmCzy", order=9)
	private String confirmCzy;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date confirmDate;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=12)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=13)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=14)
	private String actionLog;
	@ExcelAnnotation(exportName="itemType1", order=15)
	private String itemType1;
	@ExcelAnnotation(exportName="isService", order=16)
	private String isService;
	@ExcelAnnotation(exportName="isCupboard", order=17)
	private String isCupboard;
	@ExcelAnnotation(exportName="chgNo", order=18)
	private String chgNo;
	@ExcelAnnotation(exportName="perfPk", order=19)
	private Integer perfPk;
	@ExcelAnnotation(exportName="iscalPerf", order=20)
	private String iscalPerf;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	
	public String getChgType() {
		return this.chgType;
	}
	public void setChgAmount(Double chgAmount) {
		this.chgAmount = chgAmount;
	}
	
	public Double getChgAmount() {
		return this.chgAmount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setIsService(String isService) {
		this.isService = isService;
	}
	
	public String getIsService() {
		return this.isService;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	
	public String getIsCupboard() {
		return this.isCupboard;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	
	public String getChgNo() {
		return this.chgNo;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	
	public String getIscalPerf() {
		return this.iscalPerf;
	}

}
