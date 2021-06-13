package com.house.home.web.controller.query;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.query.ItemSaleAnalyseService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Map;

import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.commi.DesignCommiRule;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/itemSaleAnalyse")
public class ItemSaleAnalyseController extends BaseController{
	@Autowired
	private ItemSaleAnalyseService itemSaleAnalyseService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,Item item) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		itemSaleAnalyseService.findPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goChgJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goChgJqGrid(HttpServletRequest request, 
			HttpServletResponse response,Item item) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		itemSaleAnalyseService.findChgPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPlanJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> goPlanJqGrid(HttpServletRequest request, 
			HttpServletResponse response,Item item) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		itemSaleAnalyseService.findPlanPageBySql(page, item);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		item.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		item.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		
		return new ModelAndView("admin/query/itemSaleAnalyse/itemSaleAnalyse_list")
		.addObject("item", item);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,Item item) throws Exception {
		
		return new ModelAndView("admin/query/itemSaleAnalyse/itemSaleAnalyse_view")
		.addObject("item", item);
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Item item){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemSaleAnalyseService.findPageBySql(page, item);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料销售分析按单品_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
