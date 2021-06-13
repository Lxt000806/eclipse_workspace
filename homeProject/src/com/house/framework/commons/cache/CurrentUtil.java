package com.house.framework.commons.cache;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Description: 实用工具类
 */
@SuppressWarnings("all")
public class CurrentUtil {

	private static char charList[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' };

	private CurrentUtil() {
	}

	/**
	 * 产生数字随机码
	 * 
	 * @param digit
	 *            要产生随机码的位数,大于0的正整数
	 * @return 数字随机码
	 * @throws IllegalArgumentException
	 *             digit为0或负数时会抛出异常
	 */
	public static String makeRandom(int digit)
			throws java.lang.IllegalArgumentException {

		if (digit <= 0)
			throw new IllegalArgumentException();
		String code = "";
		int r = 0;
		Random random = new Random();
		for (int i = 1; i <= digit; i++) {
			r = random.nextInt(10);
			code += r;
		}
		return code;
	}

	/**
	 * 产生数字随机码
	 * 
	 * @param min
	 *            要产生随机码的最小值,大于0的正整数
	 * 
	 * @param max
	 *            要产生随机码的最大值,大于0的正整数
	 * @return 数字随机码
	 * @throws IllegalArgumentException
	 *             digit为0或负数时会抛出异常
	 */
	public static String makeRandom(int min, int digit)
			throws java.lang.IllegalArgumentException {
		if (digit <= 0)
			throw new IllegalArgumentException();
		Random random = new Random();
		return String.valueOf(random.nextInt(digit));
	}

	/**
	 * 数值字符串转换为数值类型的值
	 * 
	 * @param value
	 * @param clazz
	 *            类型为下列之一 java.lang.Byte java.lang.Double java.lang.Float
	 *            java.lang.Integer java.lang.Long java.lang.Short
	 * @return <T extends Number>
	 */
	public static <T extends Number> T numberFormat(String value, Class<T> clazz) {
		if (value == null || clazz == null)
			return null;
		try {
			if (clazz.getName().equals(Integer.class.getName())) {
				return (T) Integer.valueOf(value);
			} else if (clazz.getName().equals(Long.class.getName())) {
				return (T) Long.valueOf(value);
			} else if (clazz.getName().equals(Float.class.getName())) {
				return (T) Float.valueOf(value);
			} else if (clazz.getName().equals(Double.class.getName())) {
				return (T) Double.valueOf(value);
			} else if (clazz.getName().equals(Long.class.getName())) {
				return (T) Short.valueOf(value);
			} else if (clazz.getName().equals(Byte.class.getName())) {
				return (T) Byte.valueOf(value);
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
		return null;
	}

	public static String encodeMD5(String arg0) {

		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(arg0.getBytes("UTF8"));
			String result = "";
			byte[] temp;
			temp = md5.digest("".getBytes("UTF8"));
			for (int i = 0; i < temp.length; i++) {
				result += Integer.toHexString(
						(0x000000ff & temp[i]) | 0xffffff00).substring(6);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		
	}
}
