package com.house.home.web.controller.salary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpReg;
import com.house.home.entity.salary.SalaryItem;
import com.house.home.service.salary.SalaryEmpRegService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/salaryEmpReg")
public class SalaryEmpRegController extends BaseController{
	@Autowired
	private  SalaryEmpRegService salaryEmpRegService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryEmpReg salaryEmpReg) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salaryEmpRegService.findPageBySql(page, salaryEmpReg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, SalaryEmpReg salaryEmpReg){
		
		return new ModelAndView("admin/salary/salaryEmpReg/salaryEmpReg_list")
		.addObject("salaryEmpReg", salaryEmpReg);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response, SalaryEmpReg salaryEmpReg){
		
		return new ModelAndView("admin/salary/salaryEmpReg/salaryEmpReg_save")
		.addObject("salaryEmpReg", salaryEmpReg);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response, SalaryEmpReg salaryEmpReg){

		SalaryEmp salaryEmp = new SalaryEmp();
		
		if(salaryEmpReg.getPk() != null){
			salaryEmpReg = salaryEmpRegService.get(SalaryEmpReg.class, salaryEmpReg.getPk());
			if(salaryEmp != null){
				salaryEmp = salaryEmpRegService.get(SalaryEmp.class, salaryEmpReg.getSalaryEmp());
				salaryEmpReg.setEmpName(salaryEmp.getEmpName());
			}
		}
		
		return new ModelAndView("admin/salary/salaryEmpReg/salaryEmpReg_update")
		.addObject("salaryEmpReg", salaryEmpReg);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response, SalaryEmpReg salaryEmpReg){
		
		SalaryEmp salaryEmp = new SalaryEmp();
		
		if(salaryEmpReg.getPk() != null){
			salaryEmpReg = salaryEmpRegService.get(SalaryEmpReg.class, salaryEmpReg.getPk());
			if(salaryEmp != null){
				salaryEmp = salaryEmpRegService.get(SalaryEmp.class, salaryEmpReg.getSalaryEmp());
				salaryEmpReg.setEmpName(salaryEmp.getEmpName());
			}
		}
		
		return new ModelAndView("admin/salary/salaryEmpReg/salaryEmpReg_view")
		.addObject("salaryEmpReg", salaryEmpReg);
	}
	
	@RequestMapping("/checkInfo")
	public void checkInfo(HttpServletRequest request,HttpServletResponse response,
			SalaryEmpReg salaryEmpReg){
		logger.debug("检查是否存在员工");
		try{
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(StringUtils.isNotBlank(salaryEmpReg.getSalaryEmp())){
				if(salaryEmpReg.getPk() == null){
					salaryEmpReg.setPk(-1);
				}
				list = salaryEmpRegService.checkInfo(salaryEmpReg);
				if(list != null && list.size() > 0){
					ServletUtils.outPrintFail(request, response, "该员工已经存在，是否继续保存");
					return;
				}
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增员工挂证记录失败");
		}
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			SalaryEmpReg salaryEmpReg){
		logger.debug("新增开始");
		try{
			
			salaryEmpReg.setLastUpdate(new Date());
			salaryEmpReg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryEmpReg.setExpired("F");
			salaryEmpReg.setActionLog("ADD");
			
			salaryEmpRegService.save(salaryEmpReg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增员工挂证记录失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			SalaryEmpReg salaryEmpReg){
		logger.debug("编辑开始");
		try{
			
			salaryEmpReg.setLastUpdate(new Date());
			salaryEmpReg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryEmpReg.setExpired("F");
			salaryEmpReg.setActionLog("Edit");
			
			salaryEmpRegService.update(salaryEmpReg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增员工挂证记录失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,HttpServletResponse response,
			SalaryEmpReg salaryEmpReg){
		logger.debug("删除开始");
		try{
			if(salaryEmpReg.getPk() != null){
				salaryEmpReg = salaryEmpRegService.get(SalaryEmpReg.class, salaryEmpReg.getPk());
				salaryEmpReg.setLastUpdate(new Date());
				salaryEmpReg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				salaryEmpReg.setExpired("T");
				salaryEmpReg.setActionLog("EDIT");
				salaryEmpRegService.update(salaryEmpReg);
				ServletUtils.outPrintSuccess(request, response);
			}else {
				ServletUtils.outPrintFail(request, response, "员工挂证记录删除失败,没有对应pk");
			}
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "员工挂证记录删除失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryEmpReg salaryEmpReg){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryEmpRegService.findPageBySql(page, salaryEmpReg);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"员工挂证_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
