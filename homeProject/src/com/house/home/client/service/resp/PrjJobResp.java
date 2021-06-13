package com.house.home.client.service.resp;

import java.util.Date;

public class PrjJobResp extends BaseResponse{
	private String no;
	private String custCode;
	private String address;
	private String itemType1;
	private String itemType1Descr;
	private String jobType;
	private String jobTypeDescr;
	private String remarks;
	private Date date;
	private String status;
	private String statusDescr;
	private String dealCzy;
	private String dealCzyDescr;
	private Date planDate;
	private Date dealDate;
	private String dealRemark;
	private String endCode;
	private String endCodeDescr;
	private String applyEm;
	private String warBrand;
	private String cupBrand;
	
	private String customerDescr;
	private String customerPhone;
	private String supplierDescr;
	private String supplierPhone;
	private String projectManDescr;
	private String projectManPhone;
	private String isNeedSuppl;
	private String isDispCustPhn;
	private String canEndCust;
	private String isNeedReq;
	private String errPosi;
	private Date recvDate;
	private Date completeDate;
	private String supplRemarks;
	
	public String getSupplRemarks() {
		return supplRemarks;
	}
	public void setSupplRemarks(String supplRemarks) {
		this.supplRemarks = supplRemarks;
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
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobTypeDescr() {
		return jobTypeDescr;
	}
	public void setJobTypeDescr(String jobTypeDescr) {
		this.jobTypeDescr = jobTypeDescr;
	}
	public String getDealCzy() {
		return dealCzy;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	public String getDealCzyDescr() {
		return dealCzyDescr;
	}
	public void setDealCzyDescr(String dealCzyDescr) {
		this.dealCzyDescr = dealCzyDescr;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealRemark() {
		return dealRemark;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
	public String getEndCode() {
		return endCode;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	public String getEndCodeDescr() {
		return endCodeDescr;
	}
	public void setEndCodeDescr(String endCodeDescr) {
		this.endCodeDescr = endCodeDescr;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getItemType1Descr() {
		return itemType1Descr;
	}
	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplyEm() {
		return applyEm;
	}
	public void setApplyEm(String applyEm) {
		this.applyEm = applyEm;
	}
	public String getWarBrand() {
		return warBrand;
	}
	public void setWarBrand(String warBrand) {
		this.warBrand = warBrand;
	}
	public String getCupBrand() {
		return cupBrand;
	}
	public void setCupBrand(String cupBrand) {
		this.cupBrand = cupBrand;
	}
	public String getCustomerDescr() {
		return customerDescr;
	}
	public void setCustomerDescr(String customerDescr) {
		this.customerDescr = customerDescr;
	}
	public String getSupplierDescr() {
		return supplierDescr;
	}
	public void setSupplierDescr(String supplierDescr) {
		this.supplierDescr = supplierDescr;
	}
	public String getSupplierPhone() {
		return supplierPhone;
	}
	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getProjectManPhone() {
		return projectManPhone;
	}
	public void setProjectManPhone(String projectManPhone) {
		this.projectManPhone = projectManPhone;
	}
	public String getIsNeedSuppl() {
		return isNeedSuppl;
	}
	public void setIsNeedSuppl(String isNeedSuppl) {
		this.isNeedSuppl = isNeedSuppl;
	}
	public String getIsDispCustPhn() {
		return isDispCustPhn;
	}
	public void setIsDispCustPhn(String isDispCustPhn) {
		this.isDispCustPhn = isDispCustPhn;
	}
	public String getCanEndCust() {
		return canEndCust;
	}
	public void setCanEndCust(String canEndCust) {
		this.canEndCust = canEndCust;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getIsNeedReq() {
		return isNeedReq;
	}
	public void setIsNeedReq(String isNeedReq) {
		this.isNeedReq = isNeedReq;
	}
	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	
}
