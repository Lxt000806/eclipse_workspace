package com.house.home.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.oss.PrjProgPhotoUpload;
import com.house.framework.commons.utils.oss.SignInPhotoUpload;
import com.house.framework.commons.utils.oss.WorkSignInPhotoUpload;

public class WorkSignPhotoQuartz {
	private static Logger logger = LoggerFactory.getLogger(WorkSignPhotoQuartz.class);
	
	public void execute(){
		try{
			logger.debug("工人签到图片定时任务开始");
			Long start = System.currentTimeMillis();
			String isSendYunValue = SystemConfig.getProperty("value","","isSendYun");
			if("1".equals(isSendYunValue)){
				WorkSignInPhotoUpload.doUploadtoOss();
			}
			Long end  = System.currentTimeMillis();
			logger.debug("工人签到图片定时任务结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
