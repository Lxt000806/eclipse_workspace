package com.house.home.quartz;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.client.jpush.TestJpushClient;
import com.house.home.client.sms.SmsClient;
import com.house.home.entity.basic.SendMessage;
import com.house.home.service.basic.SendMessageService;

public class SendTipQuartz {
	private static Logger logger = LoggerFactory.getLogger(SendTipQuartz.class);
	
	public void execute(){
		try{
			logger.debug("定时发送消息开始");
			Long start = System.currentTimeMillis();
			//TestJpushClient.doPushProjectManReportList();
			TestJpushClient.doPushWorkerMessageList();
			Long end  = System.currentTimeMillis();
			logger.debug("定时发送消息结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
