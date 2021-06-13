package com.house.home.entity.design;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.annotation.DescrAnnotation;
import com.house.framework.annotation.NoteAnnotation;
import com.house.framework.commons.excel.ExcelAnnotation;
import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

@Entity
@Table(name = "tResrCust")
public class ResrCust extends BaseEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Code")
	private String code;
	@ExcelAnnotation(exportName="小区名称", order=1)
	@DescrAnnotation(descr="项目名称")
	@NoteAnnotation(tableName="tBuilder",code="Code")
	@Column(name="BuilderCode")
	private String builderCode;
	@ExcelAnnotation(exportName="楼盘地址", order=2)
	@DescrAnnotation(descr="楼盘")
	@Column(name="Address")
	private String address;
	@ExcelAnnotation(exportName="状态", order=3)
	@DescrAnnotation(descr="状态")
	@NoteAnnotation(tableName="tXTDM",code="RESRCUSTSTS")
	@Column(name="Status")
	private String status;
	@ExcelAnnotation(exportName="客户名称", order=4)
	@DescrAnnotation(descr="客户名称")
	@Column(name="Descr")
	private String descr;
	@ExcelAnnotation(exportName="性别", order=5)
	@DescrAnnotation(descr="性别")
	@NoteAnnotation(tableName="tXTDM",code="GENDER")
	@Column(name="Gender")
	private String gender;
	@DescrAnnotation(descr="跟单人员")
	@NoteAnnotation(tableName="tEmployee",code="Number",note="NameChi")
	@Column(name="BusinessMan")
	private String businessMan;
	@Column(name="CrtDate")
	private Date crtDate;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name="Expired")
	private String expired;
	@Column(name="ActionLog")
	private String actionLog;
	@ExcelAnnotation(exportName="手机号", order=6)
	@DescrAnnotation(descr="手机号")
	@Column(name="Mobile1")
	private String mobile1;
	@ExcelAnnotation(exportName="客户分类", order=7)
	@DescrAnnotation(descr="客户分类")
	@NoteAnnotation(tableName="tXTDM",code="CUSTKIND")
	@Column(name = "CustKind")
	private String custKind;
	@ExcelAnnotation(exportName="客户来源", order=8)
	@DescrAnnotation(descr="客户来源")
	@NoteAnnotation(tableName="tXTDM",code="CUSTOMERSOURCE")
	@Column(name = "Source")
	private String source;
	@ExcelAnnotation(exportName="楼栋号", order=9)
	@DescrAnnotation(descr="楼栋号")
	@Column(name = "BuilderNum")
	private String builderNum;
	@ExcelAnnotation(exportName="户型", order=10)
	@DescrAnnotation(descr="户型")
	@NoteAnnotation(tableName="tXTDM",code="LAYOUT")
	@Column(name = "Layout")
	private String layout;
	@DescrAnnotation(descr="面积")
	@Column(name = "Area")
	private Integer area;
	@ExcelAnnotation(exportName="微信/Email", order=12)
	@DescrAnnotation(descr="微信/Email")
	@Column(name="Email2")
	private String email2;
	@ExcelAnnotation(exportName="客户类别", order=13)
	@DescrAnnotation(descr="客户类别")
	@NoteAnnotation(tableName="tXTDM",code="CUSTCLASS")
	@Column(name="ConstructType")
	private String constructType;
	@Column(name = "CrtCzy")
	private String crtCzy;
	@Column(name = "crtCzyDept")
	private String CrtCZYDept;
	@DescrAnnotation(descr="客户资源状态")
	@NoteAnnotation(tableName="tXTDM",code="CUSTRESSTAT")
	@Column(name = "CustResStat")
	private String custResStat;
	@ExcelAnnotation(exportName="备注", order=14)
	@DescrAnnotation(descr="备注")
	@Column(name = "Remark")
	private String remark;
	@Column(name ="DispatchDate")
	private Date dispatchDate;
	@ExcelAnnotation(exportName="网络渠道", order=15)
	@DescrAnnotation(descr="网络渠道")
	@NoteAnnotation(tableName="tCustNetCnl",code="Code",note="Descr")
	@Column(name="NetChanel")
	private String netChanel;
	@ExcelAnnotation(exportName="区域", order=19)
	@DescrAnnotation(descr="区域")
	@NoteAnnotation(tableName="tRegion",code="Code",note="Descr")
	@Column(name="RegionCode")
	private String regionCode; 
	@ExcelAnnotation(exportName="楼盘性质", order=16)
	@DescrAnnotation(descr="楼盘性质")
	@NoteAnnotation(tableName="tXTDM",code="ADDRPROPERTY")
	@Column(name="AddrProperty")
	private String addrProperty;
	@ExcelAnnotation(exportName="手机号2", order=17)
	@DescrAnnotation(descr="手机号2")
	@Column(name="Mobile2")
	private String mobile2;
	@ExcelAnnotation(exportName="外部订单编号", order=18)
	@DescrAnnotation(descr="外部订单编号")
	@Column(name="ExtraOrderNo")
	private String extraOrderNo;
	@Column(name="Department")
	private String department;
	@Column(name="PublicResrLevel")
	private String publicResrLevel;
	@Column(name="CancelRsn")
	private String cancelRsn;
	@Column(name="CancelRemarks")
	private String cancelRemarks;
	@Column(name="CancelDate")
	private Date cancelDate;
	@Column(name="ShareCZY")
	private String shareCzy;
	@Column(name="ShareDate")
	private Date shareDate;
	@Column(name="ResrCustPoolNo")
	private String resrCustPoolNo;
	@Column(name="ValidDispatchCount")
	private Integer validDispatchCount;
	@Column(name="NoValidCount")
	private Integer noValidCount;
	
	
	@Transient
	private String builderDescr;
	@Transient
	private String businessManDescr;
	@Transient
	private String department2;
	@Transient
	private String czybh;
	@Transient
	private String custRight;
	@Transient
	private String rightType;
	@Transient
	private String isLead;
	@Transient
	private Date dispatchDateFrom;
	@Transient
	private Date dispatchDateTo;
	@Transient
	private String crtCzyDeptDescr;
	@Transient
	private String crtCzyDescr;
	@Transient
	private String codes;
	@Transient
	private String recentlyConcat;
	@Transient
	private String type;
	@Transient
	private String lastUpdateType;
	@Transient
	private String appType;
	@Transient
	private Date measureDateFrom;
	@Transient
	private Date measureDateTo;
	@Transient
	private Date visitDateFrom;
	@Transient
	private Date visitDateTo;
	@Transient
	private Date setDateFrom;
	@Transient
	private Date setDateTo;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@ExcelAnnotation(exportName="面积", order=11)
	@Transient
	private String areaForExcel;//Excel导入时用的面积，设置为string防止类型转换错误
	@Transient
	private Date contactBeginDate;	//最后联系时间-自定义-时间选择 add by zb on 20200411
	@Transient
	private Date contactEndDate;
	@Transient
	private String notContLastUpdateType;	//最近未联系类型
	@Transient
	private Date notContactBeginDate;	//最近未联系-自定义-时间选择
	@Transient
	private Date notContactEndDate;
	@Transient
	private String custCode;
	@Transient
	private Date nextConDateFrom;
	@Transient
	private Date nextConDateTo;
	@Transient
	private String conWay;
	@Transient
	private Date nextConDate;
	@Transient
	private String businessManDept;
	@Transient
	private String hasCreateAuth;//查看本人创建记录权限
	@Transient
	private String hasConAuth;//查看所有跟踪记录权限
	@Transient
	private String canModiResStat;//是否可编辑资源客户状态
	@Transient
	private String dispatchDateType;
	@Transient
	private Date dispatchBeginDate;
	@Transient
	private Date dispatchEndDate;
	@Transient
	private String custTag;//客户标签
	@Transient
	private String batchPlan;//批量方案
	@Transient
	private String delTagPks;//撤销的标签pk
	@Transient
	private String addTagPks;//添加的标签pk
	@Transient
	private String resrType;//资源类型
	@Transient
	private String shareCzyDescr;//共享人名称
	@Transient
	private String resrCustJson;
	@Transient
	private String hasMobileAuth;//查看其他客户电话权限
	@Transient
	private String haveCustTag;//是否设置标签
	@Transient
	private String defPoolNo;
	
	public String getDefPoolNo() {
		return defPoolNo;
	}

	public void setDefPoolNo(String defPoolNo) {
		this.defPoolNo = defPoolNo;
	}

	public String getResrCustPoolNo() {
		return resrCustPoolNo;
	}

	public void setResrCustPoolNo(String resrCustPoolNo) {
		this.resrCustPoolNo = resrCustPoolNo;
	}

	public Integer getValidDispatchCount() {
		return validDispatchCount;
	}

	public void setValidDispatchCount(Integer validDispatchCount) {
		this.validDispatchCount = validDispatchCount;
	}

	public Integer getNoValidCount() {
		return noValidCount;
	}

	public void setNoValidCount(Integer noValidCount) {
		this.noValidCount = noValidCount;
	}

	public ResrCust() {
		super();
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getLastUpdateType() {
		return lastUpdateType;
	}

	public void setLastUpdateType(String lastUpdateType) {
		this.lastUpdateType = lastUpdateType;
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

	public String getCrtCzy() {
		return crtCzy;
	}

	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}

	public String getCrtCZYDept() {
		return CrtCZYDept;
	}

	public void setCrtCZYDept(String crtCZYDept) {
		CrtCZYDept = crtCZYDept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
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

	public String getCustResStat() {
		return custResStat;
	}

	public void setCustResStat(String custResStat) {
		this.custResStat = custResStat;
	}

	public String getCustKind() {
		return custKind;
	}

	public void setCustKind(String custKind) {
		this.custKind = custKind;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getIsLead() {
		return isLead;
	}

	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
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

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getBuilderDescr() {
		return builderDescr;
	}

	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}

	public String getBusinessManDescr() {
		return businessManDescr;
	}

	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getBuilderCode() {
		return builderCode;
	}

	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDispatchDateFrom() {
		return dispatchDateFrom;
	}

	public void setDispatchDateFrom(Date dispatchDateFrom) {
		this.dispatchDateFrom = dispatchDateFrom;
	}

	public Date getDispatchDateTo() {
		return dispatchDateTo;
	}

	public void setDispatchDateTo(Date dispatchDateTo) {
		this.dispatchDateTo = dispatchDateTo;
	}

	
	public String getCrtCzyDeptDescr() {
		return crtCzyDeptDescr;
	}

	public void setCrtCzyDeptDescr(String crtCzyDeptDescr) {
		this.crtCzyDeptDescr = crtCzyDeptDescr;
	}

	public String getCrtCzyDescr() {
		return crtCzyDescr;
	}

	public void setCrtCzyDescr(String crtCzyDescr) {
		this.crtCzyDescr = crtCzyDescr;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getRecentlyConcat() {
		return recentlyConcat;
	}

	public void setRecentlyConcat(String recentlyConcat) {
		this.recentlyConcat = recentlyConcat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddrProperty() {
		return addrProperty;
	}

	public void setAddrProperty(String addrProperty) {
		this.addrProperty = addrProperty;
	}

	public Date getMeasureDateFrom() {
		return measureDateFrom;
	}

	public void setMeasureDateFrom(Date measureDateFrom) {
		this.measureDateFrom = measureDateFrom;
	}

	public Date getMeasureDateTo() {
		return measureDateTo;
	}

	public void setMeasureDateTo(Date measureDateTo) {
		this.measureDateTo = measureDateTo;
	}

	public Date getVisitDateFrom() {
		return visitDateFrom;
	}

	public void setVisitDateFrom(Date visitDateFrom) {
		this.visitDateFrom = visitDateFrom;
	}

	public Date getVisitDateTo() {
		return visitDateTo;
	}

	public void setVisitDateTo(Date visitDateTo) {
		this.visitDateTo = visitDateTo;
	}

	public Date getSetDateFrom() {
		return setDateFrom;
	}

	public void setSetDateFrom(Date setDateFrom) {
		this.setDateFrom = setDateFrom;
	}

	public Date getSetDateTo() {
		return setDateTo;
	}

	public void setSetDateTo(Date setDateTo) {
		this.setDateTo = setDateTo;
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
	
	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getAreaForExcel() {
		return areaForExcel;
	}

	public void setAreaForExcel(String areaForExcel) {
		this.areaForExcel = areaForExcel;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

    public String getExtraOrderNo() {
        return extraOrderNo;
    }

    public void setExtraOrderNo(String extraOrderNo) {
        this.extraOrderNo = extraOrderNo;
    }

	public Date getNextConDateFrom() {
		return nextConDateFrom;
	}

	public void setNextConDateFrom(Date nextConDateFrom) {
		this.nextConDateFrom = nextConDateFrom;
	}

	public Date getNextConDateTo() {
		return nextConDateTo;
	}

	public void setNextConDateTo(Date nextConDateTo) {
		this.nextConDateTo = nextConDateTo;
	}

	public String getConWay() {
		return conWay;
	}

	public void setConWay(String conWay) {
		this.conWay = conWay;
	}

	public Date getNextConDate() {
		return nextConDate;
	}

	public void setNextConDate(Date nextConDate) {
		this.nextConDate = nextConDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPublicResrLevel() {
		return publicResrLevel;
	}

	public void setPublicResrLevel(String publicResrLevel) {
		this.publicResrLevel = publicResrLevel;
	}

	public String getCancelRsn() {
		return cancelRsn;
	}

	public void setCancelRsn(String cancelRsn) {
		this.cancelRsn = cancelRsn;
	}

	public String getCancelRemarks() {
		return cancelRemarks;
	}

	public void setCancelRemarks(String cancelRemarks) {
		this.cancelRemarks = cancelRemarks;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getBusinessManDept() {
		return businessManDept;
	}

	public void setBusinessManDept(String businessManDept) {
		this.businessManDept = businessManDept;
	}

	public String getHasCreateAuth() {
		return hasCreateAuth;
	}

	public void setHasCreateAuth(String hasCreateAuth) {
		this.hasCreateAuth = hasCreateAuth;
	}

	public String getHasConAuth() {
		return hasConAuth;
	}

	public void setHasConAuth(String hasConAuth) {
		this.hasConAuth = hasConAuth;
	}

	public String getCanModiResStat() {
		return canModiResStat;
	}

	public void setCanModiResStat(String canModiResStat) {
		this.canModiResStat = canModiResStat;
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

	public String getCustTag() {
		return custTag;
	}

	public void setCustTag(String custTag) {
		this.custTag = custTag;
	}

	public String getBatchPlan() {
		return batchPlan;
	}

	public void setBatchPlan(String batchPlan) {
		this.batchPlan = batchPlan;
	}

	public String getDelTagPks() {
		return delTagPks;
	}

	public void setDelTagPks(String delTagPks) {
		this.delTagPks = delTagPks;
	}

	public String getAddTagPks() {
		return addTagPks;
	}

	public void setAddTagPks(String addTagPks) {
		this.addTagPks = addTagPks;
	}

	public String getShareCzy() {
		return shareCzy;
	}

	public void setShareCzy(String shareCzy) {
		this.shareCzy = shareCzy;
	}

	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}

	public String getResrType() {
		return resrType;
	}

	public void setResrType(String resrType) {
		this.resrType = resrType;
	}

	public String getShareCzyDescr() {
		return shareCzyDescr;
	}

	public void setShareCzyDescr(String shareCzyDescr) {
		this.shareCzyDescr = shareCzyDescr;
	}

	public String getResrCustJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(resrCustJson);
    	return xml;
	}

	public void setResrCustJson(String resrCustJson) {
		this.resrCustJson = resrCustJson;
	}

	public String getHasMobileAuth() {
		return hasMobileAuth;
	}

	public void setHasMobileAuth(String hasMobileAuth) {
		this.hasMobileAuth = hasMobileAuth;
	}

	public String getHaveCustTag() {
		return haveCustTag;
	}

	public void setHaveCustTag(String haveCustTag) {
		this.haveCustTag = haveCustTag;
	}

}
