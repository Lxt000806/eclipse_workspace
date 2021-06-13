package com.house.home.entity.finance;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tPrjPerfDetail")
public class PrjPerfDetail extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CustCheckDate")
	private Date custCheckDate;
	@Column(name = "BaseCtrlAmount")
	private Double baseCtrlAmount;
	@Column(name = "BasePlan")
	private Double basePlan;
	@Column(name = "LongFee")
	private Double longFee;
	@Column(name = "ManageFee")
	private Double manageFee;
	@Column(name = "DesignFee")
	private Double designFee;
	@Column(name = "MainSetFee")
	private Double mainSetFee;
	@Column(name = "SetAdd")
	private Double setAdd;
	@Column(name = "SetMinus")
	private Double setMinus;
	@Column(name = "MainPlan")
	private Double mainPlan;
	@Column(name = "ServPlan")
	private Double servPlan;
	@Column(name = "SoftPlan")
	private Double softPlan;
	@Column(name = "FurnPlan")
	private Double furnPlan;
	@Column(name = "IntPlan")
	private Double intPlan;
	@Column(name = "CupPlan")
	private Double cupPlan;
	@Column(name = "BaseDisc")
	private Double baseDisc;
	@Column(name = "ContractFee")
	private Double contractFee;
	@Column(name = "BaseChg")
	private Double baseChg;
	@Column(name = "ManageChg")
	private Double manageChg;
	@Column(name = "DesignChg")
	private Double designChg;
	@Column(name = "ChgDisc")
	private Double chgDisc;
	@Column(name = "MainChg")
	private Double mainChg;
	@Column(name = "ServChg")
	private Double servChg;
	@Column(name = "SoftChg")
	private Double softChg;
	@Column(name = "FurnChg")
	private Double furnChg;
	@Column(name = "IntChg")
	private Double intChg;
	@Column(name = "CheckAmount")
	private Double checkAmount;
	@Column(name = "CheckPerf")
	private Double checkPerf;
	@Column(name = "PrjDeptLeader")
	private String prjDeptLeader;
	@Column(name = "ProjectMan")
	private String projectMan;
	@Column(name = "ProvideCard")
	private Double provideCard;
	@Column(name = "ProvideAmount")
	private Double provideAmount;
	@Column(name = "CheckMan")
	private String checkMan;
	@Column(name = "DelayDay")
	private Integer delayDay;
	@Column(name = "PerfPerc")
	private Double perfPerc;
	@Column(name = "PerfDisc")
	private Double perfDisc;
	@Column(name = "BaseChgCnt")
	private Integer baseChgCnt;
	@Column(name = "MainChgCnt")
	private Integer mainChgCnt;
	@Column(name = "IntChgCnt")
	private Integer intChgCnt;
	@Column(name = "SoftChgCnt")
	private Integer softChgCnt;
	@Column(name = "ServChgCnt")
	private Integer servChgCnt;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsModified")
	private String isModified;
	@Column(name = "BaseDeduction")
	private Double baseDeduction;
	@Column(name = "ItemDeduction")
	private Double itemDeduction;
	@Column(name = "PerfExpr")
	private String perfExpr;
	@Column(name = "PerfExprRemarks")
	private String perfExprRemarks;
	@Column(name = "SoftTokenAmount")
	private Double softTokenAmount;
	@Column(name = "Gift")
	private Double gift;
	@Column(name = "Tax")
	private Double tax;
	@Column(name = "TaxChg")
	private Double taxChg;
	@Column(name = "BasePersonalPlan")
	private Double basePersonalPlan;
	@Column(name = "BasePersonalChg")
	private Double basePersonalChg;
	
	@Column(name = "SceneDesignerCheck")
	private Double sceneDesignerCheck;
	
    @Column(name = "NoSceneDesignerCheck")
	private Double noSceneDesignerCheck;
	
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
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Date getCustCheckDate() {
		return custCheckDate;
	}
	public void setCustCheckDate(Date custCheckDate) {
		this.custCheckDate = custCheckDate;
	}
	public Double getBaseCtrlAmount() {
		return baseCtrlAmount;
	}
	public void setBaseCtrlAmount(Double baseCtrlAmount) {
		this.baseCtrlAmount = baseCtrlAmount;
	}
	public Double getBasePlan() {
		return basePlan;
	}
	public void setBasePlan(Double basePlan) {
		this.basePlan = basePlan;
	}
	public Double getLongFee() {
		return longFee;
	}
	public void setLongFee(Double longFee) {
		this.longFee = longFee;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	public Double getMainSetFee() {
		return mainSetFee;
	}
	public void setMainSetFee(Double mainSetFee) {
		this.mainSetFee = mainSetFee;
	}
	public Double getSetAdd() {
		return setAdd;
	}
	public void setSetAdd(Double setAdd) {
		this.setAdd = setAdd;
	}
	public Double getSetMinus() {
		return setMinus;
	}
	public void setSetMinus(Double setMinus) {
		this.setMinus = setMinus;
	}
	public Double getMainPlan() {
		return mainPlan;
	}
	public void setMainPlan(Double mainPlan) {
		this.mainPlan = mainPlan;
	}
	public Double getServPlan() {
		return servPlan;
	}
	public void setServPlan(Double servPlan) {
		this.servPlan = servPlan;
	}
	public Double getSoftPlan() {
		return softPlan;
	}
	public void setSoftPlan(Double softPlan) {
		this.softPlan = softPlan;
	}
	public Double getFurnPlan() {
		return furnPlan;
	}
	public void setFurnPlan(Double furnPlan) {
		this.furnPlan = furnPlan;
	}
	public Double getIntPlan() {
		return intPlan;
	}
	public void setIntPlan(Double intPlan) {
		this.intPlan = intPlan;
	}
	public Double getCupPlan() {
		return cupPlan;
	}
	public void setCupPlan(Double cupPlan) {
		this.cupPlan = cupPlan;
	}
	public Double getBaseDisc() {
		return baseDisc;
	}
	public void setBaseDisc(Double baseDisc) {
		this.baseDisc = baseDisc;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public Double getBaseChg() {
		return baseChg;
	}
	public void setBaseChg(Double baseChg) {
		this.baseChg = baseChg;
	}
	public Double getManageChg() {
		return manageChg;
	}
	public void setManageChg(Double manageChg) {
		this.manageChg = manageChg;
	}
	public Double getDesignChg() {
		return designChg;
	}
	public void setDesignChg(Double designChg) {
		this.designChg = designChg;
	}
	public Double getChgDisc() {
		return chgDisc;
	}
	public void setChgDisc(Double chgDisc) {
		this.chgDisc = chgDisc;
	}
	public Double getMainChg() {
		return mainChg;
	}
	public void setMainChg(Double mainChg) {
		this.mainChg = mainChg;
	}
	public Double getServChg() {
		return servChg;
	}
	public void setServChg(Double servChg) {
		this.servChg = servChg;
	}
	public Double getSoftChg() {
		return softChg;
	}
	public void setSoftChg(Double softChg) {
		this.softChg = softChg;
	}
	public Double getFurnChg() {
		return furnChg;
	}
	public void setFurnChg(Double furnChg) {
		this.furnChg = furnChg;
	}
	public Double getIntChg() {
		return intChg;
	}
	public void setIntChg(Double intChg) {
		this.intChg = intChg;
	}
	public Double getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	public Double getCheckPerf() {
		return checkPerf;
	}
	public void setCheckPerf(Double checkPerf) {
		this.checkPerf = checkPerf;
	}
	public String getPrjDeptLeader() {
		return prjDeptLeader;
	}
	public void setPrjDeptLeader(String prjDeptLeader) {
		this.prjDeptLeader = prjDeptLeader;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public Double getProvideCard() {
		return provideCard;
	}
	public void setProvideCard(Double provideCard) {
		this.provideCard = provideCard;
	}
	public Double getProvideAmount() {
		return provideAmount;
	}
	public void setProvideAmount(Double provideAmount) {
		this.provideAmount = provideAmount;
	}
	public String getCheckMan() {
		return checkMan;
	}
	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	public Integer getDelayDay() {
		return delayDay;
	}
	public void setDelayDay(Integer delayDay) {
		this.delayDay = delayDay;
	}
	public Double getPerfPerc() {
		return perfPerc;
	}
	public void setPerfPerc(Double perfPerc) {
		this.perfPerc = perfPerc;
	}
	public Double getPerfDisc() {
		return perfDisc;
	}
	public void setPerfDisc(Double perfDisc) {
		this.perfDisc = perfDisc;
	}
	public Integer getBaseChgCnt() {
		return baseChgCnt;
	}
	public void setBaseChgCnt(Integer baseChgCnt) {
		this.baseChgCnt = baseChgCnt;
	}
	public Integer getMainChgCnt() {
		return mainChgCnt;
	}
	public void setMainChgCnt(Integer mainChgCnt) {
		this.mainChgCnt = mainChgCnt;
	}
	public Integer getIntChgCnt() {
		return intChgCnt;
	}
	public void setIntChgCnt(Integer intChgCnt) {
		this.intChgCnt = intChgCnt;
	}
	public Integer getSoftChgCnt() {
		return softChgCnt;
	}
	public void setSoftChgCnt(Integer softChgCnt) {
		this.softChgCnt = softChgCnt;
	}
	public Integer getServChgCnt() {
		return servChgCnt;
	}
	public void setServChgCnt(Integer servChgCnt) {
		this.servChgCnt = servChgCnt;
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
	public String getIsModified() {
		return isModified;
	}
	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}
	public Double getBaseDeduction() {
		return baseDeduction;
	}
	public void setBaseDeduction(Double baseDeduction) {
		this.baseDeduction = baseDeduction;
	}
	public Double getItemDeduction() {
		return itemDeduction;
	}
	public void setItemDeduction(Double itemDeduction) {
		this.itemDeduction = itemDeduction;
	}
	public String getPerfExpr() {
		return perfExpr;
	}
	public void setPerfExpr(String perfExpr) {
		this.perfExpr = perfExpr;
	}
	public String getPerfExprRemarks() {
		return perfExprRemarks;
	}
	public void setPerfExprRemarks(String perfExprRemarks) {
		this.perfExprRemarks = perfExprRemarks;
	}
	public Double getSoftTokenAmount() {
		return softTokenAmount;
	}
	public void setSoftTokenAmount(Double softTokenAmount) {
		this.softTokenAmount = softTokenAmount;
	}
	public Double getGift() {
		return gift;
	}
	public void setGift(Double gift) {
		this.gift = gift;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getTaxChg() {
		return taxChg;
	}
	public void setTaxChg(Double taxChg) {
		this.taxChg = taxChg;
	}
	
    public Double getSceneDesignerCheck() {
        return sceneDesignerCheck;
    }

    public void setSceneDesignerCheck(Double sceneDesignerCheck) {
        this.sceneDesignerCheck = sceneDesignerCheck;
    }

    public Double getNoSceneDesignerCheck() {
        return noSceneDesignerCheck;
    }

    public void setNoSceneDesignerCheck(Double noSceneDesignerCheck) {
        this.noSceneDesignerCheck = noSceneDesignerCheck;
    }
	public Double getBasePersonalPlan() {
		return basePersonalPlan;
	}
	public void setBasePersonalPlan(Double basePersonalPlan) {
		this.basePersonalPlan = basePersonalPlan;
	}
	public Double getBasePersonalChg() {
		return basePersonalChg;
	}
	public void setBasePersonalChg(Double basePersonalChg) {
		this.basePersonalChg = basePersonalChg;
	}
    

}
