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
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjManRankService;

@Controller
@RequestMapping("/admin/prjManRank")
public class PrjManRankController extends BaseController{
	@Autowired
	private PrjManRankService pManRankService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		pManRankService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		pManRankService.findDetailPageBySql(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/query/prjManRank/prjManRank_list");
	}
	
	@RequestMapping("/goView")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response,
			Employee employee) throws Exception {
		return new ModelAndView("admin/query/prjManRank/prjManRank_view")
			.addObject("employee", employee);
	}
	
}
