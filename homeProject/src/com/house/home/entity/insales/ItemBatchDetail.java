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
 * ItemBatchDetail信息
 */
@Entity
@Table(name = "tItemBatchDetail")
public class ItemBatchDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "Ibdno")
	private String ibdno;
	@Column(name = "Itcode")
	private String itcode;
	@Column(name = "Qty")
	private Double qty;
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
	@Column(name = "DispSeq")
	private Integer dispSeq;
	@Column(name = "OldReqPK")
	private Integer oldReqPk;
	@Column(name = "OldItemCode")
	private String oldItemCode;
	@Column(name = "PrePlanAreaPK")
	private Integer prePlanAreaPk;
	@Column(name = "AreaAttr")
	private String areaAttr;
	@Column(name = "OperType")
	private String operType;

	@Transient
	private String itCodeDescr;
	@Transient
	private String isSetItem;
	@Transient
	private String itemExpired;
	@Transient
	private String isLevel;
	@Transient
	private String custType;
	@Transient
	private String custCode;
	@Transient
	private String itemType1;
	
	@Transient
	private String itemType12;
	
	@Transient
	private String chgNo;
	@Transient
	private String isService;
	
	@Transient
	private String autoImport;
	
	@Transient
	private String excludedReqPks;
	
	/**
	 * 匹配类型：自动导入时不传值或传空值，套内匹配套餐材料，套外匹配普通材料；
     *          传'0'时匹配普通材料；
     *          传'1'时匹配套餐材料
	 */
	@Transient
	private String matchType;

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getIsLevel() {
		return isLevel;
	}

	public void setIsLevel(String isLevel) {
		this.isLevel = isLevel;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setIbdno(String ibdno) {
		this.ibdno = ibdno;
	}
	
	public String getIbdno() {
		return this.ibdno;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	
	public String getItcode() {
		return this.itcode;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	
	public Double getQty() {
		return this.qty;
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
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	
	public Integer getDispSeq() {
		return this.dispSeq;
	}

	public String getItCodeDescr() {
		return itCodeDescr;
	}

	public void setItCodeDescr(String itCodeDescr) {
		this.itCodeDescr = itCodeDescr;
	}

	public String getIsSetItem() {
		return isSetItem;
	}

	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}

	public String getItemExpired() {
		return itemExpired;
	}

	public void setItemExpired(String itemExpired) {
		this.itemExpired = itemExpired;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public Integer getOldReqPk() {
		return oldReqPk;
	}

	public void setOldReqPk(Integer oldReqPk) {
		this.oldReqPk = oldReqPk;
	}

	public String getOldItemCode() {
		return oldItemCode;
	}

	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}

	public Integer getPrePlanAreaPk() {
		return prePlanAreaPk;
	}

	public void setPrePlanAreaPk(Integer prePlanAreaPk) {
		this.prePlanAreaPk = prePlanAreaPk;
	}

	public String getAreaAttr() {
		return areaAttr;
	}

	public void setAreaAttr(String areaAttr) {
		this.areaAttr = areaAttr;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getChgNo() {
		return chgNo;
	}

	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}

	public String getIsService() {
		return isService;
	}

	public void setIsService(String isService) {
		this.isService = isService;
	}

    public String getAutoImport() {
        return autoImport;
    }

    public void setAutoImport(String autoImport) {
        this.autoImport = autoImport;
    }

    public String getExcludedReqPks() {
        return excludedReqPks;
    }

    public void setExcludedReqPks(String excludedReqPks) {
        this.excludedReqPks = excludedReqPks;
    }

    public String getItemType12() {
        return itemType12;
    }

    public void setItemType12(String itemType12) {
        this.itemType12 = itemType12;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
	
}
