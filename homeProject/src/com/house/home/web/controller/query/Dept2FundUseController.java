package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.query.Dept2FundUseService;
@Controller
@RequestMapping("/admin/dept2FundUse")
public class Dept2FundUseController extends BaseController {
	@Autowired
	private Dept2FundUseService dept2FundUseService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goPrepayFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSignJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findPrepayFeePageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goPurArrFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurArrFeeJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findPurArrFeePageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}

	
	@RequestMapping("/goOtherFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goOtherFeeJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findOtherFeePageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goPreAmountJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPreAmountJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findPreAmountPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goLaborFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLaborFeeJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		dept2FundUseService.findLaborFeePageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		purchase.setDateFrom1(DateUtil.startOfTheMonth(new Date()));
		purchase.setDateTo1(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/dept2FundUse/dept2FundUse_list");
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
			String postData = request.getParameter("postData");
			if(StringUtils.isNotEmpty(postData)){
				JSONObject obj = JSONObject.parseObject(postData);
				purchase.setSupplierDepartment2(obj.getString("supplierDepartment2"));
			}
		
		return new ModelAndView("admin/query/dept2FundUse/dept2FundUse_detail")
		.addObject("purchase", purchase);
				
	}
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doPrepayFeeExcel")
	public  void doSignExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findPrepayFeePageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用明细_预付金_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doPurArrFeeExcel")
	public  void doPurArrFeeExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findPurArrFeePageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用明细_采购到货_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doOtherFeeExcel")
	public  void doOtherFeeExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findOtherFeePageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用明细_其他费用_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doPreAmountExcel")
	public  void doPreAmountExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findPreAmountPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用明细_预付抵扣_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doLaborFeeExcel")
	public  void doLaborFeeExcel(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		dept2FundUseService.findLaborFeePageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"资金占用明细_人工费用_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
