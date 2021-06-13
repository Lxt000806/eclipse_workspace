package com.house.home.web.controller.finance;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;
import com.house.home.entity.finance.IntPerf;
import com.house.home.entity.finance.PerfCycle;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.finance.PerfCycleService;

@Controller
@RequestMapping("/admin/perfCycle")
public class PerfCycleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PerfCycleController.class);

	@Autowired
	private PerfCycleService perfCycleService;
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
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findPageBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PerfCycle列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/perfCycle/perfCycle_list");
	}
	/**
	 * PerfCycle查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/perfCycle/perfCycle_code");
	}
	/**
	 * 根据id查询perfCycle详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPerfCycle")
	@ResponseBody
	public JSONObject getPerfCycle(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		PerfCycle perfCycle= perfCycleService.get(PerfCycle.class, id);
		if(perfCycle == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(perfCycle, true);
	}
	/**
	 * 跳转到新增PerfCycle页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PerfCycle页面");
		PerfCycle perfCycle = null;
		if (StringUtils.isNotBlank(id)){
			perfCycle = perfCycleService.get(PerfCycle.class, id);
			perfCycle.setNo(null);
		}else{
			perfCycle = new PerfCycle();
		}
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_save")
			.addObject("perfCycle", perfCycle);
	}
	/**
	 * 跳转到修改PerfCycle页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PerfCycle页面");
		PerfCycle perfCycle = null;
		if (StringUtils.isNotBlank(id)){
			perfCycle = perfCycleService.get(PerfCycle.class, id);
		}else{
			perfCycle = new PerfCycle();
		}
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_update")
			.addObject("perfCycle", perfCycle);
	}
	
	/**
	 * 跳转到业绩计算页面
	 * @return
	 */
	@RequestMapping("/goCount")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String no,String m_umState,String status){
		logger.debug("跳转到业绩计算页面");
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_count")
				.addObject("no", no).addObject("m_umState", m_umState).addObject("status",status);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String no,String m_umState,String status){
		logger.debug("跳转到业绩计算页面");
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_count")
				.addObject("no", no).addObject("m_umState", m_umState).addObject("status",status);
	}
	/**
	 * 添加PerfCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PerfCycle perfCycle){
		logger.debug("添加PerfCycle开始");
		try{
			String str = perfCycleService.getSeqNo("tPerfCycle");
			perfCycle.setNo(str);
			perfCycle.setLastUpdate(new Date());
			perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			perfCycle.setExpired("F");
			perfCycle.setActionLog("ADD");
			this.perfCycleService.save(perfCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加PerfCycle失败");
		}
	}
	
	/**
	 * 修改PerfCycle
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PerfCycle perfCycle){
		logger.debug("修改PerfCycle开始");
		try{
			perfCycle.setLastUpdate(new Date());
			perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			perfCycle.setActionLog("EDIT");
			perfCycle.setExpired("F");
			this.perfCycleService.update(perfCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PerfCycle失败");
		}
	}
	
	/**
	 * 删除PerfCycle
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PerfCycle开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PerfCycle编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PerfCycle perfCycle = perfCycleService.get(PerfCycle.class, deleteId);
				if(perfCycle == null)
					continue;
				perfCycle.setExpired("T");
				perfCycleService.update(perfCycle);
			}
		}
		logger.debug("删除PerfCycle IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PerfCycle导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PerfCycle perfCycle){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		perfCycleService.findPageBySql(page, perfCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业绩统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查状态
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkStatus")
	public String checkStatus(HttpServletRequest request,
			HttpServletResponse response, String no) {
		logger.debug("业绩计算查状态");
		return perfCycleService.checkStatus(no);
	}
	/**
	 * 检查周期
	 * @param request
	 * @param response
	 * @param no
	 * @param beginDate
	 * @return
	 */
	@RequestMapping("/isExistsPeriod")
	@ResponseBody
	public String isExistsPeriod(HttpServletRequest request,HttpServletResponse response,
			String no,String beginDate){
	
		return perfCycleService.isExistsPeriod(no,beginDate);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmployeeInfo")
	public ModelAndView goEmployeeInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_employeeInfo");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReport")
	public ModelAndView goReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map<String, Object>>list=perfCycleService.defaultCycle();
		String allCustType=perfCycleService.findAllCustType().get(0).get("allCustType").toString();
		String defautNo="";
		if(list.size()>0 && list!=null){
			defautNo=list.get(0).get("no").toString();
		}
		return new ModelAndView("admin/finance/perfCycle/perfCycle_report")
			.addObject("defautNo", defautNo)
			.addObject("allCustType", allCustType);
	}
	
	@RequestMapping("/goSignData")
	public ModelAndView goSignData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_signData");
	}
	
	@RequestMapping("/goViewGift")
	public ModelAndView goViewGift(HttpServletRequest request, HttpServletResponse response, PerfCycle perfCycle){
		logger.debug("跳转到业绩计算页面");
		
		return new ModelAndView("admin/finance/perfCycle/perfCycle_zsxm").addObject("perfCycle", perfCycle);
	}
	
	/**
	 * 检查业绩计算员工信息是否与当前员工信息一致
	 * 
	 * @param request
	 * @param response
	 * @param workCostDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkEmployeeInfo")
	public List<Map<String, Object>> checkEmployeeInfo(HttpServletRequest request,HttpServletResponse response) {
		logger.debug("检查业绩计算员工信息是否与当前员工信息一致");
		return perfCycleService.checkEmployeeInfo();
	}
	/**
	 * 计算完成
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doComplete")
	public void doComplete(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("业绩计算完成");
		this.perfCycleService.doComplete(no);
		ServletUtils.outPrintSuccess(request, response,"计算完成");
	}
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("业绩计算退回");
		this.perfCycleService.doReturn(no);
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	/**
	 * 查询员工信息同步JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmployeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goEmployeeJqGrid(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfcycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findEmployeePageBySql(page, perfcycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 员工信息同步
	 * @param request
	 * @param response
	 * @param numbers
	 */
	@RequestMapping("/doSyncEmployee")
	public void doSyncEmployee(HttpServletRequest request, HttpServletResponse response, String numbers){
		logger.debug("员工信息同步");
		this.perfCycleService.doSyncEmployee(numbers);
		ServletUtils.outPrintSuccess(request, response,"员工信息同步成功");
	}
	
	/**
	 * 查询部门领导信息同步JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLeaderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLeaderJqGrid(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfcycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findLeaderPageBySql(page, perfcycle);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 部门领导信息同步
	 * @param request
	 * @param response
	 * @param numbers
	 */
	@RequestMapping("/doSyncLeader")
	public void doSyncLeader(HttpServletRequest request, HttpServletResponse response, String codes){
		logger.debug("部门领导信息同步");
		this.perfCycleService.doSyncLeader(codes);
		ServletUtils.outPrintSuccess(request, response,"部门领导信息同步成功");
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportDetailBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportYwbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportYwbJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportYwbBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportSjbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportSjbJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportSjbBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportSybJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportSybJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportSybBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportGcbJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportGcbJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportGcbBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportYwyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportYwyJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportYwyBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据(业务员独立销售)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goReportYwyDlxxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportYwyDlxxJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
	    perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		perfCycleService.findReportYwyDlxxBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据(业务员团队)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportYwtdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportYwtdJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportYwtdBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据(设计团队)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportSjtdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportSjtdJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportSjtdBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportSjsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportSjsJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportSjsBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportFdyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportFdyJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportFdyBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportHtyJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportHtyJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportHtyBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportYwzrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReportYwzrJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findReportYwzrBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 报表导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcelReport")
	public void doExcelReport(HttpServletRequest request, 
			HttpServletResponse response, PerfCycle perfCycle){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		perfCycleService.findReportDetailBySql(page, perfCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业绩明细"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goYjsyjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYjsyjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findYjsyjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询独立销售业绩
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIndependPerfJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goIndependPerfJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findIndependPerfBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWjsyjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWjsyjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findWjsyjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 生成业绩数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,String no,String calChgPerf){
		logger.debug("生成业绩数据");
		Map<String, Object>resultMap=this.perfCycleService.doCount(no, getUserContext(request).getCzybh(), calChgPerf);
		System.out.println(resultMap);
		ServletUtils.outPrintSuccess(request, response,resultMap.get("errmsg").toString());
	}
	/**
	 * 跳转到业绩扣减设置
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPefChgSet")
	public ModelAndView goPefChgSet(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		Map<String, Object> map=perfCycleService.findChgPefByCode(perfCycle).get(0);
		return new ModelAndView("admin/finance/perfCycle/perfCycle_perfChgSet").addObject("perfCycle", map);
	}
	/**
	 * 业绩扣减设置
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doPerfChgSet")
	public void doPerfChgSet(HttpServletRequest request, HttpServletResponse response, PerfCycle perfCycle){
		logger.debug("业绩扣减设置");
		try{
			this.perfCycleService.doPerfChgSet(perfCycle);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改Customer失败");
		}
	}
	
	/**
	 * 已经计算业绩导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doYjsyjExcel")
	public void doYjsyjExcel(HttpServletRequest request, 
			HttpServletResponse response, PerfCycle perfCycle){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		perfCycleService.findYjsyjBySql(page, perfCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业绩计算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到指定客户
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPointCust")
	public ModelAndView goPointCust(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		Map<String, Object> map=perfCycleService.beforePointCust(perfCycle).get(0);
		map.put("m_umState", perfCycle.getM_umState());
		return new ModelAndView("admin/finance/perfCycle/perfCycle_pointCust").addObject("perfCycle", map);
	}
	/**
	 * 查询参与业绩计算表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCyyjjsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCyyjjsJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findCyyjjsBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询不参与业绩计算表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBcyyjjsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBcyyjjsJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findBcyyjjsBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到添加指定客户
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPointCustAdd")
	public ModelAndView goPointCustAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		return new ModelAndView("admin/finance/perfCycle/perfCycle_pointCust_add").addObject("perfCycle", perfCycle);
	}
	/**
	 * 查是否计算业绩
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkIsCalcPerf")
	public List<Map<String, Object>> checkIsCalcPerf(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("查是否计算业绩");
		return perfCycleService.checkIsCalcPerf(perfCycle);
	}
	/**
	 * 跳转到新增业绩明细
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCountAdd")
	public ModelAndView goCountAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/finance/perfCycle/perfCycle_count_add").addObject("perfCycle", perfCycle);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goGxrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGxrJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		perfCycleService.findGxrBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goGxrxglsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goGxrxglsJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findGxrxglsBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJczjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJczjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findJczjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goClzjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goClzjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findClzjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHtfyzjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goHtfyzjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findHtfyzjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFkxxJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFkxxJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findFkxxBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增业绩干系人
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goGxrAdd")
	public ModelAndView goGxrAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		if("A".equals(perfCycle.getM_umState())){
			perfCycle.setIsCalcDeptPerf("1");
			perfCycle.setIsCalcPersonPerf("1");
			perfCycle.setIsCalcDeptPerfDescr("是");
			perfCycle.setIsCalcPersonPerfDescr("是");
		}
		return new ModelAndView("admin/finance/perfCycle/perfCycle_addGxr").addObject("perfCycle", perfCycle);
	}
	/**
	 * 跳转到新增基础增减
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJczjAdd")
	public ModelAndView goJczjAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		return new ModelAndView("admin/finance/perfCycle/perfCycle_addJczj").addObject("perfCycle", perfCycle);
	}
	/**
	 * 跳转到新增材料增减
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goClzjAdd")
	public ModelAndView goClzjAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		return new ModelAndView("admin/finance/perfCycle/perfCycle_addClzj").addObject("perfCycle", perfCycle);
	}
	/**
	 * 跳转到新增合同费用增减
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHtfyzjAdd")
	public ModelAndView goHtfyzjAdd(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		return new ModelAndView("admin/finance/perfCycle/perfCycle_addHtfyzj").addObject("perfCycle", perfCycle);
	}
	/**
	 * 根据客户号回调的信息
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInfoByCustCode")
	@ResponseBody
	public Map<String, Object> getInfoByCustCode(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		Map<String, Object> alreadyMaterPerfMap=perfCycleService.getAlreadyMaterPerf(perfCycle);
		Map<String, Object> payTypeMap=perfCycleService.getPayType(perfCycle);
		Map<String, Object> regRealMaterPerfMap=perfCycleService.getRegRealMaterPerf(perfCycle);
		Map<String, Object> sumChgRealMaterPerfMap=perfCycleService.getSumChgRealMaterPerf(perfCycle);
		Map<String, Object> isCalcBaseDiscMap=perfCycleService.getIsCalcBaseDisc(perfCycle);
		Map<String, Object> regAchieveDateMap=perfCycleService.getRegAchieveDate(perfCycle);
		alreadyMaterPerfMap.putAll(payTypeMap);
		alreadyMaterPerfMap.putAll(regRealMaterPerfMap);
		alreadyMaterPerfMap.putAll(sumChgRealMaterPerfMap);
		alreadyMaterPerfMap.putAll(isCalcBaseDiscMap);
		alreadyMaterPerfMap.putAll(regAchieveDateMap);
		return alreadyMaterPerfMap;
	}
	/**
	 * 跳转到导入原业绩页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goRegPerformance")
	public ModelAndView goRegPerformance(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		return new ModelAndView("admin/finance/perfCycle/perfCycle_importRegPerf").addObject("perfCycle", perfCycle);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goYyjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goYyjJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycleService.findYyjBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查导入原业绩数据
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRegImport")
	public List<Map<String, Object>> getRegImport(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("查导入原业绩数据");
		return perfCycleService.getRegImport(perfCycle);
	}
	/**
	 * 按业绩公式计算
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getExp")
	public List<Map<String, Object>> getExp(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("按业绩公式计算");
		return perfCycleService.getExp(perfCycle);
	}
	/**
	 * 跳转到查看原业绩
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewOldPerf")
	public ModelAndView goViewOldPerf(HttpServletRequest request,
			HttpServletResponse response,PerfCycle perfCycle) throws Exception {
		List<Map<String, Object>> list=perfCycleService.getRegPerformance(perfCycle);
		Map<String, Object> map=new HashMap<String, Object>();
		if(list!=null && list.size()>0){
			map=list.get(0);
		}
		DecimalFormat df = new DecimalFormat("##.####");  
		//遍历map，把太大或太小被转化成科学计数的数字改成正常显示
		for(String key:map.keySet()){
			if(!key.equals("perfExprRemarks") && !key.equals("perfExpr") && !key.equals("custDescr")
					&& !key.equals("address") && !key.equals("discRemark") && !key.equals("remarks")
					&& !key.equals("actionLog")){//过滤掉可能出现E的非数字字段
				try {
					if(map.get(key)!=null){
						if(map.get(key).toString().contains("E")){
							Double d = new Double(map.get(key).toString());
							String formated=df.format(d);
							if(formated.contains(".")){
								map.put(key, formated);
							}else{
								map.put(key, formated+".0");
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("m_umState", perfCycle.getM_umState());
		map.put("openCount", perfCycle.getOpenCount());
		return new ModelAndView("admin/finance/perfCycle/perfCycle_count_update").addObject("perfCycle", map);
	}
	/**
	 * 业绩计算新增已计算业绩保存
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doCountAdd")
	public void doCountAdd(HttpServletRequest request, HttpServletResponse response,PerfCycle perfCycle){
		logger.debug("业绩计算新增已计算业绩保存");
		try{
			perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = this.perfCycleService.doSaveProc(perfCycle);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加performance失败");
		}
	}
	/**
	 * 修改是否复核
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/changeIsCheck")
	public void changeIsCheck(HttpServletRequest request, HttpServletResponse response, PerfCycle perfCycle){
		logger.debug("修改是否复核");
		perfCycle.setLastUpdatedBy(getUserContext(request).getCzybh());
		this.perfCycleService.changeIsCheck(perfCycle);
	}
	/**
	 * 是否存在原业绩
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isExistRegPerfPk")
	public List<Map<String, Object>> isExistRegPerfPk(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("是否存在原业绩");
		return perfCycleService.isExistRegPerfPk(perfCycle);
	}
	/**
	 * 重签扣减业绩是否有对应的正常业绩/纯设计业绩
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isMatchedPerf")
	public List<Map<String, Object>> isMatchedPerf(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("重签扣减业绩是否有对应的正常业绩/纯设计业绩");
		return perfCycleService.isMatchedPerf(perfCycle);
	}
	/**
	 * 是否存在纯设计转施工生成的业绩
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isExistThisPerfPk")
	public List<Map<String, Object>> isExistThisPerfPk(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("是否存在纯设计转施工生成的业绩");
		return perfCycleService.isExistThisPerfPk(perfCycle);
	}
	/**
	 * 计算增减生成的基础单项扣减,材料单品扣减
	 * 
	 * @param request
	 * @param response
	 * @param perfCycle
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/calcChgDeduction")
	public Map<String, Object> calcChgDeduction(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) {
		logger.debug("计算增减生成的基础单项扣减,材料单品扣减");
		List<Map<String, Object>> list1=perfCycleService.calcBaseDeduction(perfCycle);
		List<Map<String, Object>> list2=perfCycleService.calcItemDeduction(perfCycle);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("BaseDeduction", list1.size()>0?list1.get(0).get("BaseDeduction"):0);
		map.put("ItemDeduction", list2.size()>0?list2.get(0).get("ItemDeduction"):0);
		return map;
	}
	
	/**
	 * 批量复核业绩
	 * @param request
	 * @param response
	 * @param ids 多个业绩明细pk
	 * @param isCheck 是否复核
	 */
	@RequestMapping("/doBatchChecked")
	public void doSendBatch(HttpServletRequest request, HttpServletResponse response, String ids,String isCheck){
		logger.debug("批量复核业绩开始");	
		if(StringUtils.isBlank(ids)){
			ServletUtils.outPrintFail(request, response, "业绩pk不能为空,复核失败!");
			return;
		};
		try {
			this.perfCycleService.doBatchChecked(ids, isCheck);
			ServletUtils.outPrintSuccess(request, response, "操作成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败!");
		}
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSignDataJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSignDataJqGrid(HttpServletRequest request,
			HttpServletResponse response, PerfCycle perfCycle) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		perfCycle.setFreeBaseItem(xtcsService.getQzById("FreeBaseItem"));
		perfCycleService.findSignDataJqGridBySql(page, perfCycle);
		return new WebPage<Map<String,Object>>(page);	
	}
	
	@RequestMapping("/doSignDataExcel")
	 public void doPrjExcel(HttpServletRequest request ,HttpServletResponse response,
			 PerfCycle perfCycle){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		perfCycle.setFreeBaseItem(xtcsService.getQzById("FreeBaseItem"));
		perfCycleService.findSignDataJqGridBySql(page, perfCycle);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单数据统计_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	 
	/**
	 * 获取基础增减单套外增项
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	@RequestMapping("/getBaseChgSetAdd")
	@ResponseBody
	public List<Map<String, Object>> getBaseChgSetAdd(HttpServletRequest request ,HttpServletResponse response,
			PerfCycle perfCycle ){
			return perfCycleService.getBaseChgSetAdd(perfCycle);

	}
	
	/**
	 * 获取主材增减单主材毛利率
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getChgMainProPer")
	@ResponseBody
	public List<Map<String, Object>> getChgMainProPer(HttpServletRequest request ,HttpServletResponse response,
			PerfCycle perfCycle ){
			return perfCycleService.getMainProPer_chg(perfCycle);

	}
	
	/**
	 * 获取基础增减单套外增项
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	@RequestMapping("/getBasePersonalPlan")
	@ResponseBody
	public List<Map<String, Object>> getBasePersonalPlan(HttpServletRequest request ,HttpServletResponse response,
			PerfCycle perfCycle ){
			return perfCycleService.getBasePersonalPlan(perfCycle);

	}
	
	

}
