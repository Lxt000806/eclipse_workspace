package com.house.home.client.service.resp;

public class IsCommitResp extends BaseResponse{
	
	private boolean canCommit;
	private String message;

	public boolean isCanCommit() {
		return canCommit;
	}

	public void setCanCommit(boolean canCommit) {
		this.canCommit = canCommit;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
