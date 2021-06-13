package com.house.home.entity.basic;

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
 * ProfitPerf信息
 */
@Entity
@Table(name = "tProfitPerf")
public class ProfitPerf extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ItemType12")
	private String itemType12;
	@Column(name = "FromProfit")
	private Double fromProfit;
	@Column(name = "ToProfit")
	private Double toProfit;
	@Column(name = "IsClearInv")
	private String isClearInv;
	@Column(name = "DesignCommiPer")
	private Double designCommiPer;
	@Column(name = "BuyerCommiPer")
	private Double buyerCommiPer;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "AgainManCommiPer")
	private Double againManCommiPer;
	@Column(name = "BusinessManCommiPer")
	private Double businessManCommiPer;
	@Column(name = "Buyer2CommiPer")
	private Double buyer2CommiPer;
	@Column(name = "ProdMgrCommiPer")
	private Double prodMgrCommiPer;
	@Column(name = "OutBusinessManCommiPer")
    private Double outBusinessManCommiPer;
	@Column(name = "softBusinessManCommiPer")
    private Double softBusinessManCommiPer;
	
	@Transient
	private String Descr;
	@Transient
	private String Note;

	
	public Double getAgainManCommiPer() {
		return againManCommiPer;
	}
	public void setAgainManCommiPer(Double againManCommiPer) {
		this.againManCommiPer = againManCommiPer;
	}
	public Double getBusinessManCommiPer() {
		return businessManCommiPer;
	}
	public void setBusinessManCommiPer(Double businessManCommiPer) {
		this.businessManCommiPer = businessManCommiPer;
	}
	public String getDescr() {
		return Descr;
	}
	public void setDescr(String descr) {
		Descr = descr;
	}
	
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
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
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getItemType12() {
		return itemType12;
	}
	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}
	public String getIsClearInv() {
		return isClearInv;
	}
	public void setIsClearInv(String isClearInv) {
		this.isClearInv = isClearInv;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getFromProfit() {
		return fromProfit;
	}
	public void setFromProfit(Double fromProfit) {
		this.fromProfit = fromProfit;
	}
	public Double getToProfit() {
		return toProfit;
	}
	public void setToProfit(Double toProfit) {
		this.toProfit = toProfit;
	}
	public Double getDesignCommiPer() {
		return designCommiPer;
	}
	public void setDesignCommiPer(Double designCommiPer) {
		this.designCommiPer = designCommiPer;
	}
	public Double getBuyerCommiPer() {
		return buyerCommiPer;
	}
	public void setBuyerCommiPer(Double buyerCommiPer) {
		this.buyerCommiPer = buyerCommiPer;
	}
	public Double getBuyer2CommiPer() {
		return buyer2CommiPer;
	}
	public void setBuyer2CommiPer(Double buyer2CommiPer) {
		this.buyer2CommiPer = buyer2CommiPer;
	}
	public Double getProdMgrCommiPer() {
		return prodMgrCommiPer;
	}
	public void setProdMgrCommiPer(Double prodMgrCommiPer) {
		this.prodMgrCommiPer = prodMgrCommiPer;
	}
    public Double getOutBusinessManCommiPer() {
        return outBusinessManCommiPer;
    }
    public void setOutBusinessManCommiPer(Double outBusinessManCommiPer) {
        this.outBusinessManCommiPer = outBusinessManCommiPer;
    }
	public Double getSoftBusinessManCommiPer() {
		return softBusinessManCommiPer;
	}
	public void setSoftBusinessManCommiPer(Double softBusinessManCommiPer) {
		this.softBusinessManCommiPer = softBusinessManCommiPer;
	}
    

}
