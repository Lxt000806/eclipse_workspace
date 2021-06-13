package com.house.home.bean.query;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户信息查询bean
 */
public class CustomerXxcxBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="档案号", order=1)
	private String documentno;
	@ExcelAnnotation(exportName="客户编号", order=2)
	private String code;
	@ExcelAnnotation(exportName="客户名称", order=3)
	private String descr;
	@ExcelAnnotation(exportName="性别", order=4)
	private String genderdescr;
	@ExcelAnnotation(exportName="客户来源", order=5)
	private String sourcedescr;
	@ExcelAnnotation(exportName="楼盘", order=6)
	private String address;
	@ExcelAnnotation(exportName="户型", order=7)
	private String layoutdescr;
	@ExcelAnnotation(exportName="面积", order=8)
	private Integer area;
	@ExcelAnnotation(exportName="设计风格", order=9)
	private String designstyledescr;
	@ExcelAnnotation(exportName="客户状态", order=10)
	private String statusdescr;
	@ExcelAnnotation(exportName="结束代码", order=11)
	private String endCodedescr;
	@ExcelAnnotation(exportName="结束时间", pattern="yyyy-MM-dd HH:mm:ss", order=12)
	private Date endDate;
	@ExcelAnnotation(exportName="下定时间", pattern="yyyy-MM-dd HH:mm:ss", order=13)
	private Date setDate;
	@ExcelAnnotation(exportName="签订时间", pattern="yyyy-MM-dd HH:mm:ss", order=14)
	private Date signDate;
	@ExcelAnnotation(exportName="结束原因", order=15)
	private String endRemark;
	@ExcelAnnotation(exportName="工程造价", order=16)
	private Double contractFee;
	@ExcelAnnotation(exportName="设计费", order=17)
	private Double designFee;
	@ExcelAnnotation(exportName="量房费", order=18)
	private Double measureFee;
	@ExcelAnnotation(exportName="制图费", order=19)
	private Double drawFee;
	@ExcelAnnotation(exportName="效果图费", order=20)
	private Double colorDrawFee;
	@ExcelAnnotation(exportName="管理费", order=21)
	private Double manageFee;
	@ExcelAnnotation(exportName="基础费", order=22)
	private Double baseFee;
	@ExcelAnnotation(exportName="基础优惠", order=23)
	private Double baseDisc;
	@ExcelAnnotation(exportName="基础直接费", order=24)
	private Double baseFee_dirct;
	@ExcelAnnotation(exportName="基础综合费", order=25)
	private Double baseFee_comp;
	@ExcelAnnotation(exportName="主材费", order=26)
	private Double mainFee;
	@ExcelAnnotation(exportName="主材优惠", order=27)
	private Double mainDisc;
	@ExcelAnnotation(exportName="服务性产品费", order=28)
	private Double mainServFee;
	@ExcelAnnotation(exportName="软装费", order=29)
	private Double softFee;
	@ExcelAnnotation(exportName="软装优惠", order=30)
	private Double softDisc;
	@ExcelAnnotation(exportName="软装其它费用", order=31)
	private Double softOther;
	@ExcelAnnotation(exportName="集成费", order=32)
	private Double integrateFee;
	@ExcelAnnotation(exportName="集成优惠", order=33)
	private Double integrateDisc;
	@ExcelAnnotation(exportName="橱柜费", order=34)
	private Double cupboardFee;
	@ExcelAnnotation(exportName="橱柜优惠", order=35)
	private Double cupboardDisc;
	@ExcelAnnotation(exportName="创建时间", pattern="yyyy-MM-dd HH:mm:ss", order=36)
	private Date crtDate;
	@ExcelAnnotation(exportName="设计师部门一", order=37)
	private String desiDpt1;
	@ExcelAnnotation(exportName="设计师部门二", order=38)
	private String desiDpt2;
	@ExcelAnnotation(exportName="设计师部门三", order=39)
	private String desiDpt3;
	@ExcelAnnotation(exportName="设计师", order=40)
	private String designManDescr;
	@ExcelAnnotation(exportName="业务员", order=41)
	private String businessManDescr;
	@ExcelAnnotation(exportName="翻单员", order=42)
	private String againManDescr;
	@ExcelAnnotation(exportName="软装设计师", order=43)
	private String softDesignDescr;
	@ExcelAnnotation(exportName="备注", order=44)
	private String remarks;
	@ExcelAnnotation(exportName="最后修改时间", pattern="yyyy-MM-dd HH:mm:ss", order=45)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="修改人", order=46)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="是否过期", order=47)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=48)
	private String actionLog;
	
	public String getDocumentno() {
		return documentno;
	}
	public void setDocumentno(String documentno) {
		this.documentno = documentno;
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
	public String getGenderdescr() {
		return genderdescr;
	}
	public void setGenderdescr(String genderdescr) {
		this.genderdescr = genderdescr;
	}
	public String getSourcedescr() {
		return sourcedescr;
	}
	public void setSourcedescr(String sourcedescr) {
		this.sourcedescr = sourcedescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLayoutdescr() {
		return layoutdescr;
	}
	public void setLayoutdescr(String layoutdescr) {
		this.layoutdescr = layoutdescr;
	}
	public String getDesignstyledescr() {
		return designstyledescr;
	}
	public void setDesignstyledescr(String designstyledescr) {
		this.designstyledescr = designstyledescr;
	}
	public String getStatusdescr() {
		return statusdescr;
	}
	public void setStatusdescr(String statusdescr) {
		this.statusdescr = statusdescr;
	}
	public String getEndCodedescr() {
		return endCodedescr;
	}
	public void setEndCodedescr(String endCodedescr) {
		this.endCodedescr = endCodedescr;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public String getEndRemark() {
		return endRemark;
	}
	public void setEndRemark(String endRemark) {
		this.endRemark = endRemark;
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
	public Double getMeasureFee() {
		return measureFee;
	}
	public void setMeasureFee(Double measureFee) {
		this.measureFee = measureFee;
	}
	public Double getDrawFee() {
		return drawFee;
	}
	public void setDrawFee(Double drawFee) {
		this.drawFee = drawFee;
	}
	public Double getColorDrawFee() {
		return colorDrawFee;
	}
	public void setColorDrawFee(Double colorDrawFee) {
		this.colorDrawFee = colorDrawFee;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	public Double getBaseDisc() {
		return baseDisc;
	}
	public void setBaseDisc(Double baseDisc) {
		this.baseDisc = baseDisc;
	}
	public Double getBaseFee_dirct() {
		return baseFee_dirct;
	}
	public void setBaseFee_dirct(Double baseFee_dirct) {
		this.baseFee_dirct = baseFee_dirct;
	}
	public Double getBaseFee_comp() {
		return baseFee_comp;
	}
	public void setBaseFee_comp(Double baseFee_comp) {
		this.baseFee_comp = baseFee_comp;
	}
	public Double getMainFee() {
		return mainFee;
	}
	public void setMainFee(Double mainFee) {
		this.mainFee = mainFee;
	}
	public Double getMainDisc() {
		return mainDisc;
	}
	public void setMainDisc(Double mainDisc) {
		this.mainDisc = mainDisc;
	}
	public Double getMainServFee() {
		return mainServFee;
	}
	public void setMainServFee(Double mainServFee) {
		this.mainServFee = mainServFee;
	}
	public Double getSoftFee() {
		return softFee;
	}
	public void setSoftFee(Double softFee) {
		this.softFee = softFee;
	}
	public Double getSoftDisc() {
		return softDisc;
	}
	public void setSoftDisc(Double softDisc) {
		this.softDisc = softDisc;
	}
	public Double getSoftOther() {
		return softOther;
	}
	public void setSoftOther(Double softOther) {
		this.softOther = softOther;
	}
	public Double getIntegrateFee() {
		return integrateFee;
	}
	public void setIntegrateFee(Double integrateFee) {
		this.integrateFee = integrateFee;
	}
	public Double getIntegrateDisc() {
		return integrateDisc;
	}
	public void setIntegrateDisc(Double integrateDisc) {
		this.integrateDisc = integrateDisc;
	}
	public Double getCupboardFee() {
		return cupboardFee;
	}
	public void setCupboardFee(Double cupboardFee) {
		this.cupboardFee = cupboardFee;
	}
	public Double getCupboardDisc() {
		return cupboardDisc;
	}
	public void setCupboardDisc(Double cupboardDisc) {
		this.cupboardDisc = cupboardDisc;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getDesiDpt1() {
		return desiDpt1;
	}
	public void setDesiDpt1(String desiDpt1) {
		this.desiDpt1 = desiDpt1;
	}
	public String getDesiDpt2() {
		return desiDpt2;
	}
	public void setDesiDpt2(String desiDpt2) {
		this.desiDpt2 = desiDpt2;
	}
	public String getDesiDpt3() {
		return desiDpt3;
	}
	public void setDesiDpt3(String desiDpt3) {
		this.desiDpt3 = desiDpt3;
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
	public String getAgainManDescr() {
		return againManDescr;
	}
	public void setAgainManDescr(String againManDescr) {
		this.againManDescr = againManDescr;
	}
	public String getSoftDesignDescr() {
		return softDesignDescr;
	}
	public void setSoftDesignDescr(String softDesignDescr) {
		this.softDesignDescr = softDesignDescr;
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
