package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

@Entity
@Table(name = "tCustDoc")
public class CustDoc  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name="CustCode")
	private String custCode;
	@Column(name="Descr")
	private String descr;
	@Column(name="DocName")
	private String docName;
	@Column(name="DocType2")
	private String docType2;
	@Column(name="UploadCZY")
	private String uploadCZY;
	@Column(name="UploadDate")
	private Date uploadDate;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@Column(name="Remark")
	private String remark;
	@Column (name="Status")
	private String status; 
	@Column(name="Type")
	private String type;
	@Column (name="ConfirmRemark")
	private String confirmRemark;
	@Column (name="ConfirmDate")
	private Date confirmDate;
	@Column (name ="ConfirmCZY")
	private String confirmCZY;
	@Column (name = "IsPrePlanAreaChg")
	private String isPrePlanAreaChg;
	
	@Transient
	private String docType1;
	@Transient
	private String docType2Descr;
	@Transient 
	private String minDocType2;
	@Transient 
	private String address;
	@Transient
	private int maxLen;
	@Transient
	private String fileType;
	@Transient
	private String custStatus;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private Date submitDateFrom;
	@Transient
	private Date submitDateTo;
	@Transient
	private Date confirmPassDateFrom;
	@Transient
	private Date confirmPassDateTo;
	@Transient
	private String custType;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String upDocType2; //指定文件类型2上传文件 add by zb on 20201002
	@Transient
	private String custDocExpired; //项目资料过期标识
	@Transient
	private String isFullColorDraw;//全景效果图
	
	// 效果图数量
	@Transient
	private Integer drawQty;
	// 3d效果图数量
	@Transient
	private Integer draw3DQty;
	@Transient
	private String docChgCompleted;
	@Transient
	private String itemConfirmCompleted;
	
	
	public String getDocChgCompleted() {
		return docChgCompleted;
	}
	public void setDocChgCompleted(String docChgCompleted) {
		this.docChgCompleted = docChgCompleted;
	}
	public String getItemConfirmCompleted() {
		return itemConfirmCompleted;
	}
	public void setItemConfirmCompleted(String itemConfirmCompleted) {
		this.itemConfirmCompleted = itemConfirmCompleted;
	}
	public String getIsPrePlanAreaChg() {
		return isPrePlanAreaChg;
	}
	public void setIsPrePlanAreaChg(String isPrePlanAreaChg) {
		this.isPrePlanAreaChg = isPrePlanAreaChg;
	}
	public Date getConfirmDateFrom() {
		return confirmDateFrom;
	}
	public void setConfirmDateFrom(Date confirmDateFrom) {
		this.confirmDateFrom = confirmDateFrom;
	}
	public Date getConfirmDateTo() {
		return confirmDateTo;
	}
	public void setConfirmDateTo(Date confirmDateTo) {
		this.confirmDateTo = confirmDateTo;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public Date getSignDateFrom() {
		return signDateFrom;
	}
	public void setSignDateFrom(Date signDateFrom) {
		this.signDateFrom = signDateFrom;
	}
	public Date getSignDateTo() {
		return signDateTo;
	}
	public void setSignDateTo(Date signDateTo) {
		this.signDateTo = signDateTo;
	}
	public Date getSubmitDateFrom() {
		return submitDateFrom;
	}
	public void setSubmitDateFrom(Date submitDateFrom) {
		this.submitDateFrom = submitDateFrom;
	}
	public Date getSubmitDateTo() {
		return submitDateTo;
	}
	public void setSubmitDateTo(Date submitDateTo) {
		this.submitDateTo = submitDateTo;
	}
	public Date getConfirmPassDateFrom() {
		return confirmPassDateFrom;
	}
	public void setConfirmPassDateFrom(Date confirmPassDateFrom) {
		this.confirmPassDateFrom = confirmPassDateFrom;
	}
	public Date getConfirmPassDateTo() {
		return confirmPassDateTo;
	}
	public void setConfirmPassDateTo(Date confirmPassDateTo) {
		this.confirmPassDateTo = confirmPassDateTo;
	}
	public String getConfirmCZY() {
		return confirmCZY;
	}
	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}
	public String getConfirmRemark() {
		return confirmRemark;
	}
	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	public int getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(int maxLen) {
		this.maxLen = maxLen;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMinDocType2() {
		return minDocType2;
	}
	public void setMinDocType2(String minDocType2) {
		this.minDocType2 = minDocType2;
	}
	public String getDocType2Descr() {
		return docType2Descr;
	}
	public void setDocType2Descr(String docType2Descr) {
		this.docType2Descr = docType2Descr;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getDocType1() {
		return docType1;
	}
	public void setDocType1(String docType1) {
		this.docType1 = docType1;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocType2() {
		return docType2;
	}
	public void setDocType2(String docType2) {
		this.docType2 = docType2;
	}
	public String getUploadCZY() {
		return uploadCZY;
	}
	public void setUploadCZY(String uploadCZY) {
		this.uploadCZY = uploadCZY;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
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
	public String getUpDocType2() {
		return upDocType2;
	}
	public void setUpDocType2(String upDocType2) {
		this.upDocType2 = upDocType2;
	}
	public String getCustDocExpired() {
		return custDocExpired;
	}
	public void setCustDocExpired(String custDocExpired) {
		this.custDocExpired = custDocExpired;
	}
	public String getIsFullColorDraw() {
		return isFullColorDraw;
	}
	public void setIsFullColorDraw(String isFullColorDraw) {
		this.isFullColorDraw = isFullColorDraw;
	}
    public Integer getDrawQty() {
        return drawQty;
    }
    public void setDrawQty(Integer drawQty) {
        this.drawQty = drawQty;
    }
	public Integer getDraw3DQty() {
		return draw3DQty;
	}
	public void setDraw3DQty(Integer draw3dQty) {
		draw3DQty = draw3dQty;
	}
	 
}
