package com.house.home.client.service.evt;

public class GetWfProcInstPageHtmlEvt extends BaseEvt {
	private String procKey;
	private String src;

	public String getProcKey() {
		return procKey;
	}

	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
