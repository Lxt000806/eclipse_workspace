package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.house.framework.commons.orm.BaseEntity;

/**
 * PrjItem1信息
 */
@Entity
@Table(name = "tPrjItem1")
public class PrjItem1 extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "IsConfirm")
	private String isConfirm;
	@Column(name = "Seq")
	private Integer seq;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "worktype12")
	private String worktype12;
	@Column(name = "prjphotoNum")
	private Integer prjphotoNum;
	@Column(name = "IsFirstPass")
	private String isFirstPass;
	@Column(name = "SignTimes")
	private Integer signTimes;
	@Column(name = "IsFirstComplete")
	private String isFirstComplete;
	@Column(name="DesignSignTimes")
	private Integer designSignTimes;
	
	@Column(name = "FirstAddConfirm")
	private String firstAddConfirm;
	
	public Integer getDesignSignTimes() {
		return designSignTimes;
	}

	public void setDesignSignTimes(Integer designSignTimes) {
		this.designSignTimes = designSignTimes;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	public String getIsConfirm() {
		return this.isConfirm;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getSeq() {
		return this.seq;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
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
	public void setWorktype12(String worktype12) {
		this.worktype12 = worktype12;
	}
	
	public String getWorktype12() {
		return this.worktype12;
	}
	public void setPrjphotoNum(Integer prjphotoNum) {
		this.prjphotoNum = prjphotoNum;
	}
	
	public Integer getPrjphotoNum() {
		return this.prjphotoNum;
	}
	public void setIsFirstPass(String isFirstPass) {
		this.isFirstPass = isFirstPass;
	}
	
	public String getIsFirstPass() {
		return this.isFirstPass;
	}
	public void setSignTimes(Integer signTimes) {
		this.signTimes = signTimes;
	}
	
	public Integer getSignTimes() {
		return this.signTimes;
	}

	public String getIsFirstComplete() {
		return isFirstComplete;
	}

	public void setIsFirstComplete(String isFirstComplete) {
		this.isFirstComplete = isFirstComplete;
	}

    public String getFirstAddConfirm() {
        return firstAddConfirm;
    }

    public void setFirstAddConfirm(String firstAddConfirm) {
        this.firstAddConfirm = firstAddConfirm;
    }

}
