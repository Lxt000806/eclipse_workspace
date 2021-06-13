package com.house.home.web.controller.commi;

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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.commi.SoftBusiCommi;
import com.house.home.service.commi.SoftBusiCommiService;

@Controller
@RequestMapping("/admin/softBusiCommi")
public class SoftBusiCommiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SoftBusiCommiController.class);

	@Autowired
	private SoftBusiCommiService softBusiCommiService;

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
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.findPageBySql(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询基础数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBaseJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBaseJqGrid(HttpServletRequest request,
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.goBaseJqGrid(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询汇总数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSumJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSumJqGrid(HttpServletRequest request,
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.goSumJqGrid(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 历史提成
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHisJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goHisJqGrid(HttpServletRequest request,
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.goHisJqGrid(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 独立销售
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIndJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goIndJqGrid(HttpServletRequest request,
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.goIndJqGrid(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 干系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goStakeholderJqGrid(HttpServletRequest request,
			HttpServletResponse response, SoftBusiCommi softBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softBusiCommiService.goStakeholderJqGrid(page, softBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * SoftBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBase")
	public ModelAndView goBase(HttpServletRequest request,
			HttpServletResponse response,SoftBusiCommi softBusiCommi) throws Exception {
		
		softBusiCommi.setCostRight(getUserContext(request).getCostRight());
		
		return new ModelAndView("admin/commi/softBusiCommi/softBusiCommi_base")
				.addObject("softBusiCommi", softBusiCommi);
		
	}
	
	/**
	 * SoftBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSum")
	public ModelAndView goSum(HttpServletRequest request,
			HttpServletResponse response,SoftBusiCommi softBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/softBusiCommi/softBusiCommi_sum")
			.addObject("softBusiCommi", softBusiCommi);
	}
	
	/**
	 * SoftBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHis")
	public ModelAndView goHis(HttpServletRequest request,
			HttpServletResponse response,SoftBusiCommi softBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/softBusiCommi/softBusiCommi_his")
			.addObject("softBusiCommi", softBusiCommi);
	}
	
	/**
	 * SoftBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholder")
	public ModelAndView goStakeholder(HttpServletRequest request,
			HttpServletResponse response,SoftBusiCommi softBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/softBusiCommi/softBusiCommi_stakeholder")
			.addObject("softBusiCommi", softBusiCommi);
	}
	
	/**
	 *SoftBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftBusiCommi softBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softBusiCommiService.findPageBySql(page, softBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 *SoftBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSumExcel")
	public void doSumExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftBusiCommi softBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softBusiCommiService.goSumJqGrid(page, softBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装提成汇总查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *IntBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doBaseExcel")
	public void doBaseExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftBusiCommi softBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softBusiCommiService.goBaseJqGrid(page, softBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装提成材料明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *SoftBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doIndExcel")
	public void doIndExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftBusiCommi softBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softBusiCommiService.goIndJqGrid(page, softBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装独立销售提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
