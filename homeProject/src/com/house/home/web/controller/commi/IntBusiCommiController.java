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
import com.house.home.entity.commi.IntBusiCommi;
import com.house.home.service.commi.IntBusiCommiService;

@Controller
@RequestMapping("/admin/intBusiCommi")
public class IntBusiCommiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntBusiCommiController.class);

	@Autowired
	private IntBusiCommiService intBusiCommiService;

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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.findPageBySql(page, intBusiCommi);
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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.goBaseJqGrid(page, intBusiCommi);
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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.goSumJqGrid(page, intBusiCommi);
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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.goHisJqGrid(page, intBusiCommi);
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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.goIndJqGrid(page, intBusiCommi);
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
			HttpServletResponse response, IntBusiCommi intBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intBusiCommiService.goStakeholderJqGrid(page, intBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * IntBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBase")
	public ModelAndView goBase(HttpServletRequest request,
			HttpServletResponse response,IntBusiCommi intBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/intBusiCommi/intBusiCommi_base")
				.addObject("intBusiCommi", intBusiCommi);
		
	}
	
	/**
	 * IntBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSum")
	public ModelAndView goSum(HttpServletRequest request,
			HttpServletResponse response,IntBusiCommi intBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/intBusiCommi/intBusiCommi_sum")
			.addObject("intBusiCommi", intBusiCommi);
	}
	
	/**
	 * IntBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHis")
	public ModelAndView goHis(HttpServletRequest request,
			HttpServletResponse response,IntBusiCommi intBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/intBusiCommi/intBusiCommi_his")
			.addObject("intBusiCommi", intBusiCommi);
	}
	
	/**
	 * IntBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholder")
	public ModelAndView goStakeholder(HttpServletRequest request,
			HttpServletResponse response,IntBusiCommi intBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/intBusiCommi/intBusiCommi_stakeholder")
			.addObject("intBusiCommi", intBusiCommi);
	}
	
	/**
	 *IntBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntBusiCommi intBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intBusiCommiService.findPageBySql(page, intBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 *IntBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSumExcel")
	public void doSumExcel(HttpServletRequest request, 
			HttpServletResponse response, IntBusiCommi intBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intBusiCommiService.goSumJqGrid(page, intBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成提成汇总查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *IntBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doBaseExcel")
	public void doBaseExcel(HttpServletRequest request, 
			HttpServletResponse response, IntBusiCommi intBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intBusiCommiService.goBaseJqGrid(page, intBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成提成材料明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *IntBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doIndExcel")
	public void doIndExcel(HttpServletRequest request, 
			HttpServletResponse response, IntBusiCommi intBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intBusiCommiService.goIndJqGrid(page, intBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成独立销售提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
