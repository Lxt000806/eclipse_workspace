package com.house.home.web.controller.project;

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
import com.house.home.entity.project.ConfExceptionLog;
import com.house.home.service.project.ConfExceptionLogService;
@Controller
@RequestMapping("/admin/confExceptionLog")
public class ConfExceptionLogController extends BaseController{
	@Autowired
	private  ConfExceptionLogService confExceptionLogService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ConfExceptionLog	confExceptionLog) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		confExceptionLogService.findPageBySql(page, confExceptionLog);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/confExceptionLog/confExceptionLog_list");
	}
	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ConfExceptionLog	confExceptionLog){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		confExceptionLogService.findPageBySql(page, confExceptionLog);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"审核异常日志_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
