package com.house.framework.commons.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;

/**
 *提示信息缓存
 */
@Component
public class MessageCacheManager extends SimpleCacheManager{
	protected static final Logger logger = LoggerFactory.getLogger(MessageCacheManager.class);
	
	
	public String getCacheKey() {
		return CommonConstant.CACHE_MESSAGE_KEY;
	}

	
	public void initCacheData() {
		logger.info("###### 初始化提示信息缓存器开始 ######");
		InputStream is = null;
	    try {
	    	Properties props = new Properties();
	    	is = MessageCacheManager.class.getResourceAsStream("/conf/message.properties");
	        props.load(is);
	        for (Object obj : props.keySet()){
	        	this.put(obj.toString(), new String(props.getProperty(obj.toString()).getBytes("ISO-8859-1"),"utf-8"));
	        }
	        is.close();
	    }catch (Exception e) {
	    	logger.error(e.getMessage());
	    }finally{
	    	try {
	    		if (is!=null){
	    			is.close();
	    		}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
	    }
		logger.info("###### 初始化提示信息缓存器结束 ######");
	}
	
}
