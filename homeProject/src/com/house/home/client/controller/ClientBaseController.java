package com.house.home.client.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.servlet.URLDecoder;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.validation.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.SerializeUtil;
import com.house.framework.commons.utils.WeiXinCryptUtils;
//import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.client.util.FormatTransfer;
import com.house.home.client.util.MD5EncryptionMgr;
import com.house.home.client.util.ReturnInfo;
import com.house.home.client.util.ReturnKeys;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.InterfaceLogService;

public abstract class ClientBaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected Validator springValidator;
	@Autowired
	private InterfaceLogService interfaceLogService;

	public JSONObject getMtStream(HttpServletRequest request, JSONObject json){
		BufferedReader br = null;
		StringBuilder msg = new StringBuilder();
		try{
			ServletInputStream ris = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(ris);

			InputStreamReader r = new InputStreamReader(bis, "UTF-8");
			br = new BufferedReader(r);
			bis.mark(0);
			String x = null;
			while (null != (x = br.readLine())) {
				msg.append(x);
			}
			String msgStr = msg.toString();
			String key = msgStr.substring(0, msgStr.indexOf("="));
			String value = msgStr.substring(msgStr.indexOf("=") + 1);
			//String[] str = msg.toString().split("=");
			json.put(key, URLDecoder.decode(value));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject getPayload(HttpServletRequest request, JSONObject json) throws FileUploadException, UnsupportedEncodingException{
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		// 获取多个上传文件
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		// 遍历上传文件写入磁盘
		while (it.hasNext()) {
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
			if (obit.isFormField()) { //普通域,获取页面参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString("UTF-8");
				json.put(fieldName, fieldValue);				
			}
		}
		return json;
	}

	/**
	 * 取客服端json数据流
	 * 
	 * @throws Exception
	 */
	public JSONObject getJson(HttpServletRequest req, StringBuilder msg,
			JSONObject json, BaseResponse respon){
		BufferedReader br = null;
		try{
			ServletInputStream ris = req.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(ris);

			InputStreamReader r = new InputStreamReader(bis, "UTF-8");
			br = new BufferedReader(r);
			bis.mark(0);
			String x = null;
			while (null != (x = br.readLine())) {
				msg.append(x);
			}
			if (msg.length() > 0) {
				try {
					if (!msg.toString().endsWith("}")) {
						respon.setReturnCode("200001");
					} else {
						json = JSONObject.fromObject(msg.toString());
						respon.setReturnCode("000000");
					}
				} catch (Exception e) {
					respon.setReturnCode("100001");
				}
			} else {
				respon.setReturnCode("200002");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return json;
	}

	/**
	 * 
	 * 功能说明:返回对象
	 * 
	 * @param returnCode
	 *            返回码
	 * @throws Exception
	 *             void
	 * 
	 */
	public void returnJson(String returnCode, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ReturnKeys.KEY_CODE, returnCode);
		map.put(ReturnKeys.KEY_MSG, ReturnInfo.getReturnInfo(returnCode));
		jsonToClient(map, resp, msg, respon, request, null);

	}

	/**
	 * 
	 * 功能说明:返回对象
	 * 
	 * @param obj
	 * @throws Exception
	 *             void
	 * 
	 */
	public void returnJson(Object obj, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon, HttpServletRequest request,
			InterfaceLog interfaceLog)
			throws Exception {
		jsonToClient(obj, resp, msg, respon, request, interfaceLog);
	}
	
	public void returnString(HttpServletRequest request, HttpServletResponse resp, StringBuilder data, BaseResponse respon, InterfaceLog interfaceLog) throws Exception{
		data.append(WeiXinCryptUtils.Encrypt(WeiXinCryptUtils.mtToken, 
				WeiXinCryptUtils.mtEncodingAesKey, 
				WeiXinCryptUtils.mtAppId, 
				JSONObject.fromObject(respon).toString()));
		stringToClient(request, resp, data, respon, interfaceLog);
	}

	/**
	 * 
	 * 功能说明:返回对象(不返回值为null的字段)
	 * 
	 * @param obj
	 * @throws Exception
	 *             void
	 * 
	 */
	public void returnJsonWithoutNull(Object o, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon) throws Exception {
		String json = JSON.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteNullStringAsEmpty);
		byte[] jsonBytes = FormatTransfer.StringToByte(json);
		resp.setContentType("text/html;charset=utf-8");

		OutputStream out = resp.getOutputStream();
		out.write(jsonBytes);
		out.flush();
		out.close();
	}

	public void returnSfJson(Object obj, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon) throws Exception {
		sfJsonToClient(obj, resp, msg, respon);
	}

	/**
	 * 
	 * 功能说明:返回json
	 * 
	 * @param o
	 * @throws Exception
	 *             void
	 * 
	 */
	private void jsonToClient(Object o, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon, HttpServletRequest request,
			InterfaceLog interfaceLog)
			throws Exception {
		// 记录日志
		if (interfaceLog != null 
				&& "true".equals(SystemConfig.getProperty("recordResponse", "false", "appLog"))) {
			this.saveLog(msg, respon, interfaceLog);
		}
		
		String json = JSON.toJSONStringWithDateFormat(o, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty);
		json = json.replaceAll("\t", " ");
		String callback = request.getParameter("callback");
		if (StringUtils.isNotBlank(callback)) {
			json = callback + "(" + json + ")";
			resp.setHeader("Content-type","application/x-javascript;charset=utf-8");
		}/* else {
			resp.setContentType("application/json;charset=utf-8");
//			//支持跨域访问
			resp.addHeader("Access-Control-Allow-Origin","*");
			resp.addHeader("Access-Control-Allow-Methods","GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, PATCH");
			resp.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		}*/
		PrintWriter out = resp.getWriter();
		out.print(json);
		out.flush();
		out.close();
//		byte[] jsonBytes = FormatTransfer.StringToByte(json);
//		OutputStream out = resp.getOutputStream();
//		out.write(jsonBytes);
//		out.flush();
//		out.close();
	}

	private void stringToClient(HttpServletRequest request, HttpServletResponse resp, StringBuilder data, BaseResponse respon, InterfaceLog interfaceLog)
			throws Exception {
		// 记录日志
		if (interfaceLog != null 
				&& "true".equals(SystemConfig.getProperty("recordResponse", "false", "appLog"))) {
			this.saveLog(data, respon, interfaceLog);
		}
		resp.setContentType("charset=utf-8");
//			//支持跨域访问
		resp.addHeader("Access-Control-Allow-Origin","*");
		resp.addHeader("Access-Control-Allow-Methods","GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, PATCH");
		resp.addHeader("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		
		PrintWriter out = resp.getWriter();
		out.print(data.toString());
		out.flush();
		out.close();
	}
	
	private void sfJsonToClient(Object o, HttpServletResponse resp,
			StringBuilder msg, BaseResponse respon) throws Exception {
		JSONObject json = JSONObject.fromObject(o);
		byte[] jsonBytes = FormatTransfer.StringToByte(json.toString());
		resp.setContentType("text/html;charset=utf-8");

		OutputStream out = resp.getOutputStream();
		out.write(jsonBytes);
		out.flush();
		out.close();
	}
	
	public void saveLog(StringBuilder msg, BaseResponse respon,
			InterfaceLog interfaceLog) {
		String json = JSON.toJSONStringWithDateFormat(respon,
				"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty);
		interfaceLog.setReturnTime(new Date());
		interfaceLog.setContent(msg.toString());
		interfaceLog.setReturnCode(respon.getReturnCode());
		interfaceLog.setReturnInfo(respon.getReturnInfo());
		interfaceLog.setResponseContent(json);
		interfaceLogService.save(interfaceLog);
	}

	/**
	 * 
	 * 功能说明:异常处理
	 * 
	 * @param respon
	 * @param resp
	 * @param msg
	 * @param accountInterfaceLog
	 *            void
	 */
	protected void exceptionHandle(BaseResponse respon,
			HttpServletResponse resp, StringBuilder msg,
			HttpServletRequest request,InterfaceLog interfaceLog) {
		try {
			respon.setReturnCode("100007");
			returnJson(respon, resp, msg, respon, request, interfaceLog);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	protected void exceptionHandleString(BaseResponse respon,
			HttpServletResponse resp, StringBuilder msg,
			HttpServletRequest request,InterfaceLog interfaceLog) {
		try {
			respon.setReturnCode("100007");
			returnString(request, resp, msg, respon, interfaceLog);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected static String getWebRoot(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 * @param type
	 * @return 图片名称
	 */
	protected static String generateImage(String imgStr, Integer type) {
		if (imgStr == null)
			// 图像数据为空
			return null;
		Long currentTimeMillis = System.currentTimeMillis();
		String fileName = currentTimeMillis + ".jpg";
		String path = "";
		switch (type.intValue()) {
		case 0:
			path = getPrjProgPhotoUploadPath(fileName);
			break;
		case 1:
			path = getOtherPhotoUploadPath();
			break;
		}
		try {
			FileUploadServerMgr.makeDir(path);
			// Base64解码
			byte[] bytes = Base64.decodeBase64(imgStr.getBytes());
			OutputStream out = new FileOutputStream(path+fileName);
			out.write(bytes);
			out.flush();
			out.close();
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**获取app用户
	 * @param request
	 * @return
	 */
	protected static UserContext getUserContext(HttpServletRequest request) {
		UserContext uc = null;
		System.out.println(request.getHeader("AppType"));
		if("3".equals(request.getHeader("AppType")) || "2".equals(request.getHeader("AppType")) || "5".equals(request.getHeader("AppType"))){
			int redisDbIndex = "5".equals(request.getHeader("AppType")) ? 8 : 6;
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(redisDbIndex);
			byte[] userContextBtyes = RedisUtil.get(redisConnection, request.getHeader("UserToken"));
			if(userContextBtyes != null && userContextBtyes.length > 0){
				uc = (UserContext) SerializeUtil.unSerialize(userContextBtyes);
			}
		}else{
			uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_APP_KEY);
		}
		return uc;
	}
	/**获取DriverApp用户
	 * @param request
	 * @return
	 */
	protected static UserContext getDriverUserContext(HttpServletRequest request) {
		UserContext uc = null;
		if("6".equals(request.getHeader("AppType"))){
			RedisConnection redisConnection = RedisUtil.getRedisConnection();
			redisConnection.select(9);
			byte[] userContextBtyes = RedisUtil.get(redisConnection, request.getHeader("UserToken"));
			if(userContextBtyes != null && userContextBtyes.length > 0){
				uc = (UserContext) SerializeUtil.unSerialize(userContextBtyes);
			}
		}else{
			uc = (UserContext) request.getSession().getAttribute(CommonConstant.DRIVER_APP_KEY);
		}
		return uc;
	}
	/**获取工程图片上传路径
	 * @return
	 */
	protected static String getPrjProgPhotoUploadPath(String fileName){
		String prjProgNew = SystemConfig.getProperty("prjProgNew", "", "photo");
		if (StringUtils.isBlank(prjProgNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return prjProgNew + fileName.substring(0,5) + "/";
		}else{
			return SystemConfig.getProperty("prjProg", "", "photo");
		}
	}
	
	protected static String getWorkerPicUploadPath(String fileName){
		String prjProgNew = SystemConfig.getProperty("workerPic", "", "photo");
		if (StringUtils.isBlank(prjProgNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return prjProgNew + fileName.substring(0,5) + "/";
		}else{
			return SystemConfig.getProperty("workerPic", "", "photo");
		}
	}
	
	/**获取工程图片下载路径
	 * @return
	 */
	protected static String getPrjProgPhotoDownloadPath(HttpServletRequest request, String fileName){
		String path = getPrjProgPhotoUploadPath(fileName);
		
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
		//return OssConfigure.accessUrl + "/" + path.substring(path.indexOf("/")+1);
	}
	
	protected static String getWorkSignInPicDownLoadPath(HttpServletRequest request, String custCode){
		String path = getWorkSignInPhotoUploadPath(custCode);
		
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	protected static String getWorkerPicDownloadPath(HttpServletRequest request, String fileName){
		String path = getWorkerPicUploadPath(fileName);
		
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
		//return OssConfigure.accessUrl + "/" + path.substring(path.indexOf("/")+1);
	}
	
	/**获取预领料图片上传路径
	 * @return
	 */
	protected static String getItemPreAppPhotoUploadPath(){
		return SystemConfig.getProperty("itemPreApp", "", "photo");
	}
	
	/**获取预领料图片下载路径
	 * @return
	 */
	protected static String getItemPreAppPhotoDownloadPath(HttpServletRequest request){
		String path = getItemPreAppPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)
				+ path.substring(path.indexOf("/")+1);
	}
	
	/**获取送货图片上传路径
	 * @return
	 */
	protected static String getItemAppSendPhotoUploadPath(){
		return SystemConfig.getProperty("itemAppSend", "", "photo");
	}
	
	/**获取送货图片下载路径
	 * @return
	 */
	protected static String getItemAppSendPhotoDownloadPath(HttpServletRequest request){
		String path = getItemAppSendPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)
				+ path.substring(path.indexOf("/")+1);
	}
	/**获取其他图片上传路径
	 * @return
	 */
	protected static String getOtherPhotoUploadPath(){
		return SystemConfig.getProperty("other", "", "photo");
	}

	/**获取签到图片上传路径
	 * @return
	 */
	protected static String getSignInPhotoUploadPath(){
		return SystemConfig.getProperty("signIn", "", "photo");
	}
	
	protected static String getSignInPhotoDownloadPath(HttpServletRequest request){
		String path = getSignInPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}

	protected static String getOaBasePath() {
		return SystemConfig.getProperty("oaBasePath", "", "client");
	}
	
	protected static String getPhoneVarify() {
		return SystemConfig.getProperty("numRule","","phoneVerify");
	}

	protected static String getCustPwdVarify() {
		return SystemConfig.getProperty("custPwdRule","","custPwdVerify");
	}

	protected static String getRSAKey(String type) {
		if("privateKey".equals(type)){
			return SystemConfig.getProperty("privateKey","","rsaKey");
		}
		
		return SystemConfig.getProperty("publicKey","","rsaKey");
	}
	
	protected static String getCustPicUploadPath(){
		return SystemConfig.getProperty("custPic", "", "photo");
	}

	protected static String getCustPicDownloadPath(HttpServletRequest request){
		String path = getCustPicUploadPath();
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	protected static String getWorkSignInPhotoUploadPath(String custCode){
		return SystemConfig.getProperty("workSignPic", "", "photo")+custCode+"/";
	}
	
	protected static boolean checkRequest(HttpServletRequest request, JSONObject json){
		boolean result = false;
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.CLIENT_APP_KEY);
		if(uc != null && "1".equals(uc.getAppType())){
			Iterator iterator = json.keys();
			List<String> indexList = new ArrayList<String>();
			while(iterator.hasNext()){
				indexList.add(iterator.next().toString());
			}
			Collections.sort(indexList);
			StringBuilder paramsStr = new StringBuilder();
			String dataSign = "";
			for(int i = 0; i < indexList.size(); i++){
				if("dataSign".equals(indexList.get(i))){
					dataSign = json.get(indexList.get(i)).toString();
				}else{
					paramsStr.append(json.get(indexList.get(i)));
					paramsStr.append("homeDecorMall");
					paramsStr.append(json.get(indexList.get(i)));
					paramsStr.append("homeDecorMall");
				}
			}
/*			System.out.println(paramsStr.toString());
			System.out.println(dataSign.equals(MD5EncryptionMgr.md5Encryption(paramsStr.toString())));
			System.out.println(dataSign);*/
			if(StringUtils.isNotBlank(dataSign) && dataSign.equals(MD5EncryptionMgr.md5Encryption(paramsStr.toString()))){
				result = true;
			}
		}else{
			result = true;
		}
		return result;
	}

	protected static boolean checkAPIRequest(JSONObject json, String signStr){
		boolean result = false;
		try{
			String dataSign = json.getString("dataSign");
			Date date = new Date();
			String confirmStr = (json.getString("apiKey")+signStr+DateUtil.getYear(date)+"-"+(DateUtil.getMonth(date)+1)+"-"+DateUtil.getDay(date));
/*			System.out.println(dataSign);
			System.out.println(confirmStr);
			System.out.println(MD5EncryptionMgr.md5Encryption(confirmStr));*/
			if(StringUtils.isNotBlank(dataSign) && dataSign.equals(MD5EncryptionMgr.md5Encryption(confirmStr))){
				result = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	protected static String homeDecorBase64Encrypt(String str) throws UnsupportedEncodingException {
		if (str == null) {
			return "";
		}
		return "HOMEDECOR" + Base64.encodeBase64String(str.getBytes("utf-8"));
	}
	
	protected static String homeDecorBase64Decrypt(String str) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(str) || str.length() < 9) {
			return "";
		}
		return new String(Base64.decodeBase64(str.substring(9)), "utf-8");
	}
	
	protected static String getItemPhotoUploadPath(String fileName, String itemType1,  String itemType2,  String itemType3){
		String ftpFilePath=SystemConfig.getProperty("itemPic", "", "photo");
		if (StringUtils.isNotBlank(itemType1)){
			 ftpFilePath = ftpFilePath+itemType1.trim()+'/'; 
		}
		if (StringUtils.isNotBlank(itemType2)){
			 ftpFilePath = ftpFilePath + itemType2.trim() +'/';
		}
		if (StringUtils.isNotBlank(itemType3)){
			 ftpFilePath = ftpFilePath + itemType3.trim() +'/';
		}
		if (StringUtils.isNotBlank(fileName)){
			return ftpFilePath=ftpFilePath + fileName.trim();
		}
		return ftpFilePath;
		  
	}
		
	/**获取材料图片下载路径
	 * @return
	 */
	protected static String getItemPhotoDownloadPath(HttpServletRequest request, String fileName,String itemType1,  String itemType2,  String itemType3){
		String path = getItemPhotoUploadPath(fileName,itemType1, itemType2,itemType3);
		return  path;
	}
	
	protected static String getPrjProblemPhotoUploadPath(){
		return SystemConfig.getProperty("prjProblem", "", "photo");
	}
	
	protected static String getPrjProblemPhotoDownloadPath(HttpServletRequest request){
		String path = getPrjProblemPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
}
