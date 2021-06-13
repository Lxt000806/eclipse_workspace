package com.house.home.bean.query;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户档案信息bean
 */
public class CustomerDaBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="档案编号", order=1)
	private String documentNo;
	@ExcelAnnotation(exportName="客户编号", order=2)
	private String code;
	@ExcelAnnotation(exportName="客户姓名", order=3)
	private String descr;
	@ExcelAnnotation(exportName="性别", order=4)
	private String genderDescr;
	@ExcelAnnotation(exportName="楼盘", order=5)
	private String address;
	@ExcelAnnotation(exportName="下定时间", order=6)
	private Date setDate;
	@ExcelAnnotation(exportName="签订时间", order=7)
	private Date signDate;   
	@ExcelAnnotation(exportName="开工时间", order=8)
	private Date confirmBegin; 
	@ExcelAnnotation(exportName="业务员", order=9)
	private String businessManDescr; 
	@ExcelAnnotation(exportName="业务员一级部门", order=10)
	private String businessManDesc1; 
	@ExcelAnnotation(exportName="业务员二级部门", order=11)
	private String businessManDesc2; 
	@ExcelAnnotation(exportName="业务员三级部门", order=12)
	private String businessManDesc3; 
	@ExcelAnnotation(exportName="设计师", order=13)
	private String designManDescr; 
	@ExcelAnnotation(exportName="设计师一级部门", order=14)
	private String designManDesc1; 
	@ExcelAnnotation(exportName="设计师二级部门", order=15)
	private String designManDesc2; 
	@ExcelAnnotation(exportName="设计师三级部门", order=16)
	private String designManDesc3; 
	@ExcelAnnotation(exportName="项目经理", order=17)
	private String projectManDescr; 
	@ExcelAnnotation(exportName="项目经理一级部门", order=18)
	private String projectManDesc1; 
	@ExcelAnnotation(exportName="项目经理二级部门", order=19)
	private String projectManDesc2; 
	@ExcelAnnotation(exportName="项目经理三级部门", order=20)
	private String projectManDesc3; 
	@ExcelAnnotation(exportName="户型", order=21)
	private String layOutDescr;
	@ExcelAnnotation(exportName="面积", order=22)
	private Integer area;
	@ExcelAnnotation(exportName="客户状态", order=23)
	private String statusDescr;
	@ExcelAnnotation(exportName="结束代码", order=24)
	private String endCodeDescr;
	@ExcelAnnotation(exportName="设计风格", order=25)
	private String designStyleDescr;
    @ExcelAnnotation(exportName="施工方式", order=26)
	private String constructTypeDescr;
    @ExcelAnnotation(exportName="工程造价", order=27)
   	private Double contractFee;
    @ExcelAnnotation(exportName="设计费", order=28)
   	private Double designFee;
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
	public String getGenderDescr() {
		return genderDescr;
	}
	public void setGenderDescr(String genderDescr) {
		this.genderDescr = genderDescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getSetDate() {
		return setDate;
	}
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getConfirmBegin() {
		return confirmBegin;
	}
	public void setConfirmBegin(Date confirmBegin) {
		this.confirmBegin = confirmBegin;
	}
	public String getBusinessManDescr() {
		return businessManDescr;
	}
	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}
	public String getBusinessManDesc1() {
		return businessManDesc1;
	}
	public void setBusinessManDesc1(String businessManDesc1) {
		this.businessManDesc1 = businessManDesc1;
	}
	public String getBusinessManDesc2() {
		return businessManDesc2;
	}
	public void setBusinessManDesc2(String businessManDesc2) {
		this.businessManDesc2 = businessManDesc2;
	}
	public String getBusinessManDesc3() {
		return businessManDesc3;
	}
	public void setBusinessManDesc3(String businessManDesc3) {
		this.businessManDesc3 = businessManDesc3;
	}
	public String getDesignManDescr() {
		return designManDescr;
	}
	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}
	public String getDesignManDesc1() {
		return designManDesc1;
	}
	public void setDesignManDesc1(String designManDesc1) {
		this.designManDesc1 = designManDesc1;
	}
	public String getDesignManDesc2() {
		return designManDesc2;
	}
	public void setDesignManDesc2(String designManDesc2) {
		this.designManDesc2 = designManDesc2;
	}
	public String getDesignManDesc3() {
		return designManDesc3;
	}
	public void setDesignManDesc3(String designManDesc3) {
		this.designManDesc3 = designManDesc3;
	}
	public String getProjectManDescr() {
		return projectManDescr;
	}
	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}
	public String getProjectManDesc1() {
		return projectManDesc1;
	}
	public void setProjectManDesc1(String projectManDesc1) {
		this.projectManDesc1 = projectManDesc1;
	}
	public String getProjectManDesc2() {
		return projectManDesc2;
	}
	public void setProjectManDesc2(String projectManDesc2) {
		this.projectManDesc2 = projectManDesc2;
	}
	public String getProjectManDesc3() {
		return projectManDesc3;
	}
	public void setProjectManDesc3(String projectManDesc3) {
		this.projectManDesc3 = projectManDesc3;
	}
	public String getLayOutDescr() {
		return layOutDescr;
	}
	public void setLayOutDescr(String layOutDescr) {
		this.layOutDescr = layOutDescr;
	}
	
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getStatusDescr() {
		return statusDescr;
	}
	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}
	public String getEndCodeDescr() {
		return endCodeDescr;
	}
	public void setEndCodeDescr(String endCodeDescr) {
		this.endCodeDescr = endCodeDescr;
	}
	public String getDesignStyleDescr() {
		return designStyleDescr;
	}
	public void setDesignStyleDescr(String designStyleDescr) {
		this.designStyleDescr = designStyleDescr;
	}
	public String getConstructTypeDescr() {
		return constructTypeDescr;
	}
	public void setConstructTypeDescr(String constructTypeDescr) {
		this.constructTypeDescr = constructTypeDescr;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public Double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}
	
}
