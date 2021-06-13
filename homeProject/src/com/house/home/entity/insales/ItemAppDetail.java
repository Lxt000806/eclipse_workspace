package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * ItemAppDetail信息
 */
@Entity
@Table(name = "tItemAppDetail")
public class ItemAppDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ReqPk")
	private Integer reqPk;
	@Column(name = "FixAreaPk")
	private Integer fixAreaPk;
	@Column(name = "IntProdPk")
	private Integer intProdPk;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Cost")
	private Double cost;
	@Column(name = "AftQty")
	private Double aftQty;
	@Column(name = "AftCost")
	private Double aftCost;
	@Column(name = "SendQty")
	private Double sendQty;
	@Column(name = "ProjectCost")
	private Double projectCost;
	@Column(name = "PreAppDTPK")
	private Integer preAppDtpk;
	
	@Transient
	private String custCode;
	@Transient
	private String itemType1;
	@Transient
	private String puno;
	@Transient
	private String pks; //已经选择的PK

	
	public String getPuno() {
		return puno;
	}

	public void setPuno(String puno) {
		this.puno = puno;
	}

	public String getCustCode() {
		return custCode;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

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
	public void setReqPk(Integer reqPk) {
		this.reqPk = reqPk;
	}
	
	public Integer getReqPk() {
		return this.reqPk;
	}
	public void setFixAreaPk(Integer fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	
	public Integer getFixAreaPk() {
		return this.fixAreaPk;
	}
	public void setIntProdPk(Integer intProdPk) {
		this.intProdPk = intProdPk;
	}
	
	public Integer getIntProdPk() {
		return this.intProdPk;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCode() {
		return this.itemCode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
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
	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public Double getSendQty() {
		return this.sendQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}

	public Integer getPreAppDtpk() {
		return preAppDtpk;
	}

	public void setPreAppDtpk(Integer preAppDtpk) {
		this.preAppDtpk = preAppDtpk;
	}

	public String getPks() {
		return pks;
	}

	public void setPks(String pks) {
		this.pks = pks;
	}

}
