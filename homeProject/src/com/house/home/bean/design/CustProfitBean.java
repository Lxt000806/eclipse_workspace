package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * CustProfit信息bean
 */
public class CustProfitBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="custCode", order=1)
	private String custCode;
	@ExcelAnnotation(exportName="baseDiscPer", order=2)
	private Double baseDiscPer;
	@ExcelAnnotation(exportName="baseDisc1", order=3)
	private Double baseDisc1;
	@ExcelAnnotation(exportName="baseDisc2", order=4)
	private Double baseDisc2;
	@ExcelAnnotation(exportName="designFee", order=5)
	private Double designFee;
	@ExcelAnnotation(exportName="gift", order=6)
	private Double gift;
	@ExcelAnnotation(exportName="containBase", order=7)
	private Integer containBase;
	@ExcelAnnotation(exportName="containMain", order=8)
	private Integer containMain;
	@ExcelAnnotation(exportName="containSoft", order=9)
	private Integer containSoft;
	@ExcelAnnotation(exportName="containInt", order=10)
	private Integer containInt;
	@ExcelAnnotation(exportName="containCup", order=11)
	private Integer containCup;
	@ExcelAnnotation(exportName="containMainServ", order=12)
	private Integer containMainServ;
	@ExcelAnnotation(exportName="colorDrawFee", order=13)
	private Double colorDrawFee;
	@ExcelAnnotation(exportName="remoteFee", order=14)
	private Double remoteFee;
	@ExcelAnnotation(exportName="baseDisc", order=15)
	private Double baseDisc;
	@ExcelAnnotation(exportName="mainCost", order=16)
	private Double mainCost;
	@ExcelAnnotation(exportName="jobPer", order=17)
	private Double jobPer;
	@ExcelAnnotation(exportName="basePro", order=18)
	private Double basePro;
	@ExcelAnnotation(exportName="mainPro", order=19)
	private Double mainPro;
	@ExcelAnnotation(exportName="servPro", order=20)
	private Double servPro;
	@ExcelAnnotation(exportName="intPro", order=21)
	private Double intPro;
	@ExcelAnnotation(exportName="cupPro", order=22)
	private Double cupPro;
	@ExcelAnnotation(exportName="softPro", order=23)
	private Double softPro;
	@ExcelAnnotation(exportName="managePro", order=24)
	private Double managePro;
	@ExcelAnnotation(exportName="designPro", order=25)
	private Double designPro;
	@ExcelAnnotation(exportName="allPro", order=26)
	private Double allPro;
	@ExcelAnnotation(exportName="designCalPer", order=27)
	private Double designCalPer;
	@ExcelAnnotation(exportName="costPer", order=28)
	private Double costPer;
	@ExcelAnnotation(exportName="baseCalPer", order=29)
	private Double baseCalPer;
	@ExcelAnnotation(exportName="mainCalPer", order=30)
	private Double mainCalPer;
	@ExcelAnnotation(exportName="servProPer", order=31)
	private Double servProPer;
	@ExcelAnnotation(exportName="servCalPer", order=32)
	private Double servCalPer;
	@ExcelAnnotation(exportName="jobCtrl", order=33)
	private Double jobCtrl;
	@ExcelAnnotation(exportName="jobLowPer", order=34)
	private Double jobLowPer;
	@ExcelAnnotation(exportName="jobHighPer", order=35)
	private Double jobHighPer;
	@ExcelAnnotation(exportName="intProPer", order=36)
	private Double intProPer;
	@ExcelAnnotation(exportName="intCalPer", order=37)
	private Double intCalPer;
	@ExcelAnnotation(exportName="cupProPer", order=38)
	private Double cupProPer;
	@ExcelAnnotation(exportName="cupCalPer", order=39)
	private Double cupCalPer;
	@ExcelAnnotation(exportName="softProPer", order=40)
	private Double softProPer;
	@ExcelAnnotation(exportName="softCalPer", order=41)
	private Double softCalPer;
    	@ExcelAnnotation(exportName="lastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=42)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="lastUpdatedBy", order=43)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="expired", order=44)
	private String expired;
	@ExcelAnnotation(exportName="actionLog", order=45)
	private String actionLog;
	@ExcelAnnotation(exportName="prepay", order=46)
	private Double prepay;
	@ExcelAnnotation(exportName="payType", order=47)
	private String payType;
	@ExcelAnnotation(exportName="position", order=48)
	private String position;

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

}
