package com.house.home.client.service.bean;

public class CompanyBean {
	private Long id;
	//公司名称
	private String name;
	//公司logo
	private String logo;
	//公司昵称
	private String nickName;
	private String area;
	private String address;
	private String longitude;
	private String latitude;
	private String yellowTypeName;
	private String lifeTypeName;
    private Integer distance;
    private Double rating;
	//备注
	private String remark;
	private Integer isYipay;
	private Integer isVip;
	private Integer isHot;
	private Long userId;
	
	/**
	 * 优惠信息
	 */
	private String preferentialInfo;
	/**
	 * 是否有最新优惠（0：没有/1:有）
	 */
	private Long isPreferential;
	/**
	 * 是否支持积分兑换（0：不支持/1：支持）
	 */
	private Long supportCredits;
	/**
	 * 消费次数
	 */
	private Long comsumeCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getYellowTypeName() {
		return yellowTypeName;
	}
	public void setYellowTypeName(String yellowTypeName) {
		this.yellowTypeName = yellowTypeName;
	}
	public String getLifeTypeName() {
		return lifeTypeName;
	}
	public void setLifeTypeName(String lifeTypeName) {
		this.lifeTypeName = lifeTypeName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsYipay() {
		return isYipay;
	}
	public void setIsYipay(Integer isYipay) {
		this.isYipay = isYipay;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPreferentialInfo() {
		return preferentialInfo;
	}
	public void setPreferentialInfo(String preferentialInfo) {
		this.preferentialInfo = preferentialInfo;
	}
	public Long getIsPreferential() {
		return isPreferential;
	}
	public void setIsPreferential(Long isPreferential) {
		this.isPreferential = isPreferential;
	}
	public Long getSupportCredits() {
		return supportCredits;
	}
	public void setSupportCredits(Long supportCredits) {
		this.supportCredits = supportCredits;
	}
	public Long getComsumeCount() {
		return comsumeCount;
	}
	public void setComsumeCount(Long comsumeCount) {
		this.comsumeCount = comsumeCount;
	}
	
}
