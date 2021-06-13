package com.house.home.bean.basic;

import java.util.Date;
import com.house.framework.commons.excel.ExcelAnnotation;

/**
 * Item信息bean
 */
public class ItemBean implements java.io.Serializable {

	private static final long serialVersionUID = 2L;

	@ExcelAnnotation(exportName="编号", order=1)
	private String code;
	@ExcelAnnotation(exportName="材料名称", order=2)
	private String descr;
	@ExcelAnnotation(exportName="助记码", order=3)
	private String remCode;
	@ExcelAnnotation(exportName="材料类型1", order=4)
	private String itemType1;
	@ExcelAnnotation(exportName="材料类型2", order=5)
	private String itemType2;
	@ExcelAnnotation(exportName="材料类型3", order=6)
	private String itemType3;
	@ExcelAnnotation(exportName="条码", order=7)
	private String barCode;
	@ExcelAnnotation(exportName="品牌", order=8)
	private String sqlCode;
	@ExcelAnnotation(exportName="供应商编号", order=9)
	private String supplCode;
	@ExcelAnnotation(exportName="型号", order=10)
	private String model;
	@ExcelAnnotation(exportName="规格说明", order=11)//废弃
	private Integer itemSize;
	@ExcelAnnotation(exportName="规格", order=12)
	private String sizeDesc;
	@ExcelAnnotation(exportName="颜色", order=13)
	private String color;
	@ExcelAnnotation(exportName="单位", order=14)
	private String uom;
	@ExcelAnnotation(exportName="成本", order=15)
	private Double cost;
	@ExcelAnnotation(exportName="市场价", order=16)
	private Double marketPrice;
	@ExcelAnnotation(exportName="现价", order=17)
	private Double price;
	@ExcelAnnotation(exportName="是否固定价格", order=18)
	private String isFixPrice;
	@ExcelAnnotation(exportName="提成类型", order=19)
	private String commiType;
	@ExcelAnnotation(exportName="提成比例", order=20)
	private Double commiPerc;
	@ExcelAnnotation(exportName="材料描述", order=21)
	private String remark;
    	@ExcelAnnotation(exportName="最后跟新时间", pattern="yyyy-MM-dd HH:mm:ss", order=22)
	private Date lastUpdate;
	@ExcelAnnotation(exportName="最后修改人", order=23)
	private String lastUpdatedBy;
	@ExcelAnnotation(exportName="过期", order=24)
	private String expired;
	@ExcelAnnotation(exportName="操作", order=25)
	private String actionLog;
	@ExcelAnnotation(exportName="显示顺序", order=26)
	private Integer dispSeq;
	@ExcelAnnotation(exportName="移动平均成本", order=27)
	private Double avgCost;
	@ExcelAnnotation(exportName="所有库存数量", order=28)
	private Double allQty;
	@ExcelAnnotation(exportName="项目经理结算价", order=29)
	private Double projectCost;
	@ExcelAnnotation(exportName="是否套餐材料", order=30)
	private String isSetItem;
	@ExcelAnnotation(exportName="发货类型", order=31)
	private String sendType;
	@ExcelAnnotation(exportName="发货仓库", order=32)
	private String whCode;
	@ExcelAnnotation(exportName="最小库存量", order=33)
	private Double minQty;
	@ExcelAnnotation(exportName="是否上样", order=34)
	private String hassample;
	@ExcelAnnotation(exportName="安装费类型", order=35)
	private String installFeeType;
	@ExcelAnnotation(exportName="安装费单价", order=36)
	private String installFee;
	@ExcelAnnotation(exportName="错误信息", order=37)
	private String error;
	@ExcelAnnotation(exportName="买手1", order=38)
	private String buyer1;
	@ExcelAnnotation(exportName="仓储费类型", order=39)
	private String whFeeType;
	@ExcelAnnotation(exportName="仓储费", order=40)
	private Double whFee;
	@ExcelAnnotation(exportName="客户类型", order=41)
	private String custType;
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setRemCode(String remCode) {
		this.remCode = remCode;
	}
	
	public String getRemCode() {
		return this.remCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}
	
	public String getItemType2() {
		return this.itemType2;
	}
	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}
	
	public String getItemType3() {
		return this.itemType3;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	
	public String getBarCode() {
		return this.barCode;
	}
	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}
	
	public String getSqlCode() {
		return this.sqlCode;
	}
	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}
	
	public String getSupplCode() {
		return this.supplCode;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getModel() {
		return this.model;
	}
	public void setItemSize(Integer itemSize) {
		this.itemSize = itemSize;
	}
	
	public Integer getItemSize() {
		return this.itemSize;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	
	public String getSizeDesc() {
		return this.sizeDesc;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getUom() {
		return this.uom;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public Double getCost() {
		return this.cost;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public Double getMarketPrice() {
		return this.marketPrice;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return this.price;
	}
	public void setIsFixPrice(String isFixPrice) {
		this.isFixPrice = isFixPrice;
	}
	
	public String getIsFixPrice() {
		return this.isFixPrice;
	}
	public void setCommiType(String commiType) {
		this.commiType = commiType;
	}
	
	public String getCommiType() {
		return this.commiType;
	}
	public void setCommiPerc(Double commiPerc) {
		this.commiPerc = commiPerc;
	}
	
	public Double getCommiPerc() {
		return this.commiPerc;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getExpired() {
		return this.expired;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	
	public String getActionLog() {
		return this.actionLog;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}
	public void setAvgCost(Double avgCost) {
		this.avgCost = avgCost;
	}
	
	public Double getAvgCost() {
		return this.avgCost;
	}
	public void setAllQty(Double allQty) {
		this.allQty = allQty;
	}
	
	public Double getAllQty() {
		return this.allQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	
	public String getIsSetItem() {
		return this.isSetItem;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public Double getMinQty() {
		return minQty;
	}

	public void setMinQty(Double minQty) {
		this.minQty = minQty;
	}

	public String getHassample() {
		return hassample;
	}

	public void setHassample(String hassample) {
		this.hassample = hassample;
	}

	public String getInstallFeeType() {
		return installFeeType;
	}

	public void setInstallFeeType(String installFeeType) {
		this.installFeeType = installFeeType;
	}

	public String getInstallFee() {
		return installFee;
	}

	public void setInstallFee(String installFee) {
		this.installFee = installFee;
	}

	public String getBuyer1() {
		return buyer1;
	}

	public void setBuyer1(String buyer1) {
		this.buyer1 = buyer1;
	}

	public String getWhFeeType() {
		return whFeeType;
	}

	public void setWhFeeType(String whFeeType) {
		this.whFeeType = whFeeType;
	}

	public Double getWhFee() {
		return whFee;
	}
	public void setWhFee(Double whFee) {
		this.whFee = whFee;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	
}
