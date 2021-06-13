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
import com.house.home.entity.workflow.ActEventSubscr;
import com.house.home.service.workflow.ActEventSubscrService;

@Controller
@RequestMapping("/admin/actEventSubscr")
public class ActEventSubscrController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActEventSubscrController.class);

	@Autowired
	private ActEventSubscrService actEventSubscrService;

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
			HttpServletResponse response, ActEventSubscr actEventSubscr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actEventSubscrService.findPageBySql(page, actEventSubscr);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActEventSubscr列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actEventSubscr/actEventSubscr_list");
	}
	/**
	 * ActEventSubscr查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actEventSubscr/actEventSubscr_code");
	}
	/**
	 * 跳转到新增ActEventSubscr页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActEventSubscr页面");
		ActEventSubscr actEventSubscr = null;
		if (StringUtils.isNotBlank(id)){
			actEventSubscr = actEventSubscrService.get(ActEventSubscr.class, id);
			actEventSubscr.setId(null);
		}else{
			actEventSubscr = new ActEventSubscr();
		}
		
		return new ModelAndView("admin/workflow/actEventSubscr/actEventSubscr_save")
			.addObject("actEventSubscr", actEventSubscr);
	}
	/**
	 * 跳转到修改ActEventSubscr页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActEventSubscr页面");
		ActEventSubscr actEventSubscr = null;
		if (StringUtils.isNotBlank(id)){
			actEventSubscr = actEventSubscrService.get(ActEventSubscr.class, id);
		}else{
			actEventSubscr = new ActEventSubscr();
		}
		
		return new ModelAndView("admin/workflow/actEventSubscr/actEventSubscr_update")
			.addObject("actEventSubscr", actEventSubscr);
	}
	
	/**
	 * 跳转到查看ActEventSubscr页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActEventSubscr页面");
		ActEventSubscr actEventSubscr = actEventSubscrService.get(ActEventSubscr.class, id);
		
		return new ModelAndView("admin/workflow/actEventSubscr/actEventSubscr_detail")
				.addObject("actEventSubscr", actEventSubscr);
	}
	/**
	 * 添加ActEventSubscr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActEventSubscr actEventSubscr){
		logger.debug("添加ActEventSubscr开始");
		try{
			String str = actEventSubscrService.getSeqNo("ACT_RU_EVENT_SUBSCR");
			actEventSubscr.setId(str);
			this.actEventSubscrService.save(actEventSubscr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActEventSubscr失败");
		}
	}
	
	/**
	 * 修改ActEventSubscr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActEventSubscr actEventSubscr){
		logger.debug("修改ActEventSubscr开始");
		try{
			this.actEventSubscrService.update(actEventSubscr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActEventSubscr失败");
		}
	}
	
	/**
	 * 删除ActEventSubscr
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActEventSubscr开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActEventSubscr编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActEventSubscr actEventSubscr = actEventSubscrService.get(ActEventSubscr.class, deleteId);
				if(actEventSubscr == null)
					continue;
				actEventSubscrService.update(actEventSubscr);
			}
		}
		logger.debug("删除ActEventSubscr IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActEventSubscr导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActEventSubscr actEventSubscr){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actEventSubscrService.findPageBySql(page, actEventSubscr);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActEventSubscr_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
