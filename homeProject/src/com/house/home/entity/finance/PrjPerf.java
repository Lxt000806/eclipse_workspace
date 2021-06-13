package com.house.home.entity.finance;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tPrjPerf")
public class PrjPerf extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Y")
	private Integer y;
	@Column(name = "M")
	private Integer m;
	@Column(name = "Season")
	private Integer season;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Remarks")
	private String remarks;
	@Transient
	private Integer pk;
	@Transient
	private String projectMan;
	@Transient
	private String projectManDept2;
	@Transient
	private String custType;
	@Transient
	private String address;
	@Transient
	private String company;
	@Transient
	private String freeBaseItem;
	@Transient
	private String calcType; //计算类型 1：业绩数据生成、  2：重算结算业绩
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getProjectManDept2() {
		return projectManDept2;
	}
	public void setProjectManDept2(String projectManDept2) {
		this.projectManDept2 = projectManDept2;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getM() {
		return m;
	}
	public void setM(Integer m) {
		this.m = m;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFreeBaseItem() {
		return freeBaseItem;
	}
	public void setFreeBaseItem(String freeBaseItem) {
		this.freeBaseItem = freeBaseItem;
	}
	public String getCalcType() {
		return calcType;
	}
	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}
    
	
}
