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

import com.house.home.entity.salary.SalaryInd;
import com.house.home.service.salary.SalaryIndService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/salaryInd")
public class SalaryIndController extends BaseController{
	@Autowired
	private  SalaryIndService salaryIndService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryInd salaryInd) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryIndService.findPageBySql(page, salaryInd);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryInd salaryInd){
		
		return new ModelAndView("admin/salary/salaryInd/salaryInd_list")
		.addObject("salaryInd", salaryInd);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response,SalaryInd salaryInd){
		
		return new ModelAndView("admin/salary/salaryInd/salaryInd_save")
		.addObject("salaryInd", salaryInd);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,SalaryInd salaryInd){
		
		if(StringUtils.isNotBlank(salaryInd.getCode())){
			salaryInd = salaryIndService.get(SalaryInd.class, salaryInd.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryInd/salaryInd_update")
		.addObject("salaryInd", salaryInd);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,SalaryInd salaryInd){
		
		if(StringUtils.isNotBlank(salaryInd.getCode())){
			salaryInd = salaryIndService.get(SalaryInd.class, salaryInd.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryInd/salaryInd_view")
		.addObject("salaryInd", salaryInd);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, SalaryInd salaryInd){
		logger.debug("????????????????????????");
		try{

			if(StringUtils.isBlank(salaryInd.getCode())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????");
				return;
			}
			
			SalaryInd saInd = salaryIndService.get(SalaryInd.class, salaryInd.getCode());
			if(saInd != null){
				
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
				return;
			}
			
			boolean checkDescr = salaryIndService.checkSalaryIndDescr(salaryInd.getDescr(), salaryInd.getCode(), "A");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
				return;
			}
			
			salaryInd.setLastUpdate(new Date());
			salaryInd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryInd.setExpired("F");
			salaryInd.setActionLog("ADD");
			
			salaryIndService.save(salaryInd);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryInd salaryInd){
		logger.debug("????????????????????????");
		try{

			boolean checkDescr = salaryIndService.checkSalaryIndDescr(salaryInd.getDescr(), salaryInd.getCode(), "M");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????");
				return;
			}
			
			salaryInd.setLastUpdate(new Date());
			salaryInd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryInd.setExpired("F");
			salaryInd.setActionLog("EDIT");
			
			salaryIndService.update(salaryInd);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryInd salaryInd){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryIndService.findPageBySql(page, salaryInd);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
