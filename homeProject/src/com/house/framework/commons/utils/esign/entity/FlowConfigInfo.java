package com.house.framework.commons.utils.esign.entity;

/**
 * @description 任务配置信息
 * @author 宫清
 * @date 2019年11月19日 下午2:32:59
 * @since JDK1.7
 */
public class FlowConfigInfo {
	
	//通知开发者地址
	private String noticeDeveloperUrl;
	
	//通知方式，可选择多种通知方式
	private String noticeType;
	
	//签署平台
	private String signPlatform;
	
	//签署有效截止日期
	private Long signValidity;

	public String getNoticeDeveloperUrl() {
		return noticeDeveloperUrl;
	}

	public void setNoticeDeveloperUrl(String noticeDeveloperUrl) {
		this.noticeDeveloperUrl = noticeDeveloperUrl;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getSignPlatform() {
		return signPlatform;
	}

	public void setSignPlatform(String signPlatform) {
		this.signPlatform = signPlatform;
	}

	public Long getSignValidity() {
		return signValidity;
	}

	public void setSignValidity(Long signValidity) {
		this.signValidity = signValidity;
	}

	public FlowConfigInfo(String noticeDeveloperUrl, String noticeType,String signPlatform) {
		super();
		this.noticeDeveloperUrl = noticeDeveloperUrl;
		this.noticeType = noticeType;
		this.signPlatform = signPlatform;
	}

	public FlowConfigInfo() {
	}
	
	
}
