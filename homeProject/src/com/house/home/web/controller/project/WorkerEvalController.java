package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.WorkerEval;
import com.house.home.service.project.WorkerEvalService;
@Controller
@RequestMapping("/admin/workerEval")
public class WorkerEvalController extends BaseController{
	@Autowired
	private  WorkerEvalService workerEvalService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkerEval workerEval) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerEvalService.findPageBySql(page, workerEval);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workerEval/workerEval_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/workerEval/workerEval_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, WorkerEval workerEval) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerEvalService.findPageBySql(page, workerEval);
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getTotalCount() > 0) {
			map = page.getResult().get(0);
		}
		map.put("m_umState", workerEval.getM_umState());
		
		return new ModelAndView("admin/project/workerEval/workerEval_update")
			.addObject("workerEval", map);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, WorkerEval workerEval) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerEvalService.findPageBySql(page, workerEval);
		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getTotalCount() > 0) {
			map = page.getResult().get(0);
		}
		map.put("m_umState", workerEval.getM_umState());
		
		return new ModelAndView("admin/project/workerEval/workerEval_update")
			.addObject("workerEval", map);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			WorkerEval workerEval){
		logger.debug("新增保存");
		try {
			workerEval.setLastUpdate(new Date());
			workerEval.setExpired("F");
			workerEval.setActionLog("ADD");
			
			this.workerEvalService.save(workerEval);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			WorkerEval workerEval){
		logger.debug("编辑保存");
		try {
			workerEval.setLastUpdate(new Date());
			workerEval.setEvaMan(this.getUserContext(request).getCzybh());
			workerEval.setActionLog("Edit");
			
			this.workerEvalService.update(workerEval);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, WorkerEval workerEval){
		logger.debug("删除开始");
		try{
			WorkerEval we = this.workerEvalService.get(WorkerEval.class, workerEval.getPK());
			if(null != we){
				this.workerEvalService.delete(we);
				ServletUtils.outPrintSuccess(request, response, "删除成功");
			} else {
				ServletUtils.outPrintFail(request, response, "删除失败,无该数据");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkerEval workerEval){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workerEvalService.findPageBySql(page, workerEval);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工人评价管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
