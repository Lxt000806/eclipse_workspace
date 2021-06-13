package com.house.home.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.house.framework.commons.orm.BaseEntity;

/**
 * PrjProgCheck信息
 */
@Entity
@Table(name = "tPrjProgCheck")
public class PrjProgCheck extends BaseEntity {

	private static final long serialVersionUID = 1L;

    	@Id
	@Column(name = "No")
	private String no;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "Date")
	private Date date;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "Address")
	private String address;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "AppCzy")
	private String appCzy;
	@Column(name = "IsModify")
	private String isModify;
	@Column(name = "ModifyTime")
	private Integer modifyTime;
	@Column(name = "ModifyComplete")
	private String modifyComplete;
	@Column(name = "CompRemark")
	private String compRemark;
	@Column(name = "CompCzy")
	private String compCzy;
	@Column(name = "CompDate")
	private Date compDate;
	@Column(name = "BuildStatus")
	private String buildStatus;
	@Column(name = "SafeProm")
	private String safeProm;
	@Column(name = "VisualProm")
	private String visualProm;
	@Column(name = "ArtProm")
	private String artProm;
	@Column(name = "errPosi")
	private String errPosi;
	@Column(name = "IsCheckDept")
	private String isCheckDept;
	@Column(name = "IsUpPrjProg")
	private String isUpPrjProg;
	@Column(name = "longitude")
	private Double longitude;
	@Column(name = "Latitude")
	private Double latitude;
	
	@Column
	private Date confirmDate;
	@Column(name = "ConfirmCzy")
	private String confirmCzy;
	
	@Column(name = "ToiletNum")
	private Integer toiletNum;
	@Column(name = "BedroomNum")
	private Integer bedroomNum;
	@Column(name = "KitchDoorType")
	private String kitchDoorType;
	@Column(name = "BalconyNum")
	private Integer balconyNum;
	@Column(name = "BalconyTitle")
	private String balconyTitle;
	@Column(name = "IsWood")
	private String isWood;
	@Column(name = "IsBuildWall")
	private String isBuildWall;
	@Column(name="IsPushCust")
	private String isPushCust;
	@Column(name="CigaNum")
	private Integer cigaNum;
	
	@Column(name = "CustScore")
	private Integer custScore;
	@Column(name = "CustRemarks" )
	private String custRemarks;
	
	@Transient
	private String photoString;
	@Transient
	private String statusZG;	
	@Transient
	private String projectMan;
	@Transient
	private String prjProgDetailXml;
	@Transient
	private String department1;
	@Transient
	private String prjDescr;
	@Transient
	private String remainModifyTime;
	@Transient
	private String allNo;
	@Transient
	private String nowNo;
	@Transient
	private String department2;
	
	// add by hc     工地巡检分析  begin -2017 11/14
	@Transient
	private String department2Check;//巡检人二级部门
	@Transient
	private String checkMan;//巡检人
	@Transient
	private Date beginDate;   //巡检日期从
	@Transient
	private Date endDate;     //巡检日期至
	@Transient
	private String sType ; //统计类型 ,不写成Type是怕以后PrjProg表加一个类型的时候会冲突   
	@Transient
	private String IsModifyComplete; //整改是否完成
	@Transient
	private String statistcsMethod; //统计方式
	// add by hc    工地巡检分析   end  - 2017/11/14
	@Transient
	private String custScoreCheck;
	
	// 是否包含位置异常记录
	@Transient
	private String includeErrPosi;
	
	
	public String getCustScoreCheck() {
		return custScoreCheck;
	}

	public void setCustScoreCheck(String custScoreCheck) {
		this.custScoreCheck = custScoreCheck;
	}

	public String getAllNo() {
		return allNo;
	}

	public Integer getCigaNum() {
		return cigaNum;
	}

	public void setCigaNum(Integer cigaNum) {
		this.cigaNum = cigaNum;
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

	public String getRemainModifyTime() {
		return remainModifyTime;
	}

	public void setRemainModifyTime(String remainModifyTime) {
		this.remainModifyTime = remainModifyTime;
	}

	public String getPrjDescr() {
		return prjDescr;
	}

	public void setPrjDescr(String prjDescr) {
		this.prjDescr = prjDescr;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getPrjProgDetailXml() {
		return prjProgDetailXml;
	}

	public void setPrjProgDetailXml(String prjProgDetailXml) {
		this.prjProgDetailXml = prjProgDetailXml;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Integer getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public Integer getBedroomNum() {
		return bedroomNum;
	}

	public void setBedroomNum(Integer bedroomNum) {
		this.bedroomNum = bedroomNum;
	}

	public String getKitchDoorType() {
		return kitchDoorType;
	}

	public void setKitchDoorType(String kitchDoorType) {
		this.kitchDoorType = kitchDoorType;
	}

	public Integer getBalconyNum() {
		return balconyNum;
	}

	public void setBalconyNum(Integer balconyNum) {
		this.balconyNum = balconyNum;
	}

	public String getBalconyTitle() {
		return balconyTitle;
	}

	public void setBalconyTitle(String balconyTitle) {
		this.balconyTitle = balconyTitle;
	}

	public String getIsWood() {
		return isWood;
	}

	public void setIsWood(String isWood) {
		this.isWood = isWood;
	}

	public String getIsBuildWall() {
		return isBuildWall;
	}

	public void setIsBuildWall(String isBuildWall) {
		this.isBuildWall = isBuildWall;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getStatusZG() {
		return statusZG;
	}

	public void setStatusZG(String statusZG) {
		this.statusZG = statusZG;
	}

	public String getErrPosi() {
		return errPosi;
	}

	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
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
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	
	public String getPrjItem() {
		return this.prjItem;
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
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
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

	public String getPhotoString() {
		return photoString;
	}

	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}

	public String getAppCzy() {
		return appCzy;
	}

	public void setAppCzy(String appCzy) {
		this.appCzy = appCzy;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyComplete() {
		return modifyComplete;
	}

	public void setModifyComplete(String modifyComplete) {
		this.modifyComplete = modifyComplete;
	}

	public String getCompRemark() {
		return compRemark;
	}

	public void setCompRemark(String compRemark) {
		this.compRemark = compRemark;
	}

	public String getCompCzy() {
		return compCzy;
	}

	public void setCompCzy(String compCzy) {
		this.compCzy = compCzy;
	}

	public Date getCompDate() {
		return compDate;
	}

	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getSafeProm() {
		return safeProm;
	}

	public void setSafeProm(String safeProm) {
		this.safeProm = safeProm;
	}

	public String getVisualProm() {
		return visualProm;
	}

	public void setVisualProm(String visualProm) {
		this.visualProm = visualProm;
	}

	public String getArtProm() {
		return artProm;
	}

	public void setArtProm(String artProm) {
		this.artProm = artProm;
	}

	public String getIsCheckDept() {
		return isCheckDept;
	}

	public void setIsCheckDept(String isCheckDept) {
		this.isCheckDept = isCheckDept;
	}

	public String getIsUpPrjProg() {
		return isUpPrjProg;
	}

	public void setIsUpPrjProg(String isUpPrjProg) {
		this.isUpPrjProg = isUpPrjProg;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getConfirmCzy() {
		return confirmCzy;
	}

	public void setConfirmCzy(String confirmCzy) {
		this.confirmCzy = confirmCzy;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getDepartment2Check() {
		return department2Check;
	}

	public void setDepartment2Check(String department2Check) {
		this.department2Check = department2Check;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
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

	public String getIsModifyComplete() {
		return IsModifyComplete;
	}

	public void setIsModifyComplete(String isModifyComplete) {
		IsModifyComplete = isModifyComplete;
	}

	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getIsPushCust() {
		return isPushCust;
	}

	public void setIsPushCust(String isPushCust) {
		this.isPushCust = isPushCust;
	}


	public Integer getCustScore() {
		return custScore;
	}

	public void setCustScore(Integer custScore) {
		this.custScore = custScore;
	}

	public String getCustRemarks() {
		return custRemarks;
	}

	public void setCustRemarks(String custRemarks) {
		this.custRemarks = custRemarks;
	}

    public String getIncludeErrPosi() {
        return includeErrPosi;
    }

    public void setIncludeErrPosi(String includeErrPosi) {
        this.includeErrPosi = includeErrPosi;
    }

}
