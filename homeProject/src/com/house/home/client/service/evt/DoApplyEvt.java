package com.house.home.client.service.evt;

import javax.management.loading.PrivateClassLoader;

public class DoApplyEvt extends BaseEvt{
	
	private String processDefinitionId;
	private String detailJson;
	private String procKey;
	private String photoUrlList;
	private String actName;
	private String bank;
	private String cardId;
	private String subBranch;
	
	
	public String getSubBranch() {
		return subBranch;
	}
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPhotoUrlList() {
		return photoUrlList;
	}
	public void setPhotoUrlList(String photoUrlList) {
		this.photoUrlList = photoUrlList;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}

}
