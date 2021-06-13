package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 项目信息bean
 */
public class BuilderBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ExcelAnnotation(exportName="项目名称编号", order=1)
	private String code;
	@ExcelAnnotation(exportName="项目名称", order=2)
	private String descr;
	@ExcelAnnotation(exportName="项目大类", order=3)
	private String groupCodeDescr;
	@ExcelAnnotation(exportName="地址类型", order=4)
	private String addressTypeDescr;
	@ExcelAnnotation(exportName="备注", order=5)
	private String remarks;
	@ExcelAnnotation(exportName = "最后修改时间", pattern="yyyy-MM-dd HH:mm:ss", order=6)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="修改人", order=7)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="过期标志", order=8)
	private String expired;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getGroupCodeDescr() {
		return groupCodeDescr;
	}

	public void setGroupCodeDescr(String groupCodeDescr) {
		this.groupCodeDescr = groupCodeDescr;
	}

	public String getAddressTypeDescr() {
		return addressTypeDescr;
	}

	public void setAddressTypeDescr(String addressTypeDescr) {
		this.addressTypeDescr = addressTypeDescr;
	}

}
