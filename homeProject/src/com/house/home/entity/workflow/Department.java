package com.house.home.entity.workflow;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * Department信息
 */
@Entity
@Table(name = "tDepartment")
public class Department extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "CmpCode")
	private String cmpCode;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Desc2")
	private String desc2;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "PlanNum")
	private Integer planNum;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "BusiType")
	private String busiType;
	@Column(name = "DepType")
	private String depType;
	@Column(name = "HigherDep")
	private String higherDep;
	@Column(name = "Path")
	private String path;
	@Column(name = "LeaderCode")
	private String leaderCode;
	@Column(name = "IsActual")
	private String isActual;
	@Column(name = "IsProcDept")
	private String isProcDept;
	@Column(name = "IsOutChannel")
    private String isOutChannel;
	
	@Transient
	private String parentDep;
	@Transient
	private String level;
	@Transient
	private String empCode;
	@Transient
	private String empPk;
	@Transient
	private String isEmp;//是否员工信息调用
	@Transient
	private String higherLevel;
	@Transient
	private String salfDept;
	
	@Transient
	private String isParent;//公告消息管理部门人员树结构用  add by cjm 2019-06-22
	
	public Department() {
		super();
	}

	public Department(String code, String cmpCode, String desc1, String desc2,
			Date lastUpdate, String lastUpdatedBy, String expired,
			String actionLog, Integer planNum, Integer dispSeq,
			String busiType, String depType, String higherDep, String path,
			String leaderCode, String isActual, String isProcDept) {
		super();
		this.code = code;
		this.cmpCode = cmpCode;
		this.desc1 = desc1;
		this.desc2 = desc2;
		this.lastUpdate = lastUpdate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.expired = expired;
		this.actionLog = actionLog;
		this.planNum = planNum;
		this.dispSeq = dispSeq;
		this.busiType = busiType;
		this.depType = depType;
		this.higherDep = higherDep;
		this.path = path;
		this.leaderCode = leaderCode;
		this.isActual = isActual;
		this.isProcDept = isProcDept;
	}

	
	public String getSalfDept() {
		return salfDept;
	}

	public void setSalfDept(String salfDept) {
		this.salfDept = salfDept;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}
	
	public String getCmpCode() {
		return this.cmpCode;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	
	public String getDesc1() {
		return this.desc1;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	
	public String getDesc2() {
		return this.desc2;
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
	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	
	public Integer getPlanNum() {
		return this.planNum;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	public void setDepType(String depType) {
		this.depType = depType;
	}
	
	public String getDepType() {
		return this.depType;
	}
	public void setHigherDep(String higherDep) {
		this.higherDep = higherDep;
	}
	
	public String getHigherDep() {
		return this.higherDep;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	public void setLeaderCode(String leaderCode) {
		this.leaderCode = leaderCode;
	}
	
	public String getLeaderCode() {
		return this.leaderCode;
	}
	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}
	
	public String getIsActual() {
		return this.isActual;
	}

	public String getParentDep() {
		return parentDep;
	}

	public void setParentDep(String parentDep) {
		this.parentDep = parentDep;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpPk() {
		return empPk;
	}

	public void setEmpPk(String empPk) {
		this.empPk = empPk;
	}

	public String getIsEmp() {
		return isEmp;
	}

	public void setIsEmp(String isEmp) {
		this.isEmp = isEmp;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getHigherLevel() {
		return higherLevel;
	}

	public void setHigherLevel(String higherLevel) {
		this.higherLevel = higherLevel;
	}

	public String getIsProcDept() {
		return isProcDept;
	}

	public void setIsProcDept(String isProcDept) {
		this.isProcDept = isProcDept;
	}

    public String getIsOutChannel() {
        return isOutChannel;
    }

    public void setIsOutChannel(String isOutChannel) {
        this.isOutChannel = isOutChannel;
    }
	
}
