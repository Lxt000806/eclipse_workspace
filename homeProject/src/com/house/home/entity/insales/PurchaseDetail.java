package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * PurchaseDetail信息
 */
@Entity
@Table(name = "tPurchaseDetail")
public class PurchaseDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "Puno")
	private String puno;
	@Column(name = "Itcode")
	private String itcode;
	@Column(name = "QtyCal")
	private Double qtyCal;
	@Column(name = "UnitPrice")
	private Double unitPrice;
	@Column(name = "Amount")
	private Double amount;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "ArrivQty")
	private Double arrivQty;
	@Column(name = "ProjectCost")
	private Double projectCost;
	@Column
	private Double arrivqty;
	@Column (name = "BefLinePrice")
	private double befLinePrice;
	@Column (name = "Markup")
	private double markup;
	
	
	@Transient
	private String custCode;
	@Transient
	private String itdescr;
	@Transient
	private Double allqty;//库存数量
	@Transient
	private String detailJson;
	@Transient
	private Double  purqty;//在途采购数
	@Transient
	private Double saleqty;//销售申请数
	@Transient
	private Double appqty;//领料审核数
	@Transient
	private Double applyqty;//领料申请数
	@Transient
	private Double ypqty; //样品数量
	
	
	@Transient
	private Double useqty;
	@Transient
	private String descr;
	@Transient 
	private String color;
	@Transient
	private String sqlCodeDescr;
	@Transient
	private String uniDescr;
	@Transient
	private String itemtype1;
	@Transient
	private String rowId;
	@Transient 
	private String status;
	@Transient
	private String itemType2;
	@Transient
	private String oldQtyCal;
	
	@Transient
	private String fromPage;
	@Transient
	private String type;
	@Transient
	private String whCode;
	
	
	
	
	public double getBefLinePrice() {
		return befLinePrice;
	}

	public void setBefLinePrice(double befLinePrice) {
		this.befLinePrice = befLinePrice;
	}

	public double getMarkup() {
		return markup;
	}

	public void setMarkup(double markup) {
		this.markup = markup;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getOldQtyCal() {
		return oldQtyCal;
	}

	public void setOldQtyCal(String oldQtyCal) {
		this.oldQtyCal = oldQtyCal;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPurchaseDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setPuno(String puno) {
		this.puno = puno;
	}
	
	public String getPuno() {
		return this.puno;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQtyCal(Double qtyCal) {
		this.qtyCal = qtyCal;
	}
	
	public Double getQtyCal() {
		return this.qtyCal;
	}
	
	public Double getYpqty() {
		return ypqty;
	}
	
	public void setYpqty(Double ypqty) {
		this.ypqty = ypqty;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getUnitPrice() {
		return this.unitPrice;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
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
	public void setArrivQty(Double arrivQty) {
		this.arrivQty = arrivQty;
	}
	
	public Double getArrivQty() {
		return this.arrivQty;
	}
	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	public Double getProjectCost() {
		return this.projectCost;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Double getAllqty() {
		return allqty;
	}

	public void setAllqty(Double allqty) {
		this.allqty = allqty;
	}

	public Double getPurqty() {
		return purqty;
	}

	public void setPurqty(Double purqty) {
		this.purqty = purqty;
	}

	public Double getSaleqty() {
		return saleqty;
	}

	public void setSaleqty(Double saleqty) {
		this.saleqty = saleqty;
	}

	public Double getAppqty() {
		return appqty;
	}

	public void setAppqty(Double appqty) {
		this.appqty = appqty;
	}

	public Double getApplyqty() {
		return applyqty;
	}

	public void setApplyqty(Double applyqty) {
		this.applyqty = applyqty;
	}

	public Double getUseqty() {
		return useqty;
	}

	public void setUseqty(Double useqty) {
		this.useqty = useqty;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getItdescr() {
		return itdescr;
	}

	public void setItdescr(String itdescr) {
		this.itdescr = itdescr;
	}

	public Double getArrivqty() {
		return arrivqty;
	}

	public void setArrivqty(Double arrivqty) {
		this.arrivqty = arrivqty;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSqlCodeDescr() {
		return sqlCodeDescr;
	}

	public void setSqlCodeDescr(String sqlCodeDescr) {
		this.sqlCodeDescr = sqlCodeDescr;
	}

	public String getUniDescr() {
		return uniDescr;
	}

	public void setUniDescr(String uniDescr) {
		this.uniDescr = uniDescr;
	}

	public String getItemtype1() {
		return itemtype1;
	}

	public void setItemtype1(String itemtype1) {
		this.itemtype1 = itemtype1;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
