package com.house.home.bean.basic;

import java.io.Serializable;

import com.house.framework.commons.conf.SystemConfig;

/**
 * 泛达门禁系统
 */
@SuppressWarnings("serial")
public class FarbellBean implements Serializable {
	
	/**
	 * APP认证账号
	 */
	public static final String APP_ID = SystemConfig.getProperty("appId", "yjit", "farbell");
	
	/**
	 * APP认证密钥
	 */
	public static final String SECRET_KEY = SystemConfig.getProperty("secretKey", "vkjsimdejmmqwq", "farbell");
	
	/**
	 * 门禁代理地址
	 */
//	public static final String API_URL = "http://192.168.0.136:9001/api";
	public static final String API_URL = 
			SystemConfig.getProperty("apiUrl", "http://192.168.0.136:9001/api", "farbell");
	
	/**
	 * 返回码
	 */
	public static final String RESPONSE_CODE = "code";
	
	/**
	 * 返回信息
	 */
	public static final String RESPONSE_MSG = "msg";
	
	/**
	 * 接口调用成功的返回码
	 */
	public static final int SUCCESS_CODE = 10000;
	
	/**
	 * 拨码开关
	 * 所有门禁设备的拨码开关都将设置成一样
	 */
	public static final String AC_NUM = "1";
	
	/**
	 * 持续时间（以秒为单位）
	 */
	public static final long DURATION_TIME = 30 * 60;
}
