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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjPlanPerfProfitService;

@Controller
@RequestMapping("/admin/prjPlanPerfProfit")
public class PrjPlanPerfProfitController extends BaseController{
	@Autowired
	private PrjPlanPerfProfitService prjPlanPerfProfitService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjPlanPerfProfitService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer customer=new Customer();
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(new Date());
		return new ModelAndView("admin/query/prjPlanPerfProfit/prjPlanPerfProfit_list")
		.addObject("customer", customer);
	}
	
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjPlanPerfProfitService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地预算利润分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
