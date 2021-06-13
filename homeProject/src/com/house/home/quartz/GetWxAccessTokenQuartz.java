package com.house.home.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.utils.WxAppletUtils;

public class GetWxAccessTokenQuartz {
	private static Logger logger = LoggerFactory.getLogger(GetWxAccessTokenQuartz.class);
	
	public void execute(){
		try{
			logger.debug("定时获取微信小程序后端接口调用凭证开始");
			Long start = System.currentTimeMillis();
			WxAppletUtils.getAccessToken();
			Long end  = System.currentTimeMillis();
			logger.debug("定时获取微信小程序后端接口调用凭证结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
