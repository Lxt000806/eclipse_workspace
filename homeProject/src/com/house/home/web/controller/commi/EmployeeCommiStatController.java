package com.house.home.web.controller.commi;

import java.util.Calendar;
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
import com.house.home.entity.basic.Employee;
import com.house.home.service.commi.EmployeeCommiStatService;

@Controller
@RequestMapping("/admin/employeeCommiStat")
public class EmployeeCommiStatController extends BaseController {
	
	@Autowired
	private EmployeeCommiStatService employeeCommiStatService;
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		 	
			Calendar cal = Calendar.getInstance();  
	        int year = cal.get(Calendar.YEAR);
	        int month = cal.get(Calendar.MONTH);
	        employee.setBeginMon(year*100 + month); 
	        employee.setEndMon(year*100 + month); 
		return new ModelAndView("admin/commi/employeeCommiStat/employeeCommiStat_list")
			.addObject("employee", employee);
	}
	
	@RequestMapping({"/goView","goViewPersonCommiDtl"})
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response, Employee employee,String showTabs){
		 int beginYear = employee.getBeginMon()/100;
	     int beginMon = employee.getBeginMon()%100;
	     int endYear = employee.getEndMon()/100 ;
	     int endMon = employee.getEndMon()%100;
	     employee.setDateFrom(DateUtil.startOfAMonth(beginYear, beginMon-1));
	     employee.setDateTo(DateUtil.endOfAMonth(endYear, endMon-1));
		return new ModelAndView("admin/commi/employeeCommiStat/employeeCommiStat_detail")
		.addObject("employee", employee).addObject("showTabs", showTabs);			
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
			HttpServletResponse response,Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeCommiStatService.findPageBySql(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}	
}
