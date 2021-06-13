package com.house.framework.commons.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.excel.ExcelExportUtils;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.FormTokenManager;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.design.Customer;
/**
 * Http与Servlet工具类.
 * 
 */
public class ServletUtils {
	private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	//-- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	//-- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	//-- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
	
	private final static FormTokenManager formTokenManager;
	
	static{
		formTokenManager = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class);
	}
	
	/**
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，
	 * 那么真正的用户端的真实IP则是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarder-For");
		if((StringUtils.isBlank(ip)) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-Ip");
			if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("WL-Proxy-Client-Ip");
			}
		}else{
			String[] ipArr = ip.split(",");
			for(String IP : ipArr){
				if(!"unknown".equalsIgnoreCase(IP)){
					ip = IP;
					break;
				}
			}
		}
		if((StringUtils.isBlank(ip)) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}
	
	
	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}
	
	/**
	 * 设置让浏览器弹出下载Excel对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setExcelDownloadHeader(HttpServletResponse response, String fileName) {
		response.setContentType(EXCEL_TYPE);
		setFileDownloadHeader(response, fileName);
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 对Http Basic验证的 Header进行编码.
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + Encodes.encodeBase64(encode.getBytes());
	}
	
	
	/**
	 * 将处理成功结果(默认提示信息）返回给客服端ajax脚本
	 * @param response
	 */
	public static void outPrintSuccess(HttpServletRequest request, HttpServletResponse response) {
		outPrintSuccess(request, response, CommonConstant.DEFAULT_SUCCESS_MSG, null);
	}
	
	/**
	 * 将处理成功结果(用户设置提示信息）返回给客服端ajax脚本
	 * @param response
	 * @param msg
	 */
	public static void outPrintSuccess(HttpServletRequest request, HttpServletResponse response, String msg) {
		outPrintSuccess(request, response, msg, null);
	}
	
	/**
	 * 将处理成功结果(用户返回的数据）返回给客服端ajax脚本
	 * @param response
	 * @param map 用户返回的数据
	 */
	public static void outPrintSuccess(HttpServletRequest request, HttpServletResponse response, Object obj) {
		outPrintSuccess(request, response, CommonConstant.DEFAULT_SUCCESS_MSG, obj);
	}
	
	/**
	 * 将处理成功结果(用户设置提示信息,用户返回的数据）返回给客服端ajax脚本
	 * @param response
	 * @param msg  用户设置提示信息
	 * @param map  用户返回的数据
	 */
	public static void outPrintSuccess(HttpServletRequest request, HttpServletResponse response, String msg, Object obj) {
		flushResult(request, response, buildRs(true, msg, obj, null));
	}
	
	/**
	 * 将处理失败结果（用户设置失败提示信息） 返回给客服端ajax脚本
	 * @param response
	 * @param msg  用户设置失败提示信息
	 */
	public static void outPrintFail(HttpServletRequest request, HttpServletResponse response, String msg) {
		outPrintFail(request, response, msg, null);
	}
	
	/**
	 * 将处理失败结果（用户返回失败相关数据） 返回给客服端ajax脚本
	 * @param response
	 * @param map  用户返回失败相关数据
	 */
	public static void outPrintFail(HttpServletRequest request, HttpServletResponse response, Object obj) {
		outPrintFail(request, response, CommonConstant.DEFAULT_FAIL_MSG, obj);
	}
	
	/**
	 * 将处理失败结果（用户设置失败提示信息,用户返回失败相关数据） 返回给客服端ajax脚本
	 * @param response
	 * @param msg  用户设置失败提示信息
	 * @param map  用户返回失败相关数据
	 */
	public static void outPrintFail(HttpServletRequest request, HttpServletResponse response, String msg, Object obj) {
//		flushResult(request, response, buildRs(false, msg, obj, null));
		FormToken formToken = formTokenManager.newFormToken(request);
		flushResult(request, response, buildRs(false, msg, obj, formToken));
	}
	
	/**
	 * 将用户数据 msg 不进行封装，直接传递给客服端ajax
	 * @param response
	 * @param msg
	 */
	public static void outPrintMsg(HttpServletRequest request, HttpServletResponse response, String msg) {
		flushResult(request, response, msg);
	}
	
	/**
	 * 将处理失败结果，同时返回下一个token给客服端ajax
	 * @param request
	 * @param response
	 * @param msg
	 * @param obj
	 */
	public static void outPrintWithToken(HttpServletRequest request, HttpServletResponse response, String msg){
		outPrintWithToken(request, response, msg, null);
	}
	
	/**
	 * 将处理失败结果，同时返回下一个token给客服端ajax
	 * @param request
	 * @param response
	 * @param msg
	 * @param obj
	 */
	public static void outPrintWithToken(HttpServletRequest request, HttpServletResponse response, String msg, Object obj){
		FormToken formToken = formTokenManager.newFormToken(request);
		flushResult(request, response, buildRs(false, msg, obj, formToken));
	}
	
	/**
	 * 自定义返回结果,是否刷新token根据setToken参数判断
	 * @param request
	 * @param response
	 * @param success
	 * @param msg
	 * @param obj
	 * @param setToken
	 */
	public static void outPrint(HttpServletRequest request, HttpServletResponse response, boolean success, String msg, Object obj, boolean setToken){
		FormToken formToken = null;
		if(setToken){
			formToken = formTokenManager.newFormToken(request);
		}
		flushResult(request, response, buildRs(success, msg, obj, formToken));
	}
	
	/**
	 * 返回信息最后JSON格式
	 * @param success  处理结果是否成功
	 * @param msg  提示信息
	 * @param map  用户数据
	 * @return
	 */
	private static String buildRs(boolean success, String msg, Object obj, FormToken formToken){
		StringBuilder strb = new StringBuilder("{\"rs\":");
		strb.append(success ? "true" : "false")
			.append(",\"msg\":\"").append(null == msg ? "" : msg)
			.append("\",\"datas\":").append(null == obj ? "{}" : JSON.toJSONString(obj))
			.append(", \"token\": ").append(null == formToken ? "{}" : JSON.toJSONString(formToken))
			.append("}");
		
		logger.debug("rs：[ {} ]", strb);
		
		return strb.toString();
	}
	
	/**
	 * 将用户数据返回给相应的客服端请求ajax页面
	 * @param response
	 * @param str
	 */
	private static void flushResult(HttpServletRequest request, HttpServletResponse response, String str){
		setDisableCacheHeader(response);
		response.setHeader("Content-type", JSON_TYPE);  
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.debug("用户数据返回: [ {} ]",  str);
			String callback = request.getParameter("callback");
			if (StringUtils.isNotBlank(callback)){
				str = callback + "(" + str + ")";
			}
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
	 * 返回xml格式数据到客服端页面
	 * @param response
	 * @param xml
	 */
	public static void outPrintXml(HttpServletRequest request, HttpServletResponse response, String xml){
		setDisableCacheHeader(response);
		response.setHeader("Content-type", XML_TYPE);  
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.debug("用户数据返回: [ {} ]",  xml);
			
			out.print(xml);
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
	 * 导出Excel,采用自定义命名
	 * @param <T>
	 * @param request
	 * @param response
	 * @param dataList
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void flushExcelOutputStream(HttpServletRequest request, HttpServletResponse response, 
			List dataList, String fileName, List<String> columnList, List<String> titleList, List<String> sumList){
		if (StringUtils.isBlank(fileName)){
			fileName = System.currentTimeMillis() + "";
		}
		String userAgent = request.getHeader("User-Agent");
		String newFileName = null;
//		try {
//			fileName = URLEncoder.encode(fileName, "UTF8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				newFileName = "filename=\"" + fileName + ".xls\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				newFileName = "filename*=UTF-8''" + fileName +".xls";
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				try {
					newFileName = "filename=\""
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1")
							+ ".xls\"";
					// 由于userAgent字符串中同时包含chrome和safari,针对chrome采用urlEncoder编码文件名 add by zzr 2019/03/04
					if(userAgent.indexOf("chrome") != -1){
						newFileName = "filename=\""
								+URLEncoder.encode(fileName, "UTF-8")
								+ ".xls\"";
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				try {
					fileName = MimeUtility.encodeText(fileName, "UTF8", "B");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				newFileName = "filename=\"" + fileName + ".xls\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				newFileName = "filename*=UTF-8''" + fileName +".xls";
			}
		}
		newFileName=newFileName.replace("%23", "#");
		response.setHeader("Content-Disposition", "attachment;" + newFileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		
		SXSSFWorkbook workbook;
		if (titleList==null || titleList.size()==0){
			workbook = new ExcelExportUtils().exportExcel(System.currentTimeMillis() + "", dataList, columnList);
		}else{
			workbook = new ExcelExportUtils().exportExcel(System.currentTimeMillis() + "", dataList, columnList, titleList, sumList);
		}
		
		ServletUtils.setDisableCacheHeader(response);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void downLoad(HttpServletRequest request, HttpServletResponse response,String filePath){
		File file = new File(filePath);
		String fileName = file.getName();
		OutputStream os = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			byte b[] = new byte[is.available()];
			is.read(b);
			response.setContentType("application/octet-stream");// 设置为下载application/x-download
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1")); // 这个很重要

			os = new BufferedOutputStream(response
					.getOutputStream());
			os.write(b);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
	 * @throws Exception 
     */  
    public static byte[] getImageFromNetByUrl(String strUrl) throws Exception{  
        URL url = new URL(strUrl);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);  
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
        byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
        return btImg;
    }  
    /** 
     * 根据地址判断图片是否存在 
     * @param strUrl 网络连接地址 
     * @return 
	 * @throws Exception 
     */  
    public static boolean checkImageFromNetByUrl(String strUrl) throws Exception{  
        URL url = new URL(strUrl);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);  
        InputStream inStream = conn.getInputStream();
        if (inStream!=null){
        	inStream.close();
        	return true;
        }
        return false;
    } 
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  
    
	public static String convertErrors2Msg(Errors errors){
		List<FieldError> listErrors = errors.getFieldErrors();
		StringBuffer sb = new StringBuffer("");
		
		for(FieldError fieldError : listErrors){
			sb.append(fieldError.getDefaultMessage() + "<br/>");
			if(logger.isDebugEnabled()){
				logger.debug(fieldError.getDefaultMessage() + " " + fieldError.getRejectedValue() + " " + fieldError.getCode());
			}
		}
		
		return sb.toString();
	}
	
	public static void downLoadWithName(HttpServletRequest request, HttpServletResponse response,String filePath,String name){
		File file = new File(filePath);
		String fileName = null;
		String userAgent = request.getHeader("User-Agent");
		OutputStream os = null;
		InputStream is = null;
		String suffix = filePath.substring(filePath.lastIndexOf("."));
		try {
			is = new FileInputStream(file);
			byte b[] = new byte[is.available()];
			is.read(b);
			response.setContentType("application/octet-stream");// 设置为下载application/x-download
			response.setCharacterEncoding("UTF-8");
			if (userAgent != null) {
				userAgent = userAgent.toLowerCase();
				// IE浏览器，只能采用URLEncoder编码
				if (userAgent.indexOf("msie") != -1) {
					fileName = "filename=\"" + URLEncoder.encode(name,"UTF-8") + suffix+"\"";
				}
				// Opera浏览器只能采用filename*
				else if (userAgent.indexOf("opera") != -1) {
					fileName = "filename*=UTF-8''" + name +suffix+"\"";
				}
				// Safari浏览器，只能采用ISO编码的中文输出
				else if (userAgent.indexOf("safari") != -1 || userAgent.indexOf("mozilla") != -1) {
					try {
						fileName = "filename=\""
								+ new String(name.getBytes("UTF-8"), "ISO8859-1")
								+ suffix+"\"";
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
				else if (userAgent.indexOf("applewebkit") != -1 ) {
					try {
						fileName = MimeUtility.encodeText(name, "UTF8", "B");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					fileName = "filename=\"" + name + suffix+"\"";
				}
//				// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
//				else if (userAgent.indexOf("mozilla") != -1) {
//					fileName = "filename*=UTF-8''" + name + suffix;
//				}
			}
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1")); // 这个很重要
			response.setHeader("Content-Disposition", "attachment;" + fileName); // 这个很重要
			os = new BufferedOutputStream(response
					.getOutputStream());
			os.write(b);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	   * 下载文件到本地
	   *
	   * @param urlString
	   *          被下载的文件地址
	   * @param filename
	   *          本地文件名
	 * @throws Exception
	   *           各种异常
	   */
	public static void download(HttpServletRequest request, HttpServletResponse response, 
			String urlString, String fileName) {
		OutputStream os = null;
		try {
			byte b[] = getImageFromNetByUrl(urlString);
			response.setContentType("application/octet-stream");// 设置为下载application/x-download
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1")); // 这个很重要

			os = new BufferedOutputStream(response
					.getOutputStream());
			os.write(b);
			os.flush();
		}catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "下载失败");
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	} 
/**
 * 多文件打包RAR下载 by zzr
 * @param request
 * @param response
 * @param files       需要下载的文件URL
 * @param zip         压缩文件名字
 * @param delete      是否删除压缩文件
 * @throws Exception
 */
	public static void downLoadFiles(HttpServletRequest request, HttpServletResponse response,StringBuilder[] files,String zip,boolean delete){
		File zipFile = null;
		ZipOutputStream zout = null;
		try{
			//创建压缩文件
			zipFile = new File(zip); 
			zipFile.createNewFile();         
			
			byte[] buff = new byte[1024];   //缓冲区
			int len;   //文件读取长度
			zout=new ZipOutputStream(new FileOutputStream(zipFile)); //压缩文件输出流
			
			if(files != null){
				for (int i = 0; i < files.length; i++) {
					//通过URL 获得文件输入流
			        URL url = new URL(files[i].toString());  
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			        conn.setRequestMethod("GET");
			        conn.setConnectTimeout(5 * 1000);    
			        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){   //判断是否连接成功
						InputStream in = conn.getInputStream();   
					    zout.putNextEntry(new ZipEntry(files[i].substring(files[i].lastIndexOf("/")+1)));   //创建写入文件的文件名  
					    while ((len = in.read(buff)) > 0) {               
					        zout.write(buff, 0, len);  
					    }  
					    zout.closeEntry();   //文件写入结束
					    in.close();          //关闭文件输入流
			        }
				}
			}
			zout.close();    //关闭压缩文件输出流
			
			//文件下载
			downLoad(request, response,zip);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(zout != null){
				try {
					zout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//压缩文件删除
			if(delete){
				zipFile.delete();
			}
		}
	}
	/**
	 * 多文件打包RAR下载,多传参一个'文件名称'
	 * @author	created by zb
	 * @date	2019-11-25--下午3:27:30
	 * @param request
	 * @param response
	 * @param files
	 * @param zip
	 * @param delete
	 * @param filesDescr 文件名称
	 */
	public static void downLoadFiles(HttpServletRequest request, HttpServletResponse response,
			StringBuilder[] files,String zip,boolean delete,StringBuilder[] filesDescr){
		File zipFile = null;
		ZipOutputStream zout = null;
		try{
			//创建压缩文件
			zipFile = new File(zip); 
			zipFile.createNewFile();         
			
			byte[] buff = new byte[1024];   //缓冲区
			int len;   //文件读取长度
			zout=new ZipOutputStream(new FileOutputStream(zipFile)); //压缩文件输出流
			
			if(files != null){
				for (int i = 0; i < files.length; i++) {
					//通过URL 获得文件输入流
			        URL url = new URL(files[i].toString());  
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			        conn.setRequestMethod("GET");
			        conn.setConnectTimeout(5 * 1000);    
			        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){   //判断是否连接成功
						InputStream in = conn.getInputStream();   
					    zout.putNextEntry(new ZipEntry(filesDescr[i].toString()));   //创建写入文件的文件名  
					    while ((len = in.read(buff)) > 0) {               
					        zout.write(buff, 0, len);  
					    }  
					    zout.closeEntry();   //文件写入结束
					    in.close();          //关闭文件输入流
			        }
				}
			}
			zout.close();    //关闭压缩文件输出流
			//文件下载
			downLoad(request, response,zip);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(zout != null){
				try {
					zout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//压缩文件删除
			if(delete){
				zipFile.delete();
			}
		}
	}
	/**
	 * 图片压缩
	 * @author zzr
	 * @param inputStream  输入流
	 * @param fileRealPath 保存路径
	 * @param maxSize 最大的大小(KB)
	 * @param quality 图片质量
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void compressImage(InputStream inputStream, String fileRealPath, long maxSize, float quality) throws IOException{
		try{
			if(maxSize == 0L){
				maxSize = 999999999;
			}
			
			if(quality == 0f){
				quality = 1f;
			}
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			Thumbnails.of(inputStream).size(1024, 1024).outputFormat("JPG").outputQuality(quality).toOutputStream(byteArrayOutputStream);
			inputStream.close();
			
			do{
				if(byteArrayOutputStream != null){
					if(byteArrayOutputStream.toByteArray().length/1024f <= maxSize*1f || quality <= 0.8f){
						File file = new File(fileRealPath);
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(byteArrayOutputStream.toByteArray());
						fileOutputStream.close();
						byteArrayOutputStream.close();
						break;
					}
				}
				quality *= 0.8f;
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
				byteArrayOutputStream.reset();
				Thumbnails.of(byteArrayInputStream).size(1024, 1024).outputFormat("JPG").outputQuality(quality).toOutputStream(byteArrayOutputStream);
			}while(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				inputStream.close();
			}
			
		}
	}
	/**
	 * 导出Excel,采用自定义命名
	 * @param <T>
	 * @param request
	 * @param response
	 * @param dataList
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void flushExcelOutputStreamForCustomer(HttpServletRequest request, HttpServletResponse response, 
			List dataList, String fileName, List<String> columnList, List<String> titleList, List<String> sumList,Customer customer,UserContext uc){
		if (StringUtils.isBlank(fileName)){
			fileName = System.currentTimeMillis() + "";
		}
		String userAgent = request.getHeader("User-Agent");
		String newFileName = null;
//		try {
//			fileName = URLEncoder.encode(fileName, "UTF8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				newFileName = "filename=\"" + fileName + ".xls\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				newFileName = "filename*=UTF-8''" + fileName +".xls";
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				try {
					newFileName = "filename=\""
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1")
							+ ".xls\"";

					// 由于userAgent字符串中同时包含chrome和safari,针对chrome采用urlEncoder编码文件名 add by zzr 2019/03/04
					if(userAgent.indexOf("chrome") != -1){
						newFileName = "filename=\""
								+URLEncoder.encode(fileName, "UTF-8")
								+ ".xls\"";
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				try {
					fileName = MimeUtility.encodeText(fileName, "UTF8", "B");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				newFileName = "filename=\"" + fileName + ".xls\"";
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				newFileName = "filename*=UTF-8''" + fileName +".xls";
			}
		}
		response.setHeader("Content-Disposition", "attachment;" + newFileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		
		SXSSFWorkbook workbook;
		if (titleList==null || titleList.size()==0){
			workbook = new ExcelExportUtils().exportExcel(System.currentTimeMillis() + "", dataList, columnList);
		}else{
			workbook = new ExcelExportUtils().exportExcelForCustomer(System.currentTimeMillis() + "", dataList, columnList, titleList, sumList,customer,uc);
		}
		
		ServletUtils.setDisableCacheHeader(response);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}
	}
}
