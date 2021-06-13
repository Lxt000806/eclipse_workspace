package com.house.framework.commons.utils;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class WxAesUtils {
	public static String decrypt(String encryptedData, String sessionKey, String iv, String encodingFormat) throws Exception {
        	byte[] encrypData = Base64.decode(encryptedData);
        	byte[] ivData = Base64.decode(iv);
        	byte[] sessionkey = Base64.decode(sessionKey);
        	String str="";
        	try {
        		str = doDecrypt(sessionkey,ivData,encrypData);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	return str;
    }
	
	public static String doDecrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		return new String(cipher.doFinal(encData),"UTF-8");
	}
}
