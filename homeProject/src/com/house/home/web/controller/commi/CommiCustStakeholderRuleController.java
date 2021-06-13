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
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.commi.CommiCustStakeholderRule;
import com.house.home.entity.commi.CommiCustStakeholderRule;
import com.house.home.entity.design.Customer;
import com.house.home.entity.workflow.Department;
import com.house.home.service.commi.CommiCustStakeholderRuleService;

@Controller
@RequestMapping("/admin/commiCustStakeholderRule")
public class CommiCustStakeholderRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiCustStakeholderRuleController.class);

	@Autowired
	private CommiCustStakeholderRuleService commiCustStakeholderRuleService;

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
			HttpServletResponse response, CommiCustStakeholderRule commiCustStakeholderRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderRuleService.findPageBySql(page, commiCustStakeholderRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goRuleJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goRuleJqGrid(HttpServletRequest request,
			HttpServletResponse response, CommiCustStakeholderRule commiCustStakeholderRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiCustStakeholderRuleService.goRuleJqGrid(page, commiCustStakeholderRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * CommiCustStakeholderRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_list");
	}
	/**
	 * CommiCustStakeholderRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_code");
	}
	/**
	 * 跳转到新增CommiCustStakeholderRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增CommiCustStakeholderRule页面");
		CommiCustStakeholderRule commiCustStakeholderRule = null;
		if (StringUtils.isNotBlank(id)){
			commiCustStakeholderRule = commiCustStakeholderRuleService.get(CommiCustStakeholderRule.class, Integer.parseInt(id));
			commiCustStakeholderRule.setPk(null);
		}else{
			commiCustStakeholderRule = new CommiCustStakeholderRule();
		}
		commiCustStakeholderRule.setM_umState("A");
		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_save")
			.addObject("commiCustStakeholderRule", commiCustStakeholderRule);
	}
	/**
	 * 跳转到修改CommiCustStakeholderRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改CommiCustStakeholderRule页面");
		
		CommiCustStakeholderRule commiCustStakeholderRule = commiCustStakeholderRuleService.get(CommiCustStakeholderRule.class, pk);
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getEmpCode())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getEmpCode());
			commiCustStakeholderRule.setEmpName(employee.getNameChi());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getOldStakeholder())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getOldStakeholder());
			if (employee != null) {
				commiCustStakeholderRule.setOldStakeholderDescr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getOldStakeholder())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getOldStakeholder2());
			if (employee != null) {
				commiCustStakeholderRule.setOldStakeholder2Descr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getRole())){
			Roll roll = commiCustStakeholderRuleService.get(Roll.class, commiCustStakeholderRule.getRole());
			commiCustStakeholderRule.setRoleDescr(roll.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getCustCode())){
			Customer customer = commiCustStakeholderRuleService.get(Customer.class, commiCustStakeholderRule.getCustCode());
			commiCustStakeholderRule.setCustDescr(customer.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment1())){
			Department1 department1 = commiCustStakeholderRuleService.get(Department1.class, commiCustStakeholderRule.getDepartment1());
			commiCustStakeholderRule.setDepartment1Descr(department1.getDesc2());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment2())){
			Department2 department2 = commiCustStakeholderRuleService.get(Department2.class, commiCustStakeholderRule.getDepartment2());
			commiCustStakeholderRule.setDepartment2Descr(department2.getDesc2());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment3())){
			Department3 department3 = commiCustStakeholderRuleService.get(Department3.class, commiCustStakeholderRule.getDepartment3());
			commiCustStakeholderRule.setDepartment3Descr(department3.getDesc2());
		}
		
		commiCustStakeholderRule.setM_umState("M");
		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_save")
			.addObject("commiCustStakeholderRule", commiCustStakeholderRule);
	}
	
	/**
	 * 跳转到查看CommiCustStakeholderRule页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看CommiCustStakeholderRule页面");
		
		CommiCustStakeholderRule commiCustStakeholderRule = commiCustStakeholderRuleService.get(CommiCustStakeholderRule.class, pk);
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getEmpCode())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getEmpCode());
			commiCustStakeholderRule.setEmpName(employee.getNameChi());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getOldStakeholder())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getOldStakeholder());
			if (employee != null) {
				commiCustStakeholderRule.setOldStakeholderDescr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getOldStakeholder())){
			Employee employee = 
					commiCustStakeholderRuleService.get(Employee.class, commiCustStakeholderRule.getOldStakeholder2());
			if (employee != null) {
				commiCustStakeholderRule.setOldStakeholder2Descr(employee.getNameChi());
			}
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getRole())){
			Roll roll = commiCustStakeholderRuleService.get(Roll.class, commiCustStakeholderRule.getRole());
			commiCustStakeholderRule.setRoleDescr(roll.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getCustCode())){
			Customer customer = commiCustStakeholderRuleService.get(Customer.class, commiCustStakeholderRule.getCustCode());
			commiCustStakeholderRule.setCustDescr(customer.getDescr());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment1())){
			Department1 department1 = commiCustStakeholderRuleService.get(Department1.class, commiCustStakeholderRule.getDepartment1());
			commiCustStakeholderRule.setDepartment1Descr(department1.getDesc2());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment2())){
			Department2 department2 = commiCustStakeholderRuleService.get(Department2.class, commiCustStakeholderRule.getDepartment2());
			commiCustStakeholderRule.setDepartment2Descr(department2.getDesc2());
		}
		if(StringUtils.isNotBlank(commiCustStakeholderRule.getDepartment3())){
			Department3 department3 = commiCustStakeholderRuleService.get(Department3.class, commiCustStakeholderRule.getDepartment3());
			commiCustStakeholderRule.setDepartment3Descr(department3.getDesc2());
		}
		
		commiCustStakeholderRule.setM_umState("V");
		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_save")
				.addObject("commiCustStakeholderRule", commiCustStakeholderRule);
	}
	
	/**
	 * 跳转到查看CommiCustStakeholderRule页面
	 * @return
	 */
	@RequestMapping("/goViewRule")
	public ModelAndView goViewRule(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看CommiCustStakeholderRule页面");
		
		CommiCustStakeholderRule commiCustStakeholderRule = commiCustStakeholderRuleService.get(CommiCustStakeholderRule.class, pk);
		
		return new ModelAndView("admin/commi/commiCustStakeholderRule/commiCustStakeholderRule_rule")
				.addObject("commiCustStakeholderRule", commiCustStakeholderRule);
	}
	
	/**
	 * 添加CommiCustStakeholderRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiCustStakeholderRule commiCustStakeholderRule){
		logger.debug("添加CommiCustStakeholderRule开始");
		try{
			commiCustStakeholderRule.setExpired("F");
			commiCustStakeholderRule.setLastUpdate(new Date());
			commiCustStakeholderRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustStakeholderRule.setActionLog("EDIT");
			this.commiCustStakeholderRuleService.save(commiCustStakeholderRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiCustStakeholderRule失败");
		}
	}
	
	/**
	 * 修改CommiCustStakeholderRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiCustStakeholderRule commiCustStakeholderRule){
		logger.debug("修改CommiCustStakeholderRule开始");
		try{
			commiCustStakeholderRule.setExpired("F");
			commiCustStakeholderRule.setLastUpdate(new Date());
			commiCustStakeholderRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiCustStakeholderRule.setActionLog("EDIT");
			this.commiCustStakeholderRuleService.update(commiCustStakeholderRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CommiCustStakeholderRule失败");
		}
	}
	
	/**
	 * 删除CommiCustStakeholderRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiCustStakeholderRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiCustStakeholderRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiCustStakeholderRule commiCustStakeholderRule = commiCustStakeholderRuleService.get(CommiCustStakeholderRule.class, Integer.parseInt(deleteId));
				if(commiCustStakeholderRule == null)
					continue;
				commiCustStakeholderRuleService.delete(commiCustStakeholderRule);
			}
		}
		logger.debug("删除CommiCustStakeholderRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiCustStakeholderRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholderRule commiCustStakeholderRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiCustStakeholderRuleService.findPageBySql(page, commiCustStakeholderRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiCustStakeholderRule_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
