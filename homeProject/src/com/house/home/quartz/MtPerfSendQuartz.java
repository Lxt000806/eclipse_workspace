package com.house.home.quartz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.oss.ItemPreAppPhotoUpload;
import com.house.framework.commons.utils.oss.ItemSendPhotoUpload;
import com.house.framework.commons.utils.oss.PrjJobPhotoUpload;
import com.house.framework.commons.utils.oss.WorkerProblemPhotoUpload;

public class MtPerfSendQuartz {
	private static Logger logger = LoggerFactory.getLogger(MtPerfSendQuartz.class);
	
	public void execute(){
		try{
			logger.debug("麦田业绩发送定时任务开始");
			Long start = System.currentTimeMillis();
			MtPerfSend.doSendMt();
			Long end  = System.currentTimeMillis();
			logger.debug("麦田业绩发送定时任务结束，用时："+(end - start)+"ms");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
