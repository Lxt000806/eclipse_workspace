package com.house.home.quartz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.oss.ItemPreAppPhotoUpload;
import com.house.framework.commons.utils.oss.ItemSendPhotoUpload;
import com.house.framework.commons.utils.oss.PrjJobPhotoUpload;
import com.house.framework.commons.utils.oss.WorkerProblemPhotoUpload;

public class SyncPhotoQuartz {
	private static Logger logger = LoggerFactory.getLogger(SyncPhotoQuartz.class);
	
	public void execute(){
		try{
			logger.debug("图片同步定时任务开始");
			Long start = System.currentTimeMillis();
			String isSendYunValue = SystemConfig.getProperty("value","","isSendYun");
			
			if("1".equals(isSendYunValue)){
				int taskIndex = 1;
				while(true){
					switch (taskIndex) {
						case 1:ItemSendPhotoUpload.doUploadtoOss();break;
						case 2:PrjJobPhotoUpload.doUploadtoOss();break;
						case 3:WorkerProblemPhotoUpload.doUploadtoOss();break;
						case 4:ItemPreAppPhotoUpload.doUploadtoOss();break;
						default:taskIndex = 0;break;
					}
					if(taskIndex == 0){
						break;
					}
					taskIndex++;
				}
			}
			Long end  = System.currentTimeMillis();
			logger.debug("图片同步图片定时任务结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
