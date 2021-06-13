package com.house.home.web.controller.query;

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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.NetCustAnalyService;

@Controller
@RequestMapping("/admin/netCustAnaly")
public class NetCustAnalyController extends BaseController{
	
	@Autowired
	private NetCustAnalyService netCustAnalyService;
	
	/**
	 * 网络客服派单分析
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
		netCustAnalyService.findPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 网络客服派单分析-主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		Customer customer=new Customer();
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(new Date());
		customer.setCustType(netCustAnalyService.getDefaultCustType());
		return new ModelAndView("admin/query/netCustAnaly/netCustAnaly_list").addObject("customer",customer);
	}

	/**
	 * 网络客服派单分析-输出excel
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response
			,Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		netCustAnalyService.findPageBySql(page,customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"网络客服派单分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Customer customer) throws Exception {
		logger.debug("跳转到查看页面");
		return new ModelAndView("admin/query/netCustAnaly/netCustAnaly_view").addObject("customer", customer);
	}	
	
	@RequestMapping("/goManMonthView")
	@ResponseBody
	public WebPage<Map<String,Object>> goManMonthView(HttpServletRequest request, HttpServletResponse response,
													 Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		netCustAnalyService.findManPageBySql_month(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNetChanelMonthView")
	@ResponseBody
	public WebPage<Map<String,Object>> goNetChanelMonthView(HttpServletRequest request, HttpServletResponse response,
													 Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		netCustAnalyService.findNetChanelPageBySql_month(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goManAndNetChanelView")
	@ResponseBody
	public WebPage<Map<String,Object>> goManAndNetChanelView(HttpServletRequest request, HttpServletResponse response,
													 Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		netCustAnalyService.findManAndChanelPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goManHistoryView")
	@ResponseBody
	public WebPage<Map<String,Object>> goManHistoryView(HttpServletRequest request, HttpServletResponse response,
													 Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		netCustAnalyService.findManPageBySql_history(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNetChanelHistoryView")
	@ResponseBody
	public WebPage<Map<String,Object>> goNetChanelHistoryView(HttpServletRequest request, HttpServletResponse response,
													 Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		netCustAnalyService.findChanelPageBySql_history(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
}
