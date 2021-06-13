package com.house.home.web.controller.basic;

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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.service.AuthorityService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.CustRecommend;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustMapper;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.CustRecommendService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.WorkerService;

@Controller
@RequestMapping("/admin/custRecommend")
public class CustRecommendController extends BaseController{

	
	@Autowired
	private CustRecommendService custRecommendService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private CustomerService customerService;

	
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,	HttpServletResponse response, CustRecommend custRecommend){
		logger.info("跳转到工人推荐客户管理主页");
		return new ModelAndView("admin/basic/custRecommend/custRecommend_list").addObject("custRecommend", custRecommend);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,	HttpServletResponse response, CustRecommend custRecommend) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=getUserContext(request);
		custRecommendService.goJqGrid(page, custRecommend, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,CustRecommend custRecommend){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=getUserContext(request);
		custRecommendService.goJqGrid(page, custRecommend,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工人推荐客户管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到工人推荐客户管理确认信息页");
		CustRecommend custRecommend = null;

		custRecommend=custRecommendService.get(CustRecommend.class, pk);
		String nameChi="";
		if(StringUtils.isNotBlank(custRecommend.getRecommender())){
			if("1".equals(custRecommend.getRecommenderType())){
				Worker worker=workerService.get(Worker.class, custRecommend.getRecommender());
				nameChi=worker.getNameChi();
			}else if("2".equals(custRecommend.getRecommenderType())){
				Employee employee=workerService.get(Employee.class, custRecommend.getRecommender());
				nameChi=employee.getNameChi();
			}else{
				nameChi="客户";
			}
			custRecommend.setRecommenderDescr(nameChi);
		}
		return new ModelAndView("admin/basic/custRecommend/custRecommend_confirm")
		.addObject("custRecommend", custRecommend);
	}

	@RequestMapping("/doConfirm")
	public void doConfirm(HttpServletRequest request ,
			HttpServletResponse response ,Integer pk,String status,String confirmRemarks,String address,String custName){
		logger.debug("工人推荐客户确认信息");
		CustRecommend custRecommend = custRecommendService.get(CustRecommend.class, pk);
		try{
			custRecommend.setStatus(status);
			custRecommend.setAddress(address);
			custRecommend.setCustName(custName);
			custRecommend.setConfirmDate(new Date());
			custRecommend.setConfirmRemarks(confirmRemarks);
			custRecommend.setLastUpdate(new Date());
			custRecommend.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custRecommend.setActionLog("EDIT");
			custRecommendService.update(custRecommend);
			ResrCust resrCust = custRecommendService.get(ResrCust.class, custRecommend.getResrCustCode());
			resrCust.setAddress(address);
			resrCust.setDescr(custName);
			resrCust.setCustResStat(status);
			resrCust.setLastUpdate(new Date());
			resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			resrCust.setActionLog("EDIT");
			custRecommendService.update(resrCust);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到工人推荐客户管理确认信息页");
		CustRecommend custRecommend = null;

		custRecommend=custRecommendService.get(CustRecommend.class, pk);
		String nameChi="";
		if(StringUtils.isNotBlank(custRecommend.getRecommender())){
			if("1".equals(custRecommend.getRecommenderType())){
				Worker worker=workerService.get(Worker.class, custRecommend.getRecommender());
				nameChi=worker.getNameChi();
			}else if("2".equals(custRecommend.getRecommenderType())){
				Employee employee=workerService.get(Employee.class, custRecommend.getRecommender());
				nameChi=employee.getNameChi();
			}else{
				nameChi="客户";
			}
			custRecommend.setRecommenderDescr(nameChi);
		}
		return new ModelAndView("admin/basic/custRecommend/custRecommend_update")
		.addObject("custRecommend", custRecommend);
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response ,Integer pk,String status,String confirmRemarks,String address,String custName){
		logger.debug("工人推荐客户编辑");
		CustRecommend custRecommend = custRecommendService.get(CustRecommend.class, pk);
		try{
			custRecommend.setStatus(status);
			custRecommend.setAddress(address);
			custRecommend.setCustName(custName);
			custRecommend.setConfirmDate(new Date());
			custRecommend.setConfirmRemarks(confirmRemarks);
			custRecommend.setLastUpdate(new Date());
			custRecommend.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custRecommend.setActionLog("EDIT");
			custRecommendService.update(custRecommend);
			ResrCust resrCust = custRecommendService.get(ResrCust.class, custRecommend.getResrCustCode());
			resrCust.setAddress(address);
			resrCust.setDescr(custName);
			resrCust.setCustResStat(status);
			resrCust.setLastUpdate(new Date());
			resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			resrCust.setActionLog("EDIT");
			custRecommendService.update(resrCust);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/goArrive")
	public ModelAndView goArrive(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到工人推荐客户管理确认信息页");
		CustRecommend custRecommend = null;

		custRecommend=custRecommendService.get(CustRecommend.class, pk);
		String nameChi="";
		if(StringUtils.isNotBlank(custRecommend.getRecommender())){
			if("1".equals(custRecommend.getRecommenderType())){
				Worker worker=workerService.get(Worker.class, custRecommend.getRecommender());
				nameChi=worker.getNameChi();
			}else if("2".equals(custRecommend.getRecommenderType())){
				Employee employee=workerService.get(Employee.class, custRecommend.getRecommender());
				nameChi=employee.getNameChi();
			}else{
				nameChi="客户";
			}
			custRecommend.setRecommenderDescr(nameChi);
		}
		return new ModelAndView("admin/basic/custRecommend/custRecommend_arrive")
		.addObject("custRecommend", custRecommend);
	}

	@RequestMapping("/doArrive")
	public void doArrive(HttpServletRequest request ,
			HttpServletResponse response ,Integer pk,String custCode){
		logger.debug("工人推荐客户确认信息");
		CustRecommend custRecommend = custRecommendService.get(CustRecommend.class, pk);
		Customer customer = customerService.get(Customer.class,custCode);
		try{
			if(customer!=null){
				custRecommend.setCustCode(custCode);
				custRecommend.setStatus("3");
				custRecommend.setLastUpdate(new Date());
				custRecommend.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custRecommend.setActionLog("EDIT");
				custRecommendService.update(custRecommend);
				ResrCust resrCust = custRecommendService.get(ResrCust.class, custRecommend.getResrCustCode());
				resrCust.setCustResStat(customer.getStatus());
				resrCust.setLastUpdate(new Date());
				resrCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				resrCust.setActionLog("EDIT");
				custRecommendService.update(resrCust);
				ResrCustMapper resrCustMapper = new ResrCustMapper();
				resrCustMapper.setResrCustCode(custRecommend.getResrCustCode());
				resrCustMapper.setCustCode(custCode);
				resrCustMapper.setLastUpdate(new Date());
				resrCustMapper.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				resrCustMapper.setActionLog("ADD");
				resrCustMapper.setExpired("F");
				custRecommendService.save(resrCustMapper);
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/goView")
	public ModelAndView goVIew(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到推荐客户管理查看信息页");
		CustRecommend custRecommend = null;

		custRecommend=custRecommendService.get(CustRecommend.class, pk);
		String nameChi="";
		if(StringUtils.isNotBlank(custRecommend.getRecommender())){
			if("1".equals(custRecommend.getRecommenderType())){
				Worker worker=workerService.get(Worker.class, custRecommend.getRecommender());
				nameChi=worker.getNameChi();
				custRecommend.setRecommenderPhone(worker.getPhone());
			}else if("2".equals(custRecommend.getRecommenderType()) || "4".equals(custRecommend.getRecommenderType())){
				Employee employee=workerService.get(Employee.class, custRecommend.getRecommender());
				nameChi=employee.getNameChi();
				custRecommend.setRecommenderPhone(employee.getPhone());
			}else{
				nameChi="客户";
			}
			custRecommend.setRecommenderDescr(nameChi);
		}
		if(StringUtils.isNotBlank(custRecommend.getCustCode())){
			Customer customer = customerService.get(Customer.class, custRecommend.getCustCode());
			custRecommend.setRelationAddress(customer.getAddress());
		}
		return new ModelAndView("admin/basic/custRecommend/custRecommend_view")
		.addObject("custRecommend", custRecommend);
	}
	
	@RequestMapping("/goCount")
	public ModelAndView goCount(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到工人推荐客户管理确认信息页");
		return new ModelAndView("admin/basic/custRecommend/custRecommend_count");
	}
	
	@RequestMapping("/goCountJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCountJqGrid(HttpServletRequest request, HttpServletResponse response, CustRecommend custRecommend) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=getUserContext(request);
		custRecommendService.goCountJqGrid(page, custRecommend, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doCountQueryExcel")
	public void doCountQueryExcel(HttpServletRequest request, 
			HttpServletResponse response,CustRecommend custRecommend){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=getUserContext(request);
		custRecommendService.goCountJqGrid(page, custRecommend,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工人推荐客户管理——统计信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
