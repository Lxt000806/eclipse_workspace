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
@Entity
@Table(name="tCustAccount")
public class CustAccount extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PK")
	private Integer pk;
	@Column(name="Mobile1")
	private String mobile1;
	@Column(name="MM")
	private String mm;
	@Column(name="LastUpdate")
	private Date lastUpdate;
	@Column(name="RegisterDate")
	private Date registerDate;
	@Column(name="PicAddr")
	private String picAddr;
	@Column(name="Expired")
	private String expired;
	@Column(name="NickName")
	private String nickName;
	@Column(name="WeChatOpenid")
	private String weChatOpenid;
	@Column(name="Recommender")
	private String recommender;
	@Column(name="RecommenderType")
	private String recommenderType;
	
	@Transient
	private String descr;
	
	@Transient
	private Date registerDateFrom;
	
	@Transient
	private Date registerDateTo;

	@Transient
	private String codes;
	@Transient
	private String delCodes;
	@Transient
	private String addCodes;
	
	@Transient
	private String userToken;
	@Transient
	private String hasBindAddress;
	
	public String getHasBindAddress() {
		return hasBindAddress;
	}
	public void setHasBindAddress(String hasBindAddress) {
		this.hasBindAddress = hasBindAddress;
	}
	public String getWeChatOpenid() {
		return weChatOpenid;
	}
	public void setWeChatOpenid(String weChatOpenid) {
		this.weChatOpenid = weChatOpenid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Date getRegisterDateFrom() {
		return registerDateFrom;
	}
	public void setRegisterDateFrom(Date registerDateFrom) {
		this.registerDateFrom = registerDateFrom;
	}
	public Date getRegisterDateTo() {
		return registerDateTo;
	}
	public void setRegisterDateTo(Date registerDateTo) {
		this.registerDateTo = registerDateTo;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getDelCodes() {
		return delCodes;
	}
	public void setDelCodes(String delCodes) {
		this.delCodes = delCodes;
	}
	public String getAddCodes() {
		return addCodes;
	}
	public void setAddCodes(String addCodes) {
		this.addCodes = addCodes;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getRecommenderType() {
		return recommenderType;
	}
	public void setRecommenderType(String recommenderType) {
		this.recommenderType = recommenderType;
	}

	
}
