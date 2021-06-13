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
 * ItemApp信息
 */
@Entity
@Table(name = "tItemApp")
public class ItemApp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "Type")
	private String type;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "SendCzy")
	private String sendCzy;
	@Column(name = "SendDate")
	private Date sendDate;
	@Column(name = "SendType")
	private String sendType;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "Puno")
	private String puno;
	@Column(name = "Whcode")
	private String whcode;
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
	@Column(name = "DelivType")
	private String delivType;
	@Column(name = "ProjectMan")
	private String projectMan;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "OldNo")
	private String oldNo;
	@Column(name = "OtherCost")
	private Double otherCost;
	@Column(name = "OtherCostAdj")
	private Double otherCostAdj;
	@Column(name = "IsService")
	private Integer isService;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "WhcheckOutNo")
	private String whcheckOutNo;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "PrjCheckType")
	private String prjCheckType;
	@Column(name = "ProjectOtherCost")
	private Double projectOtherCost;
	@Column(name = "IsSetItem")
	private String isSetItem;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "ProjectAmount")
	private Double projectAmount;
	@Column(name = "SplStatus")
	private String splStatus;
	@Column(name = "ArriveDate")
	private Date arriveDate;
	@Column(name = "SplRemark")
	private String splRemark;
	@Column(name = "ProductType")
	private String productType;
	@Column(name = "DelayReson")
	private String delayReson;
	@Column(name = "PreAppNo")
	private String preAppNo;
	@Column(name = "DelayRemark")
	private String delayRemark;
	@Column(name = "ArriveSplDate")
	private Date arriveSplDate;
	@Column(name = "SendBatchNo")
	private String sendBatchNo;
	@Column(name = "followRemark")
	private String followRemark;
	@Column(name = "PrintTimes")
	private Integer printTimes;
	@Column(name = "PrintDate")
	private Date printDate;
	@Column(name = "PrintCZY")
	private String printCZY;
	@Column(name = "SplRcvCZY")
	private String splRcvCZY;
	@Column(name = "SplRcvDate")
	private Date splRcvDate;
	@Column(name = "IsCupboard")
	private String isCupboard;
	@Column(name = "WHReceiveCZY")
	private String whReceiveCZY;
	@Column(name = "WHReceiveDate")
	private Date whReceiveDate;
	@Column(name = "EntrustProcStatus")
	private String entrustProcStatus; //委托加工状态
	@Column(name = "EntrustProcSendDate")
	private Date entrustProcSendDate; //委托加工状态
	@Column(name = "IntRepPK")
	private Integer intRepPK; //集成补货pk
	@Column(name = "FaultType")
	private String faultType;
	@Column(name = "FaultMan")
	private String faultMan;
	@Column(name = "NotifySendDate")
	private Date notifySendDate;
	
	@Transient
	private String custAddress;
	@Transient
	private String supplCodeDescr;
	@Transient
	private String custCodeDescr;
	@Transient
	private String detailJson;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String module; // 用来表示哪个模块调用了ItemApp
	@Transient
	private String doubleString;
	@Transient
	private String itemCode;
	@Transient
	private String itemCodeDescr;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private Boolean isAutoArriveDate; //是否自动计算预计发货日期
	@Transient
	private Boolean isTimeout; //发货是否超时(发货日期大于预计到货日期)
	@Transient
	private String delayResonUnshowValue; //不显示的延误原因，一般是【0.正常配送】 
	@Transient
	private String sendNo; //发货单号 
	@Transient
	private String custDocumentNo; //客户档案号
	@Transient
	private Integer custArea; //客户面积
	@Transient
	private String costRight; //成本权限
	@Transient
	private String projectDept2Descr; //项目经理二级部门名称
	@Transient
	private String czybh;
	@Transient
	private String whcodeDescr;
	@Transient
	private String confirmStatus;
	@Transient
	private String SplStatusDescr;
	@Transient
	private String itemType3;
	@Transient
	private String sqlCode;//品牌编号
	@Transient
	private String groupType;//统计方式 1 品牌、2 材料类型2、3 按材料类型3、4 材料名称  、5发货类型  ——add by zb 2018-07-25 增加发货类型
	@Transient
	private String department2;//
	@Transient
	private String containCmpCust;//是否包含公司内部客户，0.否 1.是
	@Transient
	private String itemRight;
	@Transient
	private String custType;
	@Transient
	private String diffQty;
	
	@Transient
	private String backType;
	@Transient
	private String openFrom;
	
	@Transient
	private String appCzyDescr;
	@Transient
	private String sendCfmStatus;
	
	@Transient
	private String useSupplierPlatform;
	@Transient
	private String unPrint;
	
	@Transient
	private String supplyRecvModel;
	
	@Transient
	private String oldSendType;
	@Transient
	private String oldSupplCodeDescr;
	@Transient
	private String oldWhcodeDescr;
	@Transient
	private String oldSupplCode;
	@Transient
	private String oldWhcode;
	@Transient
	private String wareHouseSendAuth;
	@Transient
	private String itemType;//查询材料类型
	@Transient
	private String manySendRsn;
	@Transient
	private String pPrint;
	@Transient
	private String checkStatus;
	@Transient
	private String canPass;//供应商确认时是否可以直接通过审核
	@Transient
	private Double splAmount;//对账金额
	@Transient
	private String puSplStatus;//采购供应商结算状态
	@Transient
	private String purchaseFeeDetailJson;//采购费用明细
	@Transient
	private String prjRegion;//工程大区
	@Transient
	private String checkConfirmRemarks;
	@Transient
	private String isAllApp; //是否限制全部已申请 add by zb on 20190805
	@Transient
	private String faultTypeDescr;
	@Transient
	private String faultManDescr;
	@Transient
	private Double prjQualityFee;
	@Transient
	private String isMaterialSendJob;
	
	@Transient
	private String intReplenishNo;
	
	public String getpPrint() {
		return pPrint;
	}

	public void setpPrint(String pPrint) {
		this.pPrint = pPrint;
	}

	public String getItemAppDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	
	public String getCustAddress() {
		return this.custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
	}
	public void setSendCzy(String sendCzy) {
		this.sendCzy = sendCzy;
	}
	
	public String getSendCzy() {
		return this.sendCzy;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	public String getSendType() {
		return this.sendType;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setPuno(String puno) {
		this.puno = puno;
	}
	
	public String getPuno() {
		return this.puno;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	
	public String getWhcode() {
		return this.whcode;
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
	public void setDelivType(String delivType) {
		this.delivType = delivType;
	}
	
	public String getDelivType() {
		return this.delivType;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	
	public String getProjectMan() {
		return this.projectMan;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
	}
	
	public String getOldNo() {
		return this.oldNo;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	
	public Double getOtherCost() {
		return this.otherCost;
	}
	public void setOtherCostAdj(Double otherCostAdj) {
		this.otherCostAdj = otherCostAdj;
	}
	
	public Double getOtherCostAdj() {
		return this.otherCostAdj;
	}
	public void setIsService(Integer isService) {
		this.isService = isService;
	}
	
	public Integer getIsService() {
		return this.isService;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
	public String getItemType2() {
		return this.itemType2;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setWhcheckOutNo(String whcheckOutNo) {
		this.whcheckOutNo = whcheckOutNo;
	}
	
	public String getWhcheckOutNo() {
		return this.whcheckOutNo;
	}
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setPrjCheckType(String prjCheckType) {
		this.prjCheckType = prjCheckType;
	}
	
	public String getPrjCheckType() {
		return this.prjCheckType;
	}
	public void setProjectOtherCost(Double projectOtherCost) {
		this.projectOtherCost = projectOtherCost;
	}
	
	public Double getProjectOtherCost() {
		return this.projectOtherCost;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	
	public String getIsSetItem() {
		return this.isSetItem;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setProjectAmount(Double projectAmount) {
		this.projectAmount = projectAmount;
	}
	
	public Double getProjectAmount() {
		return this.projectAmount;
	}
	public String getSupplCodeDescr() {
		return supplCodeDescr;
	}
	public void setSupplCodeDescr(String supplCodeDescr) {
		this.supplCodeDescr = supplCodeDescr;
	}
	public String getCustCodeDescr() {
		return custCodeDescr;
	}
	public void setCustCodeDescr(String custCodeDescr) {
		this.custCodeDescr = custCodeDescr;
	}

	public void setSplStatus(String splStatus) {
		this.splStatus = splStatus;
	}
	
	public String getSplStatus() {
		return splStatus;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setSplRemark(String splRemark) {
		this.splRemark = splRemark;
	}
	
	public String getSplRemark() {
		return splRemark;
	}
	public Date getConfirmDateFrom() {
		return confirmDateFrom;
	}
	public void setConfirmDateFrom(Date confirmDateFrom) {
		this.confirmDateFrom = confirmDateFrom;
	}
	public Date getConfirmDateTo() {
		return confirmDateTo;
	}
	public void setConfirmDateTo(Date confirmDateTo) {
		this.confirmDateTo = confirmDateTo;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getDoubleString() {
		return doubleString;
	}

	public void setDoubleString(String doubleString) {
		this.doubleString = doubleString;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeDescr() {
		return itemCodeDescr;
	}

	public void setItemCodeDescr(String itemCodeDescr) {
		this.itemCodeDescr = itemCodeDescr;
	}
	public Date getSendDateFrom() {
		return sendDateFrom;
	}
	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}
	public Date getSendDateTo() {
		return sendDateTo;
	}
	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setDelayReson(String delayReson) {
		this.delayReson = delayReson;
	}
	
	public String getDelayReson() {
		return delayReson;
	}
	
	public String getPreAppNo() {
		return preAppNo;
	}

	public void setPreAppNo(String preAppNo) {
		this.preAppNo = preAppNo;
	}

	public Boolean getIsAutoArriveDate() {
		return isAutoArriveDate;
	}

	public void setIsAutoArriveDate(Boolean isAutoArriveDate) {
		this.isAutoArriveDate = isAutoArriveDate;
	}

	public Boolean getIsTimeout() {
		return isTimeout;
	}

	public void setIsTimeout(Boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	public String getDelayResonUnshowValue() {
		return delayResonUnshowValue;
	}

	public void setDelayResonUnshowValue(String delayResonUnshowValue) {
		this.delayResonUnshowValue = delayResonUnshowValue;
	}

	public String getSendNo() {
		return sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}

	public String getCustDocumentNo() {
		return custDocumentNo;
	}

	public void setCustDocumentNo(String custDocumentNo) {
		this.custDocumentNo = custDocumentNo;
	}
	
	public Integer getCustArea() {
		return custArea;
	}

	public void setCustArea(Integer custArea) {
		this.custArea = custArea;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public String getProjectDept2Descr() {
		return projectDept2Descr;
	}

	public void setProjectDept2Descr(String projectDept2Descr) {
		this.projectDept2Descr = projectDept2Descr;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getWhcodeDescr() {
		return whcodeDescr;
	}

	public void setWhcodeDescr(String whcodeDescr) {
		this.whcodeDescr = whcodeDescr;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getDelayRemark() {
		return delayRemark;
	}

	public void setDelayRemark(String delayRemark) {
		this.delayRemark = delayRemark;
	}

	public Date getArriveSplDate() {
		return arriveSplDate;
	}

	public void setArriveSplDate(Date arriveSplDate) {
		this.arriveSplDate = arriveSplDate;
	}

	public String getSplStatusDescr() {
		return SplStatusDescr;
	}

	public void setSplStatusDescr(String splStatusDescr) {
		SplStatusDescr = splStatusDescr;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDiffQty() {
		return diffQty;
	}

	public void setDiffQty(String diffQty) {
		this.diffQty = diffQty;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(String openFrom) {
		this.openFrom = openFrom;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getSendBatchNo() {
		return sendBatchNo;
	}

	public void setSendBatchNo(String sendBatchNo) {
		this.sendBatchNo = sendBatchNo;
	}

	public String getFollowRemark() {
		return followRemark;
	}

	public void setFollowRemark(String followRemark) {
		this.followRemark = followRemark;
	}

	public String getSendCfmStatus() {
		return sendCfmStatus;
	}

	public void setSendCfmStatus(String sendCfmStatus) {
		this.sendCfmStatus = sendCfmStatus;
	}

	public Integer getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public String getPrintCZY() {
		return printCZY;
	}

	public void setPrintCZY(String printCZY) {
		this.printCZY = printCZY;
	}

	public String getUseSupplierPlatform() {
		return useSupplierPlatform;
	}

	public void setUseSupplierPlatform(String useSupplierPlatform) {
		this.useSupplierPlatform = useSupplierPlatform;
	}

	public String getUnPrint() {
		return unPrint;
	}

	public void setUnPrint(String unPrint) {
		this.unPrint = unPrint;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

	public String getSqlCode() {
		return sqlCode;
	}

	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getContainCmpCust() {
		return containCmpCust;
	}

	public void setContainCmpCust(String containCmpCust) {
		this.containCmpCust = containCmpCust;
	}

	public String getSupplyRecvModel() {
		return supplyRecvModel;
	}

	public void setSupplyRecvModel(String supplyRecvModel) {
		this.supplyRecvModel = supplyRecvModel;
	}

	public String getOldSendType() {
		return oldSendType;
	}

	public void setOldSendType(String oldSendType) {
		this.oldSendType = oldSendType;
	}

	public String getOldSupplCodeDescr() {
		return oldSupplCodeDescr;
	}

	public void setOldSupplCodeDescr(String oldSupplCodeDescr) {
		this.oldSupplCodeDescr = oldSupplCodeDescr;
	}

	public String getOldWhcodeDescr() {
		return oldWhcodeDescr;
	}

	public void setOldWhcodeDescr(String oldWhcodeDescr) {
		this.oldWhcodeDescr = oldWhcodeDescr;
	}

	public String getOldSupplCode() {
		return oldSupplCode;
	}

	public void setOldSupplCode(String oldSupplCode) {
		this.oldSupplCode = oldSupplCode;
	}

	public String getOldWhcode() {
		return oldWhcode;
	}

	public void setOldWhcode(String oldWhcode) {
		this.oldWhcode = oldWhcode;
	}

	public String getWareHouseSendAuth() {
		return wareHouseSendAuth;
	}

	public void setWareHouseSendAuth(String wareHouseSendAuth) {
		this.wareHouseSendAuth = wareHouseSendAuth;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSplRcvCZY() {
		return splRcvCZY;
	}

	public void setSplRcvCZY(String splRcvCZY) {
		this.splRcvCZY = splRcvCZY;
	}

	public Date getSplRcvDate() {
		return splRcvDate;
	}

	public void setSplRcvDate(Date splRcvDate) {
		this.splRcvDate = splRcvDate;
	}

	public String getManySendRsn() {
		return manySendRsn;
	}

	public void setManySendRsn(String manySendRsn) {
		this.manySendRsn = manySendRsn;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCanPass() {
		return canPass;
	}

	public void setCanPass(String canPass) {
		this.canPass = canPass;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public Double getSplAmount() {
		return splAmount;
	}

	public void setSplAmount(Double splAmount) {
		this.splAmount = splAmount;
	}

	public String getPuSplStatus() {
		return puSplStatus;
	}

	public void setPuSplStatus(String puSplStatus) {
		this.puSplStatus = puSplStatus;
	}

	public String getPurchaseFeeDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(purchaseFeeDetailJson);
		return xml;
	}

	public void setPurchaseFeeDetailJson(String purchaseFeeDetailJson) {
		this.purchaseFeeDetailJson = purchaseFeeDetailJson;
	}

	public String getPrjRegion() {
		return prjRegion;
	}

	public void setPrjRegion(String prjRegion) {
		this.prjRegion = prjRegion;
	}

	public String getCheckConfirmRemarks() {
		return checkConfirmRemarks;
	}

	public void setCheckConfirmRemarks(String checkConfirmRemarks) {
		this.checkConfirmRemarks = checkConfirmRemarks;
	}
	public String getIsAllApp() {
		return isAllApp;
	}

	public void setIsAllApp(String isAllApp) {
		this.isAllApp = isAllApp;
	}

	public String getWhReceiveCZY() {
		return whReceiveCZY;
	}

	public void setWhReceiveCZY(String whReceiveCZY) {
		this.whReceiveCZY = whReceiveCZY;
	}

	public Date getWhReceiveDate() {
		return whReceiveDate;
	}

	public void setWhReceiveDate(Date whReceiveDate) {
		this.whReceiveDate = whReceiveDate;
	}

	public String getEntrustProcStatus() {
		return entrustProcStatus;
	}

	public void setEntrustProcStatus(String entrustProcStatus) {
		this.entrustProcStatus = entrustProcStatus;
	}

	public Date getEntrustProcSendDate() {
		return entrustProcSendDate;
	}

	public void setEntrustProcSendDate(Date entrustProcSendDate) {
		this.entrustProcSendDate = entrustProcSendDate;
	}

	public Integer getIntRepPK() {
		return intRepPK;
	}

	public void setIntRepPK(Integer intRepPK) {
		this.intRepPK = intRepPK;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getFaultMan() {
		return faultMan;
	}

	public void setFaultMan(String faultMan) {
		this.faultMan = faultMan;
	}

	public String getFaultTypeDescr() {
		return faultTypeDescr;
	}

	public void setFaultTypeDescr(String faultTypeDescr) {
		this.faultTypeDescr = faultTypeDescr;
	}

	public String getFaultManDescr() {
		return faultManDescr;
	}

	public void setFaultManDescr(String faultManDescr) {
		this.faultManDescr = faultManDescr;
	}

	public Double getPrjQualityFee() {
		return prjQualityFee;
	}

	public void setPrjQualityFee(Double prjQualityFee) {
		this.prjQualityFee = prjQualityFee;
	}

	public String getIsMaterialSendJob() {
		return isMaterialSendJob;
	}

	public void setIsMaterialSendJob(String isMaterialSendJob) {
		this.isMaterialSendJob = isMaterialSendJob;
	}

    public String getIntReplenishNo() {
        return intReplenishNo;
    }

    public void setIntReplenishNo(String intReplenishNo) {
        this.intReplenishNo = intReplenishNo;
    }

	public Date getNotifySendDate() {
		return notifySendDate;
	}

	public void setNotifySendDate(Date notifySendDate) {
		this.notifySendDate = notifySendDate;
	}
	
}
