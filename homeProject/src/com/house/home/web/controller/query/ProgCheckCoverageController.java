package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.house.home.service.query.ProgCheckCoverageService;

@Controller
@RequestMapping("/admin/progCheckCoverage")
public class ProgCheckCoverageController extends BaseController  {
	
	@Autowired
	private ProgCheckCoverageService progCheckCoverageService;
	
	//页面跳转
	/**
	 * 跳转到巡检覆盖率分析主页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("跳转到巡检覆盖率分析主页");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateFrom", DateUtil.startOfTheMonth(new Date()));
		map.put("dateTo", DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/query/progCheckCoverage/progCheckCoverage_list").addObject("data", map);
	}
	/**
	 * 跳转到巡检覆盖率查看页面
	 * @param request
	 * @param response
	 * @param type
	 * @param dateFrom
	 * @param dateTo
	 * @param isCheckDept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, String type, Date dateFrom, Date dateTo, String isCheckDept) throws Exception {
		logger.debug("跳转到查看页面");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("dateFromView", DateUtil.DateToString(dateFrom, "yyyy-MM-dd"));
		map.put("dateToView", DateUtil.DateToString(dateTo, "yyyy-MM-dd"));
		map.put("isCheckDeptView", isCheckDept);
		return new ModelAndView("admin/query/progCheckCoverage/progCheckCoverage_view").addObject("data", map);
	}	
	
	//表格查询
	/**
	 * 查询巡检覆盖率查看页面表格
	 * @param request
	 * @param response
	 * @param type
	 * @param dateFrom
	 * @param dateTo
	 * @param isCheckDept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridView")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridView(HttpServletRequest request, HttpServletResponse response, String type, 
													 Date dateFrom, Date dateTo, String isCheckDept) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		progCheckCoverageService.goJqGridView(page, type, dateFrom, dateTo, isCheckDept);
		return new WebPage<Map<String,Object>>(page);
	}	
	/**
	 * 查询巡检覆盖率分析主页表格
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 * @param isCheckDept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo, String isCheckDept) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = progCheckCoverageService.goJqGrid(page, dateFrom, dateTo, isCheckDept);
		page.setTotalCount(list!=null?list.size():0);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//操作相关

	/**
	 * 查看页面导出Excel
	 * @param request
	 * @param response
	 * @param type
	 * @param dateFrom
	 * @param dateTo
	 * @param isCheckDept
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, String type, 
			 			Date dateFrom, Date dateTo, String isCheckDept){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		progCheckCoverageService.goJqGridView(page, type, dateFrom, dateTo, isCheckDept);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"巡检覆盖率分析查看-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	
}
