package com.house.home.entity.insales;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * WareHouse信息
 */
@Entity
@Table(name = "tWareHouse")
public class WareHouse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Desc1")
	private String desc1;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "IsManagePosi")
	private String isManagePosi;
	@Column(name="WareType")
	private String wareType;
	@Column(name="Address")
	private String address;
	@Column(name="IsWHFee")
	private String isWHFee;
	@Column(name="WHFeePer")
	private Double wHFeePer;
	@Column(name="whFeeType")
	private String whFeeType;
	@Column(name="whFeeCostType")
	private String whFeeCostType ;
	@Column(name="DelivType")
	private String delivType ;
	@Column(name="ItemType1")
	private String itemType1;
	
	@Column(name = "SupplCode")
	private String supplCode;
	
	@Column(name = "LimitItemType2")
	private String limitItemType2;
	
	@Column(name = "LimitRegion")
	private String limitRegion;
	
	@Column(name = "IsSupplOrder")
	private String isSupplOrder;
	@Column(name="TaxPayeeCode")
	private String taxPayeeCode;
	@Column(name = "SendFeeCostType")
	private String sendFeeCostType;

    @Transient
	private String czybh;// 操作员编号
	@Transient
	private String delXNCK;
	@Transient
	private String costRight;
	@Transient
	private Integer pk;// 仓库库位表PK --add by zb
	@Transient
	private String whpDescr;// 库位名称 
	@Transient
	private String itCode;// 仓库库位余额 产品编号 --add by zb
	@Transient
	private String itemDescr;// 产品名称
	@Transient
	private String detailJson;// 批量json转xml 用于库位调整上下架 --add by zb
	@Transient
	private Double qtyAll;// 产品余额
	@Transient
	private String haveSelect;// 已经选中的数据
	@Transient
	private String haveFromKwPk;// 已经存在的库位
	
	@Transient
	private String ZWXM;
	@Transient
	private String WareTypeDescr;
	@Transient
	private String IsWHFeeDescr;
	@Transient
	private String IsManagePosiDescr;

	@Transient
	private String showLastSendSupplier;
	@Transient
	private String lastSendSupplierDescr;
	@Transient
	private String custCode;
	@Transient
	private String itemRight;
	@Transient
	private String ctrlItemType1;
	
	@Transient
	private String limitItemType2DetailJson;
	
	@Transient
	private String limitRegionDetailJson;

    public String getTaxPayeeCode() {
		return taxPayeeCode;
	}

	public void setTaxPayeeCode(String taxPayeeCode) {
		this.taxPayeeCode = taxPayeeCode;
	}

	public String getCtrlItemType1() {
		return ctrlItemType1;
	}

	public void setCtrlItemType1(String ctrlItemType1) {
		this.ctrlItemType1 = ctrlItemType1;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getDelXNCK() {
		return delXNCK;
	}

	public void setDelXNCK(String delXNCK) {
		this.delXNCK = delXNCK;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	
	public String getDesc1() {
		return this.desc1;
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

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getCostRight() {
		return costRight;
	}

	public void setCostRight(String costRight) {
		this.costRight = costRight;
	}

	public String getIsManagePosi() {
		return isManagePosi;
	}

	public void setIsManagePosi(String isManagePosi) {
		this.isManagePosi = isManagePosi;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getItCode() {
		return itCode;
	}

	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public String getWhpDescr() {
		return whpDescr;
	}

	public void setWhpDescr(String whpDescr) {
		this.whpDescr = whpDescr;
	}

	public String getItemDescr() {
		return itemDescr;
	}

	public void setItemDescr(String itemDescr) {
		this.itemDescr = itemDescr;
	}

	public String getZWXM() {
		return ZWXM;
	}

	public void setZWXM(String zWXM) {
		ZWXM = zWXM;
	}

	public String getWareTypeDescr() {
		return WareTypeDescr;
	}

	public void setWareTypeDescr(String wareTypeDescr) {
		WareTypeDescr = wareTypeDescr;
	}

	public String getIsWHFeeDescr() {
		return IsWHFeeDescr;
	}

	public void setIsWHFeeDescr(String isWHFeeDescr) {
		IsWHFeeDescr = isWHFeeDescr;
	}

	public String getIsManagePosiDescr() {
		return IsManagePosiDescr;
	}

	public void setIsManagePosiDescr(String isManagePosiDescr) {
		IsManagePosiDescr = isManagePosiDescr;
	}

	public String getWareType() {
		return wareType;
	}

	public void setWareType(String wareType) {
		this.wareType = wareType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsWHFee() {
		return isWHFee;
	}

	public void setIsWHFee(String isWHFee) {
		this.isWHFee = isWHFee;
	}

	public Double getwHFeePer() {
		return wHFeePer;
	}

	public void setwHFeePer(Double wHFeePer) {
		this.wHFeePer = wHFeePer;
	}
	/**
	 * @Description: TODO json转xml,用xml格式传入到存储过程中
	 * @author	created by zb
	 * @date	2018-8-13--下午5:35:19
	 * @return
	 */
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public Double getQtyAll() {
		return qtyAll;
	}

	public void setQtyAll(Double qtyAll) {
		this.qtyAll = qtyAll;
	}

	public String getHaveSelect() {
		return haveSelect;
	}

	public void setHaveSelect(String haveSelect) {
		this.haveSelect = haveSelect;
	}

	public String getHaveFromKwPk() {
		return haveFromKwPk;
	}

	public void setHaveFromKwPk(String haveFromKwPk) {
		this.haveFromKwPk = haveFromKwPk;
	}

	public String getShowLastSendSupplier() {
		return showLastSendSupplier;
	}

	public void setShowLastSendSupplier(String showLastSendSupplier) {
		this.showLastSendSupplier = showLastSendSupplier;
	}

	public String getLastSendSupplierDescr() {
		return lastSendSupplierDescr;
	}

	public void setLastSendSupplierDescr(String lastSendSupplierDescr) {
		this.lastSendSupplierDescr = lastSendSupplierDescr;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWhFeeType() {
		return whFeeType;
	}

	public void setWhFeeType(String whFeeType) {
		this.whFeeType = whFeeType;
	}

	public String getWhFeeCostType() {
		return whFeeCostType;
	}

	public void setWhFeeCostType(String whFeeCostType) {
		this.whFeeCostType = whFeeCostType;
	}

	public String getDelivType() {
		return delivType;
	}

	public void setDelivType(String delivType) {
		this.delivType = delivType;
	}
	
    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public String getLimitItemType2() {
        return limitItemType2;
    }

    public void setLimitItemType2(String limitItemType2) {
        this.limitItemType2 = limitItemType2;
    }

    public String getLimitRegion() {
        return limitRegion;
    }

    public void setLimitRegion(String limitRegion) {
        this.limitRegion = limitRegion;
    }

    public String getIsSupplOrder() {
        return isSupplOrder;
    }

    public void setIsSupplOrder(String isSupplOrder) {
        this.isSupplOrder = isSupplOrder;
    }
    
    public String getLimitItemType2DetailJson() {
        return limitItemType2DetailJson;
    }
    
    public String getLimitItemType2DetailXml() {
        return XmlConverUtil.jsonToXmlNoHead(limitItemType2DetailJson);
    }

    public void setLimitItemType2DetailJson(String limitItemType2DetailJson) {
        this.limitItemType2DetailJson = limitItemType2DetailJson;
    }

    public String getLimitRegionDetailJson() {
        return limitRegionDetailJson;
    }
    
    public String getLimitRegionDetailXml() {
        return XmlConverUtil.jsonToXmlNoHead(limitRegionDetailJson);
    }

    public void setLimitRegionDetailJson(String limitRegionDetailJson) {
        this.limitRegionDetailJson = limitRegionDetailJson;
    }

    public String getSendFeeCostType() {
        return sendFeeCostType;
    }

    public void setSendFeeCostType(String sendFeeCostType) {
        this.sendFeeCostType = sendFeeCostType;
    }
}
