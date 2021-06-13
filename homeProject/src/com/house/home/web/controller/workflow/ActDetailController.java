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
import com.house.home.entity.workflow.ActDetail;
import com.house.home.service.workflow.ActDetailService;

@Controller
@RequestMapping("/admin/actDetail")
public class ActDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActDetailController.class);

	@Autowired
	private ActDetailService actDetailService;

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
			HttpServletResponse response, ActDetail actDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actDetailService.findPageBySql(page, actDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actDetail/actDetail_list");
	}
	/**
	 * ActDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actDetail/actDetail_code");
	}
	/**
	 * 跳转到新增ActDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActDetail页面");
		ActDetail actDetail = null;
		if (StringUtils.isNotBlank(id)){
			actDetail = actDetailService.get(ActDetail.class, id);
			actDetail.setId(null);
		}else{
			actDetail = new ActDetail();
		}
		
		return new ModelAndView("admin/workflow/actDetail/actDetail_save")
			.addObject("actDetail", actDetail);
	}
	/**
	 * 跳转到修改ActDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActDetail页面");
		ActDetail actDetail = null;
		if (StringUtils.isNotBlank(id)){
			actDetail = actDetailService.get(ActDetail.class, id);
		}else{
			actDetail = new ActDetail();
		}
		
		return new ModelAndView("admin/workflow/actDetail/actDetail_update")
			.addObject("actDetail", actDetail);
	}
	
	/**
	 * 跳转到查看ActDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActDetail页面");
		ActDetail actDetail = actDetailService.get(ActDetail.class, id);
		
		return new ModelAndView("admin/workflow/actDetail/actDetail_detail")
				.addObject("actDetail", actDetail);
	}
	/**
	 * 添加ActDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActDetail actDetail){
		logger.debug("添加ActDetail开始");
		try{
			String str = actDetailService.getSeqNo("ACT_HI_DETAIL");
			actDetail.setId(str);
			this.actDetailService.save(actDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActDetail失败");
		}
	}
	
	/**
	 * 修改ActDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActDetail actDetail){
		logger.debug("修改ActDetail开始");
		try{
			this.actDetailService.update(actDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActDetail失败");
		}
	}
	
	/**
	 * 删除ActDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActDetail actDetail = actDetailService.get(ActDetail.class, deleteId);
				if(actDetail == null)
					continue;
				actDetailService.update(actDetail);
			}
		}
		logger.debug("删除ActDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActDetail actDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actDetailService.findPageBySql(page, actDetail);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActDetail_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
