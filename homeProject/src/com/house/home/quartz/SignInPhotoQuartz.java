package com.house.home.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.oss.PrjProgPhotoUpload;
import com.house.framework.commons.utils.oss.SignInPhotoUpload;

public class SignInPhotoQuartz {
	private static Logger logger = LoggerFactory.getLogger(SignInPhotoQuartz.class);
	
	public void execute(){
		try{
			logger.debug("签到图片定时任务开始");
			Long start = System.currentTimeMillis();
			String isSendYunValue = SystemConfig.getProperty("value","","isSendYun");
			
			if("1".equals(isSendYunValue)){
				SignInPhotoUpload.doUploadtoOss();
			}
			Long end  = System.currentTimeMillis();
			logger.debug("签到图片定时任务结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
