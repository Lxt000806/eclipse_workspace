package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemPlanLog;
import com.house.home.service.query.ItemPlanLogService;

@Controller
@RequestMapping("/admin/itemPlanLog")
public class ItemPlanLogController extends BaseController { 
	@Autowired
	private ItemPlanLogService itemPlanLogService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customerDa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemPlanLog itemPlanLog) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanLogService.findPageBySql(page, itemPlanLog);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 材料预算日志查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemPlanLog itemPlanLog) throws Exception {
		return new ModelAndView("admin/query/itemPlanLog/itemPlanLog_list").addObject("ItemPlanLog", itemPlanLog);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPlanLog itemPlanLog){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPlanLogService.findPageBySql(page, itemPlanLog);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料预算日志查询_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
		
}
