package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.Department1;
import com.house.home.entity.salary.EmpAdvanceWage;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryInd;
import com.house.home.service.salary.SalaryAdvanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/salaryAdvance")
public class SalaryAdvanceController extends BaseController{
	@Autowired
	private  SalaryAdvanceService salaryAdvanceService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, EmpAdvanceWage empAdvanceWage) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryAdvanceService.findPageBySql(page, empAdvanceWage);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,EmpAdvanceWage empAdvanceWage){
		
		return new ModelAndView("admin/salary/salaryAdvance/salaryAdvance_list")
		.addObject("empAdvanceWage", empAdvanceWage);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response,EmpAdvanceWage empAdvanceWage){
		
		return new ModelAndView("admin/salary/salaryAdvance/salaryAdvance_save")
		.addObject("empAdvanceWage", empAdvanceWage);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,EmpAdvanceWage empAdvanceWage){
		SalaryEmp salaryEmp = new SalaryEmp();
		Department1 department1 = new Department1();
		
		if(StringUtils.isNotBlank(empAdvanceWage.getSalaryEmp())){
			salaryEmp = salaryAdvanceService.get(SalaryEmp.class, empAdvanceWage.getSalaryEmp());

			empAdvanceWage = salaryAdvanceService.get(EmpAdvanceWage.class, empAdvanceWage.getSalaryEmp());
			if (empAdvanceWage != null && salaryEmp != null) {
				department1 = salaryAdvanceService.get(Department1.class, salaryEmp.getDepartment1());
				
				empAdvanceWage.setEmpName(salaryEmp.getEmpName());
				if(department1 != null){
					empAdvanceWage.setDepartment1(department1.getCode());
					empAdvanceWage.setDept1Descr(department1.getDesc1());
				}
			}
		}
		
		return new ModelAndView("admin/salary/salaryAdvance/salaryAdvance_update")
		.addObject("empAdvanceWage", empAdvanceWage);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,EmpAdvanceWage empAdvanceWage){
		
		SalaryEmp salaryEmp = new SalaryEmp();
		Department1 department1 = new Department1();
		
		if(StringUtils.isNotBlank(empAdvanceWage.getSalaryEmp())){
			salaryEmp = salaryAdvanceService.get(SalaryEmp.class, empAdvanceWage.getSalaryEmp());

			empAdvanceWage = salaryAdvanceService.get(EmpAdvanceWage.class, empAdvanceWage.getSalaryEmp());
			if (empAdvanceWage != null && salaryEmp != null) {
				department1 = salaryAdvanceService.get(Department1.class, salaryEmp.getDepartment1());
				
				empAdvanceWage.setEmpName(salaryEmp.getEmpName());
				if(department1 != null){
					empAdvanceWage.setDepartment1(department1.getCode());
					empAdvanceWage.setDept1Descr(department1.getDesc1());
				}
			}
		}
		
		return new ModelAndView("admin/salary/salaryAdvance/salaryAdvance_view")
		.addObject("empAdvanceWage", empAdvanceWage);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, EmpAdvanceWage empAdvanceWage){
		logger.debug("添加薪酬预领开始");
		try{

			if(StringUtils.isBlank(empAdvanceWage.getSalaryEmp())){
				ServletUtils.outPrintFail(request, response, "新增失败，请选择员工编号");
				return;
			}
			EmpAdvanceWage eAdvanceWage = salaryAdvanceService.get(EmpAdvanceWage.class, empAdvanceWage.getSalaryEmp());
			if(eAdvanceWage != null){
				ServletUtils.outPrintFail(request, response, "新增失败，该员工与存在预领金额");
				return;
			}
			if(empAdvanceWage.getAdvanceWage() == null){
				ServletUtils.outPrintFail(request, response, "新增失败，请填写预领金额");
				return;
			}
			if(empAdvanceWage.getAdvanceWage() < 0.0){
				ServletUtils.outPrintFail(request, response, "新增失败，预领金额不能小于0");
				return;
			}
			
			empAdvanceWage.setLastUpdate(new Date());
			empAdvanceWage.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			empAdvanceWage.setExpired("F");
			empAdvanceWage.setActionLog("ADD");
			
			salaryAdvanceService.save(empAdvanceWage);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增薪酬预领失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdaet(HttpServletRequest request,HttpServletResponse response, EmpAdvanceWage empAdvanceWage){
		logger.debug("编辑薪酬预领开始");
		try{
			
			if(empAdvanceWage.getAdvanceWage() == null){
				ServletUtils.outPrintFail(request, response, "编辑失败，请填写预领金额");
				return;
			}
			if(empAdvanceWage.getAdvanceWage() < 0.0){
				ServletUtils.outPrintFail(request, response, "编辑失败，预领金额不能小于0");
				return;
			}
			
			empAdvanceWage.setLastUpdate(new Date());
			empAdvanceWage.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			empAdvanceWage.setExpired("F");
			empAdvanceWage.setActionLog("Edit");
			
			salaryAdvanceService.update(empAdvanceWage);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑薪酬预领失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			EmpAdvanceWage empAdvanceWage){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryAdvanceService.findPageBySql(page, empAdvanceWage);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬预领_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
