package com.house.home.client.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-2-16
 * Time: 14:33:57
 * To change this template use File | Settings | File Templates.
 */
/*
 字符串 DESede(3DES) 加密
 */
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TripleDES {

	private static final Logger log = Logger.getLogger(TripleDES.class);
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用

	// DES,DESede,Blowfish

	/**
	 * 加密String明文输入,String密文输出,该密文为BASE64加密后的密文 。
	 * 
	 * @param strMing
	 *            明文
	 * @param secretKey
	 *            密钥
	 * @return 经过3DES加密,经过Base64加密且经过URLEncode采用UTF-8编码后的密文.
	 */
	public static String getEncString(String strMing, String secretKey) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = null;
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = getEncryptBytes(secretKey.getBytes(), byteMing);
			strMi = base64en.encode(byteMi);
			strMi = URLEncoder.encode(strMi, "UTF-8");
		} catch (Exception e) {
			log.error("getEncString(String strMing, String secretKey)方法执异常!");
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以经过BASE64 String密文输入,String明文输出
	 * 
	 * @param strMi
	 *            密文
	 * @param secretKey
	 *            密钥
	 * @return
	 */
	public static String getDesString(String strMi, String secretKey) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = null;
		try {
			byteMi = base64De.decodeBuffer(URLDecoder.decode(strMi, "UTF-8"));
			byteMing = getDecryptBytes(secretKey.getBytes(), byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			log.error("getDesString(String strMi, String secretKey)方法执行异常!");
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	private static byte[] getEncryptBytes(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.lang.Exception e) {
			log.error("3DES加密异常!");
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	private static byte[] getDecryptBytes(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.lang.Exception e) {
			log.error("3DES解密异常!");
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(TripleDES.getEncString("portalPwd",
				"TcRlEiyjizMOfK4uTquKdQ5x"));
	}
}
