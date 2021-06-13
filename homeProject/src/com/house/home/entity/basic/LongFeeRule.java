package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * LongFeeRule信息
 */
@Entity
@Table(name = "tLongFeeRule")
public class LongFeeRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
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
	@Column(name ="SupplCode")
	private String supplCode;
	
	@Column(name = "WHCode")
	private String wHCode;
	
	@Column(name = "SendType")
	private String sendType;
	
	@Column(name = "CalType")
	private String calType;

    @Transient
	private String sendRegion;
	@Transient
	private Double longFee;
	@Transient
	private String sendRegionDescr;
	@Transient
	private String longFeeRuleDetailJson;
	@Transient
	private String sendRegions;
	@Transient
	private String supplDescr;
	@Transient
	private String smallSendType;
	@Transient
	private String smallSendMaxValue;
	@Transient
	private String smallSendTypeDescr;
	
    @Transient
    private String itemType3;
    
    @Transient
    private String itemType3Descr;
    
    @Transient
    private String itemDetailJson;
    
    @Transient
    private String itemType3s;
	
	public void setNo(String no) {
		this.no = no;
	}

    public String getNo() {
		return this.no;
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

	public String getSendRegion() {
		return sendRegion;
	}

	public void setSendRegion(String sendRegion) {
		this.sendRegion = sendRegion;
	}

	public Double getLongFee() {
		return longFee;
	}

	public void setLongFee(Double longFee) {
		this.longFee = longFee;
	}

	public String getSendRegionDescr() {
		return sendRegionDescr;
	}

	public void setSendRegionDescr(String sendRegionDescr) {
		this.sendRegionDescr = sendRegionDescr;
	}

	public String getLongFeeRuleDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(longFeeRuleDetailJson);
    	return xml;
	}

	public void setLongFeeRuleDetailJson(String longFeeRuleDetailJson) {
		this.longFeeRuleDetailJson = longFeeRuleDetailJson;
	}

	public String getSendRegions() {
		return sendRegions;
	}

	public void setSendRegions(String sendRegions) {
		this.sendRegions = sendRegions;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getSmallSendType() {
		return smallSendType;
	}

	public void setSmallSendType(String smallSendType) {
		this.smallSendType = smallSendType;
	}

	public String getSmallSendMaxValue() {
		return smallSendMaxValue;
	}

	public void setSmallSendMaxValue(String smallSendMaxValue) {
		this.smallSendMaxValue = smallSendMaxValue;
	}

	public String getSmallSendTypeDescr() {
		return smallSendTypeDescr;
	}

	public void setSmallSendTypeDescr(String smallSendTypeDescr) {
		this.smallSendTypeDescr = smallSendTypeDescr;
	}
	
    public String getwHCode() {
        return wHCode;
    }

    public void setwHCode(String wHCode) {
        this.wHCode = wHCode;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getCalType() {
        return calType;
    }

    public void setCalType(String calType) {
        this.calType = calType;
    }
    
    public String getItemType3() {
        return itemType3;
    }

    public void setItemType3(String itemType3) {
        this.itemType3 = itemType3;
    }

    public String getItemType3Descr() {
        return itemType3Descr;
    }

    public void setItemType3Descr(String itemType3Descr) {
        this.itemType3Descr = itemType3Descr;
    }

    public String getItemDetailJson() {
        return itemDetailJson;
    }
    
    public String getItemDetailXml() {
        return XmlConverUtil.jsonToXmlNoHead(itemDetailJson);
    }

    public void setItemDetailJson(String itemDetailJson) {
        this.itemDetailJson = itemDetailJson;
    }

    public String getItemType3s() {
        return itemType3s;
    }

    public void setItemType3s(String itemType3s) {
        this.itemType3s = itemType3s;
    }
}
