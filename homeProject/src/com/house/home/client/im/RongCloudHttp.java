package com.house.home.client.im;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.house.framework.commons.utils.StringUtil;

public class RongCloudHttp {
	public static String GetRongCloudToken(String url,String appKey,String appSecret, String username) {  
        StringBuffer res = new StringBuffer();  
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。  
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。  
        String Signature = StringUtil.SHA1(appSecret + Nonce + Timestamp);//数据签名。  
      
        HttpClient httpClient = new DefaultHttpClient();  
        HttpPost httpPost = new HttpPost(url);  
        httpPost.setHeader("App-Key", appKey);  
        httpPost.setHeader("Timestamp", Timestamp);  
        httpPost.setHeader("Nonce", Nonce);  
        httpPost.setHeader("Signature", Signature);  
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");  
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);  
        nameValuePair.add(new BasicNameValuePair("userId",username));  
        HttpResponse httpResponse = null;  
        try {  
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));  
            httpResponse = httpClient.execute(httpPost);  
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));  
            String line = null;  
            while ((line = br.readLine()) != null) {  
                res.append(line);  
            }  
        } catch (IOException e) {  
        	httpPost.abort();
            e.printStackTrace();  
        }  
        return res.toString();  
    }  
}
