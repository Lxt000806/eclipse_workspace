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


@Entity
@Table(name = "tCustRecommend")
public class CustRecommend extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "Address")
	private String address;
	@Column(name = "CustName")
	private String custName;
	@Column(name = "CustPhone")
	private String custPhone;
	@Column(name = "Status")
	private String status;
	@Column(name = "Recommender")
	private String recommender;
	@Column(name="Manager")
	private String manager;
	@Column(name = "ConfirmDate")
	private Date ConfirmDate;
	@Column(name = "ConfirmRemarks")
	private String confirmRemarks;
	@Column(name = "CustCode")
	private String custCode;
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
	@Column(name = "RecommendDate")
	private Date recommendDate;
	@Column(name="RecommendSource")
	private String recommendSource;
	@Column(name="RecommenderType")
	private String recommenderType;
	@Column(name="ResrCustCode")
	private String resrCustCode;
	
	@Transient
	private String workType12;
	@Transient
	private String recommenderDescr;
	@Transient
	private Date dateForm;
	@Transient
	private Date dateTo;
	@Transient
	private boolean defalutAuth=false;
	@Transient
	private boolean workerAuth=false;
	@Transient
	private String appType;
	@Transient
	private String portalAccount;
	@Transient
	private String searchInfo;
	@Transient
	private String isSelf;
	@Transient
	private String relationAddress;
	@Transient
	private String recommenderPhone;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Date getConfirmDate() {
		return ConfirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		ConfirmDate = confirmDate;
	}
	public String getConfirmRemarks() {
		return confirmRemarks;
	}
	public void setConfirmRemarks(String confirmRemarks) {
		this.confirmRemarks = confirmRemarks;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getRecommenderDescr() {
		return recommenderDescr;
	}
	public void setRecommenderDescr(String recommenderDescr) {
		this.recommenderDescr = recommenderDescr;
	}
	public Date getDateForm() {
		return dateForm;
	}
	public void setDateForm(Date dateForm) {
		this.dateForm = dateForm;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Date getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}
	public String getRecommendSource() {
		return recommendSource;
	}
	public void setRecommendSource(String recommendSource) {
		this.recommendSource = recommendSource;
	}
	public String getRecommenderType() {
		return recommenderType;
	}
	public void setRecommenderType(String recommenderType) {
		this.recommenderType = recommenderType;
	}
	public boolean isDefalutAuth() {
		return defalutAuth;
	}
	public void setDefalutAuth(boolean defalutAuth) {
		this.defalutAuth = defalutAuth;
	}
	public boolean isWorkerAuth() {
		return workerAuth;
	}
	public void setWorkerAuth(boolean workerAuth) {
		this.workerAuth = workerAuth;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getPortalAccount() {
		return portalAccount;
	}
	public void setPortalAccount(String portalAccount) {
		this.portalAccount = portalAccount;
	}
	public String getSearchInfo() {
		return searchInfo;
	}
	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}
	public String getResrCustCode() {
		return resrCustCode;
	}
	public void setResrCustCode(String resrCustCode) {
		this.resrCustCode = resrCustCode;
	}
	public String getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	public String getRelationAddress() {
		return relationAddress;
	}
	public void setRelationAddress(String relationAddress) {
		this.relationAddress = relationAddress;
	}
	public String getRecommenderPhone() {
		return recommenderPhone;
	}
	public void setRecommenderPhone(String recommenderPhone) {
		this.recommenderPhone = recommenderPhone;
	}
	
	
}
