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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemSignCountService;
@Controller
@RequestMapping("/admin/itemSignCount")
public class ItemSignCountController extends BaseController {
	@Autowired
	private ItemSignCountService  itemSignCountService;
	/**
	 * 查询itemSignCountJqGrid表格数据
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
		itemSignCountService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSignCountService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		customer.setCustType("1");
		return new ModelAndView("admin/query/itemSignCount/itemSignCount_list");
	}
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){	
		return new ModelAndView("admin/query/itemSignCount/itemSignCount_view").addObject("customer", customer);
				
	}
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		itemSignCountService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料签单统计_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelDetail")
	public  void doExcelDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		itemSignCountService.findPageBySql_detail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料签单统计明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
