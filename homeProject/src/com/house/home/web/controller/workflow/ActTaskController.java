package com.house.home.web.controller.workflow;

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
import com.house.home.entity.workflow.ActTask;
import com.house.home.service.workflow.ActTaskService;

@Controller
@RequestMapping("/admin/actTask")
public class ActTaskController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActTaskController.class);

	@Autowired
	private ActTaskService actTaskService;

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
			HttpServletResponse response, ActTask actTask) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actTaskService.findPageBySql(page, actTask);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActTask列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actTask/actTask_list");
	}
	/**
	 * ActTask查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actTask/actTask_code");
	}
	/**
	 * 跳转到新增ActTask页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActTask页面");
		ActTask actTask = null;
		if (StringUtils.isNotBlank(id)){
			actTask = actTaskService.get(ActTask.class, id);
			actTask.setId(null);
		}else{
			actTask = new ActTask();
		}
		
		return new ModelAndView("admin/workflow/actTask/actTask_save")
			.addObject("actTask", actTask);
	}
	/**
	 * 跳转到修改ActTask页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActTask页面");
		ActTask actTask = null;
		if (StringUtils.isNotBlank(id)){
			actTask = actTaskService.get(ActTask.class, id);
		}else{
			actTask = new ActTask();
		}
		
		return new ModelAndView("admin/workflow/actTask/actTask_update")
			.addObject("actTask", actTask);
	}
	
	/**
	 * 跳转到查看ActTask页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActTask页面");
		ActTask actTask = actTaskService.get(ActTask.class, id);
		
		return new ModelAndView("admin/workflow/actTask/actTask_detail")
				.addObject("actTask", actTask);
	}
	/**
	 * 添加ActTask
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActTask actTask){
		logger.debug("添加ActTask开始");
		try{
			String str = actTaskService.getSeqNo("ACT_RU_TASK");
			actTask.setId(str);
			this.actTaskService.save(actTask);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActTask失败");
		}
	}
	
	/**
	 * 修改ActTask
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActTask actTask){
		logger.debug("修改ActTask开始");
		try{
			this.actTaskService.update(actTask);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActTask失败");
		}
	}
	
	/**
	 * 删除ActTask
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActTask开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActTask编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActTask actTask = actTaskService.get(ActTask.class, deleteId);
				if(actTask == null)
					continue;
				actTaskService.update(actTask);
			}
		}
		logger.debug("删除ActTask IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActTask导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActTask actTask){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actTaskService.findPageBySql(page, actTask);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActTask_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
