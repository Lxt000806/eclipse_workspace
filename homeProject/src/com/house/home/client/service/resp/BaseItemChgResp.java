package com.house.home.client.service.resp;

import java.util.Date;

import com.house.framework.commons.utils.XmlConverUtil;
/**
 * 基装增减Bean
 * @author created by zb
 * @date   2019-3-7--下午4:04:00
 */
public class BaseItemChgResp extends BaseResponse {
	private String no;
	private String custCode;
	private String status;
	private Date date;
	private Double befAmount;
	private Double discAmount;
	private Double amount;
	private String remarks;
	private Date lastUpdate;
	private String lastUpdatedBy;
	private String expired;
	private String actionLog;
	private Double manageFee;
	private Double setMinus;
	private Integer perfPk;
	private String iscalPerf;
	private String appCzy;
	private Date confirmDate;
	private String confirmCzy;
	private double baseDiscPer;
	private double chgBaseDiscPer;
	private String prjStatus; //项目经理提交状态
	
	private String address;
	private String fixAreaPk;
	private String custType;
	private String custTypeType;//1、清单客户 2、套餐客户
	private String documentNo;
	private String costRight;
	private String descr;
	private String appCzyDescr;
	private String confirmCzyDescr;
	private double baseFeeDirct;
	private String canUseComBaseItem;
	
	private String statusDescr;
	private String prjStatusDescr;
	private Date changeDate; // 变更日期
	private String detailJson; // 批量json转xml
	private String m_umState; //保存标识
	//custType
	private Double manageFeeBasePer; //ManageFee_BasePer 基础管理费系数
	private Double chgManageFeePer; //增减管理费系数
	private Double longFeePer; //远程费系数
	private String longFeeCode; //xtcs 远程费编码
	private boolean haveBaseChgRight; //false:该增减单含有非套餐内减项，您没有权限进行编辑操作
	private Double baseCompAmount;
	
	public Double getBaseCompAmount() {
		return baseCompAmount;
	}
	public void setBaseCompAmount(Double baseCompAmount) {
		this.baseCompAmount = baseCompAmount;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getBefAmount() {
		return befAmount;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	public Double getDiscAmount() {
		return discAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getSetMinus() {
		return setMinus;
	}
	public void setSetMinus(Double setMinus) {
		this.setMinus = setMinus;
	}
	public Integer getPerfPk() {
		return perfPk;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	public String getIscalPerf() {
		return iscalPerf;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getConfirmCzy() {
		return confirmCzy;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	public double getBaseDiscPer() {
		return baseDiscPer;
	}
	public void setBaseDiscPer(double baseDiscPer) {
		this.baseDiscPer = baseDiscPer;
	}
	public double getChgBaseDiscPer() {
		return chgBaseDiscPer;
	}
	public void setChgBaseDiscPer(double chgBaseDiscPer) {
		this.chgBaseDiscPer = chgBaseDiscPer;
	}
	public String getPrjStatus() {
		return prjStatus;
	}
	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFixAreaPk() {
		return fixAreaPk;
	}
	public void setFixAreaPk(String fixAreaPk) {
		this.fixAreaPk = fixAreaPk;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustTypeType() {
		return custTypeType;
	}
	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getCostRight() {
		return costRight;
	}
	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAppCzyDescr() {
		return appCzyDescr;
	}
	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}
	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}
	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}
	public double getBaseFeeDirct() {
		return baseFeeDirct;
	}
	public void setBaseFeeDirct(double baseFeeDirct) {
		this.baseFeeDirct = baseFeeDirct;
	}
	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}
	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getPrjStatusDescr() {
		return prjStatusDescr;
	}
	public void setPrjStatusDescr(String prjStatusDescr) {
		this.prjStatusDescr = prjStatusDescr;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
	public Double getManageFeeBasePer() {
		return manageFeeBasePer;
	}
	public void setManageFeeBasePer(Double manageFeeBasePer) {
		this.manageFeeBasePer = manageFeeBasePer;
	}
	public Double getChgManageFeePer() {
		return chgManageFeePer;
	}
	public void setChgManageFeePer(Double chgManageFeePer) {
		this.chgManageFeePer = chgManageFeePer;
	}
	public Double getLongFeePer() {
		return longFeePer;
	}
	public void setLongFeePer(Double longFeePer) {
		this.longFeePer = longFeePer;
	}
	public String getLongFeeCode() {
		return longFeeCode;
	}
	public void setLongFeeCode(String longFeeCode) {
		this.longFeeCode = longFeeCode;
	}
	public boolean isHaveBaseChgRight() {
		return haveBaseChgRight;
	}
	public void setHaveBaseChgRight(boolean haveBaseChgRight) {
		this.haveBaseChgRight = haveBaseChgRight;
	}
	
	
}
