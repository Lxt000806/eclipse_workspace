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
 * 描 述：字典 - TSYS_DICT : 
 * 
 */
@Entity
@Table(name = "TSYS_DICT")
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 字典ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DICT_ID",nullable = false)
	private Long dictId;
	
	/** 字典名称 */
	@NotNull(message="字典名称不能为空")
	@Column(name = "DICT_NAME",nullable = false)
	private String dictName;
	/** 字典编码 */
	@NotNull(message="字典编码不能为空")
	@Column(name = "DICT_CODE",nullable = false)
	private String dictCode;
	/** 状态 */
	@Column(name = "STATUS",nullable = false)
	private String status;
	/** 备注 */
	@Column(name = "REMARK")
	private String remark;
	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(updatable=false,name = "GEN_TIME")
	private Date genTime;
	/** 修改时间 */
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	/** 字典类型 */
	@Column(name = "DICT_TYPE")
	private String dictType;
	
	@Transient
	private List<DictItem> dictItems;

	public Dict() {
	}

	/**
	 * 字典ID length=19 null=false
	 */
	public Long getDictId() {
		return this.dictId;
	}

	/**
	 * 字典ID length=19 null=false
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}


	/**
	 * 字典名称 length=100 null=false
	 */
	public String getDictName() {
		return this.dictName;
	}

	/**
	 * 字典名称 length=100 null=false
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	/**
	 * 字典编码 length=30 null=false
	 */
	public String getDictCode() {
		return this.dictCode;
	}

	/**
	 * 字典编码 length=30 null=false
	 */
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	/**
	 * 状态 length=10 null=false
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 状态 length=10 null=false
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 备注 length=200
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 备注 length=200
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 字典类型
	 */
	public String getDictType() {
		return dictType;
	}

	/**
	 * 字典类型
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public List<DictItem> getDictItems() {
		return dictItems;
	}

	public void setDictItems(List<DictItem> dictItems) {
		this.dictItems = dictItems;
	}
}
