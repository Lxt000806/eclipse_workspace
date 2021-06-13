package com.house.home.client.service.resp;

public class GetRSAPublicKeyResp extends BaseResponse {
	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
