package com.house.framework.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.SystemConfig;

@Controller
@RequestMapping("/admin/fileUpload")
public class FileUploadController extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	/**
	 * 上传文件保存路径，示例：D:/homePhoto/
	 */
	private static final String UPLOAD_DIR = SystemConfig.getProperty("uploadDir", "", "fileUpload");
	
	/**
	 * 删除文件备份保存路径，示例：D:/homePhotoDelete/
	 */
	private static final String DELETE_FILE_DIR = SystemConfig.getProperty("deleteFileDir", "", "fileUpload");
	
	@RequestMapping("/upload")
	@ResponseBody
    public Result upload(HttpServletRequest request, HttpServletResponse response){

		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			
			String fullName = ""; // 文件全名（路径+文件名），由客户端传值
			
			List<?> fileList = upload.parseRequest(request);
			Iterator<?> it = fileList.iterator();
			while (it.hasNext()) {
				FileItem obit = (FileItem) it.next();
				
				if (obit.isFormField() && "fullName".equals(obit.getFieldName())) { // 获取文件全名
					fullName = obit.getString("UTF-8");
				}
				
				if (obit instanceof DiskFileItem) { // 如果是多媒体
					DiskFileItem item = (DiskFileItem) obit;
					BufferedInputStream in = null; 
					BufferedOutputStream out = null;
					try {
						in = new BufferedInputStream(item.getInputStream()); 
						
						File file = new File(UPLOAD_DIR + fullName);
						file.getParentFile().mkdirs();
						
						out = new BufferedOutputStream(new FileOutputStream(file)); 
						Streams.copy(in, out, true); // 把文件写到指定的上传文件夹
					} finally {
						if (in != null) in.close();
						if (out != null) out.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAIL;
		}

        return Result.SUCCESS;
    }
	
	@RequestMapping("/deleteFile")
	@ResponseBody
    public Result deleteFile(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("fullName") String fullName){
		
		try {
			File srcFile = new File(UPLOAD_DIR + fullName);
			File destFile = new File(DELETE_FILE_DIR + fullName);
			FileUtils.copyFile(srcFile, destFile);
			FileUtils.deleteQuietly(srcFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAIL;	
		} 
		
		return Result.SUCCESS;
    }
	
}
