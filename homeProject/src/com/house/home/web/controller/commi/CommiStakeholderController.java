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
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.commi.CommiStakeholder;
import com.house.home.service.commi.CommiStakeholderService;

@Controller
@RequestMapping("/admin/commiStakeholder")
public class CommiStakeholderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiStakeholderController.class);

	@Autowired
	private CommiStakeholderService commiStakeholderService;

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
			HttpServletResponse response, CommiStakeholder commiStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiStakeholderService.findPageBySql(page, commiStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CommiStakeholder列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiStakeholder/commiStakeholder_list");
	}
	/**
	 * CommiStakeholder查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiStakeholder/commiStakeholder_code");
	}
	/**
	 * 跳转到新增CommiStakeholder页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			CommiStakeholder commiStakeholder){
		logger.debug("跳转到新增CommiStakeholder页面");
		commiStakeholder.setM_umState("A");
		return new ModelAndView("admin/commi/commiStakeholder/commiStakeholder_save")
			.addObject("commiStakeholder", commiStakeholder);
	}
	/**
	 * 跳转到修改CommiStakeholder页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk,String commiNo){
		logger.debug("跳转到修改CommiStakeholder页面");
		CommiStakeholder commiStakeholder = commiStakeholderService.get(CommiStakeholder.class, pk);
		if(StringUtils.isNotBlank(commiStakeholder.getEmpCode())){
			Employee employee = 
					commiStakeholderService.get(Employee.class, commiStakeholder.getEmpCode());
			commiStakeholder.setEmpName(employee.getNameChi());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getRole())){
			Roll roll = commiStakeholderService.get(Roll.class, commiStakeholder.getRole());
			commiStakeholder.setRoleDescr(roll.getDescr());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment1())){
			Department1 department1 = commiStakeholderService.get(Department1.class, commiStakeholder.getDepartment1());
			commiStakeholder.setDepartment1Descr(department1.getDesc2());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment2())){
			Department2 department2 = commiStakeholderService.get(Department2.class, commiStakeholder.getDepartment2());
			commiStakeholder.setDepartment2Descr(department2.getDesc2());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment3())){
			Department3 department3 = commiStakeholderService.get(Department3.class, commiStakeholder.getDepartment3());
			commiStakeholder.setDepartment3Descr(department3.getDesc2());
		}
		
		commiStakeholder.setM_umState("M");
		commiStakeholder.setCommiNo(commiNo);
		return new ModelAndView("admin/commi/commiStakeholder/commiStakeholder_save")
			.addObject("commiStakeholder", commiStakeholder);
	}
	
	/**
	 * 跳转到查看CommiStakeholder页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk,String commiNo){
		logger.debug("跳转到查看CommiStakeholder页面");
		CommiStakeholder commiStakeholder = commiStakeholderService.get(CommiStakeholder.class, pk);
		if(StringUtils.isNotBlank(commiStakeholder.getEmpCode())){
			Employee employee = 
					commiStakeholderService.get(Employee.class, commiStakeholder.getEmpCode());
			commiStakeholder.setEmpName(employee.getNameChi());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getRole())){
			Roll roll = commiStakeholderService.get(Roll.class, commiStakeholder.getRole());
			commiStakeholder.setRoleDescr(roll.getDescr());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment1())){
			Department1 department1 = commiStakeholderService.get(Department1.class, commiStakeholder.getDepartment1());
			commiStakeholder.setDepartment1Descr(department1.getDesc2());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment2())){
			Department2 department2 = commiStakeholderService.get(Department2.class, commiStakeholder.getDepartment2());
			commiStakeholder.setDepartment2Descr(department2.getDesc2());
		}
		
		if(StringUtils.isNotBlank(commiStakeholder.getDepartment3())){
			Department3 department3 = commiStakeholderService.get(Department3.class, commiStakeholder.getDepartment3());
			commiStakeholder.setDepartment3Descr(department3.getDesc2());
		}
		
		commiStakeholder.setM_umState("V");
		commiStakeholder.setCommiNo(commiNo);
		return new ModelAndView("admin/commi/commiStakeholder/commiStakeholder_save")
				.addObject("commiStakeholder", commiStakeholder);
	}
	/**
	 * 添加CommiStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiStakeholder commiStakeholder){
		logger.debug("添加CommiStakeholder开始");
		try{
			commiStakeholder.setExpired("F");
			commiStakeholder.setLastUpdate(new Date());
			commiStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiStakeholder.setActionLog("EDIT");
			this.commiStakeholderService.save(commiStakeholder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加CommiStakeholder失败");
		}
	}
	
	/**
	 * 修改CommiStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiStakeholder commiStakeholder){
		logger.debug("修改CommiStakeholder开始");
		try{
			commiStakeholder.setExpired("F");
			commiStakeholder.setLastUpdate(new Date());
			commiStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiStakeholder.setActionLog("EDIT");
			this.commiStakeholderService.update(commiStakeholder);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改CommiStakeholder失败");
		}
	}
	
	/**
	 * 删除CommiStakeholder
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CommiStakeholder开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CommiStakeholder编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CommiStakeholder commiStakeholder = commiStakeholderService.get(CommiStakeholder.class, Integer.parseInt(deleteId));
				if(commiStakeholder == null)
					continue;
				commiStakeholderService.delete(commiStakeholder);
			}
		}
		logger.debug("删除CommiStakeholder IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiStakeholder导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiStakeholder commiStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiStakeholderService.findPageBySql(page, commiStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiStakeholder_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
