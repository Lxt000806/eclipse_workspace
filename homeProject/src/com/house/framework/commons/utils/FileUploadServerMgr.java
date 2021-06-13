package com.house.framework.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.house.framework.commons.cache.DictCacheUtil;

/**
 * 
 *功能说明:文件路径配置帮助类
 *
 */
public class FileUploadServerMgr {

	private static final Log logger = LogFactory.getLog(FileUploadServerMgr.class);
	
	private FileUploadServerMgr() {
	}

	/**
	 * 
	 * Describe: 用于上传文件：获取上传公共路径(自动识别linux系统和windows系统) 用途: 上传路径 例: windows:
	 * //192.168.2.101/uploadrootdir linux : /mnt/uploadrootdir
	 *@return String
	 *
	 */
	@SuppressWarnings("unused")
	public static String getUploadRootDir() {
		
		// 上传根目录  /uploadrootdir
		String uploadPath = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_ROOT_DIR");
		// 服务器IP   192.168.2.101
		String uploadIP = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_IP_DIR");
		
		uploadPath = uploadPath.replace("/", "");
		String uploadRootDir = "";
		String rootPath = uploadPath != null && !"".equals(uploadPath) ? uploadPath : "";
		// linux
		if (File.separator.equals("/")) {
			rootPath = uploadPath != null && !"".equals(uploadPath.trim()) ? uploadPath : null;

			if (rootPath != null)
				if(uploadIP.indexOf(":")!=-1){
					uploadRootDir = uploadIP + "/" + rootPath;
				}else{
					uploadRootDir = "/" + uploadIP + "/" + rootPath;
				}				
			else
				uploadRootDir = "/" + uploadIP;
			// window
		} else {
			rootPath = "/" + (uploadPath != null && !"".equals(uploadPath.trim()) ? uploadPath : null);
			if (rootPath != null)
				if(uploadIP.indexOf(":")!=-1){
					uploadRootDir = uploadIP + rootPath;
				}else{
					uploadRootDir = "//" + uploadIP + rootPath;
				}				
			else
				uploadRootDir = "//" + uploadIP;
		}

		logger.debug("uploadRootDir : " + uploadRootDir);
		return uploadRootDir;
	}

	/**
	 * 
	 * Describe: 用于页面显示: 获取公共URL 用途: 显示公共连接 例: windows: /uploadpath linux :
	 * http://192.168.2.101:8067
	 *@return String
	 *
	 */
	public static String getUploadRootUrl() {
		// 虚拟路径  /uploadpath
		String uploadpathUrl = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_ROOT_URL");
		// 服务器IP  192.168.2.101
		String uploadIP = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_IP_URL");
		// 协议  http
		String uploadProtocol = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_PROTOCOL_URL");
		// 服务器端口号  80
		String uploadPort = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_PORT_URL");
		
		if(uploadpathUrl != null){
			uploadpathUrl = uploadpathUrl.replace("/", "");
		}
		
		// linux
		if (File.separator.equals("/")) {
			return uploadProtocol + "://" + uploadIP + ":" + uploadPort;
			// window
		} else {
			return uploadProtocol + "://" + uploadIP + ":" + uploadPort;

		}
	}
	/**
	 * 
	 *功能说明:获取指定模块存放的文件服务器共享的主目录,只能用于显示时使用 如：http://192.168.2.101:80/uploadpath
	 *@param moduleName
	 *@return String
	 *
	 */
	public static String getUploadPath(String moduleName) {
		return FileUploadServerMgr.getUploadRootUrl();
	}
	/**
	 * 
	 *功能说明:存放上传文件的临时目录,只能用于上传文件时使用 如：//192.168.2.101/uploadrootdir/temp
	 *@return String
	 *
	 */
	public static String getUploadFileTempDir() {
		// 临时目录  /temp
		String tempDir = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_TEMP_DIR");
		String uploadTempDir = "";
		if (tempDir.startsWith("/"))
			uploadTempDir = getUploadRootDir() + tempDir;
		else
			uploadTempDir = getUploadRootDir() + "/" + tempDir;
		return uploadTempDir;
	}


   /**
    * 
    *功能说明: 获取上传文件的临时目录,只能用于显示时使用
     *         如：http://192.168.2.101:80/uploadpath/temp
    *@return String
    *
    */
    public static String getUserFileTempDir() {
    	// 临时目录  /temp
		String tempDir = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_TEMP_DIR");
        return getUploadPath("") + tempDir;
    }

	/**
	 * 
	 *功能说明:访问上传文件的临时目录的URL
	 *@return String
	 *
	 */
	public static String getUploadFileTempUrl() {
    	// 临时目录  /temp
		String tempUrl = DictCacheUtil.getItemValue("ABSTRACT_DICT_FILE_SERVER", "UPLOAD_TEMP_DIR");
		String uploadTempUrl = "";
		if (tempUrl.startsWith("/"))
			uploadTempUrl = getUploadRootUrl() + tempUrl;
		else
			uploadTempUrl = getUploadRootUrl() + "/" + tempUrl;
		return uploadTempUrl;
	}
	
	/**创建文件目录
	 * @param path
	 */
	public static void makeDir(String path) {
		if (StringUtils.isBlank(path)){
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**获取文件名
	 * @param part
	 * @return
	 */
	public static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
	
	/**写文件到指定路径
	 * @param part
	 * @param descr
	 * @param path
	 * @param list
	 */
	public static void writePart(Part part,String descr,String path,List<Map<String,String>> list){
		if (part==null || StringUtils.isBlank(path)){
			return;
		}
		String fileName = getSubmittedFileName(part);
//		InputStream fileContent = part.getInputStream();
		// 把文件写到指定路径
		makeDir(path);
		if (StringUtils.isNotBlank(fileName)) {
			Map<String,String> map = new HashMap<String,String>();
			Long currentTimeMillis = System.currentTimeMillis();
			String formatName = fileName
					.substring(fileName.lastIndexOf("."));//获取文件后缀名
			String fileNameNew = currentTimeMillis + formatName;
			try {
				part.write(path + fileNameNew);
				map.put("path", path+fileNameNew);
				map.put("descr", descr);
				list.add(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean copyFile(File source, String distPath){
		boolean result = true;
		try {
			logger.info(distPath);
			logger.info(distPath.replace("\\", "/"));
			File dist = new File(distPath.replace("\\", "/"));
			if(!dist.exists()){
				logger.info(dist.getParentFile().getAbsolutePath());
				dist.getParentFile().mkdirs();
				dist.createNewFile();
			}
			FileInputStream fileInputStream = new FileInputStream(source);
			FileOutputStream fileOutputStream = new FileOutputStream(dist);
			byte[] buff = new byte[1024];
			int bytesRead = 0;
			  
			while ((bytesRead = fileInputStream.read(buff)) != -1) {
				fileOutputStream.write(buff, 0, bytesRead);
			}
			fileInputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = false;
			logger.info(e.getMessage());
		}finally{
			return result;
		}
	}
}
