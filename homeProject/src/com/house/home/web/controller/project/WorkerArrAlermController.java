package com.house.home.web.controller.project;

import java.util.Date;
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
import com.house.home.entity.project.WorkerArrAlerm;
import com.house.home.service.project.WorkerArrAlermService;

@Controller
@RequestMapping("/admin/workerArrAlarm")
public class WorkerArrAlermController extends BaseController{
	@Autowired
	private WorkerArrAlermService workerArrAlermService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WorkerArrAlerm workerArrAlerm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerArrAlermService.findPageBySql(page, workerArrAlerm);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,WorkerArrAlerm workerArrAlerm) throws Exception {
		return new ModelAndView("admin/project/workerArrAlerm/workerArrAlerm_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response,WorkerArrAlerm workerArrAlerm) throws Exception {
		return new ModelAndView("admin/project/workerArrAlerm/workerArrAlerm_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,WorkerArrAlerm workerArrAlerm) throws Exception {
		WorkerArrAlerm waa=workerArrAlermService.get(WorkerArrAlerm.class, workerArrAlerm.getPk());
		return new ModelAndView("admin/project/workerArrAlerm/workerArrAlerm_update").addObject("workerArrAlerm", waa);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,WorkerArrAlerm workerArrAlerm) throws Exception {
		WorkerArrAlerm waa=workerArrAlermService.get(WorkerArrAlerm.class, workerArrAlerm.getPk());
		return new ModelAndView("admin/project/workerArrAlerm/workerArrAlerm_view").addObject("workerArrAlerm", waa);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,WorkerArrAlerm workerArrAlerm){
		logger.debug("添加Item开始");
		try{
			workerArrAlerm.setLastUpdate(new Date());
			workerArrAlerm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workerArrAlerm.setExpired("F");
			workerArrAlerm.setActionLog("ADD");
			this.workerArrAlermService.save(workerArrAlerm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加工人安排提醒失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,WorkerArrAlerm workerArrAlerm){
		logger.debug("添加Item开始");
		try{
			workerArrAlerm.setLastUpdate(new Date());
			workerArrAlerm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workerArrAlerm.setExpired("F");
			workerArrAlerm.setActionLog("EDIT");
			this.workerArrAlermService.update(workerArrAlerm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑工人安排提醒失败");
		}
	}
	
	/**
	 * @Description: TODO 增加删除按钮
	 * @author	created by zb
	 * @date	2018-7-27--上午10:46:18
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,Integer pk){
		logger.debug("删除Item开始");
		try{
			WorkerArrAlerm workerArrAlerm = workerArrAlermService.get(WorkerArrAlerm.class, pk);
			workerArrAlermService.delete(workerArrAlerm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除工人安排提醒失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			 WorkerArrAlerm workerArrAlerm){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		workerArrAlermService.findPageBySql(page, workerArrAlerm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人安排提醒表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
