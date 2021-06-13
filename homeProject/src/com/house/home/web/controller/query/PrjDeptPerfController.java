package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department2;
import com.house.home.service.query.PrjDeptPerfService;

@Controller
@RequestMapping("/admin/PrjDeptPerf")
public class PrjDeptPerfController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PrjDeptPerfController.class);
	
	@Autowired
	private PrjDeptPerfService prjDeptPerfService;
	
	//页面跳转
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("跳转到工程部业绩分析主页");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("checkDateFrom",DateUtil.getPreMonth(2, 21));
		map.put("checkDateTo",DateUtil.getPreMonth(1, 20));
		map.put("dateFrom",DateUtil.getPreMonth(1, 1));
		map.put("dateTo",DateUtil.endOfTheMonth(DateUtil.getPreMonth(1, 1)));
		return new ModelAndView("admin/query/prjDeptPerf/prjDeptPerf_list").addObject("dateInfo", map);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,
								String empCode,String prjDeptLeaderCode,Date dateFrom,String department2 ,
								Date dateTo,String custType,Date checkDateFrom,Date checkDateTo) throws Exception {
		logger.debug("跳转到查看页面");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("empCode", empCode);
		map.put("prjDeptLeaderCode", prjDeptLeaderCode);
		map.put("dateFrom",dateFrom);
		map.put("dateTo",dateTo);
		map.put("checkDateFrom",checkDateFrom);
		map.put("checkDateTo",checkDateTo);
		map.put("custType", custType);
		map.put("department2", department2);
		return new ModelAndView("admin/query/prjDeptPerf/prjDeptPerf_view").addObject("dept2", map);
	}
	
	//表格查询
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response,Department2 department2,String statistcsMethod) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		prjDeptPerfService.findPageBySql(page,department2, orderBy, direction,statistcsMethod);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goConfBuildsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goConfBuildsJqGrid(HttpServletRequest request,HttpServletResponse response,
														String empCode,Date dateFrom,Date dateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goConfBuildsJqGrid(page, empCode,dateFrom,dateTo,custType);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goConfBuildsInJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goConfBuildsInJqGrid(HttpServletRequest request,HttpServletResponse response,
														String dept2Code,Date dateFrom,Date dateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goConfBuildsInJqGrid(page, dept2Code,dateFrom,dateTo,custType);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goConfBuildsOutJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goConfBuildsOutJqGrid(HttpServletRequest request,HttpServletResponse response,
														String dept2Code,Date dateFrom,Date dateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goConfBuildsOutJqGrid(page, dept2Code,dateFrom,dateTo,custType);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCheckBuildsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCheckBuildsJqGrid(HttpServletRequest request,HttpServletResponse response,
			String department2,String empCode,Date checkDateFrom,Date checkDateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goCheckBuildsJqGrid(page, empCode,checkDateFrom,checkDateTo,custType,department2);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCheckBuildsInJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCheckBuildsInJqGrid(HttpServletRequest request,HttpServletResponse response,
															String dept2Code,Date dateFrom,Date dateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goCheckBuildsInJqGrid(page, dept2Code,dateFrom,dateTo,custType);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCheckBuildsOutJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCheckBuildsOutJqGrid(HttpServletRequest request,HttpServletResponse response,
															String dept2Code,Date dateFrom,Date dateTo,String custType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goCheckBuildsOutJqGrid(page, dept2Code,dateFrom,dateTo,custType);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goReOrderBuildsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReOrderBuildsJqGrid(HttpServletRequest request,HttpServletResponse response,
																String empCode,Date dateFrom,Date dateTo,String custType,String department2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjDeptPerfService.goReOrderBuildsJqGrid(page, empCode,dateFrom,dateTo,custType,department2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goChangedPerformanceJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goChangedPerformanceJqGrid(HttpServletRequest request,
	        HttpServletResponse response, @RequestParam Map<String, String> postData) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    
	    prjDeptPerfService.goChangedPerformanceJqGrid(page, postData);
	    
	    return new WebPage<Map<String,Object>>(page);
	}

	//操作
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response,Department2 department2,String statistcsMethod){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		prjDeptPerfService.findPageBySql(page,department2, orderBy, direction,statistcsMethod);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),"工程部业绩分析--"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
}
