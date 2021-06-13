package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.basic.ItemTypeConfirmService;

@RequestMapping("/admin/itemConfirm")
@Controller
public class ItemTypeConfirmController extends BaseController{
	
	@Autowired
	private ItemTypeConfirmService itemTypeConfirmService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody 
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemTypeConfirm itemTypeConfirm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTypeConfirmService.findPageBySql(page,itemTypeConfirm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemTypeConfirm itemTypeConfirm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTypeConfirmService.findDetailPageBySql(page,itemTypeConfirm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
		ItemTypeConfirm itemTypeConfirm=new ItemTypeConfirm();
		itemTypeConfirm=itemTypeConfirmService.get(ItemTypeConfirm.class, code);

		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_update").addObject("itemTypeConfirm",itemTypeConfirm);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
		ItemTypeConfirm itemTypeConfirm=new ItemTypeConfirm();
		itemTypeConfirm=itemTypeConfirmService.get(ItemTypeConfirm.class, code);

		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_view").addObject("itemTypeConfirm",itemTypeConfirm);
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,String ar,String arr,String arry) throws Exception {
		System.out.println(ar);
		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_add").addObject("ar",ar)
		.addObject("arr",arr).addObject("arry",arry);
	}
	
	@RequestMapping("/goSaveUpdate")
	public ModelAndView goSaveUpdate(HttpServletRequest request,
			HttpServletResponse response,ItemTypeConfirm itemTypeConfirm,String arr,String arry,String ar) throws Exception {
		
		return new ModelAndView("admin/basic/confirmItemType/confirmItemType_save_update")
		.addObject("itemTypeConfirm",itemTypeConfirm).addObject("arr",arr).addObject("arry",arry).addObject("ar",ar);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,ItemTypeConfirm itemTypeConfirm){
		logger.debug("保存开始");
		try {
			itemTypeConfirm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemTypeConfirm.setM_umState("A");
			String detailJson = request.getParameter("detailJson");

			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "明细表无记录");
				return;
			}
			Result result = this.itemTypeConfirmService.doSave(itemTypeConfirm);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				//result.getInfo()
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增专盘失败");
		}
	}
		
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,ItemTypeConfirm itemTypeConfirm){
		logger.debug("保存开始");
		try {
			itemTypeConfirm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemTypeConfirm.setM_umState("M");
			String detailJson = request.getParameter("detailJson");

			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "明细表无记录");
				return;
			}
			Result result = this.itemTypeConfirmService.doSave(itemTypeConfirm);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
		
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response
			,ItemTypeConfirm itemTypeConfirm){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemTypeConfirmService.findPageBySql(page,itemTypeConfirm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"确认材料类型表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}	
	
		
}
	
	
	
	

