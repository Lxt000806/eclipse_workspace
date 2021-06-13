package com.house.framework.commons.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import com.house.framework.commons.utils.ServletUtils;

public class ZipUtil {
	
	// 文件打包下载
	public static HttpServletResponse downLoadFiles(String path,String zipName,List<File> files,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			/**
			 * 创建一个临时压缩文件， 我们会把文件流全部注入到这个文件中 这里的文件你可以自定义是.rar还是.zip
			 * 压缩完、下载压缩后的文件后，该临时压缩文件会删除
			 */
			File file = new File(path+"/"+zipName);
			if (!file.exists()) {
				file.createNewFile();
			}
			response.reset();
			// 创建文件输出流
			FileOutputStream fous = new FileOutputStream(file);
			/**
			 * 打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下
			 */
			// org.apache.tools.zip.ZipOutputStream zipOut
			// = new org.apache.tools.zip.ZipOutputStream(fous);
			ZipOutputStream zipOut = new ZipOutputStream(fous);
			/**
			 * 这个方法接受的就是一个所要打包文件的集合， 还有一个ZipOutputStream
			 */
			zipFile(files, zipOut);
			zipOut.close();
			fous.close();
			return downloadZip(request,zipName.substring(0,zipName.lastIndexOf(".")),file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	// 文件打包下载
	public static HttpServletResponse downLoadFiles(List<String> files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			if (files==null || files.size()==0){
				return response;
			}
			String zipName = DateUtil.DateToString(new Date(),"yyyyMMdd")+".rar";
			
			File file = new File("d:/"+zipName);
			if (!file.exists()) {
				file.createNewFile();
			}
			response.reset();
			// 创建文件输出流
			FileOutputStream fous = new FileOutputStream(file);
			ZipOutputStream zipOut = new ZipOutputStream(fous);
			List<File> fileList = new ArrayList<File>();
			for (String str : files){
				File f = new File(str);
				fileList.add(f);
			}
			zipFile(fileList, zipOut);
			zipOut.close();
			fous.close();
			return downloadZip(request,zipName.substring(0,zipName.lastIndexOf(".")),file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 把接受的全部文件打成压缩包
	 * 
	 * @param List
	 *            <File>;
	 * @param org
	 *            .apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(List<File> files, ZipOutputStream outputStream) {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile("",file, outputStream);
		}
	}

	public static HttpServletResponse downloadZip(HttpServletRequest request,String fileName,File file,
			HttpServletResponse response) {
		ServletUtils.downLoadWithName(request, response, file.getPath(), fileName);
		if(file.exists()){
			file.delete();
		}
		
		return response;
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 * 
	 * @param File
	 * @param org
	 *            .apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(String basePath,File inputFile, ZipOutputStream ouputStream) {
		ouputStream.setEncoding("UTF-8");
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream in = new FileInputStream(inputFile);
					BufferedInputStream bins = new BufferedInputStream(in, 512);
					// org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(basePath+inputFile.getName());
					entry.setUnixMode(644);
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					in.close();
				} else {
					/**压缩目录，特别要注意相对目录**/
					basePath += inputFile.getName()+"/";
					ZipEntry entry = new ZipEntry(basePath);
					entry.setUnixMode(755);
					ouputStream.putNextEntry(entry);
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(basePath,files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void copyFile(String oldPath, String newPath) { 
	       try { 
	           int byteread = 0; 
	           File oldfile = new File(oldPath); 
	           if (oldfile.exists()) { //文件存在时 
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close();
	               fs.close();
	           } 
	       } catch (Exception e) { 
	           e.printStackTrace(); 
	       } 

	   } 
}
