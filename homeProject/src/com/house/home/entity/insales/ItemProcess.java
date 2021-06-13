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
 * ItemProcess信息
 */
@Entity
@Table(name = "tItemProcess")
public class ItemProcess extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "ProcessItemWHCode")
	private String processItemWHCode;
	@Column(name = "SourceItemWHCode")
	private String sourceItemWHCode;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCZY")
	private String appCzy;
	@Column(name = "AppDate")
	private Date appDate;
	@Column(name = "ConfirmCZY")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	
	@Transient
	private String detailJson;// 批量json转xml
	@Transient
	private String appCzyDescr;
	@Transient
	private String confirmCzyDescr;
	@Transient
	private String processItemWHCodeDescr;
	@Transient
	private String sourceItemWHCodeDescr;

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
	
	public String getProcessItemWHCode() {
		return processItemWHCode;
	}

	public void setProcessItemWHCode(String processItemWHCode) {
		this.processItemWHCode = processItemWHCode;
	}

	public String getSourceItemWHCode() {
		return sourceItemWHCode;
	}

	public void setSourceItemWHCode(String sourceItemWHCode) {
		this.sourceItemWHCode = sourceItemWHCode;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}
	
	public String getAppCzy() {
		return this.appCzy;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	
	public Date getAppDate() {
		return this.appDate;
	}
	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}
	
	public String getConfirmCzy() {
		return this.confirmCzy;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return this.confirmDate;
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
	
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}

	public String getProcessItemWHCodeDescr() {
		return processItemWHCodeDescr;
	}

	public void setProcessItemWHCodeDescr(String processItemWHCodeDescr) {
		this.processItemWHCodeDescr = processItemWHCodeDescr;
	}

	public String getSourceItemWHCodeDescr() {
		return sourceItemWHCodeDescr;
	}

	public void setSourceItemWHCodeDescr(String sourceItemWHCodeDescr) {
		this.sourceItemWHCodeDescr = sourceItemWHCodeDescr;
	}
	
}