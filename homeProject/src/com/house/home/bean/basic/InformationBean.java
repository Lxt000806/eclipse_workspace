package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Information信息bean
 */
public class InformationBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="number", order=1)
	private String number;
	@ExcelAnnotation(exportName="infoType", order=2)
	private String infoType;
	@ExcelAnnotation(exportName="status", order=3)
	private String status;
	@ExcelAnnotation(exportName="sendCzy", order=4)
	private String sendCzy;
    	@ExcelAnnotation(exportName="sendDate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date sendDate;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date confirmDate;
	@ExcelAnnotation(exportName="confirmCzy", order=7)
	private String confirmCzy;
	@ExcelAnnotation(exportName="infoTitle", order=8)
	private String infoTitle;
	@ExcelAnnotation(exportName="infoText", order=9)
	private String infoText;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=10)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=11)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=12)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=13)
	private String actionLog;

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return this.number;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	
	public String getInfoType() {
		return this.infoType;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setSendCzy(String sendCzy) {
		this.sendCzy = sendCzy;
	}
	
	public String getSendCzy() {
		return this.sendCzy;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	
	public String getInfoTitle() {
		return this.infoTitle;
	}
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	
	public String getInfoText() {
		return this.infoText;
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

}
