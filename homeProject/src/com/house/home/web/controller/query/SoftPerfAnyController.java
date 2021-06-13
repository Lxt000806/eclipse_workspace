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
import com.house.home.service.query.SoftPerfAnyService;
@Controller
@RequestMapping("/admin/softPerfAny")
public class SoftPerfAnyController extends BaseController {
	@Autowired
	private SoftPerfAnyService softPerfAnyService;
	
	/**
	 * 查询softPerfAnyService表格数据
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
		softPerfAnyService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/softPerfAny/softPerfAny_list");
	}
	
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/softPerfAny/softPerfAny_detail").addObject("customer", customer);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfAnyService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
    @RequestMapping("/goCustomerJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goCustomerJqGrid(HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        softPerfAnyService.findCustomerPageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
}
