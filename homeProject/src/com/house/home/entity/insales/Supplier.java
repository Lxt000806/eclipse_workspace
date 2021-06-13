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
 * Supplier信息
 */
@Entity
@Table(name = "tSupplier")
public class Supplier extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Address")
	private String address;
	@Column(name = "Contact")
	private String contact;
	@Column(name = "Phone1")
	private String phone1;
	@Column(name = "Phone2")
	private String phone2;
	@Column(name = "Fax1")
	private String fax1;
	@Column(name = "Fax2")
	private String fax2;
	@Column(name = "Mobile1")
	private String mobile1;
	@Column(name = "Mobile2")
	private String mobile2;
	@Column(name = "Email1")
	private String email1;
	@Column(name = "Email2")
	private String email2;
	@Column(name = "IsSpecDay")
	private String isSpecDay;
	@Column(name = "SpecDay")
	private Integer specDay;
	@Column(name = "BillCycle")
	private Integer billCycle;
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
	@Column(name = "PrepayBalance")
	private Double prepayBalance;
	@Column(name="ActName") //户名
	private String actName;
	@Column(name="CardID") //账号
	private String cardID;
	@Column(name="Bank") //开户行
	private String bank;
	@Column(name="IsWeb") //是否网淘供应商
	private String isWeb;
	@Column(name="Department2") //软装战队
	private String department2;
	@Column(name="PurchCostModel")//采购成本模式 add by gdf
	private String purchCostModel;
	@Column(name="InOrderType")//套内拆单模式
	private String inOrderType;
	@Column(name="OutOrderType")//套外拆单模式
	private String outOrderType;
	@Column(name="SupplFeeType")
	private String supplFeeType;//费用类型
	@Column(name="IsContainTax")
	private String isContainTax;//含税价
	@Column(name="IsGroup")
	private String isGroup; //是否集团供应商
	@Column(name ="PreOrderDay")
	private Integer preOrderDay;
	@Column(name="SendMode")
	private String sendMode;
	@Column(name="BusinessPhoto")
	private String businessPhoto;
	
	@Transient
	private String readonly;//采购管理 新增时 空值供应商类型跟材料类型一样
	@Transient
	private String readAll;//查找编号查看所有 '1':显示所有内容,‘0’显示部分
	
	@Transient
	private String preAppNo;//领料预申请管理查找和预申请单号相关的供应商
	@Transient
	private String type;
	@Transient
	private String existSuppl;
	@Transient 
	private String actNo;
	@Transient
	private String isActSuppl;
	@Transient
	private Date dateF; /* 预付金管理余额查询_变动明细表 变动日期*/	
	@Transient
	private Date dateT;/* 预付金管理余额查询_变动明细表 变动日期*/
	
	@Transient
	private String itemRight;
	@Transient
	private String supplJob; //任务管理 添加供应商任务标志
	@Transient
	private String custCode;
	@Transient
	private String showAll;
	
	@Transient
	private String showLastSendSupplier;
	@Transient
	private String lastSendSupplierDescr;
	@Transient
	private String sendTimeNo; //发货时限编号
	@Transient
	private String detailJson; // 批量json转xml
	@Transient
	private String isDisabled;
	@Transient
	private String cmpCode; //公司编号
	@Transient
	private String jobType; //任务类型
	@Transient
	private String from; //判断是从哪个模块进入
	
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getSupplJob() {
		return supplJob;
	}

	public void setSupplJob(String supplJob) {
		this.supplJob = supplJob;
	}

	public String getIsActSuppl() {
		return isActSuppl;
	}

	public void setIsActSuppl(String isActSuppl) {
		this.isActSuppl = isActSuppl;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getExistSuppl() {
		return existSuppl;
	}

	public void setExistSuppl(String existSuppl) {
		this.existSuppl = existSuppl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getDescr() {
		return this.descr;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	public String getPhone1() {
		return this.phone1;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	public String getPhone2() {
		return this.phone2;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	
	public String getFax1() {
		return this.fax1;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	
	public String getFax2() {
		return this.fax2;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	
	public String getMobile1() {
		return this.mobile1;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	
	public String getMobile2() {
		return this.mobile2;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	public String getEmail1() {
		return this.email1;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	public String getEmail2() {
		return this.email2;
	}
	public void setIsSpecDay(String isSpecDay) {
		this.isSpecDay = isSpecDay;
	}
	
	public String getIsSpecDay() {
		return this.isSpecDay;
	}
	public void setSpecDay(Integer specDay) {
		this.specDay = specDay;
	}
	
	public Integer getSpecDay() {
		return this.specDay;
	}
	public void setBillCycle(Integer billCycle) {
		this.billCycle = billCycle;
	}
	
	public Integer getBillCycle() {
		return this.billCycle;
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

	public Double getPrepayBalance() {
		return prepayBalance;
	}

	public void setPrepayBalance(Double prepayBalance) {
		this.prepayBalance = prepayBalance;
	}

	public String getPreAppNo() {
		return preAppNo;
	}

	public void setPreAppNo(String preAppNo) {
		this.preAppNo = preAppNo;
	}

	public String getReadAll() {
		return readAll;
	}

	public void setReadAll(String readAll) {
		this.readAll = readAll;
	}

	public Date getDateF() {
		return dateF;
	}

	public void setDateF(Date dateF) {
		this.dateF = dateF;
	}

	public Date getDateT() {
		return dateT;
	}

	public void setDateT(Date dateT) {
		this.dateT = dateT;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getShowAll() {
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
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

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getIsWeb() {
		return isWeb;
	}

	public void setIsWeb(String isWeb) {
		this.isWeb = isWeb;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getSendTimeNo() {
		return sendTimeNo;
	}

	public void setSendTimeNo(String sendTimeNo) {
		this.sendTimeNo = sendTimeNo;
	}

	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getPurchCostModel() {
		return purchCostModel;
	}

	public void setPurchCostModel(String purchCostModel) {
		this.purchCostModel = purchCostModel;
	}

	public String getInOrderType() {
		return inOrderType;
	}

	public void setInOrderType(String inOrderType) {
		this.inOrderType = inOrderType;
	}

	public String getOutOrderType() {
		return outOrderType;
	}

	public void setOutOrderType(String outOrderType) {
		this.outOrderType = outOrderType;
	}

	public String getSupplFeeType() {
		return supplFeeType;
	}

	public void setSupplFeeType(String supplFeeType) {
		this.supplFeeType = supplFeeType;
	}

	public String getIsContainTax() {
		return isContainTax;
	}

	public void setIsContainTax(String isContainTax) {
		this.isContainTax = isContainTax;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getPreOrderDay() {
		return preOrderDay;
	}

	public void setPreOrderDay(Integer preOrderDay) {
		this.preOrderDay = preOrderDay;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSendMode() {
		return sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public String getBusinessPhoto() {
		return businessPhoto;
	}

	public void setBusinessPhoto(String businessPhoto) {
		this.businessPhoto = businessPhoto;
	}
	
	
}
