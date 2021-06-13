package com.house.home.web.controller.basic;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.insales.BaseItem;
import com.house.home.service.basic.BasePlanTempService;

@Controller
@RequestMapping("/admin/basePlanTemp")
public class BasePlanTempController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BasePlanTempController.class);

	@Autowired
	private BasePlanTempService basePlanTempService;

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
			HttpServletResponse response, BasePlanTemp basePlanTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		basePlanTempService.findPageBySql(page, basePlanTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BasePlanTemp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_list");
	}
	/**
	 * BasePlanTemp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,BasePlanTemp basePlanTemp) throws Exception {

		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_code")
		.addObject("basePlanTemp", basePlanTemp);
	}
	/**
	 * 跳转到新增BasePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			BasePlanTemp basePlanTemp){
		logger.debug("跳转到新增BasePlanTemp页面");
		basePlanTemp.setNo("保存时自动生成");
		basePlanTemp.setM_umState("A");
		basePlanTemp.setLastUpdatedBy(getUserContext(request).getCzylb());
		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_save")
			.addObject("basePlanTemp", basePlanTemp);
	}
	/**
	 * 跳转到修改BasePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BasePlanTemp页面");
		BasePlanTemp basePlanTemp = null;
		if (StringUtils.isNotBlank(id)){
			basePlanTemp = basePlanTempService.get(BasePlanTemp.class, id);
		}else{
			basePlanTemp = new BasePlanTemp();
		}
		basePlanTemp.setM_umState("M");
		try {
			basePlanTemp.setCustType(basePlanTemp.getCustType().trim());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_save")
			.addObject("basePlanTemp", basePlanTemp);
	}
	
	/**
	 * 跳转到查看BasePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BasePlanTemp页面");
		BasePlanTemp basePlanTemp = basePlanTempService.get(BasePlanTemp.class, id);
		basePlanTemp.setM_umState("V");
		try {
			basePlanTemp.setCustType(basePlanTemp.getCustType().trim());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/basic/basePlanTemp/basePlanTemp_save")
				.addObject("basePlanTemp", basePlanTemp);
	}
	/**
	 * 添加BasePlanTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BasePlanTemp basePlanTemp){
		logger.debug("添加BasePlanTemp开始");
		try {	
			if (!"T".equals(basePlanTemp.getExpired())) {
				basePlanTemp.setExpired("F");
			}
			basePlanTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String basePlanTempDetail =request.getParameter("basePlanTempDetailJson");
		    JSONObject jsonObject = JSON.parseObject(basePlanTempDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  basePlanTempDetailJson=jsonArray.toString();
			System.out.println(basePlanTempDetailJson);
			basePlanTemp.setBasePlanTempDetailJson(basePlanTempDetailJson);
			Result result = this.basePlanTempService.doSaveProc(basePlanTemp);
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
	 * 修改BasePlanTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BasePlanTemp basePlanTemp){
		logger.debug("修改BasePlanTemp开始");
		try{
			basePlanTemp.setLastUpdate(new Date());
			basePlanTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.basePlanTempService.update(basePlanTemp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BasePlanTemp失败");
		}
	}
	
	/**
	 * 删除BasePlanTemp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BasePlanTemp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BasePlanTemp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BasePlanTemp basePlanTemp = basePlanTempService.get(BasePlanTemp.class, deleteId);
				if(basePlanTemp == null)
					continue;
				basePlanTemp.setExpired("T");
				basePlanTempService.update(basePlanTemp);
			}
		}
		logger.debug("删除BasePlanTemp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BasePlanTemp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BasePlanTemp basePlanTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		basePlanTempService.findPageBySql(page, basePlanTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础算量模板管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param baseItem
	 * @return
	 */
	@RequestMapping("/checkExist")
	@ResponseBody
	public List<Map<String, Object>> checkExist(HttpServletRequest request,HttpServletResponse response,BasePlanTemp basePlanTemp){
		List<Map<String, Object>>list=basePlanTempService.checkExist(basePlanTemp);
		return list;
	}
	/**
	 * 获取基础算量模板信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBasePlanTemp")
	@ResponseBody
	public JSONObject getRoll(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BasePlanTemp basePlanTemp = basePlanTempService.get(BasePlanTemp.class, id);
		if(basePlanTemp== null){
			return this.out("系统中不存在no="+id+"的预算模板", false);
		}
		return this.out(basePlanTemp, true);
	}
	

}
