package com.house.home.client.service.resp;

import java.util.List;
import java.util.Map;

public class CustTagResp {
	private String tagGroupDescr;
	private Integer tagGroupPk;
	private String isMulti;
	private String color;
	private Integer tagPk;
	private String tagDescr;
	private List<Map<String, Object>> custTags;
	
	public String getTagGroupDescr() {
		return tagGroupDescr;
	}
	public void setTagGroupDescr(String tagGroupDescr) {
		this.tagGroupDescr = tagGroupDescr;
	}
	public Integer getTagGroupPk() {
		return tagGroupPk;
	}
	public void setTagGroupPk(Integer tagGroupPk) {
		this.tagGroupPk = tagGroupPk;
	}
	public String getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(String isMulti) {
		this.isMulti = isMulti;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getTagPk() {
		return tagPk;
	}
	public void setTagPk(Integer tagPk) {
		this.tagPk = tagPk;
	}
	public String getTagDescr() {
		return tagDescr;
	}
	public void setTagDescr(String tagDescr) {
		this.tagDescr = tagDescr;
	}
	public List<Map<String, Object>> getCustTags() {
		return custTags;
	}
	public void setCustTags(List<Map<String, Object>> custTags) {
		this.custTags = custTags;
	}
	
	
	
}
