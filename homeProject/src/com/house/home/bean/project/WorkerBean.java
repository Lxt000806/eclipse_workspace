package com.house.home.bean.project;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * worker信息bean
 */
public class WorkerBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="nameChi", order=2)
	private String nameChi;
	@ExcelAnnotation(exportName="phone", order=3)
	private String phone;
	@ExcelAnnotation(exportName="idnum", order=4)
	private String idnum;
	@ExcelAnnotation(exportName="cardId", order=5)
	private String cardId;
	@ExcelAnnotation(exportName="workType12", order=6)
	private String workType12;
	@ExcelAnnotation(exportName="introduceEmp", order=7)
	private String introduceEmp;
	@ExcelAnnotation(exportName="remarks", order=8)
	private String remarks;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=10)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=11)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=12)
	private String actionLog;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	
	public String getNameChi() {
		return this.nameChi;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	
	public String getIdnum() {
		return this.idnum;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCardId() {
		return this.cardId;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	public String getWorkType12() {
		return this.workType12;
	}
	public void setIntroduceEmp(String introduceEmp) {
		this.introduceEmp = introduceEmp;
	}
	
	public String getIntroduceEmp() {
		return this.introduceEmp;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
