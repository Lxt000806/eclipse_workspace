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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.query.DesignCustSourceAnalyService;

@Controller
@RequestMapping("/admin/designCustSourceAnaly") 
public class DesignCustSourceAnalyController extends BaseController { 
	
	@Autowired
	DesignCustSourceAnalyService designCustSourceAnalyService;
	@Autowired
	CustTypeService custTypeService;
	@Autowired
	EmployeeService employeeService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		designCustSourceAnalyService.findPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		designCustSourceAnalyService.findDetailPageBySql(page, employee, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * designCustSourceAnaly列表
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, 
			HttpServletResponse response,  Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date())); 
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		UserContext uc = getUserContext(request);
		Employee Employee=employeeService.get(Employee.class,uc.getEmnum()); 
		if(Employee!=null){
			customer.setDepartment1(Employee.getDepartment1());
			customer.setDepartment2(Employee.getDepartment2());
		}
		return new ModelAndView("admin/query/designCustSourceAnaly/designCustSourceAnaly_list")
			.addObject("customer",customer)
			.addObject("user",uc);	
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		return new ModelAndView("admin/query/designCustSourceAnaly/designCustSourceAnaly_view")
			.addObject("employee", employee);
	}
}
