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
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.project.AutoArrWorkerApp;
import com.house.home.service.query.LogWareEffAnalyService;

@Controller
@RequestMapping("/admin/logWareEffAnaly")
public class LogWareEffAnalyController extends BaseController { 
	@Autowired
	LogWareEffAnalyService logWareEffAnalyService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		logWareEffAnalyService.findPageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemWHBal itemWHBal){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		logWareEffAnalyService.findPageBySql(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地发包结算分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到物流仓储效率分析列表
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, 
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		itemWHBal.setDateFrom(DateUtil.startOfTheMonth(new Date())); 
		itemWHBal.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/logWareEffAnaly/logWareEffAnaly_list").addObject("itemWHBal",itemWHBal);	
	}
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		logWareEffAnalyService.findPageBySql_detail(page, itemWHBal);;
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSendDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		logWareEffAnalyService.findPageBySql_sendDetail(page, itemWHBal);;
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goDetailView")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,ItemWHBal itemWHBal){
		ItemType2 itemType2 = logWareEffAnalyService.get(ItemType2.class, itemWHBal.getItemType2());
		itemWHBal.setItemType1(itemType2.getItemType1());
		return new ModelAndView("admin/query/logWareEffAnaly/logWareEffAnaly_detail")
		.addObject("itemWHBal", itemWHBal);
				
	}
	
}
