package com.house.home.client.service.resp;

import java.util.Date;

public class GetDesignDemoListResp {

	private String no;
	private String picAddr;
	private String builderDescr;
	private String designStyleDescr;
	private String layoutDescr;
	private Double area;
	private Integer picAddrPk;
	private String designDescr;
	
	
	
	public String getDesignDescr() {
		return designDescr;
	}
	public void setDesignDescr(String designDescr) {
		this.designDescr = designDescr;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	public String getBuilderDescr() {
		return builderDescr;
	}
	public void setBuilderDescr(String builderDescr) {
		this.builderDescr = builderDescr;
	}
	public String getDesignStyleDescr() {
		return designStyleDescr;
	}
	public void setDesignStyleDescr(String designStyleDescr) {
		this.designStyleDescr = designStyleDescr;
	}
	public String getLayoutDescr() {
		return layoutDescr;
	}
	public void setLayoutDescr(String layoutDescr) {
		this.layoutDescr = layoutDescr;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Integer getPicAddrPk() {
		return picAddrPk;
	}
	public void setPicAddrPk(Integer picAddrPk) {
		this.picAddrPk = picAddrPk;
	}
	
	
}
