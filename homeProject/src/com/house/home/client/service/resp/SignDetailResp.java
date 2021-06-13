package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SignDetailResp extends BaseResponse{

	private String signCount;
	private String typeDescr;
	private String actCzy;
	private String address;
	private String custDescr;
	private Date date;
	private String mobile;
	private Integer pk;
	private String describe;
	private String no;
	private String type;
	private String deptType;// 一级部门或二级部门
	private List<Map<String, Object>> photos;
	private String totalNum;
	private String empName;
	private Integer personNum;
	private List<Map<String, Object>> allPhotos;
	
	
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public List<Map<String, Object>> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Map<String, Object>> photos) {
		this.photos = photos;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getSignCount() {
		return signCount;
	}
	public void setSignCount(String signCount) {
		this.signCount = signCount;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public String getActCzy() {
		return actCzy;
	}
	public void setActCzy(String actCzy) {
		this.actCzy = actCzy;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getPersonNum() {
		return personNum;
	}
	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}
	public List<Map<String, Object>> getAllPhotos() {
		return allPhotos;
	}
	public void setAllPhotos(List<Map<String, Object>> allPhotos) {
		this.allPhotos = allPhotos;
	}
}
