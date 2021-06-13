package com.house.framework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

/**
 * 描 述：权限表 - TSYS_AUTHORITY : 
 */
@Entity
@Table(name = "TSYS_AUTHORITY")
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 权限ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUTHORITY_ID",nullable = false)
	private Long authorityId;
	/** 所属菜单ID */
	@NotNull
	@Column(name = "MENU_ID",nullable = false)
	private Long menuId;
	/** 权限名称 */
	@NotNull(message="权限名称不能为空")
	@Column(name = "AUTH_NAME",nullable = false)
	private String authName;
	/** 权限编码 */
	@NotNull(message="权限编码不能为空")
	@Column(name = "AUTH_CODE",nullable = false)
	private String authCode;
	/** 访问方法 */
	@Column(name = "JAVA_FUN")
	private String javaFun;
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
	/** 提示备注 */
	@Column(name = "REMARK")
	private String remark;
	/** 访问连接地址 */
	@Column(name = "URL_STR")
	private String urlStr;
	@Column(name = "ISAdminAssign")
	private String ISAdminAssign;
	
	@Transient
	private List<Resources> resourcesList;

	public List<Resources> getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(List<Resources> resourcesList) {
		this.resourcesList = resourcesList;
	}

	public Authority() {
	}

	/**
	 * 权限ID length=19 null=false
	 */
	public Long getAuthorityId() {
		return this.authorityId;
	}

	/**
	 * 权限ID length=19 null=false
	 */
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	/**
	 * 所属菜单ID length=19 null=false
	 */
	public Long getMenuId() {
		return this.menuId;
	}

	/**
	 * 所属菜单ID length=19 null=false
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * 权限名称 length=100 null=false
	 */
	public String getAuthName() {
		return this.authName;
	}

	/**
	 * 权限名称 length=100 null=false
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}

	/**
	 * 权限编码 length=3 null=false
	 */
	public String getAuthCode() {
		return this.authCode;
	}

	/**
	 * 权限编码 length=3 null=false
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * 访问方法 length=150
	 */
	public String getJavaFun() {
		return this.javaFun;
	}

	/**
	 * 访问方法 length=150
	 */
	public void setJavaFun(String javaFun) {
		this.javaFun = javaFun;
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
	 * 提示备注 length=500
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 提示备注 length=500
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrlStr() {
		return urlStr;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public String getISAdminAssign() {
		return ISAdminAssign;
	}

	public void setISAdminAssign(String iSAdminAssign) {
		ISAdminAssign = iSAdminAssign;
	}
    
	
}
