package com.house.home.client.service.evt;

import java.util.Date;

import com.house.framework.commons.utils.XmlConverUtil;

public class SaveCustSelectionEvt extends BaseEvt {
	private String crtCzy;
	private Date date;
	private String itemType1;
	private String remarks;
	private String batchType;
	private String custType;
	private Integer dispSeq;
	private String workType12;
	private String status;
	private String custCode;
	private String otherRemarks;
	private String xmlData;
	private String m_umState;
	private String no;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getM_umState() {
		return m_umState;
	}
	public void setM_umState(String m_umState) {
		this.m_umState = m_umState;
	}
	public String getCrtCzy() {
		return crtCzy;
	}
	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getItemType1() {
		return itemType1;
	}
	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getWorkType12() {
		return workType12;
	}
	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getOtherRemarks() {
		return otherRemarks;
	}
	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}
	public String getXmlData() {
		return XmlConverUtil.jsonToXmlNoHead(xmlData);
	}
	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	
	
}
