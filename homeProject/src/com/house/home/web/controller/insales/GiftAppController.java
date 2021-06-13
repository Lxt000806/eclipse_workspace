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
	 * giftApp列表
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
	 * 获取礼品领用编号
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
			return this.out("传入的id为空", false);
		}
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);
		if(giftApp == null){
			return this.out("系统中不存在no="+id+"的礼品领用信息", false);
		}
		return this.out(giftApp, true);
	}
	/*
	 *来就送 
	 * */
	@RequestMapping("/goComeGive")
	public ModelAndView goComeGive(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("来就送");						
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
	 *下定签单送 
	 *
	 * */
	@RequestMapping("/goSignGive")
	public ModelAndView goSignGive(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("来就送");						
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
	 *员工购买 
	 *
	 * */
	@RequestMapping("/goEmpBuy")
	public ModelAndView goEmpBuy(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("内部购买");						
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
	 *内部领用
	 *
	 * */
	@RequestMapping("/goInternal")
	public ModelAndView goInternal(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("内部领用");						
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
	 *退回
	 *
	 * */
	@RequestMapping("/goReturn")
	public ModelAndView goReturn(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("礼品领用——退回");						
		giftApp.setStatus("OPEN");
		giftApp.setOutType("2");
		giftApp.setM_umState("A");
		giftApp.setDate(DateUtil.getNow());
		giftApp.setAppCzy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftApp_return").addObject("giftApp", giftApp);

	}
	
	/*
	 *退回确认
	 *
	 * */
	@RequestMapping("/goReturnConfirm")
	public ModelAndView goReturnConfirm(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("礼品领用——退回确认");
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
	 *明细查询
	 *
	 * */
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("礼品领用——明细查询");						
		return new ModelAndView("admin/insales/giftApp/giftApp_detailView").addObject("giftApp", giftApp);

	}
	
	/*
	 *礼品领用明细新增
	 *
	 * */
	@RequestMapping("/goGiftAppDetailAdd")
	public ModelAndView goGiftAppDetailAdd(HttpServletRequest request, HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("礼品领用明细新增");	
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
	 *礼品领用干系人新增
	 *
	 * */
	@RequestMapping("/goGiftStakeholderAdd")
	public ModelAndView goGiftAppDetailAdd(HttpServletRequest request, HttpServletResponse response,GiftStakeholder giftStakeholder){
		logger.debug("礼品领用干系人新增");	
		giftStakeholder.setLastUpdate(new Date());
		giftStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftStakeholder_add")
			.addObject("giftStakeholder", giftStakeholder);
	}
	
	/*
	 *跳转到礼品领用明细查看界面
	 *
	 * */
	@RequestMapping("/goGiftAppDetailView")
	public ModelAndView goGiftAppDetailView(HttpServletRequest request, HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("礼品领用明细新增");
		giftAppDetail.setHasGiftUseDisc(
			giftAppService.getGiftUseDisc(giftAppDetail.getCustCode(), giftAppDetail.getNo()) 
			
		);
		return new ModelAndView("admin/insales/giftApp/giftAppDetail_view")
			.addObject("giftAppDetail", giftAppDetail);
	}
	/*
	 *跳转到礼品领用干系人查看界面
	 *
	 * */
	@RequestMapping("/goGiftStakeholderView")
	public ModelAndView goGiftAppDetailView(HttpServletRequest request, HttpServletResponse response,GiftStakeholder giftStakeholder){
		logger.debug("跳转到礼品领用干系人查看界面");	
		return new ModelAndView("admin/insales/giftApp/giftStakeholder_view")
			.addObject("giftStakeholder", giftStakeholder);
	}
	
	/*
	 *礼品领用编辑页面 
	 * */
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到礼品领用编辑页面");
		GiftApp giftApp = null;
		if (StringUtils.isNotBlank(id)){
			giftApp = giftAppService.get(GiftApp.class, id);
			giftApp.setM_umState("M");
			resetGiftApp(giftApp);
			if(!"OPEN".equals(giftApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "状态已变更,请刷新列表后重试");
				return null;
			}
		}else{
			giftApp = new GiftApp();
		}
		
		String updateUrl = null;
		
		if ("2".equalsIgnoreCase(giftApp.getOutType().trim())) { 
			updateUrl = "admin/insales/giftApp/giftApp_return";  //退回
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_save";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp);
	}
	
	/*
	 *礼品领用取消页面 
	 * */
	
	@RequestMapping("/goCancel")
	public ModelAndView goCancel(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到礼品领用取消页面");
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
			updateUrl = "admin/insales/giftApp/giftApp_return";  //退回
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_save";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp);
	}
	
	
	/*
	 *礼品领用查看页面 
	 * */
	
	@RequestMapping("/goview")
	public ModelAndView goview(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到礼品领用编辑页面");
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
			updateUrl = "admin/insales/giftApp/giftApp_return_view";  //退回
		} else {
			updateUrl = "admin/insales/giftApp/giftApp_view";
			
		}
		return new ModelAndView(updateUrl).addObject("giftApp", giftApp).addObject("viewStatus",viewStatus);
	}
	

	/**
	 * 跳转到退回新增页面
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
	 * 查询JqGrid表格数据
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
	
	//明细查询数据
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageByDetailSql(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}

	//礼品领用明细表格数据
	@RequestMapping("/goJqGridGiftAppDetail") 
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridGiftAppDetail(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftAppDetail(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	//礼品领用干系人表格数据
	@RequestMapping("/goJqGridGiftStakeholder")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridGiftStakeholder(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftStakeholder(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}
	//根据客户号获取礼品干系人
	@RequestMapping("/goJqGridStakeholderByCustCode")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridStakeholderByCustCode(HttpServletRequest request,
			HttpServletResponse response,GiftApp giftApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		giftAppService.findPageBySqlGiftStakeholderByCustCode(page, giftApp);
		return new WebPage<Map<String,Object>>(page);
	}	
		

	/**
	 * 退回明细新增jqgrid
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
	 *礼品领用退回保存
	 *
	 */
	@RequestMapping("/doReturnSave")
	public void doReturnSave(HttpServletRequest request,HttpServletResponse response,GiftApp giftApp){
		logger.debug("礼品领用管理退回申请开始");		
		try {	
			giftApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftApp.setAppCzy(this.getUserContext(request).getCzybh());
			giftApp.setLastUpdate(new Date());
			giftApp.setExpired("F"); 
			giftApp.setSendCzy(this.getUserContext(request).getCzybh());
			String giftAppDetail =request.getParameter("giftAppDetailJson");
		    JSONObject jsonObject = JSON.parseObject(giftAppDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  giftAppDetailJson=jsonArray.toString();
			giftApp.setGiftAppDetailJson(giftAppDetailJson);

			Result result = this.giftAppService.doGiftAppForProc(giftApp);
			
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 跳转到供应商直送页面
	 * @return
	 */            
	@RequestMapping("/goSendBySuppl")
	public ModelAndView goSendBySuppl(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到供应商直送页面");
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);
		giftApp.setM_umState("P");
		resetGiftApp(giftApp);
		giftApp.setSendDate(new Date());

		return new ModelAndView("admin/insales/giftApp/giftApp_sendBySupp").addObject("giftApp",giftApp);
	
	}
	
	/**
	 * 跳转到仓库发货页面
	 * @return
	 */            
	@RequestMapping("/goSend")
	public ModelAndView goSendBy(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到仓库直送页面");
		GiftApp giftApp = giftAppService.get(GiftApp.class, id);	
		resetGiftApp(giftApp);
		giftApp.setM_umState("S");
		giftApp.setSendDate(new Date());
		giftApp.setSendCzy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/giftApp/giftApp_send").addObject("giftApp",giftApp);
	
	}
	/**
	 * 跳转到批量打印页面
	 * @return
	 */            
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			GiftApp giftApp){
		logger.debug("跳转到供应商直送页面");
		
		return new ModelAndView("admin/insales/giftApp/giftApp_qPrint").addObject("giftApp",giftApp);
	
	}
	
	/**
	 *导出Excel
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
				"礼品领用_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
				"礼品领用明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doGiftAppDetailExcel")
	public void doGiftAppDetailExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("导出查询结果数据传入的参数为空!");
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
			exportTitle = StringUtils.isEmpty(exportTitle)?"导出数据":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/doGiftStakeholderExcel")
	public void doGiftStakeholderExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("导出查询结果数据传入的参数为空!");
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
			exportTitle = StringUtils.isEmpty(exportTitle)?"导出数据":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *礼品领用管理保存 
	 *
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,GiftApp giftApp){
		logger.debug("礼品领用管理新增开始");		
		try {	
			giftApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			giftApp.setAppCzy(this.getUserContext(request).getCzybh());
			giftApp.setLastUpdate(new Date());
			giftApp.setExpired("F"); 
			String giftAppDetail =request.getParameter("giftAppDetailJson");
			String giftStakeholder =request.getParameter("giftStakeholderJson");
			
			JSONObject jsonObject = JSON.parseObject(giftAppDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  giftAppDetailJson=jsonArray.toString();
			giftApp.setGiftAppDetailJson(giftAppDetailJson);
			
			JSONObject jsonObject2 = JSON.parseObject(giftStakeholder);
			JSONArray jsonArray2 = JSON.parseArray(jsonObject2.get("detailJson").toString());//先转化成json数组 
			String giftStakeholderJson=jsonArray2.toString();
			giftApp.setGiftStakeholderJson(giftStakeholderJson);
		
			Result result = this.giftAppService.doGiftAppForProc(giftApp);
			
				if (result.isSuccess()){
				/*	ServletUtils.outPrintSuccess(request, response,"单据添加成功");*/
					ServletUtils.outPrint(request, response, true, "单据添加成功", null, true);
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	
	/**r
	 * 仓库发货
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSend")
	public void doSend(HttpServletRequest request, HttpServletResponse response, GiftApp giftApp){
		logger.debug("GiftApp发货开始");
		try{
			GiftApp ia = this.giftAppService.get(GiftApp.class, giftApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到礼品单");
				return;
			}
			if (StringUtils.isNotBlank(giftApp.getWhCode())) {
				   ia.setWhCode(giftApp.getWhCode());	
			}
			WareHouse wareHouse = wareHouseService.get(WareHouse.class, ia.getWhCode());
			Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
			if (!wareHouseService.hasWhRight(czybm, wareHouse)) { 
				ServletUtils.outPrintFail(request, response, "您没有此仓库的权限,无法进行发货操作!");
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
			ServletUtils.outPrintFail(request, response, "发货失败");
		}
	}
	/**
	 * 供应商直送
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSendBySupp")
	public void doSendBySupp(HttpServletRequest request, HttpServletResponse response, GiftApp giftApp){
		logger.debug("GiftApp发货开始");
		try{
			GiftApp ia = this.giftAppService.get(GiftApp.class, giftApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到礼品单");
				return;
			}
			if (!"OPEN".equals(ia.getStatus().trim())) { // 2.
				ServletUtils.outPrintFail(request, response, "礼品单未处于【申请】状态,无法进行发货操作!");
				return;
			}
			if (!"1".equals(ia.getOutType().trim())) { // 2.
				ServletUtils.outPrintFail(request, response, "礼品单已退回,无法进行发货操作!");
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
			ServletUtils.outPrintFail(request, response, "发货失败");
		}
	}
	
	/**
	 *礼品领用管理保存 
	 *
	 */
	
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("添加礼品领用退回开始");
		try{
			GiftApp oldGiftApp = giftAppService.get(GiftApp.class, giftApp.getOldNo());
			if (!"SEND".equals(oldGiftApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response,"只能对已发货的礼品领用单进行退回操作！");
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
				ServletUtils.outPrintFail(request, response,"无礼品领用退回明细！请先添加明细！");
				return;
			}
			Result result = this.giftAppService.doGiftAppReturnForProc(giftApp);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加giftApp退回失败");
		}
	}
	/**
	 *ajax获取明细 
	 */
	@RequestMapping("/getAjaxDetail")
	@ResponseBody
	public GiftAppDetail getAjaxDetail(HttpServletRequest request,HttpServletResponse response,GiftAppDetail giftAppDetail){
		logger.debug("ajax获取数据");  
		//当前移动成本 
		Map<String , Object> map = giftAppService.getSendQty(giftAppDetail.getItemCode(),giftAppDetail.getCustCode());
		if(map!=null){
			giftAppDetail.setSendQty((Double)map.get("sendQty"));
		}else{
			giftAppDetail.setSendQty(0.0);
		
		}
		return giftAppDetail;
	}
	/**
	 * 跳转到仓库批量发货页面
	 * @return
	 */
	@RequestMapping("/goSendBatch")
	public ModelAndView goSendBatch(HttpServletRequest request, HttpServletResponse response,GiftApp giftApp){
		logger.debug("跳转到仓库批量发货页面");
		giftApp.setStatus("OPEN");
		giftApp.setOutType("1");
		return new ModelAndView("admin/insales/giftApp/giftApp_sendBatch")
			.addObject("giftApp", giftApp);
	}
	/**
	 * 批量发货GiftApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doSendBatch")
	public void doSendBatch(HttpServletRequest request, HttpServletResponse response, String ids){
		logger.debug("批量发货GiftApp开始");
		
		if(StringUtils.isBlank(ids)){
			ServletUtils.outPrintFail(request, response, "领用单号不能为空,发货失败!");
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
						noWhRightNo  = noWhRightNo+ "【" + str.trim() + "】";
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
					failReson=failReson+"【" + str.trim() + "】:"+ result.getInfo();
				}
			}
			String msg = "发货成功【"+isuccess+"】条，失败【"+ifail+"】条!"+failReson;
			if (iNoWhRigh>0) {
				msg = msg + "以下单据无仓库或无仓库权限，请单独发货：" + noWhRightNo;
			}
			ServletUtils.outPrintSuccess(request, response, msg, null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "发货失败!");
		}
	}

	
}
