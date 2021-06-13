package com.house.home.web.controller.finance;

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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.IntPerf;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.finance.IntPerfService;
@Controller
@RequestMapping("/admin/intPerf")
public class IntPerfController extends BaseController{
	
	@Autowired
	private IntPerfService intPerfService;
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,IntPerf intPerf) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		intPerfService.findPageBySql(page, intPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJListqGrid(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//intPerfService.findListPageBySql(page, intPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *获取统计周期编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,IntPerf intPerf){
		
		return new ModelAndView("admin/finance/intPerf/intPerf_code").addObject("intPerf",intPerf);
	}
	
	/**
	 * 根据id查询intPerf详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getIntPerf")
	@ResponseBody
	public JSONObject getIntPerf(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		IntPerf intPerf= intPerfService.get(IntPerf.class, id);
		if(intPerf == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(intPerf, true);
	}
	
	@RequestMapping("/goCountIntPerJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCountIntPerJqGrid(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//intPerfService.findCountSoftPerPageBySql(page, intPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmpInfoJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getEmpInfoJqGrid(HttpServletRequest request,
			HttpServletResponse response,Employee employee) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//intPerfService.findEmpInfoPageBySql(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param intPerf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportJqGrid(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//intPerfService.findReportPageBySql(page, intPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goReportDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//intPerfService.findReportDetailPageBySql(page, intPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {

		return new ModelAndView("admin/finance/intPerf/intPerf_list");
	}
	
	@RequestMapping("/goCountComplete")
	public ModelAndView goCountComplete(HttpServletRequest request,
			HttpServletResponse response,String no,String beginDate,String endDate,String prjPerfNo ) throws Exception {
		
		return new ModelAndView("admin/finance/intPerf/intPerf_count")
			.addObject("no",no)
			.addObject("beginDate", beginDate)
			.addObject("endDate", endDate)
			.addObject("prjPerfNo",prjPerfNo);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String no,String endDate) throws Exception {
		
		return new ModelAndView("admin/finance/intPerf/intPerf_count").addObject("no",no).addObject("endDate",endDate).addObject("view", "view");
	}

	/**
	 * 报表
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReport")
	public ModelAndView goReport(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {

		return new ModelAndView("admin/finance/intPerf/intPerf_report").addObject("intPerf",intPerf);
	}
	
	@RequestMapping("/goViewReport")
	public ModelAndView goViewReport(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {

		return new ModelAndView("admin/finance/intPerf/intPerf_viewReport")
		.addObject("intPerf", intPerf);
	}
	
	/**
	 * 新增周期页面
	 * @param request
	 * @param response
	 * @param intPerf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,IntPerf intPerf) throws Exception {

		return new ModelAndView("admin/finance/intPerf/intPerf_addPeriod");
	}
	
	/**
	 * 编辑周期页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdatePeriod")
	public ModelAndView goUpdatePeriod(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		IntPerf intPerf=null;
		if(StringUtils.isNotBlank(no)){
			intPerf= intPerfService.get(IntPerf.class, no);
		}
		
		return new ModelAndView("admin/finance/intPerf/intPerf_updatePeriod")
		.addObject("intPerf", intPerf);
	}
	
	/**
	 *  新增统计周期
	 * @param request
	 * @param response
	 * @param intPerf
	 */
	@RequestMapping("/doSavePeriod")
	public void doSavePeriod(HttpServletRequest request ,
			HttpServletResponse response ,IntPerf intPerf){
		logger.debug("新增统计周期");
		try{
			intPerf.setNo(	intPerfService.getSeqNo("tIntPerf"));
			intPerf.setLastUpdate(new Date());
			intPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			intPerf.setExpired("F");
			intPerf.setActionLog("ADD");
			
			this.intPerfService.save(intPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/doUpdatePeriod")
	public void doUpdatePeriod(HttpServletRequest request ,
			HttpServletResponse response ,IntPerf intPerf){
		logger.debug("新增统计周期");
		try{
			intPerf.setLastUpdate(new Date());
			intPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			intPerf.setExpired("F");
			intPerf.setActionLog("EDIT");
			
			this.intPerfService.update(intPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	/**
	 * 主页面excel
	 * @param request
	 * @param response
	 * @param intPerf
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			IntPerf intPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		intPerfService.findPageBySql(page, intPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成业绩统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查状态
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 */
	@RequestMapping("/checkStatus")
	@ResponseBody
	public String checkStatus(HttpServletRequest request,HttpServletResponse response,
			String no){
		return intPerfService.checkStatus(no);
	}
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doSaveCountBack")
	public void doSaveCountBack(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("退回");
		this.intPerfService.doSaveCountBack(no);
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	/**
	 * 计算完成
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doSaveCount")
	public void doSaveCount(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("计算完成");
		this.intPerfService.doSaveCount(no);
		ServletUtils.outPrintSuccess(request, response,"计算完成");
	}
	/**
	 * 检查是否能计算周期
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
		return intPerfService.isExistsPeriod(no,beginDate);
	}
	/**
	 * 明细查询
	 * @author cjg
	 * @date 2019-12-12
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/finance/intPerf/intPerf_detailQuery");
	}
}
