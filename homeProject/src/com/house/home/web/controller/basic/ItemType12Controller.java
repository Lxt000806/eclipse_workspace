package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ItemType12;
import com.house.home.entity.finance.LaborFeeType;
import com.house.home.service.basic.ItemType12Service;

@Controller
@RequestMapping("/admin/itemType12")
public class ItemType12Controller extends BaseController { 	
	@Autowired
	private ItemType12Service itemType12Service;

	/**
	 * itemSetService列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * 新增跳转itemSet表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemType12 itemtype12) throws Exception {
		return new ModelAndView("admin/basic/itemtype12/itemtype12_list").addObject("itemtype12", itemtype12).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	/*this.getUserContext(request).getCzybh()   调用权限*/
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemType12 itemtype12) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemType12Service.findPageBySql(page, itemtype12);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/*
	 *itemtype12保存 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增材料套餐包页面");
		ItemType12 itemType12 = new ItemType12();		
		return new ModelAndView("admin/basic/itemtype12/itemType12_save")
			.addObject("itemType12", itemType12).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/**
	 *材料分类保存 
	 *
	 */
	@RequestMapping("/doitemtype12Save")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,ItemType12 itemtype12){
		logger.debug("材料分类新增开始");		
		try {
			itemtype12.setLastUpdate(new Date());				
			itemtype12.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemtype12.setLastUpdate(new Date());
			itemtype12.setM_umState("A");
			itemtype12.setExpired("F");
 		
			ItemType12 iSet = this.itemType12Service.getByDescr(itemtype12.getDescr());
			
			if (iSet!=null){
				ServletUtils.outPrintFail(request, response, "材料分类名称重复！");
				return;
			}	
		
			Result result = this.itemType12Service.doItemType12Save(itemtype12);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料分类新增失败");
		}
	}
	
	/**
	 *材料分类保存 
	 *
	 */
	@RequestMapping("/doitemtype12Update")
	public void doitemtype12Update(HttpServletRequest request,HttpServletResponse response,ItemType12 itemtype12){
		logger.debug("材料分类编辑开始");		
		try {
			ItemType12 iSet = this.itemType12Service.getByDescr1(itemtype12.getDescr(),itemtype12.getDescr1());			
			if (iSet!=null){
				ServletUtils.outPrintFail(request, response, "材料分类名称重复！");
				return;
			}	
			itemtype12.setLastUpdate(new Date());				
			itemtype12.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemtype12.setLastUpdate(new Date());
			itemtype12.setM_umState("M");
			itemtype12.setExpired("F");
			itemtype12.setActionLog("EDIT"); 							
			Result result = this.itemType12Service.doItemType12Save(itemtype12);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料分类编辑失败");
		}
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除材料分类开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "材料分类不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemType12 itemtype12 = this.itemType12Service.get(ItemType12.class, deleteId);
				itemtype12.setExpired("T");
				itemtype12.setM_umState("M");			
				itemtype12.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = itemType12Service.deleteForProc(itemtype12);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug(" IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/*
	 *itemtype12编辑页面 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑材料分类页面");
		ItemType12 itemtype12 = null;
		if (StringUtils.isNotBlank(id)){
			itemtype12 = itemType12Service.get(ItemType12.class, id);
		}else{
			itemtype12 = new ItemType12();
		}
		if(StringUtils.isNotBlank(itemtype12.getTransFeeType())){
			itemtype12.setTransFeeTypeDescr(itemType12Service.get(LaborFeeType.class, itemtype12.getTransFeeType()).getDescr());
		}
		if(StringUtils.isNotBlank(itemtype12.getInstallFeeType())){
			itemtype12.setInstallFeeTypeDescr(itemType12Service.get(LaborFeeType.class, itemtype12.getInstallFeeType()).getDescr());
		}
		
		return new ModelAndView("admin/basic/itemtype12/itemType12_update")
			.addObject("itemtype12", itemtype12).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/*
	 *itemtype12查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看材料分类页面");
		ItemType12 itemtype12 = null;
		if (StringUtils.isNotBlank(id)){
			itemtype12 = itemType12Service.get(ItemType12.class, id);
		}else{
			itemtype12 = new ItemType12();
		}
		if(StringUtils.isNotBlank(itemtype12.getTransFeeType())){
			itemtype12.setTransFeeTypeDescr(itemType12Service.get(LaborFeeType.class, itemtype12.getTransFeeType()).getDescr());
		}
		if(StringUtils.isNotBlank(itemtype12.getInstallFeeType())){
			itemtype12.setInstallFeeTypeDescr(itemType12Service.get(LaborFeeType.class, itemtype12.getInstallFeeType()).getDescr());
		}
		return new ModelAndView("admin/basic/itemtype12/itemtype12_view")
			.addObject("itemtype12", itemtype12).addObject("czy", this.getUserContext(request).getCzybh());
	}
		
}
