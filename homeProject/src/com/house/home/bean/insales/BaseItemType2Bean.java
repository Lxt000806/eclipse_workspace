package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * BaseItemType2信息bean
 */
public class BaseItemType2Bean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="code", order=1)
	private String code;
	@ExcelAnnotation(exportName="descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="baseItemType1", order=3)
	private String baseItemType1;
	@ExcelAnnotation(exportName="dispSeq", order=4)
	private Integer dispSeq;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=5)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=6)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=7)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=8)
	private String actionLog;
	@ExcelAnnotation(exportName="offerWorkType2", order=9)
	private String offerWorkType2;
	@ExcelAnnotation(exportName="materWorkType2", order=10)
	private String materWorkType2;

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setBaseItemType1(String baseItemType1) {
		this.baseItemType1 = baseItemType1;
	}
	
	public String getBaseItemType1() {
		return this.baseItemType1;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
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
	public void setOfferWorkType2(String offerWorkType2) {
		this.offerWorkType2 = offerWorkType2;
	}
	
	public String getOfferWorkType2() {
		return this.offerWorkType2;
	}
	public void setMaterWorkType2(String materWorkType2) {
		this.materWorkType2 = materWorkType2;
	}
	
	public String getMaterWorkType2() {
		return this.materWorkType2;
	}

}
