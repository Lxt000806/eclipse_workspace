package com.house.home.bean.design;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户信息bean
 */
public class CustomerBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="档案号", order=1)
	private String documentNo;
	@ExcelAnnotation(exportName="客户编号", order=2)
	private String code;
	@ExcelAnnotation(exportName="客户名称", order=3)
	private String descr;
	@ExcelAnnotation(exportName="楼盘", order=4)
	private String address;
	@ExcelAnnotation(exportName="性别", order=5)
	private String genderDescr;
	@ExcelAnnotation(exportName="客户类型", order=6)
	private String custTypeDescr;
	@ExcelAnnotation(exportName="客户来源", order=7)
	private String sourceDescr;
	@ExcelAnnotation(exportName="户型", order=8)
	private String layOutDescr;
	@ExcelAnnotation(exportName="面积", order=9)
	private Integer area;
	@ExcelAnnotation(exportName="设计风格", order=10)
	private String designStyleDescr;
	@ExcelAnnotation(exportName="客户状态", order=11)
	private String statusDescr;
	@ExcelAnnotation(exportName="QQ", order=12)
	private String qq;
	@ExcelAnnotation(exportName="Email2", order=14)
	private String email2;
	@ExcelAnnotation(exportName="创建时间", pattern="yyyy-MM-dd HH:mm:ss", order=15)
	private Date crtDate;
	@ExcelAnnotation(exportName="设计师", order=16)
	private String designManDescr;
    @ExcelAnnotation(exportName="业务员", order=17)
	private String businessManDescr;
    @ExcelAnnotation(exportName="开工时间", pattern="yyyy-MM-dd HH:mm:ss", order=18)
	private Date beginDate;
    @ExcelAnnotation(exportName="施工方式", order=19)
	private String constructTypeDescr;
    @ExcelAnnotation(exportName="意向金额", order=20)
	private Double planAmount;
    @ExcelAnnotation(exportName="备注", order=21)
	private String remarks;
    @ExcelAnnotation(exportName="最后修改时间", pattern="yyyy-MM-dd HH:mm:ss", order=22)
	private Date lastUpdate;
    @ExcelAnnotation(exportName="修改人", order=23)
	private String lastUpdatedBy;
    @ExcelAnnotation(exportName="是否过期", order=24)
	private String expired;
    @ExcelAnnotation(exportName="操作", order=25)
	private String actionLog;
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGenderDescr() {
		return genderDescr;
	}
	public void setGenderDescr(String genderDescr) {
		this.genderDescr = genderDescr;
	}
	public String getCustTypeDescr() {
		return custTypeDescr;
	}
	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}
	public String getSourceDescr() {
		return sourceDescr;
	}
	public void setSourceDescr(String sourceDescr) {
		this.sourceDescr = sourceDescr;
	}
	public String getLayOutDescr() {
		return layOutDescr;
	}
	public void setLayOutDescr(String layOutDescr) {
		this.layOutDescr = layOutDescr;
	}
	public String getDesignStyleDescr() {
		return designStyleDescr;
	}
	public void setDesignStyleDescr(String designStyleDescr) {
		this.designStyleDescr = designStyleDescr;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getConstructTypeDescr() {
		return constructTypeDescr;
	}
	public void setConstructTypeDescr(String constructTypeDescr) {
		this.constructTypeDescr = constructTypeDescr;
	}
	public Double getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}

}
