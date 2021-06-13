package com.house.home.web.controller.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.TaxPayee;
import com.house.home.service.basic.TaxPayeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/admin/taxPayee")
public class TaxPayeeController extends BaseController{
	@Autowired
	private  TaxPayeeService taxPayeeService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,TaxPayee taxPayee) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		taxPayeeService.findPageBySql(page, taxPayee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getTaxPayeeList")
	@ResponseBody
	public List<Map<String, Object>> getTaxPayeeList(HttpServletRequest request,HttpServletResponse response,
		TaxPayee taxPayee) throws Exception{
		logger.debug("ajax获取数据");   
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = taxPayeeService.getTaxPayeeList();
		
		return list;
	}
	
}
