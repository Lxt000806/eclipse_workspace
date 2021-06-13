package com.house.home.client.service.evt;

public class DoSaveFeedBackCustServiceEvt extends BaseEvt {

	private String no;
	private String feedBackRemarkTemp;
	public String getFeedBackRemarkTemp() {
		return feedBackRemarkTemp;
	}
	public void setFeedBackRemarkTemp(String feedBackRemarkTemp) {
		this.feedBackRemarkTemp = feedBackRemarkTemp;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
}
