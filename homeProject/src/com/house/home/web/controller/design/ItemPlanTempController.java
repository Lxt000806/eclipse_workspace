package com.house.home.web.controller.design;

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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Menu;
import com.house.framework.service.MenuService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.service.design.ItemPlanTempService;
@Controller
@RequestMapping("/admin/itemPlanTemp")
public class ItemPlanTempController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ItemPlanTempController.class);
	@Autowired
	private ItemPlanTempService itemPlanTempService;
	@Autowired
	private MenuService menuService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlanTemp itemPlanTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanTempService.findPageBySql(page, itemPlanTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据 list页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goListJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlanTemp itemPlanTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanTempService.findListPageBySql(page, itemPlanTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemPlanTemp itemPlanTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanTempService.findDetailPageBySql(page, itemPlanTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 材料报价模板列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItem页面");
		ItemPlanTemp itemPlanTemp= null;
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_save")
			.addObject("itemPlanTemp", itemPlanTemp)
			.addObject("lastUpdatedBy",this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItem页面");
		ItemPlanTemp itemPlanTemp= null;
		if (StringUtils.isNotBlank(id)){
			itemPlanTemp = itemPlanTempService.get(ItemPlanTemp.class, id);
		}else{
			itemPlanTemp = new ItemPlanTemp();
		}
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_update")
			.addObject("itemPlanTemp", itemPlanTemp)
			.addObject("lastUpdatedBy", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItem页面");
		ItemPlanTemp itemPlanTemp= null;
		if (StringUtils.isNotBlank(id)){
			itemPlanTemp = itemPlanTempService.get(ItemPlanTemp.class, id);
		}else{
			itemPlanTemp = new ItemPlanTemp();
		}
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_view")
			.addObject("itemPlanTemp", itemPlanTemp);
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			ItemPlanTemp itemPlanTemp){
		logger.debug("跳转到明细新增页面");
		
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_add")
			.addObject("itemPlanTemp", itemPlanTemp);
	}
	
	@RequestMapping("/goFastAdd")
	public ModelAndView goFastAdd(HttpServletRequest request, HttpServletResponse response, 
			ItemPlanTemp itemPlanTemp){
		logger.debug("跳转到明细新增页面");
		
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_faseAdd")
			.addObject("itemPlanTemp", itemPlanTemp);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request, HttpServletResponse response, 
			ItemPlanTemp itemPlanTemp){
		logger.debug("跳转到铭心编辑页面");
		
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_addUpdate")
			.addObject("itemPlanTemp", itemPlanTemp);
	}
	
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request, HttpServletResponse response, 
			ItemPlanTemp itemPlanTemp){
		logger.debug("跳转到明细查看页面");
		
		return new ModelAndView("admin/design/itemPlanTemp/itemPlanTemp_addView")
			.addObject("itemPlanTemp", itemPlanTemp);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			ItemPlanTemp itemPlanTemp){
		logger.debug("材料报价模板新增开始");
		try {
			itemPlanTemp.setM_umState("A");
			itemPlanTemp.setLastUpdate(new Date());
			itemPlanTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemPlanTemp.setExpired("F");
			itemPlanTemp.setActionLog("Add");
			String detailJson = request.getParameter("detailJson");
			itemPlanTemp.setDetailJson(detailJson);
			
			Result result =this.itemPlanTempService.doSave(itemPlanTemp) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料报价模板新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			ItemPlanTemp itemPlanTemp){
		logger.debug("材料报价模板新增开始");
		try {
			itemPlanTemp.setM_umState("M");
			itemPlanTemp.setLastUpdate(new Date());
			itemPlanTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemPlanTemp.setActionLog("Edit");
			String detailJson = request.getParameter("detailJson");
			itemPlanTemp.setDetailJson(detailJson);
			
			Result result = this.itemPlanTempService.doUpdate(itemPlanTemp) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料报价模板编辑失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemPlanTemp itemPlanTemp){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		itemPlanTempService.findListPageBySql(page, itemPlanTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料报价模板_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
}
