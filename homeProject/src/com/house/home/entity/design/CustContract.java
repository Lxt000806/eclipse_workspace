package com.house.home.entity.design;

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
 * CustContract信息
 */
@Entity
@Table(name = "tCustContract")
public class CustContract extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ConNo")
	private String conNo;
	@Column(name = "ConDescr")
	private String conDescr;
	@Column(name = "PartyAPhone")
	private String partyAphone;
	@Column(name = "PartyAID")
	private String partyAid;
	@Column(name = "PartyAName")
	private String partyAname;
	@Column(name = "SignDate")
	private Date signDate;
	@Column(name = "ConcreteAddress")
	private String concreteAddress;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "IsFutureCon")
	private String isFutureCon;
	@Column(name = "Status")
	private String status;
	@Column(name = "ConMode")
	private String conMode;
	@Column(name = "ConType")
	private String conType;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "ConfirmRemarks")
	private String confirmRemarks;
	@Column(name = "OriginalDoc")
	private String originalDoc;
	@Column(name = "EffectDoc")
	private String effectDoc;
	@Column(name = "PartBDescr")
	private String partBdescr;
	@Column(name = "PartBRepName")
	private String partBrepName;
	@Column(name = "FlowId")
	private String flowId;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "EndCZY")
	private String endCzy;
	@Column(name = "EndRemarks")
	private String endRemarks;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "PartASignDate")
	private Date partASignDate;
	@Column(name = "PlaneDrawDay")
	private Integer planeDrawDay;
	@Column(name = "FullDrawDay")
	private Integer fullDrawDay;
	
	@Transient 
	private String isPreView;
	@Transient 
	private Double designFee;
	@Transient 
	private Double contractFee;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
	}
	public String getConDescr() {
		return conDescr;
	}
	public void setConDescr(String conDescr) {
		this.conDescr = conDescr;
	}
	public String getPartyAphone() {
		return partyAphone;
	}
	public void setPartyAphone(String partyAphone) {
		this.partyAphone = partyAphone;
	}
	public String getPartyAid() {
		return partyAid;
	}
	public void setPartyAid(String partyAid) {
		this.partyAid = partyAid;
	}
	public String getPartyAname() {
		return partyAname;
	}
	public void setPartyAname(String partyAname) {
		this.partyAname = partyAname;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getConcreteAddress() {
		return concreteAddress;
	}
	public void setConcreteAddress(String concreteAddress) {
		this.concreteAddress = concreteAddress;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getIsFutureCon() {
		return isFutureCon;
	}
	public void setIsFutureCon(String isFutureCon) {
		this.isFutureCon = isFutureCon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConMode() {
		return conMode;
	}
	public void setConMode(String conMode) {
		this.conMode = conMode;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getAppCzy() {
		return appCzy;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
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
	public String getConfirmRemarks() {
		return confirmRemarks;
	}
	public void setConfirmRemarks(String confirmRemarks) {
		this.confirmRemarks = confirmRemarks;
	}
	public String getOriginalDoc() {
		return originalDoc;
	}
	public void setOriginalDoc(String originalDoc) {
		this.originalDoc = originalDoc;
	}
	public String getEffectDoc() {
		return effectDoc;
	}
	public void setEffectDoc(String effectDoc) {
		this.effectDoc = effectDoc;
	}
	public String getPartBdescr() {
		return partBdescr;
	}
	public void setPartBdescr(String partBdescr) {
		this.partBdescr = partBdescr;
	}
	public String getPartBrepName() {
		return partBrepName;
	}
	public void setPartBrepName(String partBrepName) {
		this.partBrepName = partBrepName;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
	public String getEndCzy() {
		return endCzy;
	}
	public void setEndCzy(String endCzy) {
		this.endCzy = endCzy;
	}
	public String getEndRemarks() {
		return endRemarks;
	}
	public void setEndRemarks(String endRemarks) {
		this.endRemarks = endRemarks;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIsPreView() {
		return isPreView;
	}
	public void setIsPreView(String isPreView) {
		this.isPreView = isPreView;
	}
	public Double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public Date getPartASignDate() {
		return partASignDate;
	}
	public void setPartASignDate(Date partASignDate) {
		this.partASignDate = partASignDate;
	}
	public Integer getPlaneDrawDay() {
		return planeDrawDay;
	}
	public void setPlaneDrawDay(Integer planeDrawDay) {
		this.planeDrawDay = planeDrawDay;
	}
	public Integer getFullDrawDay() {
		return fullDrawDay;
	}
	public void setFullDrawDay(Integer fullDrawDay) {
		this.fullDrawDay = fullDrawDay;
	}
	
}
