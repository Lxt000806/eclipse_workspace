package com.house.home.web.controller.project;

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
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.project.ConfItemType;
import com.house.home.entity.project.JobType;
import com.house.home.entity.project.JobTypeConfItemType;
import com.house.home.service.project.ConfItemTypeService;
import com.house.home.service.project.JobTypeConfItemTypeService;
import com.house.home.service.project.JobTypeService;

@Controller
@RequestMapping("/admin/jobTypeConfItemType")
public class JobTypeConfItemTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(JobTypeConfItemTypeController.class);

	@Autowired
	private JobTypeConfItemTypeService jobTypeConfItemTypeService;
	
	@Autowired
	private JobTypeService jobTypeService;
	
	@Autowired
	private ConfItemTypeService confItemTypeService;

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
			HttpServletResponse response, JobTypeConfItemType jobTypeConfItemType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		jobTypeConfItemTypeService.findPageBySql(page, jobTypeConfItemType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * JobTypeConfItemType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/jobTypeConfItemType/jobTypeConfItemType_list");
	}
	/**
	 * 跳转到新增JobTypeConfItemType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增JobTypeConfItemType页面");
		return new ModelAndView("admin/basic/jobTypeConfItemType/jobTypeConfItemType_save");
	}
	/**
	 * 跳转到修改JobTypeConfItemType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改JobTypeConfItemType页面");
		JobTypeConfItemType jobTypeConfItemType = jobTypeConfItemTypeService.get(JobTypeConfItemType.class, pk);
		System.out.println(jobTypeConfItemType.getConfItemType());
		return new ModelAndView("admin/basic/jobTypeConfItemType/jobTypeConfItemType_update")
			.addObject("jobTypeConfItemType", jobTypeConfItemType);
	}
	
	/**
	 * 跳转到查看JobTypeConfItemType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看JobTypeConfItemType页面");
		JobTypeConfItemType jobTypeConfItemType = jobTypeConfItemTypeService.get(JobTypeConfItemType.class, pk);
		return new ModelAndView("admin/basic/jobTypeConfItemType/jobTypeConfItemType_detail")
				.addObject("jobTypeConfItemType", jobTypeConfItemType);
	}
	/**
	 * 添加JobTypeConfItemType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, JobTypeConfItemType jobTypeConfItemType){
		logger.debug("添加JobTypeConfItemType开始");
		try{
			List list = this.jobTypeConfItemTypeService.getByJobTypeAndConfItemType(jobTypeConfItemType);
			if(list != null && list.size() != 0){
				JobType jobType = jobTypeService.get(JobType.class, jobTypeConfItemType.getJobType());
				ConfItemType confItemType = jobTypeService.get(ConfItemType.class, jobTypeConfItemType.getConfItemType());
				ServletUtils.outPrintFail(request, response,
						jobType.getDescr()+"和"+confItemType.getDescr()+"重复了");
				return;
			}
			jobTypeConfItemType.setActionLog("ADD");
			jobTypeConfItemType.setExpired("F");
			jobTypeConfItemType.setLastUpdate(new Date());
			jobTypeConfItemType.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			this.jobTypeConfItemTypeService.save(jobTypeConfItemType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加JobTypeConfItemType失败");
		}
	}
	
	/**
	 * 修改JobTypeConfItemType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, JobTypeConfItemType jobTypeConfItemType){
		logger.debug("修改JobTypeConfItemType开始");
		try{
			List list = this.jobTypeConfItemTypeService.getByJobTypeAndConfItemType(jobTypeConfItemType);
			if(list != null && list.size() != 0){
				JobType jobType = jobTypeService.get(JobType.class, jobTypeConfItemType.getJobType());
				ConfItemType confItemType = jobTypeService.get(ConfItemType.class, jobTypeConfItemType.getConfItemType());
				ServletUtils.outPrintFail(request, response,
						jobType.getDescr()+"和"+confItemType.getDescr()+"重复了");
				return;
			}
			jobTypeConfItemType.setActionLog("EDIT");
			jobTypeConfItemType.setLastUpdate(new Date());
			jobTypeConfItemType.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.jobTypeConfItemTypeService.update(jobTypeConfItemType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改JobTypeConfItemType失败");
		}
	}

	/**
	 *JobTypeConfItemType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, JobTypeConfItemType jobTypeConfItemType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		jobTypeConfItemTypeService.findPageBySql(page, jobTypeConfItemType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"任务材料分类对应表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
