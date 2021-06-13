package com.house.home.client.http;

import java.util.List;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.filters.StringInputStream;

public class HttpMethod {
	private static final Log logger = LogFactory.getLog(HttpMethod.class);
	
	//设置连接超时时间(单位毫秒)
	private static final int connectionTimeOut = 30000; 
	//设置读数据超时时间(单位毫秒)
	private static final int soTimeOut = 120000; 
	private HttpClient client;
	
	public HttpMethod(){
		client = new HttpClient();
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams(); 
	    // 设置连接超时时间(单位毫秒) 
	    managerParams.setConnectionTimeout(connectionTimeOut); 
	    // 设置读数据超时时间(单位毫秒) 
	    managerParams.setSoTimeout(soTimeOut);
	}
	
	public String post(String url, List<NameValuePair> nameValuePairs){
		return post(url, nameValuePairs, false);
	}

	/**
	 * post访问客户端
	 * 
	 *@param url
	 *@param nameValuePairs
	 *@return
	 *boolean
	 */
	public String post(String url, List<NameValuePair> nameValuePairs, boolean isContent){
		boolean r = true;
		//去除？后面的内容
		EncodePostMethod pMethod = new EncodePostMethod(url);
		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		pMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		if(nameValuePairs!=null && !nameValuePairs.isEmpty()){
			pMethod.setRequestBody(nameValuePairs.toArray(new NameValuePair[0]));
		}
		try {
			int statusCode = client.executeMethod(pMethod);
			r = HttpStatus.SC_OK==statusCode;
			if(r && isContent){
				byte[] byteMessage = pMethod.getResponseBody();
				String message = new String(byteMessage);
				logger.debug("responseContent is " + message);
				return message;
			}
		} catch (Throwable e) {
			logger.debug("post method error " + e.getMessage());
			return null;
		}finally{
			pMethod.releaseConnection();
		}
		return null;
	}
	
	/**
	 * 以XML请求相应的服务，并返回对应的XMl内容
	 * 
	 *@param url
	 *@param requestXML
	 *@return
	 *String
	 */
	public String post(String url, String requestXML){
		logger.debug("requestXML is " + requestXML);
		//去除？后面的内容
		EncodePostMethod pMethod = new EncodePostMethod(url);
		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		pMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		if(!StringUtils.isBlank(requestXML)){
			RequestEntity requestEntity = new InputStreamRequestEntity(new StringInputStream(requestXML,"utf-8"));
			pMethod.setRequestEntity(requestEntity);
		}
		try {
			int statusCode = client.executeMethod(pMethod);
			if(HttpStatus.SC_OK == statusCode){
				byte[] byteMessage = pMethod.getResponseBody();
				String responseXML = new String(byteMessage,"utf-8");
				logger.debug("responseXML is " + responseXML);
				// 设置请求
				return responseXML;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logger.debug("post method error " + e.getMessage());
		}finally{
			pMethod.releaseConnection();
		}
		return "";
	}
	
	/**
	 * 以XML请求相应的服务，并返回对应的XMl内容
	 * 
	 *@param url
	 *@param requestXML
	 *@param charset 字符集编码
	 *@return
	 *String
	 */
	public String post(String url, String requestXML,String charset){
		logger.debug("requestXML is " + requestXML);
		//去除？后面的内容
		EncodePostMethod pMethod = new EncodePostMethod(url);
		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		pMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		if(!StringUtils.isBlank(requestXML)){
			RequestEntity requestEntity = new InputStreamRequestEntity(new StringInputStream(requestXML,charset));
			pMethod.setRequestEntity(requestEntity);
		}
		try {
			int statusCode = client.executeMethod(pMethod);
			if(HttpStatus.SC_OK == statusCode){
				byte[] byteMessage = pMethod.getResponseBody();
				String responseXML = new String(byteMessage,charset);
				logger.debug("responseXML is " + responseXML);
				// 设置请求
				return responseXML;
			}
		} catch (Throwable e) {
			logger.debug("post method error " + e.getMessage());
		}finally{
			pMethod.releaseConnection();
		}
		return "";
	}

	/**
	 * 设置请求的头部信息和返回所有的请求参数
	 *
	 * @param request
	 * @return NameValuePair[]  
	 */
	public static NameValuePair setNameValuePair(String name, String value){
		if(StringUtils.isBlank(name)){
			return null;
		}
        return new NameValuePair(name, value);
	}
	
	/**
	 * 
	 *功能说明:发送get请求
	 *@param url
	 *@param requestXML
	 *@param charset
	 *@return String
	 */
	public String get(String url, String charset){
		GetMethod getMethod = new GetMethod(url);
		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = client.executeMethod(getMethod);
			if(HttpStatus.SC_OK == statusCode){
				byte[] byteMessage = getMethod.getResponseBody();
				String responseXML = new String(byteMessage,charset);
				logger.debug("responseXML is " + responseXML);
				// 设置请求
				return responseXML;
			}
		} catch (Throwable e) {
			logger.debug("get method error " + e.getMessage());
		}finally{
			getMethod.releaseConnection();
		}
		return "";
	}
}
