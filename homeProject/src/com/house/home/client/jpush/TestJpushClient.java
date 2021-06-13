package com.house.home.client.jpush;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.cache.MessageCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.service.basic.CustMessageService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.basic.WorkerMessageService;
import com.house.home.service.basic.XtcsService;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送
 */
public class TestJpushClient {

	/**
	 * @param args
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		Notice notice = new Notice();
		notice.setId("13645073611");
		notice.setTitle("<p>测试标题33</p><p>测试标题33</p>");
		notice.setAlert("后台测试");
		doPush(notice);
		String title = "您有{0}条推送消息";
		System.out.println(MessageFormat.format(title, "12"));
	}

	/**单个推送
	 * @param notice
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public static void doPush(Notice notice) throws APIConnectionException, APIRequestException {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("id", notice.getId());
		AndroidNotification androidNotification = AndroidNotification
				.newBuilder().setAlert(notice.getAlert())
				.setTitle(notice.getTitle()).addExtras(extras).build();
		IosNotification iosNotification = IosNotification.newBuilder()
				.setAlert(notice.getAlert()).setSound("default").setBadge(0)
				.addExtras(extras).build();

		Notification notification = Notification.newBuilder()
				.addPlatformNotification(androidNotification)
				.addPlatformNotification(iosNotification).build();
		JPushClient jpushClient = JpushClientWraper.getJpushClient();
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(notice.getId()))
				.setAudience(Audience.all())
				 .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                       // .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                      //  .setTimeToLive(86400)
	                        .build())
				.setNotification(notification).build();
		PushResult result = jpushClient.sendPush(payload);
		System.out.println(result.toString());

	}
	
	/**
	 * 员工App单个推送
	 * 
	 * @param notice
	 */
    public static void doPushEmployee(Notice notice) {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("id", notice.getId());
        
        AndroidNotification androidNotification = AndroidNotification.newBuilder()
                .setAlert(notice.getTitle())
                .setTitle(notice.getTitle())
                .addExtras(extras)
                .build();
        IosNotification iosNotification = IosNotification.newBuilder()
                .setAlert(notice.getTitle())
                .setSound("default")
                .setBadge(0)
                .addExtras(extras)
                .build();

        Notification notification = Notification.newBuilder()
                .addPlatformNotification(androidNotification)
                .addPlatformNotification(iosNotification)
                .build();
        
        try {
            
            JPushClient jpushClient = JpushClientWraper
                    .getJpushClient(JpushClientWraper.employeeAppKey,
                            JpushClientWraper.employeeMasterSecret);

            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.alias(notice.getId()))
                    .setOptions(Options.newBuilder()
                            // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                            .setApnsProduction(true)
                            // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                            // .setSendno(1)
                            // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                            // .setTimeToLive(86400)
                            .build())
                    .setNotification(notification)
                    .build();
            
            jpushClient.sendPush(payload);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 批量推送
	 */
	public static void doPushEmployeeList() {
		PersonMessageService personMessageService = (PersonMessageService) SpringContextHolder
				.getBean("personMessageServiceImpl");
		MessageCacheManager messageCacheManager = (MessageCacheManager) SpringContextHolder
				.getBean("messageCacheManager");
		String title = String.valueOf(messageCacheManager.get("phone.jpush.title"));
		List<Map<String, Object>> list = personMessageService.getEmployeePushList();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Notice notice = new Notice();
				notice.setId(String.valueOf(map.get("phone")));
				notice.setTitle(MessageFormat.format(title, String.valueOf(map.get("msgnum"))));
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("id", notice.getId());
				AndroidNotification androidNotification = AndroidNotification
						.newBuilder().setAlert(notice.getTitle())
						.setTitle(notice.getTitle()).addExtras(extras).build();
				IosNotification iosNotification = IosNotification.newBuilder()
						.setAlert(notice.getTitle()).setSound("default")
						.setBadge(0).addExtras(extras).build();

				Notification notification = Notification.newBuilder()
						.addPlatformNotification(androidNotification)
						.addPlatformNotification(iosNotification).build();
				try {
					JPushClient jpushClient=null;
					if("3".equals(map.get("rcvType"))){
						jpushClient = JpushClientWraper
								.getJpushClient(JpushClientWraper.employeeAppKey,JpushClientWraper.employeeMasterSecret);
					}else{
						jpushClient = JpushClientWraper
								.getJpushClient();
					}
					PushPayload payload = PushPayload.newBuilder()
							.setPlatform(Platform.all())
							.setAudience(Audience.alias(notice.getId()))
							 .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                      //  .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                     //   .setTimeToLive(86400)
	                        .build())
							.setNotification(notification).build();
					PushResult result = jpushClient.sendPush(payload);
					System.out.println(result.toString());
					
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}finally{
					//更新推送标志
					personMessageService.updateEmployeePushList(notice.getId());
				}
			}

		}

	}
	/**
	 * 提醒项目经理施工计划
	 */
	public static void doPushProjectManReportList() {
		PersonMessageService personMessageService = (PersonMessageService) SpringContextHolder
				.getBean("personMessageServiceImpl");
		MessageCacheManager messageCacheManager = (MessageCacheManager) SpringContextHolder
				.getBean("messageCacheManager");
		XtcsService xtcsService=(XtcsService)SpringContextHolder.getBean("xtcsServiceImpl");
		Xtcs xtcs=xtcsService.get(Xtcs.class, "CmpnyCode");
		if("01".indexOf(xtcs.getQz())==-1) return;
		String title = String.valueOf(messageCacheManager.get("phone.jpush.reportTitle"));
		List<Map<String, Object>> list = personMessageService.getProjectManReportList();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Notice notice = new Notice();
				notice.setId(String.valueOf(map.get("phone")));
				notice.setTitle(title);
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("id", notice.getId());
				AndroidNotification androidNotification = AndroidNotification
						.newBuilder().setAlert(notice.getTitle())
						.setTitle(notice.getTitle()).addExtras(extras).build();
				IosNotification iosNotification = IosNotification.newBuilder()
						.setAlert(notice.getTitle()).setSound("default")
						.setBadge(0).addExtras(extras).build();

				Notification notification = Notification.newBuilder()
						.addPlatformNotification(androidNotification)
						.addPlatformNotification(iosNotification).build();
				try {
					JPushClient jpushClient=null;
					if("3".equals(map.get("rcvType"))){
						jpushClient = JpushClientWraper
								.getJpushClient(JpushClientWraper.employeeAppKey,JpushClientWraper.employeeMasterSecret);
					}else{
						jpushClient = JpushClientWraper
								.getJpushClient();
					}
					PushPayload payload = PushPayload.newBuilder()
							.setPlatform(Platform.all())
							.setAudience(Audience.alias(notice.getId()))
							 .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                      //  .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                     //   .setTimeToLive(86400)
	                        .build())
							.setNotification(notification).build();
					PushResult result = jpushClient.sendPush(payload);
					//System.out.println(result.toString());
					
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}finally{
					//更新推送标志
					//personMessageService.updateEmployeePushList(notice.getId());
				}
			}

		}

	}
	
	public static void doPushWorkerMessageList() {
		WorkerMessageService workerMessageService =(WorkerMessageService) SpringContextHolder
				.getBean("workerMessageServiceImpl");
		MessageCacheManager messageCacheManager = (MessageCacheManager) SpringContextHolder
				.getBean("messageCacheManager");
		String title = String.valueOf(messageCacheManager.get("phone.jpush.title"));
		List<Map<String, Object>> list = workerMessageService.getWorkerMessagePushList();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Notice notice = new Notice();
				notice.setId(String.valueOf(map.get("Phone")));
				notice.setTitle(MessageFormat.format(title, String.valueOf(map.get("msgnum"))));
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("id", notice.getId());
				AndroidNotification androidNotification = AndroidNotification
						.newBuilder().setAlert(notice.getTitle())
						.setTitle(notice.getTitle()).addExtras(extras).build();
				IosNotification iosNotification = IosNotification.newBuilder()
						.setAlert(notice.getTitle()).setSound("default")
						.setBadge(0).addExtras(extras).build();

				Notification notification = Notification.newBuilder()
						.addPlatformNotification(androidNotification)
						.addPlatformNotification(iosNotification).build();
				try {
					JPushClient jpushClient=null;
						jpushClient = JpushClientWraper
								.getJpushClient(JpushClientWraper.workerAppKey,JpushClientWraper.workerMasterSecret);
					
					PushPayload payload = PushPayload.newBuilder()
							.setPlatform(Platform.all())
							.setAudience(Audience.alias(notice.getId()))
							 .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                      //  .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                     //   .setTimeToLive(86400)
	                        .build())
							.setNotification(notification).build();
					PushResult result = jpushClient.sendPush(payload);
					//System.out.println(result.toString());
					
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}finally{
					//更新推送标志
					workerMessageService.updateWorkerMessageStatus(notice.getId());
				}
			}

		}

	}
	
	public static void doPushCustomerMessageList() {
		CustMessageService custMessageService =(CustMessageService) SpringContextHolder
				.getBean("custMessageServiceImpl");
		MessageCacheManager messageCacheManager = (MessageCacheManager) SpringContextHolder
				.getBean("messageCacheManager");
		String title = String.valueOf(messageCacheManager.get("phone.jpush.title"));
		List<Map<String, Object>> list = custMessageService.getCustomerMessagePushList();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Notice notice = new Notice();
				notice.setId(String.valueOf(map.get("Phone")));
				notice.setTitle(MessageFormat.format(title, String.valueOf(map.get("msgnum"))));
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("id", notice.getId());
				AndroidNotification androidNotification = AndroidNotification
						.newBuilder().setAlert(notice.getTitle())
						.setTitle(notice.getTitle()).addExtras(extras).build();
				IosNotification iosNotification = IosNotification.newBuilder()
						.setAlert(notice.getTitle()).setSound("default")
						.setBadge(0).addExtras(extras).build();

				Notification notification = Notification.newBuilder()
						.addPlatformNotification(androidNotification)
						.addPlatformNotification(iosNotification).build();
				try {
					JPushClient jpushClient=null;
						jpushClient = JpushClientWraper
								.getJpushClient(JpushClientWraper.customerAppKey,JpushClientWraper.customerMasterSecret);
					
					PushPayload payload = PushPayload.newBuilder()
							.setPlatform(Platform.all())
							.setAudience(Audience.alias(notice.getId()))
							 .setOptions(Options.newBuilder()
	                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
	                        .setApnsProduction(true)
	                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
	                      //  .setSendno(1)
	                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
	                     //   .setTimeToLive(86400)
	                        .build())
							.setNotification(notification).build();
					PushResult result = jpushClient.sendPush(payload);
					//System.out.println(result.toString());
					//更新推送标志

					custMessageService.updateCustomerMessagePushStatus(notice.getId());
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					if(1011 != e.getErrorCode()){
						e.printStackTrace();
					}
				}
			}

		}

	}
}
