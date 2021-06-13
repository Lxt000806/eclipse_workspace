package com.house.home.web.controller.oa;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.token.FormToken;
import com.house.framework.web.token.impl.FormTokenManagerImpl;
import com.house.home.entity.oa.UserLeader;
import com.house.home.service.oa.UserLeaderService;

@Controller
@RequestMapping("/admin/userLeader")
public class UserLeaderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserLeaderController.class);

	@Autowired
	private UserLeaderService userLeaderService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, UserLeader userLeader) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		userLeaderService.findPageBySql(page, userLeader);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * UserLeader列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/oa/userLeader/userLeader_list");
	}
	
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/oa/userLeader/userLeader_import");
	}
	
	/**
	 * 
	 * 加载导入信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping("/loadImport")
	@ResponseBody
	public Map<String, Object> loadImport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ExcelImportUtils<UserLeader> eUtils = new ExcelImportUtils<UserLeader>();
		String param = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
			String fieldName = obit.getFieldName();
			String fieldValue = obit.getString();
			if ("param".equals(fieldName)){
				param = fieldValue.trim();
			}
			if ("file".equals(fieldName)){
				in=obit.getInputStream();
			}
		}
		
		try {
			List<Map<String,Object>> result = eUtils.importExcel(in);
			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> userLeader:result){
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("userid", userLeader.get("下属编号"));
				data.put("leaderid", userLeader.get("上级领导编号"));
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "当前操作使用了错误类型的值,请检查数值列是否包含非法字符!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	/**
	 * UserLeader查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/oa/userLeader/userLeader_code");
	}
	/**
	 * 跳转到新增UserLeader页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增UserLeader页面");
		UserLeader userLeader = null;
		if (StringUtils.isNotBlank(id)){
			userLeader = userLeaderService.get(UserLeader.class, id);
			userLeader.setUserId(null);
		}else{
			userLeader = new UserLeader();
		}
		
		return new ModelAndView("admin/oa/userLeader/userLeader_save")
			.addObject("userLeader", userLeader);
	}
	/**
	 * 跳转到修改UserLeader页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改UserLeader页面");
		UserLeader userLeader = null;
		if (StringUtils.isNotBlank(id)){
			userLeader = userLeaderService.get(UserLeader.class, id);
		}else{
			userLeader = new UserLeader();
		}
		
		return new ModelAndView("admin/oa/userLeader/userLeader_update")
			.addObject("userLeader", userLeader);
	}
	
	/**
	 * 跳转到查看UserLeader页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看UserLeader页面");
		UserLeader userLeader = userLeaderService.get(UserLeader.class, id);
		
		return new ModelAndView("admin/oa/userLeader/userLeader_detail")
				.addObject("userLeader", userLeader);
	}
	/**
	 * 添加UserLeader
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, UserLeader userLeader){
		logger.debug("添加UserLeader开始");
		try{
			if(userLeader.getUserId().equals(userLeader.getLeaderId())){
				ServletUtils.outPrintFail(request, response, "下属不能为自己");
				return;
			}
			UserLeader leader = userLeaderService.getByUserIdAndLeaderId(userLeader.getUserId(),userLeader.getLeaderId());
			if(leader!=null){
				ServletUtils.outPrintFail(request, response, "该下属已存在");
				return;
			}
			this.userLeaderService.save(userLeader);
			FormToken formToken = SpringContextHolder.getBean("formTokenManagerImpl", FormTokenManagerImpl.class).newFormToken(request);
			JSONObject json = new JSONObject();
			json.put("token", formToken.getToken());
			ServletUtils.outPrintSuccess(request, response, "添加下属成功", json);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加下属失败");
		}
	}
	
	/**
	 * 修改UserLeader
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, UserLeader userLeader){
		logger.debug("修改UserLeader开始");
		try{
			this.userLeaderService.update(userLeader);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改UserLeader失败");
		}
	}
	
	/**
	 * 删除UserLeader
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds, String deleteIds2){
		logger.debug("删除UserLeader开始");
		if(StringUtils.isBlank(deleteIds) || StringUtils.isBlank(deleteIds2)){
			ServletUtils.outPrintFail(request, response, "编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		List<String> deleteIdList2 = IdUtil.splitStringIds(deleteIds2);
		for(int i=0;i<deleteIdList.size();i++){
			UserLeader userLeader = userLeaderService.getByUserIdAndLeaderId(deleteIdList.get(i), deleteIdList2.get(i));
			if (userLeader!=null){
				userLeaderService.delete(userLeader);
			}
		}
		logger.debug("删除UserLeader IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *UserLeader导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, UserLeader userLeader){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		userLeaderService.findPageBySql(page, userLeader);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"UserLeader_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 导入UserLeader
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doImport")
	public void doImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("导入UserLeader开始");
		try{
			String rows = request.getParameter("rows");
			net.sf.json.JSONArray arryRows = net.sf.json.JSONArray.fromObject(rows);
			for (int i = 0; i < arryRows.size(); i++) { 
	            net.sf.json.JSONObject jsonObject = arryRows.getJSONObject(i); 
	            UserLeader userLeader = new UserLeader();
	            userLeader.setUserId(jsonObject.getString("userid"));
	            userLeader.setLeaderId(jsonObject.getString("leaderid"));
	            userLeaderService.saveOrUpdate(userLeader);
	        }
			ServletUtils.outPrintSuccess(request, response, "导入下属成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "导入下属失败");
		}
	}

}
