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

/**
 * 描 述：字典元素 - TSYS_DICT_ITEM : 
 */
@Entity
@Table(name = "TSYS_DICT_ITEM")
public class DictItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 元素ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID",nullable = false)
	private Long itemId;
	/** 所属字典 */
	@NotNull(message="所属字典不能为空")
	@Column(name = "DICT_ID",nullable = false)
	private Long dictId;
	/** 元素文本 */
	@NotNull(message="元素文本不能为空")
	@Column(name = "ITEM_LABEL",nullable = false)
	private String itemLabel;
	/** 元素编码 编码不可以改变，保存在数据库中为此值 */
	@NotNull(message="元素编码不能为空")
	@Column(name = "ITEM_CODE",nullable = false)
	private String itemCode;
	/** 元素值 值可以改变 */
	@Column(name = "ITEM_VALUE")
	private String itemValue;
	/** 状态 */
	@Column(name = "STATUS",nullable = false)
	private String status;
	/** 顺序 */
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
	/** 备注 */
	@Column(name = "REMARK")
	private String remark;
	
	@Transient
	private Dict dict;
	
	public DictItem() {
	}

	/**
	 * 元素ID length=19 null=false
	 */
	public Long getItemId() {
		return this.itemId;
	}

	/**
	 * 元素ID length=19 null=false
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * 所属字典 length=19 null=false
	 */
	public Long getDictId() {
		return this.dictId;
	}

	/**
	 * 所属字典 length=19 null=false
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 * 元素文本 length=100 null=false
	 */
	public String getItemLabel() {
		return this.itemLabel;
	}

	/**
	 * 元素文本 length=100 null=false
	 */
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	/**
	 * 元素值 length=49 null=false
	 */
	public String getItemValue() {
		return this.itemValue;
	}

	/**
	 * 元素值 length=49 null=false
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
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
	 * 顺序 length=3
	 */
	public Long getOrderNo() {
		return this.orderNo;
	}

	/**
	 * 顺序 length=3
	 */
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
	}

}
