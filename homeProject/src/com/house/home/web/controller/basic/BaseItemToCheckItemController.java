package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BaseItemToCheckItem;
import com.house.home.service.basic.BaseItemToCheckItemService;
@Controller
@RequestMapping("/admin/baseItemToCheckItem")
public class BaseItemToCheckItemController extends BaseController{
	@Autowired
	private  BaseItemToCheckItemService baseItemToCheckItemService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,BaseItemToCheckItem baseItemToCheckItem) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		baseItemToCheckItemService.findPageBySql(page, baseItemToCheckItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		return new ModelAndView("admin/basic/baseItemToCheckItem/baseItemToCheckItem_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/baseItemToCheckItem/baseItemToCheckItem_save");
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, BaseItemToCheckItem baseItemToCheckItem) throws Exception {
		return new ModelAndView("admin/basic/baseItemToCheckItem/baseItemToCheckItem_save")
			.addObject("baseItemToCheckItem", baseItemToCheckItem);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, BaseItemToCheckItem baseItemToCheckItem) throws Exception {
		return new ModelAndView("admin/basic/baseItemToCheckItem/baseItemToCheckItem_update")
			.addObject("baseItemToCheckItem", baseItemToCheckItem);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, BaseItemToCheckItem baseItemToCheckItem) throws Exception {
		return new ModelAndView("admin/basic/baseItemToCheckItem/baseItemToCheckItem_update")
			.addObject("baseItemToCheckItem", baseItemToCheckItem);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			BaseItemToCheckItem baseItemToCheckItem){
		logger.debug("新增保存");
		try {
			baseItemToCheckItem.setLastUpdate(new Date());
			baseItemToCheckItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			baseItemToCheckItem.setExpired("F");
			baseItemToCheckItem.setActionLog("ADD");
			
			this.baseItemToCheckItemService.save(baseItemToCheckItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			BaseItemToCheckItem baseItemToCheckItem){
		logger.debug("新增保存");
		try {
			baseItemToCheckItem.setLastUpdate(new Date());
			baseItemToCheckItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			baseItemToCheckItem.setExpired("F");
			baseItemToCheckItem.setActionLog("Edit");
			
			this.baseItemToCheckItemService.update(baseItemToCheckItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "pk不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					BaseItemToCheckItem bitci = new BaseItemToCheckItem();
					bitci.setPk(Integer.parseInt(deleteId));
					this.baseItemToCheckItemService.delete(bitci);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseItemToCheckItem baseItemToCheckItem){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemToCheckItemService.findPageBySql(page, baseItemToCheckItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"基础项目与结算项目映射管理-"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * @Description: 【基础项目编号+基础结算项目编号】不能重复
	 * @author	created by zb
	 * @date	2018-9-20--下午2:50:37
	 * @param request
	 * @param response
	 * @param baseItemCode
	 * @param baseCheckItemCode
	 * @return false:存在
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,
			String openComponent_baseItem_baseItemCode, String openComponent_baseCheckItem_baseCheckItemCode,
			String oldBaseItemCode, String oldBaseCheckItemCode){
		String[] baseItemCode = {""};
		String[] baseCheckItemCode = {""};
		
		if (StringUtil.isNotBlank(openComponent_baseItem_baseItemCode)) {
			baseItemCode = openComponent_baseItem_baseItemCode.split("\\|");
		}
		if (StringUtil.isNotBlank(openComponent_baseCheckItem_baseCheckItemCode)) {
			baseCheckItemCode = openComponent_baseCheckItem_baseCheckItemCode.split("\\|");
		}
		if (StringUtil.isNotBlank(oldBaseItemCode) && StringUtil.isNotBlank(oldBaseCheckItemCode)) {
			if (oldBaseItemCode.equals(baseItemCode[0]) && oldBaseCheckItemCode.equals(baseCheckItemCode[0])) {
				return this.out(true, true);
			}
		}
		boolean bcic = baseItemToCheckItemService.checkCode(baseItemCode[0], baseCheckItemCode[0]);
		
		if(bcic){
			return this.out("存在", false);
		}
		return this.out(bcic, true);
	}
}
