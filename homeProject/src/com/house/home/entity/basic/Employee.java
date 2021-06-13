package com.house.home.entity.basic;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;
/**
 * 员工信息
 */
@Entity
@Table(name = "tEmployee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Number",nullable = false)
	private String number;
	@Column(name = "RefCode")
	private String refCode;
	@Column(name = "Icnum")
	private String icnum;
	@Column(name = "NameEng")
	private String nameEng;
	@Column(name = "NameChi")
	private String nameChi;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "Department1")
	private String department1;
	@Column(name = "Department2")
	private String department2;
	@Column(name = "Department3")
	private String department3;
	@Column(name = "Department")
	private String department;
	@Column(name = "BasicWage")
	private Double basicWage;
	@Column(name = "Position")
	private String position;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "JoinDate")
	private Date joinDate;
	@Column(name = "LeaveDate")
	private Date leaveDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "IsLead")
	private String isLead;
	@Column(name = "LeadLevel")
	private String leadLevel;
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
	@Column(name = "Idnum")
	private String idnum;
	@Column(name = "BirtPlace")
	private String birtPlace;
	@Column(name="SecondPosition")
	private String secondPosition;
	@Column(name="ChgRemarks")
	private String chgRemarks;
	@Column(name="ConnMan")
	private String connMan;
	@Column(name="ConnPhone")
	private String connPhone;
	@Column(name="DocumentNo")
	private String documentNo;
	@Column(name="GraduationDate")
	private Date graduationDate;
	@Column(name="PosiChgDate")
	private Date posiChgDate;
	@Column(name="CarNo")
    private String carNo;
	@Column(name="RegularDate")
	private Date regularDate;
	@Column(name="ApplicantPK")
	private Integer applicantPK;
	@Column(name="IsPreTrain")
	private String isPreTrain;
	@Column(name="IsPreTrainPass")
	private String isPreTrainPass;
	@Column(name="CourseCode")
	private String courseCode;
	@Column(name="ManagerRegularDate")
	private Date managerRegularDate;
	@Column(name="OldDeptDate")
	private Date oldDeptDate;
	@Column(name="PerfBelongMode")
	private String perfBelongMode;
	@Column(name="OldDept")
	private String oldDept;
	@Column(name="PersonChgDate")
	private Date personChgDate;
	@Column(name="IdValidDate")
	private Date idValidDate;
	@Column(name="PrjLevel")//无用
	private String prjLevel;
	@Column(name="IsSupvr")//无用
	private String isSupvr;
	@Column(name="IsSchemeDesigner")
	private String isSchemeDesigner;
	@Column(name="MarryStatus")
    private String marryStatus;
	@Column(name="ReTurnDate")
    private Date reTurnDate;
	@Column(name = "Edu")
	private String edu;
	@Column(name = "School")
	private String school;
	@Column(name = "SchDept")
	private String schDept;
	@Column(name = "Address")
	private String address;
	@Column(name = "Birth")
	private Date birth;
	@Column(name="ConBeginDate")
	private Date conBeginDate;
	@Column(name="ConEndDate")
	private Date conEndDate;
	@Column(name="ConSignCmp")
	private String conSignCmp;//合同签约公司
	@Column(name="PerSocialInsur")
	private Double perSocialInsur;
	@Column(name="ComSocialInsur")
	private Double comSocialInsur;
	@Column(name="Type")
	private String type;
	@Column(name="InsurSignCmp")
	private String insurSignCmp;
    @Column(name = "CardID")
    private String cardId;
    @Column(name = "Bank")
    private String bank;

    @Transient
	private String czybh;
	@Transient
	private String code;//任务类型编号
	@Transient
	private String projectMan;//项目经理编号
	@Transient
	private String isSupvrDescr;
	@Transient
	private String prjLevelDescr;
	@Transient
	private String sceneDesiCustCount;//现场设计师在建套数计算 
	@Transient
	private String dep1Type;//一级部门depType
	@Transient
	private String teamCode;
	@Transient
	private String note;
	@Transient
	private String url;
	@Transient
	private String positionDescr;
	@Transient
	private String secondPositionDescr;
	@Transient
	private String validIDnum;
	@Transient
	private Date idNumExpired;
	@Transient 
	private Date joinDateFrom;
	@Transient
	private Date joinDateTo;
	@Transient
	private Date leaveDateFrom;
	@Transient
	private Date leaveDateTo;
	@Transient
	private String workingAge;
	@Transient
	private String seniority;
	@Transient
	private String oldDepartment1;
	@Transient
	private String depType;
	@Transient
	private String nums;
	@Transient
	private String empExpDetailJson;
	@Transient
	private String empCertification;
	@Transient 
	private String photoName;
	@Transient 
	private String validVP;
	@Transient
	private String imgFlag;
	/** 一级部门名称 */
	@Transient
	private String department1Descr;
	/** 二级部门名称 */
	@Transient
	private String department2Descr;
	/** 三级部门名称 */
	@Transient
	private String department3Descr;
	/** 职位名称*/
	@Transient
	private String positionName;
	@Transient
	private String positionType ; 
	@Transient
	private String secondPositionType ;
	@Transient
	private String prjDepartment2; //工程部
	@Transient
	private String prjManCustCount;//项目经理排单显示 
	@Transient
	private String onlyPrjMan;
	@Transient
	private String oldDepartment;
	@Transient
	private String departmentDescr; 
	@Transient
	private String viewType; //显示类型
	
	@Transient
	private String taskKey;
	@Transient
	private String wfProcNo;
	@Transient
	private String custCode;
	@Transient
	private String role;
	@Transient
	private String empLevel;
	@Transient
	private String department2Code;
	@Transient
	private String department1Code;
	@Transient 
	private String startMan;
	@Transient
	private String empAuthority;
	@Transient
	private String statistcsMethod; //统计方式
	@Transient
	private String designMan; //设计师（设计客户来源分析）
	@Transient
	private String isEmpForPerf;//是否要业绩相关人员 
	@Transient
	private String isManager;//是否主管
	@Transient
	private String numbers;
	@Transient
	private Double advanceAmount;
	@Transient
	private String isStakeholder; //楼盘干系人
	@Transient
	private String cmpDescr;
	@Transient
	private String cmpCode;
	@Transient
	private String isAddSalary; //是否添加到薪酬人员
	/**
	 * 可查看的员工类型
	 * 默认为空时查看全部（外部人员与非外部人员）
	 * "0" -> 只能查看非外部人员
	 * "1" -> 只能查看外部人员
	 */
	@Transient
	private String viewEmpType;
	@Transient
	private String descr;
	
	/**
	 * 是否可以查看所有外部人员
	 * 外部人员管理模块控制查看权限使用
	 */
	@Transient
	private boolean viewAllOutEmps;
	
	@Transient
	private String description;
	@Transient
	private Integer mon; // 提成月份
	@Transient
	private Integer beginMon; // 提成开始月份
	@Transient
	private Integer endMon; // 提成结束月份
	@Transient
	private String showNoCommiEmp;
	
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCmpCode() {
		return cmpCode;
	}

	public void setCmpCode(String cmpCode) {
		this.cmpCode = cmpCode;
	}

	public String getCmpDescr() {
		return cmpDescr;
	}

	public void setCmpDescr(String cmpDescr) {
		this.cmpDescr = cmpDescr;
	}

	public Double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getInsurSignCmp() {
		return insurSignCmp;
	}

	public void setInsurSignCmp(String insurSignCmp) {
		this.insurSignCmp = insurSignCmp;
	}

	public String getEmpAuthority() {
		return empAuthority;
	}

	public void setEmpAuthority(String empAuthority) {
		this.empAuthority = empAuthority;
	}

	public String getStartMan() {
		return startMan;
	}

	public void setStartMan(String startMan) {
		this.startMan = startMan;
	}

	public String getDepartment1Code() {
		return department1Code;
	}

	public void setDepartment1Code(String department1Code) {
		this.department1Code = department1Code;
	}

	public String getDepartment2Code() {
		return department2Code;
	}

	public void setDepartment2Code(String department2Code) {
		this.department2Code = department2Code;
	}

	public String getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(String empLevel) {
		this.empLevel = empLevel;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getWfProcNo() {
		return wfProcNo;
	}

	public void setWfProcNo(String wfProcNo) {
		this.wfProcNo = wfProcNo;
	}

	public String getOnlyPrjMan() {
		return onlyPrjMan;
	}

	public void setOnlyPrjMan(String onlyPrjMan) {
		this.onlyPrjMan = onlyPrjMan;
	}

    public String getEmpExpDetailXml() {
    	String xml = XmlConverUtil.jsonToXmlNoHead(empExpDetailJson);
    	return xml;
	}
    
	public String getEmpExpDetailJson() {
        return empExpDetailJson;
    }

    public void setEmpExpDetailJson(String empExpDetailJson) {
		this.empExpDetailJson = empExpDetailJson;
	}
	
	public String getEmpCertificationXml() {
		String xml = XmlConverUtil.jsonToXmlNoHead(empCertification);
		return xml;
	}

	public String getEmpCertification() {
        return empCertification;
    }

    public void setEmpCertification(String empCertification) {
		this.empCertification = empCertification;
	}

	public String getNumber() {
		return this.number;
	}

	

	public String getIsSupvrDescr() {
		return isSupvrDescr;
	}

	public void setIsSupvrDescr(String isSupvrDescr) {
		this.isSupvrDescr = isSupvrDescr;
	}

	public String getPrjLevelDescr() {
		return prjLevelDescr;
	}

	public void setPrjLevelDescr(String prjLevelDescr) {
		this.prjLevelDescr = prjLevelDescr;
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

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRefCode() {
		return this.refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getIcnum() {
		return this.icnum;
	}

	public void setIcnum(String icnum) {
		this.icnum = icnum;
	}

	public String getNameEng() {
		return this.nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public String getNameChi() {
		return this.nameChi;
	}

	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment1() {
		return this.department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return this.department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getDepartment3() {
		return this.department3;
	}

	public void setDepartment3(String department3) {
		this.department3 = department3;
	}

	public Double getBasicWage() {
		return this.basicWage;
	}

	public void setBasicWage(Double basicWage) {
		this.basicWage = basicWage;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsLead() {
		return this.isLead;
	}

	public void setIsLead(String isLead) {
		this.isLead = isLead;
	}

	public String getLeadLevel() {
		return this.leadLevel;
	}

	public void setLeadLevel(String leadLevel) {
		this.leadLevel = leadLevel;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return this.expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getActionLog() {
		return this.actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public String getIdnum() {
		return this.idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getEdu() {
		return this.edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchDept() {
		return this.schDept;
	}

	public void setSchDept(String schDept) {
		this.schDept = schDept;
	}

	public String getBirtPlace() {
		return this.birtPlace;
	}

	public void setBirtPlace(String birtPlace) {
		this.birtPlace = birtPlace;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getSceneDesiCustCount() {
		return sceneDesiCustCount;
	}

	public void setSceneDesiCustCount(String sceneDesiCustCount) {
		this.sceneDesiCustCount = sceneDesiCustCount;
	}

	public String getDep1Type() {
		return dep1Type;
	}

	public void setDep1Type(String dep1Type) {
		this.dep1Type = dep1Type;
	}

	public String getDepartment1Descr() {
		return department1Descr;
	}

	public void setDepartment1Descr(String department1Descr) {
		this.department1Descr = department1Descr;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Date getRegularDate() {
		return regularDate;
	}

	public void setRegularDate(Date regularDate) {
		this.regularDate = regularDate;
	}


	public Integer getApplicantPK() {
		return applicantPK;
	}

	public void setApplicantPK(Integer applicantPK) {
		this.applicantPK = applicantPK;
	}

	public String getIsPreTrain() {
		return isPreTrain;
	}

	public void setIsPreTrain(String isPreTrain) {
		this.isPreTrain = isPreTrain;
	}

	public String getIsPreTrainPass() {
		return isPreTrainPass;
	}

	public void setIsPreTrainPass(String isPreTrainPass) {
		this.isPreTrainPass = isPreTrainPass;
	}

	public Date getManagerRegularDate() {
		return managerRegularDate;
	}

	public void setManagerRegularDate(Date managerRegularDate) {
		this.managerRegularDate = managerRegularDate;
	}



	public String getPerfBelongMode() {
		return perfBelongMode;
	}

	public void setPerfBelongMode(String perfBelongMode) {
		this.perfBelongMode = perfBelongMode;
	}

	public String getOldDept() {
		return oldDept;
	}

	public void setOldDept(String oldDept) {
		this.oldDept = oldDept;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}


	public Date getPosiChgDate() {
		return posiChgDate;
	}

	public void setPosiChgDate(Date posiChgDate) {
		this.posiChgDate = posiChgDate;
	}

	public String getSecondPosition() {
		return secondPosition;
	}

	public void setSecondPosition(String secondPosition) {
		this.secondPosition = secondPosition;
	}

	public String getChgRemarks() {
		return chgRemarks;
	}

	public void setChgRemarks(String chgRemarks) {
		this.chgRemarks = chgRemarks;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public Date getIdValidDate() {
		return idValidDate;
	}

	public void setIdValidDate(Date idValidDate) {
		this.idValidDate = idValidDate;
	}

	public String getConnMan() {
		return connMan;
	}

	public void setConnMan(String connMan) {
		this.connMan = connMan;
	}

	public String getConnPhone() {
		return connPhone;
	}

	public void setConnPhone(String connPhone) {
		this.connPhone = connPhone;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public Date getPersonChgDate() {
		return personChgDate;
	}

	public void setPersonChgDate(Date personChgDate) {
		this.personChgDate = personChgDate;
	}

	public String getIsSchemeDesigner() {
		return isSchemeDesigner;
	}

	public void setIsSchemeDesigner(String isSchemeDesigner) {
		this.isSchemeDesigner = isSchemeDesigner;
	}

	public Date getReTurnDate() {
		return reTurnDate;
	}

	public void setReTurnDate(Date reTurnDate) {
		this.reTurnDate = reTurnDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOldDeptDate() {
		return oldDeptDate;
	}

	public void setOldDeptDate(Date oldDeptDate) {
		this.oldDeptDate = oldDeptDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPositionDescr() {
		return positionDescr;
	}

	public void setPositionDescr(String positionDescr) {
		this.positionDescr = positionDescr;
	}

	public String getSecondPositionDescr() {
		return secondPositionDescr;
	}

	public void setSecondPositionDescr(String secondPositionDescr) {
		this.secondPositionDescr = secondPositionDescr;
	}

	public String getValidIDnum() {
		return validIDnum;
	}

	public void setValidIDnum(String validIDnum) {
		this.validIDnum = validIDnum;
	}

	public Date getIdNumExpired() {
		return idNumExpired;
	}

	public void setIdNumExpired(Date idNumExpired) {
		this.idNumExpired = idNumExpired;
	}

	public Date getJoinDateFrom() {
		return joinDateFrom;
	}

	public void setJoinDateFrom(Date joinDateFrom) {
		this.joinDateFrom = joinDateFrom;
	}

	public Date getLeaveDateFrom() {
		return leaveDateFrom;
	}

	public void setLeaveDateFrom(Date leaveDateFrom) {
		this.leaveDateFrom = leaveDateFrom;
	}

	public String getWorkingAge() {
		return workingAge;
	}

	public void setWorkingAge(String workingAge) {
		this.workingAge = workingAge;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	public String getOldDepartment1() {
		return oldDepartment1;
	}

	public void setOldDepartment1(String oldDepartment1) {
		this.oldDepartment1 = oldDepartment1;
	}

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public Date getConBeginDate() {
		return conBeginDate;
	}

	public void setConBeginDate(Date conBeginDate) {
		this.conBeginDate = conBeginDate;
	}

	public Date getConEndDate() {
		return conEndDate;
	}

	public void setConEndDate(Date conEndDate) {
		this.conEndDate = conEndDate;
	}

	public String getConSignCmp() {
		return conSignCmp;
	}

	public void setConSignCmp(String conSignCmp) {
		this.conSignCmp = conSignCmp;
	}

	public String getValidVP() {
		return validVP;
	}

	public void setValidVP(String validVP) {
		this.validVP = validVP;
	}

	public String getImgFlag() {
		return imgFlag;
	}

	public void setImgFlag(String imgFlag) {
		this.imgFlag = imgFlag;
	}

	public String getDepartment3Descr() {
		return department3Descr;
	}

	public void setDepartment3Descr(String department3Descr) {
		this.department3Descr = department3Descr;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getJoinDateTo() {
		return joinDateTo;
	}

	public void setJoinDateTo(Date joinDateTo) {
		this.joinDateTo = joinDateTo;
	}

	public Date getLeaveDateTo() {
		return leaveDateTo;
	}

	public void setLeaveDateTo(Date leaveDateTo) {
		this.leaveDateTo = leaveDateTo;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getSecondPositionType() {
		return secondPositionType;
	}

	public void setSecondPositionType(String secondPositionType) {
		this.secondPositionType = secondPositionType;
	}

	public String getPrjDepartment2() {
		return prjDepartment2;
	}

	public void setPrjDepartment2(String prjDepartment2) {
		this.prjDepartment2 = prjDepartment2;
	}

	public String getPrjManCustCount() {
		return prjManCustCount;
	}

	public void setPrjManCustCount(String prjManCustCount) {
		this.prjManCustCount = prjManCustCount;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getOldDepartment() {
		return oldDepartment;
	}

	public void setOldDepartment(String oldDepartment) {
		this.oldDepartment = oldDepartment;
	}

	public String getDepartmentDescr() {
		return departmentDescr;
	}

	public void setDepartmentDescr(String departmentDescr) {
		this.departmentDescr = departmentDescr;
	}
	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	
	public Double getPerSocialInsur() {
		return perSocialInsur;
	}

	public void setPerSocialInsur(Double perSocialInsur) {
		this.perSocialInsur = perSocialInsur;
	}

	public Double getComSocialInsur() {
		return comSocialInsur;
	}

	public void setComSocialInsur(Double comSocialInsur) {
		this.comSocialInsur = comSocialInsur;
	}

	public String getIsEmpForPerf() {
		return isEmpForPerf;
	}

	public void setIsEmpForPerf(String isEmpForPerf) {
		this.isEmpForPerf = isEmpForPerf;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getIsStakeholder() {
		return isStakeholder;
	}

	public void setIsStakeholder(String isStakeholder) {
		this.isStakeholder = isStakeholder;
	}
	
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public boolean isViewAllOutEmps() {
        return viewAllOutEmps;
    }

    public void setViewAllOutEmps(boolean viewAllOutEmps) {
        this.viewAllOutEmps = viewAllOutEmps;
    }

    public String getViewEmpType() {
        return viewEmpType;
    }

    public void setViewEmpType(String viewEmpType) {
        this.viewEmpType = viewEmpType;
    }

	public String getIsAddSalary() {
		return isAddSalary;
	}

	public void setIsAddSalary(String isAddSalary) {
		this.isAddSalary = isAddSalary;
	}
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public Integer getBeginMon() {
		return beginMon;
	}

	public void setBeginMon(Integer beginMon) {
		this.beginMon = beginMon;
	}

	public Integer getEndMon() {
		return endMon;
	}

	public void setEndMon(Integer endMon) {
		this.endMon = endMon;
	}

	public String getShowNoCommiEmp() {
		return showNoCommiEmp;
	}

	public void setShowNoCommiEmp(String showNoCommiEmp) {
		this.showNoCommiEmp = showNoCommiEmp;
	}
    
}


