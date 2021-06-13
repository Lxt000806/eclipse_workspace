package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ProjectCtrlPrice;
import com.house.home.service.basic.ProjectCtrlPriceService;
@Controller
@RequestMapping("/admin/projectCtrlPrice")
public class ProjectCtrlPriceController extends BaseController{
	@Autowired
	private  ProjectCtrlPriceService projectCtrlPriceService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ProjectCtrlPrice projectCtrlPrice) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		projectCtrlPriceService.findPageBySql(page, projectCtrlPrice);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/projectCtrlPrice/projectCtrlPrice_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/projectCtrlPrice/projectCtrlPrice_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, ProjectCtrlPrice projectCtrlPrice) throws Exception {
		return new ModelAndView("admin/basic/projectCtrlPrice/projectCtrlPrice_update")
			.addObject("projectCtrlPrice", projectCtrlPrice);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, ProjectCtrlPrice projectCtrlPrice) throws Exception {
		return new ModelAndView("admin/basic/projectCtrlPrice/projectCtrlPrice_update")
			.addObject("projectCtrlPrice", projectCtrlPrice);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			ProjectCtrlPrice projectCtrlPrice){
		logger.debug("新增保存");
		try {
			projectCtrlPrice.setLastUpdate(new Date());
			projectCtrlPrice.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			projectCtrlPrice.setExpired("F");
			projectCtrlPrice.setActionLog("ADD");
			
			this.projectCtrlPriceService.save(projectCtrlPrice);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			ProjectCtrlPrice projectCtrlPrice){
		logger.debug("编辑保存");
		try {
			projectCtrlPrice.setLastUpdate(new Date());
			projectCtrlPrice.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			projectCtrlPrice.setActionLog("Edit");
			
			this.projectCtrlPriceService.update(projectCtrlPrice);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ProjectCtrlPrice projectCtrlPrice){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		projectCtrlPriceService.findPageBySql(page, projectCtrlPrice);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"发包单价管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
