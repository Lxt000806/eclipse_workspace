package com.house.home.web.controller.query;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Arith;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.JcxdybbService;
import com.house.home.service.query.SoftCostQueryService;
import com.sun.org.apache.xpath.internal.operations.And;

@Controller
@RequestMapping("/admin/jcxdybb")
public class JcxdybbController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(JcxdybbController.class);
	
	@Autowired
	private JcxdybbService jcxdybbService;

	/**
	 * 软装收支明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {	
    	customer.setDateFrom(DateUtil.endOfTheDay(new Date()));
		customer.setDateTo(DateUtil.endOfTheDay(new Date()));	
		return new ModelAndView("admin/query/jcxdybb/jcxdybb_list").addObject("customer",customer);
	}
    /**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		jcxdybbService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJcllJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcllJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setIsCupboard("0");
		jcxdybbService.llmx_findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCgllJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCgllJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setIsCupboard("1");
		jcxdybbService.llmx_findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJczjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJczjJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setIsCupboard("0");
		jcxdybbService.zjmx_findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCgzjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCgzjJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setIsCupboard("1");
		jcxdybbService.zjmx_findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/jcxdybb/jcxdybb_view")
			.addObject("customer", customer);
	}
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response,Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		jcxdybbService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成下单月报表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

		
	
}
