package com.house.home.web.controller.basic;

import java.util.ArrayList;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.AppItemTypeBatch;
import com.house.home.service.basic.AppItemTypeBatchService;

/**
 * @Description: TODO 下单材料类型批次Controller
 * @author created by zb
 * @date   2018-7-30--上午11:38:12
 */
@Controller
@RequestMapping("/admin/appItemTypeBatch")
public class AppItemTypeBatchController extends BaseController{
	@Autowired
	private  AppItemTypeBatchService appItemTypeBatchService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, AppItemTypeBatch appItemTypeBatch) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		appItemTypeBatchService.findPageBySql(page, appItemTypeBatch);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO detail分页查询
	 * @author	created by zb
	 * @date	2018-8-1--下午1:44:20
	 * @param request
	 * @param response
	 * @param appItemTypeBatch 传入code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, AppItemTypeBatch appItemTypeBatch) throws Exception {
		
		Page<Map<String, Object>> page;
		page = this.newPageForJqGrid(request);
		try {
			appItemTypeBatchService.findDetailByCode(page, appItemTypeBatch);
			return new WebPage<Map<String,Object>>(page);
		} catch (Exception e) {
			//当发生异常时，传一个空的List到result中
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			page.setResult(arrayList);
			return new WebPage<Map<String,Object>>(page);
		}
		
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,AppItemTypeBatch appItemTypeBatch) throws Exception {
		return new ModelAndView("admin/basic/appItemTypeBatch/appItemTypeBatch_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/appItemTypeBatch/appItemTypeBatch_save");
	}
	
	/**
	 * @Description: TODO 明细新增页
	 * @author	created by zb
	 * @date	2018-8-2--下午5:58:07
	 * @param request
	 * @param response
	 * @param appItemTypeBatch
	 * @param keys 传入已存在confItemTypes数组
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailSave")
	public ModelAndView goDetailSave(HttpServletRequest request,
			HttpServletResponse response, AppItemTypeBatch appItemTypeBatch, String keys) throws Exception {
		return new ModelAndView("admin/basic/appItemTypeBatch/appItemTypeBatch_detailSave")
			.addObject("appItemTypeBatch", appItemTypeBatch)
			.addObject("keys", keys);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,AppItemTypeBatch appItemTypeBatch) throws Exception {
		AppItemTypeBatch aitb=appItemTypeBatchService.get(AppItemTypeBatch.class, appItemTypeBatch.getCode());
		return new ModelAndView("admin/basic/appItemTypeBatch/appItemTypeBatch_update").addObject("appItemTypeBatch", aitb);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,AppItemTypeBatch appItemTypeBatch) throws Exception {
		AppItemTypeBatch aitb=appItemTypeBatchService.get(AppItemTypeBatch.class, appItemTypeBatch.getCode());
		return new ModelAndView("admin/basic/appItemTypeBatch/appItemTypeBatch_view").addObject("appItemTypeBatch", aitb);
	}
	
	/**
	 * @Description: TODO 表单验证code是否重复
	 * @author	created by zb
	 * @date	2018-8-1--下午4:25:34
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public JSONObject checkCode(HttpServletRequest request,HttpServletResponse response,String code){
		if(StringUtils.isEmpty(code)){
			return this.out("传入的code为空", false);
		}
		AppItemTypeBatch aitb = this.appItemTypeBatchService.get(AppItemTypeBatch.class, code);

		if(aitb != null){
			return this.out("系统中已存在code="+code+"的信息", false);
		}
		return this.out(aitb, true);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,AppItemTypeBatch appItemTypeBatch){
		logger.debug("添加ItemTypeBatch开始");
		try{
			AppItemTypeBatch aitb = this.appItemTypeBatchService.get(AppItemTypeBatch.class, appItemTypeBatch.getCode());
			if (aitb != null) {
				ServletUtils.outPrintFail(request, response, "该批次号重复");
				return;
			}
			appItemTypeBatch.setM_umState("A");
			appItemTypeBatch.setLastUpdate(new Date());
			appItemTypeBatch.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			appItemTypeBatch.setExpired("F");
			appItemTypeBatch.setActionLog("ADD");
			Result result = this.appItemTypeBatchService.doSave(appItemTypeBatch);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加下单材料类型批次失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,AppItemTypeBatch appItemTypeBatch){
		logger.debug("修改ItemTypeBatch开始");
		try{
			AppItemTypeBatch aitb = this.appItemTypeBatchService.get(AppItemTypeBatch.class, appItemTypeBatch.getCode());
			if (aitb == null) {
				appItemTypeBatch.setM_umState("A");
			} else {
				appItemTypeBatch.setM_umState("M");
			}
			appItemTypeBatch.setActionLog("Edit");
			appItemTypeBatch.setLastUpdate(new Date());
			appItemTypeBatch.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.appItemTypeBatchService.doSave(appItemTypeBatch);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑下单材料类型批次失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemTypeBatch开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "批次号不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					AppItemTypeBatch appItemTypeBatch = this.appItemTypeBatchService.get(AppItemTypeBatch.class, deleteId);
					appItemTypeBatch.setM_umState("D");			
					Result result = appItemTypeBatchService.doSave(appItemTypeBatch);
					if ("1".equals(result.getCode())){
						ServletUtils.outPrintSuccess(request, response, "删除成功");
					}else{
						ServletUtils.outPrintFail(request, response, result.getInfo());
					}
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除下单材料类型批次失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			 AppItemTypeBatch appItemTypeBatch){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		appItemTypeBatchService.findPageBySql(page, appItemTypeBatch);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"下单材料类型批次表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
