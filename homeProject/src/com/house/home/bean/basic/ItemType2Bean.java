package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemType2信息bean
 */
public class ItemType2Bean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="Code", order=1)
	private String code;
	@ExcelAnnotation(exportName="Descr", order=2)
	private String descr;
	@ExcelAnnotation(exportName="材料类型1", order=3)
	private String itemType1;
	@ExcelAnnotation(exportName="显示顺序", order=4)
	private Integer dispSeq;
	@ExcelAnnotation(exportName="Length", order=5)
	private Integer length;
	@ExcelAnnotation(exportName="最大号码", order=6)
	private Integer lastNumber;
    	@ExcelAnnotation(exportName="LastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="LastUpdatedBy", order=8)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="Expired", order=9)
	private String expired;
	@ExcelAnnotation(exportName="ActionLog", order=10)
	private String actionLog;
	@ExcelAnnotation(exportName="到货天数", order=11)
	private Integer arriveDay;
	@ExcelAnnotation(exportName="材料工种分类2", order=12)
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
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Integer getLength() {
		return this.length;
	}
	public void setLastNumber(Integer lastNumber) {
		this.lastNumber = lastNumber;
	}
	
	public Integer getLastNumber() {
		return this.lastNumber;
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
	public void setArriveDay(Integer arriveDay) {
		this.arriveDay = arriveDay;
	}
	
	public Integer getArriveDay() {
		return this.arriveDay;
	}
	public void setMaterWorkType2(String materWorkType2) {
		this.materWorkType2 = materWorkType2;
	}
	
	public String getMaterWorkType2() {
		return this.materWorkType2;
	}

}
