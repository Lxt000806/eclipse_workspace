package com.house.home.entity.project;

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
 * ItemPreMeasure信息
 */
@Entity
@Table(name = "tItemPreMeasure")
public class ItemPreMeasure extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "PreAppNo")
	private String preAppNo;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "MeasureRemark")
	private String measureRemark;
	@Column(name = "Date")
	private Date date;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "RecvDate")
	private Date recvDate;
	@Column(name = "CompleteDate")
	private Date completeDate;
	@Column(name = "completeCZY")
	private String CompleteCZY;
	@Column(name = "CancelRemark")
	private String cancelRemark;
	@Column(name = "DelayReson")
	private String delayReson;
	@Column(name = "ChgStatus")
	private String chgStatus;
	@Column(name = "ChgCompleteDate")
	private Date chgCompleteDate;
	@Column(name = "InformDate")
	private Date informDate;
	
	@Transient
	private String address;
	@Transient
	private String projectManDescr;
	@Transient
	private String phone;

	//20171016领料预申请管理BS zzr
	@Transient
	private String appCzyDescr;
	@Transient
	private String supplerDescr;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String completeCzyDescr;
	
	@Transient
	private String unShowStatus;
	@Transient
	private String completeCzy;
	@Transient
	private String itemType1;
	@Transient
	private String empCode;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	
	@Transient
	private String isService;
	@Transient
	private String isSetItem;
	@Transient
	private String projectMan;
	@Transient
	private String itemAppStatus;
	@Transient
	private String itemAppType;
	@Transient
	private String custCode;
	
	@Transient
	private String costRight;
	@Transient
	private String itemRight;
	@Transient
	private String serviceTip;
	
	@Transient
	private String appNo;
	@Transient
	private String documentNo;
	@Transient
	private Integer area;
	@Transient
	private String department1Descr;
	@Transient
	private String department2Descr;
	@Transient
	private String delivType;
	@Transient
	private String sendType;
	@Transient
	private String wareHouseCode;
	@Transient
	private String wareHouseDescr;
	@Transient
	private Double otherCost;
	
	@Transient
	private String hasInSetReq;
	@Transient
	private String canInPlan;
	@Transient
	private String canOutPlan;
	
	@Transient
	private String itemType2;
	@Transient
	private String isAllSend;
	@Transient
	private String pks;
	
	@Transient
	private String czybh;
	
	@Transient
	private String preAppDTPks;
	@Transient
	private String reqPks;
	@Transient
	private String itemCodes;

	@Transient
	private String itemCode;
	@Transient
	private String brand;
	@Transient
	private String barCode;
	@Transient
	private String itemDescr;
	@Transient
	private String sizeDesc;
	@Transient
	private String model;
	@Transient
	private String itemType3;
	
	@Transient
	private String isCmpCustCode;
	@Transient
	private String sendTypeCheck;
	@Transient
	private String applyQtyCheck;
	
	@Transient
	private String isSend;
	
	@Transient
	private String custCodeDescr;
	@Transient
	private String isQueryEmpty; 
	@Transient
	private String m_checkStatus;
	
	@Transient
	private String sendCzy;
	@Transient
	private String sendCzyDescr;
	@Transient
	private Date sendDate;
	@Transient
	private Double otherCostAdj;
	@Transient
	private String puno;
	@Transient
	private Date signDate;
	@Transient
	private String materWorkType2;
	@Transient
	private String custType;
	@Transient
	private String splRemark;
	
	@Transient
	private String splStatus;
	@Transient
	private String splStatusDescr;
	@Transient
	private Date arriveDate;
	@Transient
	private Date arriveSplDate;
	
	@Transient
	private String department2;
	
	@Transient
	private String itemRightForSupplier;//供应商选择权限控制,为了不与itemRight冲突另外设置
	@Transient
	private String continuityAdd;//新增领料界面连续新增标志
	
	@Transient
	private String addFromPage;//跳转到领料新增页面名称

	@Transient
	private String splRcvCZY;
	@Transient
	private Date splRcvDate;
	@Transient
	private String splRcvCZYDescr;
	
	@Transient
	private Integer sendCount;
	@Transient
	private String isCupboard;
	@Transient
	private String isSpecReq;
	@Transient
	private String seqpks;
	@Transient
	private String isAutoOrder;//是否为自动拆单的新增
	@Transient
	private String  confirmRemark;//审核说明
	@Transient
	private String  refCustCode;//关联客户编号
	@Transient
	private String  refCustCodeDescr;//关联客户名
	@Transient
	private String  refAddress;//关联楼盘
	@Transient
	private String region;// 片区
	@Transient
	private String prjRegion;// 工程大区
	@Transient
	private String isSubCheck;// 是否直接提交审核
	@Transient
	private String msgText;// 消息通知
	@Transient
	private String whReceiveCzyDescr;//收货人
	@Transient
	private Date whReceiveDate;// 收货时间
	@Transient
	private String regionCode;//区域
	@Transient
	private String allcost;//总成本
	@Transient
	private Integer intRepPK;//补货明细pk
	@Transient
	private String repRemarks;//补货明细详情
	@Transient
	private String toCmItemAppType; //领料单类型 1：正常单2：补货单
	@Transient
	private String faultType;
	@Transient
	private String faultMan;
	@Transient
	private String faultTypeDescr;
	@Transient
	private String faultManDescr;
	@Transient
	private Double prjQualityFee;
	@Transient
	private String callType;// 调用类型 默认领料调用，1、预算调用
	@Transient
	private String refProjectMan;
	@Transient
	private String refProjectManDescr;

	@Transient
	private String from;
	@Transient
	private String no;
	@Transient
	private String informManager;// 是否通知管家
	@Transient
	private String informManagerCode; // 通知管家的员工编号

	@Transient
	private String exceptionRemarks;
	@Transient
	private String exceptionCost;
	@Transient
	private String exceptionPayNum;
	@Transient
	private String exceptionAmount;
	
	public Date getChgCompleteDate() {
		return chgCompleteDate;
	}

	public void setChgCompleteDate(Date chgCompleteDate) {
		this.chgCompleteDate = chgCompleteDate;
	}

	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}

	public String getChgStatus() {
		return chgStatus;
	}

	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setPreAppNo(String preAppNo) {
		this.preAppNo = preAppNo;
	}
	
	public String getPreAppNo() {
		return this.preAppNo;
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
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setMeasureRemark(String measureRemark) {
		this.measureRemark = measureRemark;
	}
	
	public String getMeasureRemark() {
		return this.measureRemark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAppCzy() {
		return appCzy;
	}

	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}

	public String getConfirmCzy() {
		return confirmCzy;
	}

	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRecvDate() {
		return recvDate;
	}

	public void setRecvDate(Date recvDate) {
		this.recvDate = recvDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompleteCZY() {
		return CompleteCZY;
	}

	public void setCompleteCZY(String completeCZY) {
		CompleteCZY = completeCZY;
	}

	public String getProjectManDescr() {
		return projectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getDelayReson() {
		return delayReson;
	}

	public void setDelayReson(String delayReson) {
		this.delayReson = delayReson;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getSupplerDescr() {
		return supplerDescr;
	}

	public void setSupplerDescr(String supplerDescr) {
		this.supplerDescr = supplerDescr;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}

	public String getCompleteCzyDescr() {
		return completeCzyDescr;
	}

	public void setCompleteCzyDescr(String completeCzyDescr) {
		this.completeCzyDescr = completeCzyDescr;
	}

	public String getUnShowStatus() {
		return unShowStatus;
	}

	public void setUnShowStatus(String unShowStatus) {
		this.unShowStatus = unShowStatus;
	}

	public String getCompleteCzy() {
		return completeCzy;
	}

	public void setCompleteCzy(String completeCzy) {
		this.completeCzy = completeCzy;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getIsService() {
		return isService;
	}

	public void setIsService(String isService) {
		this.isService = isService;
	}

	public String getIsSetItem() {
		return isSetItem;
	}

	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getItemAppStatus() {
		return itemAppStatus;
	}

	public void setItemAppStatus(String itemAppStatus) {
		this.itemAppStatus = itemAppStatus;
	}

	public String getItemAppType() {
		return itemAppType;
	}

	public void setItemAppType(String itemAppType) {
		this.itemAppType = itemAppType;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public String getServiceTip() {
		return serviceTip;
	}

	public void setServiceTip(String serviceTip) {
		this.serviceTip = serviceTip;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getDepartment1Descr() {
		return department1Descr;
	}

	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getDelivType() {
		return delivType;
	}

	public void setDelivType(String delivType) {
		this.delivType = delivType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getWareHouseCode() {
		return wareHouseCode;
	}

	public void setWareHouseCode(String wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}

	public String getWareHouseDescr() {
		return wareHouseDescr;
	}

	public void setWareHouseDescr(String wareHouseDescr) {
		this.wareHouseDescr = wareHouseDescr;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getHasInSetReq() {
		return hasInSetReq;
	}

	public void setHasInSetReq(String hasInSetReq) {
		this.hasInSetReq = hasInSetReq;
	}

	public String getCanInPlan() {
		return canInPlan;
	}

	public void setCanInPlan(String canInPlan) {
		this.canInPlan = canInPlan;
	}

	public String getCanOutPlan() {
		return canOutPlan;
	}

	public void setCanOutPlan(String canOutPlan) {
		this.canOutPlan = canOutPlan;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getIsAllSend() {
		return isAllSend;
	}

	public void setIsAllSend(String isAllSend) {
		this.isAllSend = isAllSend;
	}

	public String getPks() {
		return pks;
	}

	public void setPks(String pks) {
		this.pks = pks;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getPreAppDTPks() {
		return preAppDTPks;
	}

	public void setPreAppDTPks(String preAppDTPks) {
		this.preAppDTPks = preAppDTPks;
	}

	public String getReqPks() {
		return reqPks;
	}

	public void setReqPks(String reqPks) {
		this.reqPks = reqPks;
	}

	public String getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getSizeDesc() {
		return sizeDesc;
	}

	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

	public String getIsCmpCustCode() {
		return isCmpCustCode;
	}

	public void setIsCmpCustCode(String isCmpCustCode) {
		this.isCmpCustCode = isCmpCustCode;
	}

	public String getSendTypeCheck() {
		return sendTypeCheck;
	}

	public void setSendTypeCheck(String sendTypeCheck) {
		this.sendTypeCheck = sendTypeCheck;
	}

	public String getApplyQtyCheck() {
		return applyQtyCheck;
	}

	public void setApplyQtyCheck(String applyQtyCheck) {
		this.applyQtyCheck = applyQtyCheck;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getCustCodeDescr() {
		return custCodeDescr;
	}

	public void setCustCodeDescr(String custCodeDescr) {
		this.custCodeDescr = custCodeDescr;
	}

	public String getIsQueryEmpty() {
		return isQueryEmpty;
	}

	public void setIsQueryEmpty(String isQueryEmpty) {
		this.isQueryEmpty = isQueryEmpty;
	}

	public String getM_checkStatus() {
		return m_checkStatus;
	}

	public void setM_checkStatus(String m_checkStatus) {
		this.m_checkStatus = m_checkStatus;
	}

	public String getSendCzy() {
		return sendCzy;
	}

	public void setSendCzy(String sendCzy) {
		this.sendCzy = sendCzy;
	}

	public String getSendCzyDescr() {
		return sendCzyDescr;
	}

	public void setSendCzyDescr(String sendCzyDescr) {
		this.sendCzyDescr = sendCzyDescr;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Double getOtherCostAdj() {
		return otherCostAdj;
	}

	public void setOtherCostAdj(Double otherCostAdj) {
		this.otherCostAdj = otherCostAdj;
	}

	public String getPuno() {
		return puno;
	}

	public void setPuno(String puno) {
		this.puno = puno;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getMaterWorkType2() {
		return materWorkType2;
	}

	public void setMaterWorkType2(String materWorkType2) {
		this.materWorkType2 = materWorkType2;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSplRemark() {
		return splRemark;
	}

	public void setSplRemark(String splRemark) {
		this.splRemark = splRemark;
	}

	public String getSplStatus() {
		return splStatus;
	}

	public void setSplStatus(String splStatus) {
		this.splStatus = splStatus;
	}

	public String getSplStatusDescr() {
		return splStatusDescr;
	}

	public void setSplStatusDescr(String splStatusDescr) {
		this.splStatusDescr = splStatusDescr;
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	public Date getArriveSplDate() {
		return arriveSplDate;
	}

	public void setArriveSplDate(Date arriveSplDate) {
		this.arriveSplDate = arriveSplDate;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getItemRightForSupplier() {
		return itemRightForSupplier;
	}

	public void setItemRightForSupplier(String itemRightForSupplier) {
		this.itemRightForSupplier = itemRightForSupplier;
	}

	public String getContinuityAdd() {
		return continuityAdd;
	}

	public void setContinuityAdd(String continuityAdd) {
		this.continuityAdd = continuityAdd;
	}

	public String getAddFromPage() {
		return addFromPage;
	}

	public void setAddFromPage(String addFromPage) {
		this.addFromPage = addFromPage;
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

	public String getSplRcvCZYDescr() {
		return splRcvCZYDescr;
	}

	public void setSplRcvCZYDescr(String splRcvCZYDescr) {
		this.splRcvCZYDescr = splRcvCZYDescr;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public String getIsSpecReq() {
		return isSpecReq;
	}

	public void setIsSpecReq(String isSpecReq) {
		this.isSpecReq = isSpecReq;
	}

	public String getSeqpks() {
		return seqpks;
	}

	public void setSeqpks(String seqpks) {
		this.seqpks = seqpks;
	}

	public String getIsAutoOrder() {
		return isAutoOrder;
	}

	public void setIsAutoOrder(String isAutoOrder) {
		this.isAutoOrder = isAutoOrder;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public String getRefCustCode() {
		return refCustCode;
	}

	public void setRefCustCode(String refCustCode) {
		this.refCustCode = refCustCode;
	}

	public String getRefCustCodeDescr() {
		return refCustCodeDescr;
	}

	public void setRefCustCodeDescr(String refCustCodeDescr) {
		this.refCustCodeDescr = refCustCodeDescr;
	}

	public String getRefAddress() {
		return refAddress;
	}

	public void setRefAddress(String refAddress) {
		this.refAddress = refAddress;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPrjRegion() {
		return prjRegion;
	}

	public void setPrjRegion(String prjRegion) {
		this.prjRegion = prjRegion;
	}

	public String getIsSubCheck() {
		return isSubCheck;
	}

	public void setIsSubCheck(String isSubCheck) {
		this.isSubCheck = isSubCheck;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getWhReceiveCzyDescr() {
		return whReceiveCzyDescr;
	}

	public void setWhReceiveCzyDescr(String whReceiveCzyDescr) {
		this.whReceiveCzyDescr = whReceiveCzyDescr;
	}

	public Date getWhReceiveDate() {
		return whReceiveDate;
	}

	public void setWhReceiveDate(Date whReceiveDate) {
		this.whReceiveDate = whReceiveDate;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAllcost() {
		return allcost;
	}

	public void setAllcost(String allcost) {
		this.allcost = allcost;
	}

	public Integer getIntRepPK() {
		return intRepPK;
	}

	public void setIntRepPK(Integer intRepPK) {
		this.intRepPK = intRepPK;
	}

	public String getRepRemarks() {
		return repRemarks;
	}

	public void setRepRemarks(String repRemarks) {
		this.repRemarks = repRemarks;
	}

	public String getToCmItemAppType() {
		return toCmItemAppType;
	}

	public void setToCmItemAppType(String toCmItemAppType) {
		this.toCmItemAppType = toCmItemAppType;
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

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getRefProjectMan() {
		return refProjectMan;
	}

	public void setRefProjectMan(String refProjectMan) {
		this.refProjectMan = refProjectMan;
	}

	public String getRefProjectManDescr() {
		return refProjectManDescr;
	}

	public void setRefProjectManDescr(String refProjectManDescr) {
		this.refProjectManDescr = refProjectManDescr;
	}


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getInformManager() {
		return informManager;
	}

	public void setInformManager(String informManager) {
		this.informManager = informManager;
	}

	public String getInformManagerCode() {
		return informManagerCode;
	}

	public void setInformManagerCode(String informManagerCode) {
		this.informManagerCode = informManagerCode;
	}

	public String getExceptionRemarks() {
		return exceptionRemarks;
	}

	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
	}

	public String getExceptionCost() {
		return exceptionCost;
	}

	public void setExceptionCost(String exceptionCost) {
		this.exceptionCost = exceptionCost;
	}

	public String getExceptionPayNum() {
		return exceptionPayNum;
	}

	public void setExceptionPayNum(String exceptionPayNum) {
		this.exceptionPayNum = exceptionPayNum;
	}

	public String getExceptionAmount() {
		return exceptionAmount;
	}

	public void setExceptionAmount(String exceptionAmount) {
		this.exceptionAmount = exceptionAmount;
	}
	
}
