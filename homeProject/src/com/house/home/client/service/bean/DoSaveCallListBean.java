package com.house.home.client.service.bean;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;

public class DoSaveCallListBean {

	private String mobileFilePath;
	private Integer pk;
	public String getMobileFilePath() {
		return mobileFilePath;
	}
	public void setMobileFilePath(String mobileFilePath) {
		if(StringUtils.isNotBlank(mobileFilePath)){
			try {
				mobileFilePath = "HOMEDECOR" + Base64.encodeBase64String(mobileFilePath.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.mobileFilePath = mobileFilePath;
	}
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
}
