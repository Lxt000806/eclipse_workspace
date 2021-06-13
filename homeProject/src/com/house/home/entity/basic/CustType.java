package com.house.home.entity.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CustType信息
 */
@Entity
@Table(name = "tCusttype")
public class CustType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Type")
	private String type;
	@Column(name = "AreaPer")
	private Integer areaPer;
	@Column(name = "BaseFee_CompPer")
	private Double baseFeeCompPer;
	@Column(name = "BaseFee_DirctPer")
	private Double baseFeeDirctPer;
	@Column(name = "MainSetFeePer")
	private Double mainSetFeePer;
	@Column(name = "SetMinusPer")
	private Double setMinusPer;
	@Column(name = "SetAddPer")
	private Double setAddPer;
	@Column(name = "LongFeePer")
	private Double longFeePer;
	@Column(name = "ManageFee_BasePer")
	private Double manageFeeBasePer;
	@Column(name = "MainFeePer")
	private Double mainFeePer;
	@Column(name = "MainDiscPer")
	private Double mainDiscPer;
	@Column(name = "ManageFee_MainPer")
	private Double manageFeeMainPer;
	@Column(name = "MainServFeePer")
	private Double mainServFeePer;
	@Column(name = "ManageFee_ServPer")
	private Double manageFeeServPer;
	@Column(name = "SoftFeePer")
	private Double softFeePer;
	@Column(name = "SoftDiscPer")
	private Double softDiscPer;
	@Column(name = "ManageFee_SoftPer")
	private Double manageFeeSoftPer;
	@Column(name = "IntegrateFeePer")
	private Double integrateFeePer;
	@Column(name = "IntegrateDiscPer")
	private Double integrateDiscPer;
	@Column(name = "ManageFee_IntPer")
	private Double manageFeeIntPer;
	@Column(name = "CupboardFeePer")
	private Double cupboardFeePer;
	@Column(name = "CupBoardDiscPer")
	private Double cupBoardDiscPer;
	@Column(name = "ManageFee_CupPer")
	private Double manageFeeCupPer;
	@Column(name = "ChgManageFeePer")
	private Double chgManageFeePer;
	@Column(name = "IsDefaultStatic")
	private String isDefaultStatic;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "IsAddAllInfo")
	private String isAddAllInfo;
	@Column(name = "ConfirmItem")
	private String confirmItem;
	@Column(name = "CtrlExpr")
	private String ctrlExpr;
	@Column(name = "IsCalcPerf")
	private String isCalcPerf;
	@Column(name = "FirstPayPer")
	private Double firstPayPer;
	@Column(name = "SetCtrlExpr")
	private String setCtrlExpr;
	@Column(name = "setContainInt")
	private String setContainInt;
	@Column(name = "MaterialExpr")
	private String materialExpr;
	@Column(name = "IsSetMainCtrl")
	private String isSetMainCtrl;
	@Column(name = "CmpnyName")
	private String cmpnyName;
	@Column(name = "LogoFile")
	private String logoFile;
	@Column(name = "CmpnyFullName")
	private String cmpnyFullName;
	@Column(name = "CmpnyAddress")
	private String cmpnyAddress;
	@Column(name = "BasePerfPer")
	private Double basePerfPer;
	@Column(name = "InnerAreaPer")
	private Double innerAreaPer;
	@Column(name = "IsCalcBaseDisc")
	private String isCalcBaseDisc;
	@Column(name = "ContractFeeRepType")
	private String contractFeeRepType;
	@Column(name = "MaxMaterPerfPer")
	private Double maxMaterPerfPer;
	@Column(name = "IntPerfAmount_Set")
	private Double intPerfAmount_Set;
	@Column(name = "CupPerfAmount_Set")
	private Double cupPerfAmount_Set;
	@Column(name = "MustImportTemp")
	private String mustImportTemp;
	@Column(name = "PerfExpr")
	private String perfExpr;//业绩公式
	@Column(name = "PerfExprRemarks")
	private String perfExprRemarks;//业绩公式说明
	@Column(name = "WaterCtrlPri")
	private Double waterCtrlPri;//防水补贴
	@Column(name = "IntSaleAmount_Set")//集成套餐内销售额
	private Double intSaleAmount_Set;
	@Column(name = "CupSaleAmount_Set")//,橱柜套餐内销售额
	private Double cupSaleAmount_Set;
	@Column(name ="TaxExpr")//税金公式
	private String taxExpr;
	@Column(name ="IsWaterItemCtrl")//水电材料发包工人
	private String isWaterItemCtrl;
	@Column(name ="IsPrjConfirm")//项目经理验收
	private String isPrjConfirm;
	@Column(name="CtrlExprRemarks")
	private String ctrlExprRemarks; //发包公式说明 add by zb‘
	@Column(name ="IsFacingSubsidy")//项目经理验收
	private String isFacingSubsidy;
	@Column(name = "CanUseComBaseItem")
	private String canUseComBaseItem;
	@Column(name = "ChgPerfExpr")
	private String chgPerfExpr; //增减业绩公式
	@Column(name = "ChgPerfExprRemarks")
	private String chgPerfExprRemarks; //增减业绩公式说明
	@Column(name = "DispSeq")
	private Integer dispSeq; 
	@Column(name = "IsPushPub")
	private String isPushPub; 
	@Column(name = "PayeeCode")
	private String payeeCode; 
	@Column(name = "PrjCtrlType")
	private String prjCtrlType;
	@Column(name = "IsPartDecorate")
	private String isPartDecorate; 
	@Column(name = "KitchenStdArea")
	private Double kitchenStdArea;//厨房（发包）标准面积
	@Column(name = "ToiletStdArea")
	private Double toiletStdArea;//卫生间（发包）标准面积
	@Column(name = "OverAreaSubsidyPer")
	private Double overAreaSubsidyPer;//厨卫超面积每平米补贴
	@Column(name ="WorkerClassify")
	private String  workerClassify;
	@Column(name = "DesignRiskFund")
	private Double designRiskFund; //设计师风控基金
	@Column(name = "PrjManRiskFund")
	private Double prjManRiskFund; //项目经理风控基金
	@Column(name ="DesignPayeeCode")
	private String  designPayeeCode;//设计费收款单位
	@Column(name="CtrlMainItemOK")
	private String ctrlMainItemOK;
	@Column(name="IntSaleAmountCalcType_Set")
	private String intSaleAmountCalcType_Set;
	@Column(name="IsWaterAftInsItemApp")
	private String isWaterAftInsItemApp;
	@Column(name = "MatCoopFeeCancelDate")
	private Date matCoopFeeCancelDate;//材料配合费取消时间
	@Column(name="DesignerMaxDiscAmtExpr")
	private String designerMaxDiscAmtExpr;
	@Column(name="DirectorMaxDiscAmtExpr")
	private String directorMaxDiscAmtExpr;
	@Column(name="SignQuoteType")
	private String signQuoteType;
	@Column(name="CanUseComItem")
	private String canUseComItem;
	@Column(name="ItemRemark")
	private String itemRemark;
	@Column(name="PricRemark")
	private String pricRemark;
	@Column(name="BaseSpec")
	private String baseSpec;
	@Column(name="IsCalcCommi")
	private String isCalcCommi;
	
	@Column(name = "GenTaxInfo")
	private String genTaxInfo;
	
	public String getBaseSpec() {
		return baseSpec;
	}

	public void setBaseSpec(String baseSpec) {
		this.baseSpec = baseSpec;
	}

	public String getSignQuoteType() {
		return signQuoteType;
	}

	public void setSignQuoteType(String signQuoteType) {
		this.signQuoteType = signQuoteType;
	}

	public String getIntSaleAmountCalcType_Set() {
		return intSaleAmountCalcType_Set;
	}

	public void setIntSaleAmountCalcType_Set(String intSaleAmountCalcType_Set) {
		this.intSaleAmountCalcType_Set = intSaleAmountCalcType_Set;
	}

	public String getCtrlMainItemOK() {
		return ctrlMainItemOK;
	}

	public void setCtrlMainItemOK(String ctrlMainItemOK) {
		this.ctrlMainItemOK = ctrlMainItemOK;
	}

	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}

	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}

	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}

	public String getCtrlExprRemarks() {
		return ctrlExprRemarks;
	}

	public void setCtrlExprRemarks(String ctrlExprRemarks) {
		this.ctrlExprRemarks = ctrlExprRemarks;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAreaPer() {
		return areaPer;
	}

	public void setAreaPer(Integer areaPer) {
		this.areaPer = areaPer;
	}

	public Double getBaseFeeCompPer() {
		return baseFeeCompPer;
	}

	public void setBaseFeeCompPer(Double baseFeeCompPer) {
		this.baseFeeCompPer = baseFeeCompPer;
	}

	public Double getBaseFeeDirctPer() {
		return baseFeeDirctPer;
	}

	public void setBaseFeeDirctPer(Double baseFeeDirctPer) {
		this.baseFeeDirctPer = baseFeeDirctPer;
	}

	public Double getMainSetFeePer() {
		return mainSetFeePer;
	}

	public void setMainSetFeePer(Double mainSetFeePer) {
		this.mainSetFeePer = mainSetFeePer;
	}

	public Double getSetMinusPer() {
		return setMinusPer;
	}

	public void setSetMinusPer(Double setMinusPer) {
		this.setMinusPer = setMinusPer;
	}

	public Double getSetAddPer() {
		return setAddPer;
	}

	public void setSetAddPer(Double setAddPer) {
		this.setAddPer = setAddPer;
	}

	public Double getLongFeePer() {
		return longFeePer;
	}

	public void setLongFeePer(Double longFeePer) {
		this.longFeePer = longFeePer;
	}

	public Double getManageFeeBasePer() {
		return manageFeeBasePer;
	}

	public void setManageFeeBasePer(Double manageFeeBasePer) {
		this.manageFeeBasePer = manageFeeBasePer;
	}

	public Double getMainFeePer() {
		return mainFeePer;
	}

	public void setMainFeePer(Double mainFeePer) {
		this.mainFeePer = mainFeePer;
	}

	public Double getMainDiscPer() {
		return mainDiscPer;
	}

	public void setMainDiscPer(Double mainDiscPer) {
		this.mainDiscPer = mainDiscPer;
	}

	public Double getManageFeeMainPer() {
		return manageFeeMainPer;
	}

	public void setManageFeeMainPer(Double manageFeeMainPer) {
		this.manageFeeMainPer = manageFeeMainPer;
	}

	public Double getMainServFeePer() {
		return mainServFeePer;
	}

	public void setMainServFeePer(Double mainServFeePer) {
		this.mainServFeePer = mainServFeePer;
	}

	public Double getManageFeeServPer() {
		return manageFeeServPer;
	}

	public void setManageFeeServPer(Double manageFeeServPer) {
		this.manageFeeServPer = manageFeeServPer;
	}

	public Double getSoftFeePer() {
		return softFeePer;
	}

	public void setSoftFeePer(Double softFeePer) {
		this.softFeePer = softFeePer;
	}

	public Double getSoftDiscPer() {
		return softDiscPer;
	}

	public void setSoftDiscPer(Double softDiscPer) {
		this.softDiscPer = softDiscPer;
	}

	public Double getManageFeeSoftPer() {
		return manageFeeSoftPer;
	}

	public void setManageFeeSoftPer(Double manageFeeSoftPer) {
		this.manageFeeSoftPer = manageFeeSoftPer;
	}

	public Double getIntegrateFeePer() {
		return integrateFeePer;
	}

	public void setIntegrateFeePer(Double integrateFeePer) {
		this.integrateFeePer = integrateFeePer;
	}

	public Double getIntegrateDiscPer() {
		return integrateDiscPer;
	}

	public void setIntegrateDiscPer(Double integrateDiscPer) {
		this.integrateDiscPer = integrateDiscPer;
	}

	public Double getManageFeeIntPer() {
		return manageFeeIntPer;
	}

	public void setManageFeeIntPer(Double manageFeeIntPer) {
		this.manageFeeIntPer = manageFeeIntPer;
	}

	public Double getCupboardFeePer() {
		return cupboardFeePer;
	}

	public void setCupboardFeePer(Double cupboardFeePer) {
		this.cupboardFeePer = cupboardFeePer;
	}

	public Double getCupBoardDiscPer() {
		return cupBoardDiscPer;
	}

	public void setCupBoardDiscPer(Double cupBoardDiscPer) {
		this.cupBoardDiscPer = cupBoardDiscPer;
	}

	public Double getManageFeeCupPer() {
		return manageFeeCupPer;
	}

	public void setManageFeeCupPer(Double manageFeeCupPer) {
		this.manageFeeCupPer = manageFeeCupPer;
	}

	public Double getChgManageFeePer() {
		return chgManageFeePer;
	}

	public void setChgManageFeePer(Double chgManageFeePer) {
		this.chgManageFeePer = chgManageFeePer;
	}

	public String getIsDefaultStatic() {
		return isDefaultStatic;
	}

	public void setIsDefaultStatic(String isDefaultStatic) {
		this.isDefaultStatic = isDefaultStatic;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}

	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}

	public String getConfirmItem() {
		return confirmItem;
	}

	public void setConfirmItem(String confirmItem) {
		this.confirmItem = confirmItem;
	}

	public String getCtrlExpr() {
		return ctrlExpr;
	}

	public void setCtrlExpr(String ctrlExpr) {
		this.ctrlExpr = ctrlExpr;
	}

	public String getIsCalcPerf() {
		return isCalcPerf;
	}

	public void setIsCalcPerf(String isCalcPerf) {
		this.isCalcPerf = isCalcPerf;
	}

	public Double getFirstPayPer() {
		return firstPayPer;
	}

	public void setFirstPayPer(Double firstPayPer) {
		this.firstPayPer = firstPayPer;
	}

	public String getSetCtrlExpr() {
		return setCtrlExpr;
	}

	public void setSetCtrlExpr(String setCtrlExpr) {
		this.setCtrlExpr = setCtrlExpr;
	}

	public String getSetContainInt() {
		return setContainInt;
	}

	public void setSetContainInt(String setContainInt) {
		this.setContainInt = setContainInt;
	}

	public String getMaterialExpr() {
		return materialExpr;
	}

	public void setMaterialExpr(String materialExpr) {
		this.materialExpr = materialExpr;
	}

	public String getIsSetMainCtrl() {
		return isSetMainCtrl;
	}

	public void setIsSetMainCtrl(String isSetMainCtrl) {
		this.isSetMainCtrl = isSetMainCtrl;
	}

	public String getCmpnyName() {
		return cmpnyName;
	}

	public void setCmpnyName(String cmpnyName) {
		this.cmpnyName = cmpnyName;
	}

	public String getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}

	public String getCmpnyFullName() {
		return cmpnyFullName;
	}

	public void setCmpnyFullName(String cmpnyFullName) {
		this.cmpnyFullName = cmpnyFullName;
	}

	public String getCmpnyAddress() {
		return cmpnyAddress;
	}

	public void setCmpnyAddress(String cmpnyAddress) {
		this.cmpnyAddress = cmpnyAddress;
	}

	public Double getBasePerfPer() {
		return basePerfPer;
	}

	public void setBasePerfPer(Double basePerfPer) {
		this.basePerfPer = basePerfPer;
	}

	public Double getInnerAreaPer() {
		return innerAreaPer;
	}

	public void setInnerAreaPer(Double innerAreaPer) {
		this.innerAreaPer = innerAreaPer;
	}

	public String getIsCalcBaseDisc() {
		return isCalcBaseDisc;
	}

	public void setIsCalcBaseDisc(String isCalcBaseDisc) {
		this.isCalcBaseDisc = isCalcBaseDisc;
	}

	public String getContractFeeRepType() {
		return contractFeeRepType;
	}

	public void setContractFeeRepType(String contractFeeRepType) {
		this.contractFeeRepType = contractFeeRepType;
	}

	public Double getMaxMaterPerfPer() {
		return maxMaterPerfPer;
	}

	public void setMaxMaterPerfPer(Double maxMaterPerfPer) {
		this.maxMaterPerfPer = maxMaterPerfPer;
	}

	public Double getIntPerfAmount_Set() {
		return intPerfAmount_Set;
	}

	public void setIntPerfAmount_Set(Double intPerfAmount_Set) {
		this.intPerfAmount_Set = intPerfAmount_Set;
	}

	public Double getCupPerfAmount_Set() {
		return cupPerfAmount_Set;
	}

	public void setCupPerfAmount_Set(Double cupPerfAmount_Set) {
		this.cupPerfAmount_Set = cupPerfAmount_Set;
	}

	public String getMustImportTemp() {
		return mustImportTemp;
	}

	public void setMustImportTemp(String mustImportTemp) {
		this.mustImportTemp = mustImportTemp;
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

	public Double getIntSaleAmount_Set() {
		return intSaleAmount_Set;
	}

	public void setIntSaleAmount_Set(Double intSaleAmount_Set) {
		this.intSaleAmount_Set = intSaleAmount_Set;
	}

	public Double getCupSaleAmount_Set() {
		return cupSaleAmount_Set;
	}

	public void setCupSaleAmount_Set(Double cupSaleAmount_Set) {
		this.cupSaleAmount_Set = cupSaleAmount_Set;
	}

	public String getTaxExpr() {
		return taxExpr;
	}

	public void setTaxExpr(String taxExpr) {
		this.taxExpr = taxExpr;
	}

	public String getIsWaterItemCtrl() {
		return isWaterItemCtrl;
	}

	public void setIsWaterItemCtrl(String isWaterItemCtrl) {
		this.isWaterItemCtrl = isWaterItemCtrl;
	}

	public String getIsPrjConfirm() {
		return isPrjConfirm;
	}

	public void setIsPrjConfirm(String isPrjConfirm) {
		this.isPrjConfirm = isPrjConfirm;
	}

	public Double getWaterCtrlPri() {
		return waterCtrlPri;
	}

	public void setWaterCtrlPri(Double waterCtrlPri) {
		this.waterCtrlPri = waterCtrlPri;
	}

	public String getIsFacingSubsidy() {
		return isFacingSubsidy;
	}

	public void setIsFacingSubsidy(String isFacingSubsidy) {
		this.isFacingSubsidy = isFacingSubsidy;
	}

	public String getChgPerfExpr() {
		return chgPerfExpr;
	}

	public void setChgPerfExpr(String chgPerfExpr) {
		this.chgPerfExpr = chgPerfExpr;
	}

	public String getChgPerfExprRemarks() {
		return chgPerfExprRemarks;
	}

	public void setChgPerfExprRemarks(String chgPerfExprRemarks) {
		this.chgPerfExprRemarks = chgPerfExprRemarks;
	}

	public Integer getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getIsPushPub() {
		return isPushPub;
	}

	public void setIsPushPub(String isPushPub) {
		this.isPushPub = isPushPub;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getPrjCtrlType() {
		return prjCtrlType;
	}

	public void setPrjCtrlType(String prjCtrlType) {
		this.prjCtrlType = prjCtrlType;
	}

	public String getIsPartDecorate() {
		return isPartDecorate;
	}

	public void setIsPartDecorate(String isPartDecorate) {
		this.isPartDecorate = isPartDecorate;
	}

	public Double getKitchenStdArea() {
		return kitchenStdArea;
	}

	public void setKitchenStdArea(Double kitchenStdArea) {
		this.kitchenStdArea = kitchenStdArea;
	}

	public Double getToiletStdArea() {
		return toiletStdArea;
	}

	public void setToiletStdArea(Double toiletStdArea) {
		this.toiletStdArea = toiletStdArea;
	}

	public Double getOverAreaSubsidyPer() {
		return overAreaSubsidyPer;
	}

	public void setOverAreaSubsidyPer(Double overAreaSubsidyPer) {
		this.overAreaSubsidyPer = overAreaSubsidyPer;
	}

	public Double getDesignRiskFund() {
		return designRiskFund;
	}

	public void setDesignRiskFund(Double designRiskFund) {
		this.designRiskFund = designRiskFund;
	}

	public Double getPrjManRiskFund() {
		return prjManRiskFund;
	}

	public void setPrjManRiskFund(Double prjManRiskFund) {
		this.prjManRiskFund = prjManRiskFund;
	}

	public String getDesignPayeeCode() {
		return designPayeeCode;
	}

	public void setDesignPayeeCode(String designPayeeCode) {
		this.designPayeeCode = designPayeeCode;
	}

	public String getIsWaterAftInsItemApp() {
		return isWaterAftInsItemApp;
	}

	public void setIsWaterAftInsItemApp(String isWaterAftInsItemApp) {
		this.isWaterAftInsItemApp = isWaterAftInsItemApp;
	}

	public Date getMatCoopFeeCancelDate() {
		return matCoopFeeCancelDate;
	}

	public void setMatCoopFeeCancelDate(Date matCoopFeeCancelDate) {
		this.matCoopFeeCancelDate = matCoopFeeCancelDate;
	}

	public String getDesignerMaxDiscAmtExpr() {
		return designerMaxDiscAmtExpr;
	}

	public void setDesignerMaxDiscAmtExpr(String designerMaxDiscAmtExpr) {
		this.designerMaxDiscAmtExpr = designerMaxDiscAmtExpr;
	}

	public String getDirectorMaxDiscAmtExpr() {
		return directorMaxDiscAmtExpr;
	}

	public void setDirectorMaxDiscAmtExpr(String directorMaxDiscAmtExpr) {
		this.directorMaxDiscAmtExpr = directorMaxDiscAmtExpr;
	}

	public String getCanUseComItem() {
		return canUseComItem;
	}

	public void setCanUseComItem(String canUseComItem) {
		this.canUseComItem = canUseComItem;
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}

	public String getPricRemark() {
		return pricRemark;
	}

	public void setPricRemark(String pricRemark) {
		this.pricRemark = pricRemark;
	}

	public String getIsCalcCommi() {
		return isCalcCommi;
	}

	public void setIsCalcCommi(String isCalcCommi) {
		this.isCalcCommi = isCalcCommi;
	}

    public String getGenTaxInfo() {
        return genTaxInfo;
    }

    public void setGenTaxInfo(String genTaxInfo) {
        this.genTaxInfo = genTaxInfo;
    }
	
}
