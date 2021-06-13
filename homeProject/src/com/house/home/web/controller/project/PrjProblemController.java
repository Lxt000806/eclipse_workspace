package com.house.home.web.controller.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.impl.PrjProblemUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.PrjProblem;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProblemService;

@Controller
@RequestMapping("admin/prjProblem")
public class PrjProblemController extends BaseController{
	@Autowired
	public Department2Service department2Service;
	@Autowired
	public CustomerService customerService;
	@Autowired
	public EmployeeService employeeService;
	@Autowired
	public PrjProblemService prjProblemService;
	@Autowired
	private CzybmService czybmService;
	
	@RequestMapping(value = "/promDeptandType/{type}/{pCode}") //获取商品类型1,2,3
	@ResponseBody
	public JSONObject getPromDeptandType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> PromDeptList = this.prjProblemService.findPromDeptandType(type, pCode);
		return this.out(PromDeptList, true);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProblem prjProblem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		prjProblemService.findPageBySql(page, prjProblem,uc);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goPicJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPicJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjProblem prjProblem) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProblemService.goPicJqGrid(page, prjProblem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean hasManageRight = czybmService.hasGNQXByCzybh(getUserContext(request).getCzybh(), "0335", "管理权限");
		String czybh=getUserContext(request).getCzybh();
		return new ModelAndView("admin/project/prjProblem/prjProblem_list")
		.addObject("hasManageRight", hasManageRight).addObject("czybh", czybh);
		
	}
	
	@RequestMapping("/goModify")
	public ModelAndView goModify(HttpServletRequest request,
			HttpServletResponse response ,String no) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee appCzy=null;
		Employee confirmCzy=null;
		
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		if(StringUtils.isNotBlank(prjProblem.getConfirmCZY())){
			confirmCzy=employeeService.get(Employee.class, prjProblem.getConfirmCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getAppCZY())){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
		}
		
		return new ModelAndView("admin/project/prjProblem/prjProblem_modify").addObject("prjProblem", prjProblem)
				.addObject("appCzy", appCzy).addObject("confirmCzy", confirmCzy);
	}
	
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request,
			HttpServletResponse response ,String no) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee appCzy=null;
		Employee confirmCzy=employeeService.get(Employee.class, this.getUserContext(request).getCzybh());
		
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		if(prjProblem.getAppCZY()!=""){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		prjProblem.setConfirmCZY(this.getUserContext(request).getCzybh());
		prjProblem.setConfirmDate(new Date());
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
		}
		
		return new ModelAndView("admin/project/prjProblem/prjProblem_confirm").addObject("prjProblem", prjProblem)
				.addObject("appCzy", appCzy).addObject("confirmCzy", confirmCzy);
	}
	
	@RequestMapping("/goFeedback")
	public ModelAndView goFeedback(HttpServletRequest request,
			HttpServletResponse response ,String no) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee confirmCzy=null;
		Employee appCzy=null;
		Employee feedbackCzy=null;
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		if(prjProblem.getAppCZY()!=""){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
			prjProblem.setFeedbackCZY(this.getUserContext(request).getCzybh());
			prjProblem.setFeedbackDate(new Date());
		}
		if(StringUtils.isNotBlank(prjProblem.getConfirmCZY())){
			confirmCzy=employeeService.get(Employee.class, prjProblem.getConfirmCZY());
		}
		
		feedbackCzy=employeeService.get(Employee.class, this.getUserContext(request).getCzybh());
		
		return new ModelAndView("admin/project/prjProblem/prjProblem_feedback")
		.addObject("prjProblem", prjProblem)
		.addObject("confirmCzy", confirmCzy)
		.addObject("appCzy", appCzy)
		.addObject("feedbackCzy", feedbackCzy);
	}
	
	@RequestMapping("/goDeal")
	public ModelAndView goDeal(HttpServletRequest request,
			HttpServletResponse response ,String no) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee confirmCzy=null;
		Employee appCzy=null;
		Employee feedbackCzy=null;
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
		}
		if(prjProblem.getAppCZY()!=""&&prjProblem.getAppCZY()!=null){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getConfirmCZY())){
			confirmCzy=employeeService.get(Employee.class, prjProblem.getConfirmCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getFeedbackCZY())){
			feedbackCzy=employeeService.get(Employee.class, prjProblem.getFeedbackCZY());
		}
		prjProblem.setDealCZY(this.getUserContext(request).getCzybh());
		prjProblem.setDealDate(new Date());
		
		return new ModelAndView("admin/project/prjProblem/prjProblem_deal")
		.addObject("prjProblem", prjProblem)
		.addObject("dealCZYDescr",this.getUserContext(request).getZwxm())
		.addObject("confirmCzy", confirmCzy)
		.addObject("appCzy", appCzy)
		.addObject("feedbackCzy", feedbackCzy);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response ) throws Exception {
		PrjProblem prjProblem=new PrjProblem();
		prjProblem.setConfirmCZY(this.getUserContext(request).getCzybh());
		prjProblem.setConfirmDate(new Date());
		prjProblem.setConfirmCZYDescr(this.getUserContext(request).getZwxm());
		return new ModelAndView("admin/project/prjProblem/prjProblem_save").addObject("prjProblem",prjProblem);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response ,String no,String picNum) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee confirmCzy=null;
		Employee appCzy=null;
		Employee feedbackCzy=null;
		Employee dealCzy=null;
		Employee cancelCzy=null;
		Employee fixDutyMan=null;
		
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
		}
		if(prjProblem.getAppCZY()!=""&&prjProblem.getAppCZY()!=null){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getConfirmCZY())){
			confirmCzy=employeeService.get(Employee.class, prjProblem.getConfirmCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getFeedbackCZY())){
			feedbackCzy=employeeService.get(Employee.class, prjProblem.getFeedbackCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getDealCZY())){
			dealCzy=employeeService.get(Employee.class, prjProblem.getDealCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getCancelCZY())){
			cancelCzy=employeeService.get(Employee.class, prjProblem.getCancelCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getFixDutyMan())){
			fixDutyMan=employeeService.get(Employee.class, prjProblem.getFixDutyMan());
		}
		return new ModelAndView("admin/project/prjProblem/prjProblem_view")
		.addObject("prjProblem", prjProblem)
		.addObject("dealCzy",dealCzy)
		.addObject("confirmCzy", confirmCzy)
		.addObject("appCzy", appCzy)
		.addObject("cancelCzy", cancelCzy)
		.addObject("feedbackCzy", feedbackCzy)
		.addObject("fixDutyMan", fixDutyMan)
		.addObject("picNum", picNum);
	}
			
	@RequestMapping("/goFixDuty")
	public ModelAndView goFixDuty(HttpServletRequest request,
			HttpServletResponse response ,String no) throws Exception {
		PrjProblem prjProblem=null;
		Customer customer=null;
		Employee confirmCzy=null;
		Employee appCzy=null;
		Employee feedbackCzy=null;
		Employee dealCzy=null;
		Employee cancelCzy=null;
		Employee fixDutyMan=null;
		
		prjProblem=prjProblemService.get(PrjProblem.class, no);
		prjProblem.setFixDutyDate(new Date());
		prjProblem.setFixDutyMan(getUserContext(request).getCzybh());
		if(prjProblem!=null){
			customer=customerService.get(Customer.class, prjProblem.getCustCode());
			prjProblem.setAddress(customer.getAddress());
			prjProblem.setCustDescr(customer.getDescr());
		}
		if(prjProblem.getAppCZY()!=""&&prjProblem.getAppCZY()!=null){
			appCzy=employeeService.get(Employee.class, prjProblem.getAppCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getConfirmCZY())){
			confirmCzy=employeeService.get(Employee.class, prjProblem.getConfirmCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getFeedbackCZY())){
			feedbackCzy=employeeService.get(Employee.class, prjProblem.getFeedbackCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getDealCZY())){
			dealCzy=employeeService.get(Employee.class, prjProblem.getDealCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getCancelCZY())){
			cancelCzy=employeeService.get(Employee.class, prjProblem.getCancelCZY());
		}
		if(StringUtils.isNotBlank(prjProblem.getFixDutyMan())){
			fixDutyMan=employeeService.get(Employee.class, prjProblem.getFixDutyMan());
		}
		return new ModelAndView("admin/project/prjProblem/prjProblem_fixDuty")
		.addObject("prjProblem", prjProblem)
		.addObject("dealCzy",dealCzy)
		.addObject("confirmCzy", confirmCzy)
		.addObject("appCzy", appCzy)
		.addObject("cancelCzy", cancelCzy)
		.addObject("feedbackCzy", feedbackCzy)
		.addObject("fixDutyMan", fixDutyMan);
	}
	
	@RequestMapping("/doSaveConfirm")
	public void doSaveConfirm(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题确认");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setPromDeptCode(prjProblem.getPromDeptCode());
			ppb.setPromPropCode(prjProblem.getPromPropCode());
			ppb.setPromTypeCode(prjProblem.getPromTypeCode());
			ppb.setRemarks(prjProblem.getRemarks());
			ppb.setStatus("2");
			ppb.setConfirmCZY(this.getUserContext(request).getCzybh());
			ppb.setConfirmDate(new Date());
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			ppb.setIsBringStop(prjProblem.getIsBringStop());
			if("02".equals(ppb.getPromDeptCode())){
				ppb.setDealDate(new Date());
				ppb.setDealCZY(this.getUserContext(request).getCzybh());
				ppb.setStatus("4");
			}
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题管理确认失败");
		}
	}
	
	@RequestMapping("/doSaveFeedback")
	public void doSaveFeedback(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题反馈");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setPlanDealDate(prjProblem.getPlanDealDate());
			ppb.setDealRemarks(prjProblem.getDealRemarks());
			ppb.setStatus("3");
			ppb.setFeedbackCZY(this.getUserContext(request).getCzybh());
			ppb.setFeedbackDate(new Date());
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题反馈失败");
		}
	}
	
	@RequestMapping("/doSaveDeal")
	public void doSaveDeal(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题处理");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setStatus("4");
			ppb.setDealCZY(prjProblem.getDealCZY());
			ppb.setDealDate(prjProblem.getDealDate());
			ppb.setIsDeal(prjProblem.getIsDeal());
			ppb.setLastUpdate(new Date());
			ppb.setDealRemarks(prjProblem.getDealRemarks());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			ppb.setIsBringStop(prjProblem.getIsBringStop());
			ppb.setStopDays(prjProblem.getStopDays());
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题处理失败");
		}
	}
	
	@RequestMapping("/doSaveCancelConfirm")
	public void doSaveCancelConfirm(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题反馈撤回");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setStatus("1");
			ppb.setConfirmCZY("");
			ppb.setConfirmDate(null);
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response,"撤回确认成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题撤回确认失败");
		}
	}
	
	@RequestMapping("/doSaveCancelFeedback")
	public void doSaveCancelFeedback(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题撤回反馈");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setStatus("2");
			ppb.setFeedbackCZY("");
			ppb.setFeedbackDate(null);
			ppb.setPlanDealDate(null);
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response,"撤回反馈成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题撤回反馈失败");
		}
	}
	
	@RequestMapping("/doSaveCancelDeal")
	public void doSaveCancelDeal(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题撤回处理");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setStatus("3");
			ppb.setDealCZY("");
			ppb.setIsDeal("0");
			ppb.setDealDate(null);
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response,"撤回处理成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题撤回处理失败");
		}
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题新增保存");
		
		try{
			String str = prjProblemService.getSeqNo("tPrjProblem");
			prjProblem.setNo(str);
			prjProblem.setIsDeal("0");
			prjProblem.setExpired("F");
			prjProblem.setActionLog("Add");
			prjProblem.setLastUpdate(new Date());
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjProblemService.save(prjProblem);
			
			ServletUtils.outPrintSuccess(request, response,"操作成功");
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doModify")
	public void doModify(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题修改保存");
		
		try{ 
			prjProblem.setIsDeal("0");
			prjProblem.setExpired("F");
			prjProblem.setActionLog("Edit");
			prjProblem.setLastUpdate(new Date());
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjProblemService.update(prjProblem);
			
			ServletUtils.outPrintSuccess(request, response,"操作成功");
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doFixDuty")
	public void doFixDuty(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem pp){
		logger.debug("工程问题定责保存");
		
		try{ 
			PrjProblem prjProblem=prjProblemService.get(PrjProblem.class, pp.getNo());
			prjProblem.setIsBringStop(pp.getIsBringStop());
			prjProblem.setFixDutyMan(pp.getFixDutyMan());
			prjProblem.setFixDutyDate(pp.getFixDutyDate());
			prjProblem.setDealRemarks(pp.getDealRemarks());
			prjProblem.setPromTypeCode(pp.getPromTypeCode());
			prjProblem.setStopDays(pp.getStopDays());
			prjProblem.setActionLog("Edit");
			prjProblem.setLastUpdate(new Date());
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjProblemService.update(prjProblem);
			
			ServletUtils.outPrintSuccess(request, response,"操作成功");
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	@RequestMapping("/doSaveCancel")
	public void doSaveCancel(HttpServletRequest request ,
			HttpServletResponse response ,PrjProblem prjProblem){
		logger.debug("工程问题取消");
		try{
			PrjProblem ppb=null;
			ppb=prjProblemService.get(PrjProblem.class, prjProblem.getNo());
			ppb.setStatus("5");
			ppb.setCancelCZY(this.getUserContext(request).getCzybh());
			ppb.setCancelDate(new Date());
			ppb.setLastUpdate(new Date());
			ppb.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppb.setActionLog("Edit");
			this.prjProblemService.update(ppb);
			
			ServletUtils.outPrintSuccess(request, response,"撤销成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程问题取消失败");
		}
	}
	
	@RequestMapping("/isExistType")
	@ResponseBody
	public boolean isExistType(HttpServletRequest request,HttpServletResponse response,
			String deptCode){
		logger.debug("ajax获取数据");  
		
		return prjProblemService.isExistType(deptCode);
	}
	
	@RequestMapping("/changePromTypes")
	public void changePromTypes(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载责任类型");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "code";
		String sqlLableKey = "descr";
		String retStr = "[]";
		if (StringUtils.isBlank(code)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("strSelect", retStr);
			try {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String sql = "select a.code,a.descr "
				+"from tPrjPromType a inner join (select * from fStrToTable('"+code+"',',')) t on a.DeptCode=t.item "
				+"where a.Expired='F' order by a.DeptCode";
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", virtualRootId);
		item.put("pId", "");
		item.put("name", virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				item = new HashMap<String, Object>();
				item.put("id", map.get(sqlValueKey));
				item.put("pId", virtualRootId);
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
				rsList.add(item);
			}
		}
		retStr = JSON.toJSONString(rsList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", retStr);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjProblem prjProblem){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		prjProblemService.findPageBySql(page, prjProblem,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地问题表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到修改custProblem页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String no,String dealRemarks){
		logger.debug("跳转到修改PrjRegion页面");
		PrjProblem prjProblem =new PrjProblem();
		prjProblem.setDealRemarks(dealRemarks);
		prjProblem.setNo(no);
		return new ModelAndView("admin/project/prjProblem/prjProblem_update")
			.addObject("prjProblem", prjProblem);
	}
	
	/**
	 * 修改custProblem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjProblem prjProblem){
		logger.debug("修改PrjRegion开始");
		try{
			if(!"T".equals(prjProblem.getExpired())){
				prjProblem.setExpired("F");
			}
			prjProblem.setLastUpdate(new Date());
			prjProblem.setActionLog("EDIT");
			prjProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjProblemService.doUpdate(prjProblem);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 跳转到查看图片页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewPic")
	public ModelAndView goViewSendPhoto(HttpServletRequest request,
			HttpServletResponse response,PrjProblem prjProblem) throws Exception {

		return new ModelAndView("admin/project/prjProblem/prjProblem_viewPic")
			.addObject("prjProblem", prjProblem);
	}
	
	/**
	 * 查看图片页面
	 * 
	 */
	@RequestMapping("/ajaxGetPicture")
	@ResponseBody
	public PrjProblem getPicture(HttpServletRequest request, HttpServletResponse response,PrjProblem prjProblem){
		
		prjProblem.setPhotoPath(FileUploadUtils.DOWNLOAD_URL+prjProblem.getPhotoName());
		return prjProblem;
	}
}
