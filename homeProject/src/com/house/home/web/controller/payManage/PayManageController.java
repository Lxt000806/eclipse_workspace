package com.house.home.web.controller.payManage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.workflow.Department;
import com.house.home.entity.workflow.WfProcess;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.finance.PayManageService;
import com.house.home.service.workflow.WfProcInstService;


@Controller
@RequestMapping("/admin/payManage")
public class PayManageController extends BaseController { 	
	@Autowired
	private PayManageService payManageService;

	@Autowired
	private CustomerService employeeService;
	@Autowired
	private WfProcInstService wfProcInstService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private XtdmService xtdmService;
	
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, SupplierPrepay supplierPrepay) throws Exception {
		return new ModelAndView("admin/finance/payManage/payManage_list").addObject("supplierPrepay", supplierPrepay).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (supplierPrepay.getAppDate()!=null) {
			if(supplierPrepay.getAppDate().equals(supplierPrepay.getAppdate1())){
				 Calendar c = Calendar.getInstance();  
			     c.setTime(supplierPrepay.getAppdate1());  
			     c.add(Calendar.DAY_OF_MONTH, 1);
			     supplierPrepay.setAppdate1(c.getTime());					
			}
		}
		String orderBy="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if ("bitemtype1".equals(orderBy)){
			orderBy="itemtype1";
		}else if ("btype".equals(orderBy)){
			orderBy="type";
		}else if ("bstatus".equals(orderBy)){
			orderBy="status";
		}else if ("bappemp".equals(orderBy)){
			orderBy="appemp";
		}else if ("bconfirmemp".equals(orderBy)){
			orderBy="confirmemp";
		}								
		page.setPageOrderBy(orderBy);
		payManageService.findPageBySql(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridMxSelect")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridMxSelect(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
	/*	String orderBy="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if ("typedescr".equals(orderBy)){
			orderBy="a.type";
		}else if ("spldescr".equals(orderBy)){
			if("1".equals(supplierPrepay.getType())){
				orderBy="d.code";
			}else {
				orderBy="f.code";
			}
			
		}else if ("puno".equals(orderBy)){
			orderBy="b.puno";
		}else if ("statusdescr".equals(orderBy)){
			orderBy="b.Status";
		}else if ("amount".equals(orderBy)){
			orderBy="b.amount";
		}else if ("itemtype1descr".equals(orderBy)){											
			orderBy="a.ItemType1";
		}else if ("remarks".equals(orderBy) || ("lastupdate".equals(orderBy)) || ("lastupdatedby".equals(orderBy))|| ("actionlog".equals(orderBy)) ){
			orderBy="a."+page.getPageOrderBy();
		}
		page.setPageOrderBy(orderBy);*/
		payManageService.findPageBySqlMxSelect(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	@RequestMapping("/goJqGridYeSelect")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridYeSelect(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		payManageService.findPageBySqlYeSelect(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/*
	 *payManage?????????????????????????????? 
	 * */
	@RequestMapping("/goJqGridYeChangeSelect")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridYeChangeSelect(HttpServletRequest request,
			HttpServletResponse response,Supplier supplier) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		payManageService.goJqGridYeChangeSelect(page, supplier);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goProcListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goProcListJqGrid(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		payManageService.findProcListJqGrid(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getSupplAccount")
	@ResponseBody
	public WebPage<Map<String, Object>> getSupplAccount(HttpServletRequest request ,
			HttpServletResponse response, SupplierPrepay supplierPrepay) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		payManageService.getSupplAccountJqGrid(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/*
	 *payManage????????????????????? 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????????????????????????????");	
		SupplierPrepay supplierPrepay = new SupplierPrepay();	
		supplierPrepay.setStatus("1");
		supplierPrepay.setAppDate(new Date());
		supplierPrepay.setAppDate(new java.sql.Timestamp(supplierPrepay.getAppDate().getTime()));
		supplierPrepay.setAppEmp(this.getUserContext(request).getCzybh());
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
        return new ModelAndView("admin/finance/payManage/payManage_save")
			.addObject("supplierPrepay", supplierPrepay).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/* 
	 *payManage???????????? 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		return new ModelAndView("admin/finance/payManage/payManage_update")
			.addObject("supplierPrepay", supplierPrepay);
	}
	
	/* 
	 *payManage???????????? 
	 * */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		
		supplierPrepay.setConfirmDate(new Date());
		supplierPrepay.setConfirmDate(new java.sql.Timestamp(supplierPrepay.getConfirmDate().getTime()));
		supplierPrepay.setConfirmEmp(this.getUserContext(request).getCzybh());
		if (supplierPrepay.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getConfirmEmp());
			supplierPrepay.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
		
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		
		return new ModelAndView("admin/finance/payManage/payManage_Check")
			.addObject("supplierPrepay", supplierPrepay);
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param supplierPrepay
	 * @return
	 */
	@RequestMapping("/goViewPu")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response,SupplierPrepay supplierPrepay){
		logger.debug("??????????????????????????????");
		return new ModelAndView("admin/finance/payManage/payManage_viewPu")
			.addObject("supplierPrepay", supplierPrepay);
	}
	
	/* *
	 *payManage????????????????????????
	 * */
	@RequestMapping("/CheckCancel")
	public void CheckCancel(HttpServletRequest request,
			HttpServletResponse response,Integer pk) {
		logger.debug("????????????");
		SupplierPrepayDetail  supplierPrepayDetail = payManageService.get(SupplierPrepayDetail.class, pk);
		try{
			supplierPrepayDetail.setStatus("3");
			supplierPrepayDetail.setActionLog("EDIT");
			supplierPrepayDetail.setLastUpdate(new Date());
			supplierPrepayDetail.setLastUpdatedBy(getUserContext(request).getCzybh());	
			this.payManageService.update(supplierPrepayDetail);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????PreMeasure??????");
		}
	}
	/* *
	 *payManage????????????????????????
	 * */
	@RequestMapping("/recovery")
	public void recovery(HttpServletRequest request,
			HttpServletResponse response,Integer pk) {
		logger.debug("????????????");
		SupplierPrepayDetail  supplierPrepayDetail = payManageService.get(SupplierPrepayDetail.class, pk);
		try{
			supplierPrepayDetail.setStatus("1");
			supplierPrepayDetail.setActionLog("EDIT");
			supplierPrepayDetail.setLastUpdate(new Date());
			supplierPrepayDetail.setLastUpdatedBy(getUserContext(request).getCzybh());	
			this.payManageService.update(supplierPrepayDetail);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????PreMeasure??????");
		}
	}
	/**
	 * ??????goJqGridDetail????????????   ??????deteil???
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridDetail(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		payManageService.findPageBySqlDetail(page, supplierPrepayDetail);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/**
	 * ??????goJqGridDetail????????????   ??????deteil???
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPuJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPuJqGrid(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		payManageService.getPuJqGrid(page, supplierPrepay);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/* 
	 *payManage??????????????? 
	 * */
	@RequestMapping("/goback")
	public ModelAndView goback(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("???????????????????????????????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay= new SupplierPrepay();
		}
		return new ModelAndView("admin/finance/payManage/payManage_back")
			.addObject("supplierPrepay", supplierPrepay);
	}
	
	/* 
	 *payManage???????????? 
	 * */
	@RequestMapping("/goMark")
	public ModelAndView goMark(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????????????????????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		
		supplierPrepay.setPayDate(new Date());
		supplierPrepay.setPayDate(new java.sql.Timestamp(supplierPrepay.getPayDate().getTime()));
		if (supplierPrepay.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getConfirmEmp());
			supplierPrepay.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		supplierPrepay.setPayEmp(this.getUserContext(request).getCzybh());
		if (supplierPrepay.getPayEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getPayEmp());
			supplierPrepay.setPayEmpDescr(employee==null?"":employee.getNameChi());
		}
		
		return new ModelAndView("admin/finance/payManage/payManage_Mark")
			.addObject("supplierPrepay", supplierPrepay);
	}
	
	
	/* 
	 *payManage???????????? 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (supplierPrepay.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getConfirmEmp());
			supplierPrepay.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (supplierPrepay.getPayEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getPayEmp());
			supplierPrepay.setPayEmpDescr(employee==null?"":employee.getNameChi());
		}
		return new ModelAndView("admin/finance/payManage/payManage_view")
			.addObject("supplierPrepay", supplierPrepay);
	}
	/* 
	 *payManage?????????????????? 
	 * */
	@RequestMapping("/goMxSelect")
	public ModelAndView goMxSelect(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????_????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		return new ModelAndView("admin/finance/payManage/payManage_MxView")
			.addObject("supplierPrepay", supplierPrepay);
	}
	/* 
	 *payManage?????????????????? 
	 * */
	@RequestMapping("/goYeSelect")
	public ModelAndView goYeSelect(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("????????????????????????_????????????");
		SupplierPrepay supplierPrepay = null;
		if (StringUtils.isNotBlank(id)){
			supplierPrepay = payManageService.get(SupplierPrepay.class, id);
		}else{
			supplierPrepay = new SupplierPrepay();
		}
		if (supplierPrepay.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, supplierPrepay.getAppEmp());
			supplierPrepay.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		return new ModelAndView("admin/finance/payManage/payManage_YeView")
			.addObject("supplierPrepay", supplierPrepay);
	}
	/* 
	 *payManage???????????????????????????????????????
	 * */
	@RequestMapping("/gochangeView")
	public ModelAndView gochangeView(HttpServletRequest request, HttpServletResponse response,Supplier supplier){
		logger.debug("????????????????????????????????????_????????????");		
		supplier.setDateF(DateUtil.addDate(new Date(),-7));
		supplier.setDateT(new Date());
		return new ModelAndView("admin/finance/payManage/payManage_YeChangeView")
			.addObject("supplier", supplier);
	}
	
	/*
	 * goadd ?????????????????????????????????
	 * */
	@RequestMapping("/goadd")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail){
		logger.debug("??????????????????????????????????????????");	
		
		if ("1".equals(supplierPrepayDetail.getType())){
		return new ModelAndView("admin/finance/payManage/payManageDetail_addyf")
			.addObject("supplierPrepayDetail", supplierPrepayDetail);
		}else if ("2".equals(supplierPrepayDetail.getType())){
		return new ModelAndView("admin/finance/payManage/payManageDetail_adddj")
		.addObject("supplierPrepayDetail", supplierPrepayDetail);
		}
		else {
			return null;
		}
	}

	/*
	 * ??????????????? ??????
	 * */
	@RequestMapping("/goaddUpdate")
	public ModelAndView goaddUpdate(HttpServletRequest request, HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail){
		logger.debug("?????????????????????");		
		if ("1".equals(supplierPrepayDetail.getType())){
			return new ModelAndView("admin/finance/payManage/payManageDetail_updateyf")
				.addObject("paymanageDetail", supplierPrepayDetail);
			}else if ("2".equals(supplierPrepayDetail.getType())){
			return new ModelAndView("admin/finance/payManage/payManageDetail_updatedj")
			.addObject("supplierPrepayDetail", supplierPrepayDetail);
			}
			else {
				return null;
			}
	}
	/*
	 * ??????????????? ??????
	 * */
	@RequestMapping("/goDetailview")
	public ModelAndView goDetailview(HttpServletRequest request, HttpServletResponse response,SupplierPrepayDetail supplierPrepayDetail){
		logger.debug("?????????????????????");		
			if ("1".equals(supplierPrepayDetail.getType())){
			return new ModelAndView("admin/finance/payManage/payManageDetail_viewyf")
				.addObject("supplierPrepayDetail", supplierPrepayDetail);
			}else if ("2".equals(supplierPrepayDetail.getType())){
			return new ModelAndView("admin/finance/payManage/payManageDetail_viewdj")
			.addObject("supplierPrepayDetail", supplierPrepayDetail);
			}
			else {
				return null;
			}
	}
	
	@RequestMapping("/goWfProcApply")
	public ModelAndView goWfProcApply(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("no") String no) {
		// ?????????????????????????????????????????????????????????
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey("purchaseAdvance")
			.latestVersion()
			.singleResult();
		
		String url = FileUploadUtils.DOWNLOAD_URL;
		
		// ?????????????????????
        WfProcess wfProcess = this.wfProcInstService.getWfProcessByProcKey(processDefinition.getKey());
		String wfProcNo = "";
		if(wfProcess != null){
			wfProcNo = wfProcess.getNo();
			wfProcess.setRemarks((wfProcess.getRemarks()==null?"":wfProcess.getRemarks()).replace("\r\n", "<br/>"));
		}
		
		SupplierPrepay supplierPrepay = payManageService.get(SupplierPrepay.class, no);
		
		Employee employee = wfProcInstService.get(Employee.class, this.getUserContext(request).getEmnum());
		// ???????????? ??????????????????
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> detailJson = new HashMap<String, Object>();
		Map<String , Object> detailMap = new HashMap<String, Object>();
		
		dataList = payManageService.getDetailOrderBySupplCode(supplierPrepay);
		
		detailMap.put("tWfCust_PurchaseAdvance", 1);
		double payAmount = 0.0;
		
		// ???????????????
		if(employee != null){
			Department department = new Department();
			Department1 department1 = new Department1();
			department = employeeService.get(Department.class, employee.getDepartment());
			detailJson.put("fp__tWfCust_PurchaseAdvance__0__EmpCode", this.getUserContext(request).getCzybh());
			detailJson.put("fp__tWfCust_PurchaseAdvance__0__EmpName", employee.getNameChi());
			department1 = employeeService.get(Department1.class,employee.getDepartment1());
			if(department != null){
				Map<String, Object> cmpData = wfProcInstService.getEmpCompany(employee.getDepartment());
    			if(cmpData != null){
    				employee.setCmpCode(cmpData.get("Code").toString());
    				employee.setCmpDescr(cmpData.get("CmpDescr").toString());
    			}
			}
			if(department1 != null){
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__DeptCode", department1.getCode());
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__DeptDescr", department1.getDesc1());
			}
		}
		
		if(supplierPrepay != null){
			detailJson.put("fp__tWfCust_PurchaseAdvance__0__RefNo", supplierPrepay.getNo());
			detailJson.put("fp__tWfCust_PurchaseAdvance__0__Remarks", supplierPrepay.getRemarks());
			detailJson.put("fp__tWfCust_PurchaseAdvance__0__ApplyDate", new Date());
			
			Xtdm splPrepayType = xtdmService.getByIdAndCbm("SPLPREPAYTYPE", supplierPrepay.getType());
			if(splPrepayType != null){
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__Type", splPrepayType.getNote());
				if("??????".equals(splPrepayType.getNote())){
					detailJson.put("fp__tWfCust_PurchaseAdvance__0__AdvanceRemarks", "???????????????");
				} else {
					detailJson.put("fp__tWfCust_PurchaseAdvance__0__AdvanceRemarks", "???????????????");
				}
			} else {
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__Type", "??????");
			}
			if(StringUtils.isNotBlank(supplierPrepay.getItemType1())){
				ItemType1 itemType1 = payManageService.get(ItemType1.class, supplierPrepay.getItemType1());
				if(itemType1 != null){
					detailJson.put("fp__tWfCust_PurchaseAdvance__0__ItemType1", itemType1.getDescr());
				} else {
					detailJson.put("fp__tWfCust_PurchaseAdvance__0__ItemType1", "??????");
				}
			} else {
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__ItemType1", "??????");
			}
		}
		
		if(dataList != null && dataList.size()>0){
			for(int i = 0; i < dataList.size(); i++){
				Map<String, Object> dtlMap = dataList.get(i);
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__PayAmount", dtlMap.get("PayAmount"));
				payAmount += Double.parseDouble(dtlMap.get("PayAmount").toString());
				
				detailJson.put("fp__tWfCust_PurchaseAdvance__0__AdvanceAmount", payAmount);
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplCode", dtlMap.get("Supplier"));
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__SupplDescr", dtlMap.get("SupplDescr"));
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvBank", dtlMap.get("Bank"));
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvCardId", dtlMap.get("CardId"));
				detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__RcvActName", dtlMap.get("ActName"));
				
				if("??????".equals(detailJson.get("fp__tWfCust_PurchaseAdvance__0__Type")) 
						&& ("??????".equals(detailJson.get("fp__tWfCust_PurchaseAdvance__0__ItemType1")) || "??????".equals(detailJson.get("fp__tWfCust_PurchaseAdvance__0__ItemType1"))||
								"??????".equals(detailJson.get("fp__tWfCust_PurchaseAdvance__0__ItemType1")))
						&& dtlMap.get("Supplier") != null){
					String puno = "";
					puno = payManageService.getPunos(supplierPrepay.getNo(), dtlMap.get("Supplier").toString());
					detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__Reason", puno);
				} else {
					detailJson.put("fp__tWfCust_PurchaseAdvanceDtl__"+i+"__Reason", "");
				}
				
			}

			detailMap.put("tWfCust_PurchaseAdvanceDtl", dataList.size());
		}
		
		String startMan = this.getUserContext(request).getCzybh();
		
		Xtcs xtcs = wfProcInstService.get(Xtcs.class, "CMPNYCODE");
		
		return new ModelAndView("admin/workflow/wfProcInst/wfProcInst_apply")
			.addObject("processDefinition", processDefinition)
			.addObject("processDefinitionKey", processDefinition.getId())
			.addObject("wfProcNo",wfProcNo)
			.addObject("applyPage", processDefinition.getKey()+".jsp")
			.addObject("m_umState", "A")
			.addObject("datas", detailJson)
			.addObject("detailJson", JSONObject.toJSONString(detailJson))
			.addObject("processInstanceId", "processInstanceId")
			.addObject("detailList",JSONObject.toJSONString(detailMap))
			.addObject("startMan", startMan)
			.addObject("wfProcess", wfProcess)
			.addObject("url", url)
			.addObject("cmpcode", xtcs.getQz())
			.addObject("employee", employee).addObject("activityId", "startevent");
	}
	
	@RequestMapping("/goViewProcTrack")
	public ModelAndView goViewProcTrack(HttpServletRequest request,
			HttpServletResponse response, SupplierPrepay supplierPrepay) throws Exception {
		
		return new ModelAndView("admin/finance/payManage/payManage_viewProcTrack").addObject("supplierPrepay", supplierPrepay);
	}
	
	@RequestMapping("/goSupplAccount")
	public ModelAndView goSupplAccount(HttpServletRequest request,
			HttpServletResponse response, SupplierPrepay supplierPrepay) throws Exception {
		
		return new ModelAndView("admin/finance/payManage/payManage_supplAccount").addObject("supplierPrepay", supplierPrepay);
	}
	
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,
			HttpServletResponse response,SupplierPrepay supplierPrepay) throws Exception {
		String wfProcInstNo = supplierPrepay.getWfProcInstNo();
		
		if(StringUtils.isNotBlank(supplierPrepay.getNo())){
			supplierPrepay = payManageService.get(SupplierPrepay.class, supplierPrepay.getNo());
			if(supplierPrepay != null){
				supplierPrepay.setWfProcInstNo(wfProcInstNo);
			}
		}
		
		return new ModelAndView("admin/finance/payManage/payManage_print").addObject("supplierPrepay", supplierPrepay);
	}
	
	//????????????dopayManageSave
	@RequestMapping("/dopayManageSave")
	public void dopayManageSave(HttpServletRequest request,HttpServletResponse response,SupplierPrepay supplierPrepay){
		logger.debug("???????????????????????????");		
		try {
			supplierPrepay.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			supplierPrepay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			supplierPrepay.setM_umState("A");
			supplierPrepay.setPayDate(new Date());
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
				return;
			}
		
			Result result = this.payManageService.dopayManageSave(supplierPrepay);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"????????????");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	//????????????dopayManageSave      M????????????C????????????B???????????????W???????????????
		@RequestMapping("/dopayManageUpdateSave")
		public void dopayManageUpdateSave(HttpServletRequest request,HttpServletResponse response,SupplierPrepay supplierPrepay){
			logger.debug("?????????????????????????????????");		
			try {
				supplierPrepay.setLastUpdate(new Date());
				String detailJson = request.getParameter("detailJson");
				supplierPrepay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				supplierPrepay.setPayEmp(this.getUserContext(request).getCzybh());
				supplierPrepay.setConfirmEmp(this.getUserContext(request).getCzybh());
				supplierPrepay.setConfirmDate(new Date());
				
				if ("M".equals(supplierPrepay.getM_umState())){    //????????????
					supplierPrepay.setPayDate(new Date());
				} 
				
				if ("C".equals(supplierPrepay.getM_umState())){    //??????????????????
					supplierPrepay.setPayDate(new Date());
					supplierPrepay.setStatus("2");						
				}
				if ("Q".equals(supplierPrepay.getM_umState())){   // ????????????????????????
					supplierPrepay.setM_umState("C");
					supplierPrepay.setPayDate(new Date());
					supplierPrepay.setStatus("3");						
				}
				if ("B".equals(supplierPrepay.getM_umState())){   //???????????????
					supplierPrepay.setPayDate(new Date());
					supplierPrepay.setStatus("1");		
				}
				if ("W".equals(supplierPrepay.getM_umState())){   //??????????????????
					supplierPrepay.setStatus("2");								
				}

				if(detailJson.equals("[]")){
					ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
					return;
				}
			
				Result result = this.payManageService.dopayManageSave(supplierPrepay);
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
		 *????????????Excel
		 * @param request
		 * @param response
		 */
		@RequestMapping("/doExcel")
		public void doExcel(HttpServletRequest request, 
				HttpServletResponse response, SupplierPrepay supplierPrepay){
			Page<Map<String,Object>> page = this.newPage(request);
			page.setPageSize(50000);
			payManageService.findPageBySql(page, supplierPrepay);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
					"???????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}
		
		@RequestMapping("/doExcelView")
		public void doExcelView(HttpServletRequest request, 
				HttpServletResponse response, SupplierPrepayDetail supplierPrepayDetail){
			Page<Map<String,Object>> page = this.newPage(request);
			page.setPageSize(50000);
			payManageService.findPageBySqlDetail(page, supplierPrepayDetail);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
					"???????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}
		/**
		 *??????Excel
		 * @param request
		 * @param response
		 */
		@RequestMapping("/doExcelDetail")
		public void doExcelDetail(HttpServletRequest request, 
				HttpServletResponse response, SupplierPrepay supplierPrepay){
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(50000);
			payManageService.findPageBySqlMxSelect(page, supplierPrepay);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
					"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}
		
		@RequestMapping("/doExcelForYe")
		public void doExcelForYe(HttpServletRequest request ,HttpServletResponse response,
				SupplierPrepay supplierPrepay){		
			Page<Map<String, Object>>page= this.newPage(request);
			page.setPageSize(-1);
			payManageService.findPageBySqlYeSelect(page, supplierPrepay);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
					"???????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}
}
