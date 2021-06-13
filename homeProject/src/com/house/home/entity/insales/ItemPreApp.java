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
 * ItemPreApp信息
 */
@Entity
@Table(name = "tItemPreApp")
public class ItemPreApp extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "IsSetItem")
	private String isSetItem;
	@Column(name = "Status")
	private String status;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	@Column(name = "ConfirmDate")
	private Date confirmDate;
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
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "EndCode")
	private String endCode;
	@Column(name = "WorkerCode")
	private String workerCode;// 增加workerCode字段
	@Column(name = "ConfirmRemark")
	private String confirmRemark;
	
	@Transient
	private String address;
	@Transient
	private String photoNames;

	@Transient
    private String fromInfoAdd;
	@Transient
    private Integer infoPk;
	
	//20171011领料预申请管理 zzr
	@Transient
	private Date appDateFrom;
	@Transient
	private Date appDateTo;
	@Transient
	private String mainManCode;
	@Transient
	private Date confirmDateFrom;
	@Transient
	private Date confirmDateTo;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String itemRight;

	@Transient
	private String confirmCzyDescr;
	@Transient
	private String appCzyDescr;
	@Transient
	private String mainManDescr;
	
	@Transient
	private String documentNo;
	@Transient
	private String phone;
	@Transient
	private Date signDate;
	@Transient
	private String unShowEndCode;
	@Transient
	private String photoPath;
	
	@Transient
	private String itemConfCheck;
	
	@Transient
	private String requestPage;
	@Transient
	private String unShowStatus;
	@Transient
	private String czybh;
	@Transient
	private Double area;
	@Transient
	private String workerApp;
	
	@Transient
	private String itemRightForSupplier;
	
	@Transient
	private String workerName;// 工人姓名
	@Transient
	private String region;// 片区
	@Transient
	private String prjRegion;// 工程大区
	@Transient
	private String saleIndependence;
	@Transient
	private String custType;
	@Transient
	private String detailJson;
	
	public String getWorkerApp() {
		return workerApp;
	}

	public void setWorkerApp(String workerApp) {
		this.workerApp = workerApp;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setIsSetItem(String isSetItem) {
		this.isSetItem = isSetItem;
	}
	
	public String getIsSetItem() {
		return this.isSetItem;
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
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return this.date;
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
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhotoNames() {
		return photoNames;
	}

	public void setPhotoNames(String photoNames) {
		this.photoNames = photoNames;
	}

	public String getFromInfoAdd() {
		return fromInfoAdd;
	}

	public void setFromInfoAdd(String fromInfoAdd) {
		this.fromInfoAdd = fromInfoAdd;
	}

	public Integer getInfoPk() {
		return infoPk;
	}

	public void setInfoPk(Integer infoPk) {
		this.infoPk = infoPk;
	}
	public Date getAppDateFrom() {
		return appDateFrom;
	}

	public void setAppDateFrom(Date appDateFrom) {
		this.appDateFrom = appDateFrom;
	}

	public Date getAppDateTo() {
		return appDateTo;
	}

	public void setAppDateTo(Date appDateTo) {
		this.appDateTo = appDateTo;
	}

	public String getMainManCode() {
		return mainManCode;
	}

	public void setMainManCode(String mainManCode) {
		this.mainManCode = mainManCode;
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

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getConfirmCzyDescr() {
		return confirmCzyDescr;
	}

	public void setConfirmCzyDescr(String confirmCzyDescr) {
		this.confirmCzyDescr = confirmCzyDescr;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getMainManDescr() {
		return mainManDescr;
	}

	public void setMainManDescr(String mainManDescr) {
		this.mainManDescr = mainManDescr;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getUnShowEndCode() {
		return unShowEndCode;
	}

	public void setUnShowEndCode(String unShowEndCode) {
		this.unShowEndCode = unShowEndCode;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getItemConfCheck() {
		return itemConfCheck;
	}

	public void setItemConfCheck(String itemConfCheck) {
		this.itemConfCheck = itemConfCheck;
	}

	public String getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}

	public String getUnShowStatus() {
		return unShowStatus;
	}

	public void setUnShowStatus(String unShowStatus) {
		this.unShowStatus = unShowStatus;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getItemRightForSupplier() {
		return itemRightForSupplier;
	}

	public void setItemRightForSupplier(String itemRightForSupplier) {
		this.itemRightForSupplier = itemRightForSupplier;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPrjRegion() {
		return prjRegion;
	}

	public void setPrjRegion(String prjRegion) {
		this.prjRegion = prjRegion;
	}

	public String getSaleIndependence() {
		return saleIndependence;
	}

	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
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
}
