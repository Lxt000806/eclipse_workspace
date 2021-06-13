package com.house.home.web.controller.item;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.ItemSetService;

@Controller
@RequestMapping("/admin/itemSet")
public class ItemSetController extends BaseController { 
	
	@Autowired
	private ItemSetService itemSetService;

	/**
	 * itemSetService列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemSet itemSet) throws Exception {
		return new ModelAndView("admin/basic/itemSet/itemSet_list")
		.addObject("czybh",this.getUserContext(request).getCzybh());
	}
	/*
	 * 新增跳转itemSet表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	/*
	 *itemSet保存 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增材料套餐包页面");						
		ItemSet itemSet = new ItemSet();		
		return new ModelAndView("admin/basic/itemSet/itemSet_save")
			.addObject("itemSet", itemSet);
	}
	
	/* 
	 *itemset编辑页面 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到编辑材料套餐包页面");
		ItemSet itemSet = null;
		if (StringUtils.isNotBlank(id)){
			itemSet = itemSetService.get(ItemSet.class, id);
		}else{
			itemSet = new ItemSet();
		}
		return new ModelAndView("admin/basic/itemSet/itemSet_update")
			.addObject("itemSet", itemSet);
	}
	
	
	/*
	 *itemset查看页面 
	 * */
	@RequestMapping("/goview")
	public ModelAndView goview(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看材料套餐包页面");
		ItemSet itemSet = null;
		if (StringUtils.isNotBlank(id)){
			itemSet = itemSetService.get(ItemSet.class, id);
		}else{
			itemSet = new ItemSet();
		}
		return new ModelAndView("admin/basic/itemSet/itemSet_view")
			.addObject("itemSet", itemSet);
	}
	
	@RequestMapping("/goadd")
	public ModelAndView goadd(HttpServletRequest request, HttpServletResponse response,ItemSetDetail itemSetDetail){
		logger.debug("跳转到新增材料套餐包明细页面");	
		
		return new ModelAndView("admin/basic/itemSet/itemSet_add")
			.addObject("itemSetDetail", itemSetDetail);
	}

	/*
	 * 新增页面的 编辑
	 * */
	@RequestMapping("/goaddUpdate")
	public ModelAndView goaddUpdate(HttpServletRequest request, HttpServletResponse response,ItemSetDetail itemSetDetail){
		logger.debug("跳转到修改材料套餐包明细页面");			
		return new ModelAndView("admin/basic/itemSet/itemSetDetail_update")
			.addObject("itemSetDetail", itemSetDetail);
	}
	
	/*
	 * 新增页面的查看
	 * */
	@RequestMapping("/goaddView")
	public ModelAndView goaddview(HttpServletRequest request, HttpServletResponse response,ItemSetDetail itemSetDetail){
		logger.debug("跳转到查看材料套餐包明细页面");	
		return new ModelAndView("admin/basic/itemSet/itemSetDetail_view")
			.addObject("itemSetDetail", itemSetDetail);
	}
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
			HttpServletResponse response,ItemSet itemSet) throws Exception {
	    
	    itemSet.setItemType1("RZ");
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSetService.findPageBySql(page, itemSet);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/* 新增 */
	@RequestMapping("/goJqGridDetailadd")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridadd(HttpServletRequest request,
			HttpServletResponse response,ItemSetDetail itemSetDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSetService.findPageBySqlDetailadd(page, itemSetDetail);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/* 编辑 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemSetDetail itemSetDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSetService.findPageBySqlDetail(page, itemSetDetail);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	/**
	 *材料套餐保存 
	 *
	 */
	@RequestMapping("/doitemSetSave")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,ItemSet itemSet){
		logger.debug("材料套餐包新增开始");		
		try {
			itemSet.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			itemSet.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemSet.setLastUpdate(new Date());
			itemSet.setM_umState("A");
			itemSet.setExpired("F"); 		
			ItemSet iSet = this.itemSetService.getByDescr(itemSet.getDescr());			
			if (iSet!=null){
				ServletUtils.outPrintFail(request, response, "材料名称重复！");
				return;
			}	
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}
		
			Result result = this.itemSetService.doItemSetSave(itemSet);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料套餐新增失败");
		}
	}
	
	/**
	 *材料套餐编辑 
	 *
	 */
	@RequestMapping("/doitemSetUpdate")
	public void doitemSetUpdate(HttpServletRequest request,HttpServletResponse response,ItemSet itemSet){
		logger.debug("材料套餐包编辑开始");
		try {
			ItemSet iSet = this.itemSetService.getByDescr1(itemSet.getDescr(), itemSet.getDescr1());			
			if (iSet!=null){
				ServletUtils.outPrintFail(request, response, "材料名称重复！");
				return;
			}	
			itemSet.setLastUpdate(new Date());
			String detailJson = request.getParameter("detailJson");
			itemSet.setDetailJson(detailJson);
			itemSet.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			itemSet.setLastUpdate(new Date());
			itemSet.setM_umState("M");
			itemSet.setExpired("F");	
			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}		
			Result result = this.itemSetService.doItemSetSave(itemSet);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "材料套餐编辑失败");
		}
	}
	
	/**
	 * 删除套餐包
	 * @param request
	 * @param response
	 * @param roleId
	 */
	/**
	 *材料套餐编辑 
	 *
	 */
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除材料套餐包开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "材料套餐包编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemSet itemSet = this.itemSetService.get(ItemSet.class, deleteId);
				itemSet.setExpired("T");
				itemSet.setM_umState("M");			
				itemSet.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = itemSetService.deleteForProc(itemSet);
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
	
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemSet itemSet){
		logger.debug("添加套餐包明细开始");
		try{
			String str = itemSetService.getSeqNo("tItemSetDetail");
			if ("".equals(str)){
				itemSet.setNo("");
			}else {
				itemSet.setNo(str);
			}
			this.itemSetService.save(itemSet);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加套餐包明细失败");
			
		}
	}
	/**
	 * 套餐包
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/itemSetNo") //*获取商品类型1,2,3*/
	@ResponseBody
	public JSONObject itemSetNo(HttpServletRequest request, HttpServletResponse response, ItemSet itemSet){
		List<Map<String,Object>> regionList =itemSetService.findItemSetNo(itemSet);
		return this.out(regionList, true);
	}


}
