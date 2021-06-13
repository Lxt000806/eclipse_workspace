package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.BaseItem;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseFee;
import com.house.home.service.design.CustomerService;

@Controller
@RequestMapping("/admin/jcfbkz")
public class JcfbkzController extends BaseController{
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/goJcysJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJcysJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_jcys(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailReport")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailReport(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_detailReport(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailReportMX")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailReportMX(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_detailReportMX(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJczjJqgrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJczjJqgrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_jczj(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getJczjDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJczjDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_jczjDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJczfbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJczfbJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.findPageBySql_jczfb(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getReqCtrlList")
	@ResponseBody
	public WebPage<Map<String,Object>> getReqCtrlList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customerService.getReqCtrlList(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/jcfbkz/jcfbkz_list");
	}
	
	@RequestMapping("/goCtrlPer")
	public ModelAndView goCtrlPer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_ctrlPer")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goButiePer")
	public ModelAndView goButiePer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_butiePer")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goReqCtrlPer")
	public ModelAndView goReqCtrlPer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_reqCtrlPer")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goAddReqCtrlPer")
	public ModelAndView goAddReqCtrlPer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_addReqCtrlPer")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goFastAddReqCtrlPer")
	public ModelAndView goFastAddReqCtrlPer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_fastAddReqCtrlPer")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goPlanDetail")
	public ModelAndView goPlanDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_planDetail")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goChgDetail")
	public ModelAndView goChgDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_chgDetail")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goAllCtrlDetail")
	public ModelAndView goAllCtrlDetail(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		if (StringUtils.isNotBlank(customer.getCode())){
			customer = customerService.get(Customer.class, customer.getCode());
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_allCtrlDetail")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goUpdateReqCtrlPer")
	public ModelAndView goUpdateReqCtrlPer(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		String baseItemDescr="";
		if(StringUtils.isNotBlank(customer.getBaseItemCode())){
			BaseItem baseItem = customerService.get(BaseItem.class, customer.getBaseItemCode());
			baseItemDescr = baseItem.getDescr();
		}
		return new ModelAndView("admin/project/jcfbkz/jcfbkz_addReqCtrlPer")
		.addObject("customer", customer).addObject("baseItemDescr",baseItemDescr);
	}
	
	@RequestMapping("/doSaveReqCtrlPer")
	public void doSaveReqCtrlPer(HttpServletRequest request ,HttpServletResponse response ,Customer customer){
		try {
			customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.customerService.doSaveReqCtrlPer(customer);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		
	}
	
	@RequestMapping("/doSaveCtrlPer")
	public void doSaveCtrlPer(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("部分到货开始");
		try {
			Customer ct = new Customer();
			if(StringUtils.isNotBlank(customer.getCode())){
				ct = customerService.get(Customer.class, customer.getCode());
				ct.setBaseCtrlPer(customer.getBaseCtrlPer());
				ct.setBaseCtrlAmount(customer.getBaseCtrlAmount());
				ct.setSpecifyBaseCtrl(customer.getSpecifyBaseCtrl());
				ct.setLastUpdate(new Date());
				ct.setLastUpdatedBy( this.getUserContext(request).getCzybh());
				customerService.update(ct);
				ServletUtils.outPrintSuccess(request, response, "操作成功");
			}else {
				ServletUtils.outPrintFail(request, response, "修改失败,无客户号");
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doSaveButiePer")
	public void doSaveButiePer(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("部分到货开始");
		try {
			Customer ct = new Customer();
			if(StringUtils.isNotBlank(customer.getCode())){
				ct = customerService.get(Customer.class, customer.getCode());
				ct.setProjectCtrlAdj(customer.getProjectCtrlAdj());
				ct.setCtrlAdjRemark(customer.getCtrlAdjRemark());
				ct.setLastUpdate(new Date());
				ct.setLastUpdatedBy( this.getUserContext(request).getCzybh());
				customerService.update(ct);
				ServletUtils.outPrintSuccess(request, response, "操作成功");
			}else {
				ServletUtils.outPrintFail(request, response, "修改失败,无客户号");
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer,String tableId){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		Customer ct = new Customer();
		ct = customerService.get(Customer.class, customer.getCode());
		String excelName = "";
		page.setPageSize(-1);
		if("dataTable".equals(tableId)){
			excelName ="基础预算发包";
			customerService.findPageBySql_jcys(page, customer);
		}
		if("dataTableJczj".equals(tableId)){
			excelName ="基础增减发包";
			customerService.findPageBySql_jczj(page, customer);
		}
		if("dataTableJczfb".equals(tableId)){
			excelName ="基础总发包发包";
			customerService.findPageBySql_jczfb(page, customer);
		}
		getExcelList(request);
		for(int i= 0;i<titleList.size();i++){
			if("人工".equals(titleList.get(i))||"材料".equals(titleList.get(i))||"总价".equals(titleList.get(i))){
				titleList.set(i, "预算金额|"+titleList.get(i));
			}
			if("材料费".equals(titleList.get(i))||"人工费".equals(titleList.get(i))||"总费用".equals(titleList.get(i))){
				titleList.set(i, "成本控制金额|"+titleList.get(i));
			}
		}
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				ct.getAddress()+"-"+excelName+"_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList,titleList , sumList);
	}
}

