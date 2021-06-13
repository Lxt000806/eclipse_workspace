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
import com.house.home.service.query.DispatchCenterKPIService;
@Controller
@RequestMapping("/admin/dispatchCenterKPI")
public class DispatchCenterKPIController extends BaseController {
	@Autowired
	private DispatchCenterKPIService dispatchCenterKPIService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询图纸审核明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridPicConfirmDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPicConfirmDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_picConfirmDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询开工明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridBeginDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridBeginDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_beginDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询结算明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridCheckDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCheckDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_checkDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询解单明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridSpecItemReqDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridSpecItemReqDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_specItemReqDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 定责明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridFixDutyDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridFixDutyDetailDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_fixDutyDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 调度明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridDispatchDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDispatchDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_dispatchDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 调度明细JqGrid表格数据
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goJqGridMainCheckDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMainCheckDetail(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dispatchCenterKPIService.findPageBySql_mainCheckDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		
		return new ModelAndView("admin/query/dispatchCenterKPI/dispatchCenterKPI_list");
	}
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		String sUrlSuffix="";
		if("1".equals(customer.getStatistcsMethod())){
			sUrlSuffix="picConfirm";
		}else if("2".equals(customer.getStatistcsMethod())){
			sUrlSuffix="dispatch";
		}else if("3".equals(customer.getStatistcsMethod())){
			sUrlSuffix="specItemReq";
		}else{
			sUrlSuffix="beginAndCheck";
		}
		return new ModelAndView("admin/query/dispatchCenterKPI/dispatchCenterKPI_"+sUrlSuffix)
		.addObject("customer", customer);		
	}

	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		dispatchCenterKPIService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单排行_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelPicConfirmDetail")
	public  void doExcelPicConfirmDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_picConfirmDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"图纸审核明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelBeginDetail")
	public  void doExceBeginDetaill(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_beginDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"开工明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelCheckDetail")
	public  void doExcelCheckDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_checkDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"结算明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelSpecItemReqDetail")
	public  void doExcelSpecItemReqDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_specItemReqDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"解单明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doExcelFixDutyDetail")
	public  void doExcelFixDutyDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_fixDutyDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"定责明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelDispatchDetail")
	public  void doExcelDispatchDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_dispatchDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"调度明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelMainCheckDetail")
	public  void doExcelMainCheckDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dispatchCenterKPIService.findPageBySql_mainCheckDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材结算明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}	
}
