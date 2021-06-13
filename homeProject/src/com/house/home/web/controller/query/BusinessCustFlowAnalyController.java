package com.house.home.web.controller.query;

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
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.BusinessCustFlowAnalyService;

@Controller
@RequestMapping("/admin/businessCustFlowAnaly")
public class BusinessCustFlowAnalyController extends BaseController{
	@Autowired
	private BusinessCustFlowAnalyService businessCustFlowAnalyService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customer.setCustRight(this.getUserContext(request).getCustRight());
		businessCustFlowAnalyService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 明细查看
	 * @author	created by zb
	 * @date	2019-9-9--下午5:50:07
	 * @param request
	 * @param response
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		businessCustFlowAnalyService.findDetailPageBySql(page, employee, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Employee employee = this.businessCustFlowAnalyService.get(Employee.class, this.getUserContext(request).getEmnum());
		Czybm czybm = this.businessCustFlowAnalyService.get(Czybm.class, this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/query/businessCustFlowAnaly/businessCustFlowAnaly_list")
			.addObject("employee", employee)
			.addObject("user", czybm);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		return new ModelAndView("admin/query/businessCustFlowAnaly/businessCustFlowAnaly_view")
			.addObject("employee", employee);
	}
	
}
