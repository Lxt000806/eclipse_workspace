package com.house.framework.commons.utils;

/**
 * 银联开放平台签名算法
 */
public class UnionPaySignUtil {

	/**
	 * 签名算法
	 * 
	 * @param body
	 *            报文体
	 * @param ts
	 *            时间戳
	 * @param secret
	 *            密钥
	 * @return
	 */
	public static String sign(String body, String ts, String secret) {
		String sign = UnionPayEncodeUtils.encodeBySHA256(secret + body + ts);

		return sign;
	}
}
