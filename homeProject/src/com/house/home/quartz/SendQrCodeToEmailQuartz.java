package com.house.home.quartz;

import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.EmailUtils;
import com.house.framework.commons.utils.FarbellUtils;
import com.house.framework.commons.utils.QrCodeImageUtils;
import com.house.home.bean.basic.QrCodeToEmailBean;

public class SendQrCodeToEmailQuartz {
	private static Logger logger = LoggerFactory.getLogger(SendQrCodeToEmailQuartz.class);
	
	public void execute(){
		try{
			logger.debug("发送二维码到邮箱定时任务开始");
			Long start = System.currentTimeMillis();

			String isSendQrCodeToEmail = SystemConfig.getProperty("value","","isSendQrCodeToEmail");
			//发件人SMTP服务器
			String smtpHost = QrCodeToEmailBean.SMTPHOST;
			//发送人用户名
			String sendUserName = QrCodeToEmailBean.SENDUSERNAME;
			//发送人密码
			String sendUserPass = QrCodeToEmailBean.SENDUSERPASS;
			//发送人地址
			String senderAddress = QrCodeToEmailBean.SENDERADDRESS;
			//收件人地址
			String receviceAddress = QrCodeToEmailBean.RECEIVEADDRESS;
			//邮件标题
			String title = QrCodeToEmailBean.TITLE;
			//邮件固定内容
			String context= QrCodeToEmailBean.CONTEXT;
			//生成二维码字符串
			String qrcode = FarbellUtils.qrcode(101002589, 108000);
			context += qrcode;
			byte[] data = QrCodeImageUtils.buildQuickMark(qrcode);
			ByteArrayDataSource bads = new ByteArrayDataSource(data,"application/x-jpg");
			if("1".equals(isSendQrCodeToEmail)){
				EmailUtils.sendEmail(smtpHost, sendUserName, sendUserPass, senderAddress, receviceAddress, title, context,bads);
			}
			Long end  = System.currentTimeMillis();
			logger.debug("发送二维码到邮箱定时任务结束，用时："+(end - start)+"ms");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
