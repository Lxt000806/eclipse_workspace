package com.house.framework.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * 日常工具类针对String字符串
 *   
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		return null == str || str.length() < 1;
	}

	public static boolean isEmptyTrim(String str) {
		return null == str || str.trim().length() < 1;
	}

	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 移除 最后一个mark
	 * @param orig 源对象
	 * @param mark
	 * @return
	 */
	public static String removeLast(String orig, String mark) {
		return removeLastMark(new StringBuilder(orig), mark).toString();
	}

	/**
	 * 移除 最后一个mark
	 * @param builder 源对象
	 * @param mark 
	 * @return
	 */
	public static StringBuilder removeLastMark(StringBuilder builder, String mark) {
		if (notEmpty(builder) && notEmpty(mark)) {
			int begin = builder.lastIndexOf(mark);
			if (begin > 0) {
				builder.delete(begin, begin + mark.length());
			}
		}
		return builder;
	}

	public static boolean notEmpty(StringBuilder str) {
		return !(null == str || str.length() < 1);
	}

	/**
	 * 将集合 转换为 按指定mark 连接的字符串 如：aaa,bbb,ccc,ddd 形式
	 * 当 field 不为空时  取field值进行连接 
	 * @param <T>
	 * @param list
	 * @param mark 连接标记
	 * @param field 连接数据 对应的T类型的属性名
	 * @param builder 连接的builder对象
	 * @return
	 */
	public static <T> StringBuilder collectionJoin(Collection<T> list, String mark, String field, StringBuilder builder) {
		if (null != list && !list.isEmpty()) {
			builder = null != builder ? builder : new StringBuilder();
			for (T t : list) {
				if (!String.class.isInstance(t)) {
					if (!isEmpty(field)) {
						Object obj = Reflections.invokeGetterMethod(t, field);
						if (null != obj) {
							String temp = String.valueOf(obj);
							builder.append(temp);
							builder.append(mark);
						}
					}
				} else {
					builder.append(t.toString());
					builder.append(mark);
				}
			}
			removeLastMark(builder, mark);
			return builder;
		}
		return null;
	}

	/**
	 * 将集合 转换为 按默认“,” 连接的字符串 如：aaa,bbb,ccc,ddd 形式
	 * 当 field 不为空时  取field值进行连接 
	 * @param <T>
	 * @param list
	 * @param field 连接数据 对应的T类型的属性名
	 * @param builder 连接的builder对象
	 * @return
	 */
	public static <T> StringBuilder collectionJoin(Collection<T> list, String field, StringBuilder... builder) {
		return collectionJoin(list, ",", field, builder.length > 0 ? builder[0] : null);
	}

	/**
	 * 将字符串集合 转换为 按默认“,” 连接的字符串 如：aaa,bbb,ccc,ddd 形式
	 * @param list
	 * @param builder 连接的builder对象
	 * @return
	 */
	public static StringBuilder collectionJoin(Collection<String> list, StringBuilder... builder) {
		return collectionJoin(list, ",", null, builder.length > 0 ? builder[0] : null);
	}

	/**
	 * 消除fck 传入后台文本内容 为html时，附加的空格。
	 * @param orig
	 * @return
	 */
	public static String filterBlankOutTag(String orig) {
		if (notEmpty(orig)) {
			Matcher mat = Pattern.compile("> +<").matcher(orig);
			while (mat.find()) {
				String res = mat.group();
				orig = orig.replaceFirst(res, res.replace(" ", ""));
			}
		}
		return orig;
	}

	/**
	 * 第一个字母转换成大写
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str) {
		String first = String.valueOf(str.charAt(0));
		return str.replaceFirst(first, first.toUpperCase());
	}

	
	/**
	 * 第一个字母转换成小写
	 * @param str
	 * @return
	 */
	public static String lowerFirstLetter(String str) {
		String first = String.valueOf(str.charAt(0));
		return str.replaceFirst(first, first.toLowerCase());
	}
	
	/**
	 * 生成包含字母与数字的随机字符串
	 * @param length 要生成的目标长度
	 * 
	 * 
	 * @return
	 */
	public static String randomString(int length) {
		int aVal = (int) new Character('A').charValue();
		String[] arr = new String[36];
		for (int i = 0; i < 26; i++) {
			arr[i] = new String(new char[] { (char) (aVal + i) });
		}
		for (int i = 0; i < 10; i++) {
			arr[i + 26] = new String(i + "");
		}

		Random r = new Random();
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < length; i++) {
			res.append(arr[r.nextInt(36)]);
		}

		return res.toString();
	}
	
	/**
	 * 向前填充字符串
	 * @param str 原字符串
	 * @param key 填充的字符
	 * @param length 填充后的字符串长度
	 * @return 
	 */
	public static String cramStringWithKeyAtBefore(String str,String key,int length){
		
		if(str==null||str.length()>=length){
			return str;
		}
		
		int strLength = str.length();
		String beforeStr = "";
		for(int i=0;i<length-strLength;i++){
			beforeStr+=key;
		}
		return beforeStr+str;
	}
    /**
     * 根据分隔符分割字符串
     *
     * @param str
     * @param delim
     * @return String[] 分割后的数组
     */
    public static String[] splitString(String str, String delim) {
        if (str == null || delim == null) return new String[0];

        java.util.StringTokenizer st = new StringTokenizer(str, delim);
        List<String> list = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
	
	/**
	 * @param strs
	 *        待转化字符串
	 * @return
	 * @author estone
	 * @description 下划线格式字符串转换成驼峰格式字符串
	 *              eg: player_id -> playerId;<br>
	 *              player_name -> playerName;
	 */
	public static String underScore2CamelCase(String strs) {
		String[] elems = strs.split("_");
		for ( int i = 0 ; i < elems.length ; i++ ) {
			elems[i] = elems[i].toLowerCase();
			if (i != 0) {
				String elem = elems[i];
				char first = elem.toCharArray()[0];
				elems[i] = "" + (char) (first - 32) + elem.substring(1);
			}
		}
		for ( String e : elems ) {
			System.out.print(e);
		}
		return Arrays.toString(elems);
	}

	/**
	 * @param param
	 *        待转换字符串
	 * @return
	 * @author estone
	 * @description 驼峰格式字符串 转换成 下划线格式字符串
	 *              eg: playerId -> player_id;<br>
	 *              playerName -> player_name;
	 */
	public static String camelCase2Underscore(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i,mc.end() + i,"_" + mc.group().toLowerCase());
			i++;
		}
		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String queryStringToJsonString(String queryString){
		String jsonString = "";
		if (StringUtils.isNotBlank(queryString)){
			try{
				String[] arr = queryString.split("&");
				Map map = new HashMap();
				for (String str:arr){
					String ss[] = str.split("=");
					if (ss.length==2){
						map.put(ss[0], ss[1]);
					}else{
						map.put(ss[0], "");
					}
				}
				jsonString = JSONObject.fromObject(map).toString();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return jsonString;
	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static JSONObject queryStringToJSONObject(String queryString){
//		JSONObject jsonObject = new JSONObject();
//		if (StringUtils.isNotBlank(queryString)){
//			try{
////				queryString = "id=1&name=kkk";
//				String[] arr = queryString.split("&");
//				Map map = new HashMap();
//				for (String str:arr){
//					String ss[] = str.split("=");
//					if (ss.length==2){
//						map.put(ss[0], ss[1]);
//						jsonObject.put(ss[0], ss[1]);
//					}else{
//						map.put(ss[0], null);
//						jsonObject.put(ss[0], null);
//					}
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//		return jsonObject;
//	}
	
	/**
	 * 防止中文乱码
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JSONObject queryStringToJSONObject(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)){
			try{
				String[] arr = queryString.split("&");
				Map map = new HashMap();
				for (String str:arr){
					String ss[] = str.split("=");
					if (ss.length==2){
						map.put(ss[0], request.getParameter(ss[0]));
						jsonObject.put(ss[0], request.getParameter(ss[0]));
					}else{
						map.put(ss[0], null);
						jsonObject.put(ss[0], null);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return jsonObject;
	}
	
	public static String formatQueryString(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		String queryString = request.getQueryString();
		String result = "";
		if (StringUtils.isNotBlank(queryString)){
			try{
				String[] arr = queryString.split("&");
				for (String str:arr){
					String ss[] = str.split("=");
					if (ss.length==2){
						sb.append(ss[0]).append("=").append(request.getParameter(ss[0])).append("&");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (sb.length()>0 && sb.lastIndexOf("&")==sb.length()-1){
			result = sb.substring(0, sb.length()-1);
		}else{
			result = sb.toString();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(queryStringToJsonString("a"));
	}
	/**
	 * 产生并返回N位随机数
	 */
	public static  String  getRandomNum(int num){
		String res="";
		for(int i=0;i<num;i++){
			res+=new  Random().nextInt(10);
		}
		return res;
	}
	/**
	 * sha1加密
	 * @param decript
	 * @return
	 */
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
