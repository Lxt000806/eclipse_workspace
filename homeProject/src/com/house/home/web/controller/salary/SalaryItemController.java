package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.project.CustWorker;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryItem;
import com.house.home.entity.salary.SalaryItemStatCfg;
import com.house.home.service.salary.SalaryItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/salaryItem")
public class SalaryItemController extends BaseController{
	@Autowired
	private  SalaryItemService salaryItemService;

	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salaryItemService.findPageBySql(page, salaryItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCategoryDefindJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goCategoryDefindJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salaryItemService.findCategoryDefindPageBySql(page, salaryItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getFormulaNode")
	@ResponseBody
	public WebPage<Map<String, Object>> getFormulaNode(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);

		salaryItemService.findFormulaNodeBySql(page, salaryItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_list")
		.addObject("salaryItem", salaryItem);
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_save")
		.addObject("salaryItem", salaryItem);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_update")
		.addObject("salaryItem", salaryItem);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_view")
		.addObject("salaryItem", salaryItem);
	}
	
	@RequestMapping("/goCategoryDefine")
	public ModelAndView goCategoryDefine(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_categoryDefine")
		.addObject("salaryItem", salaryItem);
	}
	
	@RequestMapping("/goPersonalDefine")
	public ModelAndView goPersonalDefine(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_personalDefine")
		.addObject("salaryItem", salaryItem);
	}
	
	@RequestMapping("/goPersonalDefineAdd")
	public ModelAndView goPersonalDefineAdd(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		//Map<String, Object> map = salaryItemService.getOperatorCfg();
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		List<Map<String, Object>> node = salaryItemService.findFormulaNodeBySql(page, salaryItem);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_personalDefineAdd")
		.addObject("salaryItem", salaryItem).addObject("node", json);
	}
	
	@RequestMapping("/goCategoryDefineAdd")
	public ModelAndView goCategoryDefineAdd(HttpServletRequest request ,
			HttpServletResponse response, SalaryItem salaryItem){
		
		if(StringUtils.isNotBlank(salaryItem.getCode())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
		}
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		List<Map<String, Object>> node = salaryItemService.findFormulaNodeBySql(page, salaryItem);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_categoryDefineAdd")
		.addObject("salaryItem", salaryItem).addObject("node", json);
	}
	
	@RequestMapping("/goCategoryDefineUpdate")
	public ModelAndView goCategoryDefineUpdate(HttpServletRequest request ,
			HttpServletResponse response, Integer pk){
		
		SalaryItemStatCfg salaryItemStatCfg = new SalaryItemStatCfg();
		SalaryItem salaryItem = new SalaryItem();
		
		if(pk != null){
			salaryItemStatCfg = salaryItemService.get(SalaryItemStatCfg.class, pk);
			if(salaryItemStatCfg != null && StringUtils.isNotBlank(salaryItemStatCfg.getSalaryItem())){
				salaryItem = salaryItemService.get(SalaryItem.class, salaryItemStatCfg.getSalaryItem());
				salaryItemStatCfg.setSalaryItemDescr(salaryItem != null?salaryItem.getDescr():"");
			}
		}
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		List<Map<String, Object>> node = salaryItemService.findFormulaNodeBySql(page, salaryItem);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_categoryDefineUpdate")
		.addObject("salaryItemStatCfg", salaryItemStatCfg).addObject("node",json);
	}
	
	@RequestMapping("/goPersonalDefineUpdate")
	public ModelAndView goPersonalDefineUpdate(HttpServletRequest request ,
			HttpServletResponse response, Integer pk){
		SalaryItemStatCfg salaryItemStatCfg = new SalaryItemStatCfg();
		SalaryItem salaryItem = new SalaryItem();
		SalaryEmp salaryEmp = new SalaryEmp();
		
		if(pk != null){
			salaryItemStatCfg = salaryItemService.get(SalaryItemStatCfg.class, pk);
			if(salaryItemStatCfg != null && StringUtils.isNotBlank(salaryItemStatCfg.getSalaryItem())){
				salaryItem = salaryItemService.get(SalaryItem.class, salaryItemStatCfg.getSalaryItem());
				salaryItemStatCfg.setSalaryItemDescr(salaryItem != null?salaryItem.getDescr():"");
				if(StringUtils.isNotBlank(salaryItemStatCfg.getFilterFormula())){
					salaryEmp = salaryItemService.get(SalaryEmp.class, salaryItemStatCfg.getFilterFormula());
					salaryItemStatCfg.setEmpName(salaryEmp != null? salaryEmp.getEmpName():"");
				}
			}
		}
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		List<Map<String, Object>> node = salaryItemService.findFormulaNodeBySql(page, salaryItem);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_personalDefineUpdate")
		.addObject("salaryItemStatCfg", salaryItemStatCfg).addObject("node", json);
	}
	
	@RequestMapping("/goCategoryDefineView")
	public ModelAndView goCategoryDefineView(HttpServletRequest request ,
			HttpServletResponse response, Integer pk){
		
		SalaryItemStatCfg salaryItemStatCfg = new SalaryItemStatCfg();
		SalaryItem salaryItem = new SalaryItem();
		
		if(pk != null){
			salaryItemStatCfg = salaryItemService.get(SalaryItemStatCfg.class, pk);
			if(salaryItemStatCfg != null && StringUtils.isNotBlank(salaryItemStatCfg.getSalaryItem())){
				salaryItem = salaryItemService.get(SalaryItem.class, salaryItemStatCfg.getSalaryItem());
				salaryItemStatCfg.setSalaryItemDescr(salaryItem != null?salaryItem.getDescr():"");
			}
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_categoryDefineView")
		.addObject("salaryItemStatCfg", salaryItemStatCfg);
	}
	
	@RequestMapping("/goPersonalDefineView")
	public ModelAndView goPersonalDefineView(HttpServletRequest request ,
			HttpServletResponse response, Integer pk){
		SalaryItemStatCfg salaryItemStatCfg = new SalaryItemStatCfg();
		SalaryItem salaryItem = new SalaryItem();
		SalaryEmp salaryEmp = new SalaryEmp();
		
		if(pk != null){
			salaryItemStatCfg = salaryItemService.get(SalaryItemStatCfg.class, pk);
			if(salaryItemStatCfg != null && StringUtils.isNotBlank(salaryItemStatCfg.getSalaryItem())){
				salaryItem = salaryItemService.get(SalaryItem.class, salaryItemStatCfg.getSalaryItem());
				salaryItemStatCfg.setSalaryItemDescr(salaryItem != null?salaryItem.getDescr():"");
				if(StringUtils.isNotBlank(salaryItemStatCfg.getFilterFormula())){
					salaryEmp = salaryItemService.get(SalaryEmp.class, salaryItemStatCfg.getFilterFormula());
					salaryItemStatCfg.setEmpName(salaryEmp != null? salaryEmp.getEmpName():"");
				}
			}
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_personalDefineView")
		.addObject("salaryItemStatCfg", salaryItemStatCfg);
	}
	
	@RequestMapping("/goFilterFormula")
	public ModelAndView goFilterFormula(HttpServletRequest request ,
			HttpServletResponse response, SalaryItemStatCfg salaryItemStatCfg){
		
		System.out.println(request.getParameter("filterFormula"));
		SalaryItem salaryItem = new SalaryItem();
		if(StringUtils.isNotBlank(salaryItemStatCfg.getSalaryItem())){
			salaryItem = salaryItemService.get(SalaryItem.class, salaryItemStatCfg.getSalaryItem());
		} else {
			salaryItem.setItemLevel(10);
		}
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		List<Map<String, Object>> node = salaryItemService.findFormulaNodeBySql(page, salaryItem);
		String json = "[]";
		if(node != null){
			json = JSON.toJSONString(node);
		}
		
		return new ModelAndView("admin/salary/salaryItem/salaryItem_filterFormula")
		.addObject("salaryItemStatCfg", salaryItemStatCfg).addObject("node", json);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			SalaryItem salaryItem){
		logger.debug("新增开始");
		try{
			SalaryItem saItem = salaryItemService.get(SalaryItem.class, salaryItem.getCode());
			if(saItem != null){
				ServletUtils.outPrintFail(request, response, "新增失败，薪酬项目编号已经存在");
				return;
			}
			
			boolean checkDescr = salaryItemService.checkSalaryItemDescr(salaryItem, "A");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "新增失败，薪酬项目名称重复");
				return;
			}
			
			salaryItem.setLastUpdate(new Date());
			salaryItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryItem.setExpired("F");
			salaryItem.setActionLog("ADD");
			
			salaryItemService.save(salaryItem);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增薪酬项目失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryItem salaryItem){
		logger.debug("编辑系统指标开始");
		try{

			boolean checkDescr = salaryItemService.checkSalaryItemDescr(salaryItem, "M");
			if(!checkDescr){
				ServletUtils.outPrintFail(request, response, "编辑失败，薪酬项目名称已经存在");
				return;
			}
			
			salaryItem.setLastUpdate(new Date());
			salaryItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryItem.setExpired("F");
			salaryItem.setActionLog("EDIT");
			
			salaryItemService.update(salaryItem);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑系统指标失败");
		}
	}
	

	@RequestMapping("/doSaveDefine")
	public void doSaveDefine(HttpServletRequest request,HttpServletResponse response,
			SalaryItemStatCfg salaryItemStatCfg){
		logger.debug("新增开始");
		try{
			
			salaryItemStatCfg.setLastUpdate(new Date());
			salaryItemStatCfg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryItemStatCfg.setExpired("F");
			salaryItemStatCfg.setActionLog("ADD");
			
			if(!checkMon(salaryItemStatCfg.getBeginMon(), salaryItemStatCfg.getEndMon())){

				ServletUtils.outPrintFail(request, response, "新增薪酬定义失败,开始时间大于结束时间");
				return;
			}
			
			salaryItemService.save(salaryItemStatCfg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增薪酬项目失败");
		}
	}
	
	@RequestMapping("/doUpdateDefine")
	public void doUpdateDefine(HttpServletRequest request,HttpServletResponse response,
			SalaryItemStatCfg salaryItemStatCfg){
		logger.debug("新增开始");
		try{
			
			salaryItemStatCfg.setLastUpdate(new Date());
			salaryItemStatCfg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryItemStatCfg.setExpired("F");
			salaryItemStatCfg.setActionLog("ADD");
			
			if(!checkMon(salaryItemStatCfg.getBeginMon(), salaryItemStatCfg.getEndMon())){

				ServletUtils.outPrintFail(request, response, "编辑薪酬定义失败,开始时间大于结束时间");
				return;
			}
			
			salaryItemService.update(salaryItemStatCfg);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增薪酬项目失败");
		}
	}
	
	@RequestMapping("/doDelDefine")
	public void doDelDefine(HttpServletRequest request ,
			HttpServletResponse response ,Integer pk){
		logger.debug("定义删除开始");
		SalaryItemStatCfg salaryItemStatCfg = null;
		try{
			if(pk!=null){
				salaryItemStatCfg = salaryItemService.get(SalaryItemStatCfg.class, pk);
				
				if(salaryItemStatCfg != null){
					salaryItemService.delete(salaryItemStatCfg);
					ServletUtils.outPrintSuccess(request, response,"删除成功");
				} else {
					ServletUtils.outPrintFail(request, response, "删除失败,该数据已经不存在");
				}
				
			} else {
				ServletUtils.outPrintFail(request, response, "删除失败，没有获取到PK");
			}
		} catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	public boolean checkMon(Integer beginMon, Integer endMon){
		
		if(endMon != null && beginMon != null){
			if(beginMon > endMon){
				return false;
			}
		}
		
		return true;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryItem salaryItem){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryItemService.findPageBySql(page, salaryItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬项目_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
