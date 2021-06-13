package com.house.framework.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

import com.house.framework.web.login.UserContext;

/**
 * redis工具类
 */
public class RedisUtil {
	
	/**
	 * 获取redis连接
	 */
	public static RedisConnection getRedisConnection() {
		RedisConnection redisConnection = null;
		try{
			JedisConnectionFactory jedisConnectionFactory = SpringContextHolder.getBean(JedisConnectionFactory.class);
			redisConnection = jedisConnectionFactory.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return redisConnection;
	}
	
	/**
	 * 保存key-value
	 */
	public static void set(RedisConnection redisConnection, String key, String value,Long time){
		try{
			boolean result = true;
			if(redisConnection != null && StringUtils.isNotBlank(key)){
				result = redisConnection.setNX(key.getBytes(), value.getBytes());
				if(!result){
					redisConnection.del(key.getBytes());
					result = redisConnection.setNX(key.getBytes(), value.getBytes());
				}
				if(result){
					if(time > 0){
						redisConnection.expire(key.getBytes(), time);
					}else{
						redisConnection.persist(key.getBytes());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(redisConnection != null){
				redisConnection.close();
			}
		}
	}
	
	/**
	 * 
	 * @param redisConnection
	 * @param key
	 * @param value
	 * @param time
	 */
	public static void set(RedisConnection redisConnection, String key, byte[] value,Long time){
		try{
			boolean result = true;
			if(redisConnection != null && StringUtils.isNotBlank(key)){
				result = redisConnection.setNX(key.getBytes(), value);
				if(!result){
					redisConnection.del(key.getBytes());
					result = redisConnection.setNX(key.getBytes(), value);
				}
				if(result){
					if(time > 0){
						redisConnection.expire(key.getBytes(), time);
					}else{
						redisConnection.persist(key.getBytes());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(redisConnection != null){
				redisConnection.close();
			}
		}
	}
	
	/**
	 * 获取key-value
	 */
	@SuppressWarnings("finally")
	public static byte[] get(RedisConnection redisConnection, String key){
		byte[] result = null;
		try{
			if(redisConnection != null && StringUtils.isNotBlank(key)){
				result = redisConnection.get(key.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(redisConnection != null){
				redisConnection.close();
			}
			return result;
		}
	}
	
	/**
	 * 删除key-value
	 */
	@SuppressWarnings("finally")
	public static boolean del(RedisConnection redisConnection, String key){
		boolean result = false;
		try{
			if(redisConnection != null && StringUtils.isNotBlank(key) && redisConnection.exists(key.getBytes())){
				redisConnection.del(key.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(redisConnection != null){
				redisConnection.close();
			}
			return result;
		}
	}
	
	/**
	 * 是否存在key-value
	 */
	@SuppressWarnings("finally")
	public static boolean exists(RedisConnection redisConnection, String key){
		boolean result = false;
		try{
			if(redisConnection != null && StringUtils.isNotBlank(key)){
				result = redisConnection.exists(key.getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(redisConnection != null){
				redisConnection.close();
			}
			return result;
		}
	}
	
	public static void main(String[] args){
		RedisConnection redisConnection = null;
		Jedis jedis = null;
		try{
			jedis = new Jedis("192.168.0.155", 6379, 1000, 1000);
			jedis.auth("a123456");
			redisConnection = new JedisConnection(jedis);
			redisConnection.select(6);
			Set<byte[]> keys = redisConnection.keys("*".getBytes());
			Iterator<byte[]> iterator = keys.iterator();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> custList = new ArrayList<Map<String, Object>>();
			int count = 0;
			int custCount = 0;
			while(iterator.hasNext()){
				byte[] key = iterator.next();
				byte[] value = redisConnection.get(key);
				UserContext userContext = (UserContext) SerializeUtil.unSerialize(value);
				if(StringUtils.isNotBlank(userContext.getZwxm())){
					count++;
					int i = 0;
					for(;i < list.size() && !list.get(i).get("zwxm").toString().equals(userContext.getZwxm());i++);
					if(i == list.size()){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("key", new String(key));
						map.put("zwxm", userContext.getZwxm());
						map.put("expired", redisConnection.pTtl(key));
						list.add(map);
					}
				}else if(StringUtils.isNotBlank(userContext.getPhone())){
					custCount++;
					int i = 0;
					for(;i < custList.size() && !custList.get(i).get("phone").toString().equals(userContext.getPhone());i++);
					if(i == custList.size()){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("key", new String(key));
						map.put("phone", userContext.getPhone());
						map.put("expired", redisConnection.pTtl(key));
						custList.add(map);
					}
				}
			}
			System.out.println("count: "+count);
			for(int i = 0;i < list.size(); i++){
				System.out.println(list.get(i));
			}
			System.out.println("list size:"+list.size());
			System.out.println("custCount: "+custCount);
			for(int i = 0;i < custList.size(); i++){
				System.out.println(custList.get(i));
			}
			System.out.println("custList size:"+custList.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(redisConnection != null){
				redisConnection.close();
			}
			if(jedis != null){
				jedis.close();
			}
		}
	}
	
}
