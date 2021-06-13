package com.house.home.entity.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import com.house.framework.commons.orm.BaseEntity;

/**
 * WorkSign信息
 */
@Entity
@Table(name = "tWorkSign")
public class WorkSign extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "CustWkPk")
	private Integer custWkPk;	
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "WorkerCode")
	private String workerCode;
	@Column(name = "PrjItem2")
	private String prjItem2;
	@Column(name = "Address")
	private String address;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "IsComplete")
	private String isComplete;
	@Column(name = "No")
	private String no;
	
	@Column(name = "CustScore")
	private Integer custScore;
	@Column(name = "CustRemarks" )
	private String custRemarks;
	@Column(name = "LeaveProblemRemark")
	private String leaveProblemRemark;
	@Column(name = "IsLeaveProblem")
	private String isLeaveProblem;
	@Transient
	private String lastUpdatedBy;
	
	
	public String getLeaveProblemRemark() {
		return leaveProblemRemark;
	}
	public void setLeaveProblemRemark(String leaveProblemRemark) {
		this.leaveProblemRemark = leaveProblemRemark;
	}
	public String getIsLeaveProblem() {
		return isLeaveProblem;
	}
	public void setIsLeaveProblem(String isLeaveProblem) {
		this.isLeaveProblem = isLeaveProblem;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getPrjItem2() {
		return prjItem2;
	}
	public void setPrjItem2(String prjItem2) {
		this.prjItem2 = prjItem2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public Integer getCustScore() {
		return custScore;
	}
	public void setCustScore(Integer custScore) {
		this.custScore = custScore;
	}
	public String getCustRemarks() {
		return custRemarks;
	}
	public void setCustRemarks(String custRemarks) {
		this.custRemarks = custRemarks;
	}
	
}
