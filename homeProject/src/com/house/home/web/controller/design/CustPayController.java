package com.house.home.web.controller.design;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.ItemBean;
import com.house.home.bean.design.CustPayBean;
import com.house.home.entity.basic.BankPos;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.basic.RcvAct;
import com.house.home.entity.basic.Uom;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.CustPayPre;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;
import com.house.home.entity.finance.LaborFee;
import com.house.home.entity.insales.Brand;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustPayService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.finance.CustTaxService;

@Controller
@RequestMapping("/admin/custPay")
public class CustPayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustPayController.class);

	@Autowired
	private CustPayService custPayService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustTaxService custTaxService;
	@Autowired
	private XtdmService xtdmService ;
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goProcListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goProcListJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustPay custPay) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findProcListJqGrid(page, custPay);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/custPay/custPay_list");
	}
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping("/goRepairCard")
	public ModelAndView goRepairCard(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		Map<String, Object> map=custPayService.findListBySql(customer).get(0);
		return new ModelAndView("admin/finance/custPay/custPay_repairCard")
			.addObject("customer", customer).addObject("map", map);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goViewPay")
	public ModelAndView goViewPay(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		customer.setM_umState("V");
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
		customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(customer.getCode()));
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		
		// 20200521 modify by xzp double?????????????????????????????????324449.00-56724.72-273619.00?????????107554.71999999997????????????BigDecimal
//		customerPayMap.put("wdz", Double.parseDouble(customerPayMap.get("wdz").toString())+Double.parseDouble(customerPayMap.get("zjzje").toString())-Double.parseDouble(customerPayMap.get("haspay").toString()));
		BigDecimal wdz = new BigDecimal(customerPayMap.get("wdz").toString());
		BigDecimal zjzje = new BigDecimal(customerPayMap.get("zjzje").toString());
		BigDecimal haspay = new BigDecimal(customerPayMap.get("haspay").toString());
		customerPayMap.put("wdz", wdz.add(zjzje).subtract(haspay).doubleValue());
		
		List<Map<String, Object>> list=custPayService.findDesignFeeType(customer);
		if(list.size()>0)customerPayMap.put("designFeeType",list.get(0).get("DesignFeeType").toString());
		DecimalFormat df = new DecimalFormat("##.####");    
		Double d = new Double(customerPayMap.get("contractfee").toString()); 
		Double d2 = new Double(customerPayMap.get("designfee").toString()); 
		customerPayMap.put("contractfee",df.format(d)+".0");
		customerPayMap.put("contractfeedesignfee",df.format(d+d2)+".0");
		return new ModelAndView("admin/finance/custPay/custPay_custPay")
		.addObject("customer", customer)
		.addObject("customerPayMap", customerPayMap)
		.addObject("costRight", costRight)
		.addObject("itemRight", itemRight)
		.addObject("balanceMap", balanceMap);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goPayPlan")
	public ModelAndView goPayPlan(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		List<Map<String, Object>> list=custPayService.findDesignFeeType(customer);
		if(list.size()>0)customerPayMap.put("designFeeType",list.get(0).get("DesignFeeType").toString());
		return new ModelAndView("admin/finance/custPay/custPay_payPlan").addObject("customerPayMap", customerPayMap);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping(value = {"/goCustPay","/goReturnPayCustPay"})
	public ModelAndView goCustPay(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		UserContext uc = this.getUserContext(request);
		String costRight = uc.getCostRight();
		String itemRight = uc.getItemRight();
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
		customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(customer.getCode()));
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		
		// 20200521 modify by xzp double?????????????????????????????????324449.00-56724.72-273619.00?????????107554.71999999997????????????BigDecimal
//		customerPayMap.put("wdz", Double.parseDouble(customerPayMap.get("wdz").toString())+Double.parseDouble(customerPayMap.get("zjzje").toString())-Double.parseDouble(customerPayMap.get("haspay").toString()));
		BigDecimal wdz = new BigDecimal(customerPayMap.get("wdz").toString());
		BigDecimal zjzje = new BigDecimal(customerPayMap.get("zjzje").toString());
		BigDecimal haspay = new BigDecimal(customerPayMap.get("haspay").toString());
		customerPayMap.put("wdz", wdz.add(zjzje).subtract(haspay).doubleValue());
		
		List<Map<String, Object>> list=custPayService.findDesignFeeType(customer);
		DecimalFormat df = new DecimalFormat("##.####");    
		Double d = new Double(customerPayMap.get("contractfee").toString()); 
		Double d2 = new Double(customerPayMap.get("designfee").toString()); 
		customerPayMap.put("contractfee",df.format(d)+".0");
		customerPayMap.put("contractfeedesignfee",df.format(d+d2)+".0");
		if(list.size()>0)customerPayMap.put("designFeeType",list.get(0).get("DesignFeeType").toString());
		return new ModelAndView("admin/finance/custPay/custPay_custPay")
		.addObject("customer", customer)
		.addObject("customerPayMap", customerPayMap)
		.addObject("costRight", costRight)
		.addObject("itemRight", itemRight)
		.addObject("balanceMap", balanceMap);
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdateCust")
	public ModelAndView goUpdateCust(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		Map<String, Object> map=custPayService.findListBySql(customer).get(0);
		if(!"4".equals(map.get("Status").toString())){
			map.put("unShowValue", "4,5");
		}
		return new ModelAndView("admin/finance/custPay/custPay_updateCust")
			.addObject("customer", customer).addObject("map", map);
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response, 
			Customer customer ){
		return new ModelAndView("admin/finance/custPay/custPay_detailQuery")
			.addObject("customer", customer);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			CustPay custPay ){
		logger.debug("???????????????CustPay??????");
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(custPay.getCustCode());
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(custPay.getCustCode()));
		custPay.setProcedureFee(0.0);
		custPay.setAddDate(new Date());
		custPay.setDate(new Date());
		custPay.setM_umState("A");
		return new ModelAndView("admin/finance/custPay/custPay_payinfo_add")
			.addObject("custPay", custPay).addObject("customerPayMap", customerPayMap);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String pk){
		logger.debug("???????????????CustPay??????");
		CustPay custPay = null;
		if (StringUtils.isNotBlank(pk)){
			custPay = custPayService.get(CustPay.class, Integer.parseInt(pk));
		}else{
			custPay = new CustPay();
		}
		custPay.setM_umState("M");
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(custPay.getCustCode());
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(custPay.getCustCode()));
		return new ModelAndView("admin/finance/custPay/custPay_payinfo_add")
			.addObject("custPay", custPay).addObject("customerPayMap", customerPayMap);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			String pk){
		logger.debug("???????????????CustPay??????");
		CustPay custPay = null;
		if (StringUtils.isNotBlank(pk)){
			custPay = custPayService.get(CustPay.class, Integer.parseInt(pk));
		}else{
			custPay = new CustPay();
		}
		custPay.setM_umState("C");
		custPay.setAmount(null);
		custPay.setProcedureFee(0.0);
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(custPay.getCustCode());
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(custPay.getCustCode()));
		return new ModelAndView("admin/finance/custPay/custPay_payinfo_add")
			.addObject("custPay", custPay).addObject("customerPayMap", customerPayMap);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String pk){
		logger.debug("???????????????CustPay??????");
		CustPay custPay = null;
		if (StringUtils.isNotBlank(pk)){
			custPay = custPayService.get(CustPay.class, Integer.parseInt(pk));
		}else{
			custPay = new CustPay();
		}
		custPay.setM_umState("V");
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(custPay.getCustCode());
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(custPay.getCustCode()));
		return new ModelAndView("admin/finance/custPay/custPay_payinfo_add")
			.addObject("custPay", custPay).addObject("customerPayMap", customerPayMap);
	}
	
	/**
	 * ?????????????????????????????????
	 * @author	created by zb
	 * @date	2018-12-9--??????2:51:11
	 */
	@RequestMapping("/goIsPubReturn")
	public ModelAndView goIsPubReturn(HttpServletRequest request, HttpServletResponse response, 
			String isPubReturn, String custCode){
		logger.debug("???????????????????????????");
		return new ModelAndView("admin/finance/custPay/custPay_isPubReturn")
			.addObject("isPubReturn", isPubReturn)
			.addObject("custCode", custCode);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustPay custPay){
		logger.debug("??????CustPay??????");
		try{
			custPay.setIsCheckOut("0");
			custPay.setLastUpdate(new Date());
			custPay.setLastUpdatedBy(getUserContext(request).getCzybh());
			custPay.setExpired("F");
			custPay.setActionLog("ADD");
			this.custPayService.save(custPay);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustPay??????");
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustPay custPay){
		logger.debug("??????CustPay??????");
		try{
			custPay.setIsCheckOut("0");
			custPay.setExpired("F");
			custPay.setActionLog("EDIT");
			custPay.setLastUpdate(new Date());
			custPay.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.custPayService.update(custPay);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????CustPay??????");
		}
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????CustPay??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CustPay??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CustPay custPay= custPayService.get(CustPay.class, Integer.parseInt(deleteId));
				if(custPay == null)
					continue;
				custPayService.delete(custPay);
			}
		}
		logger.debug("??????CustPay IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}
	/**
	 *custPay??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custPayService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * ??????????????????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChgInfoJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goChgInfoJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findChgInfoPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChgAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goChgAppJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findChgAppPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPayInfoJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPayInfoJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findPayInfoPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ???????????????pos???????????????
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/actAndPosByAuthority/{type}/{pCode}") //??????????????????1,2,3
	@ResponseBody
	public JSONObject getActAndPosByAuthority(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> list = this.custPayService.findActAndPosByAuthority(type, pCode, uc);
		return this.out(list, true);
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param payNo
	 * @return
	 */
	@RequestMapping("/checkPayNo")
	@ResponseBody
	public List<Map<String, Object>> checkPayNo(HttpServletRequest request,String payNo,String pk){
		return custPayService.findPayNo(payNo,pk);
	}
	/**
	 * ???bankpos
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("/findBankPos")
	@ResponseBody
	public List<Map<String, Object>> findBankPos(HttpServletRequest request,String code){
		return custPayService.findBankPos(code);
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/updatePayPlan")
	public void updatePayPlan(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("????????????????????????");
		try{
			this.custPayService.updatePayPlan(customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/doUpdateCust")
	public void doSave(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("??????");		
		try {	
			customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.custPayService.doUpdateProc(customer);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"????????????");
				}else{
					ServletUtils.outPrintFail(request, response,"???????????????"+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	/**
	 * ???????????????
	 * @param request
	 * @param customer
	 * @return
	 */
	@RequestMapping("/findCustStatus")
	@ResponseBody
	public List<Map<String, Object>> findCustStatus(HttpServletRequest request,Customer customer){
		return custPayService.findCustStatus(customer);
	}
	/**
	 * ???????????????
	 * @param request
	 * @param response
	 * @param customer
	 */
	@RequestMapping("/updateRepairCard")
	public void updateRepairCard(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("?????????????????????");
		try{
			this.custPayService.updateRepairCard(customer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "?????????????????????");
		}
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailQueryJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustPay custPay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findDetailQueryBySql(page, custPay);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *??????????????????????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailQueryExcel")
	public void doDetailQueryExcel(HttpServletRequest request, 
			HttpServletResponse response, CustPay custPay){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custPayService.findDetailQueryBySql(page, custPay);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2018-12-7--??????5:14:24
	 */
	@RequestMapping("/doIsPubReturnSave")
	@ResponseBody
	public void doIsPubReturnSave(HttpServletRequest request, 
			HttpServletResponse response, String isPubReturn, String custCode) {
		// ????????????????????????????????????
		CustTax custTax = this.custTaxService.get(CustTax.class, custCode);
		if (null==custTax) {
			ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
			return;
		}
		Boolean success = this.custPayService.doIsPubReturnSave(isPubReturn, custCode);
		if (success) {
			ServletUtils.outPrintSuccess(request, response, "????????????");
		}
		ServletUtils.outPrintFail(request, response, "????????????");
	}
	/**
	 * ???????????????????????????
	 * @return
	 */            
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			CustPay custPay){
		logger.debug("????????????????????????????????????");
		
		return new ModelAndView("admin/finance/custPay/custPay_qPrint").addObject("custPay",custPay);
	
	}
	
	@RequestMapping("/goQPrintJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getQPrintJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findPageByQPrintSql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ????????????excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goImportExcel")
	public ModelAndView goItemImportExcel(HttpServletRequest request,
			HttpServletResponse response,CustPay custPay) throws Exception {
		return new ModelAndView("admin/finance/custPay/custPay_payinfo_importExcel").addObject("custPay",custPay);
	}
	
	/**
	 * excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<CustPayBean> eUtils=new ExcelImportUtils<CustPayBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("POS???");
		titleList.add("?????????");
		titleList.add("????????????");
		titleList.add("??????");
		titleList.add("??????");
		try {		
			List<CustPayBean> result=eUtils.importExcel(in,CustPayBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(CustPayBean custPayBean:result){
				/*if(StringUtils.isNotBlank(custPayBean.getError())){
					map.put("success", false);
					map.put("returnInfo", custPayBean.getError());
					map.put("hasInvalid", true);
					return map;
				}*/
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "??????");
				data.put("date", custPayBean.getDate());
				data.put("amount", custPayBean.getAmount()==null?0:custPayBean.getAmount());
				data.put("procedurefee", custPayBean.getProcedureFee()==null?0:custPayBean.getProcedureFee());
				data.put("payno", custPayBean.getPayNo());
				data.put("remarks", custPayBean.getRemarks());
				
				if (StringUtils.isNotBlank(custPayBean.getCustCode())) {
				    data.put("custcode", custPayBean.getCustCode());
				    
				    Customer customer = customerService.get(Customer.class, custPayBean.getCustCode());
				    
				    if (customer == null) {
                        data.put("isinvalid", "0");
                        data.put("isinvaliddescr", "??????????????????????????????");
                    }
				} else {
                    data.put("isinvalid", "0");
                    data.put("isinvaliddescr", "?????????????????????????????????");
                }
				
				if(StringUtils.isNotBlank(custPayBean.getRcvAct())){
					data.put("rcvact", custPayBean.getRcvAct());
					RcvAct rcvAct=custPayService.get(RcvAct.class,custPayBean.getRcvAct());
					if (rcvAct==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "???????????????????????????");
					}else{
						data.put("rcvactdescr", rcvAct.getDescr());
						if("".equals(rcvAct.getDescr())||!"F".equals(rcvAct.getExpired())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "???????????????????????????");
						}
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "?????????????????????????????????");
				}
				if(StringUtils.isNotBlank(custPayBean.getPosCode())){
					data.put("poscode", custPayBean.getPosCode());
					BankPos bankPos=custPayService.get(BankPos.class,custPayBean.getPosCode());
					if(bankPos==null){
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "?????????POS?????????");
					}else{
						data.put("posdescr", bankPos.getDescr());
						if("".equals(bankPos.getDescr())||!"F".equals(bankPos.getExpired())){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????POS?????????");
						}
					}
				}
				if(StringUtils.isNotBlank(custPayBean.getType())){
					 data.put("type", custPayBean.getType());
					 Xtdm xtdmType=xtdmService.getByIdAndCbm("CPTRANTYPE",custPayBean.getType());
					 if(xtdmType==null){
							data.put("isinvalid","0");
							data.put("isinvaliddescr", "?????????????????????");
					 }else{
						 data.put("typedescr", xtdmType.getNote()); 
					 }	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "???????????????????????????");
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo","???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	

	@RequestMapping("/getCheckPayInfo")
	@ResponseBody
	public String getCheckPayInfo(CustPay custPay){
		
		List<Map<String , Object>> list = custPayService.getPayInfo(custPay);
		
		if(list != null && list.size()>0){
			return "false";
		}
		
		return "true";
	}
	
	@RequestMapping("/goViewProcTrack")
	public ModelAndView goViewProcTrack(HttpServletRequest request,
			HttpServletResponse response,CustPay custPay) throws Exception {
		
		return new ModelAndView("admin/finance/custPay/custPay_viewProcTrack").addObject("custPay", custPay);
	}
	
	
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,CustPay custPay){
		logger.debug("????????????????????????");
		try {
			
			custPay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.custPayService.doSaveBatch(custPay);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPayBillJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPayBillJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustPay custPay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custPayService.findPayBillQueryBySql(page, custPay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param pk
	 * @param czybh
	 */
	@RequestMapping("/doUpatePrintCZY")
	public void doUpatePrintCZY(HttpServletRequest request, HttpServletResponse response, String pk, String czybh){
		try{
			CustPay custPay = null;
			if (StringUtils.isNotBlank(pk)){
				custPay = custPayService.get(CustPay.class, Integer.parseInt(pk));
			}
			if(custPay==null){
				ServletUtils.outPrintFail(request, response, "????????????????????????");
			}
			custPay.setPrintCZY(czybh);
			custPay.setPrintDate(new Date());
			custPay.setLastUpdatedBy(czybh);
			custPay.setLastUpdate(new Date());
			custPayService.update(custPay);
			ServletUtils.outPrintSuccess(request, response, "????????????????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	@RequestMapping("/updateLatestPrintInfo")
    public void updateLatestPrintInfo(HttpServletRequest request,
            HttpServletResponse response, int custPayPk) {
        
        try {
            CustPay custPay = custPayService.get(CustPay.class, custPayPk);
            
            if (custPay == null) {
                ServletUtils.outPrintFail(request, response, "???????????????????????????????????????!");
                return;
            }
            
            custPay.setPrintCZY(getUserContext(request).getCzybh());
            custPay.setPrintDate(new Date());
            
            custPayService.update(custPay);
            ServletUtils.outPrintSuccess(request, response, "???????????????");
            
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "???????????????????????????!");
        }
        
    }
	
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doQPrintExcel")
	public void doQPrintExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custPayService.findPageByQPrintSql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
