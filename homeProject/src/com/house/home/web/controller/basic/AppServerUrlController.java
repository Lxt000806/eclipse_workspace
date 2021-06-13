package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.AppServerUrl;
import com.house.home.service.basic.AppServerUrlService;

@Controller
@RequestMapping("/admin/appServerUrl")
public class AppServerUrlController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AppServerUrlController.class);

	@Autowired
	private AppServerUrlService appServerUrlService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,AppServerUrl appServerUrl) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		appServerUrlService.findPageBySql(page, appServerUrl);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * AppServerUrl列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/appServerUrl/appServerUrl_list");
	}
	
	/**
	 * 跳转到新增AppServerUrl页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增AppServerUrl页面");
		AppServerUrl appServerUrl = null;
		if (StringUtils.isNotBlank(id)){
			appServerUrl = appServerUrlService.get(AppServerUrl.class, id);
			appServerUrl.setDescr(null);
		}else{
			appServerUrl = new AppServerUrl();
		}
		
		return new ModelAndView("admin/basic/appServerUrl/appServerUrl_save")
			.addObject("appServerUrl", appServerUrl);
	}
	/**
	 * 跳转到修改AppServerUrl页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改AppServerUrl页面");
		AppServerUrl appServerUrl = null;
		if (StringUtils.isNotBlank(id)){
			appServerUrl = appServerUrlService.get(AppServerUrl.class, id);
		}else{
			appServerUrl = new AppServerUrl();
		}
		
		return new ModelAndView("admin/basic/appServerUrl/appServerUrl_update")
			.addObject("appServerUrl", appServerUrl);
	}
	
	/**
	 * 跳转到查看AppServerUrl页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看AppServerUrl页面");
		AppServerUrl appServerUrl = appServerUrlService.get(AppServerUrl.class, id);
		
		return new ModelAndView("admin/basic/appServerUrl/appServerUrl_detail")
				.addObject("appServerUrl", appServerUrl);
	}
	/**
	 * 添加AppServerUrl
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, AppServerUrl appServerUrl){
		logger.debug("添加AppServerUrl开始");
		try{
			AppServerUrl xt = this.appServerUrlService.get(AppServerUrl.class, appServerUrl.getDescr());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "AppServerUrl重复");
				return;
			}
			String str = appServerUrlService.getSeqNo("tAppServerUrl");
			appServerUrl.setDescr(str);
			this.appServerUrlService.save(appServerUrl);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加AppServerUrl失败");
		}
	}
	
	/**
	 * 修改AppServerUrl
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, AppServerUrl appServerUrl){
		logger.debug("修改AppServerUrl开始");
		try{
			AppServerUrl xt = this.appServerUrlService.get(AppServerUrl.class, appServerUrl.getDescr());
			if (xt!=null){
				this.appServerUrlService.update(appServerUrl);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.appServerUrlService.save(appServerUrl);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改AppServerUrl失败");
		}
	}
	
	/**
	 * 删除AppServerUrl
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除AppServerUrl开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "AppServerUrl编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				AppServerUrl appServerUrl = appServerUrlService.get(AppServerUrl.class, deleteId);
				if(appServerUrl == null)
					continue;
				appServerUrlService.update(appServerUrl);
			}
		}
		logger.debug("删除AppServerUrl IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *AppServerUrl导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, AppServerUrl appServerUrl){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		appServerUrlService.findPageBySql(page, appServerUrl);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"AppServerUrl_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
