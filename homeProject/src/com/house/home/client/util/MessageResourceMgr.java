package com.house.home.client.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 *功能说明:消息资源管理类
 *
 */
public final class MessageResourceMgr {

    private static Logger logger = LoggerFactory.getLogger(MessageResourceMgr.class);

    private static Properties propertie = new Properties();

    public static final String LIMIT = "||";

    private static String DEFAULT_MESSAGE;

    static {
        try {
            DEFAULT_MESSAGE = new String("未知提示信息。".getBytes(), "ISO8859-1");
            propertie = PropertiesLoaderUtils.loadProperties(
                    new PathMatchingResourcePatternResolver().getResource("classpath:/conf/message_resources_zh.properties"));
        } catch (Exception e) {
            String s = "Load resource file failed.";
            logger.error(s);
        }
    }

    public static String getResource(String key) {
        String message = propertie.getProperty(key, DEFAULT_MESSAGE);
        try {
            message = new String(message.getBytes("ISO8859-1"), "GB2312");
        } catch (UnsupportedEncodingException e) {
            return DEFAULT_MESSAGE;
        }
        return message;
    }

}
