package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.ItemCheck;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.PrjJob;
import com.house.home.service.basic.CmpCustTypeService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CljsglService;
import com.house.home.service.project.ItemCheckService;
import com.house.home.service.project.PreWorkCostDetailService;

@Controller
@RequestMapping("/admin/cljsgl")
public class CljsglController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CljsglController.class);
	@Autowired
	private CljsglService cljsglService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CmpCustTypeService cmpCustTypeService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemCheck itemCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String itemRight=this.getUserContext(request).getItemRight();
		cljsglService.findPageBySql(page, itemCheck,itemRight);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PreWorkCostDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemCheck itemCheck) throws Exception {
		if(StringUtils.isNotBlank(this.getUserContext(request).getItemRight())){
			itemCheck.setItemType1(this.getUserContext(request).getItemRight().split(",").length==1?this.getUserContext(request).getItemRight():"");
		}
		return new ModelAndView("admin/project/cljsgl/cljsgl_list").addObject("itemCheck", itemCheck);
	}
	/**
	 *新增页面 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增页面");	
		ItemCheck itemCheck = new ItemCheck();	
		itemCheck.setStatus("1");
		itemCheck.setAppdate(new java.sql.Timestamp(new Date().getTime()));
		itemCheck.setAppEmp(this.getUserContext(request).getCzybh());
		if (itemCheck.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getAppEmp());
			itemCheck.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if(StringUtils.isNotBlank(this.getUserContext(request).getItemRight())){
			itemCheck.setItemType1(this.getUserContext(request).getItemRight().split(",").length==1?this.getUserContext(request).getItemRight():"");
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_save")
			.addObject("itemCheck", itemCheck).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/**
	 *编辑页面 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑页面");	
		ItemCheck itemCheck = null;
		String custDescr="";
		
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		if (itemCheck.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getAppEmp());
			itemCheck.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (itemCheck.getNo()!=null) {
			Customer customer= null;
			customer = customerService.get(Customer.class, itemCheck.getCustCode());
			itemCheck.setAddress(itemCheck==null?"":customer.getAddress());
			custDescr=customer.getDescr();
		}
		if (itemCheck.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getConfirmEmp());
			itemCheck.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_update")
			.addObject("itemCheck", itemCheck).addObject("custDescr", custDescr);
	}
	
	/**
	 *审核页面 
	 * */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到审核页面");	
		ItemCheck itemCheck = null;
		String custDescr="";
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		if (itemCheck.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getAppEmp());
			itemCheck.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (itemCheck.getNo()!=null) {
			Customer customer= null;
			customer = customerService.get(Customer.class, itemCheck.getCustCode());
			itemCheck.setAddress(itemCheck==null?"":customer.getAddress());
			custDescr=customer.getDescr();
		}
		itemCheck.setConfirmEmp(this.getUserContext(request).getCzybh());
		itemCheck.setConfirmDate(new java.sql.Timestamp(new Date().getTime()));
		if (itemCheck.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getConfirmEmp());
			itemCheck.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_check")
			.addObject("itemCheck", itemCheck).addObject("custDescr", custDescr);
	}
	
	/**
	 *反审核页面 
	 * */
	@RequestMapping("/goBack")
	public ModelAndView goBack(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到反审核页面");	
		ItemCheck itemCheck = null;
		String custDescr="";
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		if (itemCheck.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getAppEmp());
			itemCheck.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (itemCheck.getNo()!=null) {
			Customer customer= null;
			customer = customerService.get(Customer.class, itemCheck.getCustCode());
			itemCheck.setAddress(itemCheck==null?"":customer.getAddress());
			custDescr=customer.getDescr();
		}
		if (itemCheck.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getConfirmEmp());
			itemCheck.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_back")
			.addObject("itemCheck", itemCheck).addObject("custDescr", custDescr);
	}
	
	/**
	 *材料升级页面 
	 * */
	@RequestMapping("/goItemUp")
	public ModelAndView goItemUp(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到材料升级页面");	
		ItemCheck itemCheck = null;
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		Customer customer = new Customer();
		if (StringUtils.isNotBlank(id)){
			customer = customerService.get(Customer.class,itemCheck.getCustCode());
		}else{
			itemCheck = new ItemCheck();
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_itemUp")
			.addObject("itemCheck", itemCheck).addObject("customer",customer);
	}
	
	/**
	 *查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看页面");	
		ItemCheck itemCheck = null;
		String custDescr="";
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		if (itemCheck.getAppEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getAppEmp());
			itemCheck.setAppEmpDescr(employee==null?"":employee.getNameChi());
		}
		if (itemCheck.getNo()!=null) {
			Customer customer= null;
			customer = customerService.get(Customer.class, itemCheck.getCustCode());
			itemCheck.setAddress(itemCheck==null?"":customer.getAddress());
			custDescr = customer.getDescr();
		}
		//itemCheck.setConfirmEmp(this.getUserContext(request).getCzybh());
		//itemCheck.setConfirmDate(new java.sql.Timestamp(new Date().getTime()));
		if (itemCheck.getConfirmEmp()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, itemCheck.getConfirmEmp());
			itemCheck.setConfirmEmpDescr(employee==null?"":employee.getNameChi());
		
		}
        return new ModelAndView("admin/project/cljsgl/cljsgl_view")
			.addObject("itemCheck", itemCheck).addObject("custDescr", custDescr);
	}
	
	/**
	 *打印页面 
	 * */
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到打印页面");	
		ItemCheck itemCheck = null;
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		String path="admin/project/cljsgl/cljsgl_print";
		//path=path+itemCheck.getItemType1();
		String logoFile=cmpCustTypeService.getLogoFile(itemCheck.getCustCode());
		return new ModelAndView(path).addObject("itemCheck", itemCheck)
				.addObject("logoFile", logoFile);
	}
	/**
	 *优惠分摊
	 * */
	@RequestMapping("/goDiscCost")
	public ModelAndView goDiscCost(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到优惠分摊");	
		ItemCheck itemCheck = null;
		if (StringUtils.isNotBlank(id)){
			itemCheck = cljsglService.get(ItemCheck.class,id);
		}else{
			itemCheck = new ItemCheck();
		}
		if(StringUtils.isNotBlank(this.getUserContext(request).getItemRight())){
			itemCheck.setItemType1(this.getUserContext(request).getItemRight().split(",").length==1?this.getUserContext(request).getItemRight():"");
		}
		String path="admin/project/cljsgl/cljsgl_discCost";
		return new ModelAndView(path).addObject("itemCheck", itemCheck);
	}
	
	/**
	 * 判断页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/ReqQtyEqualSendQty")
	@ResponseBody
	public String ReqQtyEqualSendQty(HttpServletRequest request, HttpServletResponse response,ItemCheck itemCheck){
		Map<String, Object>  listRet= cljsglService.IsContinueWhenReqQtyNotEqualSendQty(itemCheck.getCustCode(),itemCheck.getItemType1());
		if ((listRet!=null)&&(listRet.size()>0)) {		
			itemCheck.setRet(listRet.get("ret").toString()); //1:有进行材料结算操作 0:无进行材料结算操作
		}
		return	itemCheck.getRet();	
	}
	
	/**
	 * 保存页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCljsglSave")
	public void doCljsglSave(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理新增开始");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("A");
			itemCheck.setExpired("F"); 						
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理新增失败");
		}
	}
	
	/**
	 * 编辑页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCljsglUpdate")
	public void doCljsglUpdate(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理编辑保存");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("M");
			itemCheck.setExpired("F"); 						
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理编辑失败");
		}
	}
	/**
	 * 审核页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCljsglCheck")
	public void doCljsglCheck(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理审核");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("C");
			itemCheck.setExpired("F"); 						
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理审核失败");
		}
	}
	
	@RequestMapping("/doCljsglCheckReturn")
	public void doCljsglCheckReturn(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理审核—退回");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("R");
			itemCheck.setExpired("F"); 						
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理审核失败");
		}
	}
	/**
	 * 反审核页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCljsglBack")
	public void doCljsglBack(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理反审核");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("B");
			itemCheck.setExpired("F"); 						
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理反审核失败");
		}
	}
	
	/**
	 * 保存材料升级页面
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCljsglItemUp")
	public void doCljsglItemUp(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理材料升级");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("I");
			itemCheck.setExpired("F"); 									
			Result result = this.cljsglService.docljsglSave(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理材料升级失败");
		}
	}
	/**
	 * 保存优惠分摊
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/docljsglDiscCost")
	public void docljsglDiscCost(HttpServletRequest request,HttpServletResponse response,ItemCheck itemCheck){
		logger.debug("材料结算管理优惠分摊");		
		try {
			itemCheck.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemCheck.setLastUpdate(new Date());
			itemCheck.setM_umState("Y");			
			itemCheck.setExpired("F"); 		
			Result result = this.cljsglService.doCljsglDiscCost(itemCheck);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			return ;
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料结算管理优惠分摊失败");
		}
	}
	
	/**
	 * 当Qty不等于SendQty时
	 * 跳转到材料确认页面
	 * */
	@RequestMapping("/goAddCljsqr")
	public ModelAndView goAddCljsqr(HttpServletRequest request,
			HttpServletResponse response, ItemCheck itemCheck) throws Exception {
		return new ModelAndView("admin/project/cljsgl/cljsgl_addCljsqr").addObject("itemCheck", itemCheck);
	}
	
	/**
	 * 当Qty不等于SendQty时
	 * 跳转到材料确认页面
	 * */
	@RequestMapping("/goUpdateCljsqr")
	public ModelAndView goUpdateCljsqr(HttpServletRequest request,
			HttpServletResponse response, ItemCheck itemCheck) throws Exception {
		return new ModelAndView("admin/project/cljsgl/cljsgl_updateCljsqr").addObject("itemCheck", itemCheck);
	}
	
	/**
	 * 当Qty不等于SendQty时
	 * 跳转到材料确认页面
	 * */
	@RequestMapping("/goCheckCljsqr")
	public ModelAndView goCheckCljsqr(HttpServletRequest request,
			HttpServletResponse response, ItemCheck itemCheck) throws Exception {
		
		return new ModelAndView("admin/project/cljsgl/cljsgl_checkCljsqr").addObject("itemCheck", itemCheck);
	}
	
	/**
	 * 查询材料确认表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridCljsqr")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCljsqr(HttpServletRequest request,
			HttpServletResponse response,ItemCheck itemCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cljsglService.findPageBySqlCljsqr(page, itemCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询优惠分摊页面表格
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDiscCost")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDiscCost(HttpServletRequest request,
			HttpServletResponse response,ItemCheck itemCheck) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cljsglService.findPageBySqlCljsDiscCost(page, itemCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getHasNotConfirmedItemChg")
	@ResponseBody
	public boolean getHasNotConfirmedItemChg(HttpServletRequest request,HttpServletResponse response,
			String custCode,String itemType1){
		
		return cljsglService.getHasNotConfirmedItemChg(custCode,itemType1);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemCheck itemCheck){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		String itemRight= uc.getItemRight();
		page.setPageSize(-1);
		cljsglService.findPageBySql(page, itemCheck,itemRight);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料结算管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 获取客户付款相关信息
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/getCustomerPay")
	@ResponseBody
	public Map<String, Object> getQualityFee(HttpServletRequest request,
			HttpServletResponse response, String custCode)
			throws Exception {
		Map<String,Object> customerPayMap = null;
		Customer customer=customerService.get(Customer.class,custCode);
		if(customer!=null){
			CustType custType=customerService.get(CustType.class,customer.getCustType());
			if("0".equals(custType.getIsAddAllInfo())&&"3".equals(custType.getIsPartDecorate())){
			    customerPayMap = customerService.getCustomerPayByCode(custCode);
				customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(custCode));
				customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(custCode));
				customerPayMap.put("wdz", Double.parseDouble(customerPayMap.get("wdz").toString())+Double.parseDouble(customerPayMap.get("zjzje").toString())-Double.parseDouble(customerPayMap.get("haspay").toString()));
			}else{
				customerPayMap.put("wdz",0);		
			}	
		}	
		return customerPayMap;
	}
		
}
