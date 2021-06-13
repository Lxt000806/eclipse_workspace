package com.house.framework.web.form;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

public class AuthorityForm {
	
	@ExcelAnnotation(exportName="权限ID", order=1)
	private Long authorityId;
	
	@ExcelAnnotation(exportName="编号", order=0)
	private Long index;
	
	/** 所属菜单ID */
	@ExcelAnnotation(exportName="菜单ID", order=2)
	private Long menuId;
	
	/** 所属菜单名称 */
	@ExcelAnnotation(exportName="所属菜单", order=5)
	private String menuName;
	
	/** 权限名称 */
	@ExcelAnnotation(exportName="权限名称", order=4)
	private String authName;
	
	/** 权限编码 */
	@ExcelAnnotation(exportName="权限编码", order=3)
	private String authCode;
	
	/** 排列顺序 */
	private Long orderNo;
	
	/** 创建时间 */
	@ExcelAnnotation(exportName="创建时间", order=6, pattern="yyyy-MM-dd")
	private Date genTime;
	
	/** 修改时间 */
	@ExcelAnnotation(exportName="修改时间", order=7, pattern="yyyy-MM-dd")
	private Date updateTime;
	
	/** 提示备注 */
	@ExcelAnnotation(exportName="备注提示", order=8)
	private String remark;

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Long getOrderNo() {
		return orderNo;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}
}
