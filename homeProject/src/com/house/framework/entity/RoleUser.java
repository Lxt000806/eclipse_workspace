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

/**
 * 描 述：角色用户关联表 - TSYS_ROLE_USER : 
 */
@Entity
@Table(name = "TSYS_ROLE_USER")
public class RoleUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色用户ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	/** 角色ID */
	@Column(name = "ROLE_ID",nullable = false)
	private Long roleId;
	/** 操作员编号 */
	@Column(name = "CZYBH",nullable = false)
	private String czybh;
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(updatable=false,name = "GEN_TIME")
	private Date genTime;

	public RoleUser() {
	}

	/**
	 * 角色用户ID length=19 null=false
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 角色用户ID length=19 null=false
	 */
	public void setId(Long id) {
		this.id = id;
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

	
	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
}
