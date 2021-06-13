package com.house.home.entity.basic;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tProjectCtrlPrice")
public class ProjectCtrlPrice extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pK;
	@Column(name = "FromArea")
	private Double fromArea;
	@Column(name = "ToArea")
	private Double toArea;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "Price")
	private Double price;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ManageFee")
	private Double manageFee;
	@Column(name = "BaseQuotaPrice")
	private Double baseQuotaPrice;
	@Column(name = "MinArea")
	private Double minArea;//增加维护保底面积字段 --add by zb
	
	public Integer getpK() {
		return pK;
	}
	public void setpK(Integer pK) {
		this.pK = pK;
	}
	public Double getFromArea() {
		return fromArea;
	}
	public void setFromArea(Double fromArea) {
		this.fromArea = fromArea;
	}
	public Double getToArea() {
		return toArea;
	}
	public void setToArea(Double toArea) {
		this.toArea = toArea;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getBaseQuotaPrice() {
		return baseQuotaPrice;
	}
	public void setBaseQuotaPrice(Double baseQuotaPrice) {
		this.baseQuotaPrice = baseQuotaPrice;
	}
	public Double getMinArea() {
		return minArea;
	}
	public void setMinArea(Double minArea) {
		this.minArea = minArea;
	}


}
