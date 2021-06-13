package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * CustProfit信息
 */
@Entity
@Table(name = "tCustProfit")
public class CustProfit extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "BaseDiscPer")
	private Double baseDiscPer;
	@Column(name = "BaseDisc1")
	private Double baseDisc1;
	@Column(name = "BaseDisc2")
	private Double baseDisc2;
	@Column(name = "DesignFee")
	private Double designFee;
	@Column(name = "Gift")
	private Double gift;
	@Column(name = "ContainBase")
	private Integer containBase;
	@Column(name = "ContainMain")
	private Integer containMain;
	@Column(name = "ContainSoft")
	private Integer containSoft;
	@Column(name = "ContainInt")
	private Integer containInt;
	@Column(name = "ContainCup")
	private Integer containCup;
	@Column(name = "ContainMainServ")
	private Integer containMainServ;
	@Column(name = "ColorDrawFee")
	private Double colorDrawFee;
	@Column(name = "RemoteFee")
	private Double remoteFee;
	@Column(name = "BaseDisc")
	private Double baseDisc;
	@Column(name = "MainCost")
	private Double mainCost;
	@Column(name = "JobPer")
	private Double jobPer;
	@Column(name = "BasePro")
	private Double basePro;
	@Column(name = "MainPro")
	private Double mainPro;
	@Column(name = "ServPro")
	private Double servPro;
	@Column(name = "IntPro")
	private Double intPro;
	@Column(name = "CupPro")
	private Double cupPro;
	@Column(name = "SoftPro")
	private Double softPro;
	@Column(name = "ManagePro")
	private Double managePro;
	@Column(name = "DesignPro")
	private Double designPro;
	@Column(name = "AllPro")
	private Double allPro;
	@Column(name = "DesignCalPer")
	private Double designCalPer;
	@Column(name = "CostPer")
	private Double costPer;
	@Column(name = "BaseCalPer")
	private Double baseCalPer;
	@Column(name = "MainCalPer")
	private Double mainCalPer;
	@Column(name = "ServProPer")
	private Double servProPer;
	@Column(name = "ServCalPer")
	private Double servCalPer;
	@Column(name = "JobCtrl")
	private Double jobCtrl;
	@Column(name = "JobLowPer")
	private Double jobLowPer;
	@Column(name = "JobHighPer")
	private Double jobHighPer;
	@Column(name = "IntProPer")
	private Double intProPer;
	@Column(name = "IntCalPer")
	private Double intCalPer;
	@Column(name = "CupProPer")
	private Double cupProPer;
	@Column(name = "CupCalPer")
	private Double cupCalPer;
	@Column(name = "SoftProPer")
	private Double softProPer;
	@Column(name = "SoftCalPer")
	private Double softCalPer;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Prepay")
	private Double prepay;
	@Column(name = "PayType")
	private String payType;
	@Column(name = "position")
	private String position;
	@Column(name = "ReturnDesignFee")
	private Double returnDesignFee;
	@Column(name = "StdDesignFee")
	private Double stdDesignFee;
	@Column(name = "Tax")
	private Double tax;
	@Transient
	private Double taxRate;
	
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setBaseDiscPer(Double baseDiscPer) {
		this.baseDiscPer = baseDiscPer;
	}
	
	public Double getBaseDiscPer() {
		return this.baseDiscPer;
	}
	public void setBaseDisc1(Double baseDisc1) {
		this.baseDisc1 = baseDisc1;
	}
	
	public Double getBaseDisc1() {
		return this.baseDisc1;
	}
	public void setBaseDisc2(Double baseDisc2) {
		this.baseDisc2 = baseDisc2;
	}
	
	public Double getBaseDisc2() {
		return this.baseDisc2;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	
	public Double getDesignFee() {
		return this.designFee;
	}
	public void setGift(Double gift) {
		this.gift = gift;
	}
	
	public Double getGift() {
		return this.gift;
	}
	public void setContainBase(Integer containBase) {
		this.containBase = containBase;
	}
	
	public Integer getContainBase() {
		return this.containBase;
	}
	public void setContainMain(Integer containMain) {
		this.containMain = containMain;
	}
	
	public Integer getContainMain() {
		return this.containMain;
	}
	public void setContainSoft(Integer containSoft) {
		this.containSoft = containSoft;
	}
	
	public Integer getContainSoft() {
		return this.containSoft;
	}
	public void setContainInt(Integer containInt) {
		this.containInt = containInt;
	}
	
	public Integer getContainInt() {
		return this.containInt;
	}
	public void setContainCup(Integer containCup) {
		this.containCup = containCup;
	}
	
	public Integer getContainCup() {
		return this.containCup;
	}
	public void setContainMainServ(Integer containMainServ) {
		this.containMainServ = containMainServ;
	}
	
	public Integer getContainMainServ() {
		return this.containMainServ;
	}
	public void setColorDrawFee(Double colorDrawFee) {
		this.colorDrawFee = colorDrawFee;
	}
	
	public Double getColorDrawFee() {
		return this.colorDrawFee;
	}
	public void setRemoteFee(Double remoteFee) {
		this.remoteFee = remoteFee;
	}
	
	public Double getRemoteFee() {
		return this.remoteFee;
	}
	public void setBaseDisc(Double baseDisc) {
		this.baseDisc = baseDisc;
	}
	
	public Double getBaseDisc() {
		return this.baseDisc;
	}
	public void setMainCost(Double mainCost) {
		this.mainCost = mainCost;
	}
	
	public Double getMainCost() {
		return this.mainCost;
	}
	public void setJobPer(Double jobPer) {
		this.jobPer = jobPer;
	}
	
	public Double getJobPer() {
		return this.jobPer;
	}
	public void setBasePro(Double basePro) {
		this.basePro = basePro;
	}
	
	public Double getBasePro() {
		return this.basePro;
	}
	public void setMainPro(Double mainPro) {
		this.mainPro = mainPro;
	}
	
	public Double getMainPro() {
		return this.mainPro;
	}
	public void setServPro(Double servPro) {
		this.servPro = servPro;
	}
	
	public Double getServPro() {
		return this.servPro;
	}
	public void setIntPro(Double intPro) {
		this.intPro = intPro;
	}
	
	public Double getIntPro() {
		return this.intPro;
	}
	public void setCupPro(Double cupPro) {
		this.cupPro = cupPro;
	}
	
	public Double getCupPro() {
		return this.cupPro;
	}
	public void setSoftPro(Double softPro) {
		this.softPro = softPro;
	}
	
	public Double getSoftPro() {
		return this.softPro;
	}
	public void setManagePro(Double managePro) {
		this.managePro = managePro;
	}
	
	public Double getManagePro() {
		return this.managePro;
	}
	public void setDesignPro(Double designPro) {
		this.designPro = designPro;
	}
	
	public Double getDesignPro() {
		return this.designPro;
	}
	public void setAllPro(Double allPro) {
		this.allPro = allPro;
	}
	
	public Double getAllPro() {
		return this.allPro;
	}
	public void setDesignCalPer(Double designCalPer) {
		this.designCalPer = designCalPer;
	}
	
	public Double getDesignCalPer() {
		return this.designCalPer;
	}
	public void setCostPer(Double costPer) {
		this.costPer = costPer;
	}
	
	public Double getCostPer() {
		return this.costPer;
	}
	public void setBaseCalPer(Double baseCalPer) {
		this.baseCalPer = baseCalPer;
	}
	
	public Double getBaseCalPer() {
		return this.baseCalPer;
	}
	public void setMainCalPer(Double mainCalPer) {
		this.mainCalPer = mainCalPer;
	}
	
	public Double getMainCalPer() {
		return this.mainCalPer;
	}
	public void setServProPer(Double servProPer) {
		this.servProPer = servProPer;
	}
	
	public Double getServProPer() {
		return this.servProPer;
	}
	public void setServCalPer(Double servCalPer) {
		this.servCalPer = servCalPer;
	}
	
	public Double getServCalPer() {
		return this.servCalPer;
	}
	public void setJobCtrl(Double jobCtrl) {
		this.jobCtrl = jobCtrl;
	}
	
	public Double getJobCtrl() {
		return this.jobCtrl;
	}
	public void setJobLowPer(Double jobLowPer) {
		this.jobLowPer = jobLowPer;
	}
	
	public Double getJobLowPer() {
		return this.jobLowPer;
	}
	public void setJobHighPer(Double jobHighPer) {
		this.jobHighPer = jobHighPer;
	}
	
	public Double getJobHighPer() {
		return this.jobHighPer;
	}
	public void setIntProPer(Double intProPer) {
		this.intProPer = intProPer;
	}
	
	public Double getIntProPer() {
		return this.intProPer;
	}
	public void setIntCalPer(Double intCalPer) {
		this.intCalPer = intCalPer;
	}
	
	public Double getIntCalPer() {
		return this.intCalPer;
	}
	public void setCupProPer(Double cupProPer) {
		this.cupProPer = cupProPer;
	}
	
	public Double getCupProPer() {
		return this.cupProPer;
	}
	public void setCupCalPer(Double cupCalPer) {
		this.cupCalPer = cupCalPer;
	}
	
	public Double getCupCalPer() {
		return this.cupCalPer;
	}
	public void setSoftProPer(Double softProPer) {
		this.softProPer = softProPer;
	}
	
	public Double getSoftProPer() {
		return this.softProPer;
	}
	public void setSoftCalPer(Double softCalPer) {
		this.softCalPer = softCalPer;
	}
	
	public Double getSoftCalPer() {
		return this.softCalPer;
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
	public void setPrepay(Double prepay) {
		this.prepay = prepay;
	}
	
	public Double getPrepay() {
		return this.prepay;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return this.position;
	}

	public Double getReturnDesignFee() {
		return returnDesignFee;
	}

	public void setReturnDesignFee(Double returnDesignFee) {
		this.returnDesignFee = returnDesignFee;
	}

	public Double getStdDesignFee() {
		return stdDesignFee;
	}

	public void setStdDesignFee(Double stdDesignFee) {
		this.stdDesignFee = stdDesignFee;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	
}
