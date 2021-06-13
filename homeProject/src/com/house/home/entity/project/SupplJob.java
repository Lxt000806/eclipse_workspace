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
 * SupplJob信息
 */
@Entity
@Table(name = "tSupplJob")
public class SupplJob extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name = "PrjJobNo")
	private String prjJobNo;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "RecvDate")
	private Date recvDate;
	@Column(name = "PlanDate")
	private Date planDate;
	@Column(name = "CompleteDate")
	private Date completeDate;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "SupplRemarks")
	private String supplRemarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String no;
	@Transient
	private String Address;
	@Transient
	private String JobType;
	@Transient
	private String NOTE;
	@Transient
	private String rwRemarks;
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private Date recvDateFrom;
	@Transient
	private Date recvDateTo;
	@Transient
	private String JobTypeDescr;
	@Transient
	private Date comfirmDateFrom;
	@Transient
	private Date comfirmDateTo;
	@Transient
	private String itemType1;
	@Transient
	private String appDescr;
	@Transient
	private String supplDescr;
	@Transient
	private String custCode;
	@Transient
	private String buildPass;
	
	@Transient
	private String custName;
	
	@Transient
	private String custMobile1;
	
	@Transient
    private Date completeDateFrom;
	
    @Transient
    private Date completeDateTo;
	
	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getAppDescr() {
		return appDescr;
	}

	public void setAppDescr(String appDescr) {
		this.appDescr = appDescr;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public Date getComfirmDateFrom() {
		return comfirmDateFrom;
	}

	public void setComfirmDateFrom(Date comfirmDateFrom) {
		this.comfirmDateFrom = comfirmDateFrom;
	}

	public Date getComfirmDateTo() {
		return comfirmDateTo;
	}

	public void setComfirmDateTo(Date comfirmDateTo) {
		this.comfirmDateTo = comfirmDateTo;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPrjJobNo(String prjJobNo) {
		this.prjJobNo = prjJobNo;
	}
	
	public String getPrjJobNo() {
		return this.prjJobNo;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setRecvDate(Date recvDate) {
		this.recvDate = recvDate;
	}
	
	public Date getRecvDate() {
		return this.recvDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	public Date getPlanDate() {
		return this.planDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	public Date getCompleteDate() {
		return this.completeDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setSupplRemarks(String supplRemarks) {
		this.supplRemarks = supplRemarks;
	}
	
	public String getSupplRemarks() {
		return this.supplRemarks;
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getJobType() {
		return JobType;
	}

	public void setJobType(String jobType) {
		JobType = jobType;
	}

	public String getNOTE() {
		return NOTE;
	}

	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}

	public String getRwRemarks() {
		return rwRemarks;
	}

	public void setRwRemarks(String rwRemarks) {
		this.rwRemarks = rwRemarks;
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

	public Date getRecvDateFrom() {
		return recvDateFrom;
	}

	public void setRecvDateFrom(Date recvDateFrom) {
		this.recvDateFrom = recvDateFrom;
	}

	public Date getRecvDateTo() {
		return recvDateTo;
	}

	public void setRecvDateTo(Date recvDateTo) {
		this.recvDateTo = recvDateTo;
	}

	public String getJobTypeDescr() {
		return JobTypeDescr;
	}

	public void setJobTypeDescr(String jobTypeDescr) {
		JobTypeDescr = jobTypeDescr;
	}

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getBuildPass() {
        return buildPass;
    }

    public void setBuildPass(String buildPass) {
        this.buildPass = buildPass;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustMobile1() {
        return custMobile1;
    }

    public void setCustMobile1(String custMobile1) {
        this.custMobile1 = custMobile1;
    }

    public Date getCompleteDateFrom() {
        return completeDateFrom;
    }

    public void setCompleteDateFrom(Date completeDateFrom) {
        this.completeDateFrom = completeDateFrom;
    }

    public Date getCompleteDateTo() {
        return completeDateTo;
    }

    public void setCompleteDateTo(Date completeDateTo) {
        this.completeDateTo = completeDateTo;
    }
	

	
}
