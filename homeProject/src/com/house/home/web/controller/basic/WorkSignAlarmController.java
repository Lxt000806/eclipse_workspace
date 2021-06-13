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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.GiftToken;
import com.house.home.entity.basic.WorkSignAlarm;
import com.house.home.service.basic.WorkSignAlarmService;

@Controller
@RequestMapping("/admin/workSignAlarm")
public class WorkSignAlarmController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkSignAlarmController.class);

	@Autowired
	private WorkSignAlarmService workSignAlarmService;

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
			HttpServletResponse response, WorkSignAlarm workSignAlarm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workSignAlarmService.findPageBySql(page, workSignAlarm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkSignAlarm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workSignAlarm/workSignAlarm_list");
	}
	/**
	 * 跳转到新增WorkSignAlarm页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增WorkSignAlarm页面");
		WorkSignAlarm workSignAlarm = null;
		if (StringUtils.isNotBlank(id)){
			workSignAlarm = workSignAlarmService.get(WorkSignAlarm.class, Integer.parseInt(id));
			workSignAlarm.setPk(null);
		}else{
			workSignAlarm = new WorkSignAlarm();
		}
		
		return new ModelAndView("admin/basic/workSignAlarm/workSignAlarm_save")
			.addObject("workSignAlarm", workSignAlarm);
	}
	/**
	 * 跳转到修改WorkSignAlarm页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改WorkSignAlarm页面");
		WorkSignAlarm workSignAlarm = null;
		if (StringUtils.isNotBlank(id)){
			workSignAlarm = workSignAlarmService.get(WorkSignAlarm.class, Integer.parseInt(id));
			if(workSignAlarm.getNotExistOffWorkType12()!=null){
				workSignAlarm.setNotExistOffWorkType12(workSignAlarm.getNotExistOffWorkType12().trim());
			}
			if(workSignAlarm.getConfirmPrjItem()!=null&&!"".equals(workSignAlarm.getConfirmPrjItem())){
				workSignAlarm.setIsConfirm("1");
			}else{
				workSignAlarm.setIsConfirm("0");
			}
		}else{
			workSignAlarm = new WorkSignAlarm();
		}
		
		
		return new ModelAndView("admin/basic/workSignAlarm/workSignAlarm_update")
			.addObject("workSignAlarm", workSignAlarm);
	}
	
	/**
	 * 跳转到查看WorkSignAlarm页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看WorkSignAlarm页面");
		WorkSignAlarm workSignAlarm = workSignAlarmService.get(WorkSignAlarm.class, Integer.parseInt(id));
		if(workSignAlarm.getNotExistOffWorkType12()!=null){
			workSignAlarm.setNotExistOffWorkType12(workSignAlarm.getNotExistOffWorkType12().trim());
		}
		if(workSignAlarm.getConfirmPrjItem()!=null&&!"".equals(workSignAlarm.getConfirmPrjItem())){
			workSignAlarm.setIsConfirm("1");
		}else{
			workSignAlarm.setIsConfirm("0");
		}
		return new ModelAndView("admin/basic/workSignAlarm/workSignAlarm_detail")
				.addObject("workSignAlarm", workSignAlarm);
	}
	/**
	 * 添加WorkSignAlarm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkSignAlarm workSignAlarm){
		logger.debug("添加WorkSignAlarm开始");
		try{
			workSignAlarm.setLastUpdate(new Date());
			workSignAlarm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workSignAlarm.setExpired("F");
			workSignAlarm.setActionLog("ADD");
			this.workSignAlarmService.save(workSignAlarm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加工人签到提醒失败");
		}
	}
	
	/**
	 * 修改WorkSignAlarm
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WorkSignAlarm workSignAlarm){
		logger.debug("修改WorkSignAlarm开始");
		try{
			workSignAlarm.setLastUpdate(new Date());
			workSignAlarm.setLastUpdatedBy(getUserContext(request).getCzybh());
			workSignAlarm.setActionLog("Edit");
			workSignAlarm.setExpired(workSignAlarm.getExpired());

			this.workSignAlarmService.update(workSignAlarm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改工人签到提醒失败");
		}
	}
	
	/**
	 * 删除WorkSignAlarm
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除WorkSignAlarm开始");
		try{
			WorkSignAlarm workSignAlarm  =workSignAlarmService.get(WorkSignAlarm.class, pk);
			workSignAlarmService.delete(workSignAlarm);
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除记录失败");
		}
	}

	/**
	 *WorkSignAlarm导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkSignAlarm workSignAlarm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workSignAlarmService.findPageBySql(page, workSignAlarm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人签到提醒_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
