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
import com.house.home.entity.workflow.ActExecution;
import com.house.home.service.workflow.ActExecutionService;

@Controller
@RequestMapping("/admin/actExecution")
public class ActExecutionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActExecutionController.class);

	@Autowired
	private ActExecutionService actExecutionService;

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
			HttpServletResponse response, ActExecution actExecution) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actExecutionService.findPageBySql(page, actExecution);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActExecution列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actExecution/actExecution_list");
	}
	/**
	 * ActExecution查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actExecution/actExecution_code");
	}
	/**
	 * 跳转到新增ActExecution页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActExecution页面");
		ActExecution actExecution = null;
		if (StringUtils.isNotBlank(id)){
			actExecution = actExecutionService.get(ActExecution.class, id);
			actExecution.setId(null);
		}else{
			actExecution = new ActExecution();
		}
		
		return new ModelAndView("admin/workflow/actExecution/actExecution_save")
			.addObject("actExecution", actExecution);
	}
	/**
	 * 跳转到修改ActExecution页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActExecution页面");
		ActExecution actExecution = null;
		if (StringUtils.isNotBlank(id)){
			actExecution = actExecutionService.get(ActExecution.class, id);
		}else{
			actExecution = new ActExecution();
		}
		
		return new ModelAndView("admin/workflow/actExecution/actExecution_update")
			.addObject("actExecution", actExecution);
	}
	
	/**
	 * 跳转到查看ActExecution页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActExecution页面");
		ActExecution actExecution = actExecutionService.get(ActExecution.class, id);
		
		return new ModelAndView("admin/workflow/actExecution/actExecution_detail")
				.addObject("actExecution", actExecution);
	}
	/**
	 * 添加ActExecution
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActExecution actExecution){
		logger.debug("添加ActExecution开始");
		try{
			String str = actExecutionService.getSeqNo("ACT_RU_EXECUTION");
			actExecution.setId(str);
			this.actExecutionService.save(actExecution);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActExecution失败");
		}
	}
	
	/**
	 * 修改ActExecution
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActExecution actExecution){
		logger.debug("修改ActExecution开始");
		try{
			this.actExecutionService.update(actExecution);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActExecution失败");
		}
	}
	
	/**
	 * 删除ActExecution
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActExecution开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActExecution编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActExecution actExecution = actExecutionService.get(ActExecution.class, deleteId);
				if(actExecution == null)
					continue;
				actExecutionService.update(actExecution);
			}
		}
		logger.debug("删除ActExecution IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActExecution导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActExecution actExecution){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actExecutionService.findPageBySql(page, actExecution);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActExecution_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
