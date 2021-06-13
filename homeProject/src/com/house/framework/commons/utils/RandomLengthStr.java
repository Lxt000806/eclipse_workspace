package com.house.framework.commons.utils;

public class RandomLengthStr {

	public final static String str = "0123456789abcdefghijklmnopqrstuvwxzyABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("mtToken:"+getRandomLengthStr(10));
		System.out.println("mtEncodingAesKey:"+getRandomLengthStr(WeiXinCryptUtils.mtEncodingAesKey.length()));
	}
	
	public static String getRandomLengthStr(int length){
		StringBuilder randomLengthStr = new StringBuilder();
		
		for(int i = 0; i < length; i++){
			int index = (int) Math.floor(Math.random() * str.length());
			randomLengthStr.append(str.charAt(index));
		}
		
		return randomLengthStr.toString();
	}
	
	
}
