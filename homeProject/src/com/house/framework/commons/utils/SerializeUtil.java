package com.house.framework.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具类
 */
public class SerializeUtil {
	
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	@SuppressWarnings("finally")
	public static byte[] serialize(Object object){
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		byte[] bytes = null;
		try{
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			bytes = byteArrayOutputStream.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			return bytes;
		}
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Object unSerialize(byte[] bytes){
		ByteArrayInputStream byteArrayInputStream = null;
		Object object = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			object = objectInputStream.readObject();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			return object;
		}
	}
}
