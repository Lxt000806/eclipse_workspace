package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemAppSendDetail信息
 */
@Entity
@Table(name = "tItemAppSendDetail")
public class ItemAppSendDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "RefPk")
	private Integer refPk;
	@Column(name = "SendQty")
	private Double sendQty;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "AftQty")
	private Double aftQty;
	@Column(name = "AftCost")
	private Double aftCost;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
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
