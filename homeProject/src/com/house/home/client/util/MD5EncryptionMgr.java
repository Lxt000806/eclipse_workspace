package com.house.home.client.util;

import java.security.MessageDigest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 *功能说明:md5加密
 *
 */
public class MD5EncryptionMgr {
	
	private static final Log logger = LogFactory.getLog(MD5EncryptionMgr.class);
	
    private MD5EncryptionMgr() {
    }


    public static String md5Encryption(String pwd) {
        String resultPwd = "";

        try {

            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes("UTF8"));
            byte s[] = md.digest();

            for (int i = 0; i < s.length; i++) {
                resultPwd += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
        }catch (Exception e) {
        	logger.error("加密异常!");
		}
        return resultPwd;
    }
}
