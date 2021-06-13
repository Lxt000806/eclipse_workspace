package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * CustContractFile信息
 */
@Entity
@Table(name = "tCustContractFile")
public class CustContractFile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "ConPK")
	private Integer conPk;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "BeginPageNum")
	private Integer beginPageNum;
	@Column(name = "EndPageNum")
	private Integer endPageNum;
	@Column(name = "TotalPageNum")
	private Integer totalPageNum;
	@Column(name = "IsCrossPageSeal")
	private String isCrossPageSeal;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	public Integer getPk() {
		return pk;
	}
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getConPk() {
		return conPk;
	}
	
	public void setConPk(Integer conPk) {
		this.conPk = conPk;
	}
	
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public Integer getBeginPageNum() {
		return beginPageNum;
	}
	
	public void setBeginPageNum(Integer beginPageNum) {
		this.beginPageNum = beginPageNum;
	}
	
	public Integer getEndPageNum() {
		return endPageNum;
	}
	
	public void setEndPageNum(Integer endPageNum) {
		this.endPageNum = endPageNum;
	}
	
	public Integer getTotalPageNum() {
		return totalPageNum;
	}
	
	public void setTotalPageNum(Integer totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	
	public String getIsCrossPageSeal() {
		return isCrossPageSeal;
	}
	
	public void setIsCrossPageSeal(String isCrossPageSeal) {
		this.isCrossPageSeal = isCrossPageSeal;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Integer getDispSeq() {
		return dispSeq;
	}
	
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
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
