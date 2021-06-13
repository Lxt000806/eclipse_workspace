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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntSetAnalyService;

@Controller
@RequestMapping("/admin/intSetAnaly")
public class IntSetAnalyController extends BaseController {
	@Autowired
	private IntSetAnalyService intSetAnalyService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intSetAnalyService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 领料明细查询
	 * @author	created by zb
	 * @date	2019-10-30--上午11:56:17
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIaDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getIaDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intSetAnalyService.findIaDetailBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return new ModelAndView("admin/query/intSetAnaly/intSetAnaly_list")
				.addObject("customer", customer);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return new ModelAndView("admin/query/intSetAnaly/intSetAnaly_iaView")
				.addObject("customer", customer);
	}
	
}
