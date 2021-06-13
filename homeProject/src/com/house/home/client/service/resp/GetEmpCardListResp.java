package com.house.home.client.service.resp;

public class GetEmpCardListResp {
	
	private String actName;
	private String cardId;
	private String bank;
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
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
}
