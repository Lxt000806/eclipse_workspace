package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.entity.finance.IntPerf;
import com.house.home.entity.finance.MainCommi;
import com.house.home.service.finance.MainCommiService;

@Controller
@RequestMapping("/admin/mainCommi")
public class MainCommiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MainCommiController.class);

	@Autowired
	private MainCommiService mainCommiService;

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
			HttpServletResponse response, MainCommi mainCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiService.findPageBySql(page, mainCommi);
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
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/mainCommi/mainCommi_list");
	}
	/**
	 *获取统计周期编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,MainCommi mainCommi){
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_code").addObject("mainCommi",mainCommi);
	}
	
	/**
	 * 根据id查询intPerf详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getMainCommi")
	@ResponseBody
	public JSONObject getMainCommi(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		MainCommi mainCommi= mainCommiService.get(MainCommi.class, id);
		if(mainCommi == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(mainCommi, true);
	}
	@RequestMapping("/goCount")
	public ModelAndView goCountComplete(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_count").addObject("no",no);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String no ) throws Exception {
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_count").addObject("no",no).addObject("m_umState", "V");
	}

	/**
	 * 新增周期页面
	 * @param request
	 * @param response
	 * @param mainCommi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,MainCommi mainCommi) throws Exception {

		return new ModelAndView("admin/finance/mainCommi/mainCommi_addPeriod");
	}
	
	/**
	 * 编辑周期页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdatePeriod(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		MainCommi mainCommi=null;
		if(StringUtils.isNotBlank(no)){
			mainCommi= mainCommiService.get(MainCommi.class, no);
		}
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_updatePeriod")
		.addObject("mainCommi", mainCommi);
	}
	
	/**
	 *  新增统计周期
	 * @param request
	 * @param response
	 * @param mainCommi
	 */
	@RequestMapping("/doSavePeriod")
	public void doSavePeriod(HttpServletRequest request ,
			HttpServletResponse response ,MainCommi mainCommi){
		logger.debug("新增统计周期");
		try{
			mainCommi.setNo(mainCommiService.getSeqNo("tMainCommi"));
			mainCommi.setLastUpdate(new Date());
			mainCommi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			mainCommi.setExpired("F");
			mainCommi.setActionLog("ADD");
			
			this.mainCommiService.save(mainCommi);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/doUpdatePeriod")
	public void doUpdatePeriod(HttpServletRequest request ,
			HttpServletResponse response ,MainCommi mainCommi){
		logger.debug("新增统计周期");
		try{
			mainCommi.setLastUpdate(new Date());
			mainCommi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			mainCommi.setExpired("F");
			mainCommi.setActionLog("EDIT");
			
			this.mainCommiService.update(mainCommi);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	/**
	 * 主页面excel
	 * @param request
	 * @param response
	 * @param mainCommi
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			MainCommi mainCommi){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		mainCommiService.findPageBySql(page, mainCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
		return mainCommiService.checkStatus(no);
	}
	/**
	 * 退回
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCountBack")
	public void doSaveCountBack(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("退回");
		this.mainCommiService.doSaveCountBack(no);
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	/**
	 * 计算完成
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCountComplete")
	public void doSaveCount(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("计算完成");
		this.mainCommiService.doSaveCount(no);
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
		return mainCommiService.isExistsPeriod(no,beginDate);
	}
	/**
	 * 生成提成数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("生成提成数据");
		Map<String, Object>resultMap=this.mainCommiService.doCount(no, getUserContext(request).getCzybh());
		ServletUtils.outPrintSuccess(request, response,resultMap.get("errmsg").toString());
	}
	/**
	 * 查询goFdlJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFdlJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFdlJqGrid(HttpServletRequest request,
			HttpServletResponse response, MainCommi mainCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiService.goFdlJqGrid(page, mainCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goDlJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDlJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDlJqGrid(HttpServletRequest request,
			HttpServletResponse response, MainCommi mainCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiService.goDlJqGrid(page, mainCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查看非独立销售明细
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewFdl")
	public ModelAndView goViewFdl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/mainCommi/mainCommi_viewFdl")
				.addObject("map", jsonObject);
	}
	/**
	 * 查看独立销售明细
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewDl")
	public ModelAndView goViewDl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/mainCommi/mainCommi_viewDl")
				.addObject("map", jsonObject);
	}
	@RequestMapping("/doUpdateFdl")
	public void doUpdateFdl(HttpServletRequest request ,HttpServletResponse response,Integer pk,Double managercommi,Double mainbusimancommi,
			Double declaremancommi,Double checkmancommi,Double deptfundcommi,Double totalcommi){
		logger.debug("更新非独立销售明细");
		try{
			this.mainCommiService.doUpdateFdl(pk, managercommi, mainbusimancommi, declaremancommi, checkmancommi, deptfundcommi, totalcommi,getUserContext(request).getCzybh());
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "更新非独立销售明细失败");
		}
	}
	@RequestMapping("/doUpdateDl")
	public void doUpdateDl(HttpServletRequest request ,HttpServletResponse response,Integer pk,Double businessmancommi){
		logger.debug("更新独立销售明细");
		try{
			this.mainCommiService.doUpdateDl(pk, businessmancommi,getUserContext(request).getCzybh());
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "更新独立销售明细失败");
		}
	}
	/**
	 *获取统计周期编号
	 * 
	 **/
	@RequestMapping("/goReport")
	public ModelAndView goReport(HttpServletRequest  request ,
				HttpServletResponse response){
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_report");
	}
	/**
	 * 查询goFdlReportJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goFdlReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goFdlReportJqGrid(HttpServletRequest request,
			HttpServletResponse response, MainCommi mainCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiService.goFdlReportJqGrid(page, mainCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goDlReportJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDlReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDlReportJqGrid(HttpServletRequest request,
			HttpServletResponse response, MainCommi mainCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiService.goDlReportJqGrid(page, mainCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *跳转到提成比例设置
	 * 
	 **/
	@RequestMapping("/goCommiPerc")
	public ModelAndView goCommiPerc(HttpServletRequest  request,
				HttpServletResponse response){
		
		return new ModelAndView("admin/finance/mainCommi/mainCommi_commiPerc");
	}
	
}
