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
import com.house.home.entity.workflow.ActDeployment;
import com.house.home.service.workflow.ActDeploymentService;

@Controller
@RequestMapping("/admin/actDeployment")
public class ActDeploymentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActDeploymentController.class);

	@Autowired
	private ActDeploymentService actDeploymentService;

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
			HttpServletResponse response, ActDeployment actDeployment) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actDeploymentService.findPageBySql(page, actDeployment);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActDeployment列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actDeployment/actDeployment_list");
	}
	/**
	 * ActDeployment查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actDeployment/actDeployment_code");
	}
	/**
	 * 跳转到新增ActDeployment页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActDeployment页面");
		ActDeployment actDeployment = null;
		if (StringUtils.isNotBlank(id)){
			actDeployment = actDeploymentService.get(ActDeployment.class, id);
			actDeployment.setId(null);
		}else{
			actDeployment = new ActDeployment();
		}
		
		return new ModelAndView("admin/workflow/actDeployment/actDeployment_save")
			.addObject("actDeployment", actDeployment);
	}
	/**
	 * 跳转到修改ActDeployment页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActDeployment页面");
		ActDeployment actDeployment = null;
		if (StringUtils.isNotBlank(id)){
			actDeployment = actDeploymentService.get(ActDeployment.class, id);
		}else{
			actDeployment = new ActDeployment();
		}
		
		return new ModelAndView("admin/workflow/actDeployment/actDeployment_update")
			.addObject("actDeployment", actDeployment);
	}
	
	/**
	 * 跳转到查看ActDeployment页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActDeployment页面");
		ActDeployment actDeployment = actDeploymentService.get(ActDeployment.class, id);
		
		return new ModelAndView("admin/workflow/actDeployment/actDeployment_detail")
				.addObject("actDeployment", actDeployment);
	}
	/**
	 * 添加ActDeployment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActDeployment actDeployment){
		logger.debug("添加ActDeployment开始");
		try{
			String str = actDeploymentService.getSeqNo("ACT_RE_DEPLOYMENT");
			actDeployment.setId(str);
			this.actDeploymentService.save(actDeployment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActDeployment失败");
		}
	}
	
	/**
	 * 修改ActDeployment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActDeployment actDeployment){
		logger.debug("修改ActDeployment开始");
		try{
			this.actDeploymentService.update(actDeployment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActDeployment失败");
		}
	}
	
	/**
	 * 删除ActDeployment
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActDeployment开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActDeployment编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActDeployment actDeployment = actDeploymentService.get(ActDeployment.class, deleteId);
				if(actDeployment == null)
					continue;
				actDeploymentService.update(actDeployment);
			}
		}
		logger.debug("删除ActDeployment IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActDeployment导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActDeployment actDeployment){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actDeploymentService.findPageBySql(page, actDeployment);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActDeployment_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
