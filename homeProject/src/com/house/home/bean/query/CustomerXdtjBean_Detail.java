package com.house.home.bean.query;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * 客户信息查询bean
 */
public class CustomerXdtjBean_Detail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelAnnotation(exportName="一级部门", order=1)
	private String depart1Descr;
	@ExcelAnnotation(exportName="二级部门", order=2)
	private String depart2Descr;
	@ExcelAnnotation(exportName="三级部门", order=3)
	private String depart3Descr;
	@ExcelAnnotation(exportName="楼盘地址", order=4)
	private String address;
	@ExcelAnnotation(exportName="项目名称", order=5)
	private String builderDescr;
	@ExcelAnnotation(exportName="面积", order=6)
	private Integer area;
	@ExcelAnnotation(exportName="设计师", order=7)
	private String designManDescr;
	@ExcelAnnotation(exportName="业务员", order=8)
	private String businessManDescr;
	@ExcelAnnotation(exportName="优惠政策", order=9)
	private String discRemark;
    @ExcelAnnotation(exportName="基础造价", order=10)
    private Double lineBaseFee;
    @ExcelAnnotation(exportName="管理费", order=11)
    private Double manageFee;
    @ExcelAnnotation(exportName="主材", order=12)
    private Double lineMainFee;
    @ExcelAnnotation(exportName="软装", order=13)
    private Double lineSoftFee;
    @ExcelAnnotation(exportName="集成", order=14)
    private Double lineIntegrateFee;
    @ExcelAnnotation(exportName="橱柜", order=15)
    private Double lineCupboardFee;
    @ExcelAnnotation(exportName="服务性产品", order=16)
    private Double mainServFee;
    @ExcelAnnotation(exportName="设计费", order=17)
    private Double designFee;
    @ExcelAnnotation(exportName="制图费", order=18)
    private Double drawFee;
    @ExcelAnnotation(exportName="效果图费", order=19)
    private Double colorDrawFee;
    @ExcelAnnotation(exportName="基础协议优惠", order=20)
    private Double baseDisc;
    @ExcelAnnotation(exportName="实物赠送", order=21)
    private Double gift;
    @ExcelAnnotation(exportName="工程造价", order=22)
    private Double contractFee;
    @ExcelAnnotation(exportName="业绩扣除数", order=23)
    private Double achievded;
    @ExcelAnnotation(exportName="业绩金额", order=24)
    private Double achievFee;
    @ExcelAnnotation(exportName="下定时间", pattern="yyyy-MM-dd", order=25)
	private Date setDate;
    @ExcelAnnotation(exportName="量房时间", pattern="yyyy-MM-dd", order=26)
	private Date measureDate;
    @ExcelAnnotation(exportName="平面时间", pattern="yyyy-MM-dd", order=27)
	private Date planeDate;
    @ExcelAnnotation(exportName="立面时间", pattern="yyyy-MM-dd", order=28)
	private Date facadeDate;
    @ExcelAnnotation(exportName="报价时间", pattern="yyyy-MM-dd", order=29)
	private Date priceDate;
    @ExcelAnnotation(exportName="效果图时间", pattern="yyyy-MM-dd", order=30)
	private Date drawDate;
    @ExcelAnnotation(exportName="放样时间", pattern="yyyy-MM-dd", order=31)
	private Date prevDate;
    @ExcelAnnotation(exportName="签单时间", pattern="yyyy-MM-dd", order=32)
	private Date signDate;
    
	public String getDepart1Descr() {
		return depart1Descr;
	}
	public void setDepart1Descr(String depart1Descr) {
		this.depart1Descr = depart1Descr;
	}
	public String getDepart2Descr() {
		return depart2Descr;
	}
	public void setDepart2Descr(String depart2Descr) {
		this.depart2Descr = depart2Descr;
	}
	public String getDepart3Descr() {
		return depart3Descr;
	}
	public void setDepart3Descr(String depart3Descr) {
		this.depart3Descr = depart3Descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
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
	public String getDiscRemark() {
		return discRemark;
	}
	public void setDiscRemark(String discRemark) {
		this.discRemark = discRemark;
	}
	public Double getLineBaseFee() {
		return lineBaseFee;
	}
	public void setLineBaseFee(Double lineBaseFee) {
		this.lineBaseFee = lineBaseFee;
	}
	public Double getManageFee() {
		return manageFee;
	}
	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}
	public Double getLineMainFee() {
		return lineMainFee;
	}
	public void setLineMainFee(Double lineMainFee) {
		this.lineMainFee = lineMainFee;
	}
	public Double getLineSoftFee() {
		return lineSoftFee;
	}
	public void setLineSoftFee(Double lineSoftFee) {
		this.lineSoftFee = lineSoftFee;
	}
	public Double getLineIntegrateFee() {
		return lineIntegrateFee;
	}
	public void setLineIntegrateFee(Double lineIntegrateFee) {
		this.lineIntegrateFee = lineIntegrateFee;
	}
	public Double getLineCupboardFee() {
		return lineCupboardFee;
	}
	public void setLineCupboardFee(Double lineCupboardFee) {
		this.lineCupboardFee = lineCupboardFee;
	}
	public Double getMainServFee() {
		return mainServFee;
	}
	public void setMainServFee(Double mainServFee) {
		this.mainServFee = mainServFee;
	}
	public Double getDesignFee() {
		return designFee;
	}
	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
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
	public Double getBaseDisc() {
		return baseDisc;
	}
	public void setBaseDisc(Double baseDisc) {
		this.baseDisc = baseDisc;
	}
	public Double getGift() {
		return gift;
	}
	public void setGift(Double gift) {
		this.gift = gift;
	}
	public Double getContractFee() {
		return contractFee;
	}
	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}
	public Double getAchievded() {
		return achievded;
	}
	public void setAchievded(Double achievded) {
		this.achievded = achievded;
	}
	public Double getAchievFee() {
		return achievFee;
	}
	public void setAchievFee(Double achievFee) {
		this.achievFee = achievFee;
	}
	public Date getSetDate() {
		return setDate;
	}
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}
	public Date getMeasureDate() {
		return measureDate;
	}
	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}
	public Date getPlaneDate() {
		return planeDate;
	}
	public void setPlaneDate(Date planeDate) {
		this.planeDate = planeDate;
	}
	public Date getFacadeDate() {
		return facadeDate;
	}
	public void setFacadeDate(Date facadeDate) {
		this.facadeDate = facadeDate;
	}
	public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	public Date getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}
	public Date getPrevDate() {
		return prevDate;
	}
	public void setPrevDate(Date prevDate) {
		this.prevDate = prevDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
    
}
