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
import com.house.home.entity.workflow.ActByteArray;
import com.house.home.service.workflow.ActByteArrayService;

@Controller
@RequestMapping("/admin/actByteArray")
public class ActByteArrayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActByteArrayController.class);

	@Autowired
	private ActByteArrayService actByteArrayService;

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
			HttpServletResponse response, ActByteArray actByteArray) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actByteArrayService.findPageBySql(page, actByteArray);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActByteArray列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actByteArray/actByteArray_list");
	}
	/**
	 * ActByteArray查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actByteArray/actByteArray_code");
	}
	/**
	 * 跳转到新增ActByteArray页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActByteArray页面");
		ActByteArray actByteArray = null;
		if (StringUtils.isNotBlank(id)){
			actByteArray = actByteArrayService.get(ActByteArray.class, id);
			actByteArray.setId(null);
		}else{
			actByteArray = new ActByteArray();
		}
		
		return new ModelAndView("admin/workflow/actByteArray/actByteArray_save")
			.addObject("actByteArray", actByteArray);
	}
	/**
	 * 跳转到修改ActByteArray页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActByteArray页面");
		ActByteArray actByteArray = null;
		if (StringUtils.isNotBlank(id)){
			actByteArray = actByteArrayService.get(ActByteArray.class, id);
		}else{
			actByteArray = new ActByteArray();
		}
		
		return new ModelAndView("admin/workflow/actByteArray/actByteArray_update")
			.addObject("actByteArray", actByteArray);
	}
	
	/**
	 * 跳转到查看ActByteArray页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActByteArray页面");
		ActByteArray actByteArray = actByteArrayService.get(ActByteArray.class, id);
		
		return new ModelAndView("admin/workflow/actByteArray/actByteArray_detail")
				.addObject("actByteArray", actByteArray);
	}
	/**
	 * 添加ActByteArray
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActByteArray actByteArray){
		logger.debug("添加ActByteArray开始");
		try{
			String str = actByteArrayService.getSeqNo("ACT_GE_BYTEARRAY");
			actByteArray.setId(str);
			this.actByteArrayService.save(actByteArray);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActByteArray失败");
		}
	}
	
	/**
	 * 修改ActByteArray
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActByteArray actByteArray){
		logger.debug("修改ActByteArray开始");
		try{
			this.actByteArrayService.update(actByteArray);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActByteArray失败");
		}
	}
	
	/**
	 * 删除ActByteArray
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActByteArray开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActByteArray编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActByteArray actByteArray = actByteArrayService.get(ActByteArray.class, deleteId);
				if(actByteArray == null)
					continue;
				actByteArrayService.update(actByteArray);
			}
		}
		logger.debug("删除ActByteArray IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActByteArray导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActByteArray actByteArray){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actByteArrayService.findPageBySql(page, actByteArray);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActByteArray_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
