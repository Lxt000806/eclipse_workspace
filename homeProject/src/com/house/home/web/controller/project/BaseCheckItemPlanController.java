package com.house.home.web.controller.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.BaseCheckItemPlan;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.project.BaseCheckItemPlanService;

@Controller
@RequestMapping("/admin/baseCheckItemPlan")
public class BaseCheckItemPlanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseCheckItemPlanController.class);

	@Autowired
	private BaseCheckItemPlanService baseCheckItemPlanService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseCheckItemPlan baseCheckItemPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseCheckItemPlanService.findPageBySql(page, baseCheckItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseCheckItemPlan列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,BaseCheckItemPlan baseCheckItemPlan) throws Exception {
		List<Map<String, Object>> list=baseCheckItemPlanService.addAllInfoCustType();
		String showCustType="";
		for(Map<String, Object> map:list){
			if(StringUtils.isNotBlank(showCustType)){
				showCustType+=",";
			}
			showCustType+=map.get("code").toString();
		}
		baseCheckItemPlan.setCustType(showCustType);
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_list").addObject("baseCheckItemPlan", baseCheckItemPlan);
	}
	/**
	 * BaseCheckItemPlan查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_code");
	}
	/**
	 * 跳转到新增BaseCheckItemPlan页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseCheckItemPlan页面");
		BaseCheckItemPlan baseCheckItemPlan = null;
		if (StringUtils.isNotBlank(id)){
			baseCheckItemPlan = baseCheckItemPlanService.get(BaseCheckItemPlan.class, Integer.parseInt(id));
			baseCheckItemPlan.setPk(null);
		}else{
			baseCheckItemPlan = new BaseCheckItemPlan();
		}
		
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_save")
			.addObject("baseCheckItemPlan", baseCheckItemPlan);
	}
	/**
	 * 跳转到修改BaseCheckItemPlan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			BaseCheckItemPlan baseCheckItemPlan){
		logger.debug("跳转到修改BaseCheckItemPlan页面");
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_update")
			.addObject("baseCheckItemPlan", baseCheckItemPlan);
	}
	
	/**
	 * 跳转到结算报价页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String code,String m_umState){
		logger.debug("跳转到结算报价页面");
		Map<String, Object> map=baseCheckItemPlanService.findHeadInfoBySql(code).get(0);
		map.put("lastUpdatedBy", getUserContext(request).getCzybh());
		map.put("m_umState", m_umState);
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_detail")
				.addObject("map", map);
	}
	
	/**
	 * 修改BaseCheckItemPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseCheckItemPlan baseCheckItemPlan){
		logger.debug("修改BaseCheckItemPlan开始");
		try{
			baseCheckItemPlan.setLastUpdate(new Date());
			baseCheckItemPlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseCheckItemPlanService.update(baseCheckItemPlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseCheckItemPlan失败");
		}
	}
	
	/**
	 * 删除BaseCheckItemPlan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseCheckItemPlan开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseCheckItemPlan编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseCheckItemPlan baseCheckItemPlan = baseCheckItemPlanService.get(BaseCheckItemPlan.class, Integer.parseInt(deleteId));
				if(baseCheckItemPlan == null)
					continue;
				baseCheckItemPlan.setExpired("T");
				baseCheckItemPlanService.update(baseCheckItemPlan);
			}
		}
		logger.debug("删除BaseCheckItemPlan IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseCheckItemPlan导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseCheckItemPlan baseCheckItemPlan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseCheckItemPlanService.findPageBySql(page, baseCheckItemPlan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础结算项目预算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查询DetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseCheckItemPlan baseCheckItemPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageOrderBy("workType1Descr,a.worktype12Descr,a.dispseq,a.fixareadescr");
		page.setPageOrder("asc");
		baseCheckItemPlanService.findBodyInfoBySql(page, baseCheckItemPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到结算报价页面
	 * @return
	 */
	@RequestMapping("/goQuickSave")
	public ModelAndView goQuickSave(HttpServletRequest request, HttpServletResponse response, 
			BaseCheckItemPlan baseCheckItemPlan){
		logger.debug("跳转到结算报价页面");
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_quickSave")
				.addObject("baseCheckItemPlan", baseCheckItemPlan);
	}
	/**
	 * 
	 * 基础结算项目
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/goBaseCheckItem")
	public ModelAndView goBaseCheckItem(HttpServletRequest request,
			HttpServletResponse response,BaseCheckItemPlan baseCheckItemPlan) throws Exception {
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_baseCheckItem")
				.addObject("baseCheckItemPlan", baseCheckItemPlan);
	}
	/**
	 * 构建合并临时JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/goTmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTmpJqGrid(HttpServletRequest request,
			HttpServletResponse response,String params,String orderBy) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		JSONArray jsonArray = JSONArray.fromObject(params); 
		@SuppressWarnings("rawtypes")
		List<Map<String,Object>> list = (List)jsonArray;
		if(orderBy!=null){
			String[] orders=orderBy.split(",");
			for(String order:orders){
				Collections.sort(list, new ListCompareUtil(order));
			}
		}
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setPageSize(10000);
		page.setResult(list);	
		return new WebPage<Map<String,Object>>(page);

	}
	/**
	 * 从客户报价导入
	 * 
	 * @param request
	 * @param response
	 * @param baseCheckItemPlan
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importFromCust")
	public List<Map<String, Object>> importFromCust(HttpServletRequest request,
			HttpServletResponse response, BaseCheckItemPlan baseCheckItemPlan) {
		logger.debug("从客户报价导入");
		baseCheckItemPlan.setLastUpdatedBy(getUserContext(request).getCzybh());
		List<Map<String, Object>> list = baseCheckItemPlanService.importFromCust(baseCheckItemPlan);
		return list;
	}
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param workCost
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,BaseCheckItemPlan baseCheckItemPlan){
		logger.debug("保存");		
		try {	
			
			String bcip =request.getParameter("baseCheckItemPlanJson");
		    JSONObject jsonObject = JSON.parseObject(bcip);
			com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  baseCheckItemPlanJson=jsonArray.toString();
			baseCheckItemPlan.setBaseCheckItemPlanJson(baseCheckItemPlanJson);
			Result result = this.baseCheckItemPlanService.doSaveProc(baseCheckItemPlan);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 跳转修改单价窗口
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/goUpdatePrice")
	public ModelAndView goUpdatePrice(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/baseCheckItemPlan/baseCheckItemPlan_UpdatePrice");
		
	}
}
