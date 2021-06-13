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
 * SignIn信息
 */
@Entity
@Table(name = "tSignIn")
public class SignIn extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Pk")
	private Integer pk;
	@Column(name = "CustCode")
	private String custCode;
	@Column(name = "SignCzy")
	private String signCzy;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "Longitude")
	private Double longitude;
	@Column(name = "Latitude")
	private Double latitude;
	@Column(name = "Address")
	private String address;
	@Column(name="ErrPosi")
	private String errPosi;
	@Column(name = "No")
	private String no;
	@Column(name = "SignInType2")
	private String signInType2;
	@Column(name = "PrjItem")
	private String prjItem;
	@Column(name = "IsPass")
	private String isPass;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CustScore")
	private Integer custScore;
	@Column(name = "CustRemarks" )
	private String custRemarks;
	@Column(name = "SignPlacePK")
	private Integer signPlacePK; //打卡地点PK add by zb on 20190522
	@Column(name = "DockRemark")
	private String dockRemark;
	@Column(name = "WorkType12")
	private String workType12;
	
	@Transient
	private Date dateFrom;
	@Transient
	private Date dateTo;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String custAddress;
	@Transient
	private String custScoreSignIn;
	
	
	
	public String getDockRemark() {
		return dockRemark;
	}
	public void setDockRemark(String dockRemark) {
		this.dockRemark = dockRemark;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getCustScoreSignIn() {
		return custScoreSignIn;
	}
	public void setCustScoreSignIn(String custScoreSignIn) {
		this.custScoreSignIn = custScoreSignIn;
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
	
	public void setSignCzy(String signCzy) {
		this.signCzy = signCzy;
	}
	public String getSignCzy() {
		return this.signCzy;
	}
	
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	public Date getCrtDate() {
		return this.crtDate;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLatitude() {
		return this.latitude;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	public String getErrPosi() {
		return errPosi;
	}
	public void setErrPosi(String errPosi) {
		this.errPosi = errPosi;
	}
	
	
	
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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
	
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSignInType2() {
		return signInType2;
	}
	public void setSignInType2(String signInType2) {
		this.signInType2 = signInType2;
	}
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public Integer getSignPlacePK() {
		return signPlacePK;
	}
	public void setSignPlacePK(Integer signPlacePK) {
		this.signPlacePK = signPlacePK;
	}
	
	
}
