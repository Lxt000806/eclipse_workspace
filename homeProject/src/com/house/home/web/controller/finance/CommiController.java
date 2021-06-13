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
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.Commi;
import com.house.home.service.finance.CommiService;
@Controller
@RequestMapping("/admin/commi")
public class CommiController extends BaseController{
	
	@Autowired
	private CommiService commiService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		commiService.findPageBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJListqGrid(HttpServletRequest request,
			HttpServletResponse response,Commi commi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//commiService.findListPageBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *获取统计周期编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Commi commi){
		
		return new ModelAndView("admin/finance/commi/commi_code").addObject("commi",commi);
	}
	
	/**
	 * 根据id查询commi详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getCommi")
	@ResponseBody
	public JSONObject getCommi(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Commi commi= commiService.get(Commi.class, id);
		if(commi == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(commi, true);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param commi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportJqGrid(HttpServletRequest request,
			HttpServletResponse response,Commi commi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//commiService.findReportPageBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goReportDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Commi commi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//commiService.findReportDetailPageBySql(page, commi);
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

		return new ModelAndView("admin/finance/commi/commi_list");
	}
	
	@RequestMapping("/goCount")
	public ModelAndView goCountComplete(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		return new ModelAndView("admin/finance/commi/commi_count").addObject("no",no);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String no ) throws Exception {
		
		return new ModelAndView("admin/finance/commi/commi_count").addObject("no",no).addObject("m_umState", "V");
	}

	/**
	 * 新增周期页面
	 * @param request
	 * @param response
	 * @param commi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,Commi commi) throws Exception {

		return new ModelAndView("admin/finance/commi/commi_addPeriod");
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
		Commi commi=null;
		if(StringUtils.isNotBlank(no)){
			commi= commiService.get(Commi.class, no);
		}
		
		return new ModelAndView("admin/finance/commi/commi_updatePeriod")
		.addObject("commi", commi);
	}
	
	/**
	 *  新增统计周期
	 * @param request
	 * @param response
	 * @param commi
	 */
	@RequestMapping("/doSavePeriod")
	public void doSavePeriod(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi){
		logger.debug("新增统计周期");
		try{
			commi.setNo(	commiService.getSeqNo("tCommi"));
			commi.setLastUpdate(new Date());
			commi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			commi.setExpired("F");
			commi.setActionLog("ADD");
			
			this.commiService.save(commi);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/doUpdatePeriod")
	public void doUpdatePeriod(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi){
		logger.debug("新增统计周期");
		try{
			commi.setLastUpdate(new Date());
			commi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			commi.setExpired("F");
			commi.setActionLog("EDIT");
			
			this.commiService.update(commi);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	/**
	 * 主页面excel
	 * @param request
	 * @param response
	 * @param commi
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Commi commi){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		commiService.findPageBySql(page, commi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"设计师主材提成统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
		return commiService.checkStatus(no);
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
		this.commiService.doSaveCountBack(no);
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
		this.commiService.doSaveCount(no);
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
		return commiService.isExistsPeriod(no,beginDate);
	}
	/**
	 * 主材提成客户列表
	 * @param request
	 * @param response
	 * @param commi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getCustJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		commiService.findCustBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 主材提成材料列表
	 * @param request
	 * @param response
	 * @param commi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getItemJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		commiService.findItemBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 主材提成材料excel
	 * @param request
	 * @param response
	 * @param commi
	 */
	@RequestMapping("/doCustExcel")
	public void doCustExcel(HttpServletRequest request ,HttpServletResponse response,
			Commi commi){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		commiService.findCustBySql(page, commi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成客户明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 主材提成材料excel
	 * @param request
	 * @param response
	 * @param commi
	 */
	@RequestMapping("/doItemExcel")
	public void doItemExcel(HttpServletRequest request ,HttpServletResponse response,
			Commi commi){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		commiService.findItemBySql(page, commi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成材料明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 生成提成数据
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,String no,String isRegenCommiPerc){
		logger.debug("生成提成数据");
		Map<String, Object>resultMap=this.commiService.doCount(no, getUserContext(request).getCzybh() ,isRegenCommiPerc);
		ServletUtils.outPrintSuccess(request, response,resultMap.get("errmsg").toString());
	}
	@RequestMapping("/goViewCust")
	public ModelAndView goViewCust(HttpServletRequest request,
			HttpServletResponse response,String pk ) throws Exception {
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		Map<String, Object> map=commiService.findCustMap(page,pk);
		return new ModelAndView("admin/finance/commi/commi_viewCust").addObject("map",map);
	}
	@RequestMapping("/goViewItem")
	public ModelAndView goViewItem(HttpServletRequest request,
			HttpServletResponse response,String pk ) throws Exception {
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		Map<String, Object> map=commiService.findItemMap(page,pk);
		return new ModelAndView("admin/finance/commi/commi_viewItem").addObject("map",map);
	}
	@RequestMapping("/goUpdateCommiPerc")
	public ModelAndView goUpdateCommiPerc(HttpServletRequest request,HttpServletResponse response,
			Commi commi){
		logger.debug("跳转到批量修改页面");
		
		return new ModelAndView("admin/finance/commi/commi_updateCommiPerc")
					.addObject("commi",commi);
	}
	/**
	 * 主材需求
	 * @param request
	 * @param response
	 * @param commi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemReqJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getItemReqJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Commi commi) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		commiService.findItemReqBySql(page, commi);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到批量修改值界面
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goUpdateValue")
	public ModelAndView goUpdateValue(HttpServletRequest request, HttpServletResponse response,Commi commi){
		return new ModelAndView("admin/finance/commi/commi_valueUpdate").addObject("commi",commi);
	}
	/**
	 * 批量修改
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doUpdateBatch")
	public void doUpdateBatch(HttpServletRequest request, HttpServletResponse response,
			Commi commi){
		logger.debug("批量修改开始");
		try{
			commi.setLastUpdate(new Date());
			commi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			commiService.doUpdateBatch(commi);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 导出Excel
	 * @author	created by zb
	 * @date	2019-11-11--下午4:21:04
	 * @param request
	 * @param response
	 * @param custService
	 */
	@RequestMapping("/doItemReqExcel")
	public void doItemReqExcel(HttpServletRequest request, 
			HttpServletResponse response,Commi commi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiService.findItemReqBySql(page, commi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"修改提成比例_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
