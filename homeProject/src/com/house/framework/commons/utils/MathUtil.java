package com.house.framework.commons.utils;

import java.text.DecimalFormat;

/**
 * 
 * 功能说明:计算工具类
 * @author zzr
 *
 */
public class MathUtil {
	public static Double round(Double value,int digit){
		Double factor = 1.0;
		for(int i=0;i<digit;i++){
			factor *= 10;
		}
		return Math.round(value*factor)/factor;
	}
	/**
	 * 去除Double类型自带".0"问题,输出后最多保留四位小数
	 * @param value
	 * @return
	 */
	public static String stripTrailingZeros(Double value){
		DecimalFormat decimalFormat = new DecimalFormat("##################.####"); 
		return decimalFormat.format(value);
	}
}
