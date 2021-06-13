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
import com.house.home.entity.workflow.ActTaskinst;
import com.house.home.service.workflow.ActTaskinstService;

@Controller
@RequestMapping("/admin/actTaskinst")
public class ActTaskinstController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActTaskinstController.class);

	@Autowired
	private ActTaskinstService actTaskinstService;

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
			HttpServletResponse response, ActTaskinst actTaskinst) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actTaskinstService.findPageBySql(page, actTaskinst);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActTaskinst列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actTaskinst/actTaskinst_list");
	}
	/**
	 * ActTaskinst查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actTaskinst/actTaskinst_code");
	}
	/**
	 * 根据实例编号获取列表
	 * @param request
	 * @param response
	 * @param actTask
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridByProcInstId")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridByProcInstId(HttpServletRequest request,
			HttpServletResponse response, String procInstId) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actTaskinstService.findByProcInstId(page, procInstId);
		
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增ActTaskinst页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActTaskinst页面");
		ActTaskinst actTaskinst = null;
		if (StringUtils.isNotBlank(id)){
			actTaskinst = actTaskinstService.get(ActTaskinst.class, id);
			actTaskinst.setId(null);
		}else{
			actTaskinst = new ActTaskinst();
		}
		
		return new ModelAndView("admin/workflow/actTaskinst/actTaskinst_save")
			.addObject("actTaskinst", actTaskinst);
	}
	/**
	 * 跳转到修改ActTaskinst页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActTaskinst页面");
		ActTaskinst actTaskinst = null;
		if (StringUtils.isNotBlank(id)){
			actTaskinst = actTaskinstService.get(ActTaskinst.class, id);
		}else{
			actTaskinst = new ActTaskinst();
		}
		
		return new ModelAndView("admin/workflow/actTaskinst/actTaskinst_update")
			.addObject("actTaskinst", actTaskinst);
	}
	
	/**
	 * 跳转到查看ActTaskinst页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActTaskinst页面");
		ActTaskinst actTaskinst = actTaskinstService.get(ActTaskinst.class, id);
		
		return new ModelAndView("admin/workflow/actTaskinst/actTaskinst_detail")
				.addObject("actTaskinst", actTaskinst);
	}
	/**
	 * 添加ActTaskinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActTaskinst actTaskinst){
		logger.debug("添加ActTaskinst开始");
		try{
			String str = actTaskinstService.getSeqNo("ACT_HI_TASKINST");
			actTaskinst.setId(str);
			this.actTaskinstService.save(actTaskinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActTaskinst失败");
		}
	}
	
	/**
	 * 修改ActTaskinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActTaskinst actTaskinst){
		logger.debug("修改ActTaskinst开始");
		try{
			this.actTaskinstService.update(actTaskinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActTaskinst失败");
		}
	}
	
	/**
	 * 删除ActTaskinst
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActTaskinst开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActTaskinst编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActTaskinst actTaskinst = actTaskinstService.get(ActTaskinst.class, deleteId);
				if(actTaskinst == null)
					continue;
				actTaskinstService.update(actTaskinst);
			}
		}
		logger.debug("删除ActTaskinst IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActTaskinst导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActTaskinst actTaskinst){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actTaskinstService.findPageBySql(page, actTaskinst);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActTaskinst_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
