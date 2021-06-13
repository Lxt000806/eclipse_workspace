package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * GiftAppDetail信息
 */
@Entity
@Table(name = "tGiftAppDetail")
public class GiftAppDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "ItemCode")
	private String itemCode;
	@Column(name = "TokenPk")
	private Integer tokenPk;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "SendQty")
	private Double sendQty;
	@Column(name = "Price")
	private Double price;
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
	@Column(name = "cost")
	private Double cost;
	@Column(name = "UseDiscAmount")
	private String useDiscAmount;
	@Transient
	private String detailJson;
	@Transient
	private String itemDescr;
	@Transient
	private String uom;
	@Transient
	private String m_umState;
	@Transient
	private String actNo;
	@Transient
	private String giftAppType;
	@Transient
	private String custCode;
	@Transient
	private String  tokenNo;
	@Transient
	private Double  hasGiftUseDisc; //礼品已使用优惠
	@Transient
	private Double  thisGiftUseDisc; //本单礼品已使用优惠
	
	public String getItemAppDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getTokenPk() {
		return tokenPk;
	}

	public void setTokenPk(Integer tokenPk) {
		this.tokenPk = tokenPk;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getSendQty() {
		return sendQty;
	}

	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public String getM_umState() {
		return m_umState;
	}

	@Override
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getGiftAppType() {
		return giftAppType;
	}

	public void setGiftAppType(String giftAppType) {
		this.giftAppType = giftAppType;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getUseDiscAmount() {
		return useDiscAmount;
	}

	public void setUseDiscAmount(String useDiscAmount) {
		this.useDiscAmount = useDiscAmount;
	}

	public Double getHasGiftUseDisc() {
		return hasGiftUseDisc;
	}

	public void setHasGiftUseDisc(Double hasGiftUseDisc) {
		this.hasGiftUseDisc = hasGiftUseDisc;
	}

	public Double getThisGiftUseDisc() {
		return thisGiftUseDisc;
	}

	public void setThisGiftUseDisc(Double thisGiftUseDisc) {
		this.thisGiftUseDisc = thisGiftUseDisc;
	}

	
	
}
