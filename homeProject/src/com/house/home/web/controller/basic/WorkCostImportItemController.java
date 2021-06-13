package com.house.home.web.controller.basic;

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
import com.house.home.entity.basic.WorkCostImportItem;
import com.house.home.service.basic.WorkCostImportItemService;

@Controller
@RequestMapping("/admin/workCostImportItem")
public class WorkCostImportItemController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkCostImportItemController.class);

	@Autowired
	private WorkCostImportItemService workCostImportItemService;

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
			HttpServletResponse response, WorkCostImportItem workCostImportItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workCostImportItemService.findPageBySql(page, workCostImportItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkCostImportItem列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workCostImportItem/workCostImportItem_list");
	}
	/**
	 * WorkCostImportItem查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workCostImportItem/workCostImportItem_code");
	}
	/**
	 * 跳转到新增WorkCostImportItem页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增WorkCostImportItem页面");
		WorkCostImportItem workCostImportItem = null;
		if (StringUtils.isNotBlank(id)){
			workCostImportItem = workCostImportItemService.get(WorkCostImportItem.class, id);
			workCostImportItem.setNo(null);
		}else{
			workCostImportItem = new WorkCostImportItem();
		}
		workCostImportItem.setM_umState("A");
		return new ModelAndView("admin/basic/workCostImportItem/workCostImportItem_save")
			.addObject("workCostImportItem", workCostImportItem);
	}
	/**
	 * 跳转到修改WorkCostImportItem页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改WorkCostImportItem页面");
		WorkCostImportItem workCostImportItem = null;
		if (StringUtils.isNotBlank(id)){
			workCostImportItem = workCostImportItemService.get(WorkCostImportItem.class, id);
			workCostImportItem.setM_umState("M");
		}else{
			workCostImportItem = new WorkCostImportItem();
		}
		
		return new ModelAndView("admin/basic/workCostImportItem/workCostImportItem_save")
			.addObject("workCostImportItem", workCostImportItem);
	}
	
	/**
	 * 跳转到查看WorkCostImportItem页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看WorkCostImportItem页面");
		WorkCostImportItem workCostImportItem = workCostImportItemService.get(WorkCostImportItem.class, id);
		workCostImportItem.setM_umState("V");
		return new ModelAndView("admin/basic/workCostImportItem/workCostImportItem_save")
				.addObject("workCostImportItem", workCostImportItem);
	}
	/**
	 * 添加WorkCostImportItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkCostImportItem workCostImportItem){
		logger.debug("添加WorkCostImportItem开始");
		try{
			String str = workCostImportItemService.getSeqNo("tWorkCostImportItem");
			workCostImportItem.setNo(str);
			workCostImportItem.setLastUpdate(new Date());
			workCostImportItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workCostImportItem.setExpired("F");
			workCostImportItem.setActionLog("ADD");
			this.workCostImportItemService.save(workCostImportItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加WorkCostImportItem失败");
		}
	}
	
	/**
	 * 修改WorkCostImportItem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WorkCostImportItem workCostImportItem){
		logger.debug("修改WorkCostImportItem开始");
		try{
			workCostImportItem.setLastUpdate(new Date());
			workCostImportItem.setLastUpdatedBy(getUserContext(request).getCzybh());
			workCostImportItem.setActionLog("EDIT");
			this.workCostImportItemService.update(workCostImportItem);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改WorkCostImportItem失败");
		}
	}
	
	/**
	 * 删除WorkCostImportItem
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除WorkCostImportItem开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "WorkCostImportItem编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				WorkCostImportItem workCostImportItem = workCostImportItemService.get(WorkCostImportItem.class, deleteId);
				if(workCostImportItem == null)
					continue;
				workCostImportItem.setExpired("T");
				workCostImportItemService.update(workCostImportItem);
			}
		}
		logger.debug("删除WorkCostImportItem IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *WorkCostImportItem导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WorkCostImportItem workCostImportItem){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workCostImportItemService.findPageBySql(page, workCostImportItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"人工成本导入项目管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
