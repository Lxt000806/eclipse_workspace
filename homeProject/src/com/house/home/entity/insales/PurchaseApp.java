package com.house.home.entity.insales;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
@Entity
@Table(name = "tPurchaseApp")
public class PurchaseApp extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "Status")
	private String status;
	@Column(name = "WHCode")
	private String whCode;
	@Column(name = "AppCZY")
	private String appCZY;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmCZY")
	private String confirmCZY;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "Remark")
	private String remark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String detailJson;
	@Transient
	private String puStatus;
	@Transient
	private String itemCodes;
	
	/**
	 * 不可用元素，选择组合使用
	 */
	@Transient
	private String disabledElements;
	
	@Transient
	private String supplier;
	
	@Transient
	private String savedAppDetailPks;
	@Transient
	private String includeAllDetailsPurchased;	
	@Transient
	private String itemCode;
	
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemCodes() {
		return itemCodes;
	}
	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}
    public String getNo() {

		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppCZY() {
		return appCZY;
	}
	public void setAppCZY(String appCZY) {
		this.appCZY = appCZY;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
    public String getDisabledElements() {
        return disabledElements;
    }
    public void setDisabledElements(String disabledElements) {
        this.disabledElements = disabledElements;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getSavedAppDetailPks() {
        return savedAppDetailPks;
    }
    public void setSavedAppDetailPks(String savedAppDetailPks) {
        this.savedAppDetailPks = savedAppDetailPks;
    }
    public String getPuStatus() {
        return puStatus;
    }
    public void setPuStatus(String puStatus) {
        this.puStatus = puStatus;
    }
    public String getIncludeAllDetailsPurchased() {
        return includeAllDetailsPurchased;
    }
    public void setIncludeAllDetailsPurchased(String includeAllDetailsPurchased) {
        this.includeAllDetailsPurchased = includeAllDetailsPurchased;
    }
}
