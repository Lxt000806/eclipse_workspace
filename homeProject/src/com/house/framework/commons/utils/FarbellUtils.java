package com.house.framework.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.home.bean.basic.FarbellBean;
import net.farbell.sdk.trudiansdk.TDCloudApp;
import net.farbell.sdk.trudiansdk.TDCloudResult;
import net.farbell.sdk.trudiansdk.constants.QrType;

/**
 * 泛达门禁系统
 */
public class FarbellUtils {
	
	private static Logger logger = LoggerFactory.getLogger(FarbellUtils.class);
	
	/**
	 * 泛达门禁sdk
	 */
	private static TDCloudApp app = new TDCloudApp(FarbellBean.APP_ID, 
			FarbellBean.SECRET_KEY, FarbellBean.API_URL);
	
	/**
	 * 生成开门二维码
	 * @param qrId 二维码ID，规则：共9位，第1位代表员工（1）/客户（2）、2-3位代表公司编码、4-9位代表员工号/客户号（不足前面补0）
	 * @param durationTime 有效期持续时间（以秒为单位）
	 * @return
	 */
	public static String qrcode(int qrId, long durationTime) {
		
		// 有效期起始的时间戳（10位时间戳）
		long startTime = System.currentTimeMillis() / 1000;
		
		// 传入有效时间小于等于0，取默认的有效时间
		if (durationTime <= 0) durationTime = FarbellBean.DURATION_TIME;
		// 有效期结束的时间戳（10位时间戳）
		long endTime = startTime + durationTime;
		
		// 调用sdk的生成二维码功能
		TDCloudResult result = app.qrcode(startTime, endTime, QrType.MANY, qrId, null,
				FarbellBean.AC_NUM);
		
		String qrcode = "";
		if (result != null && result.getCode() == FarbellBean.SUCCESS_CODE) {
			qrcode = result.getData().get("qrcode");
		} else {
			String msg = "无法生成二维码。二维码ID：" + qrId + "；有效时间：" + durationTime + "秒。";
			if (result != null) {
				msg = msg + "错误码：" + result.getCode() + "；错误信息：" + result.getMsg();
			}
			logger.error(msg);
		}
		
		return qrcode;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis() / 1000;
		// 有效期结束的时间戳（10位时间戳）
		long endTime = startTime + FarbellBean.DURATION_TIME;
		TDCloudResult result = app.qrcode(startTime, endTime, QrType.MANY, 1, null,
				FarbellBean.AC_NUM);
		System.out.println(result.getCode() + "  " + result.getMsg());
	}
}
