package com.house.home.client.jpush;

import com.house.framework.commons.conf.SystemConfig;

import cn.jpush.api.JPushClient;

public class JpushClientWraper {
	private static final String appKey = SystemConfig.getProperty("appKey", "", "jpush");
	private static final String masterSecret = SystemConfig.getProperty("masterSecret", "", "jpush");
	public  static final String employeeAppKey = SystemConfig.getProperty("employeeAppKey", "", "jpush");
	public  static final String workerAppKey = SystemConfig.getProperty("workerAppKey", "", "jpush");
	public  static final String employeeMasterSecret = SystemConfig.getProperty("employeeMasterSecret", "", "jpush");
	public  static final String workerMasterSecret = SystemConfig.getProperty("workerMasterSecret", "", "jpush");
	public  static final String customerMasterSecret = SystemConfig.getProperty("customerMasterSecret", "", "jpush");
	public  static final String customerAppKey = SystemConfig.getProperty("customerAppKey", "", "jpush");
	public static final String APP_PUSH="1";
	public static final String EMPLOYEEAPP_PUSH="3";
	public static JPushClient getJpushClient(String appKey, String masterSecret) {
		return new JPushClient(masterSecret, appKey);

	}

	public static JPushClient getJpushClient() {
		return new JPushClient(masterSecret, appKey);
	}

}
