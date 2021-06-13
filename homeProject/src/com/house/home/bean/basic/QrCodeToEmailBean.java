package com.house.home.bean.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QrCodeToEmailBean implements Serializable{
	/**
	 * 发件人SMTP服务器
	 */
	public static final String SMTPHOST = "smtp.exmail.qq.com";
	
	/**
	 * 发送人用户名
	 */
	public static final String SENDUSERNAME = "it@u-om.com";
	
	/**
	 * 发送人密码
	 */
	public static final String SENDUSERPASS = "yjIT2019";
	
	/**
	 * 发送人地址
	 */
	public static final String SENDERADDRESS = "it@u-om.com";
	
	/**
	 * 接收人地址
	 */
	public static final String RECEIVEADDRESS = "491802918@qq.com";
	
	/**
	 * 邮件标题
	 */
	public static final String TITLE = "泛达门禁二维码";
	
	/**
	 * 邮件内容
	 */
	public static final String CONTEXT = "二维码：";
}
