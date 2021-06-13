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
import com.house.home.entity.workflow.ActJob;
import com.house.home.service.workflow.ActJobService;

@Controller
@RequestMapping("/admin/actJob")
public class ActJobController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActJobController.class);

	@Autowired
	private ActJobService actJobService;

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
			HttpServletResponse response, ActJob actJob) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actJobService.findPageBySql(page, actJob);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActJob列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actJob/actJob_list");
	}
	/**
	 * ActJob查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actJob/actJob_code");
	}
	/**
	 * 跳转到新增ActJob页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActJob页面");
		ActJob actJob = null;
		if (StringUtils.isNotBlank(id)){
			actJob = actJobService.get(ActJob.class, id);
			actJob.setId(null);
		}else{
			actJob = new ActJob();
		}
		
		return new ModelAndView("admin/workflow/actJob/actJob_save")
			.addObject("actJob", actJob);
	}
	/**
	 * 跳转到修改ActJob页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActJob页面");
		ActJob actJob = null;
		if (StringUtils.isNotBlank(id)){
			actJob = actJobService.get(ActJob.class, id);
		}else{
			actJob = new ActJob();
		}
		
		return new ModelAndView("admin/workflow/actJob/actJob_update")
			.addObject("actJob", actJob);
	}
	
	/**
	 * 跳转到查看ActJob页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActJob页面");
		ActJob actJob = actJobService.get(ActJob.class, id);
		
		return new ModelAndView("admin/workflow/actJob/actJob_detail")
				.addObject("actJob", actJob);
	}
	/**
	 * 添加ActJob
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActJob actJob){
		logger.debug("添加ActJob开始");
		try{
			String str = actJobService.getSeqNo("ACT_RU_JOB");
			actJob.setId(str);
			this.actJobService.save(actJob);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActJob失败");
		}
	}
	
	/**
	 * 修改ActJob
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActJob actJob){
		logger.debug("修改ActJob开始");
		try{
			this.actJobService.update(actJob);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActJob失败");
		}
	}
	
	/**
	 * 删除ActJob
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActJob开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActJob编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActJob actJob = actJobService.get(ActJob.class, deleteId);
				if(actJob == null)
					continue;
				actJobService.update(actJob);
			}
		}
		logger.debug("删除ActJob IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActJob导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActJob actJob){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actJobService.findPageBySql(page, actJob);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActJob_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
