package com.house.home.web.controller.query;

import java.util.Date;
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
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.query.EmployeeDdphService;
import com.house.home.service.query.MainBusiManWorkloadService;
@Controller
@RequestMapping("/admin/mainBusiManWorkload")
public class MainBusiManWorkloadController extends BaseController {
	
	@Autowired
	private MainBusiManWorkloadService mainBusiManWorkloadService;
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
		mainBusiManWorkloadService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goBeginJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBeginJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_beginDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goBuildingJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBuildingJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_buildingDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCompletedJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCompletedJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_completedDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goFirstConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFirstConfirmJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_firstConfirmDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/ goSecondConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSecondConfirmJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_secondConfirmDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goChgJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> gochgJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiManWorkloadService.findPageBySql_chgDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/mainBusiManWorkload/mainBusiManWorkload_list");
	}
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		return new ModelAndView("admin/query/mainBusiManWorkload/mainBusiManWorkload_detail")
		.addObject("customer", customer);
				
	}
}
