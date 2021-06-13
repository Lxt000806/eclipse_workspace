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


/**
 * ItemBalAdjHeader信息
 */
@Entity
@Table(name = "tItemBalAdjDetail")
public class ItemBalAdjDetail extends BaseEntity  {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "PK")
		private Integer pk;
		@Column(name = "IBHNo") //仓库调整编号
		private String ibhNo;
		@Column(name = "ITCode") //产品编号
		private String itCode;
		@Column(name = "AdjQty") //调整数量
		private Double adjQty;	
		@Column(name = "Qty") //变动后数量
		private Double qty;		
		@Column(name="Remarks")
		private String remarks;
		@Column(name="LastUpdate")
		private Date lastUpdate;
		@Column(name="LastUpdatedBy")
		private String lastUpdatedBy;
		@Column(name="Expired")
		private String expired;
		@Column(name="ActionLog")
		private String actionLog;
		@Column(name="Cost") //成本单价
		private Double cost;
		@Column(name="AftCost") //变动后平均成本
		private Double aftCost;
		
		
		@Transient
		private Double allQty; //现存库存数量
		@Transient
		private Double adjCost; //调整成本
		@Transient
		private String uom; //单位
		@Transient
		private String uom1; //单位
		@Transient
		private String uom2; //单位
		@Transient
		private String itDescr; //材料名称
		@Transient
		private String whCode; //仓库编号
		@Transient
		private double posiQty;
		@Transient
		private double noPosiQty;
		@Transient
		private String adjType;
		@Transient
		private String allItCode;
		@Transient
		private String noRepeat;
		@Transient
		private String retItCode;
		@Transient
		private String itemType1;
		@Transient
		private String headerRemarks;
		@Transient
		private String headerStatus;
		@Transient
		private String headerType;
		@Transient
		private String WHCode;
		@Transient
		private String adjReason; //调整原因
		
		public String getUom1() {
			return uom1;
		}
		public void setUom1(String uom1) {
			this.uom1 = uom1;
		}
		public String getUom2() {
			return uom2;
		}
		public void setUom2(String uom2) {
			this.uom2 = uom2;
		}
		public String getRetItCode() {
			return retItCode;
		}
		public void setRetItCode(String retItCode) {
			this.retItCode = retItCode;
		}
		public String getNoRepeat() {
			return noRepeat;
		}
		public void setNoRepeat(String noRepeat) {
			this.noRepeat = noRepeat;
		}
		public String getAllItCode() {
			return allItCode;
		}
		public void setAllItCode(String allItCode) {
			this.allItCode = allItCode;
		}
		public String getAdjType() {
			return adjType;
		}
		public void setAdjType(String adjType) {
			this.adjType = adjType;
		}
		public double getPosiQty() {
			return posiQty;
		}
		public void setPosiQty(double posiQty) {
			this.posiQty = posiQty;
		}
		public double getNoPosiQty() {
			return noPosiQty;
		}
		public void setNoPosiQty(double noPosiQty) {
			this.noPosiQty = noPosiQty;
		}
		public Integer getPk() {
			return this.pk;
		}
		public void setPk(Integer pk) {
			this.pk = pk;
		}
		
		
		public String getIbhNo() {
			return this.ibhNo;
		}
		public void setIbhNo(String ibhNo) {
			this.ibhNo = ibhNo;
		}
		
		
		public String getItCode() {
			return this.itCode;
		}
		public void setItCode(String itCode) {
			this.itCode = itCode;
		}
		
		
		public Double getAdjQty() {
			return this.adjQty;
		}		
		public void setAdjQty(Double adjQty) {
			this.adjQty = adjQty;
		}
		
		
		public Double getQty() {
			return this.qty;
		}		
		public void setQty(Double qty) {
			this.qty = qty;
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
		

		public String getActionLog() {
			return this.actionLog;
		}
		public void setActionLog(String actionLog) {
			this.actionLog = actionLog;
		}
		
		
		public Double getCost() {
			return this.cost;
		}		
		public void setCost(Double cost) {
			this.cost = cost;
		}
		
		
		public Double getAftCost() {
			return this.aftCost;
		}		
		public void setAftCost(Double aftCost) {
			this.aftCost = aftCost;
		}
		
		
		public Double getAllQty() {
			return allQty;
		}
		public void setAllQty(Double allQty) {
			this.allQty = allQty;
		}
		
		
		public Double getAdjCost() {
			return adjCost;
		}
		public void setAdjCost(Double adjCost) {
			this.adjCost = adjCost;
		}
		
		
		public String getUom() {
			return this.uom;
		}
		public void setUom(String uom) {
			this.uom = uom;
		}
		
		
		public String getItDescr() {
			return this.itDescr;
		}
		public void setItDescr(String itDescr) {
			this.itDescr = itDescr;
		}
		
		
		public String getWhCode() {
			return whCode;
		}
		public void setWhCode(String whCode) {
			this.whCode = whCode;
		}
		public String getItemType1() {
			return itemType1;
		}
		public void setItemType1(String itemType1) {
			this.itemType1 = itemType1;
		}
		public String getHeaderRemarks() {
			return headerRemarks;
		}
		public void setHeaderRemarks(String headerRemarks) {
			this.headerRemarks = headerRemarks;
		}
		public String getHeaderStatus() {
			return headerStatus;
		}
		public void setHeaderStatus(String headerStatus) {
			this.headerStatus = headerStatus;
		}
		public String getHeaderType() {
			return headerType;
		}
		public void setHeaderType(String headerType) {
			this.headerType = headerType;
		}
		public String getWHCode() {
			return WHCode;
		}
		public void setWHCode(String wHCode) {
			WHCode = wHCode;
		}
		public String getAdjReason() {
			return adjReason;
		}
		public void setAdjReason(String adjReason) {
			this.adjReason = adjReason;
		}
		
}
