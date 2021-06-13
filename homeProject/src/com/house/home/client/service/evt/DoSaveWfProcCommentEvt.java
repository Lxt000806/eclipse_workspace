package com.house.home.client.service.evt;

public class DoSaveWfProcCommentEvt extends BaseEvt {

	private String WfProcInstNo;
	private String comment;
	
	
	public String getWfProcInstNo() {
		return WfProcInstNo;
	}
	public void setWfProcInstNo(String wfProcInstNo) {
		WfProcInstNo = wfProcInstNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
