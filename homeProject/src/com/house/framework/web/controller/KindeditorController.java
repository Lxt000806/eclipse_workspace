package com.house.framework.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.conf.DictConstant;

@Controller
@RequestMapping("/admin/kindeditor")
public class KindeditorController {
	private static final Logger logger = LoggerFactory.getLogger(KindeditorController.class);
	private static final String PARAM_DIR = "dir";
	
	private HashMap<String, String> extMap = new HashMap<String, String>();
	
	public KindeditorController(){
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}
	
	@RequestMapping("/kindeditor_demo")
	public ModelAndView kindeditorDemo(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("widget/kindeditor_demo");
	}
	
	@RequestMapping("/upload_json")
	public void uploadJson(HttpServletRequest request, HttpServletResponse response) {
		
		String uploadRoot = DictCacheUtil.getItemValue(DictConstant.ABSTRACT_DICT_UPLOAD_URL, DictConstant.DICT_KINDEDITOR_UPLOAD_URL);
		
		//????????????????????????
		String savePath = request.getSession().getServletContext().getRealPath("/") + uploadRoot;
		savePath = savePath.replaceAll("\\\\", "/").replaceAll("//", "/");

		//??????????????????URL
		String saveUrl  = request.getContextPath() + File.separator + uploadRoot;
		
		if(!ServletFileUpload.isMultipartContent(request)){
			flushResult(request, response, getError("??????????????????") );
			return;
		}
		
		//????????????
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			flushResult(request, response, getError("????????????????????????") );
			return;
		}
		
		//?????????????????????
		if(!uploadDir.canWrite()){
			flushResult(request, response, getError("??????????????????????????????") );
			return;
		}
		
		//????????????????????????, ?????????????????????
		String dirName = request.getParameter(PARAM_DIR);
		if(!extMap.containsKey(dirName)){
			flushResult(request, response, getError("????????????????????????") );
			return;
		}
		
//		//???????????????
//		savePath += dirName + File.separator;
//		saveUrl += dirName + File.separator;
		
		uploadFile(request, response, savePath, saveUrl);
	}
	
	@RequestMapping("/file_manager_json")
	@SuppressWarnings("unchecked")
	public void fileManagerJson(HttpServletRequest request, HttpServletResponse response){
		String uploadRoot = DictCacheUtil.getItemValue(DictConstant.ABSTRACT_DICT_UPLOAD_URL, DictConstant.DICT_KINDEDITOR_UPLOAD_URL );
		
		//????????????????????????
		String rootPath = request.getSession().getServletContext().getRealPath("/") + uploadRoot;
		rootPath = rootPath.replaceAll("\\\\", "/").replaceAll("//", "/");

		//??????????????????URL
		String rootUrl  = request.getContextPath() + "/" + uploadRoot;
		rootUrl = rootUrl.replaceAll("//", "/");
		
		//???????????????
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		
		//??????????????????
		String dirName = request.getParameter(PARAM_DIR);
		if (dirName != null) {
			if(!Arrays.<String>asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
				flushResult(request, response, "Invalid Directory name.");
				return;
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		
		//??????path???????????????????????????URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";//???????????????
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		
		//???????????????name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";
		
		//???????????????..????????????????????????
		if (path.indexOf("..") >= 0) {
			flushResult(request, response, "Access is not allowed.");
			return;
		}
		//????????????????????????/
		if (!"".equals(path) && !path.endsWith("/")) {
			flushResult(request, response, "Parameter is not valid.");
			return;
		}
		
		//??????????????????????????????
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			flushResult(request, response, "Directory does not exist.");
			return;
		}
		
		//??????????????????????????????
		List<Hashtable<String, ?>> fileList = new ArrayList<Hashtable<String, ?>>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		
		response.setContentType("application/json; charset=UTF-8");
		flushResult(request, response, result.toJSONString());
	}
	


	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param savePath
	 * @param saveUrl
	 */
	private void uploadFile(HttpServletRequest request, HttpServletResponse response, String savePath, String saveUrl) {
		// ?????????MultipartHttpRequest???
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		// ?????????????????????????????????????????????, ??????kindeditor ????????????, ?????????????????????,??????for????????????
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile file = multipartRequest.getFile(key);
			saveFile(request, response, file, savePath, saveUrl);
		}
	}

	@SuppressWarnings("unused")
	private void saveFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String savePath, String saveUrl) {
		if (file == null || file.isEmpty())return;
		
		String uploadRoot = DictCacheUtil.getItemValue(DictConstant.ABSTRACT_DICT_UPLOAD_URL, DictConstant.DICT_KINDEDITOR_UPLOAD_URL);
		
		//???????????????????????????
		String ymd = DateFormatUtils.format(new Date(), "yyyyMMdd");
		savePath += ymd + File.separator;
		saveUrl += ymd + File.separator;
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		
		//????????????????????????
		String dirName = request.getParameter(PARAM_DIR);
		if (dirName == null) {
			dirName = "image";
		}
		
		//???????????????
		String fileName = file.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			flushResult(request, response, getError("????????????????????????????????????????????????\n?????????" + extMap.get(dirName) + "?????????") );
			return;
		}
		
		//????????????
		String newFileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "_" + new Random().nextInt(1000) + "." + fileExt;
		
		saveUrl = saveUrl.replaceAll("\\\\", "/").replaceAll("//", "/");
		try{
			File uploadedFile = new File(savePath, newFileName);
			file.transferTo(uploadedFile);
			flushResult(request, response, getSuccess(saveUrl, newFileName));
		}catch(Exception e){
			e.printStackTrace();
			flushResult(request, response, getError("?????????????????????") );
		}
	}
	
	
	/**
	 * ????????????????????????????????????????????????ajax??????
	 * @param response
	 * @param str
	 */
	private void flushResult(HttpServletRequest request, HttpServletResponse response, String str){
		setDisableCacheHeader(response);
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.debug("??????????????????: [ {} ]",  str);
			
			out.print(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close();
			}
		}
	}
	
	/**
	 * ??????????????????????????????Header.
	 */
	private void setDisableCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}
	
	/**
	 * ????????????????????????
	 * @param message
	 * @return
	 */
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
	/**
	 * ?????????????????????
	 * @param saveUrl
	 * @param newFileName
	 * @return
	 */
	private String getSuccess(String saveUrl, String newFileName) {
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url",saveUrl+newFileName);
		return obj.toJSONString();
	}

	
}

@SuppressWarnings("rawtypes")
class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
		}
	}
}

@SuppressWarnings({ "rawtypes" })
class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
				return 1;
			} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

@SuppressWarnings("rawtypes")
class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
		}
	}
}
