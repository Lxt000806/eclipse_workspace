package com.house.home.entity.design;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tDesignDemo")
public class DesignDemo extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "BuilderCode")
	private String builderCode;
	@Column(name = "DesignMan")
	private String designMan;
	@Column(name = "Layout")
	private String layout;
	@Column(name = "DesignSty")
	private String designSty;
	@Column(name = "Area")
	private Integer area;
	@Column(name = "DesignRemark")
	private String designRemark;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "IsPushCust")
	private String isPushCust;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name ="CustName")
	private String custName;
	
	@Transient
	private String address;
	@Transient
	private String areaArrange;
	
	
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAreaArrange() {
		return areaArrange;
	}
	public void setAreaArrange(String areaArrange) {
		this.areaArrange = areaArrange;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getBuilderCode() {
		return builderCode;
	}
	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getDesignSty() {
		return designSty;
	}
	public void setDesignSty(String designSty) {
		this.designSty = designSty;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getDesignRemark() {
		return designRemark;
	}
	public void setDesignRemark(String designRemark) {
		this.designRemark = designRemark;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getIsPushCust() {
		return isPushCust;
	}
	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
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


}
