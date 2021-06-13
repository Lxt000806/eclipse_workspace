package com.house.framework.commons.utils.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DownLoadFiles;

public class OssManager {
	/**
	 * @param OssConfigure
	 * @param file
	 * @param remotePath 文件路径
	 * @param newFileName 新文件名，包括扩展名
	 * @throws Exception
	 *             String
	 * @Des:上传OSS服务器文件
	 */
	public static String uploadFile(File file, String remotePath, String newFileName) {
		InputStream fileContent = null;
		OSSClient ossClient = null;
		String returnUrl = "";
		try{
			String fileName = file.getName();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String contentType = getContentType(suffix);
			fileContent = new FileInputStream(file);
			ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
			String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";
			//System.err.println("remoteFilePath:" + remoteFilePath);
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(fileContent.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentEncoding("utf-8");
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			if (StringUtils.isNotBlank(newFileName)){
				fileName = newFileName;
			}
			// 上传文件
			PutObjectResult putObject = ossClient.putObject(
					OssConfigure.bucketName, remoteFilePath + fileName,
					fileContent, objectMetadata);
	
	/*		System.err.println("putObjectgetETag:" + putObject.getETag());
			System.err.println("putObjectgetRequestId:" + putObject.getRequestId());
			System.err.println("putObjectgetClientCRC:" + putObject.getClientCRC());
			System.err.println("putObjectgetServerCRC:" + putObject.getServerCRC());
			*/
			returnUrl = OssConfigure.accessUrl + "/" + remoteFilePath + fileName;
		}catch (OSSException oe) {
		    System.err.println("Caught an OSSException, which means your request made it to OSS, "
		            + "but was rejected with an error response for some reason.");
		    System.err.println("Error Message: " + oe.getErrorCode());
		    System.err.println("Error Code:       " + oe.getErrorCode());
		    System.err.println("Request ID:      " + oe.getRequestId());
		    System.err.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
		    System.err.println("Caught an ClientException, which means the client encountered "
		            + "a serious internal problem while trying to communicate with OSS, "
		            + "such as not being able to access the network.");
		    System.err.println("Error Message: " + ce.getMessage());
		} catch (Exception e){
			e.printStackTrace();
		} finally {
		    if (ossClient != null) {
				ossClient.shutdown();
		    }
		}
		
		return returnUrl;
	}
	
	
	/**
	 * @param OssConfigure
	 * @param file
	 * @param remotePath 文件路径
	 * @param newFileName 新文件名，包括扩展名
	 * @throws Exception
	 *             String
	 * @Des:上传OSS服务器文件 多文件
	 */
	public static String uploadFiles(List<File> fileList)
			throws Exception {
		InputStream fileContent = null;
		OSSClient ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
		for(int i=0;i<fileList.size();i++){
			String fileName = fileList.get(i).getName();
		/*	System.out.println(fileName);*/
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			String contentType = getContentType(suffix);
			fileContent = new FileInputStream(fileList.get(i));
/*			System.out.println(fileList.get(i).getParent());
			System.out.println(fileList.get(i).getParent().split(fileName)[0]);*/
			String remoteFilePath = fileList.get(i).getParent().split(fileName)[0].substring(0, fileList.get(i)
					.getParent().split(fileName)[0].length()).replaceAll("\\\\", "/")+ "/";
	/*		System.out.println(remoteFilePath+"\n\n");*/
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(fileContent.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentEncoding("utf-8");
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putObject = ossClient.putObject(
					OssConfigure.bucketName, remoteFilePath + fileName,
					fileContent, objectMetadata);
		}
		ossClient.shutdown();
		
		return "";//OssConfigure.accessUrl + "/" + remoteFilePath + fileName;
	}
	

	/**
	 * @param OssConfigure
	 * @param filePath
	 *            void
	 * @Des:根据key删除OSS服务器上的文件
	 */
	public static void deleteFile(String filePath) throws Exception{
		OSSClient ossClient = null;
		try{
			ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
			boolean fileExists = ossClient.doesObjectExist(OssConfigure.bucketName, filePath);
			if(fileExists){
				ossClient.deleteObject(OssConfigure.bucketName, filePath);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}
	
	public static void deleteFiles(List<File> fileList) throws Exception{
		OSSClient ossClient = null;
		try{
			ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
			for(int i=0;i<fileList.size();i++){
				boolean fileExists = ossClient.doesObjectExist(OssConfigure.bucketName,fileList.get(i).getPath().replaceAll("\\\\", "/"));
			/*	System.out.println(fileList.get(i).getPath().replaceAll("\\\\", "/"));*/
				if(fileExists){
					ossClient.deleteObject(OssConfigure.bucketName, fileList.get(i).getPath().replaceAll("\\\\", "/"));
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}

	/**
	 * @param FilenameExtension
	 * @return String
	 * @Des:判断OSS服务文件上传时文件的contentType
	 */
	public static String getContentType(String FilenameExtension) {
		if (FilenameExtension.equals(".mp3") || FilenameExtension.equals(".m4a") || FilenameExtension.equals(".amr")) {
			return "audio/*";
		}
		if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equals(".JPEG")
				|| FilenameExtension.equals(".jpeg")
				|| FilenameExtension.equals(".JPG")
				|| FilenameExtension.equals(".jpg")
				|| FilenameExtension.equals(".PNG")
				|| FilenameExtension.equals(".png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equals(".HTML")
				|| FilenameExtension.equals(".html")) {
			return "text/html";
		}
		if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equals(".PPTX")
				|| FilenameExtension.equals(".pptx")
				|| FilenameExtension.equals(".PPT")
				|| FilenameExtension.equals(".ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equals(".DOCX")
				|| FilenameExtension.equals(".docx")
				|| FilenameExtension.equals(".DOC")
				|| FilenameExtension.equals(".doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
			return "text/xml";
		}
		return "text/html";
	}
	
	/**
	 * 从Oss获取图片url
	 * @param fileName
	 * @param remotePath
	 * @return
	 */
	public static String getRemoteFileUrl(String fileName, String remotePath){
		String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";
		String result = OssConfigure.accessUrl + "/" + remoteFilePath + fileName;
		return result;
	}
	
	/**
	 * 下载到本地
	 * @param fileUrl
	 * @param fileName
	 * @param downloadPath
	 */
	public static void downloadFile(String fileUrl, String fileName, String downloadPath){
		DownLoadFiles.saveUrlAs(fileUrl, downloadPath + fileName);
	}
	
	/**
	 * 
	 * 上传文件流到oss
	 * @param inputStream 文件流
	 * @param fileName 文件名
	 * @param remotePath oss存储路径
	 * @param newFileName oss存储文件名
	 * @throws Exception
	 */
	public static void uploadFile(InputStream inputStream, String fileName, String remotePath, String newFileName) throws Exception {
		OSSClient ossClient = null;
		try{
			String suffix = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀名
			String contentType = getContentType(suffix);
			ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
			String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/");
			if (!remoteFilePath.endsWith("/")) {
				remoteFilePath += "/";
			}
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(inputStream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentEncoding("utf-8");
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putObject = ossClient.putObject(OssConfigure.bucketName, remoteFilePath + fileName, inputStream, objectMetadata);
			
			/*System.err.println("putObjectgetETag:" + putObject.getETag());
			System.err.println("putObjectgetRequestId:" + putObject.getRequestId());
			System.err.println("putObjectgetClientCRC:" + putObject.getClientCRC());
			System.err.println("putObjectgetServerCRC:" + putObject.getServerCRC());*/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				inputStream.close();
			}
			if(ossClient != null){
				ossClient.shutdown();
			}
		}
	}
	
	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public static Map<String, Object> getFilesByDir(String remotePath, String marker, int pageSize){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List fileList = new ArrayList<Map<String,Object>>();
		OSSClient ossClient = null;
		int maxKeys = pageSize;
		if(StringUtils.isBlank(marker)){
			maxKeys++;
		}
		try{

			ossClient = new OSSClient(OssConfigure.endpoint, OssConfigure.accessKeyId, OssConfigure.accessKeySecret);
			
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
			listObjectsRequest.setBucketName(OssConfigure.bucketName);
			listObjectsRequest.setPrefix(remotePath);
			listObjectsRequest.setMaxKeys(maxKeys);
			listObjectsRequest.setMarker(marker);
		
			ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
			
			List list = objectListing.getObjectSummaries();
			for(int i = 0;i < list.size();i++){
				if(maxKeys != pageSize && i == 0){
					continue;
				}
				OSSObjectSummary ossObjectSummary = (OSSObjectSummary) list.get(i);
				fileList.add(ossObjectSummary.getKey().replace(remotePath, ""));
			}
			returnMap.put("marker", objectListing.getNextMarker());
			returnMap.put("hasNext", objectListing.isTruncated());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ossClient != null){
				ossClient.shutdown();
			}
			returnMap.put("fileList", fileList);
			return returnMap;
		}
	}
}
