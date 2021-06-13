package com.house.framework.commons.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import java.util.regex.Pattern;

public class IpUtils {

	public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

	/**
	 * 返回用户请求ip
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIpAddr(HttpServletRequest request) throws UnknownHostException{
		String ip = request.getHeader("x-forwarded-for");
		
		if(StringUtils.isNotBlank(request.getHeader("X-Real-IP"))){
			ip = "127.0.0.1".equals(request.getHeader("X-Real-IP"))? InetAddress.getLocalHost().getHostAddress()
					:request.getHeader("X-Real-IP");
		} else if("0:0:0:0:0:0:0:1".equals(request.getRemoteAddr())){
			ip = InetAddress.getLocalHost().getHostAddress();
		} else {
			ip = request.getRemoteAddr();
		}
		
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		
		return ip;
	}
	
	/**
	 * 请求的ip是否为内网ip
	 * @param ip
	 * @return
	 * @throws UnknownHostException
	 */
	public static boolean isInnerIp(String ip) throws UnknownHostException{
		
		boolean result = false;
		
		// ip是内网地址 或者 是本机地址
        if(InetAddress.getByName(ip).isSiteLocalAddress() 
        		|| InetAddress.getByName(ip).isLoopbackAddress()){
        	result = true;
        }
		
        return result;
	}
	
	 /**
     * 获取String类型真实ip地址，基于反向代理。
     *
     * @param request
     * @return
     * 在反向代理中将X-Forward-For替换为remote_addr，即，真实的IP地址。
     */
	public static String getIpFromRequest(HttpServletRequest request) {
        String ip;
        boolean found = false;
        if ((ip = request.getHeader("x-forwarded-for")) != null) {
            StrTokenizer tokenizer = new StrTokenizer(ip, ",");
            while (tokenizer.hasNext()) {
                ip = tokenizer.nextToken().trim();
                if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
                    found = true;
                    break;
                }
           }
       }
       if (!found) {
           ip = request.getRemoteAddr();// 获得ip地址
       }
        return ip;
    }
	
	/**
     * @param ip
     * @return boolean
     */
	public static boolean isIPv4Private(String ip) {
       long longIp = ipV4ToLong(ip);
       return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
                || (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
                || longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
    }
	
	public static boolean isIPv4Valid(String ip) {
        return pattern.matcher(ip).matches();
    }
	
	/**
     * Long类型ip转为String类型
     *
     * @param longIp
     * @return Long
     */
	public static long ipV4ToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
                + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }
	
	 /**
     * String类型ip转为Long类型
     *
     * @param longIp
     * @return String
     */
    public static String longToIpV4(long longIp) {
        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) ((longIp) % 256);
        return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
   }
    
    public static void main(String[] args) {
    	System.out.println(ipV4ToLong("192.168.0.64"));
    }
}
