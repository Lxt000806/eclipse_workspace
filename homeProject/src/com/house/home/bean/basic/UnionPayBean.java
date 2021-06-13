package com.house.home.bean.basic;

import java.io.Serializable;

/**
 * 银联开放平台
 */
@SuppressWarnings("serial")
public class UnionPayBean implements Serializable {
	
	/**
	 * APP认证账号
	 */
	public static final String APP_ID = "up_fc8eba211beu_z3wg";
	
	/**
	 * APP认证密钥
	 */
	public static final String APP_SECRET = "b6d0d4b4ec441816af547469dc1fc844";
	
	/**
	 * 用户签名密钥
	 */
	public static final String SIGN_SECRET = "xuzhuping";
	
	/**
	 * 返回码
	 */
	public static final String RESPONSE_CODE = "respCd";
	
	/**
	 * 返回信息
	 */
	public static final String RESPONSE_MSG = "respMsg";
	
	/**
	 * 接口调用成功的返回码
	 */
	public static final String SUCCESS_CODE = "0000";
	
	/**
	 * token过期时间
	 * 银联开放平台规定过期时间为10分钟，访问网络需要时间，这里过期时间设为8分钟
	 */
	public static final long TOKEN_TIMEOUT = 1000 * 60 * 8;
	
	/**
	 * 银行卡信息查询-获取token的url
	 */
	public static final String CARDBIN_TOKEN_URL = "https://openapi.unionpay.com/upapi/cardbin/token";
	
	/**
	 * 银行卡信息查询的url
	 */
	public static final String CARDBIN_CARDINFO_URL = "https://openapi.unionpay.com/upapi/cardbin/cardinfo";
	
}
