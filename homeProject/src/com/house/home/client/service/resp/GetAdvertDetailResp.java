package com.house.home.client.service.resp;

public class GetAdvertDetailResp extends BaseResponse{

	private String content;
	private String title;
	private String outUrl;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

}
