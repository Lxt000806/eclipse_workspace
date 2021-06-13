package com.house.home.client.util;

/**
 *功能说明:TOKEN申请验证加密解密接口。
 */
public interface ITokenEncryptor {
	/**
	 * 解密 以经过BASE64加密的String密文输入,String明文输出
	 * 
	 * @param strMi 经过密钥加密后再经过BASE64加密后的密文。
	 * @param secretKey 加密密钥
	 * @return 当密文与密钥均为空（null或者空字符串时）返回null值。
	 */
	public String getDesString(String strMi, String secretKey);

	/**
	 * 加密String明文输入,String密文输出,该密文为BASE64加密后的密文 。
	 * 
	 * @param strMing 明文
	 * @param secretKey 加密密钥
	 * @return 
	 *         当明文与密钥均为空（null或者空字符串时）返回null值，反之返回经过BASE64加密且经过URLEncode对特殊字符串过行编码后的密文
	 *         。
	 */
	public String getEncString(String strMing, String secretKey);
}
