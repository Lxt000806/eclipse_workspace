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
import com.house.home.entity.workflow.ActVarinst;
import com.house.home.service.workflow.ActVarinstService;

@Controller
@RequestMapping("/admin/actVarinst")
public class ActVarinstController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActVarinstController.class);

	@Autowired
	private ActVarinstService actVarinstService;

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
			HttpServletResponse response, ActVarinst actVarinst) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actVarinstService.findPageBySql(page, actVarinst);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActVarinst列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actVarinst/actVarinst_list");
	}
	/**
	 * ActVarinst查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actVarinst/actVarinst_code");
	}
	/**
	 * 跳转到新增ActVarinst页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActVarinst页面");
		ActVarinst actVarinst = null;
		if (StringUtils.isNotBlank(id)){
			actVarinst = actVarinstService.get(ActVarinst.class, id);
			actVarinst.setId(null);
		}else{
			actVarinst = new ActVarinst();
		}
		
		return new ModelAndView("admin/workflow/actVarinst/actVarinst_save")
			.addObject("actVarinst", actVarinst);
	}
	/**
	 * 跳转到修改ActVarinst页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActVarinst页面");
		ActVarinst actVarinst = null;
		if (StringUtils.isNotBlank(id)){
			actVarinst = actVarinstService.get(ActVarinst.class, id);
		}else{
			actVarinst = new ActVarinst();
		}
		
		return new ModelAndView("admin/workflow/actVarinst/actVarinst_update")
			.addObject("actVarinst", actVarinst);
	}
	
	/**
	 * 跳转到查看ActVarinst页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActVarinst页面");
		ActVarinst actVarinst = actVarinstService.get(ActVarinst.class, id);
		
		return new ModelAndView("admin/workflow/actVarinst/actVarinst_detail")
				.addObject("actVarinst", actVarinst);
	}
	/**
	 * 添加ActVarinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActVarinst actVarinst){
		logger.debug("添加ActVarinst开始");
		try{
			String str = actVarinstService.getSeqNo("ACT_HI_VARINST");
			actVarinst.setId(str);
			this.actVarinstService.save(actVarinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActVarinst失败");
		}
	}
	
	/**
	 * 修改ActVarinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActVarinst actVarinst){
		logger.debug("修改ActVarinst开始");
		try{
			this.actVarinstService.update(actVarinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActVarinst失败");
		}
	}
	
	/**
	 * 删除ActVarinst
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActVarinst开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActVarinst编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActVarinst actVarinst = actVarinstService.get(ActVarinst.class, deleteId);
				if(actVarinst == null)
					continue;
				actVarinstService.update(actVarinst);
			}
		}
		logger.debug("删除ActVarinst IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActVarinst导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActVarinst actVarinst){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actVarinstService.findPageBySql(page, actVarinst);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActVarinst_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
