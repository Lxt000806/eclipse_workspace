package com.house.home.bean.basic;

import java.io.Serializable;

import com.house.framework.commons.conf.SystemConfig;

/**
 * 钉钉开放平台
 */
@SuppressWarnings("serial")
public class DingTalkBean implements Serializable {
	
	/**
	 * APP认证账号
	 */
	public static final String APP_KEY = SystemConfig.getProperty("appKey", "", "dingtalk");
	
	/**
	 * APP认证密钥
	 */
	public static final String APP_SECRET = SystemConfig.getProperty("appSecret", "", "dingtalk");
	
	/**
	 * 应用代理ID
	 */
	public static final String AGENT_ID = SystemConfig.getProperty("agentId", "", "dingtalk");
	
	/**
	 * 返回码
	 */
	public static final String RESPONSE_CODE = "errcode";
	
	/**
	 * 返回信息
	 */
	public static final String RESPONSE_MSG = "errmsg";
	
	/**
	 * 接口调用成功的返回码
	 */
	public static final Long SUCCESS_CODE = 0L;
	
	/**
	 * token过期时间
	 * 银联开放平台规定过期时间为7200秒，访问网络需要时间，这里过期时间设为7000秒
	 */
	public static final long TOKEN_TIMEOUT = 7000;
	
	/**
	 * 获取token的url
	 */
	public static final String GET_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";
	
	/**
	 * 根据手机号获取userid的url
	 */
	public static final String GET_USER_ID_BY_MOBILE_URL = "https://oapi.dingtalk.com/user/get_by_mobile";
	
	/**
	 * 发送工作通知消息的url
	 */
	public static final String SEND_MESSAGE_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
	
}
