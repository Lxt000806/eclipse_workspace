package com.house.home.entity.finance;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tAsset")
public class Asset extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Status")
	private String status;
	@Column(name = "AssetType")
	private String assetType;
	@Column(name = "Model")
	private String model;
	@Column(name = "Uom")
	private String uom;
	@Column(name = "AddType")
	private String addType;
	@Column(name = "Department")
	private String department;
	@Column(name = "UseMan")
	private String useMan;
	@Column(name = "Address")
	private String address;
	@Column(name = "Qty")
	private Double qty;
	@Column(name = "OriginalValue")
	private Double originalValue;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "TotalDeprAmount")
	private Double totalDeprAmount;
	@Column(name = "UseYear")
	private Integer useYear;
	@Column(name = "DeprType")
	private String deprType;
	@Column(name = "DeprMonth")
	private Integer deprMonth;
	@Column(name = "RemainDeprMonth")
	private Integer remainDeprMonth;
	@Column(name = "NetValue")
	private Double netValue;
	@Column(name = "DeprAmountPerMonth")
	private Double deprAmountPerMonth;
	@Column(name = "CreateCZY")
	private String createCZY;
	@Column(name = "CreateDate")
	private Date createDate;
	@Column(name = "DecType")
	private String decType;
	@Column(name = "DecCZY")
	private String decCZY;
	@Column(name = "DecDate")
	private Date decDate;
	@Column(name = "DecRemarks")
	private String decRemarks;
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
	
	@Transient
	private String deptDescr;
	@Transient
	private String useManDescr;
	@Transient
	private String decCzyDescr;
	@Transient
	private String createCzyDescr;
	@Transient
	private String chgType;
	@Transient
	private String period;
	@Transient
	private String periodFrom;
	@Transient 
	private String periodTo;
	@Transient
	private String remCode;
	@Transient
	private String assetTypeDescr;
	@Transient
	private Date decDateFrom;
	@Transient
	private Date decDateTo;
	@Transient
	private String cmpCode;
	@Transient
	private Date createDateFrom;
	@Transient
	private Date createDateTo;
	
	public String getCmpCode() {
		return cmpCode;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	public Date getDecDateFrom() {
		return decDateFrom;
	}
	public void setDecDateFrom(Date decDateFrom) {
		this.decDateFrom = decDateFrom;
	}
	public Date getDecDateTo() {
		return decDateTo;
	}
	public void setDecDateTo(Date decDateTo) {
		this.decDateTo = decDateTo;
	}
	public String getAssetTypeDescr() {
		return assetTypeDescr;
	}
	public void setAssetTypeDescr(String assetTypeDescr) {
		this.assetTypeDescr = assetTypeDescr;
	}
	public String getRemCode() {
		return remCode;
	}
	public void setRemCode(String remCode) {
		this.remCode = remCode;
	}
	public String getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}
	public String getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getChgType() {
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getDeptDescr() {
		return deptDescr;
	}
	public void setDeptDescr(String deptDescr) {
		this.deptDescr = deptDescr;
	}
	public String getUseManDescr() {
		return useManDescr;
	}
	public void setUseManDescr(String useManDescr) {
		this.useManDescr = useManDescr;
	}
	public String getDecCzyDescr() {
		return decCzyDescr;
	}
	public void setDecCzyDescr(String decCzyDescr) {
		this.decCzyDescr = decCzyDescr;
	}
	public String getCreateCzyDescr() {
		return createCzyDescr;
	}
	public void setCreateCzyDescr(String createCzyDescr) {
		this.createCzyDescr = createCzyDescr;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getUseMan() {
		return useMan;
	}
	public void setUseMan(String useMan) {
		this.useMan = useMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public Double getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(Double originalValue) {
		this.originalValue = originalValue;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Double getTotalDeprAmount() {
		return totalDeprAmount;
	}
	public void setTotalDeprAmount(Double totalDeprAmount) {
		this.totalDeprAmount = totalDeprAmount;
	}
	public Integer getUseYear() {
		return useYear;
	}
	public void setUseYear(Integer useYear) {
		this.useYear = useYear;
	}
	public String getDeprType() {
		return deprType;
	}
	public void setDeprType(String deprType) {
		this.deprType = deprType;
	}
	public Integer getDeprMonth() {
		return deprMonth;
	}
	public void setDeprMonth(Integer deprMonth) {
		this.deprMonth = deprMonth;
	}
	public Integer getRemainDeprMonth() {
		return remainDeprMonth;
	}
	public void setRemainDeprMonth(Integer remainDeprMonth) {
		this.remainDeprMonth = remainDeprMonth;
	}
	public Double getNetValue() {
		return netValue;
	}
	public void setNetValue(Double netValue) {
		this.netValue = netValue;
	}
	public Double getDeprAmountPerMonth() {
		return deprAmountPerMonth;
	}
	public void setDeprAmountPerMonth(Double deprAmountPerMonth) {
		this.deprAmountPerMonth = deprAmountPerMonth;
	}
	public String getCreateCZY() {
		return createCZY;
	}
	public void setCreateCZY(String createCZY) {
		this.createCZY = createCZY;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDecType() {
		return decType;
	}
	public void setDecType(String decType) {
		this.decType = decType;
	}
	public String getDecCZY() {
		return decCZY;
	}
	public void setDecCZY(String decCZY) {
		this.decCZY = decCZY;
	}
	public Date getDecDate() {
		return decDate;
	}
	public void setDecDate(Date decDate) {
		this.decDate = decDate;
	}
	public String getDecRemarks() {
		return decRemarks;
	}
	public void setDecRemarks(String decRemarks) {
		this.decRemarks = decRemarks;
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
	public Date getCreateDateFrom() {
		return createDateFrom;
	}
	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}
	public Date getCreateDateTo() {
		return createDateTo;
	}
	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}
	
}
