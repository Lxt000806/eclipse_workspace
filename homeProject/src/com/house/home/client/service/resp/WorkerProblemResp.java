package com.house.home.client.service.resp;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkerProblemResp extends BaseResponse{
	private String no;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private String typeDescr;
	private String remark;
	private double stopDay;
	private String type;
	private Integer custWkPk;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getStopDay() {
		return stopDay;
	}
	public void setStopDay(double stopDay) {
		this.stopDay = stopDay;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCustWkPk() {
		return custWkPk;
	}
	public void setCustWkPk(Integer custWkPk) {
		this.custWkPk = custWkPk;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	
	
	
}
