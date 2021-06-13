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
 * SendFeeRule信息
 */
@Entity
@Table(name = "tSendFeeRule")
public class SendFeeRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ItemType2")
	private String itemType2;
	@Column(name = "CalType")
	private String calType;
	@Column(name = "Price")
	private Double price;
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
	@Column(name = "SendType")
	private String sendType;
	@Column(name = "SmallSendType")
	private String smallSendType;
	@Column(name = "SmallSendMaxValue")
	private Double smallSendMaxValue;
	@Column(name = "SmallSendFeeAdj")
	private Double smallSendFeeAdj;
	
	@Column(name = "SupplCode")
	private String supplCode;
	
	@Column(name = "WHCode")
	private String wHCode;
	
	@Column(name = "BeginTimes")
	private Integer beginTimes;
	
	@Column(name = "EndTimes")
	private Integer endTimes;
	
	@Column(name = "CardAmount")
	private Double cardAmount;
	
	@Column(name = "IncValue")
	private Double incValue;

    @Transient
	private String itemType3;
	@Transient
	private String itemType3Descr;
	@Transient
	String sendFeeRuleItemJson;
	@Transient
	String itemType3s;
	
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
	public void setCalType(String calType) {
		this.calType = calType;
	}
	
	public String getCalType() {
		return this.calType;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return this.price;
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

	public String getSendFeeRuleItemJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(sendFeeRuleItemJson);
    	return xml;
	}

	public void setSendFeeRuleItemJson(String sendFeeRuleItemJson) {
		this.sendFeeRuleItemJson = sendFeeRuleItemJson;
	}

	public String getItemType3s() {
		return itemType3s;
	}

	public void setItemType3s(String itemType3s) {
		this.itemType3s = itemType3s;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSmallSendType() {
		return smallSendType;
	}

	public void setSmallSendType(String smallSendType) {
		this.smallSendType = smallSendType;
	}

	public Double getSmallSendMaxValue() {
		return smallSendMaxValue;
	}

	public void setSmallSendMaxValue(Double smallSendMaxValue) {
		this.smallSendMaxValue = smallSendMaxValue;
	}

	public Double getSmallSendFeeAdj() {
		return smallSendFeeAdj;
	}

	public void setSmallSendFeeAdj(Double smallSendFeeAdj) {
		this.smallSendFeeAdj = smallSendFeeAdj;
	}
	
    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public String getwHCode() {
        return wHCode;
    }

    public void setwHCode(String wHCode) {
        this.wHCode = wHCode;
    }

    public Integer getBeginTimes() {
        return beginTimes;
    }

    public void setBeginTimes(Integer beginTimes) {
        this.beginTimes = beginTimes;
    }

    public Integer getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(Integer endTimes) {
        this.endTimes = endTimes;
    }

    public Double getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(Double cardAmount) {
        this.cardAmount = cardAmount;
    }

    public Double getIncValue() {
        return incValue;
    }

    public void setIncValue(Double incValue) {
        this.incValue = incValue;
    }

}
