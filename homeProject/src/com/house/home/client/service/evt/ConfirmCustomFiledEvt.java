package com.house.home.client.service.evt;

public class ConfirmCustomFiledEvt extends BaseEvt {
    
    private String prjItem;
    private String prjProgConfirmNo;
	
	public String getPrjItem() {
		return prjItem;
	}
	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}
	public String getPrjProgConfirmNo() {
		return prjProgConfirmNo;
	}
	public void setPrjProgConfirmNo(String prjProgConfirmNo) {
		this.prjProgConfirmNo = prjProgConfirmNo;
	}


}
