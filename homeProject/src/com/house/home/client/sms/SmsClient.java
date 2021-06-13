package com.house.home.client.sms;

import java.util.Date;

import net.sf.json.JSONObject;

import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.bean.basic.SmsBean;
import com.house.home.entity.basic.SendMessage;
import com.house.home.service.basic.SendMessageService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;


public class SmsClient {
	/**
	 * 
	 * @param sendMessage 要发送的消息
	 * @param json 消息参数
	 */
	public static void  doSend(SendMessage sendMessage,JSONObject json){
		SendMessageService SendMessageService = (SendMessageService) SpringContextHolder
				.getBean("sendMessageServiceImpl");
		sendMessage.setSendStatus("3");
		sendMessage.setSendDate(new Date());
		SendMessageService.update(sendMessage);
		SmsBean sms=new SmsBean();
		TaobaoClient client = new DefaultTaobaoClient(sms.ALIDAYU_API_URL, sms.ALIDAYU_APP_KEY, sms.ALIDAYU_APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName(sms.ALIDAYU_SMS_SIGN_PROJECTMAN);
		req.setSmsParamString(json.toString());
		req.setRecNum(sendMessage.getCustPhone());
		req.setSmsTemplateCode(sms.SMS_TEMPLATE_CODE.get(sendMessage.getMsgType()));
		AlibabaAliqinFcSmsNumSendResponse rsp=null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendMessage.setSendStatus("2");
			sendMessage.setRetMsg("短信发送发生异常");
			SendMessageService.update(sendMessage);
		}
		sendMessage.setRetMsg("短信类型"+sendMessage.getMsgType()+rsp.getBody());
		if(rsp.isSuccess()){
			sendMessage.setSendStatus("1");
		}else{
			sendMessage.setSendStatus("2");
		}
		SendMessageService.update(sendMessage);
	}
}
