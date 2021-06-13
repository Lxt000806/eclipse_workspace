package com.house.home.entity.project;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * worker信息
 */
@Entity
@Table(name = "tWorker")
public class Worker extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Code")
	private String code;
	@Column(name = "NameChi")
	private String nameChi;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "Idnum")
	private String idnum;
	@Column(name = "CardId")
	private String cardId;
	@Column(name = "CardId2")
	private String cardId2;
	@Column(name = "WorkType12")
	private String workType12;
	@Column(name = "IntroduceEmp")
	private String introduceEmp;
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
	@Column(name="isSign")
	private String isSign;
	@Column(name="SignDate")
	private Date signDate;
	@Column(name="level")
	private String level;
	@Column(name="MM")
	private String mm;
	@Column(name="EmpCode")
	private String EmpCode;
	@Column(name="QualityFee")
	private BigDecimal qualityFee;/*质保金余额*/
	@Column(name="LiveRegion")
	private String  liveRegion;/*一级居住区域*/
	@Column(name="LiveRegion2")
	private String  liveRegion2;
	@Column(name="Num")
	private Double num;/*班组人数*/
	@Column(name="Department1")
	private String  department1;
	@Column(name="SpcBuilder")
	private String  spcBuilder;
	@Column(name="IsAutoArrange")
	private String  isAutoArrange;/*是否自动安排*/
	@Column(name="BelongRegion")
	private String  belongRegion;
	@Column(name="IsLeave")
	private String isLeave;
	@Column(name="Efficiency")
	private BigDecimal efficiency;/*工作效率比例*/
	@Column(name="WorkType12Dept")
	private String workType12Dept;/*所属分组*/
	@Column(name="PrjRegionCode")
	private String prjRegionCode;/*工程大区编号*/
	@Column(name="Address")
	private String address;
	@Column(name="Vehicle")
	private String vehicle;/*交通工具*/
	@Column(name="RcvPrjType")
	private String rcvPrjType;/*承接工地类型*/
	@Column(name="AllowItemApp")
	private String allowItemApp;
	@Column(name="leaveDate")
	private Date leaveDate;
	@Column(name="WorkerClassify")
	private String workerClassify;
	@Column(name="CardId3")
	private String cardId3;
	@Column(name="Phone3")
	private String phone3;
	@Column(name="IdNum3")
	private String idNum3;
	@Column(name="NameChi3")
	private String nameChi3;
	@Column(name="CardId4")
	private String cardId4;
	@Column(name="Phone4")
	private String phone4;
	@Column(name="IdNum4")
	private String idNum4;
	@Column(name="NameChi4")
	private String nameChi4;
	@Column(name="Department2")
	private String department2;
	@Column(name = "Bank")
	private String bank;
	@Column(name = "Bank2")
	private String bank2;
	@Column(name = "LaborCmpCode")
	private String laborCmpCode;
	@Column(name = "PersonalProfile")
	private String personalProfile;
	@Column(name = "IsRegisterMall")
	private String isRegisterMall;
	@Column(name = "AvatarPic")
	private String avatarPic;
	
    @Transient
	private Integer score;
	@Transient
	private String beforeEmpCode;/*之前是否有员工号*/
	@Transient
	private String empCodeDescr;/*员工编号描述*/
	@Transient
	private String introduceEmpDescr;/*介绍人描述*/
	@Transient
	private String isSupvr;/*是否监理*/
	@Transient
	private String prjLevel;/*监理星级*/
	@Transient
	private String region;
	@Transient
	private String region2;
	@Transient
	private String workType1;
	@Transient
	private String isLifeCost;
	@Transient
	private String onDoNum;
	@Transient
	private Date beginDate;
	@Transient
	private Date endDate;
	@Transient 
    private String czybh;
	@Transient 
    private String workerCode;
	@Transient 
    private String orderBy;
	@Transient 
    private String direction;
	
	@Transient
	private String isWithHold;
	@Transient
	private String platform; //1: 项目经理App
	@Transient
	private String custCode;
	
	@Transient
	private String workType2;
	@Transient
	private String custType;
	@Transient
	private String conRegion;
	
	@Transient
	private String appType;
	@Transient
	private String hasBuild;

	@Transient
	private String department2Descr;
	@Transient
	private String statisticalMethods; //统计方式
	@Transient
	private String workType12Strings;
	@Transient
	private String workType12Descr;
	@Transient
	private String detailJson;
	@Transient
	private String refCustCode;
	@Transient
	private String token;
	@Transient
	private String memberJson;
	@Transient
	private String description;
	@Transient
	private String memberName;
	@Transient
    private String workType12Query;
	
	public String getWorkType12Query() {
		return workType12Query;
	}
	public void setWorkType12Query(String workType12Query) {
		this.workType12Query = workType12Query;
	}
	public String getDetailJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
    	return xml;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getWorkType12Descr() {
		return workType12Descr;
	}

	public void setWorkType12Descr(String workType12Descr) {
		this.workType12Descr = workType12Descr;
	}

	public String getWorkType12Strings() {
		return workType12Strings;
	}

	public void setWorkType12Strings(String workType12Strings) {
		this.workType12Strings = workType12Strings;
	}

	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}

	public String getHasBuild() {
		return hasBuild;
	}

	public void setHasBuild(String hasBuild) {
		this.hasBuild = hasBuild;
	}

	public String getConRegion() {
		return conRegion;
	}

	public void setConRegion(String conRegion) {
		this.conRegion = conRegion;
	}

	public String getAllowItemApp() {
		return allowItemApp;
	}

	public void setAllowItemApp(String allowItemApp) {
		this.allowItemApp = allowItemApp;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getRcvPrjType() {
		return rcvPrjType;
	}

	public void setRcvPrjType(String rcvPrjType) {
		this.rcvPrjType = rcvPrjType;
	}

	public String getPrjRegionCode() {
		return prjRegionCode;
	}

	public void setPrjRegionCode(String prjRegionCode) {
		this.prjRegionCode = prjRegionCode;
	}

	public String getWorkType12Dept() {
		return workType12Dept;
	}

	public void setWorkType12Dept(String workType12Dept) {
		this.workType12Dept = workType12Dept;
	}

	public String getOnDoNum() {
		return onDoNum;
	}

	public void setOnDoNum(String onDoNum) {
		this.onDoNum = onDoNum;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion2() {
		return region2;
	}

	public void setRegion2(String region2) {
		this.region2 = region2;
	}

	public String getSpcBuilder() {
		return spcBuilder;
	}

	public void setSpcBuilder(String spcBuilder) {
		this.spcBuilder = spcBuilder;
	}

	public String getBelongRegion() {
		return belongRegion;
	}

	public void setBelongRegion(String belongRegion) {
		this.belongRegion = belongRegion;
	}
	
	public String getLiveRegion2() {
		return liveRegion2;
	}

	public void setLiveRegion2(String liveRegion2) {
		this.liveRegion2 = liveRegion2;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	
	public String getNameChi() {
		return this.nameChi;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	
	public String getIdnum() {
		return this.idnum;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getCardId() {
		return this.cardId;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	
	public String getWorkType12() {
		return this.workType12;
	}
	public void setIntroduceEmp(String introduceEmp) {
		this.introduceEmp = introduceEmp;
	}
	
	public String getIntroduceEmp() {
		return this.introduceEmp;
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

	
	
	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getEmpCode() {
		return EmpCode;
	}

	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getIsLifeCost() {
		return isLifeCost;
	}

	public void setIsLifeCost(String isLifeCost) {
		this.isLifeCost = isLifeCost;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLiveRegion() {
		return liveRegion;
	}

	public void setLiveRegion(String liveRegion) {
		this.liveRegion = liveRegion;
	}

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String getIsAutoArrange() {
		return isAutoArrange;
	}

	public void setIsAutoArrange(String isAutoArrange) {
		this.isAutoArrange = isAutoArrange;
	}

	public String getIsSupvr() {
		return isSupvr;
	}

	public void setIsSupvr(String isSupvr) {
		this.isSupvr = isSupvr;
	}

	public String getPrjLevel() {
		return prjLevel;
	}

	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}

	public BigDecimal getQualityFee() {
		return qualityFee;
	}

	public void setQualityFee(BigDecimal qualityFee) {
		this.qualityFee = qualityFee;
	}

	public BigDecimal getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(BigDecimal efficiency) {
		this.efficiency = efficiency;
	}

	public String getEmpCodeDescr() {
		return empCodeDescr;
	}

	public void setEmpCodeDescr(String empCodeDescr) {
		this.empCodeDescr = empCodeDescr;
	}

	public String getIntroduceEmpDescr() {
		return introduceEmpDescr;
	}

	public void setIntroduceEmpDescr(String introduceEmpDescr) {
		this.introduceEmpDescr = introduceEmpDescr;
	}

	public String getBeforeEmpCode() {
		return beforeEmpCode;
	}

	public void setBeforeEmpCode(String beforeEmpCode) {
		this.beforeEmpCode = beforeEmpCode;
	}

	public String getCardId2() {
		return cardId2;
	}

	public void setCardId2(String cardId2) {
		this.cardId2 = cardId2;
	}

	public String getIsWithHold() {
		return isWithHold;
	}

	public void setIsWithHold(String isWithHold) {
		this.isWithHold = isWithHold;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getWorkType2() {
		return workType2;
	}

	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getCardId3() {
		return cardId3;
	}

	public void setCardId3(String cardId3) {
		this.cardId3 = cardId3;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getIdNum3() {
		return idNum3;
	}

	public void setIdNum3(String idNum3) {
		this.idNum3 = idNum3;
	}

	public String getNameChi3() {
		return nameChi3;
	}

	public void setNameChi3(String nameChi3) {
		this.nameChi3 = nameChi3;
	}

	public String getCardId4() {
		return cardId4;
	}

	public void setCardId4(String cardId4) {
		this.cardId4 = cardId4;
	}

	public String getPhone4() {
		return phone4;
	}

	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}

	public String getIdNum4() {
		return idNum4;
	}

	public void setIdNum4(String idNum4) {
		this.idNum4 = idNum4;
	}

	public String getNameChi4() {
		return nameChi4;
	}

	public void setNameChi4(String nameChi4) {
		this.nameChi4 = nameChi4;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	
	public String getDepartment2Descr() {
		return department2Descr;
	}
	
	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}
	
	public String getStatisticalMethods() {
		return statisticalMethods;
	}

	public void setStatisticalMethods(String statisticalMethods) {
		this.statisticalMethods = statisticalMethods;
	}
	public String getRefCustCode() {
		return refCustCode;
	}
	public void setRefCustCode(String refCustCode) {
		this.refCustCode = refCustCode;
	}
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBank2() {
        return bank2;
    }
    public void setBank2(String bank2) {
        this.bank2 = bank2;
    }
    public String getLaborCmpCode() {
        return laborCmpCode;
    }
    public void setLaborCmpCode(String laborCmpCode) {
        this.laborCmpCode = laborCmpCode;
    }
    
    public String getMemberJson() {
        return memberJson;
    }
    
    public String getMemberXml() {
        return XmlConverUtil.jsonToXmlNoHead(memberJson);
    }
    
    public void setMemberJson(String memberJson) {
        this.memberJson = memberJson;
    }
	public String getPersonalProfile() {
		return personalProfile;
	}
	public void setPersonalProfile(String personalProfile) {
		this.personalProfile = personalProfile;
	}
	public String getIsRegisterMall() {
		return isRegisterMall;
	}
	public void setIsRegisterMall(String isRegisterMall) {
		this.isRegisterMall = isRegisterMall;
	}
	public String getAvatarPic() {
		return avatarPic;
	}
	public void setAvatarPic(String avatarPic) {
		this.avatarPic = avatarPic;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
}
