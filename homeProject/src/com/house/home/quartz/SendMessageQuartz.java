package com.house.home.quartz;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.oss.DesignDemoUpload;
import com.house.home.bean.basic.SmsBean;
import com.house.home.client.jpush.TestJpushClient;
import com.house.home.client.sms.SmsClient;
import com.house.home.entity.basic.SendMessage;
import com.house.home.service.basic.SendMessageService;

public class SendMessageQuartz {
	private static Logger logger = LoggerFactory.getLogger(SendMessageQuartz.class);
	
	public void execute(){
		try{
			logger.debug("定时发送消息开始");
			Long start = System.currentTimeMillis();
			TestJpushClient.doPushEmployeeList();
			TestJpushClient.doPushWorkerMessageList();
			TestJpushClient.doPushCustomerMessageList();
			DesignDemoUpload.doUploadtoOss();
			SendMessage message=new SendMessage();
			message.setSendStatus("0");
			message.setMsgType("3");
			SendMessageService SendMessageService = (SendMessageService) SpringContextHolder
					.getBean("sendMessageServiceImpl");
			List<SendMessage> list=SendMessageService.getSendMessageListBySql(message);
			for(SendMessage sendMessage:list){
				JSONObject json=new JSONObject();
				json.put("code", sendMessage.getSendPara1());
				json.put("product", sendMessage.getSendPara2());
				SmsClient.doSend(sendMessage,json);
			}
			Long end  = System.currentTimeMillis();
			logger.debug("定时发送消息结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
