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
 * ItemBatchHeader信息
 */
@Entity
@Table(name = "tItemBatchHeader")
public class ItemBatchHeader extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Date")
	private Date date;
	@Column(name = "CrtCzy")
	private String crtCzy;
	@Column(name = "ItemType1")
	private String itemType1;
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
	@Column(name = "BatchType")
	private String batchType;
	@Column(name = "Status")
	private String status;
	@Column(name = "custCode")
	private String custCode;
	@Column(name = "OtherRemarks")
	private String otherRemarks;
	

	@Column(name = "CustType")
	private String custType;
	@Column(name = "DispSeq")
	private String dispSeq;
	@Column(name = "WorkType12")
	private String workType12;
	
	@Transient
	private String lastUpdatedByDescr;
	@Transient
	private String crtCzyDescr;
	@Transient
	private String itemType1Descr;
	@Transient
	private String disabledEle;//禁用元素

	@Transient
	private String itemType2;
	@Transient
	private String itemType3;
	@Transient
	private String containDiff;
	@Transient
	private String whCode;
	@Transient
	private String isMultiselect; //是否多选
	
	

	@Transient
	private String ItemRight;
	@Transient
	private String itcodes;
	@Transient
	private String itCode;
	@Transient
	private String itemBatchDetailJson;
	@Transient
	private Integer custWkPk;
	@Transient
	private String workerCode;
	@Transient
	private String address;

	
	
	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public Integer getCustWkPk() {
		return custWkPk;
	}

	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}
	
	public String getCrtCzy() {
		return this.crtCzy;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
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
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
	public String getBatchType() {
		return this.batchType;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getLastUpdatedByDescr() {
		return lastUpdatedByDescr;
	}

	public void setLastUpdatedByDescr(String lastUpdatedByDescr) {
		this.lastUpdatedByDescr = lastUpdatedByDescr;
	}

	public String getCrtCzyDescr() {
		return crtCzyDescr;
	}

	public void setCrtCzyDescr(String crtCzyDescr) {
		this.crtCzyDescr = crtCzyDescr;
	}

	public String getItemType1Descr() {
		return itemType1Descr;
	}

	public void setItemType1Descr(String itemType1Descr) {
		this.itemType1Descr = itemType1Descr;
	}

	public String getDisabledEle() {
		return disabledEle;
	}

	public void setDisabledEle(String disabledEle) {
		this.disabledEle = disabledEle;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getItemType3() {
		return itemType3;
	}

	public void setItemType3(String itemType3) {
		this.itemType3 = itemType3;
	}

	public String getContainDiff() {
		return containDiff;
	}

	public void setContainDiff(String containDiff) {
		this.containDiff = containDiff;
	}

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getIsMultiselect() {
		return isMultiselect;
	}

	public void setIsMultiselect(String isMultiselect) {
		this.isMultiselect = isMultiselect;
	}
	
	
	
	public String getItemRight() {
		return ItemRight;
	}

	public void setItemRight(String itemRight) {
		ItemRight = itemRight;
	}

	public String getItemBatchDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(itemBatchDetailJson);
    	return xml;
	}

	public void setItemBatchDetailJson(String itemBatchDetailJson) {
		this.itemBatchDetailJson = itemBatchDetailJson;
	}

	public String getDispSeq() {
		return dispSeq;
	}

	public void setDispSeq(String dispSeq) {
		this.dispSeq = dispSeq;
	}

	public String getItcodes() {
		return itcodes;
	}

	public void setItcodes(String itcodes) {
		this.itcodes = itcodes;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}

	public String getItCode() {
		return itCode;
	}

	public void setItCode(String itCode) {
		this.itCode = itCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
