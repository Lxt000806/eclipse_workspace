package com.house.home.entity.design;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
@Entity
@Table(name = "tResrCustPool")
public class ResrCustPool extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Type")
	private String type;
	@Column(name = "IsVirtualPhone")
	private String isVirtualPhone;
	@Column(name = "IsHideChannel")
	private String isHideChannel;
	@Column(name = "ReceiveRule")
	private String receiveRule;
	@Column(name = "DispatchRule")
	private String dispatchRule;
	@Column(name = "RecoverRule")
	private String recoverRule;
	@Column(name = "MaxValidResrCustNumber")
	private Integer maxValidResrCustNumber;
	@Column(name = "MaxNoDispatchAlarmNumber")
	private Integer maxNoDispatchAlarmNumber;
	@Column(name = "Priority")
	private Integer priority;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	/**
	 * 超级管理员（CZYBM_ADMIN）可以管理所有线索池，包括系统预设的线索池；
	 * 非超级管理员，判断是否有线索池模块高级管理员(RESR_CUST_POOL_MODULE_ADMIN)权限，有此权限可以管理非系统预设的线索池；
	 * 否则只显示管理员是当前操作员的线索池；
	 */
	@Transient
	private String adminType;
	
	@Transient
	private String excludedCzys;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsVirtualPhone() {
		return isVirtualPhone;
	}
	public void setIsVirtualPhone(String isVirtualPhone) {
		this.isVirtualPhone = isVirtualPhone;
	}
	public String getIsHideChannel() {
		return isHideChannel;
	}
	public void setIsHideChannel(String isHideChannel) {
		this.isHideChannel = isHideChannel;
	}
	public String getReceiveRule() {
		return receiveRule;
	}
	public void setReceiveRule(String receiveRule) {
		this.receiveRule = receiveRule;
	}
	public String getDispatchRule() {
		return dispatchRule;
	}
	public void setDispatchRule(String dispatchRule) {
		this.dispatchRule = dispatchRule;
	}
	public String getRecoverRule() {
		return recoverRule;
	}
	public void setRecoverRule(String recoverRule) {
		this.recoverRule = recoverRule;
	}
	public Integer getMaxValidResrCustNumber() {
		return maxValidResrCustNumber;
	}
	public void setMaxValidResrCustNumber(Integer maxValidResrCustNumber) {
		this.maxValidResrCustNumber = maxValidResrCustNumber;
	}
	public Integer getMaxNoDispatchAlarmNumber() {
		return maxNoDispatchAlarmNumber;
	}
	public void setMaxNoDispatchAlarmNumber(Integer maxNoDispatchAlarmNumber) {
		this.maxNoDispatchAlarmNumber = maxNoDispatchAlarmNumber;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
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
    public String getAdminType() {
        return adminType;
    }
    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }
    public String getExcludedCzys() {
        return excludedCzys;
    }
    public void setExcludedCzys(String excludedCzys) {
        this.excludedCzys = excludedCzys;
    }

    @Override
    public String toString() {
        return "ResrCustPool{no: " + no + ", descr: " + descr + "}";
    }
}
