package com.house.home.client.service.resp;

public class RepositoryResp extends BaseResponse{
	private Integer pk;
	private String topic;
	private String content;
	private Integer visitCnt;
	private String docCode;
	private String docName;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getVisitCnt() {
		return visitCnt;
	}
	public void setVisitCnt(Integer visitCnt) {
		this.visitCnt = visitCnt;
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
	
}
