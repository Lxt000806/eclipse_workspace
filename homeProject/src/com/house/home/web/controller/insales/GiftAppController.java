package com.house.home.web.controller.insales;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.excel.ExportExcel;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.GiftAppDetail;
import com.house.home.entity.insales.GiftStakeholder;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.cmpActivityService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.GiftAppService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.insales.WareHouseService;

@Controller
@RequestMapping("/admin/giftApp")
public class GiftAppController extends BaseController { 
		

	@Autowired
	private GiftAppService giftAppService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private cmpActivityService cmpActivityService;
	
	

	private void resetGiftApp(GiftApp giftApp){
		if (giftApp!=null){
			if (StringUtils.isNotBlank(giftApp.getAppCzy())){
				Czybm czybm = czybmService.get(Czybm.class, giftApp.getAppCzy());
				if (czybm!=null){
					giftApp.setAppCzyDescr(czybm.getZwxm());
				}
			}
			
			if (StringUtils.isNotBlank(giftApp.getWhCode())){
				WareHouse wareHouse = wareHouseService.get(WareHouse.class, giftApp.getWhCode());
				if (wareHouse!=null){
					giftApp.setWhDescr(wareHouse.getDesc1());
				}
			}
			if (StringUtils.isNotBlank(giftApp.getSupplCode())){
				Supplier supplier = supplierService.get(Supplier.class, giftApp.getSupplCode());
				if (supplier!=null){
					giftApp.setSupplDescr(supplier.getDescr());
				}
			}
			if (StringUtils.isNotBlank(giftApp.getCustCode())){
				Customer customer = customerService.get(Customer.class, giftApp.getCustCode());
				if (customer!=null){
					giftApp.setCustDescr(customer.getDescr());
					giftApp.setAddress(customer.getAddress());
					giftApp.setSignDate(customer.getSignDate());
					giftApp.setSetDate(customer.getSetDate());
				}
			}
			if (StringUtils.isNotBlank(giftApp.getUseMan())){
				Employee employee = employeeService.get(Employee.class, giftApp.getUseMan());
				if (employee!=null){
					giftApp.setUseManDescr(employee.getNameChi());
				}
			}
			if (StringUtils.isNotBlank(giftApp.getActNo())){
				cmpActivity cmpActivity = cmpActivityService.get(cmpActivity.class, giftApp.getActNo());
				if (cmpActivity!=null){
					giftApp.setActDescr(cmpActivity.getDescr());
				}
			}
			
		}
	}
	
	/**
	 * giftApp??????
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, 
			HttpServletResponse response, GiftApp giftApp) throws Exception {
		giftApp.setStatus("OPEN,SEND,RETURN");
		return new ModelAndView("admin/insales/giftApp/giftApp_list").addObject("giftApp", giftApp);
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, GiftApp giftApp) throws Exception {
		return new ModelAndView("admin/insales/giftApp/giftapp_code").addObject("giftApp", giftApp);
	}
	
	@RequestMapping("/getGiftApp")
	@ResponseBody
	public JSONObject getGiftApp(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);
		if(giftApp == null){
			return this.out("??????????????????no="+id+"?????????????????????", false);
		}
		return this.out(giftApp, true);
	}
	/*
	 *????????? 
	 * */
	@RequestMapping("/goComeGive")
	public ModelAndView goComeGive(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("?????????");						
		giftApp.setStatus("OPEN");
		giftApp.setType("1");
		giftApp.setM_umState("A");
		giftApp.setOutType("1");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		resetGiftApp(giftApp);
		return new ModelAndView("admin/insales/giftApp/giftApp_save").addObject("giftApp", giftApp);

	}
	
	/*
	 *??????????????? 
	 *
	 * */
	@RequestMapping("/goSignGive")
	public ModelAndView goSignGive(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("?????????");						
		giftApp.setStatus("OPEN");
		giftApp.setType("2");
		giftApp.setM_umState("A");
		giftApp.setOutType("1");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		resetGiftApp(giftApp);
		return new ModelAndView("admin/insales/giftApp/giftApp_save").addObject("giftApp", giftApp);

	}
	
