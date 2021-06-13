package com.house.home.bean.insales;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Purchase信息bean
 */
public class PurchaseBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="采购单号", order=1)
	private String no;
	@ExcelAnnotation(exportName="采购单状态", order=2)
	private String statusdescr;
    	@ExcelAnnotation(exportName="到货日期", pattern="yyyy-MM-dd HH:mm:ss", order=3)
	private Date date;
	@ExcelAnnotation(exportName="采购类型", order=4)
	private String typeDescr;
	@ExcelAnnotation(exportName="仓库编号", order=5)
	private String whcode;
	@ExcelAnnotation(exportName="供应商编号", order=6)
	private String supplier;
	@ExcelAnnotation(exportName="总价", order=7)
	private Double amount;
	@ExcelAnnotation(exportName="备注", order=8)
	private String remarks;
	@ExcelAnnotation(exportName="是否结算", order=9)
	private String yesno1;
	@ExcelAnnotation(exportName="结算单号", order=10)
	private String checkOutNo;
    	@ExcelAnnotation(exportName="最后修改时间", pattern="yyyy-MM-dd HH:mm:ss", order=11)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="最后修改人", order=12)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期", order=13)
	private String expired;
	@ExcelAnnotation(exportName="操作类型", order=14)
	private String actionLog;
	@ExcelAnnotation(exportName="配送地点", order=15)
	private String delivtypedescr;
	@ExcelAnnotation(exportName="客户编号", order=16)
	private String custCode;
	@ExcelAnnotation(exportName="业务员", order=17)
	private String projectMan;
	@ExcelAnnotation(exportName="手机号码", order=18)
	private String phone;
	@ExcelAnnotation(exportName="配送方式", order=19)
	private String delivWay;
	@ExcelAnnotation(exportName="材料类型", order=20)
	private String itemtypedescr;
	@ExcelAnnotation(exportName="已付定金", order=21)
	private Double firstAmount;
	@ExcelAnnotation(exportName="已付到货款", order=22)
	private Double secondAmount;
	@ExcelAnnotation(exportName="应付金额", order=23)
	private Double remainAmount;
	@ExcelAnnotation(exportName="其他费用", order=24)
	private Double otherCost;
	@ExcelAnnotation(exportName="其他费用调整", order=25)
	private Double otherCostAdj;
	@ExcelAnnotation(exportName="结算顺序号", order=26)
	private Integer checkSeq;
    	@ExcelAnnotation(exportName="到货日期", pattern="yyyy-MM-dd HH:mm:ss", order=27)
	private Date arriveDate;
	@ExcelAnnotation(exportName="下单员", order=28)
	private String applyMan;
	@ExcelAnnotation(exportName="记账说明", order=29)
	private String payRemark;
	@ExcelAnnotation(exportName="销售单号", order=30)
	private String sino;
	@ExcelAnnotation(exportName="到货状态", order=31)
	private String arriveStatus;
	@ExcelAnnotation(exportName="到货备注", order=32)
	private String arriveRemark;
	@ExcelAnnotation(exportName="项目经理其他费用", order=33)
	private Double projectOtherCost;
	@ExcelAnnotation(exportName="成本超出额", order=34)
	private Double overCost;
	@ExcelAnnotation(exportName="项目经理材料总价", order=35)
	private Double projectAmount;
	

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	
	public String getStatusdescr() {
		return statusdescr;
	}

	public void setStatusdescr(String statusdescr) {
		this.statusdescr = statusdescr;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
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

	public String getItemtypedescr() {
		return itemtypedescr;
	}

	public void setItemtypedescr(String itemtypedescr) {
		this.itemtypedescr = itemtypedescr;
	}

	public String getTypeDescr() {
		return typeDescr;
	}

	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}

	public String getYesno1() {
		return yesno1;
	}

	public void setYesno1(String yesno1) {
		this.yesno1 = yesno1;
	}

	public String getDelivtypedescr() {
		return delivtypedescr;
	}

	public void setDelivtypedescr(String delivtypedescr) {
		this.delivtypedescr = delivtypedescr;
	}

	

}
