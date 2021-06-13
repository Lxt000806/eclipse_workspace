package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
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
import com.house.home.service.project.CustItemConfStatService;
@Controller
@RequestMapping("/admin/custItemConfStat")
public class CustItemConfStatController extends BaseController {
	
	@Autowired
	private CustItemConfStatService custItemConfStatService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response, Date dateFrom,Date dateTo,
			Date sdDateFrom,Date sdDateTo,Date nsDateFrom,Date nsDateTo, String mainBusinessMan, String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custItemConfStatService.findPageBySql(page, dateFrom, dateTo,sdDateFrom, sdDateTo,nsDateFrom, nsDateTo, mainBusinessMan, custType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到custItemConfStat
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateFrom", DateUtil.startOfTheMonth(new Date()));
		map.put("dateTo", DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/project/custItemConfStat/custItemConfStat_list").addObject("data", map);
	}
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo,
			Date sdDateFrom,Date sdDateTo,Date nsDateFrom,Date nsDateTo,String mainBusinessMan, String custType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custItemConfStatService.findPageBySql(page, dateFrom, dateTo,sdDateFrom, sdDateTo,nsDateFrom, nsDateTo, mainBusinessMan,custType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"主材确认情况-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	}
}
