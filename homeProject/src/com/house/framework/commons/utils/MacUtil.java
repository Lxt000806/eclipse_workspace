package com.house.framework.commons.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacUtil {
	
	/**
     * 根据IP地址获取mac地址
     * @param ipAddress 127.0.0.1
     * @return
     * @throws SocketException
     * @throws UnknownHostException
     */
	public static String getLocalMac(String ipAddress) throws SocketException,
	UnknownHostException {
	    String str = "";
	    String macAddress = "";
	    final String LOOPBACK_ADDRESS = "127.0.0.1";
	    // 如果为127.0.0.1,则获取本地MAC地址。
	    if (LOOPBACK_ADDRESS.equals(ipAddress)) {
	        InetAddress inetAddress = InetAddress.getLocalHost();
	        byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
	            .getHardwareAddress();
	        // 下面代码是把mac地址拼装成String
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	            if (i != 0) {
	                sb.append("-");
	            }
	            // mac[i] & 0xFF 是为了把byte转化为正整数
	            String s = Integer.toHexString(mac[i] & 0xFF);
	            sb.append(s.length() == 1 ? 0 + s : s);
	        }
	        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
	        macAddress = sb.toString().trim().toUpperCase();
	        return macAddress;
	    } else {
	        // 获取非本地IP的MAC地址
	        try {
	            System.out.println(ipAddress);
	            Process p = Runtime.getRuntime()
	                .exec("nbtstat -A " + ipAddress);
	            InputStreamReader ir = new InputStreamReader(p.getInputStream());
	
	            BufferedReader br = new BufferedReader(ir);
	
	            while ((str = br.readLine()) != null) {
	                if(str.indexOf("MAC")>1){
	                    macAddress = str.substring(str.indexOf("MAC")+9, str.length());
	                    macAddress = macAddress.trim();
	                    break;
	                }
	            }
	            p.destroy();
	            br.close();
	            ir.close();
	        } catch (IOException ex) {
	        	
	        }
	        return macAddress;
	    }
	}


}
