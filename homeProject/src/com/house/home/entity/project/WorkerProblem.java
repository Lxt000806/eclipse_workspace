package com.house.home.entity.project;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tWorkerProblem")
public class WorkerProblem extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustWkPk")
	private Integer custWkPk;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Type")
	private String type;
	@Column(name = "StopDay")
	private Double stopDay;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Status")
	private String status;
	@Column(name = "ConfirmRemark")
	private String confirmRemark;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "DealRemark")
	private String dealRemark;
	@Column(name = "DealCZY")
	private String dealCzy;
	@Column(name = "DealDate")
	private Date dealDate;
	
	@Transient
	private String picNum;/*照片数量*/
	@Transient
	private String custCode;/*客户编号*/
	@Transient
	private String address;/*楼盘地址*/
	@Transient
	private Date dateFrom;/*反馈日期*/
	@Transient
	private Date dateTo;
	@Transient
	private String workType12;/*工种*/
	@Transient
	private String workType12Descr;/*工种描述*/
	@Transient
	private String workerCode;/*工人编码*/
	@Transient
	private String nameChi;/*工人名字*/
	@Transient
	private String prjRegionCode;/*工程大区编号*/
	@Transient
	private String prjRegionDescr;/*工程大区描述*/
	@Transient
	private String projectMan;   //新增项目经理查询字段 --update By @zb 2018-07-28
	@Transient
	private String cwStatus;
	@Transient
	private String isCupboard;
	@Transient
	private String department2;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getStopDay() {
		return stopDay;
	}
	public void setStopDay(Double stopDay) {
		this.stopDay = stopDay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}
	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getPrjRegionCode() {
		return prjRegionCode;
	}
	public void setPrjRegionCode(String prjRegionCode) {
		this.prjRegionCode = prjRegionCode;
	}
	public String getPrjRegionDescr() {
		return prjRegionDescr;
	}
	public void setPrjRegionDescr(String prjRegionDescr) {
		this.prjRegionDescr = prjRegionDescr;
	}
	public String getPicNum() {
		return picNum;
	}
	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
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
	public String getDealRemark() {
		return dealRemark;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
	public String getDealCzy() {
		return dealCzy;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getCwStatus() {
		return cwStatus;
	}
	public void setCwStatus(String cwStatus) {
		this.cwStatus = cwStatus;
	}
	public String getIsCupboard() {
		return isCupboard;
	}
	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
}
