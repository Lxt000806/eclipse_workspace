package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * ItemApp信息bean
 */
public class ItemAppBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="No", order=1)
	private String no;
	@ExcelAnnotation(exportName="类型", order=2)
	private String type;
	@ExcelAnnotation(exportName="材料类型1", order=3)
	private String itemType1;
	@ExcelAnnotation(exportName="客户编号", order=4)
	private String custCode;
	@ExcelAnnotation(exportName="Status", order=5)
	private String status;
	@ExcelAnnotation(exportName="申请人员", order=6)
	private String appCzy;
    	@ExcelAnnotation(exportName="申请日期", pattern="yyyy-MM-dd HH:mm:ss", order=7)
	private Date date;
	@ExcelAnnotation(exportName="confirmCzy", order=8)
	private String confirmCzy;
    	@ExcelAnnotation(exportName="confirmDate", pattern="yyyy-MM-dd HH:mm:ss", order=9)
	private Date confirmDate;
	@ExcelAnnotation(exportName="发货人员", order=10)
	private String sendCzy;
    	@ExcelAnnotation(exportName="发货日期", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date sendDate;
	@ExcelAnnotation(exportName="发货类型", order=12)
	private String sendType;
	@ExcelAnnotation(exportName="供应商代码", order=13)
	private String supplCode;
	@ExcelAnnotation(exportName="采购单号", order=14)
	private String puno;
	@ExcelAnnotation(exportName="仓库编号", order=15)
	private String whcode;
	@ExcelAnnotation(exportName="Remarks", order=16)
	private String remarks;
    	@ExcelAnnotation(exportName="LastUpdate", pattern="yyyy-MM-dd HH:mm:ss", order=17)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="LastUpdatedBy", order=18)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="Expired", order=19)
	private String expired;
	@ExcelAnnotation(exportName="ActionLog", order=20)
	private String actionLog;
	@ExcelAnnotation(exportName="配送方式", order=21)
	private String delivType;
	@ExcelAnnotation(exportName="项目经理", order=22)
	private String projectMan;
	@ExcelAnnotation(exportName="电话号码", order=23)
	private String phone;
	@ExcelAnnotation(exportName="原批次号", order=24)
	private String oldNo;
	@ExcelAnnotation(exportName="其它费用（付供应商）", order=25)
	private Double otherCost;
	@ExcelAnnotation(exportName="其它费用调整（付供应商）", order=26)
	private Double otherCostAdj;
	@ExcelAnnotation(exportName="是否服务性产品", order=27)
	private Integer isService;
	@ExcelAnnotation(exportName="材料类型2", order=28)
	private String itemType2;
	@ExcelAnnotation(exportName="是否记账", order=29)
	private String isCheckOut;
	@ExcelAnnotation(exportName="仓库发货记账单号", order=30)
	private String whcheckOutNo;
	@ExcelAnnotation(exportName="结算顺序号", order=31)
	private Integer checkSeq;
	@ExcelAnnotation(exportName="项目经理结算类型", order=32)
	private String prjCheckType;
	@ExcelAnnotation(exportName="项目经理其它费用", order=33)
	private Double projectOtherCost;
	@ExcelAnnotation(exportName="是否套餐材料", order=34)
	private String isSetItem;
	@ExcelAnnotation(exportName="材料总价", order=35)
	private Double amount;
	@ExcelAnnotation(exportName="项目经理材料总价", order=36)
	private Double projectAmount;

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

}
