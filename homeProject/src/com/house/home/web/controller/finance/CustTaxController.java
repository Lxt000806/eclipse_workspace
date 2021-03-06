package com.house.home.web.controller.finance;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.insales.CustInvoiceBean;
import com.house.home.bean.insales.CustLaborInvoiceBean;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.finance.CustTaxService;
@Controller
@RequestMapping("/admin/custTax")
public class CustTaxController extends BaseController{
	@Autowired
	private  CustTaxService custTaxService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTaxService.findPageBySql(page, custTax, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  ????????????????????????
	 * @author	created by zb
	 * @date	2018-8-14--??????11:25:21
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax) throws Exception {
		
		Page<Map<String, Object>> page;
		page = this.newPageForJqGrid(request);
		try {
			custTaxService.findDetailByCode(page, custTax);
			return new WebPage<Map<String,Object>>(page);
		} catch (Exception e) {
			//????????????????????????????????????List???result???
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			page.setResult(arrayList);
			return new WebPage<Map<String,Object>>(page);
		}
		
	}
	
	/**
	 * @Description: ??????????????????
	 */
	@RequestMapping("/goInvoiceJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getInvoiceJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTaxService.findInvoicePageBySql(page, custTax, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * @Description: ????????????????????????
	 */
	@RequestMapping("/goLaborJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLaborJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTaxService.findLaborPageBySql(page, custTax);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * @Description:  ????????????????????????
	 */
	@RequestMapping("/goCustPayJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustPayJqGrid(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTaxService.findCustPayPageBySql(page, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/custTax/custTax_list");
	}
	
	/**
	 * @Description:  ??????????????????
	 * @author	created by zb
	 * @date	2018-8-13--??????6:05:38
	 * @param request
	 * @param response
	 * @param custTax ???????????????
	 * @param keys ?????????????????????
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goInvoiceWin")
	public ModelAndView goInvoiceSave(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax, String keys,String custDescr) throws Exception {
		if(StringUtils.isNotBlank(custTax.getInvoiceCode())){
			custTax.setInvoiceCode(custTax.getInvoiceCode().trim());
		}
		if(StringUtils.isNotBlank(custTax.getBuyer())){
			custTax.setBuyer(custTax.getBuyer().trim());
		}
		if(StringUtils.isNotBlank(custTax.getTaxService())){
			custTax.setTaxService(custTax.getTaxService().trim());
		}
		if ("A".equals(custTax.getM_umState())) {
			return new ModelAndView("admin/finance/custTax/custTax_invoice_save")
				.addObject("keys", keys)
				.addObject("custDescr", custDescr);	
		} else {
			return new ModelAndView("admin/finance/custTax/custTax_invoice_update")
				.addObject("custTax", custTax)
				.addObject("keys", keys);
		}
		
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/custTax/custTax_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,CustTax custTax) throws Exception {
		CustTax cTax = custTaxService.get(CustTax.class, custTax.getCustCode());
		Customer customer  = customerService.get(Customer.class, custTax.getCustCode());
		cTax.setPayeeCode(cTax.getPayeeCode().trim());
		cTax.setM_umState(custTax.getM_umState());

		if (StringUtils.isNotBlank(cTax.getLaborCompny())) {
            cTax.setLaborCompny(cTax.getLaborCompny().trim());
        }
		
		return new ModelAndView("admin/finance/custTax/custTax_update")
			.addObject("custTax", cTax)
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,CustTax custTax) throws Exception {
		CustTax cTax = custTaxService.get(CustTax.class, custTax.getCustCode());
		Customer customer  = customerService.get(Customer.class, custTax.getCustCode());
		cTax.setPayeeCode(cTax.getPayeeCode().trim());
		cTax.setM_umState(custTax.getM_umState());
		
		if (StringUtils.isNotBlank(cTax.getLaborCompny())) {
            cTax.setLaborCompny(cTax.getLaborCompny().trim());
        }
		
		return new ModelAndView("admin/finance/custTax/custTax_update")
			.addObject("custTax", cTax)
			.addObject("customer", customer);
	}
	
	// ????????????
	@RequestMapping("/goFindInvoice")
	public ModelAndView goFindInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/custTax/custTax_findInvoice");
	}
	
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-12-7--??????11:03:39
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustInvoice")
	public ModelAndView goCustInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/custTax/custTax_custInvoice");
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,CustTax custTax){
		logger.debug("??????CustTax??????");
		try{
			CustTax cTax = this.custTaxService.get(CustTax.class, custTax.getCustCode());
			Customer customer = this.customerService.get(Customer.class, custTax.getCustCode());
			if (null == customer) {
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
			if (cTax != null) {
				ServletUtils.outPrintFail(request, response, "????????????????????????");
				return;
			}
			custTax.setM_umState("A");
			custTax.setLastUpdate(new Date());
			custTax.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custTax.setExpired("F");
			custTax.setActionLog("ADD");
			Result result = this.custTaxService.doSave(custTax);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustTax??????");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,
			CustTax custTax){
		logger.debug("??????CustTax??????");
		try{
			// ?????????????????????????????????????????????
			if (!custTax.getOldCustCode().trim().equals(custTax.getCustCode().trim())) {
				CustTax cTax = this.custTaxService.get(CustTax.class, custTax.getCustCode().trim());
				if (cTax != null) {
					ServletUtils.outPrintFail(request, response,"????????????????????????");
					return;
				}
			}
			custTax.setM_umState("M");
			custTax.setActionLog("Edit");
			custTax.setLastUpdate(new Date());
			custTax.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			System.out.println(custTax.getDetailJson());
			Result result = this.custTaxService.doSave(custTax);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustTax??????");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????CustTax??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					// ????????????????????????????????????
					CustTax custTax = this.custTaxService.get(CustTax.class, deleteId);
					// ??????????????????????????????????????????????????????
					Page<Map<String,Object>> page = this.newPageForJqGrid(request);
					custTaxService.findDetailByCode(page, custTax);
					if (page.getTotalCount() > 0) {
						ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
						return;
					}
					custTax.setM_umState("D");			
					Result result = custTaxService.doSave(custTax);
					if ("1".equals(result.getCode())){
						ServletUtils.outPrintSuccess(request, response, "????????????");
					}else{
						ServletUtils.outPrintFail(request, response, result.getInfo());
					}
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustTax??????");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustTax custTax, Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		custTaxService.findPageBySql(page, custTax, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doInvoiceExcel")
	public void doInvoiceExcel(HttpServletRequest request ,HttpServletResponse response,
			CustTax custTax, Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		custTaxService.findInvoicePageBySql(page, custTax, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goLaborAdd")
	public ModelAndView goLaborAdd(HttpServletRequest request,
			HttpServletResponse response, CustTax custTax) throws Exception {
		if("A".equals(custTax.getM_umState())){
			custTax.setAmount(new BigDecimal(0));
		}
		return new ModelAndView("admin/finance/custTax/custTax_labor_save")
		.addObject("custTax", custTax);	
		
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-12-7--??????3:34:35
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		DiskFileItemFactory fac = new DiskFileItemFactory();// ??????FileItem ???????????????????????????
		ServletFileUpload upload = new ServletFileUpload(fac);// ???????????????
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);// ????????????????????????
		Iterator it = fileList.iterator();// ?????????
		InputStream in = getInputStream(it);
		List<String> titleList=getTitleList();
		return checkImportData(in, titleList);
	}
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????11:36:41
	 * @param it
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public InputStream getInputStream(Iterator it) {
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			String fieldName = obit.getFieldName();
//			String fieldValue = obit.getString();
			if ("file".equals(fieldName)){
				try {
					in=obit.getInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return in;
	}
	/**
	 * ?????????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????11:29:57
	 * @return
	 */
	public List<String> getTitleList() {
		List<String> titleList = new ArrayList<String>();
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("?????????");
		titleList.add("????????????");
		return titleList;
	}
	/**
	 * ???????????????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????11:22:30
	 * @param in
	 * @param titleList
	 * @return
	 */
	public Map<String, Object> checkImportData(InputStream in, List<String> titleList) {
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<CustInvoiceBean> eUtils=new ExcelImportUtils<CustInvoiceBean>();// ??????EXCEL????????????, ????????????pojo ??????
		try {
			List<CustInvoiceBean> result=eUtils.importExcel(in, CustInvoiceBean.class, titleList);// ??????????????????????????????null????????????
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(CustInvoiceBean custInvoiceBean:result){
				if(StringUtils.isNotBlank(custInvoiceBean.getError())){
					map.put("success", false);
					map.put("returnInfo", custInvoiceBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=checkExcelColumnData(custInvoiceBean);
				if("1".equals(data.get("isinvalid"))){// ???hasInvalid???1???????????????????????????
					map.put("hasInvalid", true);
				}else{
					excelDataIntoMap(data, custInvoiceBean);
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
			map.put("returnInfo", "???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	/**
	 * ??????Excel?????????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????11:42:56
	 * @param custInvoiceBean
	 * @return
	 */
	public Map<String,Object> checkExcelColumnData(CustInvoiceBean custInvoiceBean) {
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("isinvalid", "0");// ??????
		if (StringUtils.isNotBlank(custInvoiceBean.getCustcode())) {
			CustTax custTax = custTaxService.get(CustTax.class, custInvoiceBean.getCustcode().trim());
			if(null==custTax) {
				data = setErrorInformation(data, "??????,?????????????????????????????????");
			} else {
				if (null == custInvoiceBean.getDate()) {
					setErrorInformation(data, "??????,????????????????????????");
				} else if (StringUtils.isBlank(custInvoiceBean.getInvoiceno())) {
					setErrorInformation(data, "??????,?????????????????????");
				} else if (StringUtils.isBlank(custInvoiceBean.getTaxservice())) {
					setErrorInformation(data, "??????,??????????????????????????????");
				} else if (0.0 == custInvoiceBean.getAmount()) {
					setErrorInformation(data, "??????,????????????????????????");
				} else if (0.0 == custInvoiceBean.getTaxamount()) {
					setErrorInformation(data, "??????,??????????????????");
				} else {
					checkInvoiceNoTaxServiceRepeat(data, custTax, custInvoiceBean);
				}
			}
		} else {
			setErrorInformation(data, "??????,????????????????????????");
		}
		return data;
	}
	/**
	 * ????????????????????????????????????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????5:34:49
	 * @param data
	 * @param custTax
	 * @return
	 */
	public Map<String,Object> checkInvoiceNoTaxServiceRepeat(final Map<String,Object> data, CustTax custTax,
			CustInvoiceBean custInvoiceBean) {
		Page<Map<String, Object>> page= new Page<Map<String, Object>>();
		this.custTaxService.findDetailByCode(page, custTax);
		if (page.getTotalCount()>0) {
			for (Map<String, Object> map : page.getResult()) {
				if (custInvoiceBean.getInvoiceno().equals(map.get("InvoiceNo").toString()) && 
						custInvoiceBean.getTaxservice().equals(map.get("TaxService").toString())) {
					setErrorInformation(data, "??????,?????????+????????????????????????");
					break;
				}
			}
		}
		return data;
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-12-18--??????5:00:41
	 * @param data
	 * @param information
	 * @return
	 */
	public Map<String,Object> setErrorInformation(final Map<String,Object> data, String information) {
		data.put("isinvalid", "1");
		data.put("isinvaliddescr", information);
		return data;
	}
	/**
	 * Excel???????????????map
	 * @author	created by zb
	 * @date	2019-12-18--??????4:55:28
	 * @param data
	 * @param custInvoiceBean
	 * @return
	 */
	public Map<String,Object> excelDataIntoMap(final Map<String,Object> data, CustInvoiceBean custInvoiceBean) {
		data.put("isinvaliddescr", "??????");
		data.put("custcode", custInvoiceBean.getCustcode());
		data.put("date", custInvoiceBean.getDate());
		data.put("invoiceno", custInvoiceBean.getInvoiceno());
		data.put("invoicecode", custInvoiceBean.getInvoicecode());
		data.put("taxservice", custInvoiceBean.getTaxservice());
		data.put("buyer", custInvoiceBean.getBuyer());
		data.put("amount", custInvoiceBean.getAmount()==null?0:custInvoiceBean.getAmount());
		data.put("taxper", custInvoiceBean.getTaxper()==null?0:custInvoiceBean.getTaxper());
		data.put("notaxamount", custInvoiceBean.getNotaxamount()==null?0:custInvoiceBean.getNotaxamount());
		data.put("taxamount", custInvoiceBean.getTaxamount()==null?0:custInvoiceBean.getTaxamount());
		data.put("remarks", custInvoiceBean.getRemarks());
		return data;
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2019-12-10--??????3:07:44
	 * @param request
	 * @param response
	 * @param custVisit
	 */
	@RequestMapping("/doCustInvoice")
	public void doCustInvoice(HttpServletRequest request,HttpServletResponse response,CustTax custTax){
		logger.debug("??????????????????");
		try {
			custTax.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "?????????Excel??????");
				return;
			}
			//??????????????????
			Result result = this.custTaxService.doCustInvoice(custTax);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goImportlaborInvoice")
	public ModelAndView goImportlaborInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/custTax/custTax_importlaborInvoice");
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/loadExcel_laborInvoice")
	@ResponseBody
	public Map<String, Object> loadExcel_laborInvoice(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		DiskFileItemFactory fac = new DiskFileItemFactory();// ??????FileItem ???????????????????????????
		ServletFileUpload upload = new ServletFileUpload(fac);// ???????????????
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);// ????????????????????????
		Iterator it = fileList.iterator();// ?????????
		InputStream in = getInputStream(it);
		List<String> titleList=getLaborInvoiceTitleList();
		return checkImportLaborInvoiceData(in, titleList);
	}
	
	/**
	 * ???????????????????????????????????????
	 * @return
	 */
	public List<String> getLaborInvoiceTitleList() {
		List<String> titleList = new ArrayList<String>();
		titleList.add("????????????");
		titleList.add("??????????????????");
		titleList.add("??????????????????");
		titleList.add("??????????????????");
		return titleList;
	}
	
	/**
	 * ???????????????????????????????????????
	 * @param inputStream 
	 * @param titleList
	 * @return
	 */
	public Map<String, Object> checkImportLaborInvoiceData(InputStream in, List<String> titleList) {
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<CustLaborInvoiceBean> eUtils=new ExcelImportUtils<CustLaborInvoiceBean>();// ??????EXCEL????????????, ????????????pojo ??????
		
		List<Map<String,Object>> laborCompanies = custTaxService.findLaborCompanyList();
		List<String> laborCompanyNames = new ArrayList<String>();
		for (Map<String,Object> company : laborCompanies) {
            laborCompanyNames.add((String) company.get("Descr"));
        }
		
		try {
			List<CustLaborInvoiceBean> result=eUtils.importExcel(in, CustLaborInvoiceBean.class, titleList);// ??????????????????????????????null????????????
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(CustLaborInvoiceBean custLaborInvoiceBean:result){
				if(StringUtils.isNotBlank(custLaborInvoiceBean.getError())){
					map.put("success", false);
					map.put("returnInfo", custLaborInvoiceBean.getError());
					map.put("hasInvalid", true);
					return map;
				}
				Map<String,Object> data=checkCustLaborInvoiceExcelColumnData(custLaborInvoiceBean, laborCompanyNames);
				if("1".equals(data.get("isinvalid"))){// ???hasInvalid???1???????????????????????????
					map.put("hasInvalid", true);
				}else{
					excelCustLaborInvoiceDataIntoMap(data, custLaborInvoiceBean);
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
			map.put("returnInfo", "???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	/**
	 * ??????????????????Excel???????????????map
	 * @param data
	 * @param custLaborInvoiceBean
	 * @return
	 */
	public Map<String,Object> excelCustLaborInvoiceDataIntoMap(final Map<String,Object> data, CustLaborInvoiceBean custLaborInvoiceBean) {
		data.put("isinvaliddescr", "??????");
		data.put("custcode", custLaborInvoiceBean.getCustcode());
		data.put("date", custLaborInvoiceBean.getDate());
		data.put("amount", custLaborInvoiceBean.getAmount()==null?0:custLaborInvoiceBean.getAmount());
		data.put("laborcompny", custLaborInvoiceBean.getLaborcompny());
		return data;
	}
	
	/**
	 * ????????????????????????Excel?????????????????????
	 * @param custLaborInvoiceBean
	 * @return
	 */
	public Map<String,Object> checkCustLaborInvoiceExcelColumnData(
	        CustLaborInvoiceBean custLaborInvoiceBean, List<String> laborCompanies) {
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("isinvalid", "0");// ??????
		if (StringUtils.isNotBlank(custLaborInvoiceBean.getCustcode())) {
		    
		    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
			CustTax custTax = custTaxService.get(CustTax.class, custLaborInvoiceBean.getCustcode().trim());
			if(null==custTax) {
				data = setErrorInformation(data, "??????,?????????????????????????????????");
			}else {
			    
			    // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
			    custLaborInvoiceBean.setCustcode(custLaborInvoiceBean.getCustcode().trim());
			    
				if (null == custLaborInvoiceBean.getDate()) {
					setErrorInformation(data, "??????,??????????????????????????????");
				} else if (0.0 == custLaborInvoiceBean.getAmount()) {
					setErrorInformation(data, "??????,??????????????????????????????");
				} else if (!laborCompanies.contains(custLaborInvoiceBean.getLaborcompny())) {
                    setErrorInformation(data, "??????,???????????????????????????");
                }
			}
		} else {
			setErrorInformation(data, "??????,????????????????????????");
		}
		return data;
	}
	
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param custTax
	 */
	@RequestMapping("/doCustLaborInvoice")
	public void doCustLaborInvoice(HttpServletRequest request,HttpServletResponse response,CustTax custTax){
		logger.debug("????????????????????????");
		try {
			custTax.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "?????????Excel??????");
				return;
			}
			Result result = this.custTaxService.doCustLaborInvoice(custTax);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	@RequestMapping("/goLaborCtrlList")
	public ModelAndView goLaborCtrlList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer customer=new Customer();
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		return new ModelAndView("admin/finance/custTax/custTax_laborCtrlList").addObject("customer", customer);
	}
	
	@RequestMapping("/goLaborCtrlListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLaborCtrlListJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTaxService.goLaborCtrlListJqGrid(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
}
