package com.house.home.client.service.resp;


import java.util.Date;

public class FixDutyResp extends BaseResponse{
	
	private String custCode;
	private Integer custWkPk;
	private String appCZY;
	private String appWorkerCode;
	private Date prjConfirmDate;
	private String prjMan;
	private String prjRemark;
	private Date cfmDate;
	private String cfmMan;
	private String cfmRemark;
	private Double cfmOfferPrj;
	private Double cfmMaterial;
	private String dutyMan;
	private Date dutyDate;
	private Date manageCfmDate;
	private String manageCfmMan;
	private String provideMan;
	private Date provideDate;
	private Date lastUpdate;
	private String lastUpdatedBy;
	private String expired;
	private String actionLog;
	private String workType12Descr;
	private String appManDescr;
	private String appManTypeDescr;
	private String baseCheckItemCode;
	private String baseCheckItemDescr;
	private String uomDescr;
	private double offerPri;  //人工单价
	private double material;
	private String remarks;
	private Date appDate; 
	private double price;
	private double cfmPrice;
	private String status;
	private String statusDescr;
	private String no;
	private double qty;
	private double offerPrj; //人工金额
	private String nameChi; //工人名字
	private String address;
	private String remark;
	private String workType12;
	private String regionDescr;
	private String regionCode;
	private String isPrjAllDuty; //全部承担费用
	private String cfmReturnRemark;
	private String cancelRemark;
	private String fixDutyManRemark;
	private String code;
	private String descr;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getFixDutyManRemark() {
		return fixDutyManRemark;
	}
	public void setFixDutyManRemark(String fixDutyManRemark) {
		this.fixDutyManRemark = fixDutyManRemark;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public String getCfmReturnRemark() {
		return cfmReturnRemark;
	}
	public void setCfmReturnRemark(String cfmReturnRemark) {
		this.cfmReturnRemark = cfmReturnRemark;
	}
	public String getRegionDescr() {
		return regionDescr;
	}
	public void setRegionDescr(String regionDescr) {
		this.regionDescr = regionDescr;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public String getAppWorkerCode() {
		return appWorkerCode;
	}
	public void setAppWorkerCode(String appWorkerCode) {
		this.appWorkerCode = appWorkerCode;
	}
	public Date getPrjConfirmDate() {
		return prjConfirmDate;
	}
	public void setPrjConfirmDate(Date prjConfirmDate) {
		this.prjConfirmDate = prjConfirmDate;
	}
	public String getPrjMan() {
		return prjMan;
	}
	public void setPrjMan(String prjMan) {
		this.prjMan = prjMan;
	}
	public String getPrjRemark() {
		return prjRemark;
	}
	public void setPrjRemark(String prjRemark) {
		this.prjRemark = prjRemark;
	}
	public Date getCfmDate() {
		return cfmDate;
	}
	public void setCfmDate(Date cfmDate) {
		this.cfmDate = cfmDate;
	}
	public String getCfmMan() {
		return cfmMan;
	}
	public void setCfmMan(String cfmMan) {
		this.cfmMan = cfmMan;
	}
	public String getCfmRemark() {
		return cfmRemark;
	}
	public void setCfmRemark(String cfmRemark) {
		this.cfmRemark = cfmRemark;
	}
	public Double getCfmOfferPrj() {
		return cfmOfferPrj;
	}
	public void setCfmOfferPrj(Double cfmOfferPrj) {
		this.cfmOfferPrj = cfmOfferPrj;
	}
	public Double getCfmMaterial() {
		return cfmMaterial;
	}
	public void setCfmMaterial(Double cfmMaterial) {
		this.cfmMaterial = cfmMaterial;
	}
	public String getDutyMan() {
		return dutyMan;
	}
	public void setDutyMan(String dutyMan) {
		this.dutyMan = dutyMan;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public Date getManageCfmDate() {
		return manageCfmDate;
	}
	public void setManageCfmDate(Date manageCfmDate) {
		this.manageCfmDate = manageCfmDate;
	}
	public String getManageCfmMan() {
		return manageCfmMan;
	}
	public void setManageCfmMan(String manageCfmMan) {
		this.manageCfmMan = manageCfmMan;
	}
	public String getProvideMan() {
		return provideMan;
	}
	public void setProvideMan(String provideMan) {
		this.provideMan = provideMan;
	}
	public Date getProvideDate() {
		return provideDate;
	}
	public void setProvideDate(Date provideDate) {
		this.provideDate = provideDate;
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
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getAppManDescr() {
		return appManDescr;
	}
	public void setAppManDescr(String appManDescr) {
		this.appManDescr = appManDescr;
	}
	public String getAppManTypeDescr() {
		return appManTypeDescr;
	}
	public void setAppManTypeDescr(String appManTypeDescr) {
		this.appManTypeDescr = appManTypeDescr;
	}
	public String getBaseCheckItemCode() {
		return baseCheckItemCode;
	}
	public void setBaseCheckItemCode(String baseCheckItemCode) {
		this.baseCheckItemCode = baseCheckItemCode;
	}
	public String getBaseCheckItemDescr() {
		return baseCheckItemDescr;
	}
	public void setBaseCheckItemDescr(String baseCheckItemDescr) {
		this.baseCheckItemDescr = baseCheckItemDescr;
	}
	public String getUomDescr() {
		return uomDescr;
	}
	public void setUomDescr(String uomDescr) {
		this.uomDescr = uomDescr;
	}
	public double getOfferPri() {
		return offerPri;
	}
	public void setOfferPri(double offerPri) {
		this.offerPri = offerPri;
	}
	public double getMaterial() {
		return material;
	}
	public void setMaterial(double material) {
		this.material = material;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCfmPrice() {
		return cfmPrice;
	}
	public void setCfmPrice(double cfmPrice) {
		this.cfmPrice = cfmPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getOfferPrj() {
		return offerPrj;
	}
	public void setOfferPrj(double offerPrj) {
		this.offerPrj = offerPrj;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsPrjAllDuty() {
		return isPrjAllDuty;
	}
	public void setIsPrjAllDuty(String isPrjAllDuty) {
		this.isPrjAllDuty = isPrjAllDuty;
	}
	

}
