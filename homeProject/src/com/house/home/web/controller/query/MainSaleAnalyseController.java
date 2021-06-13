package com.house.home.web.controller.query;

import java.sql.Timestamp;
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
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.MainSaleAnalyseService;

@Controller
@RequestMapping("/admin/mainSaleAnalyse")
public class MainSaleAnalyseController extends BaseController {
	
	@Autowired
	private MainSaleAnalyseService mainSaleAnalyseService;
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		customer.setDateFrom(new Timestamp(DateUtil.startOfTheMonth(new Date()).getTime()));
		customer.setDateTo(new Timestamp(new Date().getTime()));
		return new ModelAndView("admin/query/mainSaleAnalyse/mainSaleAnalyse_list")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response, Customer customer){
		
		return new ModelAndView("admin/query/mainSaleAnalyse/mainSaleAnalyse_detail")
		.addObject("customer", customer);			
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
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainSaleAnalyseService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChgDetailGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goChgDetailGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainSaleAnalyseService.findChgDetailSql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemPlanJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemPlanJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainSaleAnalyseService.findItemPlanSql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}	
}
