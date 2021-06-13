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
import com.house.home.entity.workflow.ActProcinst;
import com.house.home.service.workflow.ActProcinstService;

@Controller
@RequestMapping("/admin/actProcinst")
public class ActProcinstController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActProcinstController.class);

	@Autowired
	private ActProcinstService actProcinstService;

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
			HttpServletResponse response, ActProcinst actProcinst) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actProcinstService.findPageBySql(page, actProcinst);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActProcinst列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actProcinst/actProcinst_list");
	}
	/**
	 * ActProcinst查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actProcinst/actProcinst_code");
	}
	/**
	 * 跳转到新增ActProcinst页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActProcinst页面");
		ActProcinst actProcinst = null;
		if (StringUtils.isNotBlank(id)){
			actProcinst = actProcinstService.get(ActProcinst.class, id);
			actProcinst.setId(null);
		}else{
			actProcinst = new ActProcinst();
		}
		
		return new ModelAndView("admin/workflow/actProcinst/actProcinst_save")
			.addObject("actProcinst", actProcinst);
	}
	/**
	 * 跳转到修改ActProcinst页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActProcinst页面");
		ActProcinst actProcinst = null;
		if (StringUtils.isNotBlank(id)){
			actProcinst = actProcinstService.get(ActProcinst.class, id);
		}else{
			actProcinst = new ActProcinst();
		}
		
		return new ModelAndView("admin/workflow/actProcinst/actProcinst_update")
			.addObject("actProcinst", actProcinst);
	}
	
	/**
	 * 跳转到查看ActProcinst页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActProcinst页面");
		ActProcinst actProcinst = actProcinstService.get(ActProcinst.class, id);
		
		return new ModelAndView("admin/workflow/actProcinst/actProcinst_detail")
				.addObject("actProcinst", actProcinst);
	}
	/**
	 * 添加ActProcinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActProcinst actProcinst){
		logger.debug("添加ActProcinst开始");
		try{
			String str = actProcinstService.getSeqNo("ACT_HI_PROCINST");
			actProcinst.setId(str);
			this.actProcinstService.save(actProcinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActProcinst失败");
		}
	}
	
	/**
	 * 修改ActProcinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActProcinst actProcinst){
		logger.debug("修改ActProcinst开始");
		try{
			this.actProcinstService.update(actProcinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActProcinst失败");
		}
	}
	
	/**
	 * 删除ActProcinst
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActProcinst开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActProcinst编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActProcinst actProcinst = actProcinstService.get(ActProcinst.class, deleteId);
				if(actProcinst == null)
					continue;
				actProcinstService.update(actProcinst);
			}
		}
		logger.debug("删除ActProcinst IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActProcinst导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActProcinst actProcinst){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actProcinstService.findPageBySql(page, actProcinst);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActProcinst_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
