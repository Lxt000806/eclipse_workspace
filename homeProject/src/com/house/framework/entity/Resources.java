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
 * 描 述：权限路径表 - TSYS_RESOURCES : 
 * 
 */
@Entity
@Table(name = "TSYS_RESOURCES")
public class Resources implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 资源ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESOURCES_ID",nullable = false)
	private Long resourcesId;
	/** 权限ID */
	@Column(name = "AUTHORITY_ID",nullable = false)
	private Long authorityId;
	/** 访问连接地址 */
	@Column(name = "URL_STR",nullable = false)
	private String urlStr;
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
	

	public Resources() {
	}

	
	/**
	 * 资源ID
	 * @return
	 */
	public Long getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
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

	
}
