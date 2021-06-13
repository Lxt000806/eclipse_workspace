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
import com.house.home.entity.basic.ProjectManQltFeeWithHoldRule;
import com.house.home.service.basic.ProjectManQltFeeWithHoldRuleService;

@Controller
@RequestMapping("/admin/projectManQltFeeWithHoldRule")
public class ProjectManQltFeeWithHoldRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectManQltFeeWithHoldRuleController.class);

	@Autowired
	private ProjectManQltFeeWithHoldRuleService projectManQltFeeWithHoldRuleService;
	

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
			HttpServletResponse response, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		projectManQltFeeWithHoldRuleService.findPageBySql(page, projectManQltFeeWithHoldRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * projectManQltFeeWithHoldRule列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/projectManQltFeeWithHoldRule/projectManQltFeeWithHoldRule_list");
	}
	
	
	/**
	 * 跳转到新增projectManQltFeeWithHoldRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增projectManQltFeeWithHoldRule页面");
		ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule = null;
		if (StringUtils.isNotBlank(id)){
			projectManQltFeeWithHoldRule = projectManQltFeeWithHoldRuleService.get(ProjectManQltFeeWithHoldRule.class, Integer.parseInt(id));
			projectManQltFeeWithHoldRule.setPk(null);
		}else{
			projectManQltFeeWithHoldRule = new ProjectManQltFeeWithHoldRule();
		}
		projectManQltFeeWithHoldRule.setM_umState("A");
		return new ModelAndView("admin/basic/projectManQltFeeWithHoldRule/projectManQltFeeWithHoldRule_save")
			.addObject("projectManQltFeeWithHoldRule", projectManQltFeeWithHoldRule);
	}
	
	/**
	 * 跳转到修改projectManQltFeeWithHoldRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConDayCalcRule页面");
		ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule = null;
		if (StringUtils.isNotBlank(id)){
			projectManQltFeeWithHoldRule = projectManQltFeeWithHoldRuleService.get(ProjectManQltFeeWithHoldRule.class, Integer.parseInt(id));
		}else{
			projectManQltFeeWithHoldRule = new ProjectManQltFeeWithHoldRule();
		}
		projectManQltFeeWithHoldRule.setM_umState("M");
		return new ModelAndView("admin/basic/projectManQltFeeWithHoldRule/projectManQltFeeWithHoldRule_save")
			.addObject("projectManQltFeeWithHoldRule", projectManQltFeeWithHoldRule);
	}
	
	/**
	 * 跳转到查看projectManQltFeeWithHoldRule页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ProjectManQltFeeWithHoldRule页面");
		ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule = projectManQltFeeWithHoldRuleService.get(ProjectManQltFeeWithHoldRule.class, Integer.parseInt(id));
		projectManQltFeeWithHoldRule.setM_umState("V");
		return new ModelAndView("admin/basic/projectManQltFeeWithHoldRule/projectManQltFeeWithHoldRule_save")
				.addObject("projectManQltFeeWithHoldRule", projectManQltFeeWithHoldRule);
	}
	/**
	 * 添加projectManQltFeeWithHoldRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public void doSave(HttpServletRequest request, HttpServletResponse response, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule){
		logger.debug("添加projectManQltFeeWithHoldRule开始");
		try{
			projectManQltFeeWithHoldRule.setActionLog("ADD");
			projectManQltFeeWithHoldRule.setExpired("F");
			projectManQltFeeWithHoldRule.setLastUpdate(new Date());
			projectManQltFeeWithHoldRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.projectManQltFeeWithHoldRuleService.save(projectManQltFeeWithHoldRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加失败");
		}
	}
	
	/**
	 * 修改ProjectManQltFeeWithHoldRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule){
		logger.debug("修改projectManQltFeeWithHoldRule开始");
		try{
			projectManQltFeeWithHoldRule.setActionLog("EDIT");
			projectManQltFeeWithHoldRule.setLastUpdate(new Date());
			projectManQltFeeWithHoldRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.projectManQltFeeWithHoldRuleService.update(projectManQltFeeWithHoldRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 删除ConDayCalcRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除projectManQltFeeWithHoldRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "项目经理质保金扣款规则pk不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule = projectManQltFeeWithHoldRuleService.get(ProjectManQltFeeWithHoldRule.class, Integer.parseInt(deleteId));
				if(projectManQltFeeWithHoldRule == null)
					continue;
				projectManQltFeeWithHoldRuleService.delete(projectManQltFeeWithHoldRule);
			}
		}
		logger.debug("删除projectManQltFeeWithHoldRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ConDayCalcRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		projectManQltFeeWithHoldRuleService.findPageBySql(page, projectManQltFeeWithHoldRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"项目经理质保金扣款规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
