package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * Activity信息
 */
@Entity
@Table(name = "tItemAppConfRule")
public class ItemAppConfRule extends BaseEntity {
	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "PayType")
	private String payType;
	@Column(name = "PayNum")
	private String payNum;
	@Column(name = "PayPer")
	private Double payPer;
	@Column(name = "Prior")
	private Double prior;
	@Column(name = "DiffAmount")
	private Double diffAmount;
	@Column(name = "ItemCost")
	private Double itemCost;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String itemAppConfRuleJson;
	@Transient
	private String itemType2;
	@Transient
	private String itemType3;
	@Transient
	private String itemDesc;
	@Transient
	private String itemType2Com;
	@Transient
    private String itemType3Com;
    @Transient
    private  String itemDescCom;
	
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayNum() {
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	public Double getPayPer() {
		return payPer;
	}
	public void setPayPer(Double payPer) {
		this.payPer = payPer;
	}
	public Double getPrior() {
		return prior;
	}
	public void setPrior(Double prior) {
		this.prior = prior;
	}
	public Double getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}
	public Double getItemCost() {
		return itemCost;
	}
	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getItemAppConfRuleJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemAppConfRuleJson);
    	return xml;
	}
	public void setItemAppConfRuleJson(String itemAppConfRuleJson) {
		this.itemAppConfRuleJson = itemAppConfRuleJson;
	}
	public String getItemType2() {
		return itemType2;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	public String getItemType3() {
		return itemType3;
	}
	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemType2Com() {
		return itemType2Com;
	}
	public void setItemType2Com(String itemType2Com) {
		this.itemType2Com = itemType2Com;
	}
	public String getItemType3Com() {
		return itemType3Com;
	}
	public void setItemType3Com(String itemType3Com) {
		this.itemType3Com = itemType3Com;
	}
	public String getItemDescCom() {
		return itemDescCom;
	}
	public void setItemDescCom(String itemDescCom) {
		this.itemDescCom = itemDescCom;
	}
}
