package com.house.home.web.controller.design;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.design.CustEval;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.service.design.CustEvalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/custEval")
public class CustEvalController extends BaseController{
	@Autowired
	private  CustEvalService custEvalService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustEval custEval) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custEvalService.findPageBySql(page, custEval);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custEval/custEval_list");
	}
	
}
