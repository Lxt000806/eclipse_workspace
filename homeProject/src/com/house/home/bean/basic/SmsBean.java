package com.house.home.bean.basic;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("serial")
public class SmsBean implements Serializable {
	public static final String ALIDAYU_API_URL="http://gw.api.taobao.com/router/rest";
	public static final String ALIDAYU_APP_KEY="23363294";
	public static final String ALIDAYU_APP_SECRET="0823602fa966c4c793780a002c19d28e";
	public static final String ALIDAYU_SMS_SIGN="有家装饰";
	public static final String ALIDAYU_SMS_SIGN_PROJECTMAN="项目经理APP";
	public static final String ALIDAYU_SMS_REGISTER="1";
	public static final String ALIDAYU_SMS_RETPWD="2";
	public static final String ALIDAYU_SMS_gzjs="3";
	public static  Map<String,String> SMS_TEMPLATE_CODE=new HashMap<String,String>();
	static{
		SMS_TEMPLATE_CODE.put(ALIDAYU_SMS_REGISTER,"SMS_8956352");
		SMS_TEMPLATE_CODE.put(ALIDAYU_SMS_RETPWD,"SMS_8956350");
		SMS_TEMPLATE_CODE.put(ALIDAYU_SMS_gzjs,"SMS_63465373");
	}
	private String phone;
	private String code;
	private String type;
	private String date;
	
	
	public SmsBean(String phone, String code, String type) {
		super();
		this.phone = phone;
		this.code = code;
		this.type = type;
	}
	public SmsBean() {
		
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
