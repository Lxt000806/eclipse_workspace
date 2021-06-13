package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.AutoArrWorkerApp;
import com.house.home.service.project.AutoArrWorkerAppService;

@Controller
@RequestMapping("/admin/autoArrWorkerApp")
public class AutoArrWorkerAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AutoArrWorkerAppController.class);

	@Autowired
	private AutoArrWorkerAppService autoArrWorkerAppService;

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
			HttpServletResponse response, AutoArrWorkerApp autoArrWorkerApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		autoArrWorkerAppService.findPageBySql(page, autoArrWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * AutoArrWorkerApp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/autoArrWorkerApp/autoArrWorkerApp_list");
	}
	/**
	 * AutoArrWorkerApp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/autoArrWorkerApp/autoArrWorkerApp_code");
	}
	/**
	 * 跳转到新增AutoArrWorkerApp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增AutoArrWorkerApp页面");
		AutoArrWorkerApp autoArrWorkerApp = null;
		if (StringUtils.isNotBlank(id)){
			autoArrWorkerApp = autoArrWorkerAppService.get(AutoArrWorkerApp.class, Integer.parseInt(id));
			autoArrWorkerApp.setPk(null);
		}else{
			autoArrWorkerApp = new AutoArrWorkerApp();
		}
		
		return new ModelAndView("admin/project/autoArrWorkerApp/autoArrWorkerApp_save")
			.addObject("autoArrWorkerApp", autoArrWorkerApp);
	}
	/**
	 * 跳转到修改AutoArrWorkerApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改AutoArrWorkerApp页面");
		AutoArrWorkerApp autoArrWorkerApp = null;
		if (StringUtils.isNotBlank(id)){
			autoArrWorkerApp = autoArrWorkerAppService.get(AutoArrWorkerApp.class, Integer.parseInt(id));
		}else{
			autoArrWorkerApp = new AutoArrWorkerApp();
		}
		
		return new ModelAndView("admin/project/autoArrWorkerApp/autoArrWorkerApp_update")
			.addObject("autoArrWorkerApp", autoArrWorkerApp);
	}
	
	/**
	 * 跳转到查看AutoArrWorkerApp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看AutoArrWorkerApp页面");
		AutoArrWorkerApp autoArrWorkerApp = autoArrWorkerAppService.get(AutoArrWorkerApp.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/autoArrWorkerApp/autoArrWorkerApp_detail")
				.addObject("autoArrWorkerApp", autoArrWorkerApp);
	}
	/**
	 * 添加AutoArrWorkerApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, AutoArrWorkerApp autoArrWorkerApp){
		logger.debug("添加AutoArrWorkerApp开始");
		try{
			this.autoArrWorkerAppService.save(autoArrWorkerApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加AutoArrWorkerApp失败");
		}
	}
	
	/**
	 * 修改AutoArrWorkerApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, AutoArrWorkerApp autoArrWorkerApp){
		logger.debug("修改AutoArrWorkerApp开始");
		try{
			autoArrWorkerApp.setLastUpdate(new Date());
			autoArrWorkerApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.autoArrWorkerAppService.update(autoArrWorkerApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改AutoArrWorkerApp失败");
		}
	}
	
	/**
	 * 删除AutoArrWorkerApp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除AutoArrWorkerApp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "AutoArrWorkerApp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				AutoArrWorkerApp autoArrWorkerApp = autoArrWorkerAppService.get(AutoArrWorkerApp.class, Integer.parseInt(deleteId));
				if(autoArrWorkerApp == null)
					continue;
				autoArrWorkerApp.setExpired("T");
				autoArrWorkerAppService.update(autoArrWorkerApp);
			}
		}
		logger.debug("删除AutoArrWorkerApp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *AutoArrWorkerApp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, AutoArrWorkerApp autoArrWorkerApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		autoArrWorkerAppService.findPageBySql(page, autoArrWorkerApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"AutoArrWorkerApp_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
