package com.house.home.client.service.resp;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class DocDetailResp extends BaseResponse{
	private Integer pk;
	private String docCode;
	private String docName;
	private Date enableDate;
	private Date expiredDate;
	private String docVersion;
	private String briefDesc;
	private String keyWords;
	private List<Map<String, Object>> docAttachmentList;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getDocVersion() {
		return docVersion;
	}
	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}
	public String getBriefDesc() {
		return briefDesc;
	}
	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public List<Map<String, Object>> getDocAttachmentList() {
		return docAttachmentList;
	}
	public void setDocAttachmentList(List<Map<String, Object>> docAttachmentList) {
		this.docAttachmentList = docAttachmentList;
	} 
	
}
