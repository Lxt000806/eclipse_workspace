package com.house.home.web.controller.query;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemChg;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.service.design.CustSceneDesiService;
import com.house.home.service.query.RzxscxService;

@Controller
@RequestMapping("/admin/rzxscx")
public class RzxscxController extends BaseController{
	
	@Autowired
	private RzxscxService rzxscxService;
	@Autowired
	private CustSceneDesiService custSceneDesiService;
	/**
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		rzxscxService.findPageBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goChgDetailGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getChgDetailGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		rzxscxService.findChgDetailBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goItemPlanJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemPlanJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		rzxscxService.findItemPlanBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSaleDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSaleDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		rzxscxService.findSaleDetailBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goItemReqJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemReqJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemChg itemChg) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		rzxscxService.findItemReqBySql(page, itemChg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response,ItemChg itemChg) throws Exception {
		logger.debug("材料销售分析");
		itemChg.setDateFrom(new Timestamp(DateUtil.startOfTheMonth(new Date()).getTime()));
		itemChg.setDateTo(new Timestamp(new Date().getTime()));
		String isAddCustType=custSceneDesiService.getIsAddCustType();
		return new ModelAndView("admin/query/rzxscx/rzxscx_list").addObject("itemChg", itemChg)
				.addObject("isAddCustType", isAddCustType)
				.addObject("itemRight", this.getUserContext(request).getItemRight())
				.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,
			ItemChg itemChg) throws Exception {
		logger.debug("销售明细");
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		
		return new ModelAndView("admin/query/rzxscx/rzxscx_detail").addObject("itemChg", itemChg);
	
	}
	
	@RequestMapping("/getItem1ByItem2")
	@ResponseBody 	
	public String getItem1ByItem2(String itemType2){
		logger.debug("ajax获取数据");  
		String itemType1=this.rzxscxService.getItem1byItem2(itemType2);
		return 	itemType1;
	
	}
	
	@RequestMapping("/getDiscounts")
	@ResponseBody 	
	public String[] getDiscounts(HttpServletRequest request,HttpServletResponse response,ItemChg itemChg){
		logger.debug("ajax获取数据"); 
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		String YHJE=this.rzxscxService.getYHJEbySql(itemChg);
		String DJYH=this.rzxscxService.getDLYHbySql(itemChg);
		String YSYH=this.rzxscxService.getYSYHbySql(itemChg);
		String[] str={YHJE==""?"0":YHJE,DJYH==""?"0":DJYH,YSYH==""?"0":YSYH};
		return str;
	
	}
	 
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemChg itemChg){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		rzxscxService.findPageBySql(page, itemChg);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装销售_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemChg itemChg,String tableName){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemChg.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		itemChg.setDateTo(DateFormatString(request.getParameter("dateTo")));
		if("材料增减".equals(tableName)){
			rzxscxService.findChgDetailBySql(page, itemChg);
		}else  if("材料预算".equals(tableName)){
			rzxscxService.findItemPlanBySql(page, itemChg);
		}else if("独立销售明细".equals(tableName)){
			rzxscxService.findSaleDetailBySql(page, itemChg);
		}else if("材料需求".equals(tableName)){
			rzxscxService.findItemReqBySql(page, itemChg);
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				tableName+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}
	
	
	
	
	
	
}
