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
import com.house.home.entity.workflow.ActVariable;
import com.house.home.service.workflow.ActVariableService;

@Controller
@RequestMapping("/admin/actVariable")
public class ActVariableController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActVariableController.class);

	@Autowired
	private ActVariableService actVariableService;

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
			HttpServletResponse response, ActVariable actVariable) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actVariableService.findPageBySql(page, actVariable);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActVariable列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actVariable/actVariable_list");
	}
	/**
	 * ActVariable查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actVariable/actVariable_code");
	}
	/**
	 * 跳转到新增ActVariable页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActVariable页面");
		ActVariable actVariable = null;
		if (StringUtils.isNotBlank(id)){
			actVariable = actVariableService.get(ActVariable.class, id);
			actVariable.setId(null);
		}else{
			actVariable = new ActVariable();
		}
		
		return new ModelAndView("admin/workflow/actVariable/actVariable_save")
			.addObject("actVariable", actVariable);
	}
	/**
	 * 跳转到修改ActVariable页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActVariable页面");
		ActVariable actVariable = null;
		if (StringUtils.isNotBlank(id)){
			actVariable = actVariableService.get(ActVariable.class, id);
		}else{
			actVariable = new ActVariable();
		}
		
		return new ModelAndView("admin/workflow/actVariable/actVariable_update")
			.addObject("actVariable", actVariable);
	}
	
	/**
	 * 跳转到查看ActVariable页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActVariable页面");
		ActVariable actVariable = actVariableService.get(ActVariable.class, id);
		
		return new ModelAndView("admin/workflow/actVariable/actVariable_detail")
				.addObject("actVariable", actVariable);
	}
	/**
	 * 添加ActVariable
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActVariable actVariable){
		logger.debug("添加ActVariable开始");
		try{
			String str = actVariableService.getSeqNo("ACT_RU_VARIABLE");
			actVariable.setId(str);
			this.actVariableService.save(actVariable);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActVariable失败");
		}
	}
	
	/**
	 * 修改ActVariable
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActVariable actVariable){
		logger.debug("修改ActVariable开始");
		try{
			this.actVariableService.update(actVariable);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActVariable失败");
		}
	}
	
	/**
	 * 删除ActVariable
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActVariable开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActVariable编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActVariable actVariable = actVariableService.get(ActVariable.class, deleteId);
				if(actVariable == null)
					continue;
				actVariableService.update(actVariable);
			}
		}
		logger.debug("删除ActVariable IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActVariable导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActVariable actVariable){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actVariableService.findPageBySql(page, actVariable);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActVariable_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
