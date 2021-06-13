package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemAppSendDetail信息bean
 */
public class ItemAppSendDetailBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="pk", order=1)
	private Integer pk;
	@ExcelAnnotation(exportName="no", order=2)
	private String no;
	@ExcelAnnotation(exportName="refPk", order=3)
	private Integer refPk;
	@ExcelAnnotation(exportName="sendQty", order=4)
	private Double sendQty;
	@ExcelAnnotation(exportName="cost", order=5)
	private Double cost;
	@ExcelAnnotation(exportName="aftQty", order=6)
	private Double aftQty;
	@ExcelAnnotation(exportName="aftCost", order=7)
	private Double aftCost;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=8)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=9)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=10)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=11)
	private String actionLog;

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setRefPk(Integer refPk) {
		this.refPk = refPk;
	}
	
	public Integer getRefPk() {
		return this.refPk;
	}
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public Double getSendQty() {
		return this.sendQty;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setAftQty(Double aftQty) {
		this.aftQty = aftQty;
	}
	
	public Double getAftQty() {
		return this.aftQty;
	}
	public void setAftCost(Double aftCost) {
		this.aftCost = aftCost;
	}
	
	public Double getAftCost() {
		return this.aftCost;
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
