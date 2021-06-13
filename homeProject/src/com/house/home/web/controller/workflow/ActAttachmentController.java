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
import com.house.home.entity.workflow.ActAttachment;
import com.house.home.service.workflow.ActAttachmentService;

@Controller
@RequestMapping("/admin/actAttachment")
public class ActAttachmentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActAttachmentController.class);

	@Autowired
	private ActAttachmentService actAttachmentService;

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
			HttpServletResponse response, ActAttachment actAttachment) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actAttachmentService.findPageBySql(page, actAttachment);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActAttachment列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actAttachment/actAttachment_list");
	}
	/**
	 * ActAttachment查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actAttachment/actAttachment_code");
	}
	/**
	 * 跳转到新增ActAttachment页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActAttachment页面");
		ActAttachment actAttachment = null;
		if (StringUtils.isNotBlank(id)){
			actAttachment = actAttachmentService.get(ActAttachment.class, id);
			actAttachment.setId(null);
		}else{
			actAttachment = new ActAttachment();
		}
		
		return new ModelAndView("admin/workflow/actAttachment/actAttachment_save")
			.addObject("actAttachment", actAttachment);
	}
	/**
	 * 跳转到修改ActAttachment页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActAttachment页面");
		ActAttachment actAttachment = null;
		if (StringUtils.isNotBlank(id)){
			actAttachment = actAttachmentService.get(ActAttachment.class, id);
		}else{
			actAttachment = new ActAttachment();
		}
		
		return new ModelAndView("admin/workflow/actAttachment/actAttachment_update")
			.addObject("actAttachment", actAttachment);
	}
	
	/**
	 * 跳转到查看ActAttachment页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActAttachment页面");
		ActAttachment actAttachment = actAttachmentService.get(ActAttachment.class, id);
		
		return new ModelAndView("admin/workflow/actAttachment/actAttachment_detail")
				.addObject("actAttachment", actAttachment);
	}
	/**
	 * 添加ActAttachment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActAttachment actAttachment){
		logger.debug("添加ActAttachment开始");
		try{
			String str = actAttachmentService.getSeqNo("ACT_HI_ATTACHMENT");
			actAttachment.setId(str);
			this.actAttachmentService.save(actAttachment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActAttachment失败");
		}
	}
	
	/**
	 * 修改ActAttachment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActAttachment actAttachment){
		logger.debug("修改ActAttachment开始");
		try{
			this.actAttachmentService.update(actAttachment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActAttachment失败");
		}
	}
	
	/**
	 * 删除ActAttachment
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActAttachment开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActAttachment编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActAttachment actAttachment = actAttachmentService.get(ActAttachment.class, deleteId);
				if(actAttachment == null)
					continue;
				actAttachmentService.update(actAttachment);
			}
		}
		logger.debug("删除ActAttachment IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActAttachment导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActAttachment actAttachment){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actAttachmentService.findPageBySql(page, actAttachment);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActAttachment_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
