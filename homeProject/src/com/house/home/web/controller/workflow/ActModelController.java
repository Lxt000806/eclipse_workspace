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
import com.house.home.entity.workflow.ActModel;
import com.house.home.service.workflow.ActModelService;

@Controller
@RequestMapping("/admin/actModel")
public class ActModelController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActModelController.class);

	@Autowired
	private ActModelService actModelService;

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
			HttpServletResponse response, ActModel actModel) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actModelService.findPageBySql(page, actModel);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActModel列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actModel/actModel_list");
	}
	/**
	 * ActModel查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actModel/actModel_code");
	}
	/**
	 * 跳转到新增ActModel页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActModel页面");
		ActModel actModel = null;
		if (StringUtils.isNotBlank(id)){
			actModel = actModelService.get(ActModel.class, id);
			actModel.setId(null);
		}else{
			actModel = new ActModel();
		}
		
		return new ModelAndView("admin/workflow/actModel/actModel_save")
			.addObject("actModel", actModel);
	}
	/**
	 * 跳转到修改ActModel页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActModel页面");
		ActModel actModel = null;
		if (StringUtils.isNotBlank(id)){
			actModel = actModelService.get(ActModel.class, id);
		}else{
			actModel = new ActModel();
		}
		
		return new ModelAndView("admin/workflow/actModel/actModel_update")
			.addObject("actModel", actModel);
	}
	
	/**
	 * 跳转到查看ActModel页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActModel页面");
		ActModel actModel = actModelService.get(ActModel.class, id);
		
		return new ModelAndView("admin/workflow/actModel/actModel_detail")
				.addObject("actModel", actModel);
	}
	/**
	 * 添加ActModel
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActModel actModel){
		logger.debug("添加ActModel开始");
		try{
			String str = actModelService.getSeqNo("ACT_RE_MODEL");
			actModel.setId(str);
			this.actModelService.save(actModel);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActModel失败");
		}
	}
	
	/**
	 * 修改ActModel
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActModel actModel){
		logger.debug("修改ActModel开始");
		try{
			this.actModelService.update(actModel);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActModel失败");
		}
	}
	
	/**
	 * 删除ActModel
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActModel开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActModel编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActModel actModel = actModelService.get(ActModel.class, deleteId);
				if(actModel == null)
					continue;
				actModelService.update(actModel);
			}
		}
		logger.debug("删除ActModel IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActModel导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActModel actModel){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actModelService.findPageBySql(page, actModel);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActModel_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
