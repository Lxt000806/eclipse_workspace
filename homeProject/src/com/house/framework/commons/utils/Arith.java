package com.house.framework.commons.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java大数据精确计算
 * 只能用Double.toString，用Double.valueOf也会不精确
 */
public class Arith {
	/**
	 * 提供精确加法计算的add方法
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确减法运算的sub方法
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确乘法运算的mul方法
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的除法运算方法div
	 * 
	 * @param value1
	 *            被除数
	 * @param value2
	 *            除数
	 * @param scale
	 *            精确范围
	 * @return 两个参数的商
	 * @throws IllegalAccessException
	 */
	public static double div(double value1, double value2, int scale)
			throws IllegalAccessException {
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
			throw new IllegalAccessException("精确度不能小于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.divide(b2, scale).doubleValue();
	}
	
	/**
     * 判断是否是数字
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toPlainString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        if (str.startsWith("0") && !bigStr.startsWith("0")){//000111
        	return false;
        }
        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    
    /**
     * 字符串保留四位小数
     * @param str
     * @return
     */
    public static String strNumToStr(String str) {
        boolean flag = isNumeric(str);
        if (flag == true){
        	NumberFormat nf = NumberFormat.getInstance();
        	nf.setMaximumFractionDigits(4);
        	return nf.format(new BigDecimal(str)).replace(",", "");
        }else{
        	return str;
        }
    }
    
    public static void main(String[] args) {
		System.out.println(isNumeric("-0E-16"));
		System.out.println(isNumeric("-0.0111"));
		System.out.println(isNumeric("-00.0111"));
		System.out.println(isNumeric("-0110"));
		System.out.println(isNumeric("0110"));
		System.out.println(isNumeric("-100"));
		System.out.println(isNumeric("100"));
		System.out.println(isNumeric("1.222222222222222222222222222222"));
		System.out.println(isNumeric("kkkkkk3.40256010353E11kkkkkk"));
		System.out.println(isNumeric("我的1.22222"));
		System.out.println(isNumeric("3.40256010353E11"));
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(0.10000));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(-00.0111));
        System.out.println(nf.format(-0111));
        System.out.println(nf.format(-111));
	}
}
