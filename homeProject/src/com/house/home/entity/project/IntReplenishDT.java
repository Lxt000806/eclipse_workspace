package com.house.home.entity.project;

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
 * IntReplenishDT信息
 */
@Entity
@Table(name = "tIntReplenishDT")
public class IntReplenishDT extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "No")
	private String no;
	@Column(name = "IntSpl")
	private String intSpl;
	@Column(name = "Type")
	private String type;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ArriveDate")
	private Date arriveDate;
	@Column(name = "DocDescr")
	private String docDescr;
	@Column(name = "DocName")
	private String docName;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "ArriveRemarks")
	private String arriveRemarks; //到货备注
	@Column(name = "ResPart")
	private String resPart; //责任人
	@Column(name = "Undertaker")
	private String undertaker; //责任人
	
	@Transient
	private String CustCode;
	@Transient
	private String intSplDescr; 
	@Transient
	private String address;
	@Transient
	private String isCupboard;
	@Transient
	private String source;
	@Transient
	private String status;
	@Transient
	private Date finishDateFrom;
	@Transient
	private Date finishDateTo;
	@Transient
	private Date arriveDateFrom;
	@Transient
	private Date arriveDateTo;
	@Transient
	private Date okDateFrom;
	@Transient
	private Date okDateTo;
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setIntSpl(String intSpl) {
		this.intSpl = intSpl;
	}
	
	public String getIntSpl() {
		return this.intSpl;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	public Date getArriveDate() {
		return this.arriveDate;
	}
	public void setDocDescr(String docDescr) {
		this.docDescr = docDescr;
	}
	
	public String getDocDescr() {
		return this.docDescr;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	public String getDocName() {
		return this.docName;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
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

	public String getCustCode() {
		return CustCode;
	}

	public void setCustCode(String custCode) {
		CustCode = custCode;
	}

	public String getIntSplDescr() {
		return intSplDescr;
	}

	public void setIntSplDescr(String intSplDescr) {
		this.intSplDescr = intSplDescr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsCupboard() {
		return isCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		this.isCupboard = isCupboard;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFinishDateFrom() {
		return finishDateFrom;
	}

	public void setFinishDateFrom(Date finishDateFrom) {
		this.finishDateFrom = finishDateFrom;
	}

	public Date getFinishDateTo() {
		return finishDateTo;
	}

	public void setFinishDateTo(Date finishDateTo) {
		this.finishDateTo = finishDateTo;
	}

	public Date getArriveDateFrom() {
		return arriveDateFrom;
	}

	public void setArriveDateFrom(Date arriveDateFrom) {
		this.arriveDateFrom = arriveDateFrom;
	}

	public Date getArriveDateTo() {
		return arriveDateTo;
	}

	public void setArriveDateTo(Date arriveDateTo) {
		this.arriveDateTo = arriveDateTo;
	}

	public String getArriveRemarks() {
		return arriveRemarks;
	}

	public void setArriveRemarks(String arriveRemarks) {
		this.arriveRemarks = arriveRemarks;
	}

	public String getResPart() {
		return resPart;
	}

	public void setResPart(String resPart) {
		this.resPart = resPart;
	}

	public Date getOkDateFrom() {
		return okDateFrom;
	}

	public void setOkDateFrom(Date okDateFrom) {
		this.okDateFrom = okDateFrom;
	}

	public Date getOkDateTo() {
		return okDateTo;
	}

	public void setOkDateTo(Date okDateTo) {
		this.okDateTo = okDateTo;
	}

	public String getUndertaker() {
		return undertaker;
	}

	public void setUndertaker(String undertaker) {
		this.undertaker = undertaker;
	}

}
