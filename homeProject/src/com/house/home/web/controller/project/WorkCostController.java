package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.project.WorkCostService;

@Controller
@RequestMapping("/admin/workCost")
public class WorkCostController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkCostController.class);

	@Autowired
	private WorkCostService workCostService;
	
	@Autowired
	private XtcsService xtcsService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkCost workCost) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workCostService.findPageBySql(page, workCost);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 导入定责工资查询
	 * @author	created by zb
	 * @date	2019-7-19--下午3:17:11
	 * @param request
	 * @param response
	 * @param fixDuty
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFixDutyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFixDutyJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixDuty fixDuty) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workCostService.goFixDutyJqGrid(page, fixDuty);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * WorkCost列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workCost/workCost_list");
	}
	/**
	 * WorkCost查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workCost/workCost_code");
	}
	/**
	 * 跳转导入定责功能
	 * @author	created by zb
	 * @date	2019-7-19--下午4:00:40
	 * @param request
	 * @param response
	 * @param workCost
	 * @return
	 */
	@RequestMapping("/goFixDuty")
	public ModelAndView goFixDuty(HttpServletRequest request, HttpServletResponse response, 
			FixDuty fixDuty){
		return new ModelAndView("admin/project/workCost/workCost_fixDuty")
			.addObject("keys", fixDuty.getKeys());
	}
	/**
	 * 跳转到新增WorkCost页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response,WorkCost workCost){
		logger.debug("跳转到新增WorkCost页面");
		Xtcs xtcs_BaseOneCtrl = xtcsService.get(Xtcs.class, "BaseOneCtrl");
		Xtcs xtcs_BaseAllCtrl = xtcsService.get(Xtcs.class, "BaseAllCtrl");
		Xtcs xtcs_AftCustCode = xtcsService.get(Xtcs.class, "AftCustCode");
		if("add".equals(workCost.getButton())){
			workCost.setNo("保存时生成");
			workCost.setAppCzy(getUserContext(request).getEmnum());
			workCost.setAppCZYDescr(workCostService.findNameByEmnum(getUserContext(request).getEmnum()));
			workCost.setDate(new Date());
			workCost.setStatus("1");	
		}else if ("examine".equals(workCost.getButton())) {
			workCost.setConfirmCzy(getUserContext(request).getEmnum());
			workCost.setConfirmCZYDescr(workCostService.findNameByEmnum(getUserContext(request).getEmnum()));
			workCost.setConfirmDate(new Date());
		}else if("sign".equals(workCost.getButton())) {
			workCost.setPayCzy(getUserContext(request).getEmnum());
			workCost.setPayCZYDescr(workCostService.findNameByEmnum(getUserContext(request).getEmnum()));
			workCost.setPayDate(new Date());
		}
		workCost.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/workCost/workCost_add")
			.addObject("workCost", workCost).addObject("BaseOneCtrl", xtcs_BaseOneCtrl.getQz())
			.addObject("BaseAllCtrl", xtcs_BaseAllCtrl.getQz())
			.addObject("AftCustCode", xtcs_AftCustCode.getQz());
	}
	/**
	 * 跳转到修改WorkCost页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		return goAdd(request,response,workCost);
	}
	/**
	 * 跳转到出纳签字页面
	 * @return
	 */
	@RequestMapping("/goSign")
	public ModelAndView goSign(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		return goAdd(request,response,workCost);
	}
	/**
	 * 跳转到审核WorkCost页面
	 * @return
	 */
	@RequestMapping("/goExamine")
	public ModelAndView goExamine(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		return goAdd(request,response,workCost);
	}
	/**
	 * 跳转到反审核页面
	 * @return
	 */
	@RequestMapping("/goReturnExamine")
	public ModelAndView goReturnExamine(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		return goAdd(request,response,workCost);
	}
	/**
	 * 跳转到查看WorkCost页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		return goAdd(request,response,workCost);
	}
	
	/**
	 *WorkCost导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkCost workCost){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostService.findPageBySql(page, workCost);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础人工成本管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转人工成本汇总
	 * @return
	 */
	@RequestMapping("/goCostGather")
	public ModelAndView goCostGather(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		logger.debug("跳转到修改WorkCost页面");
		workCost.setType("1");
		return new ModelAndView("admin/project/workCost/workCost_costGather")
			.addObject("workCost", workCost);
	}
	
	/**
	 * 跳转到工资出账处理页面
	 * @return
	 */
	@RequestMapping("/goSalaryCheckOut")
	public ModelAndView goSalaryCheckOut(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		logger.debug("跳转到工资出账处理页面");
		return new ModelAndView("admin/project/workCost/workCost_salaryCheckOut")
			.addObject("workCost", workCost);
	}
	
	/**
	 * 跳转到工资出账查询
	 * @return
	 */
	@RequestMapping("/goSalaryCheckOutQuery")
	public ModelAndView goSalaryCheckOutQuery(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到工资出账查询");
		return new ModelAndView("admin/project/workCost/workCost_salaryCheckOutQuery");
	}
	
	/**
	 * 查询人工成本汇总JqGrid2表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid2(HttpServletRequest request,
			HttpServletResponse response, WorkCost workCost) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workCostService.findPageBySql2(page, workCost);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转人工成本汇总
	 * @return
	 */
	@RequestMapping("/goWorkCard")
	public ModelAndView goWorkCard(HttpServletRequest request, HttpServletResponse response, 
			WorkCost workCost){
		logger.debug("跳转到修改WorkCost页面");
		return new ModelAndView("admin/project/workCost/workCost_workCard")
			.addObject("workCost", workCost);
	}
	/**
	 * 按账号汇总JqGrid3表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid3")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid3(HttpServletRequest request,
			HttpServletResponse response, WorkCost workCost) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//讲字符串拆分成数组，加上单引号再拼接成字符串，用于 in()查询
		String[] array=workCost.getNos().split(",");
		
		String nos="";
		for (int i = 0; i < array.length; i++) {
			nos+="'"+array[i]+"',";
		}
		workCost.setNos(nos.substring(0,nos.length()-1));
		workCostService.findPageBySql3(page, workCost);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *WorkCost导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_workCard")
	public void doExcel_workCard(HttpServletRequest request, 
			HttpServletResponse response, WorkCost workCost){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostService.findPageBySql3(page, workCost);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础人工成本按账号汇总_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 保存，审核，审核取消，反审核，出纳签字
	 * @param request
	 * @param response
	 * @param workCost
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,WorkCost workCost){
		logger.debug("保存");		
		try {	
			workCost.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workCost.setLastUpdate(new Date());
			workCost.setExpired("F"); 
			Result result = this.workCostService.doSaveProc(workCost);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
}
