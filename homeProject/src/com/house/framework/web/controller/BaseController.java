package com.house.framework.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ImageUtil;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Authority;
import com.house.framework.entity.Menu;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;

/**
 * Controller基类，其它Controller可以直接继承这个Controller，集成一些公共基础功能。
 * 
 */
public abstract class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Integer maxPictureSize = 1000;
	@Autowired
	protected Validator springValidator;
	
	protected List<String> titleList;
	
	protected List<String> columnList;
	
	protected List<String> sumList;
	
	
	/**
	 * 
	* @Title: out 
	* @Description: 返回JSON结果
	* @param @param object
	* @param @param flag
	* @param @return    设定文件 
	* @return JSONObject    返回类型 
	* @throws
	 */
	protected JSONObject out(Object object,boolean flag){
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("message", "");
		json.put("valid",true); 
		if(!flag){
			json.put("code", "-1");
			json.put("message", "");
			json.put("valid",false); 
		}
		json.put("data", object);
		if(null == object){
			json.put("data", "{}");
		}
		return json;
	}
	
	/**
	 * 请求参数page，pagesize设置到page对象并返回(专给JqGrid使用)
	 * @param request
	 * @return
	 */
	protected <T> Page<T> newPageForJqGrid(HttpServletRequest request){
		Page<T> page = new Page<T>();
		
		String pageSize = request.getParameter("rows");
		String pageOrder = request.getParameter("sord");
		page.setPageSize(StringUtils.isEmpty(pageSize)?CommonConstant.DEFAULT_PAGE_SIZE:Integer.parseInt(pageSize)); 
		String pageNo = request.getParameter("page");
		page.setPageNo(StringUtils.isEmpty(pageNo)?CommonConstant.DEFAULT_PAGE_NO:Integer.parseInt(pageNo));
		page.setPageOrderBy(request.getParameter("sidx"));
		page.setPageOrder("asc".equalsIgnoreCase(pageOrder)?"":pageOrder);
		
		return page;
	}

	/**
	 * 请求参数page，pagesize设置到page对象并返回
	 * @param <T>
	 * @param request
	 * @return
	 */
	protected <T> Page<T> newPage(HttpServletRequest request){
		Page<T> page = new Page<T>();
		
		page.setPageSize(ServletRequestUtils.getIntParameter(request, CommonConstant.PAGE_SIZE, CommonConstant.DEFAULT_PAGE_SIZE));
		page.setPageNo(ServletRequestUtils.getIntParameter(request, CommonConstant.PAGE_NO, CommonConstant.DEFAULT_PAGE_NO));
		page.setPageOrderBy(ServletRequestUtils.getStringParameter(request, CommonConstant.ORDER_BY, ""));
		page.setPageOrder(ServletRequestUtils.getStringParameter(request, CommonConstant.ORDER, ""));
		
		return page;
	}
	
	/**
	 * 获取用户上下文信息
	 * @param request
	 * @return
	 */
	public UserContext getUserContext(HttpServletRequest request){
		UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		return uc;
	}
	
	/**
	 * 当前登入用户是否超级管理员
	 * @param request
	 * @return
	 */
	public boolean isSuperAdmin(HttpServletRequest request) {
		UserContext uc = getUserContext(request);
		return uc.isSuperAdmin();
	}
	
	
	/**
	 * 当前登入用户是否是一级管理员
	 * @param request
	 * @return
	 */
	public boolean isOneAdmin(HttpServletRequest request) {
		UserContext uc = getUserContext(request);
		return uc.isOneAdmin();
	}
	
	/**
	 * 当前登入用户是否是二级管理员
	 * @param request
	 * @return
	 */
	public boolean isTwoAdmin(HttpServletRequest request) {
		UserContext uc = getUserContext(request);
		return uc.isTwoAdmin();
	}
    
	/**
	 * 获得项目工程的绝对路径
	 * @param request
	 * @return
	 */
	public static String getWebRoot(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 
	 *功能说明:用户上传图片
	 *@param request
	 *@param response
	 *@param dir
	 *@param maxSize 图片最大大小，默认1000k
	 */
	protected void doUploadPicture(HttpServletRequest request,HttpServletResponse response,
			String dir,Integer maxSize,int width,int height,boolean flagEqual) {

		String uploadPath = getWebRoot(request) + dir;
		String createPath = DateFormatUtils.format(new Date(), "yyyyMMdd")+ "/";
		String name = "";
		String tempPath = "";
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		Iterator<String> it = fileMap.keySet().iterator();
		JSONObject jsonObject = new JSONObject();
		while (it.hasNext()) {
			String key = it.next();
			MultipartFile multipartFile = fileMap.get(key);
			Result result = new Result();
			try {
				maxSize = (maxSize == null ? maxPictureSize : maxSize);
				result = ImageUtil.checkImg(multipartFile, maxSize , width, height, flagEqual);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(!"000000".equals(result.getCode())){
				jsonObject.put("result", result.getInfo());
			}else{
				jsonObject.put("result", "");
			}
			name = multipartFile.getOriginalFilename();
			name = "user_" + System.currentTimeMillis()+ name.substring(name.lastIndexOf("."));
			createPath += new Date().getTime()+ name.substring(name.lastIndexOf("."));
			File file = new File(uploadPath + "/" + name);
			file.getParentFile().mkdirs();
			try {
				multipartFile.transferTo(file);
				tempPath = uploadPath + "/" + createPath;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		jsonObject.put("path", tempPath);
		jsonObject.put("name", name);
		jsonObject.put("createPath", dir + "/" + name);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	/**获取工程图片下载路径
	 * @return
	 */
	protected static String getPrjProgPhotoDownloadPath(HttpServletRequest request, String fileName){
		String path = getPrjProgPhotoUploadPath(fileName);
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
		//return "http://192.168.0.155:8080/homeProject/"+path.substring(path.indexOf("/")+1);
	}
	protected void downLoad(HttpServletRequest request, HttpServletResponse response, String urlString, String fileName) {
		ServletUtils.download(request, response, urlString, fileName);
	}
	protected List<String> getHiddenList(String jsonString){
		if (StringUtils.isBlank(jsonString)){
			return null;
		}
		List<String> hiddenList = new ArrayList<String>();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
		String colModel = (String) jo.get("colModel");
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            Boolean hidden = jsonObject.get("hidden")==null?false:(Boolean)jsonObject.get("hidden");
            if (!"rn".equals(name) && !"cb".equals(name)){
            	if (hidden.booleanValue()==true){
            		hiddenList.add(name.toLowerCase());
            	}
            }
        }
		return hiddenList;
	}
	
	protected void getExcelList(HttpServletRequest request){
		titleList = new ArrayList<String>();
		columnList = new ArrayList<String>();
		sumList = new ArrayList<String>();
		boolean hasSum = false;
		String jsonString = request.getParameter("jsonString");
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(jsonString); 
		String colModel = (String) jo.get("colModel");
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = String.valueOf(jsonObject.get("name"));
            Boolean sum = (Boolean) jsonObject.get("sum");
            Boolean hidden = jsonObject.get("hidden")==null?false:(Boolean)jsonObject.get("hidden");
            if (!"rn".equals(name) && !"cb".equals(name)){
            	if (hidden.booleanValue()==false){
            		titleList.add(jsonObject.get("excelLabel")!=null?(String) jsonObject.get("excelLabel"):(String) jsonObject.get("label"));
            		columnList.add(name);
            		if (sum!=null && sum==true){
            			sumList.add(name);
            			hasSum = true;
            		}else{
            			sumList.add("");
            		}
            	}
            }
        }
		if (hasSum==false){
			sumList = new ArrayList<String>();
		}
	}
	
	protected Map<String,Object> menuNodeMapper(Menu menu,List<Long> menuList){
		if(menu == null)
			return null;
		boolean flag = false;
		Long pid = menu.getParentId();
		if (pid==null){
			pid = 0L;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(menuList != null){
			if (pid.longValue()==0){
				flag = true;
			}else{
				for(Long id : menuList){
					if(pid.longValue() == id.longValue()){
						flag = true;
						break;
					}
				}
			}
		}
		if (flag){
			map = new HashMap<String,Object>();
			map.put("id", menu.getMenuId()+"_menu");
			map.put("name", menu.getMenuName());
			map.put("pId", (menu.getParentId() == null || menu.getParentId() == 0) ? "0" : menu.getParentId()+"_menu");
			map.put("isParent", true);
			map.put("open", false);//进入权限设置界面，默认所有节点不展开 --change by zb
			map.put("nodeType", "menu");
		}
		
		return map;
	}
	
	protected Map<String,Object> authorityNodeMapper(Authority authority,List<Long> selIds,List<Long> menuList){
		if(null == authority)
			return null;
		Map<String, Object> map = new HashMap<String,Object>();
		if (menuList.indexOf(authority.getMenuId()) > 0){
			map.put("id", authority.getAuthorityId());
			map.put("name", authority.getAuthName());
			map.put("pId", authority.getMenuId()+"_menu");
			map.put("isParent", false);
			map.put("nodeType", "auth");
			map.put("isAdminAssign", authority.getISAdminAssign());
			if(selIds != null){
				for(Long id : selIds){
					if(id.longValue() == authority.getAuthorityId().longValue()){
						map.put("checked", true);
						break;
					}
				}
			}
		}
		return map;
	}
	
	protected Map<String,Object> userNodeMapper(Czybm user,List<String> selIds){
		if(user == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		map = new HashMap<String, Object>();
		map.put("id", user.getCzybh());
		map.put("name", user.getZwxm());
		map.put("isParent", false);
		map.put("nodeType", CommonConstant.USER);
		
		if(selIds != null && selIds.size()>0){
			for(String id : selIds){
				if(id.equals(user.getCzybh())){
					map.put("checked", true);
					break;
				}
			}
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	protected List<Map<String, Object>> treeNodeMapper(List list,List<Long> selIds,List<Long> menuList){
		List<Map<String, Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(list != null && list.size()>0){
			rsList = new ArrayList<Map<String,Object>>(list.size());
			for(Object obj : list){
				if(obj instanceof Czybm){
//					map = this.userNodeMapper((Czybm)obj, selIds);
				}else if(obj instanceof Authority){
					map = this.authorityNodeMapper((Authority)obj,selIds,menuList);
				}else{
					map = this.menuNodeMapper((Menu)obj,menuList);
				}
				if(map != null && map.size()>0){
					rsList.add(map);
				}
			}
		}
		return rsList;
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
		System.out.println(PathUtil.getWebRootAddress(request)+"\n"+ path.substring(path.indexOf("/")+1));
		return PathUtil.getWebRootAddress(request)
				+ path.substring(path.indexOf("/")+1);
		}
	/**获取集成测量分析图片上传路径
	 * @return
	 */
	protected static String getprjJobPhotoUploadPath(){
		return SystemConfig.getProperty("prjProgNew", "", "photo");
	}
	
	/**获取集成测量分析图片下载路径
	 * @return
	 */
	protected static String getprjJobPhotoDownloadPath(HttpServletRequest request){
		String path = getprjJobPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)
				+ path.substring(path.indexOf("/")+1);
	}
	
	/**获取发货，退货图片上传路径
	 * @return
	 */
	protected static String getItemAppSendPhotoUploadPath(){
		return SystemConfig.getProperty("itemAppSend", "", "photo");
	}
	/**获取发货，退货图片下载路径
	 * @return
	 */
	protected static String getItemAppSendPhotoDownloadPath(HttpServletRequest request){
		String path = getItemAppSendPhotoUploadPath();
		System.out.println(PathUtil.getWebRootAddress(request)+"\n"+ path.substring(path.indexOf("/")+1));
		return PathUtil.getWebRootAddress(request)
				+ path.substring(path.indexOf("/")+1);
	}
	protected static String getSignInPhotoUploadPath(){
		return SystemConfig.getProperty("signIn", "", "photo");
	}

	protected static String getWorkSignInPhotoUploadPath(String custCode){
		return SystemConfig.getProperty("workSignPic", "", "photo")+custCode+"/";
	}
	
	protected static String getSignInPhotoDownloadPath(HttpServletRequest request){
		String path = getSignInPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	/**
	 * 获取员工信息上传图片路径
	 * 
	 */
	public static String getEmpPicUploadPath(){
			return SystemConfig.getProperty("empPic", "", "photo");
		
	}
	/**
	 * 获取员工信息图片下载路径
	 * 
	 */
	public static String getEmpPicDownloadPath(HttpServletRequest request){
		String path = getEmpPicUploadPath();
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	protected static String getWorkSignInPicDownLoadPath(HttpServletRequest request, String custCode){
		String path = getWorkSignInPhotoUploadPath(custCode);
		
		return PathUtil.getWebRootAddress(request)+path.substring(path.indexOf("/")+1);
	}
	
	public static String getInformationFileUploadPath(){
		return SystemConfig.getProperty("infoAttach", "", "photo");
	}
	
	/**获取工地问题图片上传路径
	 * @return
	 */
	protected static String getPrjProblemPhotoUploadPath(){
		return SystemConfig.getProperty("prjProblemPic", "", "photo");
	}
	
	/**获取工地问题图片下载路径
	 * @return
	 */
	protected static String getPrjProblemPhotoDownloadPath(HttpServletRequest request){
		String path = getPrjProblemPhotoUploadPath();
		return PathUtil.getWebRootAddress(request)+ path.substring(path.indexOf("/")+1);
	}
}
