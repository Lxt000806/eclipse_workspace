package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.house.framework.commons.orm.BaseEntity;

/**
 * tMtCustInfo信息
 */
@Entity
@Table(name = "tMtCustInfo")
public class MtCustInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private int PK;
    @Column(name = "Status")
    private String status;
    @Column(name = "CrtDate")
    private Date crtDate;
    @Column(name = "RegionCode")
    private String regionCode;
    @Column(name = "RegionDescr")
    private String regionDescr;
    @Column(name = "Region2")
    private String region2;
	@Column(name = "ShopName")
	private String shopName;
    @Column(name = "Manage")
    private String manage;
    @Column(name = "ManagePhone")
    private String managePhone;
    @Column(name = "CustCodeMT")
    private String custCodeMT;
    @Column(name = "Address")
    private String address;
    @Column(name = "Area")
    private int area;
	@Column(name = "IsFixtures")
	private String isFixtures;
    @Column(name = "CustDescr")
    private String custDescr;
    @Column(name = "CustPhone")
    private String custPhone;
    @Column(name = "Layout")
    private String layout;
    @Column(name = "Gender")
    private String gender;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "BusinessMan")
    private String businessMan;
    @Column(name = "Perf")
    private Double perf;
    @Column(name = "PerfCompDate")
    private Date perfCompDate;
    @Column(name = "SendDate")
    private Date sendDate;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
    @Column(name = "LastUpdatedBy")
    private String lastUpdatedBy;
    @Column(name = "ActionLog")
    private String actionLog;
    @Column(name = "Expired")
    private String expired;
    @Column(name = "Region2Code")
    private String region2Code;
    
    @Transient
    private String isDistribute;//是否分配
    @Transient
    private String custRight;//查看所有客户权限
    @Transient
    private String czybh;//接收人编号
    @Transient
    private String codes;//区域编号（多选）
    @Transient 
    private String conRemarks;
    @Transient
    private String custCode;
    @Transient
    private String custRemarks;
    
    
	public int getPK() {
		return PK;
	}
	public void setPK(int pK) {
		PK = pK;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionDescr() {
		return regionDescr;
	}
	public void setRegionDescr(String regionDescr) {
		this.regionDescr = regionDescr;
	}
	public String getRegion2() {
		return region2;
	}
	public void setRegion2(String region2) {
		this.region2 = region2;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getManage() {
		return manage;
	}
	public void setManage(String manage) {
		this.manage = manage;
	}
	public String getManagePhone() {
		return managePhone;
	}
	public void setManagePhone(String managePhone) {
		this.managePhone = managePhone;
	}
	public String getCustCodeMT() {
		return custCodeMT;
	}
	public void setCustCodeMT(String custCodeMT) {
		this.custCodeMT = custCodeMT;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getIsFixtures() {
		return isFixtures;
	}
	public void setIsFixtures(String isFixtures) {
		this.isFixtures = isFixtures;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	public Double getPerf() {
		return perf;
	}
	public void setPerf(Double perf) {
		this.perf = perf;
	}
	public Date getPerfCompDate() {
		return perfCompDate;
	}
	public void setPerfCompDate(Date perfCompDate) {
		this.perfCompDate = perfCompDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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
	public String getActionLog() {
		return actionLog;
	}
	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getRegion2Code() {
		return region2Code;
	}
	public void setRegion2Code(String region2Code) {
		this.region2Code = region2Code;
	}
	public String getIsDistribute() {
		return isDistribute;
	}
	public void setIsDistribute(String isDistribute) {
		this.isDistribute = isDistribute;
	}
	public String getCustRight() {
		return custRight;
	}
	public void setCustRight(String custRight) {
		this.custRight = custRight;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getConRemarks() {
		return conRemarks;
	}
	public void setConRemarks(String conRemarks) {
		this.conRemarks = conRemarks;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustRemarks() {
		return custRemarks;
	}
	public void setCustRemarks(String custRemarks) {
		this.custRemarks = custRemarks;
	}
	
    
}
