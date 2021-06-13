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
import com.house.home.entity.workflow.ActProperty;
import com.house.home.service.workflow.ActPropertyService;

@Controller
@RequestMapping("/admin/actProperty")
public class ActPropertyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActPropertyController.class);

	@Autowired
	private ActPropertyService actPropertyService;

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
			HttpServletResponse response, ActProperty actProperty) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actPropertyService.findPageBySql(page, actProperty);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActProperty列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actProperty/actProperty_list");
	}
	/**
	 * ActProperty查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actProperty/actProperty_code");
	}
	/**
	 * 跳转到新增ActProperty页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActProperty页面");
		ActProperty actProperty = null;
		if (StringUtils.isNotBlank(id)){
			actProperty = actPropertyService.get(ActProperty.class, id);
			actProperty.setName(null);
		}else{
			actProperty = new ActProperty();
		}
		
		return new ModelAndView("admin/workflow/actProperty/actProperty_save")
			.addObject("actProperty", actProperty);
	}
	/**
	 * 跳转到修改ActProperty页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActProperty页面");
		ActProperty actProperty = null;
		if (StringUtils.isNotBlank(id)){
			actProperty = actPropertyService.get(ActProperty.class, id);
		}else{
			actProperty = new ActProperty();
		}
		
		return new ModelAndView("admin/workflow/actProperty/actProperty_update")
			.addObject("actProperty", actProperty);
	}
	
	/**
	 * 跳转到查看ActProperty页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActProperty页面");
		ActProperty actProperty = actPropertyService.get(ActProperty.class, id);
		
		return new ModelAndView("admin/workflow/actProperty/actProperty_detail")
				.addObject("actProperty", actProperty);
	}
	/**
	 * 添加ActProperty
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActProperty actProperty){
		logger.debug("添加ActProperty开始");
		try{
			String str = actPropertyService.getSeqNo("ACT_GE_PROPERTY");
			actProperty.setName(str);
			this.actPropertyService.save(actProperty);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActProperty失败");
		}
	}
	
	/**
	 * 修改ActProperty
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActProperty actProperty){
		logger.debug("修改ActProperty开始");
		try{
			this.actPropertyService.update(actProperty);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActProperty失败");
		}
	}
	
	/**
	 * 删除ActProperty
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActProperty开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActProperty编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActProperty actProperty = actPropertyService.get(ActProperty.class, deleteId);
				if(actProperty == null)
					continue;
				actPropertyService.update(actProperty);
			}
		}
		logger.debug("删除ActProperty IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActProperty导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActProperty actProperty){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actPropertyService.findPageBySql(page, actProperty);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActProperty_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
