package com.house.home.client.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class FormatTransfer {

	/**
	 * 默认的字符集编码 UTF-8 一个汉字占三个字节
	 */
	private static String CHAR_ENCODE = "UTF-8";

	/**
	 * 将 int转为低字节在前，高字节在后的byte数组
	 * 
	 * @param n
	 *            int
	 * @return byte[]
	 */
	public static byte[] toLH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 * 将 int转为高字节在前，低字节在后的byte数组
	 * 
	 * @param n
	 *            int
	 * @return byte[]
	 */
	public static byte[] toHH(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0xff);
		b[2] = (byte) (n >> 8 & 0xff);
		b[1] = (byte) (n >> 16 & 0xff);
		b[0] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 * 将 short转为低字节在前，高字节在后的byte数组
	 * 
	 * @param n
	 *            short
	 * @return byte[]
	 */
	public static byte[] toLH(short n) {
		byte[] b = new byte[2];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		return b;
	}

	/**
	 * 将 short转为高字节在前，低字节在后的byte数组
	 * 
	 * @param n
	 *            short
	 * @return byte[]
	 */
	public static byte[] toHH(short n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) (n >> 8 & 0xff);
		return b;
	}

	/**
	 * 将 float转为低字节在前，高字节在后的byte数组
	 */
	public static byte[] toLH(float f) {
		return toLH(Float.floatToRawIntBits(f));
	}

	/**
	 * 将 float转为高字节在前，低字节在后的byte数组
	 */
	public static byte[] toHH(float f) {
		return toHH(Float.floatToRawIntBits(f));
	}

	/**
	 * 将 String转为byte数组
	 */
	public static byte[] stringToBytes(String s, int length) {
		while (s.getBytes().length < length) {
			s += " ";
		}
		return s.getBytes();
	}

	/**
	 * 将高字节数组转换为int
	 * 
	 * @param b
	 *            byte[]
	 * @return int
	 */
	public static int hBytesToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i] >= 0) {
				s = s + b[i];
			} else {
				s = s + 256 + b[i];
			}
			s = s * 256;
		}
		if (b[3] >= 0) {
			s = s + b[3];
		} else {
			s = s + 256 + b[3];
		}
		return s;
	}

	/**
	 * 将低字节数组转换为int
	 * 
	 * @param b
	 *            byte[]
	 * @return int
	 */
	public static int lBytesToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[3 - i] >= 0) {
				s = s + b[3 - i];
			} else {
				s = s + 256 + b[3 - i];
			}
			s = s * 256;
		}
		if (b[0] >= 0) {
			s = s + b[0];
		} else {
			s = s + 256 + b[0];
		}
		return s;
	}

	/**
	 * 高字节数组到short的转换
	 * 
	 * @param b
	 *            byte[]
	 * @return short
	 */
	public static short hBytesToShort(byte[] b) {
		int s = 0;
		if (b[0] >= 0) {
			s = s + b[0];
		} else {
			s = s + 256 + b[0];
		}
		s = s * 256;
		if (b[1] >= 0) {
			s = s + b[1];
		} else {
			s = s + 256 + b[1];
		}
		short result = (short) s;
		return result;
	}

	/**
	 * 低字节数组到short的转换
	 * 
	 * @param b
	 *            byte[]
	 * @return short
	 */
	public static short lBytesToShort(byte[] b) {
		int s = 0;
		if (b[1] >= 0) {
			s = s + b[1];
		} else {
			s = s + 256 + b[1];
		}
		s = s * 256;
		if (b[0] >= 0) {
			s = s + b[0];
		} else {
			s = s + 256 + b[0];
		}
		short result = (short) s;
		return result;
	}

	/**
	 * 高字节数组转换为float
	 * 
	 * @param b
	 *            byte[]
	 * @return float
	 */
	@SuppressWarnings("static-access")
	public static float hBytesToFloat(byte[] b) {
		int i = 0;
		Float F = new Float(0.0f);
		i = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
				| (b[3] & 0xff);
		return F.intBitsToFloat(i);
	}

	/**
	 * 低字节数组转换为float
	 * 
	 * @param b
	 *            byte[]
	 * @return float
	 */
	@SuppressWarnings("static-access")
	public static float lBytesToFloat(byte[] b) {
		int i = 0;
		Float F = new Float(0.0f);
		i = ((((b[3] & 0xff) << 8 | (b[2] & 0xff)) << 8) | (b[1] & 0xff)) << 8
				| (b[0] & 0xff);
		return F.intBitsToFloat(i);
	}

	/**
	 * 将 byte数组中的元素倒序排列
	 */
	public static byte[] bytesReverseOrder(byte[] b) {
		int length = b.length;
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[length - i - 1] = b[i];
		}
		return result;
	}

	/**
	 * 打印byte数组
	 */
	public static void printBytes(byte[] bb) {
		int length = bb.length;
		for (int i = 0; i < length; i++) {
			System.out.print(Arrays.toString(bb) + " ");
		}
		System.out.println("");
	}

	public static void logBytes(byte[] bb) {
		int length = bb.length;
		String out = "";
		for (int i = 0; i < length; i++) {
			out = out + Arrays.toString(bb) + " ";
		}

	}

	/**
	 * 将 int类型的值转换为字节序颠倒过来对应的int值
	 * 
	 * @param i
	 *            int
	 * @return int
	 */
	public static int reverseInt(int i) {
		int result = FormatTransfer.hBytesToInt(FormatTransfer.toLH(i));
		return result;
	}

	/**
	 * 将 short类型的值转换为字节序颠倒过来对应的short值
	 * 
	 * @param s
	 *            short
	 * @return short
	 */
	public static short reverseShort(short s) {
		short result = FormatTransfer.hBytesToShort(FormatTransfer.toLH(s));
		return result;
	}

	/**
	 * 将 float类型的值转换为字节序颠倒过来对应的float值
	 * 
	 * @param f
	 *            float
	 * @return float
	 */
	public static float reverseFloat(float f) {
		float result = FormatTransfer.hBytesToFloat(FormatTransfer.toLH(f));
		return result;
	}

	/**
	 * 设置全局的字符编码
	 * 
	 * @param charEncode
	 */
	public static void configCharEncode(String charEncode) {
		CHAR_ENCODE = charEncode;
	}

	/**
	 * @param str
	 *            源字符串转换成字节数组的字符串
	 * @return
	 */
	public static byte[] StringToByte(String str) {
		return StringToByte(str, CHAR_ENCODE);
	}

	/**
	 * 
	 * @param srcObj
	 *            源字节数组转换成String的字节数组
	 * @return
	 */
	public static String ByteToString(byte[] srcObj) {
		return ByteToString(srcObj, CHAR_ENCODE);
	}

	/**
	 * UTF-8 一个汉字占三个字节
	 * 
	 * @param str
	 *            源字符串 转换成字节数组的字符串
	 * @return
	 */
	public static byte[] StringToByte(String str, String charEncode) {
		byte[] destObj = null;
		try {
			if (null == str || str.trim().equals("")) {
				destObj = new byte[0];
				return destObj;
			} else {
				destObj = str.getBytes(charEncode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj;
	}

	/**
	 * @param srcObj
	 *            源字节数组转换成String的字节数组
	 * @return
	 */
	public static String ByteToString(byte[] srcObj, String charEncode) {
		String destObj = null;
		try {
			destObj = new String(srcObj, charEncode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj==null?null:destObj.replaceAll("\0", " ");
	}
}
