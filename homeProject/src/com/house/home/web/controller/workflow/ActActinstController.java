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
import com.house.home.entity.workflow.ActActinst;
import com.house.home.service.workflow.ActActinstService;

@Controller
@RequestMapping("/admin/actActinst")
public class ActActinstController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActActinstController.class);

	@Autowired
	private ActActinstService actActinstService;

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
			HttpServletResponse response, ActActinst actActinst) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		actActinstService.findPageBySql(page, actActinst);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ActActinst列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actActinst/actActinst_list");
	}
	/**
	 * ActActinst查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/actActinst/actActinst_code");
	}
	/**
	 * 跳转到新增ActActinst页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ActActinst页面");
		ActActinst actActinst = null;
		if (StringUtils.isNotBlank(id)){
			actActinst = actActinstService.get(ActActinst.class, id);
			actActinst.setId(null);
		}else{
			actActinst = new ActActinst();
		}
		
		return new ModelAndView("admin/workflow/actActinst/actActinst_save")
			.addObject("actActinst", actActinst);
	}
	/**
	 * 跳转到修改ActActinst页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ActActinst页面");
		ActActinst actActinst = null;
		if (StringUtils.isNotBlank(id)){
			actActinst = actActinstService.get(ActActinst.class, id);
		}else{
			actActinst = new ActActinst();
		}
		
		return new ModelAndView("admin/workflow/actActinst/actActinst_update")
			.addObject("actActinst", actActinst);
	}
	
	/**
	 * 跳转到查看ActActinst页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ActActinst页面");
		ActActinst actActinst = actActinstService.get(ActActinst.class, id);
		
		return new ModelAndView("admin/workflow/actActinst/actActinst_detail")
				.addObject("actActinst", actActinst);
	}
	/**
	 * 添加ActActinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ActActinst actActinst){
		logger.debug("添加ActActinst开始");
		try{
			String str = actActinstService.getSeqNo("ACT_HI_ACTINST");
			actActinst.setId(str);
			this.actActinstService.save(actActinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ActActinst失败");
		}
	}
	
	/**
	 * 修改ActActinst
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ActActinst actActinst){
		logger.debug("修改ActActinst开始");
		try{
			this.actActinstService.update(actActinst);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ActActinst失败");
		}
	}
	
	/**
	 * 删除ActActinst
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ActActinst开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ActActinst编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ActActinst actActinst = actActinstService.get(ActActinst.class, deleteId);
				if(actActinst == null)
					continue;
				actActinstService.update(actActinst);
			}
		}
		logger.debug("删除ActActinst IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ActActinst导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ActActinst actActinst){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		actActinstService.findPageBySql(page, actActinst);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ActActinst_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
