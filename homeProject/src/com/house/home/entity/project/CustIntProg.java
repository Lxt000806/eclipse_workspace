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
 * CustIntProg信息
 */
@Entity
@Table(name = "tCustIntProg")
public class CustIntProg extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "CupSpl")
	private String cupSpl;
	@Column(name = "IntSpl")
	private String intSpl;
	@Column(name = "DoorSpl")
	private String doorSpl;
	@Column(name = "CupAppDate")
	private Date cupAppDate;
	@Column(name = "IntAppDate")
	private Date intAppDate;
	@Column(name = "DoorAppDate")
	private Date doorAppDate;
	@Column(name = "TableSpl")
	private String tableSpl;
	@Column(name = "TableAppDate")
	private Date tableAppDate;
	@Column(name = "EcoArea")
	private Double ecoArea;
	@Column(name = "MdfArea")
	private Double mdfArea;
	@Column(name = "OtherArea")
	private Double otherArea;
	@Column(name = "CupMaterial")
	private String cupMaterial;
	@Column(name = "IntMaterial")
	private String intMaterial;
	@Column(name = "CupSendDays")
	private Integer cupSendDays;
	@Column(name = "IntSendDays")
	private Integer intSendDays;
	@Column(name = "CupSendDate")
	private Date cupSendDate;
	@Column(name = "IntSendDate")
	private Date intSendDate;
	@Column(name = "CupInstallDate")
	private Date cupInstallDate;
	@Column(name = "IntInstallDate")
	private Date intInstallDate;
	@Column(name = "CupDeliverDate")
	private Date cupDeliverDate;
	@Column(name = "IntDeliverDate")
	private Date intDeliverDate;
	@Column(name = "CupCheckDate")
	private Date cupCheckDate;
	@Column(name = "IntCheckDate")
	private Date intCheckDate;
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
	@Column(name = "DelayRemarks")
	private String delayRemarks;
	@Column(name = "TableSendDate")
	private Date tableSendDate;
	@Column(name = "TableInstallDate")
	private Date tableInstallDate;
	@Column(name="IntDesignDate")
	private Date intDesignDate;
	@Column(name="cupDesignDate")
	private Date cupDesignDate;
	
	
	@Transient
	private String Address;
	@Transient
	private String Status;
	@Transient
	private String StatusDescr;
	@Transient
	private String Layout;
	@Transient
	private String Layoutdescr;
	@Transient
	private String Area;
	@Transient
	private String CustType;
	@Transient
	private String CustTypeDescr;
	@Transient
	private String DesignDept1;
	@Transient
	private String DesignDept1Descr;
	@Transient
	private String DesignMan;
	@Transient
	private String DesignManDescr;
	@Transient
	private String PrjDept1Descr;
	@Transient
	private String ProjectMan;
	@Transient
	private String ProjectManDescr;
	@Transient
	private String IntDept2Descr;
	@Transient
	private String CupsplDescr;
	@Transient
	private String IntsplDescr;
	@Transient
	private String DoorsplDescr;
	@Transient
	private String tablespldescr;
	@Transient
	private String IntDesignDescr;
	@Transient
	private String IntPlanManDescr;
	@Transient
	private String NowIntProgDescr;
	@Transient
	private String NowCupProgDescr;
	@Transient
	private Date ConfirmBegin;
	@Transient
	private Date MeasureAppDate;
	@Transient
	private Date MeasureCupAppDate;
	@Transient
	private String IntAppDays;
	@Transient
	private String CupAppDays;
	@Transient
	private String AppDelayDdays;
	@Transient
	private String CupAppDelayDdays;
	@Transient
	private String IntProduceDays;
	@Transient
	private String CupProduceDays;
	@Transient
	private String IntSendDelayDays;
	@Transient
	private String CupSendDelayDays;
	@Transient
	private String IntDeliverDays;
	@Transient
	private String CupDeliverDays;
	@Transient
	private String IntAllDays;
	@Transient
	private String CupAllDays;
	@Transient
	private String IntInstallDelayDays;
	@Transient
	private String CupInstallDelayDays;
	@Transient
	private String AllPlan;
	@Transient
	private String OrderAmount;
	@Transient
	private String addreduceAll;
	@Transient
	private String IntYs;
	@Transient
	private String CupYs;
	@Transient
	private String CupXd;
	@Transient
	private String CupZj;
	@Transient
	private String IntXd;
	@Transient
	private String IntZj;
	@Transient
	private String IsErrorDescr;
	@Transient
	private String NoInstallDescr;
	@Transient
	private String IsPayDescr;
	@Transient
	private String IsBusiDescr;
	@Transient
	private String IsReturnDescr;
	@Transient
	private String IsCmplDescr;
	@Transient
	private String IsSaleDescr;
	@Transient
	private Date DealDate;
	@Transient
	private Date CupDealDate;
	@Transient
	private String CupDesignDescr;
	@Transient
	private String SupplCode;
	@Transient
	private Date buyDateFrom;
	@Transient
	private Date buyDateTo;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private Date installDateFrom;
	@Transient
	private Date installDateTo;
	@Transient
	private Date deliverDateFrom;
	@Transient
	private Date deliverDateTo;
	@Transient
	private Date checkDateFrom;
	@Transient
	private Date checkDateTo;
	@Transient
	private Date measureAppDateFrom;
	@Transient
	private Date measureAppDateTo;
	@Transient
	private Date confirmBeginFrom;
	@Transient
	private Date confirmBeginTo;
	@Transient
	private Date dealDateFrom;
	@Transient
	private Date dealDateTo;
	@Transient
	private String checkDelayDays;
	@Transient
	private Date intPlanSendDate;
	@Transient
	private Date cupPlanSendDate;
	@Transient
	private String intMeasureDays;
	@Transient
	private String cupInstallDays;
	@Transient
	private String intInstallDays;
	@Transient
	private String intMeasureStandardDays;
	@Transient
	private String cupMeasureDays;
	@Transient
	private String cupMeasureStandardDays;
	@Transient
	private String intProgDetailJson;
	@Transient
	private String costRight;
	@Transient
	private Date designDateFrom;
	@Transient
	private Date designDateTo;
	
	
	public Date getIntDesignDate() {
		return intDesignDate;
	}

	public void setIntDesignDate(Date intDesignDate) {
		this.intDesignDate = intDesignDate;
	}

	public Date getCupDesignDate() {
		return cupDesignDate;
	}

	public void setCupDesignDate(Date cupDesignDate) {
		this.cupDesignDate = cupDesignDate;
	}

	public Date getDesignDateFrom() {
		return designDateFrom;
	}

	public void setDesignDateFrom(Date designDateFrom) {
		this.designDateFrom = designDateFrom;
	}

	public Date getDesignDateTo() {
		return designDateTo;
	}

	public void setDesignDateTo(Date designDateTo) {
		this.designDateTo = designDateTo;
	}

	public Date getIntPlanSendDate() {
		return intPlanSendDate;
	}

	public void setIntPlanSendDate(Date intPlanSendDate) {
		this.intPlanSendDate = intPlanSendDate;
	}

	public Date getCupPlanSendDate() {
		return cupPlanSendDate;
	}

	public void setCupPlanSendDate(Date cupPlanSendDate) {
		this.cupPlanSendDate = cupPlanSendDate;
	}

	public String getIntMeasureDays() {
		return intMeasureDays;
	}

	public void setIntMeasureDays(String intMeasureDays) {
		this.intMeasureDays = intMeasureDays;
	}

	public String getCupInstallDays() {
		return cupInstallDays;
	}

	public void setCupInstallDays(String cupInstallDays) {
		this.cupInstallDays = cupInstallDays;
	}

	public String getIntInstallDays() {
		return intInstallDays;
	}

	public void setIntInstallDays(String intInstallDays) {
		this.intInstallDays = intInstallDays;
	}

	public String getIntMeasureStandardDays() {
		return intMeasureStandardDays;
	}

	public void setIntMeasureStandardDays(String intMeasureStandardDays) {
		this.intMeasureStandardDays = intMeasureStandardDays;
	}

	public String getCupMeasureDays() {
		return cupMeasureDays;
	}

	public void setCupMeasureDays(String cupMeasureDays) {
		this.cupMeasureDays = cupMeasureDays;
	}

	public String getCupMeasureStandardDays() {
		return cupMeasureStandardDays;
	}

	public void setCupMeasureStandardDays(String cupMeasureStandardDays) {
		this.cupMeasureStandardDays = cupMeasureStandardDays;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setCupSpl(String cupSpl) {
		this.cupSpl = cupSpl;
	}
	
	public String getCupSpl() {
		return this.cupSpl;
	}
	public void setIntSpl(String intSpl) {
		this.intSpl = intSpl;
	}
	
	public String getIntSpl() {
		return this.intSpl;
	}
	public void setDoorSpl(String doorSpl) {
		this.doorSpl = doorSpl;
	}
	
	public String getDoorSpl() {
		return this.doorSpl;
	}
	public void setCupAppDate(Date cupAppDate) {
		this.cupAppDate = cupAppDate;
	}
	
	public Date getCupAppDate() {
		return this.cupAppDate;
	}
	public void setIntAppDate(Date intAppDate) {
		this.intAppDate = intAppDate;
	}
	
	public Date getIntAppDate() {
		return this.intAppDate;
	}
	public void setDoorAppDate(Date doorAppDate) {
		this.doorAppDate = doorAppDate;
	}
	
	public Date getDoorAppDate() {
		return this.doorAppDate;
	}
	public void setTableSpl(String tableSpl) {
		this.tableSpl = tableSpl;
	}
	
	public String getTableSpl() {
		return this.tableSpl;
	}
	public void setTableAppDate(Date tableAppDate) {
		this.tableAppDate = tableAppDate;
	}
	
	public Date getTableAppDate() {
		return this.tableAppDate;
	}
	public void setEcoArea(Double ecoArea) {
		this.ecoArea = ecoArea;
	}
	
	public Double getEcoArea() {
		return this.ecoArea;
	}
	public void setMdfArea(Double mdfArea) {
		this.mdfArea = mdfArea;
	}
	
	public Double getMdfArea() {
		return this.mdfArea;
	}
	public void setOtherArea(Double otherArea) {
		this.otherArea = otherArea;
	}
	
	public Double getOtherArea() {
		return this.otherArea;
	}
	public void setCupMaterial(String cupMaterial) {
		this.cupMaterial = cupMaterial;
	}
	
	public String getCupMaterial() {
		return this.cupMaterial;
	}
	public void setIntMaterial(String intMaterial) {
		this.intMaterial = intMaterial;
	}
	
	public String getIntMaterial() {
		return this.intMaterial;
	}
	public void setCupSendDays(Integer cupSendDays) {
		this.cupSendDays = cupSendDays;
	}
	
	public Integer getCupSendDays() {
		return this.cupSendDays;
	}
	public void setIntSendDays(Integer intSendDays) {
		this.intSendDays = intSendDays;
	}
	
	public Integer getIntSendDays() {
		return this.intSendDays;
	}
	public void setCupSendDate(Date cupSendDate) {
		this.cupSendDate = cupSendDate;
	}
	
	public Date getCupSendDate() {
		return this.cupSendDate;
	}
	public void setIntSendDate(Date intSendDate) {
		this.intSendDate = intSendDate;
	}
	
	public Date getIntSendDate() {
		return this.intSendDate;
	}
	public void setCupInstallDate(Date cupInstallDate) {
		this.cupInstallDate = cupInstallDate;
	}
	
	public Date getCupInstallDate() {
		return this.cupInstallDate;
	}
	public void setIntInstallDate(Date intInstallDate) {
		this.intInstallDate = intInstallDate;
	}
	
	public Date getIntInstallDate() {
		return this.intInstallDate;
	}
	public void setCupDeliverDate(Date cupDeliverDate) {
		this.cupDeliverDate = cupDeliverDate;
	}
	
	public Date getCupDeliverDate() {
		return this.cupDeliverDate;
	}
	public void setIntDeliverDate(Date intDeliverDate) {
		this.intDeliverDate = intDeliverDate;
	}
	
	public Date getIntDeliverDate() {
		return this.intDeliverDate;
	}
	public void setCupCheckDate(Date cupCheckDate) {
		this.cupCheckDate = cupCheckDate;
	}
	
	public Date getCupCheckDate() {
		return this.cupCheckDate;
	}
	public void setIntCheckDate(Date intCheckDate) {
		this.intCheckDate = intCheckDate;
	}
	
	public Date getIntCheckDate() {
		return this.intCheckDate;
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
	public void setDelayRemarks(String delayRemarks) {
		this.delayRemarks = delayRemarks;
	}
	
	public String getDelayRemarks() {
		return this.delayRemarks;
	}
	public void setTableSendDate(Date tableSendDate) {
		this.tableSendDate = tableSendDate;
	}
	
	public Date getTableSendDate() {
		return this.tableSendDate;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getStatusDescr() {
		return StatusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}

	public String getLayout() {
		return Layout;
	}

	public void setLayout(String layout) {
		Layout = layout;
	}

	public String getLayoutdescr() {
		return Layoutdescr;
	}

	public void setLayoutdescr(String layoutdescr) {
		Layoutdescr = layoutdescr;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getCustType() {
		return CustType;
	}

	public void setCustType(String custType) {
		CustType = custType;
	}

	public String getCustTypeDescr() {
		return CustTypeDescr;
	}

	public void setCustTypeDescr(String custTypeDescr) {
		CustTypeDescr = custTypeDescr;
	}

	public String getDesignDept1() {
		return DesignDept1;
	}

	public void setDesignDept1(String designDept1) {
		DesignDept1 = designDept1;
	}

	public String getDesignDept1Descr() {
		return DesignDept1Descr;
	}

	public void setDesignDept1Descr(String designDept1Descr) {
		DesignDept1Descr = designDept1Descr;
	}

	public String getDesignMan() {
		return DesignMan;
	}

	public void setDesignMan(String designMan) {
		DesignMan = designMan;
	}

	public String getDesignManDescr() {
		return DesignManDescr;
	}

	public void setDesignManDescr(String designManDescr) {
		DesignManDescr = designManDescr;
	}

	public String getPrjDept1Descr() {
		return PrjDept1Descr;
	}

	public void setPrjDept1Descr(String prjDept1Descr) {
		PrjDept1Descr = prjDept1Descr;
	}

	public String getProjectMan() {
		return ProjectMan;
	}

	public void setProjectMan(String projectMan) {
		ProjectMan = projectMan;
	}

	public String getProjectManDescr() {
		return ProjectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		ProjectManDescr = projectManDescr;
	}

	public String getIntDept2Descr() {
		return IntDept2Descr;
	}

	public void setIntDept2Descr(String intDept2Descr) {
		IntDept2Descr = intDept2Descr;
	}

	public String getCupsplDescr() {
		return CupsplDescr;
	}

	public void setCupsplDescr(String cupsplDescr) {
		CupsplDescr = cupsplDescr;
	}

	public String getIntsplDescr() {
		return IntsplDescr;
	}

	public void setIntsplDescr(String intsplDescr) {
		IntsplDescr = intsplDescr;
	}

	public String getDoorsplDescr() {
		return DoorsplDescr;
	}

	public void setDoorsplDescr(String doorsplDescr) {
		DoorsplDescr = doorsplDescr;
	}

	public String getTablespldescr() {
		return tablespldescr;
	}

	public void setTablespldescr(String tablespldescr) {
		this.tablespldescr = tablespldescr;
	}

	public String getIntDesignDescr() {
		return IntDesignDescr;
	}

	public void setIntDesignDescr(String intDesignDescr) {
		IntDesignDescr = intDesignDescr;
	}

	public String getIntPlanManDescr() {
		return IntPlanManDescr;
	}

	public void setIntPlanManDescr(String intPlanManDescr) {
		IntPlanManDescr = intPlanManDescr;
	}

	public String getNowIntProgDescr() {
		return NowIntProgDescr;
	}

	public void setNowIntProgDescr(String nowIntProgDescr) {
		NowIntProgDescr = nowIntProgDescr;
	}

	public String getNowCupProgDescr() {
		return NowCupProgDescr;
	}

	public void setNowCupProgDescr(String nowCupProgDescr) {
		NowCupProgDescr = nowCupProgDescr;
	}
	public Date getConfirmBegin() {
		return ConfirmBegin;
	}

	public void setConfirmBegin(Date confirmBegin) {
		ConfirmBegin = confirmBegin;
	}

	public Date getMeasureAppDate() {
		return MeasureAppDate;
	}

	public void setMeasureAppDate(Date measureAppDate) {
		MeasureAppDate = measureAppDate;
	}

	public Date getMeasureCupAppDate() {
		return MeasureCupAppDate;
	}

	public void setMeasureCupAppDate(Date measureCupAppDate) {
		MeasureCupAppDate = measureCupAppDate;
	}

	public String getIntAppDays() {
		return IntAppDays;
	}

	public void setIntAppDays(String intAppDays) {
		IntAppDays = intAppDays;
	}

	public String getCupAppDays() {
		return CupAppDays;
	}

	public void setCupAppDays(String cupAppDays) {
		CupAppDays = cupAppDays;
	}

	public String getAppDelayDdays() {
		return AppDelayDdays;
	}

	public void setAppDelayDdays(String appDelayDdays) {
		AppDelayDdays = appDelayDdays;
	}

	public String getCupAppDelayDdays() {
		return CupAppDelayDdays;
	}

	public void setCupAppDelayDdays(String cupAppDelayDdays) {
		CupAppDelayDdays = cupAppDelayDdays;
	}

	public String getIntProduceDays() {
		return IntProduceDays;
	}

	public void setIntProduceDays(String intProduceDays) {
		IntProduceDays = intProduceDays;
	}

	public String getCupProduceDays() {
		return CupProduceDays;
	}

	public void setCupProduceDays(String cupProduceDays) {
		CupProduceDays = cupProduceDays;
	}

	public String getIntSendDelayDays() {
		return IntSendDelayDays;
	}

	public void setIntSendDelayDays(String intSendDelayDays) {
		IntSendDelayDays = intSendDelayDays;
	}

	public String getCupSendDelayDays() {
		return CupSendDelayDays;
	}

	public void setCupSendDelayDays(String cupSendDelayDays) {
		CupSendDelayDays = cupSendDelayDays;
	}

	public String getIntDeliverDays() {
		return IntDeliverDays;
	}

	public void setIntDeliverDays(String intDeliverDays) {
		IntDeliverDays = intDeliverDays;
	}

	public String getCupDeliverDays() {
		return CupDeliverDays;
	}

	public void setCupDeliverDays(String cupDeliverDays) {
		CupDeliverDays = cupDeliverDays;
	}

	public String getIntAllDays() {
		return IntAllDays;
	}

	public void setIntAllDays(String intAllDays) {
		IntAllDays = intAllDays;
	}

	public String getCupAllDays() {
		return CupAllDays;
	}

	public void setCupAllDays(String cupAllDays) {
		CupAllDays = cupAllDays;
	}

	public String getIntInstallDelayDays() {
		return IntInstallDelayDays;
	}

	public void setIntInstallDelayDays(String intInstallDelayDays) {
		IntInstallDelayDays = intInstallDelayDays;
	}

	public String getCupInstallDelayDays() {
		return CupInstallDelayDays;
	}

	public void setCupInstallDelayDays(String cupInstallDelayDays) {
		CupInstallDelayDays = cupInstallDelayDays;
	}

	public String getAllPlan() {
		return AllPlan;
	}

	public void setAllPlan(String allPlan) {
		AllPlan = allPlan;
	}

	public String getOrderAmount() {
		return OrderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}

	public String getAddreduceAll() {
		return addreduceAll;
	}

	public void setAddreduceAll(String addreduceAll) {
		this.addreduceAll = addreduceAll;
	}

	public String getIntYs() {
		return IntYs;
	}

	public void setIntYs(String intYs) {
		IntYs = intYs;
	}

	public String getCupYs() {
		return CupYs;
	}

	public void setCupYs(String cupYs) {
		CupYs = cupYs;
	}

	public String getCupXd() {
		return CupXd;
	}

	public void setCupXd(String cupXd) {
		CupXd = cupXd;
	}

	public String getCupZj() {
		return CupZj;
	}

	public void setCupZj(String cupZj) {
		CupZj = cupZj;
	}

	public String getIsErrorDescr() {
		return IsErrorDescr;
	}

	public void setIsErrorDescr(String isErrorDescr) {
		IsErrorDescr = isErrorDescr;
	}

	public String getNoInstallDescr() {
		return NoInstallDescr;
	}

	public void setNoInstallDescr(String noInstallDescr) {
		NoInstallDescr = noInstallDescr;
	}

	public String getIsPayDescr() {
		return IsPayDescr;
	}

	public void setIsPayDescr(String isPayDescr) {
		IsPayDescr = isPayDescr;
	}

	public String getIsBusiDescr() {
		return IsBusiDescr;
	}

	public void setIsBusiDescr(String isBusiDescr) {
		IsBusiDescr = isBusiDescr;
	}

	public String getIsReturnDescr() {
		return IsReturnDescr;
	}

	public void setIsReturnDescr(String isReturnDescr) {
		IsReturnDescr = isReturnDescr;
	}

	public String getIsCmplDescr() {
		return IsCmplDescr;
	}

	public void setIsCmplDescr(String isCmplDescr) {
		IsCmplDescr = isCmplDescr;
	}

	public String getIsSaleDescr() {
		return IsSaleDescr;
	}

	public void setIsSaleDescr(String isSaleDescr) {
		IsSaleDescr = isSaleDescr;
	}

	public Date getDealDate() {
		return DealDate;
	}

	public void setDealDate(Date dealDate) {
		DealDate = dealDate;
	}

	public Date getCupDealDate() {
		return CupDealDate;
	}

	public void setCupDealDate(Date cupDealDate) {
		CupDealDate = cupDealDate;
	}

	public String getCupDesignDescr() {
		return CupDesignDescr;
	}

	public void setCupDesignDescr(String cupDesignDescr) {
		CupDesignDescr = cupDesignDescr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSupplCode() {
		return SupplCode;
	}

	public void setSupplCode(String supplCode) {
		SupplCode = supplCode;
	}

	public Date getBuyDateFrom() {
		return buyDateFrom;
	}

	public void setBuyDateFrom(Date buyDateFrom) {
		this.buyDateFrom = buyDateFrom;
	}

	public Date getBuyDateTo() {
		return buyDateTo;
	}

	public void setBuyDateTo(Date buyDateTo) {
		this.buyDateTo = buyDateTo;
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

	public Date getInstallDateFrom() {
		return installDateFrom;
	}

	public void setInstallDateFrom(Date installDateFrom) {
		this.installDateFrom = installDateFrom;
	}

	public Date getInstallDateTo() {
		return installDateTo;
	}

	public void setInstallDateTo(Date installDateTo) {
		this.installDateTo = installDateTo;
	}

	public Date getDeliverDateFrom() {
		return deliverDateFrom;
	}

	public void setDeliverDateFrom(Date deliverDateFrom) {
		this.deliverDateFrom = deliverDateFrom;
	}

	public Date getDeliverDateTo() {
		return deliverDateTo;
	}

	public void setDeliverDateTo(Date deliverDateTo) {
		this.deliverDateTo = deliverDateTo;
	}

	public Date getCheckDateFrom() {
		return checkDateFrom;
	}

	public void setCheckDateFrom(Date checkDateFrom) {
		this.checkDateFrom = checkDateFrom;
	}

	public Date getCheckDateTo() {
		return checkDateTo;
	}

	public void setCheckDateTo(Date checkDateTo) {
		this.checkDateTo = checkDateTo;
	}

	public Date getMeasureAppDateFrom() {
		return measureAppDateFrom;
	}

	public void setMeasureAppDateFrom(Date measureAppDateFrom) {
		this.measureAppDateFrom = measureAppDateFrom;
	}

	public Date getMeasureAppDateTo() {
		return measureAppDateTo;
	}

	public void setMeasureAppDateTo(Date measureAppDateTo) {
		this.measureAppDateTo = measureAppDateTo;
	}

	public Date getConfirmBeginFrom() {
		return confirmBeginFrom;
	}

	public void setConfirmBeginFrom(Date confirmBeginFrom) {
		this.confirmBeginFrom = confirmBeginFrom;
	}

	public Date getConfirmBeginTo() {
		return confirmBeginTo;
	}

	public void setConfirmBeginTo(Date confirmBeginTo) {
		this.confirmBeginTo = confirmBeginTo;
	}

	public Date getDealDateFrom() {
		return dealDateFrom;
	}

	public void setDealDateFrom(Date dealDateFrom) {
		this.dealDateFrom = dealDateFrom;
	}

	public Date getDealDateTo() {
		return dealDateTo;
	}

	public void setDealDateTo(Date dealDateTo) {
		this.dealDateTo = dealDateTo;
	}

	public String getCheckDelayDays() {
		return checkDelayDays;
	}

	public void setCheckDelayDays(String checkDelayDays) {
		this.checkDelayDays = checkDelayDays;
	}

	public String getIntXd() {
		return IntXd;
	}

	public void setIntXd(String intXd) {
		IntXd = intXd;
	}

	public String getIntZj() {
		return IntZj;
	}

	public void setIntZj(String intZj) {
		IntZj = intZj;
	}

	public String getIntProgDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(intProgDetailJson);
    	return xml;
	}
	public void setIntProgDetailJson(String intProgDetailJson) {
		this.intProgDetailJson = intProgDetailJson;
	}

	public Date getTableInstallDate() {
		return tableInstallDate;
	}

	public void setTableInstallDate(Date tableInstallDate) {
		this.tableInstallDate = tableInstallDate;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}
	
}
