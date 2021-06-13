package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.house.home.entity.project.JobType;
import com.house.home.service.project.JobTypeService;

@Controller
@RequestMapping("/admin/jobType")
public class JobTypeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(JobTypeController.class);

	@Autowired
	private JobTypeService jobTypeService;

	/**
	 * ERP主页面查询
	 * @author	created by zb
	 * @date	2019-9-4--上午10:06:07
	 * @param request
	 * @param response
	 * @param jobType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, JobType jobType) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		jobTypeService.findERPPageBySql(page, jobType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * JobType列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/jobType/jobType_list");
	}
	/**
	 * JobType查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/jobType/jobType_code");
	}
	/**
	 * 跳转到新增JobType页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			JobType jobType){
		logger.debug("跳转到新增JobType页面");
		jobType.setIsMaterialSendJob("0");  
		return new ModelAndView("admin/basic/jobType/jobType_save")
			.addObject("jobType", jobType);
	}
	/**
	 * 跳转到修改JobType页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到修改JobType页面");
		JobType jobType = null;
		if (StringUtils.isNotBlank(id)){
			jobType = jobTypeService.get(JobType.class, id);
		}else{
			jobType = new JobType();
		}
		jobType.setM_umState(m_umState);
		jobType.setItemType1(jobType.getItemType1().trim());
		if (StringUtils.isNotBlank(jobType.getRole())) {
			jobType.setRole(jobType.getRole().trim());
		}
		return new ModelAndView("admin/basic/jobType/jobType_save")
			.addObject("jobType", jobType);
	}
	
	/**
	 * 跳转到查看JobType页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id, String m_umState){
		logger.debug("跳转到查看JobType页面");
		JobType jobType = null;
		if (StringUtils.isNotBlank(id)){
			jobType = jobTypeService.get(JobType.class, id);
		}else{
			jobType = new JobType();
		}
		jobType.setM_umState(m_umState);
		jobType.setItemType1(jobType.getItemType1().trim());
		if (StringUtils.isNotBlank(jobType.getRole())) {
			jobType.setRole(jobType.getRole().trim());
		}
		return new ModelAndView("admin/basic/jobType/jobType_save")
				.addObject("jobType", jobType);
	}
	/**
	 * 添加JobType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, JobType jobType){
		logger.debug("添加JobType开始");
		try{
			JobType jt = this.jobTypeService.get(JobType.class, jobType.getCode());
			if (null != jt) {
				ServletUtils.outPrintFail(request, response, "编号重复");
			}
			jobType.setLastUpdate(new Date());
			jobType.setLastUpdatedBy(getUserContext(request).getCzybh());
			jobType.setExpired("F");
			jobType.setActionLog("ADD");
			this.jobTypeService.save(jobType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加JobType失败");
		}
	}
	/**
	 * 修改JobType
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, JobType jobType){
		logger.debug("修改JobType开始");
		try{
			jobType.setLastUpdate(new Date());
			jobType.setLastUpdatedBy(getUserContext(request).getCzybh());
			jobType.setActionLog("Edit");
			this.jobTypeService.update(jobType);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改JobType失败");
		}
	}
	/**
	 * 删除JobType
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除JobType开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "JobType编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				JobType jobType = jobTypeService.get(JobType.class, deleteId);
				if(jobType == null)
					continue;
				jobType.setExpired("T");
				jobTypeService.update(jobType);
			}
		}
		logger.debug("删除JobType IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	/**
	 *JobType导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, JobType jobType){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		jobTypeService.findERPPageBySql(page, jobType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"任务类型管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
