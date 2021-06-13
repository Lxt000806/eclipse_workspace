package com.house.home.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.oss.PrjProgPhotoUpload;

public class PrjProgPhotoQuartz {
	private static Logger logger = LoggerFactory.getLogger(PrjProgPhotoQuartz.class);
	
	public void execute(){
		try{
			logger.debug("工地图片定时任务开始");
			Long start = System.currentTimeMillis();
			String isSendYunValue = SystemConfig.getProperty("value","","isSendYun");
			
			if("1".equals(isSendYunValue)){
				PrjProgPhotoUpload.setPrjConfirmPhotoIsPushCust();
				PrjProgPhotoUpload.setPrjProgConfirmIsPushCust();
				PrjProgPhotoUpload.doUploadtoOss();
			}
			Long end  = System.currentTimeMillis();
			logger.debug("工地图片定时任务结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
