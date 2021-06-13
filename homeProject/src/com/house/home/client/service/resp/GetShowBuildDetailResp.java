package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetShowBuildDetailResp extends BaseResponse {

	private String address;
	private Integer area;
	private String custDescr;
	private Date beginDate;
	private Date planEndDate;
	private String custTypeDescr;
	private String gender;
	private String designMan;
	private List<Map<String, Object>> datas;
	private String projectMan;
	private String custCode;
	private String empAddress;
	
	
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getProjectMan() {
		return projectMan;
	}
	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}
	public String getDesignMan() {
		return designMan;
	}
	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getCustTypeDescr() {
		return custTypeDescr;
	}
	public void setCustTypeDescr(String custTypeDescr) {
		this.custTypeDescr = custTypeDescr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getCustDescr() {
		return custDescr;
	}
	public void setCustDescr(String custDescr) {
		this.custDescr = custDescr;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public List<Map<String, Object>> getDatas() {
		return datas;
	}
	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
