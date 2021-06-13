package com.house.framework.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.house.home.bean.basic.UnionPayBean;
import com.house.home.client.http.HttpMethod;

/**
 * 对接银联开放平台
 */
public class UnionPayUtils {
	
	private static Logger logger = LoggerFactory.getLogger(UnionPayUtils.class);
	
	/**
	 * 银行卡信息查询token
	 */
	private static String CARDBIN_TOKEN;
	
	/**
	 * 银行卡信息查询token的时间
	 */
	private static long CARDBIN_TOKEN_TIME;
	
	/**
	 * 银行卡信息查询-获取token（未过期不重新获取）
	 */
	public static void generateCardBinToken() {
		generateCardBinToken(false);
	}
	
	/**
	 * 银行卡信息查询-获取token
	 * @param force 强制获取token；如果为false，token未过期不重新获取。
	 */
	public static void generateCardBinToken(boolean force) {
		
		long tokenTime = System.currentTimeMillis();
		
		if (!force) {
			if (tokenTime - CARDBIN_TOKEN_TIME <= UnionPayBean.TOKEN_TIMEOUT) { // token未过期，不重新获取
				return;
			}
		}
		
		String url = UnionPayBean.CARDBIN_TOKEN_URL + "?app_id=" + UnionPayBean.APP_ID 
				+ "&app_secret=" + UnionPayBean.APP_SECRET;
		
		HttpMethod httpMethod = new HttpMethod();
		String result = httpMethod.get(url, "utf-8");
		JSONObject jsonObject = JSONObject.parseObject(result);
		
		if (!UnionPayBean.SUCCESS_CODE.equals(jsonObject.getString(UnionPayBean.RESPONSE_CODE))) {
			logger.error("银行卡信息查询-获取token出错。错误码：" + jsonObject.getString(UnionPayBean.RESPONSE_CODE) 
					+ "； 错误信息：" + jsonObject.getString(UnionPayBean.RESPONSE_MSG));
		}
		
		CARDBIN_TOKEN = jsonObject.getString("token");
		CARDBIN_TOKEN_TIME = tokenTime;
	}
	
	/**
	 * 银行卡信息查询
	 * @param cardNo
	 * @return
	 */
	public static String getCardInfo(String cardNo) {
		
		generateCardBinToken();
		String url = UnionPayBean.CARDBIN_CARDINFO_URL + "?token=" + CARDBIN_TOKEN;
		String body = "{\"cardNo\":\"" + cardNo + "\"}";
		
		// 时间戳
		long ts = System.currentTimeMillis();
		// 计算签名
		String sign = UnionPaySignUtil.sign(JSONObject.parseObject(body)
				.toString(), String.valueOf(ts), UnionPayBean.SIGN_SECRET);
		
		HttpMethod httpMethod = new HttpMethod();
		String result = httpMethod.post(url + "&ts=" + ts + "&sign=" + sign, body);
		
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (!UnionPayBean.SUCCESS_CODE.equals(jsonObject.getString(UnionPayBean.RESPONSE_CODE))) {
			logger.error("银行卡信息查询出错。卡号：" + cardNo 
					+ "；错误码：" + jsonObject.getString(UnionPayBean.RESPONSE_CODE) 
					+ "； 错误信息：" + jsonObject.getString(UnionPayBean.RESPONSE_MSG));
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getCardInfo("4392260030479461"));
	}	
}
