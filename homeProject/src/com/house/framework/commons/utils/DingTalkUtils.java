package com.house.framework.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.house.home.bean.basic.DingTalkBean;
import com.house.home.client.http.HttpMethod;

/**
 * 对接钉钉开放平台
 */
public class DingTalkUtils {
	
	private static Logger logger = LoggerFactory.getLogger(DingTalkUtils.class);
	
	/**
	 * token
	 */
	private static String ACCESS_TOKEN;
	
	/**
	 * 上次取得token的时间
	 */
	private static long ACCESS_TOKEN_TIME;
	
	/**
	 * 银行卡信息查询-获取token（未过期不重新获取）
	 */
	public static void generateAccessToken() {
		generateAccessToken(false);
	}
	
	/**
	 * 获取token
	 * @param force 强制获取token；如果为false，token未过期不重新获取。
	 */
	public static void generateAccessToken(boolean force) {
		
		// token未过期，不重新获取
		if (!force && System.currentTimeMillis() - ACCESS_TOKEN_TIME <= DingTalkBean.TOKEN_TIMEOUT) { 
			return;
		}
		
		String url = DingTalkBean.GET_TOKEN_URL + "?appkey=" + DingTalkBean.APP_KEY 
				+ "&appsecret=" + DingTalkBean.APP_SECRET;
		
		HttpMethod httpMethod = new HttpMethod();
		String result = httpMethod.get(url, "utf-8");
		JSONObject jsonObject = JSONObject.parseObject(result);
		
		if (!DingTalkBean.SUCCESS_CODE.equals(jsonObject.getLong(DingTalkBean.RESPONSE_CODE))) {
			logger.error("钉钉应用-获取token出错。错误码：" + jsonObject.getString(DingTalkBean.RESPONSE_CODE) 
					+ "； 错误信息：" + jsonObject.getString(DingTalkBean.RESPONSE_MSG));
		}
		
		ACCESS_TOKEN = jsonObject.getString("access_token");
		ACCESS_TOKEN_TIME = System.currentTimeMillis();
	}
	
	/**
	 * 根据手机号获取userid
	 * @param mobile
	 * @return
	 */
	public static String getUserIdByMobile(String mobile) {
		
		// 生成token
		generateAccessToken();
		
		String url = DingTalkBean.GET_USER_ID_BY_MOBILE_URL + "?access_token=" + ACCESS_TOKEN 
				+ "&mobile=" + mobile;
		HttpMethod httpMethod = new HttpMethod();
		String jsonString = httpMethod.get(url, "utf-8");
		
		String result = "";
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		if (!DingTalkBean.SUCCESS_CODE.equals(jsonObject.getLong(DingTalkBean.RESPONSE_CODE))) {
			logger.error("钉钉应用-根据手机号获取userid出错。错误码：" + jsonObject.getString(DingTalkBean.RESPONSE_CODE) 
					+ "； 错误信息：" + jsonObject.getString(DingTalkBean.RESPONSE_MSG));
		} else {
			result = jsonObject.getString("userid");
		}
		
		return result;
	}
	
	/**
	 * 发送工作消息
	 * @param userIds
	 * @param content
	 */
	public static void sendMessage(String userIds, String content) {
		
		// 生成token
		generateAccessToken();
		
		HttpMethod httpMethod = new HttpMethod();
		String msg = "\"msg\":{\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}";
		String body = "{\"agent_id\":\"" + DingTalkBean.AGENT_ID + "\",\"userid_list\":\"" + userIds + "\","+msg+"}";
		String url = DingTalkBean.SEND_MESSAGE_URL + "?access_token=" + ACCESS_TOKEN;
		String result = httpMethod.post(url, body);
		
		JSONObject jsonObject = JSONObject.parseObject(result);
		
		if (!DingTalkBean.SUCCESS_CODE.equals(jsonObject.getLong(DingTalkBean.RESPONSE_CODE))) {
			logger.error("钉钉应用-发送工作消息出错。错误码：" + jsonObject.getString(DingTalkBean.RESPONSE_CODE) 
					+ "； 错误信息：" + jsonObject.getString(DingTalkBean.RESPONSE_MSG));
		}
	}
	
	public static void main(String[] args) {
		String userId;
		userId = getUserIdByMobile("18750794209");
		System.out.println(userId+",00");
		sendMessage(userId, "你有81条待审批信息，请前往易居通进行审批【18:13】");
	}	
}
