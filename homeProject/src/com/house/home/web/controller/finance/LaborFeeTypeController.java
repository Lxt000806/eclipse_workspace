package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.SupplFeeType;
import com.house.home.entity.finance.LaborFeeType;
import com.house.home.entity.finance.ReturnPay;
import com.house.home.service.finance.LaborFeeTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/laborFeeType")
public class LaborFeeTypeController extends BaseController{
	@Autowired
	private  LaborFeeTypeService laborFeeTypeService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,LaborFeeType laborFeeType) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		laborFeeTypeService.findPageBySql(page, laborFeeType);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/laborFeeType/laborFeeType_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, LaborFeeType lft){
		logger.debug("跳转到新增一级部门页面");
		LaborFeeType laborFeeType = null;
		if (StringUtils.isNotBlank(lft.getCode())) {
			laborFeeType = laborFeeTypeService.getByCode(lft.getCode());
		} else {
			laborFeeType = new LaborFeeType();
		}
		laborFeeType.setM_umState(lft.getM_umState());		
		return new ModelAndView("admin/finance/laborFeeType/laborFeeType_save")
			.addObject("laborFeeType", laborFeeType);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, LaborFeeType laborFeeType){
		logger.debug("添加SalaryType开始");
		try{
			LaborFeeType lft = laborFeeTypeService.getByCode(laborFeeType.getCode());
			if(lft!=null){
				ServletUtils.outPrintFail(request, response, "人工费用类型编号不能重复");
				return;
			}
			laborFeeType.setLastUpdate(new Date());
			laborFeeType.setLastUpdatedBy(getUserContext(request).getCzybh());
			laborFeeType.setExpired("F");
			laborFeeType.setActionLog("ADD");
			this.laborFeeTypeService.save(laborFeeType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加LaborFeeType失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, LaborFeeType laborFeeType){
		logger.debug("修改SalaryType开始");
		try{
			laborFeeType.setLastUpdate(new Date());
			laborFeeType.setLastUpdatedBy(getUserContext(request).getCzybh());
			laborFeeType.setActionLog("EDIT");
			this.laborFeeTypeService.update(laborFeeType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改LaborFeeType失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,LaborFeeType laborFeeType){		
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		laborFeeTypeService.findPageBySql(page, laborFeeType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"人工费用类型表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,LaborFeeType laborFeeType) throws Exception {

		return new ModelAndView("admin/finance/laborFeeType/laborFeeType_code").addObject("laborFeeType", laborFeeType);
	}
	
	@RequestMapping("/getLaborFeeType")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的code为空", false);
		}
		LaborFeeType laborFeeType= laborFeeTypeService.get(LaborFeeType.class, id);
		if(laborFeeType == null){
			return this.out("系统中不存在Code="+id+"的费用类型", false);
		}
		return this.out(laborFeeType, true);
	}
}
