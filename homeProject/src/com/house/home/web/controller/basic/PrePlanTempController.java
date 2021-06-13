package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.basic.PrePlanTempService;

@Controller
@RequestMapping("/admin/prePlanTemp")
public class PrePlanTempController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrePlanTempController.class);

	@Autowired
	private PrePlanTempService prePlanTempService;
	
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPrePlanTempNo")
	@ResponseBody
	public JSONObject getItemBatchHeader(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		PrePlanTemp prePlanTemp = prePlanTempService.get(PrePlanTemp.class, id);
		if(prePlanTemp== null){
			return this.out("系统中不存在no="+id+"的预算模板", false);
		}
		return this.out(prePlanTemp, true);
	}

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
			HttpServletResponse response, PrePlanTemp prePlanTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prePlanTempService.findPageBySql(page, prePlanTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrePlanTemp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/prePlanTemp/prePlanTemp_list");
	}
	/**
	 * PrePlanTemp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,PrePlanTemp prePlanTemp) throws Exception {

		return new ModelAndView("admin/basic/prePlanTemp/prePlanTemp_code").addObject("prePlanTemp", prePlanTemp);
	}
	/**
	 * 跳转到新增PrePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			PrePlanTemp prePlanTemp){
		logger.debug("跳转到新增PrePlanTemp页面");
		prePlanTemp.setNo("保存时自动生成");
		prePlanTemp.setM_umState("A");
		prePlanTemp.setLastUpdatedBy(getUserContext(request).getCzylb());
		return new ModelAndView("admin/basic/prePlanTemp/prePlanTemp_save")
			.addObject("prePlanTemp", prePlanTemp);
	}
	/**
	 * 跳转到修改PrePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrePlanTemp页面");
		PrePlanTemp prePlanTemp = null;
		if (StringUtils.isNotBlank(id)){
			prePlanTemp = prePlanTempService.get(PrePlanTemp.class, id);
		}else{
			prePlanTemp = new PrePlanTemp();
		}
		prePlanTemp.setM_umState("M");
		try {
			prePlanTemp.setCustType(prePlanTemp.getCustType().trim());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/basic/prePlanTemp/prePlanTemp_save")
			.addObject("prePlanTemp", prePlanTemp);
	}
	
	/**
	 * 跳转到查看PrePlanTemp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrePlanTemp页面");
		PrePlanTemp prePlanTemp = prePlanTempService.get(PrePlanTemp.class, id);
		prePlanTemp.setM_umState("V");
		try {
			prePlanTemp.setCustType(prePlanTemp.getCustType().trim());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/basic/prePlanTemp/prePlanTemp_save")
				.addObject("prePlanTemp", prePlanTemp);
	}
	/**
	 * 添加PrePlanTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrePlanTemp prePlanTemp){
		logger.debug("添加PrePlanTemp开始");
		try {	
			if (!"T".equals(prePlanTemp.getExpired())) {
				prePlanTemp.setExpired("F");
			}
			prePlanTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String prePlanTempDetail =request.getParameter("prePlanTempDetailJson");
		    JSONObject jsonObject = JSON.parseObject(prePlanTempDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  prePlanTempDetailJson=jsonArray.toString();
			System.out.println(prePlanTempDetailJson);
			prePlanTemp.setPrePlanTempDetailJson(prePlanTempDetailJson);
			Result result = this.prePlanTempService.doSaveProc(prePlanTemp);
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
	 * 修改PrePlanTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrePlanTemp prePlanTemp){
		logger.debug("修改PrePlanTemp开始");
		try{
			prePlanTemp.setLastUpdate(new Date());
			prePlanTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prePlanTempService.update(prePlanTemp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrePlanTemp失败");
		}
	}
	
	/**
	 * 删除PrePlanTemp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrePlanTemp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrePlanTemp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrePlanTemp prePlanTemp = prePlanTempService.get(PrePlanTemp.class, deleteId);
				if(prePlanTemp == null)
					continue;
				prePlanTemp.setExpired("T");
				prePlanTempService.update(prePlanTemp);
			}
		}
		logger.debug("删除PrePlanTemp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrePlanTemp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrePlanTemp prePlanTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prePlanTempService.findPageBySql(page, prePlanTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"快速预报价模板管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
