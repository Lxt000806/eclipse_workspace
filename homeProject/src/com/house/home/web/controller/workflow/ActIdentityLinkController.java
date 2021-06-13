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
import com.house.home.entity.workflow.ActIdentityLink;
import com.house.home.service.workflow.ActIdentityLinkService;

@Controller
@RequestMapping("/admin/actIdentityLink")
public class ActIdentityLinkController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActIdentityLinkController.class);

	@Autowired
	private ActIdentityLinkService actIdentityLinkService;

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
			HttpServletResponse response, ActIdentityLink actIdentityLink) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actIdentityLinkService.findPageBySql(page, actIdentityLink);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActIdentityLink列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actIdentityLink/actIdentityLink_list");
	}
	/**
	 * ActIdentityLink查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actIdentityLink/actIdentityLink_code");
	}
	/**
	 * 跳转到新增ActIdentityLink页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActIdentityLink页面");
		ActIdentityLink actIdentityLink = null;
		if (StringUtils.isNotBlank(id)){
			actIdentityLink = actIdentityLinkService.get(ActIdentityLink.class, id);
			actIdentityLink.setId(null);
		}else{
			actIdentityLink = new ActIdentityLink();
		}
		
		return new ModelAndView("admin/workflow/actIdentityLink/actIdentityLink_save")
			.addObject("actIdentityLink", actIdentityLink);
	}
	/**
	 * 跳转到修改ActIdentityLink页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActIdentityLink页面");
		ActIdentityLink actIdentityLink = null;
		if (StringUtils.isNotBlank(id)){
			actIdentityLink = actIdentityLinkService.get(ActIdentityLink.class, id);
		}else{
			actIdentityLink = new ActIdentityLink();
		}
		
		return new ModelAndView("admin/workflow/actIdentityLink/actIdentityLink_update")
			.addObject("actIdentityLink", actIdentityLink);
	}
	
	/**
	 * 跳转到查看ActIdentityLink页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActIdentityLink页面");
		ActIdentityLink actIdentityLink = actIdentityLinkService.get(ActIdentityLink.class, id);
		
		return new ModelAndView("admin/workflow/actIdentityLink/actIdentityLink_detail")
				.addObject("actIdentityLink", actIdentityLink);
	}
	/**
	 * 添加ActIdentityLink
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActIdentityLink actIdentityLink){
		logger.debug("添加ActIdentityLink开始");
		try{
			String str = actIdentityLinkService.getSeqNo("ACT_RU_IDENTITYLINK");
			actIdentityLink.setId(str);
			this.actIdentityLinkService.save(actIdentityLink);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActIdentityLink失败");
		}
	}
	
	/**
	 * 修改ActIdentityLink
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActIdentityLink actIdentityLink){
		logger.debug("修改ActIdentityLink开始");
		try{
			this.actIdentityLinkService.update(actIdentityLink);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActIdentityLink失败");
		}
	}
	
	/**
	 * 删除ActIdentityLink
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActIdentityLink开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActIdentityLink编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActIdentityLink actIdentityLink = actIdentityLinkService.get(ActIdentityLink.class, deleteId);
				if(actIdentityLink == null)
					continue;
				actIdentityLinkService.update(actIdentityLink);
			}
		}
		logger.debug("删除ActIdentityLink IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActIdentityLink导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActIdentityLink actIdentityLink){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actIdentityLinkService.findPageBySql(page, actIdentityLink);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActIdentityLink_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
