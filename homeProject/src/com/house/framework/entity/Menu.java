package com.house.framework.entity;

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

import com.house.framework.commons.orm.BaseEntity;

/**
 * 描 述：菜单表 - TSYS_MENU : 菜单层次不复杂，所以不用层级编码只有叶子菜单才有URL  也才会被分配
 */
@Entity
@Table(name = "TSYS_MENU")
public class Menu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 菜单ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MENU_ID",nullable = false)
	private Long menuId;
	/** 父菜单ID */
	@Column(name = "PARENT_ID")
	private Long parentId;
	/** 菜单名 */
	@NotNull(message="菜单名称不能为空")
	@Column(name = "MENU_NAME",nullable = false)
	private String menuName;
	/** 操作链接 */
	@Column(name = "MENU_URL")
	private String menuUrl;
	/** 菜单类型 */
	@NotNull(message="菜单类型不能为空")
	@Column(name = "MENU_TYPE")
	private String menuType;
	/** 打开图标 */
	@Column(name = "OPEN_ICON")
	private String openIcon;
	/** 关闭图标 */
	@Column(name = "CLOSE_ICON")
	private String closeIcon;
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
	/** 备注 */
	@Column(name = "REMARK")
	private String remark;
	
	/** 标签菜单备注 */
	@Column(name = "TAB_MENU_ID")
	private Long tabMenuId;
	/** 页面打开方式 0,、iframe内打开，1、弹出窗口打开 */
	@Column(name="OPEN_TYPE")
	private String openType;
	
	@Column(name="SYS_CODE")
	private String sysCode;
	/**父菜单id字符类型 */
	
	@Transient
	private String parentIdStr;
	/**菜单id字符类型 */
	@Transient
	private String menuIdStr;
	@Transient
	private String czybh;
	
	public Menu() {
	}

	/**
	 * 菜单ID length=19 null=false
	 */
	public Long getMenuId() {
		return this.menuId;
	}

	/**
	 * 菜单ID length=19 null=false
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * 父菜单ID length=19
	 */
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * 父菜单ID length=19
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 菜单名 length=100 null=false
	 */
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * 菜单名 length=100 null=false
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 操作链接 length=200
	 */
	public String getMenuUrl() {
		return this.menuUrl;
	}

	/**
	 * 操作链接 length=200
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 菜单类型 length=2
	 */
	public String getMenuType() {
		return this.menuType;
	}

	/**
	 * 菜单类型 length=2
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * 打开图标 length=30
	 */
	public String getOpenIcon() {
		return this.openIcon;
	}

	/**
	 * 打开图标 length=30
	 */
	public void setOpenIcon(String openIcon) {
		this.openIcon = openIcon;
	}

	/**
	 * 关闭图标 length=30
	 */
	public String getCloseIcon() {
		return this.closeIcon;
	}

	/**
	 * 关闭图标 length=30
	 */
	public void setCloseIcon(String closeIcon) {
		this.closeIcon = closeIcon;
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
	 * 备注 length=500
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 备注 length=500
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTabMenuId() {
		return tabMenuId;
	}

	public void setTabMenuId(Long tabMenuId) {
		this.tabMenuId = tabMenuId;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	
	public boolean equals(Object object){
		if(object instanceof Menu){
			if(menuName.equals(((Menu)object).getMenuName().trim())){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}
	
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result + ((menuUrl == null) ? 0 : menuUrl.hashCode());
		result = prime * result + ((menuType == null) ? 0 : menuType.hashCode());
		result = prime * result + ((closeIcon == null) ? 0 : closeIcon.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((genTime == null) ? 0 : genTime.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		return result;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getParentIdStr() {
		return parentIdStr;
	}

	public void setParentIdStr(String parentIdStr) {
		this.parentIdStr = parentIdStr;
	}

	public String getMenuIdStr() {
		return menuIdStr;
	}

	public void setMenuIdStr(String menuIdStr) {
		this.menuIdStr = menuIdStr;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	
}
