package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CutFeeSet;
import com.house.home.service.basic.CutFeeSetService;
@Controller
@RequestMapping("/admin/cutFeeSet")
public class CutFeeSetController extends BaseController{
	@Autowired
	private  CutFeeSetService cutFeeSetService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CutFeeSet cutFeeSet) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cutFeeSetService.findPageBySql(page, cutFeeSet);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/cutFeeSet/cutFeeSet_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/cutFeeSet/cutFeeSet_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, CutFeeSet cutFeeSet) throws Exception {
		return new ModelAndView("admin/basic/cutFeeSet/cutFeeSet_update")
			.addObject("cutFeeSet", cutFeeSet);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, CutFeeSet cutFeeSet) throws Exception {
		return new ModelAndView("admin/basic/cutFeeSet/cutFeeSet_update")
			.addObject("cutFeeSet", cutFeeSet);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			CutFeeSet cutFeeSet){
		logger.debug("????????????");
		try {
			cutFeeSet.setLastUpdate(new Date());
			cutFeeSet.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cutFeeSet.setExpired("F");
			cutFeeSet.setActionLog("ADD");
			
			this.cutFeeSetService.save(cutFeeSet);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			CutFeeSet cutFeeSet){
		logger.debug("????????????");
		try {
			cutFeeSet.setLastUpdate(new Date());
			cutFeeSet.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			cutFeeSet.setActionLog("Edit");
			
			Boolean result = this.cutFeeSetService.doUpdate(cutFeeSet);
			if (result) {
				ServletUtils.outPrintSuccess(request, response);
			}else {
				ServletUtils.outPrintFail(request, response, "????????????");
			}
		}catch (Exception e) {
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, CutFeeSet cutFeeSet){
		logger.debug("????????????");
		try{
			if(StringUtils.isNotBlank(cutFeeSet.getCutType()) && null != cutFeeSet.getSize()){
				Boolean result = cutFeeSetService.doDelete(cutFeeSet);
				if (result) {
					ServletUtils.outPrintSuccess(request, response, "????????????");
				} else {
					ServletUtils.outPrintFail(request, response, "????????????");
				}
			} else {
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CutFeeSet cutFeeSet){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cutFeeSetService.findPageBySql(page, cutFeeSet);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"?????????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * @Description: TODO ?????????????????????????????????????????????
	 * @author	created by zb
	 * @date	2018-10-22--??????11:40:40
	 * @param request
	 * @param response
	 * @param cutType
	 * @param size
	 * @param oldCutType ???????????????????????????????????????
	 * @param oldSize
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,
			String cutType, String size,
			String oldCutType, String oldSize){
		if (StringUtil.isNotBlank(oldCutType) && StringUtil.isNotBlank(oldSize)) {
			if (oldCutType.equals(cutType) && oldSize.equals(size)) {
				return this.out(true, true);
			}
		}
		boolean cfs = cutFeeSetService.checkCode(cutType, size);
		
		if(cfs){
			return this.out("??????", false);
		}
		return this.out(cfs, true);
	}
}
