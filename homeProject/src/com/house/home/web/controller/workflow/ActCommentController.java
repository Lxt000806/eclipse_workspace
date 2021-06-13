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
import com.house.home.entity.workflow.ActComment;
import com.house.home.service.workflow.ActCommentService;

@Controller
@RequestMapping("/admin/actComment")
public class ActCommentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActCommentController.class);

	@Autowired
	private ActCommentService actCommentService;

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
			HttpServletResponse response, ActComment actComment) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actCommentService.findPageBySql(page, actComment);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActComment列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actComment/actComment_list");
	}
	/**
	 * ActComment查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actComment/actComment_code");
	}
	/**
	 * 跳转到新增ActComment页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActComment页面");
		ActComment actComment = null;
		if (StringUtils.isNotBlank(id)){
			actComment = actCommentService.get(ActComment.class, id);
			actComment.setId(null);
		}else{
			actComment = new ActComment();
		}
		
		return new ModelAndView("admin/workflow/actComment/actComment_save")
			.addObject("actComment", actComment);
	}
	/**
	 * 跳转到修改ActComment页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActComment页面");
		ActComment actComment = null;
		if (StringUtils.isNotBlank(id)){
			actComment = actCommentService.get(ActComment.class, id);
		}else{
			actComment = new ActComment();
		}
		
		return new ModelAndView("admin/workflow/actComment/actComment_update")
			.addObject("actComment", actComment);
	}
	
	/**
	 * 跳转到查看ActComment页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActComment页面");
		ActComment actComment = actCommentService.get(ActComment.class, id);
		
		return new ModelAndView("admin/workflow/actComment/actComment_detail")
				.addObject("actComment", actComment);
	}
	/**
	 * 添加ActComment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActComment actComment){
		logger.debug("添加ActComment开始");
		try{
			String str = actCommentService.getSeqNo("ACT_HI_COMMENT");
			actComment.setId(str);
			this.actCommentService.save(actComment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActComment失败");
		}
	}
	
	/**
	 * 修改ActComment
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActComment actComment){
		logger.debug("修改ActComment开始");
		try{
			this.actCommentService.update(actComment);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActComment失败");
		}
	}
	
	/**
	 * 删除ActComment
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActComment开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActComment编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActComment actComment = actCommentService.get(ActComment.class, deleteId);
				if(actComment == null)
					continue;
				actCommentService.update(actComment);
			}
		}
		logger.debug("删除ActComment IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActComment导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActComment actComment){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actCommentService.findPageBySql(page, actComment);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActComment_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
