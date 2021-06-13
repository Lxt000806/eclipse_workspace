package com.house.home.web.controller.project;

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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.IntReplenishDT;
import com.house.home.service.project.IntReplenishDTService;

@Controller
@RequestMapping("/admin/intReplenishDT")
public class IntReplenishDTController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IntReplenishDTController.class);

	@Autowired
	private IntReplenishDTService intReplenishDTService;

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
			HttpServletResponse response, IntReplenishDT intReplenishDT) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intReplenishDTService.findPageBySql(page, intReplenishDT);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 根据No获取DT信息
	 * @author	created by zb
	 * @date	2019-11-20--上午11:55:02
	 * @param request
	 * @param response
	 * @param intReplenishDT
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridByNo")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridByNo(HttpServletRequest request,
			HttpServletResponse response, IntReplenishDT intReplenishDT) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intReplenishDTService.findNoPageBySql(page, intReplenishDT);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, IntReplenishDT intReplenishDT) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intReplenishDTService.goCodeJqGrid(page, intReplenishDT);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * IntReplenishDT列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/intReplenishDT/intReplenishDT_list");
	}
	/**
	 * IntReplenishDT查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,IntReplenishDT intReplenishDT) throws Exception {

		return new ModelAndView("admin/project/intReplenishDT/intReplenishDT_code").addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * 根据id查询详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getIntReplenishDT")
	@ResponseBody
	public JSONObject getIntReplenishDT(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		IntReplenishDT intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, id);
		if(intReplenishDT == null){
			return this.out("系统中不存在code="+id+"的集成补货明细", false);
		}
		return this.out(intReplenishDT, true);
	}
	/**
	 * 跳转到新增IntReplenishDT页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增IntReplenishDT页面");
		IntReplenishDT intReplenishDT = null;
		if (StringUtils.isNotBlank(id)){
			intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, Integer.parseInt(id));
			intReplenishDT.setPk(null);
		}else{
			intReplenishDT = new IntReplenishDT();
		}
		
		return new ModelAndView("admin/project/intReplenishDT/intReplenishDT_save")
			.addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * 跳转到修改IntReplenishDT页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改IntReplenishDT页面");
		IntReplenishDT intReplenishDT = null;
		if (StringUtils.isNotBlank(id)){
			intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, Integer.parseInt(id));
		}else{
			intReplenishDT = new IntReplenishDT();
		}
		
		return new ModelAndView("admin/project/intReplenishDT/intReplenishDT_update")
			.addObject("intReplenishDT", intReplenishDT);
	}
	
	/**
	 * 跳转到查看IntReplenishDT页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看IntReplenishDT页面");
		IntReplenishDT intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/intReplenishDT/intReplenishDT_detail")
				.addObject("intReplenishDT", intReplenishDT);
	}
	/**
	 * 添加IntReplenishDT
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, IntReplenishDT intReplenishDT){
		logger.debug("添加IntReplenishDT开始");
		try{
			this.intReplenishDTService.save(intReplenishDT);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加IntReplenishDT失败");
		}
	}
	
	/**
	 * 修改IntReplenishDT
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, IntReplenishDT intReplenishDT){
		logger.debug("修改IntReplenishDT开始");
		try{
			intReplenishDT.setLastUpdate(new Date());
			intReplenishDT.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intReplenishDTService.update(intReplenishDT);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改IntReplenishDT失败");
		}
	}
	
	/**
	 * 删除IntReplenishDT
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntReplenishDT开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntReplenishDT编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntReplenishDT intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, Integer.parseInt(deleteId));
				if(intReplenishDT == null)
					continue;
				intReplenishDT.setExpired("T");
				intReplenishDTService.update(intReplenishDT);
			}
		}
		logger.debug("删除IntReplenishDT IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	/**
	 * 删除明细文件名字、地址（弃用）
	 * @author	created by zb
	 * @date	2019-11-21--上午11:20:48
	 * @param request
	 * @param response
	 * @param deleteIds
	 */
	/*@RequestMapping("/doDeleteDoc")
	public void doDeleteDoc(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除IntReplenishDT文件开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "IntReplenishDT编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				IntReplenishDT intReplenishDT = intReplenishDTService.get(IntReplenishDT.class, Integer.parseInt(deleteId));
				if(intReplenishDT == null)
					continue;
				intReplenishDT.setDocDescr("");
				intReplenishDT.setDocName("");
				intReplenishDTService.update(intReplenishDT);
			}
		}
		logger.debug("删除IntReplenishDT IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}*/
	/**
	 *IntReplenishDT导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, IntReplenishDT intReplenishDT){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intReplenishDTService.findPageBySql(page, intReplenishDT);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"IntReplenishDT_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
