package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

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
import com.house.home.entity.project.WorkCost;
import com.house.home.service.design.CustomerService;
import com.house.home.service.query.ItemShouldOrderService;
@Controller
@RequestMapping("/admin/itemShouldOrder")
public class ItemShouldOrderController extends BaseController {
	@Autowired
	private ItemShouldOrderService itemShouldOrderService;
	@Autowired
	private CustomerService customerService;
	/**
	 * 查询itemShouldOrderService表格数据
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
		itemShouldOrderService.findPageBySql(page, customer);
		List<Map<String,Object>> resultList=page.getResult();
		Map<String,Object> nowNumMap=new HashMap<String, Object>();
		Boolean flag=false;
		for(int i=0;i<resultList.size();i++){
			String custCode=resultList.get(i).get("custcode").toString();
			if(i>0){
				String custCode_next=resultList.get(i-1).get("custcode").toString();
				if(!custCode.equals(custCode_next)){
					flag=true;
				}else{
					flag=false;
				}
			}else{
				flag=true;
			}
			if(flag){
				nowNumMap=customerService.getShouldBanlance(custCode);
			}
			resultList.get(i).putAll(nowNumMap);
		}
		page.setResult(resultList);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setEndDateFrom(DateUtil.addWeek(new Date(), -1));
		customer.setEndDateTo(new Date());
		return new ModelAndView("admin/query/itemShouldOrder/itemShouldOrder_list").addObject("customer", customer);
	}
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/itemShouldOrder/itemShouldOrder_detail").addObject("customer", customer);
	}
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemShouldOrderService.findPageBySql_detail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemShouldOrderService.findPageBySql(page, customer);
		List<Map<String,Object>> resultList=page.getResult();
		Map<String,Object> nowNumMap=new HashMap<String, Object>();
		Boolean flag=false;
		for(int i=0;i<resultList.size();i++){
			String custCode=resultList.get(i).get("custcode").toString();
			if(i>0){
				String custCode_next=resultList.get(i-1).get("custcode").toString();
				if(!custCode.equals(custCode_next)){
					flag=true;
				}else{
					flag=false;
				}
			}else{
				flag=true;
			}
			if(flag){
				nowNumMap=customerService.getShouldBanlance(custCode);
			}
			resultList.get(i).putAll(nowNumMap);
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, resultList,
				"材料应下单楼盘明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
