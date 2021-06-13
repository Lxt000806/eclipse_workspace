package com.house.home.entity.basic;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * 二级部门
 */
@Entity
@Table(name = "tDepartment2")
public class Department2 extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "Desc2")
	private String desc2;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "DepType")
	private String depType;
	@Column(name = "PlanNum")
	private Integer planNum;
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name="BusiType")
	private String busiType;
	@Column(name="Department")
	private String department;
	@Column(name = "IsOutChannel")
    private String isOutChannel;
	// Constructors
	@Transient
	private Date checkDateFrom;
	@Transient
	private Date checkDateTo;
	@Transient
	private String custType;
	@Transient
	private String dept1Code;
	@Transient
	private String depLeader;
	
	/**
	 * 二级部门下的三级部门列表
	 */
	@Transient
	private List<Department3> children;
	/**
	 * 部门级别
	 */
	@Transient
	private String deptLevel;
	
	
	/** default constructor */
	public Department2() {
	}
	
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc1() {
		return this.desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return this.desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDepartment1() {
		return this.department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getActionLog() {
		return this.actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getDepType() {
		return depType;
	}
	public void setDepType(String depType) {
		this.depType = depType;
	}
	public Integer getPlanNum() {
		return planNum;
	}
	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}

	public Date getCheckDateFrom() {
		return checkDateFrom;
	}

	public void setCheckDateFrom(Date checkDateFrom) {
		this.checkDateFrom = checkDateFrom;
	}

	public Date getCheckDateTo() {
		return checkDateTo;
	}

	public void setCheckDateTo(Date checkDateTo) {
		this.checkDateTo = checkDateTo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDept1Code() {
		return dept1Code;
	}

	public void setDept1Code(String dept1Code) {
		this.dept1Code = dept1Code;
	}

	public String getDepLeader() {
		return depLeader;
	}

	public void setDepLeader(String depLeader) {
		this.depLeader = depLeader;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Department3> getChildren() {
        return children;
    }

    public void setChildren(List<Department3> children) {
        this.children = children;
    }

    public String getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(String deptLevel) {
        this.deptLevel = deptLevel;
    }

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

    public String getIsOutChannel() {
        return isOutChannel;
    }

    public void setIsOutChannel(String isOutChannel) {
        this.isOutChannel = isOutChannel;
    }
    
}
