package com.house.home.web.controller.commi;

import java.util.Date;
import java.util.List;
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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.entity.commi.MainBusiCommi;
import com.house.home.entity.design.Customer;
import com.house.home.service.commi.CommiCustStakeholderService;

@Controller
@RequestMapping("/admin/commiCustStakeholder")
public class CommiCustStakeholderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCustStakeholderController.class);

	@Autowired
	private CommiCustStakeholderService commiCustStakeholderService;

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
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderService.findPageBySql(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转到查看CommiCustStakeholder页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看CommiCustStakeholder页面");
		CommiCustStakeholder commiCustStakeholder = commiCustStakeholderService.get(CommiCustStakeholder.class, pk);
		if(StringUtils.isNotBlank(commiCustStakeholder.getRole())){
			Roll roll = commiCustStakeholderService.get(Roll.class, commiCustStakeholder.getRole());
			commiCustStakeholder.setRoleDescr(roll.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholder.getCustCode())){
			Customer customer = commiCustStakeholderService.get(Customer.class, commiCustStakeholder.getCustCode());
			commiCustStakeholder.setAddress(customer.getAddress());
		}
		if(StringUtils.isNotBlank(commiCustStakeholder.getEmpCode())){
			Employee employee = commiCustStakeholderService.get(Employee.class, commiCustStakeholder.getEmpCode());
			commiCustStakeholder.setEmpName(employee.getNameChi());
		}
		commiCustStakeholder.setM_umState("V");
		return new ModelAndView("admin/commi/commiCustStakeholder/commiCustStakeholder_view")
				.addObject("commiCustStakeholder", commiCustStakeholder);
	}
	
	/**
	 * 跳转到查看基础个性化提成干系人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholder")
	public ModelAndView goStakeholder(HttpServletRequest request,
			HttpServletResponse response,CommiCustStakeholder commiCustStakeholder) throws Exception {

		return new ModelAndView("admin/commi/commiCustStakeholder/commiCustStakeholder_stakeholder")
			.addObject("commiCustStakeholder", commiCustStakeholder);
	}

	/**
	 *CommiCustStakeholder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustStakeholderService.findPageBySql(page, commiCustStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业绩提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	/**
	 * 跳转到调整金额页面
	 * @return
	 */
	@RequestMapping("/goAdjAmount")
	public ModelAndView goAdjAmount(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到调整金额页面");
		CommiCustStakeholder commiCustStakeholder = commiCustStakeholderService.get(CommiCustStakeholder.class, pk);
		return new ModelAndView("admin/commi/commiCycle/tab_yjtc_adj")
			.addObject("commiCustStakeholder", commiCustStakeholder);
	}
	
	/**
	 * 调整金额
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doAdjustAmount")
	public void doAdjustAmount(HttpServletRequest request, HttpServletResponse response, CommiCustStakeholder ccs){
		logger.debug("调整金额开始");
		try{
			CommiCustStakeholder commiCustStakeholder = commiCustStakeholderService.get(CommiCustStakeholder.class, ccs.getPk());
			commiCustStakeholder.setAdjustAmount(ccs.getAdjustAmount());
			commiCustStakeholder.setAdjustRemarks(ccs.getAdjustRemarks());
			commiCustStakeholderService.adjustAmount(commiCustStakeholder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "调整金额失败");
		}
	}
	
	/**
	 * 查询goDesignFeeJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDesignFeeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDesignFeeJqGrid(HttpServletRequest request,
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderService.goDesignFeeJqGrid(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *CommiCustStakeholder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDesignFeeExcel")
	public void doDesignFeeExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustStakeholderService.goDesignFeeJqGrid(page, commiCustStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"设计费提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到调整金额页面
	 * @return
	 */
	@RequestMapping("/goViewHisCommi")
	public ModelAndView goViewHisCommi(HttpServletRequest request, HttpServletResponse response, 
			Integer pk,Integer mon){
		logger.debug("跳转到查看历史提成页面");
		CommiCustStakeholder commiCustStakeholder = commiCustStakeholderService.get(CommiCustStakeholder.class, pk);
		commiCustStakeholder.setMon(mon);
		return new ModelAndView("admin/commi/commiCycle/tab_yjtc_his")
			.addObject("commiCustStakeholder", commiCustStakeholder);
	}
	
	/**
	 * 查询goHisJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goHisJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goHisJqGrid(HttpServletRequest request,
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderService.goHisJqGrid(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询goBasePersonalJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBasePersonalJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goBasePersonalJqGrid(HttpServletRequest request,
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderService.goBasePersonalJqGrid(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 基础个性化提成干系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goStakeholderJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goStakeholderJqGrid(HttpServletRequest request,
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderService.goStakeholderJqGrid(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *CommiCustStakeholder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doBasePersonalExcel")
	public void doBasePersonalExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustStakeholderService.goBasePersonalJqGrid(page, commiCustStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础个性化提成_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
