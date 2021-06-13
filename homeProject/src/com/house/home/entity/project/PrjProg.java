package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * PrjProg信息
 */
@Entity
@Table(name = "tPrjProg")
public class PrjProg extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "PlanBegin")
	private Date planBegin;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "PlanEnd")
	private Date planEnd;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name ="ConfirmDate")
	private Date confirmDate;
	@Column(name ="ConfirmCZY")
	private String confirmCZY;
	@Column(name ="ConfirmDesc")
	private String confirmDesc;
	@Column(name="prjLevel")
	private String prjLevel;
	@Column(name="adjPlanEnd")
	private Date adjPlanEnd;
	
	@Transient
	private String projectMan;
	@Transient
	private Integer area;
	@Transient
	private String descr;
	@Transient
	private String custType;
	@Transient
	private String layout;
	@Transient
	private String address;
	@Transient
	private Integer constructDay;
	@Transient
	private String prjProgTempNo;
	@Transient
	private String prjProgTempNoDescr;
	@Transient
	private String detailJson;
	@Transient
	private String department2;
	@Transient
	private Date photoDate;
	@Transient
	private String prjItemDescr;
	@Transient
	private String confirmCZYDescr;
	@Transient
	private String notConfirm;//显示未验收
	@Transient
	private String notCompleted;//显示未竣工
	@Transient
	private String notSign;//显示未签约班组
	@Transient 
	private String department1;
	@Transient
	private String readonly;
	@Transient
	private String code;
	@Transient
	private String photoPath;
	@Transient
	private Integer postPoneDate;
	@Transient
	private Integer postPoneEndDate;
	@Transient
	private Date photoLastUpDate;
	@Transient
	private String prjDescr;
	@Transient
	private String photoName;
	@Transient
	private String xjPhotoPath;
	@Transient 
	private String ysPhotoPath;
	@Transient 
	private String allNo;
	@Transient
	private String nowNo;
	@Transient 
	private String allNo1;
	@Transient
	private String nowNo1;
	@Transient
	private String hasPrjItem;
	@Transient
	private String actCode;//编辑时操作代码  1：新增、复制，2：编辑
	@Transient
	private String isDelay;
	@Transient 
	private String logDescr;
	@Transient
	private String prjJobType;
	@Transient
	private String buildStatus;
	@Transient
	private String constructType;
	@Transient
	private String isPrjConfirm;
	@Transient
	private String isPushCust;
	@Transient
	private Date confirmBegin;
	@Transient
	private String prjProgJson;
	
	@Transient
	private String progStage;
	
	public String getIsPushCust() {
		return isPushCust;
	}

	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}

	public String getIsPrjConfirm() {
		return isPrjConfirm;
	}

	public void setIsPrjConfirm(String isPrjConfirm) {
		this.isPrjConfirm = isPrjConfirm;
	}

	public String getConstructType() {
		return constructType;
	}

	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getPrjJobType() {
		return prjJobType;
	}

	public void setPrjJobType(String prjJobType) {
		this.prjJobType = prjJobType;
	}

	public Date getAdjPlanEnd() {
		return adjPlanEnd;
	}

	public void setAdjPlanEnd(Date adjPlanEnd) {
		this.adjPlanEnd = adjPlanEnd;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getLogDescr() {
		return logDescr;
	}

	public void setLogDescr(String logDescr) {
		this.logDescr = logDescr;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getHasPrjItem() {
		return hasPrjItem;
	}

	public void setHasPrjItem(String hasPrjItem) {
		this.hasPrjItem = hasPrjItem;
	}

	public String getAllNo1() {
		return allNo1;
	}

	public void setAllNo1(String allNo1) {
		this.allNo1 = allNo1;
	}

	public String getNowNo1() {
		return nowNo1;
	}

	public void setNowNo1(String nowNo1) {
		this.nowNo1 = nowNo1;
	}

	public String getAllNo() {
		return allNo;
	}

	public void setAllNo(String allNo) {
		this.allNo = allNo;
	}

	public String getNowNo() {
		return nowNo;
	}

	public void setNowNo(String nowNo) {
		this.nowNo = nowNo;
	}

	public String getXjPhotoPath() {
		return xjPhotoPath;
	}

	public void setXjPhotoPath(String xjPhotoPath) {
		this.xjPhotoPath = xjPhotoPath;
	}

	public String getYsPhotoPath() {
		return ysPhotoPath;
	}

	public void setYsPhotoPath(String ysPhotoPath) {
		this.ysPhotoPath = ysPhotoPath;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPrjDescr() {
		return prjDescr;
	}

	public void setPrjDescr(String prjDescr) {
		this.prjDescr = prjDescr;
	}

	public Date getPhotoLastUpDate() {
		return photoLastUpDate;
	}

	public void setPhotoLastUpDate(Date photoLastUpDate) {
		this.photoLastUpDate = photoLastUpDate;
	}

	public Integer getPostPoneDate() {
		return postPoneDate;
	}

	public void setPostPoneDate(Integer postPoneDate) {
		this.postPoneDate = postPoneDate;
	}

	public Integer getPostPoneEndDate() {
		return postPoneEndDate;
	}

	public void setPostPoneEndDate(Integer postPoneEndDate) {
		this.postPoneEndDate = postPoneEndDate;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getNotConfirm() {
		return notConfirm;
	}

	public void setNotConfirm(String notConfirm) {
		this.notConfirm = notConfirm;
	}

	public String getNotCompleted() {
		return notCompleted;
	}

	public void setNotCompleted(String notCompleted) {
		this.notCompleted = notCompleted;
	}

	public String getNotSign() {
		return notSign;
	}

	public void setNotSign(String notSign) {
		this.notSign = notSign;
	}

	public String getConfirmCZYDescr() {
		return confirmCZYDescr;
	}

	public void setConfirmCZYDescr(String confirmCZYDescr) {
		this.confirmCZYDescr = confirmCZYDescr;
	}

	public String getPrjItemDescr() {
		return prjItemDescr;
	}

	public void setPrjItemDescr(String prjItemDescr) {
		this.prjItemDescr = prjItemDescr;
	}

	public Date getPhotoDate() {
		return photoDate;
	}

	public void setPhotoDate(Date photoDate) {
		this.photoDate = photoDate;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getDetailJson() {
		return detailJson;
	}

	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	
	public String getPrjProgXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public Integer getConstructDay() {
		return constructDay;
	}

	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}

	public String getPrjProgTempNo() {
		return prjProgTempNo;
	}

	public void setPrjProgTempNo(String prjProgTempNo) {
		this.prjProgTempNo = prjProgTempNo;
	}

	public String getPrjProgTempNoDescr() {
		return prjProgTempNoDescr;
	}

	public void setPrjProgTempNoDescr(String prjProgTempNoDescr) {
		this.prjProgTempNoDescr = prjProgTempNoDescr;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public Integer getPk() {
		return this.pk;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	public String getCustCode() {
		return this.custCode;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
	}
	public void setPlanBegin(Date planBegin) {
		this.planBegin = planBegin;
	}
	
	public Date getPlanBegin() {
		return this.planBegin;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	
	public Date getPlanEnd() {
		return this.planEnd;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
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

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmCZY() {
		return confirmCZY;
	}

	public void setConfirmCZY(String confirmCZY) {
		this.confirmCZY = confirmCZY;
	}

	public String getConfirmDesc() {
		return confirmDesc;
	}

	public void setConfirmDesc(String confirmDesc) {
		this.confirmDesc = confirmDesc;
	}

	public String getPrjLevel() {
		return prjLevel;
	}

	public void setPrjLevel(String prjLevel) {
		this.prjLevel = prjLevel;
	}

	public Date getConfirmBegin() {
		return confirmBegin;
	}

	public void setConfirmBegin(Date confirmBegin) {
		this.confirmBegin = confirmBegin;
	}

	public String getPrjProgJson() {
		String xml = XmlConverUtil.jsonToXmlNoHead(prjProgJson);
		return xml;
	}

	public void setPrjProgJson(String prjProgJson) {
		this.prjProgJson = prjProgJson;
	}

    public String getProgStage() {
        return progStage;
    }

    public void setProgStage(String progStage) {
        this.progStage = progStage;
    }
	
}
