package com.house.home.client.service.evt;

import java.util.Date;

public class ResrCustEvt extends BaseQueryEvt {

	private String code;
	private String descr;
	private String address;
	private String mobile1;
	private String mobile2;
	private String custKind;
	private String custResStat;
	private String lastUpdateType;
	private String czybh;
	private String source;
	private String builderCode;
	private String builderNum;
	private String layout;
	private Integer area;
	private String gender;
	private String email2;
	private String constructType;
	private String netChanel;
	private String regionCode;
	private String crtCzy;
	private Date crtDate;
	private String businessMan;
	private Date disptchDate;
	private String remark;
	private String regionDescr;
	private String status;
	private String addrProperty;
	private Date contactBeginDate;	//最后联系时间-自定义-时间选择 add by zb on 20200411
	private Date contactEndDate;
	private String notContLastUpdateType;	//最近未联系类型
	private Date notContactBeginDate;	//最近未联系-自定义-时间选择
	private Date notContactEndDate;
	private String extraOrderNo;
	private String codes;
	private String cancelRemarks;
	private String cancelRsn;
	private String dispatchDateType;
	private Date dispatchBeginDate;	
	private Date dispatchEndDate;
	private String tagPks;
	private String resrType;
	private String haveCustTag;
	private String resrCustPoolNo;
	
	public String getExtraOrderNo() {
		return extraOrderNo;
	}
	public void setExtraOrderNo(String extraOrderNo) {
		this.extraOrderNo = extraOrderNo;
	}
	public String getAddrProperty() {
		return addrProperty;
	}
	public void setAddrProperty(String addrProperty) {
		this.addrProperty = addrProperty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegionDescr() {
		return regionDescr;
	}
	public void setRegionDescr(String regionDescr) {
		this.regionDescr = regionDescr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBuilderCode() {
		return builderCode;
	}
	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}
	public String getBuilderNum() {
		return builderNum;
	}
	public void setBuilderNum(String builderNum) {
		this.builderNum = builderNum;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getConstructType() {
		return constructType;
	}
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}
	public String getNetChanel() {
		return netChanel;
	}
	public void setNetChanel(String netChanel) {
		this.netChanel = netChanel;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCrtCzy() {
		return crtCzy;
	}
	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public String getBusinessMan() {
		return businessMan;
	}
	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}
	
	public Date getDisptchDate() {
		return disptchDate;
	}
	public void setDisptchDate(Date disptchDate) {
		this.disptchDate = disptchDate;
	}
	public String getCzybh() {
		return czybh;
	}
	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getCustKind() {
		return custKind;
	}
	public void setCustKind(String custKind) {
		this.custKind = custKind;
	}
	public String getCustResStat() {
		return custResStat;
	}
	public void setCustResStat(String custResStat) {
		this.custResStat = custResStat;
	}
	public String getLastUpdateType() {
		return lastUpdateType;
	}
	public void setLastUpdateType(String lastUpdateType) {
		this.lastUpdateType = lastUpdateType;
	}
	public Date getContactBeginDate() {
		return contactBeginDate;
	}
	public void setContactBeginDate(Date contactBeginDate) {
		this.contactBeginDate = contactBeginDate;
	}
	public Date getContactEndDate() {
		return contactEndDate;
	}
	public void setContactEndDate(Date contactEndDate) {
		this.contactEndDate = contactEndDate;
	}
	public String getNotContLastUpdateType() {
		return notContLastUpdateType;
	}
	public void setNotContLastUpdateType(String notContLastUpdateType) {
		this.notContLastUpdateType = notContLastUpdateType;
	}
	public Date getNotContactBeginDate() {
		return notContactBeginDate;
	}
	public void setNotContactBeginDate(Date notContactBeginDate) {
		this.notContactBeginDate = notContactBeginDate;
	}
	public Date getNotContactEndDate() {
		return notContactEndDate;
	}
	public void setNotContactEndDate(Date notContactEndDate) {
		this.notContactEndDate = notContactEndDate;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getCancelRemarks() {
		return cancelRemarks;
	}
	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
	}
	public String getCancelRsn() {
		return cancelRsn;
	}
	public void setCancelRsn(String cancelRsn) {
		this.cancelRsn = cancelRsn;
	}
	public String getDispatchDateType() {
		return dispatchDateType;
	}
	public void setDispatchDateType(String dispatchDateType) {
		this.dispatchDateType = dispatchDateType;
	}
	public Date getDispatchBeginDate() {
		return dispatchBeginDate;
	}
	public void setDispatchBeginDate(Date dispatchBeginDate) {
		this.dispatchBeginDate = dispatchBeginDate;
	}
	public Date getDispatchEndDate() {
		return dispatchEndDate;
	}
	public void setDispatchEndDate(Date dispatchEndDate) {
		this.dispatchEndDate = dispatchEndDate;
	}
	public String getTagPks() {
		return tagPks;
	}
	public void setTagPks(String tagPks) {
		this.tagPks = tagPks;
	}
	public String getResrType() {
		return resrType;
	}
	public void setResrType(String resrType) {
		this.resrType = resrType;
	}
	public String getHaveCustTag() {
		return haveCustTag;
	}
	public void setHaveCustTag(String haveCustTag) {
		this.haveCustTag = haveCustTag;
	}
	public String getResrCustPoolNo() {
		return resrCustPoolNo;
	}
	public void setResrCustPoolNo(String resrCustPoolNo) {
		this.resrCustPoolNo = resrCustPoolNo;
	}
	
}
