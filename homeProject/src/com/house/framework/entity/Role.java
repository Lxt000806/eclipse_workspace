package com.house.framework.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.house.framework.commons.utils.XmlConverUtil;

/**
 * 描 述：角色表 - TSYS_ROLE : 城市管理员 角色分配了所有城市管理员共有的权限操作
 * 
 */
@Entity
@Table(name = "TSYS_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID",nullable = false)
	private Long roleId;
	/** 创建人ID */
	@Column(name = "CREATER_ID",nullable = false)
	private String createrId;
	/** 角色名称 */
	@NotNull(message="角色名称不能为空")
	@Column(name = "ROLE_NAME",nullable = false)
	private String roleName;
	/** 角色编码 */
	@NotNull(message="角色编码不能为空")
	@Column(name = "ROLE_CODE",nullable = false)
	private String roleCode;
	/** 排列顺序 */
	@Column(name = "ORDER_NO")
	private Long orderNo;
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(updatable=false,name = "GEN_TIME")
	private Date genTime;
	/** 修改时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	/** 备注提示 */
	@Column(name = "REMARK")
	private String remark;
	/** 所属地市 */
	@Column(name = "CITY_CODE")
	private String cityCode;
	
	@Column(name="SYS_CODE")
	private String sysCode;
	@Column(name="Priority")
	private Integer priority;
	
	
	@Transient 
	private String ptDescr;
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String operatorType; // 操作员类型
	
	
	public String getPtDescr() {
		return ptDescr;
	}

	public void setPtDescr(String ptDescr) {
		this.ptDescr = ptDescr;
	}

	public Role() {
	}

	/**
	 * 角色ID length=19 null=false
	 */
	public Long getRoleId() {
		return this.roleId;
	}

	/**
	 * 角色ID length=19 null=false
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 角色名称 length=100 null=false
	 */
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * 角色名称 length=100 null=false
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 角色编码 length=3 null=false
	 */
	public String getRoleCode() {
		return this.roleCode;
	}

	/**
	 * 角色编码 length=3 null=false
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * 排列顺序 length=4
	 */
	public Long getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 排列顺序 length=4
	 */
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 创建时间
	 */
	public Date getGenTime() {
		return this.genTime;
	}

	/**
	 * 创建时间
	 */
	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	/**
	 * 修改时间
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 备注提示 length=500
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 备注提示 length=500
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 所属地市 length=10
	 */
	public String getCityCode() {
		return this.cityCode;
	}

	/**
	 * 所属地市 length=10
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	
	

}
