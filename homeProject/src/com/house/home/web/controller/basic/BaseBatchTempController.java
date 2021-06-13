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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.entity.basic.CstrDayStd;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.basic.BaseBatchTempService;
import com.house.home.bean.basic.BaseBatchTempBean;

@Controller
@RequestMapping("/admin/baseBatchTemp")
public class BaseBatchTempController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseBatchTempController.class);

	@Autowired
	private BaseBatchTempService baseBatchTempService;

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
			HttpServletResponse response, BaseBatchTemp baseBatchTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseBatchTempService.findPageBySql(page, baseBatchTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseBatchTemp列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_list");
	}
	/**
	 * BaseBatchTemp查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,BaseBatchTemp baseBatchTemp) throws Exception {

		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_code").addObject("baseBatchTemp",baseBatchTemp);
	}
	/**
	 * 跳转到新增BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseBatchTemp页面");
		BaseBatchTemp baseBatchTemp = null;
		if (StringUtils.isNotBlank(id)){
			baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, id);
			baseBatchTemp.setNo(null);
		}else{
			baseBatchTemp = new BaseBatchTemp();
		}
		
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_save")
			.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 跳转到修改BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseBatchTemp页面");
		BaseBatchTemp baseBatchTemp = null;
		if (StringUtils.isNotBlank(id)){
			baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, id);
		}else{
			baseBatchTemp = new BaseBatchTemp();
		}
		baseBatchTemp.setM_umState("M");
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_update")
			.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 跳转到修改BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goArea")
	public ModelAndView goRegionSet(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseBatchTemp页面");
		BaseBatchTemp baseBatchTemp = null;
		if (StringUtils.isNotBlank(id)){
			baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, id);
		}else{
			baseBatchTemp = new BaseBatchTemp();
		}
		
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_area")
			.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 跳转到修改BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goItem")
	public ModelAndView goItem(HttpServletRequest request, HttpServletResponse response, BaseBatchTemp baseBatchTemp){
		logger.debug("跳转到修改BaseBatchTemp页面");
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_item")
			.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 跳转到修改BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goAddItem")
	public ModelAndView goAddItem(HttpServletRequest request, HttpServletResponse response, BaseBatchTemp baseBatchTemp){
		logger.debug("跳转到修改BaseBatchTemp页面");
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_addItem")
			.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 跳转到查看BaseBatchTemp页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseBatchTemp页面");
		BaseBatchTemp baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, id);
		
		return new ModelAndView("admin/basic/baseBatchTemp/baseBatchTemp_detail")
				.addObject("baseBatchTemp", baseBatchTemp);
	}
	/**
	 * 添加BaseBatchTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseBatchTemp baseBatchTemp){
		logger.debug("添加BaseBatchTemp开始");
		try{
			String str = baseBatchTempService.getSeqNo("tBaseBatchTemp");
			baseBatchTemp.setNo(str);
			baseBatchTemp.setLastUpdate(new Date());
			baseBatchTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseBatchTemp.setExpired("F");
			baseBatchTemp.setActionLog("ADD");
			this.baseBatchTempService.save(baseBatchTemp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseBatchTemp失败");
		}
	}
	
	/**
	 * 修改BaseBatchTemp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseBatchTemp baseBatchTemp){
		logger.debug("修改BaseBatchTemp开始");
		try{
			if (!"T".equals(baseBatchTemp.getExpired())) {
				baseBatchTemp.setExpired("F");
			}
			baseBatchTemp.setLastUpdate(new Date());
			baseBatchTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseBatchTemp.setActionLog("EDIT");
			this.baseBatchTempService.update(baseBatchTemp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseBatchTemp失败");
		}
	}
	
	/**
	 * 删除BaseBatchTemp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseBatchTemp开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseBatchTemp编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseBatchTemp baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, deleteId);
				if(baseBatchTemp == null)
					continue;
				baseBatchTemp.setExpired("T");
				baseBatchTempService.update(baseBatchTemp);
			}
		}
		logger.debug("删除BaseBatchTemp IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 * 根据id查询基础项目信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getBaseBatchTemp")
	@ResponseBody
	public JSONObject getBaseBatchTemp(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		BaseBatchTemp baseBatchTemp = baseBatchTempService.get(BaseBatchTemp.class, id);
		if(baseBatchTemp== null){
			return this.out("系统中不存在code="+id+"基础批量报价模板信息", false);
		}
		return this.out(baseBatchTemp, true);
	}
	/**
	 *baseBatchTemp导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, BaseBatchTemp baseBatchTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseBatchTempService.findPageBySql(page, baseBatchTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"基础批量报价模板_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAreaJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAreaJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseBatchTemp baseBatchTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseBatchTempService.goAreaJqGrid(page, baseBatchTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseBatchTemp baseBatchTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseBatchTempService.goItemJqGrid(page, baseBatchTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkExistType")
	@ResponseBody
	public List<Map<String, Object>> checkExistType(HttpServletRequest request,HttpServletResponse response,BaseBatchTemp baseBatchTemp){
		List<Map<String, Object>>list=baseBatchTempService.checkExistType(baseBatchTemp);
		return list;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkExistDescr")
	@ResponseBody
	public List<Map<String, Object>> checkExistDescr(HttpServletRequest request,HttpServletResponse response,BaseBatchTemp baseBatchTemp){
		List<Map<String, Object>>list=baseBatchTempService.checkExistDescr(baseBatchTemp);
		return list;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkExistTempDescr")
	@ResponseBody
	public List<Map<String, Object>> checkExistTempDescr(HttpServletRequest request,HttpServletResponse response,BaseBatchTemp baseBatchTemp){
		List<Map<String, Object>>list=baseBatchTempService.checkExistTempDescr(baseBatchTemp);
		return list;
	}
	/**
	 * 保存区域区间
	 * @param request
	 * @param response
	 * @param baseBatchTemp
	 */
	@RequestMapping("/doSaveItem")
	public void doSaveItem(HttpServletRequest request,HttpServletResponse response,BaseBatchTemp baseBatchTemp){
		logger.debug("保存");		
		try {	
			baseBatchTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.baseBatchTempService.doSaveProc(baseBatchTemp);
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
	 * 更新顺序
	 * @param request
	 * @param response
	 * @param baseBatchTemp
	 */
	@RequestMapping("/updateDispSeq")
	public void updateDispSeq(HttpServletRequest request,HttpServletResponse response,BaseBatchTemp baseBatchTemp){
		logger.debug("更新顺序");		
		try {	
			baseBatchTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			baseBatchTempService.updateDispSeq(baseBatchTemp);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "更新顺序失败");
		}
	}
}
