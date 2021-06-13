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
 * Purchase信息
 */
@Entity
@Table(name = "tPurchase")
public class Purchase extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Type")
	private String type;
	@Column(name = "Whcode")
	private String whcode;
	@Column(name = "Supplier")
	private String supplier;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "IsCheckOut")
	private String isCheckOut;
	@Column(name = "CheckOutNo")
	private String checkOutNo;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column (name = "DelivType")
	private String delivType;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "ProjectMan")
	private String projectMan;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "DelivWay")
	private String delivWay;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "FirstAmount")
	private Double firstAmount;
	@Column(name = "SecondAmount")
	private Double secondAmount;
	@Column(name = "RemainAmount")
	private Double remainAmount;
	@Column(name = "OtherCost")
	private Double otherCost;
	@Column(name = "OtherCostAdj")
	private Double otherCostAdj;
	@Column(name = "CheckSeq")
	private Integer checkSeq;
	@Column(name = "ArriveDate")
	private Date arriveDate;
	@Column(name = "ApplyMan")
	private String applyMan;
	@Column(name = "PayRemark")
	private String payRemark;
	@Column(name = "Sino")
	private String sino;
	@Column(name = "ArriveStatus")
	private String arriveStatus;
	@Column(name = "ArriveRemark")
	private String arriveRemark;
	@Column(name = "ProjectOtherCost")
	private Double projectOtherCost;
	@Column(name = "OverCost")
	private Double overCost;
	@Column(name = "ProjectAmount")
	private Double projectAmount;
	@Column(name = "SplAmount")
	private Double splAmount;
	@Column
	private String oldPUNo;
	@Column
	private Date confirmDate;
	@Column 
	private String confirmCZY;
	@Column(name="IsAppItem")
	private String isAppItem;
	@Column(name="AppItemDate")
	private Date appItemDate;
	@Column(name="AppItemMan")
	private String appItemMan;
	@Column(name="SplStatus")
	private String splStatus;//供应商状态
	@Column(name="AppCheckDate")
	private Date appCheckDate;//申请结算日期
	@Column(name="CheckConfirmDate")
	private Date checkConfirmDate;//结算审核日期
	@Column(name="CheckConfirmCZY")
	private String checkConfirmCZY;//结算审核人
	@Column(name="CheckConfirmRemarks")
	private String checkConfirmRemarks;//结算审核说明

	
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private Date arriveDateFrom;
	@Transient
	private Date arriveDateTo;
	@Transient
	private Date dateFrom1;
	@Transient
	private Date dateTo1;
	@Transient
	private String projectManDescr;
	@Transient
	private String applyManDescr;
	@Transient
	private String CZYDescr;
	@Transient
	private String address;
	@Transient
	private String typeDescr;
	@Transient
	private String statusDescr;
	@Transient
	private String documentNo;
	@Transient
	private String unSelected;
	@Transient
	private String supplierDescr;
	@Transient
	private String splcode;
	@Transient
	private String detailJson;
	@Transient
	private int temp;
	@Transient
	private String WHCodeDescr;
	@Transient
	private String supDescr;
	@Transient
	private String czybh;
	@Transient
	private double thearrivqty;
	@Transient
	private double allqty;
	@Transient
	private String itemtypedescr;
	@Transient
	private String oldNo;
	@Transient
	private String sumArrivQty;
	@Transient
	private String itCode;
	@Transient
	private String itemType2;
	@Transient
	private String isService;
	@Transient
	private String saleType;
	@Transient
	private String splStatusDescr;
	@Transient
	private String scStatusDescr;
	@Transient
	private String deliveType;//判断采购到工地 或 仓库
	@Transient
	private String purType;
	@Transient
	private String custDescr;
	@Transient
	private String remainTime;
	@Transient
	private String supplierDepartment2;//供应商所属战队 by zjf 
	@Transient
	private String department2;
	@Transient
	private String itemRight;
	@Transient
	private Date appItemDateFrom;
	@Transient
	private Date appItemDateTo;
	@Transient
	private String itemType12;
	@Transient
	private String fromPage;
	@Transient
	private String readonly;
	@Transient
	private String isSupplPrepaySelect; //预付金调用条件
	@Transient
	private String generateType;
	@Transient
	private String supplFeeType;
	@Transient
	private Date appCheckDateFrom; //申请结算时间从
	@Transient
	private Date appCheckDateTo;
	@Transient
	private String IANo;//领料单号
	@Transient
	private String isIncludeSetComp;//包含结算完成 add by zb on 20190411
	@Transient
	private String checkOutStatus; //供应商结算状态
	@Transient
	private String tableData;
	@Transient
	private String purchFeeNo;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private String logoName;
	@Transient
	private String custType;
	@Transient
	private String statistcsMethod;
	@Transient
	private String costRight;
	
	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
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

	public String getPurchFeeNo() {
		return purchFeeNo;
	}

	public void setPurchFeeNo(String purchFeeNo) {
		this.purchFeeNo = purchFeeNo;
	}

	public String getTableData() {
		return tableData;
	}

	public void setTableData(String tableData) {
		this.tableData = tableData;
	}

	public String getItemType12() {
		return itemType12;
	}

	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}

	public String getIsAppItem() {
		return isAppItem;
	}

	public void setIsAppItem(String isAppItem) {
		this.isAppItem = isAppItem;
	}

	public Date getAppItemDate() {
		return appItemDate;
	}

	public void setAppItemDate(Date appItemDate) {
		this.appItemDate = appItemDate;
	}

	public String getAppItemMan() {
		return appItemMan;
	}

	public void setAppItemMan(String appItemMan) {
		this.appItemMan = appItemMan;
	}

	public Date getAppItemDateFrom() {
		return appItemDateFrom;
	}

	public void setAppItemDateFrom(Date appItemDateFrom) {
		this.appItemDateFrom = appItemDateFrom;
	}

	public Date getAppItemDateTo() {
		return appItemDateTo;
	}

	public void setAppItemDateTo(Date appItemDateTo) {
		this.appItemDateTo = appItemDateTo;
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

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getProjectManDescr() {
		return projectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}

	public String getCZYDescr() {
		return CZYDescr;
	}

	public void setCZYDescr(String cZYDescr) {
		CZYDescr = cZYDescr;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getCustDescr() {
		return custDescr;
	}

	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}

	public String getPurType() {
		return purType;
	}

	public void setPurType(String purType) {
		this.purType = purType;
	}

	public String getDeliveType() {
		return deliveType;
	}

	public void setDeliveType(String deliveType) {
		this.deliveType = deliveType;
	}

	public String getPurchaseDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	
	public String getWhcode() {
		return this.whcode;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public String getSupplier() {
		return this.supplier;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	
	public String getIsCheckOut() {
		return this.isCheckOut;
	}
	public void setCheckOutNo(String checkOutNo) {
		this.checkOutNo = checkOutNo;
	}
	
	public String getCheckOutNo() {
		return this.checkOutNo;
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
	
	public String getDelivType() {
		return delivType;
	}

	public void setDelivType(String delivType) {
		this.delivType = delivType;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
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
	public void setDelivWay(String delivWay) {
		this.delivWay = delivWay;
	}
	
	public String getDelivWay() {
		return this.delivWay;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public Double getFirstAmount() {
		return this.firstAmount;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}
	
	public Double getSecondAmount() {
		return this.secondAmount;
	}
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	public Double getRemainAmount() {
		return this.remainAmount;
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
	public void setCheckSeq(Integer checkSeq) {
		this.checkSeq = checkSeq;
	}
	
	public Integer getCheckSeq() {
		return this.checkSeq;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	public Date getArriveDate() {
		return this.arriveDate;
	}
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	
	public String getApplyMan() {
		return this.applyMan;
	}
	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}
	
	public String getPayRemark() {
		return this.payRemark;
	}
	public void setSino(String sino) {
		this.sino = sino;
	}
	
	public String getSino() {
		return this.sino;
	}
	public void setArriveStatus(String arriveStatus) {
		this.arriveStatus = arriveStatus;
	}
	
	public String getArriveStatus() {
		return this.arriveStatus;
	}
	public void setArriveRemark(String arriveRemark) {
		this.arriveRemark = arriveRemark;
	}
	
	public String getArriveRemark() {
		return this.arriveRemark;
	}
	public void setProjectOtherCost(Double projectOtherCost) {
		this.projectOtherCost = projectOtherCost;
	}
	
	public Double getProjectOtherCost() {
		return this.projectOtherCost;
	}
	public void setOverCost(Double overCost) {
		this.overCost = overCost;
	}
	
	public Double getOverCost() {
		return this.overCost;
	}
	public void setProjectAmount(Double projectAmount) {
		this.projectAmount = projectAmount;
	}
	
	public Double getProjectAmount() {
		return this.projectAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getUnSelected() {
		return unSelected;
	}

	public void setUnSelected(String unSelected) {
		this.unSelected = unSelected;
	}

	public String getSupplierDescr() {
		return supplierDescr;
	}

	public void setSupplierDescr(String supplierDescr) {
		this.supplierDescr = supplierDescr;
	}

	public Double getSplAmount() {
		return splAmount;
	}

	public void setSplAmount(Double splAmount) {
		this.splAmount = splAmount;
	}

	public String getTypeDescr() {
		return typeDescr;
	}

	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}

	public String getStatusDescr() {
		return statusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}

	public String getSplcode() {
		return splcode;
	}

	public void setSplcode(String splcode) {
		this.splcode = splcode;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getOldPUNo() {
		return oldPUNo;
	}

	public void setOldPUNo(String oldPUNo) {
		this.oldPUNo = oldPUNo;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public String getApplyManDescr() {
		return applyManDescr;
	}

	public void setApplyManDescr(String applyManDescr) {
		this.applyManDescr = applyManDescr;
	}

	public String getWHCodeDescr() {
		return WHCodeDescr;
	}

	public void setWHCodeDescr(String wHCodeDescr) {
		WHCodeDescr = wHCodeDescr;
	}

	public String getSupDescr() {
		return supDescr;
	}

	public void setSupDescr(String supDescr) {
		this.supDescr = supDescr;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public double getThearrivqty() {
		return thearrivqty;
	}

	public void setThearrivqty(double thearrivqty) {
		this.thearrivqty = thearrivqty;
	}

	public double getAllqty() {
		return allqty;
	}

	public void setAllqty(double allqty) {
		this.allqty = allqty;
	}

	public Date getArriveDateFrom() {
		return arriveDateFrom;
	}

	public void setArriveDateFrom(Date arriveDateFrom) {
		this.arriveDateFrom = arriveDateFrom;
	}

	public Date getArriveDateTo() {
		return arriveDateTo;
	}

	public void setArriveDateTo(Date arriveDateTo) {
		this.arriveDateTo = arriveDateTo;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmCZY() {
		return confirmCZY;
	}

	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}

	public Date getDateFrom1() {
		return dateFrom1;
	}

	public void setDateFrom1(Date dateFrom1) {
		this.dateFrom1 = dateFrom1;
	}

	public Date getDateTo1() {
		return dateTo1;
	}

	public void setDateTo1(Date dateTo1) {
		this.dateTo1 = dateTo1;
	}

	public String getItemtypedescr() {
		return itemtypedescr;
	}

	public void setItemtypedescr(String itemtypedescr) {
		this.itemtypedescr = itemtypedescr;
	}

	public String getOldNo() {
		return oldNo;
	}

	public void setOldNo(String oldNo) {
		this.oldNo = oldNo;
	}

	public String getSumArrivQty() {
		return sumArrivQty;
	}

	public void setSumArrivQty(String sumArrivQty) {
		this.sumArrivQty = sumArrivQty;
	}

	public String getItCode() {
		return itCode;
	}

	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getIsService() {
		return isService;
	}

	public void setIsService(String isService) {
		this.isService = isService;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getSplStatusDescr() {
		return splStatusDescr;
	}

	public void setSplStatusDescr(String splStatusDescr) {
		this.splStatusDescr = splStatusDescr;
	}

	public String getScStatusDescr() {
		return scStatusDescr;
	}

	public void setScStatusDescr(String scStatusDescr) {
		this.scStatusDescr = scStatusDescr;
	}

	public String getSupplierDepartment2() {
		return supplierDepartment2;
	}

	public void setSupplierDepartment2(String supplierDepartment2) {
		this.supplierDepartment2 = supplierDepartment2;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getIsSupplPrepaySelect() {
		return isSupplPrepaySelect;
	}

	public void setIsSupplPrepaySelect(String isSupplPrepaySelect) {
		this.isSupplPrepaySelect = isSupplPrepaySelect;
	}
	
	public String getGenerateType() {
		return generateType;
	}

	public void setGenerateType(String generateType) {
		this.generateType = generateType;
	}

	public String getSupplFeeType() {
		return supplFeeType;
	}

	public void setSupplFeeType(String supplFeeType) {
		this.supplFeeType = supplFeeType;
	}

	public String getSplStatus() {
		return splStatus;
	}

	public void setSplStatus(String splStatus) {
		this.splStatus = splStatus;
	}

	public Date getAppCheckDate() {
		return appCheckDate;
	}

	public void setAppCheckDate(Date appCheckDate) {
		this.appCheckDate = appCheckDate;
	}

	public Date getCheckConfirmDate() {
		return checkConfirmDate;
	}

	public void setCheckConfirmDate(Date checkConfirmDate) {
		this.checkConfirmDate = checkConfirmDate;
	}

	public String getCheckConfirmCZY() {
		return checkConfirmCZY;
	}

	public void setCheckConfirmCZY(String checkConfirmCZY) {
		this.checkConfirmCZY = checkConfirmCZY;
	}

	public String getCheckConfirmRemarks() {
		return checkConfirmRemarks;
	}

	public void setCheckConfirmRemarks(String checkConfirmRemarks) {
		this.checkConfirmRemarks = checkConfirmRemarks;
	}

	public Date getAppCheckDateFrom() {
		return appCheckDateFrom;
	}

	public void setAppCheckDateFrom(Date appCheckDateFrom) {
		this.appCheckDateFrom = appCheckDateFrom;
	}

	public Date getAppCheckDateTo() {
		return appCheckDateTo;
	}

	public void setAppCheckDateTo(Date appCheckDateTo) {
		this.appCheckDateTo = appCheckDateTo;
	}

	public String getIANo() {
		return IANo;
	}

	public void setIANo(String iANo) {
		IANo = iANo;
	}

	public String getIsIncludeSetComp() {
		return isIncludeSetComp;
	}

	public void setIsIncludeSetComp(String isIncludeSetComp) {
		this.isIncludeSetComp = isIncludeSetComp;
	}

	public String getCheckOutStatus() {
		return checkOutStatus;
	}

	public void setCheckOutStatus(String checkOutStatus) {
		this.checkOutStatus = checkOutStatus;
	}

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	
	
	
}
