package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProblem;
import com.house.home.service.query.PrjProblemAnalyService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/prjProblemAnaly")
public class PrjProblemAnalyController extends BaseController{
	@Autowired
	private PrjProblemAnalyService prjProblemAnalyService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response, PrjProblem prjProblem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if ("1".equals(prjProblem.getStatistcsMethod())) {
			prjProblemAnalyService.findPageBySql(page, prjProblem);
		} else {
			prjProblemAnalyService.findPageBySqlWaitDeal(page, prjProblem);
		}
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridView")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridView(HttpServletRequest request, HttpServletResponse response, PrjProblem prjProblem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProblemAnalyService.findPageBySqlView(page, prjProblem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/query/prjProblemAnaly/prjProblemAnaly_list");
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, PrjProblem prjProblem){
		logger.debug("跳转到工地问题分析查看页面");
		return new ModelAndView("admin/query/prjProblemAnaly/prjProblemAnaly_view").addObject("prjProblem", prjProblem);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response, PrjProblem prjProblem){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		String fileTitle = "";
		if ("1".equals(prjProblem.getStatistcsMethod())) {
			fileTitle = "工地问题分析_待确认问题";
			prjProblemAnalyService.findPageBySql(page, prjProblem);
		} else {
			fileTitle = "工地问题分析_待处理问题";
			prjProblemAnalyService.findPageBySqlWaitDeal(page, prjProblem);
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(), 
											fileTitle + DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
