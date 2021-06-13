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
 * 描 述：角色权限关联表 - TSYS_ROLE_AUTHORITY : 
 */
@Entity
@Table(name = "TSYS_ROLE_AUTHORITY")
public class RoleAuthority implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色权限ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID",nullable = false)
	private Long id;
	/** 角色ID */
	@Column(name = "ROLE_ID",nullable = false)
	private Long roleId;
	/** 权限ID */
	@Column(name = "AUTHORITY_ID",nullable = false)
	private Long authorityId;
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(updatable=false,name = "GEN_TIME")
	private Date genTime;

	public RoleAuthority() {
	}

	/**
	 * 角色权限ID length=19 null=false
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 角色权限ID length=19 null=false
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


}
