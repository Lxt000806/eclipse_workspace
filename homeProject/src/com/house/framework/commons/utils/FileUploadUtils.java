package com.house.framework.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.impl.CustDocUploadRule;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {
	
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
	
	/**
	 * 文件上传URL，示例：http://192.168.0.246:20003/admin/fileUpload/upload/
	 */
	public static final String UPLOAD_URL = SystemConfig.getProperty("uploadUrl", "", "fileUpload");

	/**
	 * 文件下载路径，示例：http://csoss.app.u-om.com/
	 */
	//public static final String DOWNLOAD_URL = SystemConfig.getProperty("downloadUrl", "", "fileUpload");
	public static final String DOWNLOAD_URL = OssConfigure.cdnAccessUrl + "/";
	
	/**
	 * 文件上传路径，示例：D:/homePhoto/
	 */
	public static final String UPLOAD_DIR = SystemConfig.getProperty("uploadDir", "", "fileUpload");
	
	/**
	 * 文件删除URL，示例：http://192.168.0.246:20003/admin/fileUpload/deleteFile/
	 */
	public static final String DELETE_FILE_URL = SystemConfig.getProperty("deleteFileUrl", "", "fileUpload");
	
	/**
	 * 文件上传
	 * @return
	 */
	public static Result upload(InputStream is,String fileName,String filePath) {
		
        try {
            // 创建HttpClient
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost(UPLOAD_URL);
//            
//            // 超时设置，单位：毫秒
//			RequestConfig requestConfig = RequestConfig.custom()
//					.setConnectTimeout(60000)
//					.setConnectionRequestTimeout(10000)  
//			        .setSocketTimeout(60000).build();  
//			httpPost.setConfig(requestConfig);  
//            
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.addTextBody("fullName", rule.getFullName(), 
//            		ContentType.create("text/plain", Charset.forName("UTF-8")));
//            
//            // 绑定文件参数，传入文件流和contenttype，此处也可以继续添加其他formdata参数
//            builder.addBinaryBody("file", is, ContentType.create("multipart/form-data", 
//            		Charset.forName("UTF-8")), rule.getOriginalName());
//            HttpEntity entity = builder.build();
//            httpPost.setEntity(entity);
//
//            // 执行提交
//            HttpResponse response = httpClient.execute(httpPost);
//            HttpEntity responseEntity = response.getEntity();
//            if(responseEntity != null){
//                // 将响应的内容转换成字符串
//                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
//                return JSONObject.parseObject(result, Result.class);
//            }
//        	
        	//改为上传到阿里云oss
        	OssManager.uploadFile(is, fileName,filePath, fileName);
            
            return Result.SUCCESS;
        } catch (Exception e){
            logger.error("文件上传异常，文件全名：{}，异常信息：\n{}", fileName, e.getMessage());
            return Result.FAIL;
        } finally {
        	if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	/**
	 * 下载文件
	 * @param fullName 文件全名（含路径）
	 * @return
	 */
	public static InputStream download(String fullName) {
		
		String url = DOWNLOAD_URL + fullName;
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			
			// 超时设置，单位：毫秒
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(60000)
					.setConnectionRequestTimeout(10000)  
			        .setSocketTimeout(60000).build();  
			httpGet.setConfig(requestConfig);
			
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			return entity.getContent();
		} catch (Exception e) {
			logger.error("文件下载异常，文件url：{}，异常信息：\n{}", url, e.getMessage());
		}
		
		return null;	
	}
	
	/**
	 * 获取上传文件的url地址
	 * @param request
	 * @param fullName 文件全名（含路径）
	 * @return
	 */
	public static String getFileUrl(String fullName) {
		return DOWNLOAD_URL + fullName;	
	}
	
	/**
	 * 删除图片
	 * @param request
	 * @param fullName 文件全名（含路径）
	 * @return
	 */
	public static Result deleteFile(String fullName){
		
		try {
			 // 创建HttpClient
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            
//            String url = DELETE_FILE_URL + "?fullName=" + fullName; 
//            HttpGet httpGet = new HttpGet(url);  
//            // 超时设置，单位：毫秒
//			RequestConfig requestConfig = RequestConfig.custom()
//					.setConnectTimeout(60000)
//					.setConnectionRequestTimeout(10000)  
//			        .setSocketTimeout(60000).build();
//			httpGet.setConfig(requestConfig); 
//			
//            // 执行提交
//            HttpResponse response = httpClient.execute(httpGet);
//            HttpEntity responseEntity = response.getEntity();
//            if(responseEntity != null){
//                // 将响应的内容转换成字符串
//                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
//                return JSONObject.parseObject(result, Result.class);
//            }
			OssManager.deleteFile(fullName);
            return Result.SUCCESS;
        } catch (Exception e){
            logger.error("文件删除异常，文件全名：{}，异常信息：\n{}", fullName);
            return Result.FAIL;
       
        }	
	}
	

	/**
	 * 获取上传文件的根目录
	 * @param request
	 * @return
	 */
	public static String getBaseFileUrl(HttpServletRequest request) {
		return PathUtil.getWebRootAddress(request) 
				+ UPLOAD_DIR.substring(UPLOAD_DIR.indexOf("/") + 1);	
	}
}
