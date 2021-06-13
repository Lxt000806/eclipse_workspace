package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * AgainSign信息bean
 */
public class AgainSignBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="custCode", order=2)
	private String custCode;
	@ExcelAnnotation(exportName="custType", order=3)
	private String custType;
	@ExcelAnnotation(exportName="contractType", order=4)
	private String contractType;
	@ExcelAnnotation(exportName="contractFee", order=5)
	private Double contractFee;
    	@ExcelAnnotation(exportName="signDate", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date signDate;
	@ExcelAnnotation(exportName="remarks", order=7)
	private String remarks;
    	@ExcelAnnotation(exportName="newSignDate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date newSignDate;
	@ExcelAnnotation(exportName="hadCalcPerf", order=9)
	private String hadCalcPerf;
	@ExcelAnnotation(exportName="perfPk", order=10)
	private Integer perfPk;
	@ExcelAnnotation(exportName="hadCalcBackPerf", order=11)
	private String hadCalcBackPerf;
	@ExcelAnnotation(exportName="backPerfPk", order=12)
	private Integer backPerfPk;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=13)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=14)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=15)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=16)
	private String actionLog;

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
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	public String getCustType() {
		return this.custType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractType() {
		return this.contractType;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	
	public Double getContractFee() {
		return this.contractFee;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	public Date getSignDate() {
		return this.signDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setNewSignDate(Date newSignDate) {
		this.newSignDate = newSignDate;
	}
	
	public Date getNewSignDate() {
		return this.newSignDate;
	}
	public void setHadCalcPerf(String hadCalcPerf) {
		this.hadCalcPerf = hadCalcPerf;
	}
	
	public String getHadCalcPerf() {
		return this.hadCalcPerf;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setHadCalcBackPerf(String hadCalcBackPerf) {
		this.hadCalcBackPerf = hadCalcBackPerf;
	}
	
	public String getHadCalcBackPerf() {
		return this.hadCalcBackPerf;
	}
	public void setBackPerfPk(Integer backPerfPk) {
		this.backPerfPk = backPerfPk;
	}
	
	public Integer getBackPerfPk() {
		return this.backPerfPk;
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
