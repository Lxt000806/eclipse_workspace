package com.house.home.web.controller.query;

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
import com.house.home.service.query.MainBusiManPerfService;
@Controller
@RequestMapping("/admin/mainBusiManPerf")
public class MainBusiManPerfController extends BaseController {
	@Autowired
	private MainBusiManPerfService mainBusiManPerfService;
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
		mainBusiManPerfService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/mainBusiManPerf/mainBusiManPerf_list");
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/mainBusiManPerf/mainBusiManPerf_view");
	}
	
	@RequestMapping("/goJsmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJxmxJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setStatistcsMethod("1");
		mainBusiManPerfService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goDlxsmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDlxsmxJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setStatistcsMethod("2");
		mainBusiManPerfService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCkclxsmxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCkclxsmxJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setStatistcsMethod("3");
		mainBusiManPerfService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
}
