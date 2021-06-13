package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * PrjJob信息
 */
@Entity
@Table(name = "tPrjJob")
public class PrjJob extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "Status")
	private String status;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "SupplCode")
	private String supplCode;
	@Column(name = "ItemType1")
	private String itemType1;
	@Column(name = "JobType")
	private String jobType;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "DealCzy")
	private String dealCzy;
	@Column(name = "PlanDate")
	private Date planDate;
	@Column(name = "DealDate")
	private Date dealDate;
	@Column(name = "DealRemark")
	private String dealRemark;
	@Column(name = "EndCode")
	private String endCode;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "address")
	private String address;
	@Column(name = "WarBrand")
	private String warBrand;
	@Column(name = "CupBrand")
	private String cupBrand;
	@Column(name = "ErrPosi")
	private String errPosi;
	@Transient
	private String isNeedSuppl;//是否需要供应商
	@Transient
	private String isDispCustPhn;
	@Transient
	private String photoString;

    @Transient
    private String fromInfoAdd; //use in app add prjJob    log:add by zzr 
    @Transient
    private Integer infoPk;//use in app add prjJob    log:add by zzr 
    
    //  集成测量分析   add by hc begin
    @Transient
	private Date appDateform;  //申请日期 从
    @Transient
   	private Date appDateto;  //申请日期 至
    @Transient
    private String sType; //集成测量分析  传入的统计方式类型   1.申报明细 2.未申报明细  3.按项目经理  4.按工程部  5.按集成设计师 6.按集成部
    @Transient
    private String ProjectMan;  // 项目经理
    @Transient
    private String Department2; //工程部
    @Transient
    private String JCDesigner;// 任务处理人(为了兼容旧版本不进行变量名修改。old:集成设计师,new:任务处理人)
    @Transient
    private String Department2jc;// 集成设计师部门
    @Transient
    private String custType; // 客户类型
    @Transient
    private String statistcsMethod; //统计方式
    @Transient
    private String appCzyDescr; //申请人
    @Transient
    private String dealCzyDescr; //处理人
    @Transient
    private String warBrandDescr;
    @Transient
    private String cupBrandDescr;
    @Transient
    private String prjJobNo;
    @Transient
	private String photoPath;
    @Transient
  	private String phone;//申请人电话
    @Transient
  	private String custName;//客户名称
    @Transient
  	private String custPhone;//客户电话
    @Transient
  	private String supplDescr;//供应商
    @Transient
	private String endCode0;
	
    //   集成测量分析   add by hc   end
    
    @Transient
    private String dealOverTime; //只显示处理未及时
    @Transient
    private Date confirmDateLate;//开工时间晚于
    
    @Transient
    private String intDesigner;//集成设计师
    @Transient
    private String cupDesigner;//橱柜设计师
    
    @Transient
    private String StatusDescr;
    @Transient
    private String EndDescr;
    @Transient
    private String JobTypeDescr;
    @Transient
    private String DealCZYDescr;
    @Transient
    private String isConfirmed;//是否验收
    @Transient
    private String prjItemDescr;
    @Transient
    private String custStage; //客户阶段（任务管理用）
    @Transient
    private String isNotTr; //包含待触发
    @Transient
    private String prjJobJson; 
    
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNo() {
		return this.no;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	
	public String getItemType1() {
		return this.itemType1;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getJobType() {
		return this.jobType;
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
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getRemarks() {
		return this.remarks;
	}
	public void setDealCzy(String dealCzy) {
		this.dealCzy = dealCzy;
	}
	
	public String getDealCzy() {
		return this.dealCzy;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	public Date getPlanDate() {
		return this.planDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
	public Date getDealDate() {
		return this.dealDate;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
	
	public String getDealRemark() {
		return this.dealRemark;
	}
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
	
	public String getEndCode() {
		return this.endCode;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhotoString() {
		return photoString;
	}

	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}

	public String getWarBrand() {
		return warBrand;
	}

	public void setWarBrand(String warBrand) {
		this.warBrand = warBrand;
	}

	public String getCupBrand() {
		return cupBrand;
	}

	public void setCupBrand(String cupBrand) {
		this.cupBrand = cupBrand;
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

	public Date getAppDateform() {
		return appDateform;
	}

	public void setAppDateform(Date appDateform) {
		this.appDateform = appDateform;
	}

	public Date getAppDateto() {
		return appDateto;
	}

	public void setAppDateto(Date appDateto) {
		this.appDateto = appDateto;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getProjectMan() {
		return ProjectMan;
	}

	public void setProjectMan(String projectMan) {
		ProjectMan = projectMan;
	}

	public String getDepartment2() {
		return Department2;
	}

	public void setDepartment2(String department2) {
		Department2 = department2;
	}

	public String getJCDesigner() {
		return JCDesigner;
	}

	public void setJCDesigner(String jCDesigner) {
		JCDesigner = jCDesigner;
	}

	public String getDepartment2jc() {
		return Department2jc;
	}

	public void setDepartment2jc(String department2jc) {
		Department2jc = department2jc;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getAppCzyDescr() {
		return appCzyDescr;
	}

	public void setAppCzyDescr(String appCzyDescr) {
		this.appCzyDescr = appCzyDescr;
	}

	public String getDealCzyDescr() {
		return dealCzyDescr;
	}

	public void setDealCzyDescr(String dealCzyDescr) {
		this.dealCzyDescr = dealCzyDescr;
	}

	public String getWarBrandDescr() {
		return warBrandDescr;
	}

	public void setWarBrandDescr(String warBrandDescr) {
		this.warBrandDescr = warBrandDescr;
	}

	public String getCupBrandDescr() {
		return cupBrandDescr;
	}

	public void setCupBrandDescr(String cupBrandDescr) {
		this.cupBrandDescr = cupBrandDescr;
	}

	public String getPrjJobNo() {
		return prjJobNo;
	}

	public void setPrjJobNo(String prjJobNo) {
		this.prjJobNo = prjJobNo;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getSupplCode() {
		return supplCode;
	}

	public void setSupplCode(String supplCode) {
		this.supplCode = supplCode;
	}

	public String getSupplDescr() {
		return supplDescr;
	}

	public void setSupplDescr(String supplDescr) {
		this.supplDescr = supplDescr;
	}

	public String getIsNeedSuppl() {
		return isNeedSuppl;
	}

	public void setIsNeedSuppl(String isNeedSuppl) {
		this.isNeedSuppl = isNeedSuppl;
	}

	public String getIsDispCustPhn() {
		return isDispCustPhn;
	}

	public void setIsDispCustPhn(String isDispCustPhn) {
		this.isDispCustPhn = isDispCustPhn;
	}

	public String getEndCode0() {
		return endCode0;
	}

	public void setEndCode0(String endCode0) {
		this.endCode0 = endCode0;
	}

	public String getDealOverTime() {
		return dealOverTime;
	}

	public void setDealOverTime(String dealOverTime) {
		this.dealOverTime = dealOverTime;
	}

	public Date getConfirmDateLate() {
		return confirmDateLate;
	}

	public void setConfirmDateLate(Date confirmDateLate) {
		this.confirmDateLate = confirmDateLate;
	}

	public String getIntDesigner() {
		return intDesigner;
	}

	public void setIntDesigner(String intDesigner) {
		this.intDesigner = intDesigner;
	}

	public String getCupDesigner() {
		return cupDesigner;
	}

	public void setCupDesigner(String cupDesigner) {
		this.cupDesigner = cupDesigner;
	}

	public String getStatusDescr() {
		return StatusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		StatusDescr = statusDescr;
	}

	public String getEndDescr() {
		return EndDescr;
	}

	public void setEndDescr(String endDescr) {
		EndDescr = endDescr;
	}

	public String getJobTypeDescr() {
		return JobTypeDescr;
	}

	public void setJobTypeDescr(String jobTypeDescr) {
		JobTypeDescr = jobTypeDescr;
	}

	public String getDealCZYDescr() {
		return DealCZYDescr;
	}

	public void setDealCZYDescr(String dealCZYDescr) {
		DealCZYDescr = dealCZYDescr;
	}

	public String getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public String getPrjItemDescr() {
		return prjItemDescr;
	}

	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}

	public String getCustStage() {
		return custStage;
	}

	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}

	public String getIsNotTr() {
		return isNotTr;
	}

	public void setIsNotTr(String isNotTr) {
		this.isNotTr = isNotTr;
	}

	public String getErrPosi() {
		return errPosi;
	}

	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}

	public String getPrjJobJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(prjJobJson);
    	return xml;
	}

	public void setPrjJobJson(String prjJobJson) {
		this.prjJobJson = prjJobJson;
	}
	
	
}
