package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * BaseItemChg信息
 */
@Entity
@Table(name = "tBaseItemChg")
public class BaseItemChg extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "Date")
	private Date date;
	@Column(name = "BefAmount")
	private Double befAmount;
	@Column(name = "DiscAmount")
	private Double discAmount;
	@Column(name = "Amount")
	private Double amount;
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
	@Column(name = "ManageFee")
	private Double manageFee;
	@Column(name = "SetMinus")
	private Double setMinus;
	@Column(name = "PerfPK")
	private Integer perfPk;
	@Column(name = "IscalPerf")
	private String iscalPerf;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	@Column(name="BaseDiscPer")
	private double baseDiscPer;
	@Column(name="ChgBaseDiscPer")
	private double chgBaseDiscPer;
	@Column(name="PrjStatus")
	private String prjStatus; //项目经理提交状态
	@Column(name="IsAddAllInfo")
	private String isAddAllInfo; //非独立销售
	@Column(name = "Tax")
	private Double tax;
	@Column(name = "TempNo")
	private String tempNo;
	
	// 部门经理确认人
	@Column(name = "DeptLeaderConfirmCZY")
	private String deptLeaderConfirmCzy;
	
	// 部门经理确认日期
	@Column(name = "DeptLeaderConfirmDate")
	private Date deptLeaderConfirmDate;
	
	@Transient
	private String address;
	
	@Transient
	private Integer dispSeq;
	@Transient
	private String applyManDept;//申请人部门
	
	
	public String getApplyManDept() {
		return applyManDept;
	}
	public void setApplyManDept(String applyManDept) {
		this.applyManDept = applyManDept;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getDeptLeaderConfirmCzy() {
        return deptLeaderConfirmCzy;
    }
    public void setDeptLeaderConfirmCzy(String deptLeaderConfirmCzy) {
        this.deptLeaderConfirmCzy = deptLeaderConfirmCzy;
    }
    public Date getDeptLeaderConfirmDate() {
        return deptLeaderConfirmDate;
    }
    public void setDeptLeaderConfirmDate(Date deptLeaderConfirmDate) {
        this.deptLeaderConfirmDate = deptLeaderConfirmDate;
    }
    @Transient
	private String fixAreaPk;
	@Transient
	private String custType;
	@Transient
	private String custTypeType;//1、清单客户 2、套餐客户
	@Transient
	private String documentNo;
	@Transient
	private String costRight;
	@Transient
	private String descr;
	@Transient
	private String appCzyDescr;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private double baseFeeDirct;
	@Transient 
	private String canUseComBaseItem;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String baseItemChgType1;
	@Transient
	private String baseItemChgType2;
	@Transient
	private String code;
	@Transient
	private String empCode;//员工编号
	@Transient
	private String empDescr;//员工描述
	@Transient
	private String chgStakeholderList;//干系人列表XML
	@Transient
	private Integer area;//楼盘面积
	@Transient 
	private String isRegenCanreplace ;//可替换项是否替换
	@Transient 
	private String isRegenCanmodiQty ;//可替换项是否替换
	@Transient
	private String detailJson;
	@Transient
	private String tempDescr;
	@Transient
	private String signQuoteType;
	@Transient
	private String baseTempNo;
	
	
	public String getBaseTempNo() {
		return baseTempNo;
	}
	public void setBaseTempNo(String baseTempNo) {
		this.baseTempNo = baseTempNo;
	}
	public String getSignQuoteType() {
		return signQuoteType;
	}
	public void setSignQuoteType(String signQuoteType) {
		this.signQuoteType = signQuoteType;
	}
	public String getTempDescr() {
		return tempDescr;
	}
	public void setTempDescr(String tempDescr) {
		this.tempDescr = tempDescr;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getIsRegenCanreplace() {
		return isRegenCanreplace;
	}
	public void setIsRegenCanreplace(String isRegenCanreplace) {
		this.isRegenCanreplace = isRegenCanreplace;
	}
	public String getIsRegenCanmodiQty() {
		return isRegenCanmodiQty;
	}
	public void setIsRegenCanmodiQty(String isRegenCanmodiQty) {
		this.isRegenCanmodiQty = isRegenCanmodiQty;
	}
	public String getTempNo() {
		return tempNo;
	}
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBaseItemChgType1() {
		return baseItemChgType1;
	}
	public void setBaseItemChgType1(String baseItemChgType1) {
		this.baseItemChgType1 = baseItemChgType1;
	}
	public String getBaseItemChgType2() {
		return baseItemChgType2;
	}
	public void setBaseItemChgType2(String baseItemChgType2) {
		this.baseItemChgType2 = baseItemChgType2;
	}
	public String getCanUseComBaseItem() {
		return canUseComBaseItem;
	}
	public void setCanUseComBaseItem(String canUseComBaseItem) {
		this.canUseComBaseItem = canUseComBaseItem;
	}

	public double getBaseFeeDirct() {
		return baseFeeDirct;
	}

	public void setBaseFeeDirct(double baseFeeDirct) {
		this.baseFeeDirct = baseFeeDirct;
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

	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}
	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
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
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setBefAmount(Double befAmount) {
		this.befAmount = befAmount;
	}
	
	public Double getBefAmount() {
		return this.befAmount;
	}
	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}
	
	public Double getDiscAmount() {
		return this.discAmount;
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
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	
	public Double getManageFee() {
		return this.manageFee;
	}
	public void setSetMinus(Double setMinus) {
		this.setMinus = setMinus;
	}
	
	public Double getSetMinus() {
		return this.setMinus;
	}
	public void setPerfPk(Integer perfPk) {
		this.perfPk = perfPk;
	}
	
	public Integer getPerfPk() {
		return this.perfPk;
	}
	public void setIscalPerf(String iscalPerf) {
		this.iscalPerf = iscalPerf;
	}
	
	public String getIscalPerf() {
		return this.iscalPerf;
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

	public String getCustTypeType() {
		return custTypeType;
	}

	public void setCustTypeType(String custTypeType) {
		this.custTypeType = custTypeType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpDescr() {
		return empDescr;
	}
	public void setEmpDescr(String empDescr) {
		this.empDescr = empDescr;
	}
	public String getChgStakeholderList() {
		String xml = XmlConverUtil.jsonToXmlNoHead(chgStakeholderList);
    	return xml;
	}
	public void setChgStakeholderList(String chgStakeholderList) {
		this.chgStakeholderList = chgStakeholderList;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getBaseItemChgXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	
}
