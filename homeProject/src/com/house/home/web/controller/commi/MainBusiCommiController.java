package com.house.home.web.controller.commi;

import java.util.Date;
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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.commi.IntBusiCommi;
import com.house.home.entity.commi.MainBusiCommi;
import com.house.home.service.commi.MainBusiCommiService;

@Controller
@RequestMapping("/admin/mainBusiCommi")
public class MainBusiCommiController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MainBusiCommiController.class);

	@Autowired
	private MainBusiCommiService mainBusiCommiService;

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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.findPageBySql(page, mainBusiCommi);
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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.goBaseJqGrid(page, mainBusiCommi);
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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.goSumJqGrid(page, mainBusiCommi);
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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.goHisJqGrid(page, mainBusiCommi);
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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.goIndJqGrid(page, mainBusiCommi);
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
			HttpServletResponse response, MainBusiCommi mainBusiCommi) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainBusiCommiService.goStakeholderJqGrid(page, mainBusiCommi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * MainBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBase")
	public ModelAndView goBase(HttpServletRequest request,
			HttpServletResponse response,MainBusiCommi mainBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/mainBusiCommi/mainBusiCommi_base")
				.addObject("mainBusiCommi", mainBusiCommi);
		
	}
	
	/**
	 * MainBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSum")
	public ModelAndView goSum(HttpServletRequest request,
			HttpServletResponse response,MainBusiCommi mainBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/mainBusiCommi/mainBusiCommi_sum")
			.addObject("mainBusiCommi", mainBusiCommi);
	}
	
	/**
	 * MainBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHis")
	public ModelAndView goHis(HttpServletRequest request,
			HttpServletResponse response,MainBusiCommi mainBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/mainBusiCommi/mainBusiCommi_his")
			.addObject("mainBusiCommi", mainBusiCommi);
	}
	
	/**
	 * MainBusiCommi列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholder")
	public ModelAndView goStakeholder(HttpServletRequest request,
			HttpServletResponse response,MainBusiCommi mainBusiCommi) throws Exception {

		return new ModelAndView("admin/commi/mainBusiCommi/mainBusiCommi_stakeholder")
			.addObject("mainBusiCommi", mainBusiCommi);
	}
	
	/**
	 *MainBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, MainBusiCommi mainBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainBusiCommiService.findPageBySql(page, mainBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 *MainBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doSumExcel")
	public void doSumExcel(HttpServletRequest request, 
			HttpServletResponse response, MainBusiCommi mainBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainBusiCommiService.goSumJqGrid(page, mainBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成汇总查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *MainBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doBaseExcel")
	public void doBaseExcel(HttpServletRequest request, 
			HttpServletResponse response, MainBusiCommi mainBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainBusiCommiService.goBaseJqGrid(page, mainBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材提成材料明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *MainBusiCommi导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doIndExcel")
	public void doIndExcel(HttpServletRequest request, 
			HttpServletResponse response, MainBusiCommi mainBusiCommi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainBusiCommiService.goIndJqGrid(page, mainBusiCommi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材独立销售提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
