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
 * PreItemAppSend信息
 */
@Entity
@Table(name = "tPreItemAppSend")
public class PreItemAppSend extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "IANo")
	private String iaNo;
	@Column(name = "WHCode")
	private String whCode;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "SendCZY")
	private String sendCzy;
	@Column(name = "SendDate")
	private Date sendDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String itemType1;
	@Transient
	private String address;
	@Transient
	private Integer pk; //领料发货申请明细 pk
	@Transient
	private Integer refPk; //领料明细pk
	@Transient
	private Double sendQty; // 发货数量
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String sendBachNo;	//送货批次编号
	@Transient
	private String whDescr; //仓库名称
	
	// 领料单配送方式
	@Transient
	private String delivType;
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getIaNo() {
		return iaNo;
	}

	public void setIaNo(String iaNo) {
		this.iaNo = iaNo;
	}

	
	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setSendCzy(String sendCzy) {
		this.sendCzy = sendCzy;
	}
	
	public String getSendCzy() {
		return this.sendCzy;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public Date getSendDate() {
		return this.sendDate;
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

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public Integer getRefPk() {
		return refPk;
	}

	public void setRefPk(Integer refPk) {
		this.refPk = refPk;
	}

	public Double getSendQty() {
		return sendQty;
	}

	public void setSendQty(Double sendQty) {
		this.sendQty = sendQty;
	}
	
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getSendBachNo() {
		return sendBachNo;
	}

	public void setSendBachNo(String sendBachNo) {
		this.sendBachNo = sendBachNo;
	}

	public String getWhDescr() {
		return whDescr;
	}

	public void setWhDescr(String whDescr) {
		this.whDescr = whDescr;
	}

    public String getDelivType() {
        return delivType;
    }

    public void setDelivType(String delivType) {
        this.delivType = delivType;
    }
}
