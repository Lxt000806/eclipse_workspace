package com.house.home.client.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 
 *功能说明:返回结果对象
 *
 */
public final class ReturnInfo {

    private static Properties properties;

    private static String DEFAULT_MESSAGE = "unknown info.";

    static {

        String className = ReturnInfo.class.getName();
        String prefix = className.substring(0, className.lastIndexOf("."));
        prefix = prefix.replace(".", "/");
        String path = prefix + "/return_info.properties";

        try {
            properties = Resources.getResourceAsProperties(path);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private ReturnInfo() {
    }

    public static String getReturnInfo(String returnCode) {
        return getResource(returnCode);
    }

    public static String getResource(String key) {
        String message = properties.getProperty(key, DEFAULT_MESSAGE);
        try {
            message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return DEFAULT_MESSAGE;
        }
        return message;
    }

}