	/*
	 *???????????? 
	 *
	 * */
	@RequestMapping("/goEmpBuy")
	public ModelAndView goEmpBuy(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("????????????");						
		giftApp.setStatus("OPEN");
		giftApp.setType("3");
		giftApp.setM_umState("A");
		giftApp.setOutType("1");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		resetGiftApp(giftApp);
		return new ModelAndView("admin/insales/giftApp/giftApp_save").addObject("giftApp", giftApp);

	}
	/*
	 *????????????
	 *
	 * */
	@RequestMapping("/goInternal")
	public ModelAndView goInternal(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("????????????");						
		giftApp.setStatus("OPEN");
		giftApp.setM_umState("A");
		giftApp.setType("4");
		giftApp.setOutType("1");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		resetGiftApp(giftApp);
		return new ModelAndView("admin/insales/giftApp/giftApp_save").addObject("giftApp", giftApp);

	}
	
	/*
	 *??????
	 *
	 * */
	@RequestMapping("/goReturn")
	public ModelAndView goReturn(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("????????????????????????");						
		giftApp.setStatus("OPEN");
		giftApp.setOutType("2");
		giftApp.setM_umState("A");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftApp_return").addObject("giftApp", giftApp);

	}
	
	/*
	 *????????????
	 *
	 * */
	@RequestMapping("/goReturnConfirm")
	public ModelAndView goReturnConfirm(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("??????????????????????????????");
		GiftApp giftApp = null;
		if (StringUtils.isNotBlank(id)){
			giftApp = giftAppService.get(GiftApp.class, id);
			resetGiftApp(giftApp);
			giftApp.setM_umState("C");
		}else{
			giftApp = new GiftApp();
		}
		return new ModelAndView("admin/insales/giftApp/giftApp_return").addObject("giftApp", giftApp);

	}
	/*
	 *????????????
	 *
	 * */
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("??????????????????????????????");						
		return new ModelAndView("admin/insales/giftApp/giftApp_detailView").addObject("giftApp", giftApp);

	}
	
	/*
	 *????????????????????????
	 *
	 * */
	@RequestMapping("/goGiftAppDetailAdd")
	public ModelAndView goGiftAppDetailAdd(HttpServletRequest request, HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("????????????????????????");	
		giftAppDetail.setLastUpdate(new Date());
		giftAppDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		giftAppDetail.setHasGiftUseDisc(
				giftAppService.getGiftUseDisc(giftAppDetail.getCustCode(), giftAppDetail.getNo()) 
				+ giftAppDetail.getThisGiftUseDisc()
		);
		return new ModelAndView("admin/insales/giftApp/giftAppDetail_add")
			.addObject("giftAppDetail", giftAppDetail);
	}
	/*
	 *???????????????????????????
	 *
	 * */
	@RequestMapping("/goGiftStakeholderAdd")
	public ModelAndView goGiftAppDetailAdd(HttpServletRequest request, HttpServletResponse response,GiftStakeholder giftStakeholder){
		logger.debug("???????????????????????????");	
		giftStakeholder.setLastUpdate(new Date());
		giftStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftStakeholder_add")
			.addObject("giftStakeholder", giftStakeholder);
	}
	
	/*
	 *???????????????????????????????????????
	 *
	 * */
	@RequestMapping("/goGiftAppDetailView")
	public ModelAndView goGiftAppDetailView(HttpServletRequest request, HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("????????????????????????");
		giftAppDetail.setHasGiftUseDisc(
			giftAppService.getGiftUseDisc(giftAppDetail.getCustCode(), giftAppDetail.getNo()) 
			
		);
		return new ModelAndView("admin/insales/giftApp/giftAppDetail_view")
			.addObject("giftAppDetail", giftAppDetail);
	}
	/*
	 *??????????????????????????????????????????
	 *
	 * */
	@RequestMapping("/goGiftStakeholderView")
	public ModelAndView goGiftAppDetailView(HttpServletRequest request, HttpServletResponse response,GiftStakeholder giftStakeholder){
		logger.debug("??????????????????????????????????????????");	
		return new ModelAndView("admin/insales/giftApp/giftStakeholder_view")
			.addObject("giftStakeholder", giftStakeholder);
	}
	
	/*
	 *???????????????????????? 
	 * */
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("?????????????????????????????????");
		GiftApp giftApp = null;
		if (StringUtils.isNotBlank(id)){
			giftApp = giftAppService.get(GiftApp.class, id);
			giftApp.setM_umState("M");
			resetGiftApp(giftApp);
			if(!"OPEN".equals(giftApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????,????????????????????????");
				return null;
			}
		}else{
			giftApp = new GiftApp();
		}
		
		String updateUrl = null;
		
		if ("2".equalsIgnoreCase(giftApp.getOutType().trim())) { 
			updateUrl = "admin/insales/giftApp/giftApp_return";  //??????
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_save";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp);
	}
	
	/*
	 *???????????????????????? 
	 * */
	
	@RequestMapping("/goCancel")
	public ModelAndView goCancel(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("?????????????????????????????????");
		GiftApp giftApp = null;
		if (StringUtils.isNotBlank(id)){
			giftApp = giftAppService.get(GiftApp.class, id);
			giftApp.setM_umState("D");
			resetGiftApp(giftApp);
		}else{
			giftApp = new GiftApp();
		}
		
		String updateUrl = null;
		if ("2".equalsIgnoreCase(giftApp.getOutType().trim())) { 
			updateUrl = "admin/insales/giftApp/giftApp_return";  //??????
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_save";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp);
	}
	
	
	/*
	 *???????????????????????? 
	 * */
	
	@RequestMapping("/goview")
	public ModelAndView goview(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("?????????????????????????????????");
		GiftApp giftApp = null;
		String viewStatus=request.getParameter("viewStatus");
		if (StringUtils.isNotBlank(id)){
			giftApp = giftAppService.get(GiftApp.class, id);
			giftApp.setM_umState("V");
			resetGiftApp(giftApp);
		}else{
			giftApp = new GiftApp();
		}
		
		String updateUrl = null;
		if ("2".equalsIgnoreCase(giftApp.getOutType().trim())) { 
			updateUrl = "admin/insales/giftApp/giftApp_return_view";  //??????
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_view";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp).addObject("viewStatus",viewStatus);
	}
	

	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goGiftAppDetailExistsReturn")
	public ModelAndView goGiftAppDetailExistsReturn(HttpServletRequest request,
			HttpServletResponse response, GiftAppDetail giftAppDetail) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSON.parseObject(postData);
		}
		return new ModelAndView("admin/insales/giftApp/giftAppDetail_exists_return").addObject("postParam", postParam);
	}
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		giftAppService.findPageBySql(page, giftApp,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//??????????????????
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageByDetailSql(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}

	//??????????????????????????????
	@RequestMapping("/goJqGridGiftAppDetail") 
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridGiftAppDetail(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftAppDetail(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	//?????????????????????????????????
	@RequestMapping("/goJqGridGiftStakeholder")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridGiftStakeholder(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftStakeholder(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}
	//????????????????????????????????????
	@RequestMapping("/goJqGridStakeholderByCustCode")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridStakeholderByCustCode(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftStakeholderByCustCode(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}	
		

	/**
	 * ??????????????????jqgrid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goGiftAppDetailExistsReturnJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGiftAppDetailExistsReturnJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String no = request.getParameter("no");
		String unSelected = request.getParameter("unSelected");
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("no", no);
		param.put("unSelected", unSelected);
		giftAppService.findGiftAppDetailExistsReturn(page, param);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goQPrintJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getQPrintJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageByQPrintSql(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 *????????????????????????
	 *
	 */
	@RequestMapping("/doReturnSave")
	public void doReturnSave(HttpServletRequest request,HttpServletResponse response,GiftApp giftApp){
		logger.debug("????????????????????????????????????");		
		try {	
			giftApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftApp.setAppCzy(this.getUserContext(request).getCzybh());
			giftApp.setLastUpdate(new Date());
			giftApp.setExpired("F"); 
			giftApp.setSendCzy(this.getUserContext(request).getCzybh());
			String giftAppDetail =request.getParameter("giftAppDetailJson");
		    JSONObject jsonObject = JSON.parseObject(giftAppDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  giftAppDetailJson=jsonArray.toString();
			giftApp.setGiftAppDetailJson(giftAppDetailJson);

			Result result = this.giftAppService.doGiftAppForProc(giftApp);
			
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"????????????");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */            
	@RequestMapping("/goSendBySuppl")
	public ModelAndView goSendBySuppl(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("??????????????????????????????");
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);
		giftApp.setM_umState("P");
		resetGiftApp(giftApp);
		giftApp.setSendDate(new Date());

		return new ModelAndView("admin/insales/giftApp/giftApp_sendBySupp").addObject("giftApp",giftApp);
	
	}
	
	/**
	 * ???????????????????????????
	 * @return
	 */            
	@RequestMapping("/goSend")
	public ModelAndView goSendBy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????????????????");
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);	
		resetGiftApp(giftApp);
		giftApp.setM_umState("S");
		giftApp.setSendDate(new Date());
		giftApp.setSendCzy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftApp_send").addObject("giftApp",giftApp);
	
	}
	/**
	 * ???????????????????????????
	 * @return
	 */            
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			GiftApp giftApp){
		logger.debug("??????????????????????????????");
		
		return new ModelAndView("admin/insales/giftApp/giftApp_qPrint").addObject("giftApp",giftApp);
	
	}
	
	/**
	 *??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, GiftApp giftApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		giftAppService.findPageBySql(page, giftApp,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, GiftApp giftApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		giftAppService.findPageByDetailSql(page, giftApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doGiftAppDetailExcel")
	public void doGiftAppDetailExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("?????????????????????????????????????????????!");
			return;
		}
		JSONObject dataObj = JSONObject.parseObject(exportData);
		JSONArray colHeader = dataObj.getJSONArray("colHeader");
		if(colHeader != null && !colHeader.isEmpty()){
			List<String> colNames = Lists.newArrayList();
			List<String> colLabels = Lists.newArrayList();
			for(int i = 0; i < colHeader.size(); i ++){
				JSONObject obj = colHeader.getJSONObject(i);
				colNames.add(obj.getString("colName"));
				colLabels.add(obj.getString("colLabel"));
			}
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(100000000);
			GiftApp giftApp = new GiftApp();
			giftApp.setNo(dataObj.getString("no"));;
			giftAppService.findPageBySqlGiftAppDetail(page,giftApp);
			String exportTitle = dataObj.getString("exportTitle");
			exportTitle = StringUtils.isEmpty(exportTitle)?"????????????":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("???????????????????????????excel???????????????"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/doGiftStakeholderExcel")
	public void doGiftStakeholderExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("?????????????????????????????????????????????!");
			return;
		}
		JSONObject dataObj = JSONObject.parseObject(exportData);
		JSONArray colHeader = dataObj.getJSONArray("colHeader");
		if(colHeader != null && !colHeader.isEmpty()){
			List<String> colNames = Lists.newArrayList();
			List<String> colLabels = Lists.newArrayList();
			for(int i = 0; i < colHeader.size(); i ++){
				JSONObject obj = colHeader.getJSONObject(i);
				colNames.add(obj.getString("colName"));
				colLabels.add(obj.getString("colLabel"));
			}
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(100000000);
			GiftApp giftApp = new GiftApp();
			giftApp.setNo(dataObj.getString("no"));
			giftAppService.findPageBySqlGiftStakeholder(page, giftApp);
			String exportTitle = dataObj.getString("exportTitle");
			exportTitle = StringUtils.isEmpty(exportTitle)?"????????????":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("???????????????????????????excel???????????????"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *???????????????????????? 
	 *
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,GiftApp giftApp){
		logger.debug("??????????????????????????????");		
		try {	
			giftApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftApp.setAppCzy(this.getUserContext(request).getCzybh());
			giftApp.setLastUpdate(new Date());
			giftApp.setExpired("F"); 
			String giftAppDetail =request.getParameter("giftAppDetailJson");
			String giftStakeholder =request.getParameter("giftStakeholderJson");
			
			JSONObject jsonObject = JSON.parseObject(giftAppDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  giftAppDetailJson=jsonArray.toString();
			giftApp.setGiftAppDetailJson(giftAppDetailJson);
			
			JSONObject jsonObject2 = JSON.parseObject(giftStakeholder);
			JSONArray jsonArray2 = JSON.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String giftStakeholderJson=jsonArray2.toString();
			giftApp.setGiftStakeholderJson(giftStakeholderJson);
		
			Result result = this.giftAppService.doGiftAppForProc(giftApp);
			
				if (result.isSuccess()){
				/*	ServletUtils.outPrintSuccess(request, response,"??????????????????");*/
					ServletUtils.outPrint(request, response, true, "??????????????????", null, true);
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	
	/**r
	 * ????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSend")
	public void doSend(HttpServletRequest request, HttpServletResponse response, GiftApp giftApp){
		logger.debug("GiftApp????????????");
		try{
			GiftApp ia = this.giftAppService.get(GiftApp.class, giftApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "?????????????????????");
				return;
			}
			if (StringUtils.isNotBlank(giftApp.getWhCode())) {
				   ia.setWhCode(giftApp.getWhCode());	
			}
			WareHouse wareHouse = wareHouseService.get(WareHouse.class, ia.getWhCode());
			Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
			if (!wareHouseService.hasWhRight(czybm, wareHouse)) { 
				ServletUtils.outPrintFail(request, response, "???????????????????????????,????????????????????????!");
				return;
			}
			ia.setM_umState("S");
			ia.setSendCzy(this.getUserContext(request).getCzybh());
			ia.setSendDate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());		
			Result result = this.giftAppService.doGiftAppSendForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	/**
	 * ???????????????
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSendBySupp")
	public void doSendBySupp(HttpServletRequest request, HttpServletResponse response, GiftApp giftApp){
		logger.debug("GiftApp????????????");
		try{
			GiftApp ia = this.giftAppService.get(GiftApp.class, giftApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "?????????????????????");
				return;
			}
			if (!"OPEN".equals(ia.getStatus().trim())) { // 2.
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????,????????????????????????!");
				return;
			}
			if (!"1".equals(ia.getOutType().trim())) { // 2.
				ServletUtils.outPrintFail(request, response, "??????????????????,????????????????????????!");
				return;
			}
			ia.setM_umState("p");
			ia.setSendCzy(this.getUserContext(request).getCzybh());
			ia.setSendDate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());	
			Result result = this.giftAppService.doGiftAppSendBySuppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 *???????????????????????? 
	 *
	 */
	
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("??????????????????????????????");
		try{
			GiftApp oldGiftApp = giftAppService.get(GiftApp.class, giftApp.getOldNo());
			if (!"SEND".equals(oldGiftApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response,"?????????????????????????????????????????????????????????");
				return;
			}	
			giftApp.setDate(new Date());
			giftApp.setAppCzy(this.getUserContext(request).getCzybh());
			giftApp.setSendCzy(this.getUserContext(request).getCzybh());
			giftApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftApp.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			giftApp.setGiftAppDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response,"???????????????????????????????????????????????????");
				return;
			}
			Result result = this.giftAppService.doGiftAppReturnForProc(giftApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????giftApp????????????");
		}
	}
	/**
	 *ajax???????????? 
	 */
	@RequestMapping("/getAjaxDetail")
	@ResponseBody
	public GiftAppDetail getAjaxDetail(HttpServletRequest request,HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("ajax????????????");  
		//?????????????????? 
		Map<String , Object> map = giftAppService.getSendQty(giftAppDetail.getItemCode(),giftAppDetail.getCustCode());
		if(map!=null){
			giftAppDetail.setSendQty((Double)map.get("sendQty"));
		}else{
			giftAppDetail.setSendQty(0.0);
		
		}
		return giftAppDetail;
	}
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping("/goSendBatch")
	public ModelAndView goSendBatch(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("?????????????????????????????????");
		giftApp.setStatus("OPEN");
		giftApp.setOutType("1");
		return new ModelAndView("admin/insales/giftApp/giftApp_sendBatch")
			.addObject("giftApp", giftApp);
	}
	/**
	 * ????????????GiftApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doSendBatch")
	public void doSendBatch(HttpServletRequest request, HttpServletResponse response, String ids){
		logger.debug("????????????GiftApp??????");
		
		if(StringUtils.isBlank(ids)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????!");
			return;
		};
		
		try {
			String arr[] = ids.split(",");
			int ifail=0,isuccess=0,iNoWhRigh=0;
			String noWhRightNo = "",failReson="";
			for (String str : arr){
				GiftApp ia = this.giftAppService.get(GiftApp.class, str);
				if ("1".equalsIgnoreCase(ia.getSendType().trim())) {
					ia.setM_umState("P");
					ia.setWhCode("1000");
				} else {
					ia.setM_umState("S");
					WareHouse wareHouse = wareHouseService.get(WareHouse.class, ia.getWhCode());
					Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
					if ((StringUtils.isBlank(ia.getWhCode()))||(!wareHouseService.hasWhRight(czybm, wareHouse))) { 
						ifail++;
						iNoWhRigh++;
						noWhRightNo  = noWhRightNo+ "???" + str.trim() + "???";
						continue;
					}
				}
				ia.setSendDate(new Date());
				ia.setSendCzy(this.getUserContext(request).getCzybh());
				ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				ia.setExpired("F");
				
				Result result = this.giftAppService.doSendBatchForProc(ia);
				if (result.isSuccess()){
					isuccess++;
				} else {
					ifail++;
					failReson=failReson+"???" + str.trim() + "???:"+ result.getInfo();
				}
			}
			String msg = "???????????????"+isuccess+"??????????????????"+ifail+"??????!"+failReson;
			if (iNoWhRigh>0) {
				msg = msg + "????????????????????????????????????????????????????????????" + noWhRightNo;
			}
			ServletUtils.outPrintSuccess(request, response, msg, null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????!");
		}
	}

	
}
