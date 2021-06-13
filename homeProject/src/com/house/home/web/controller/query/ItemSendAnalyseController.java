package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.query.ItemSendAnalyseService;
@Controller
@RequestMapping("/admin/itemSendAnalyse")
public class ItemSendAnalyseController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ItemSendAnalyseController.class);
	@Autowired
	private ItemSendAnalyseService itemSendAnalyseService;
	
	/**
	 * 材料发货分析-按品类列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		itemApp.setSendDateFrom(DateUtil.startOfTheMonth(new Date()));
		itemApp.setSendDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/itemSendAnalyse/itemSendAnalyse_list");
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemSendAnalyseService.findPageBySql(page, itemApp, uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=this.getUserContext(request);
		itemApp.setLastUpdatedBy(uc.getCzybh());
		itemSendAnalyseService.findDetailPageBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * TODO 按发货类型查看详细材料数据
	 * @author	created by zb
	 * @date	2018-7-25--下午5:21:16
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSendAnalyseService.findItemPageBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			ItemApp itemApp){
		//跳转查看发货类型 -zb
		if ("5".equals(itemApp.getGroupType())) {
			return new ModelAndView("admin/query/itemSendAnalyse/itemSendAnalyse_detail_sendType")
				.addObject("itemApp", itemApp);
		}
		return new ModelAndView("admin/query/itemSendAnalyse/itemSendAnalyse_detail")
				.addObject("itemApp", itemApp);
	}
	/**
	 *ItemChg导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemSendAnalyseService.findPageBySql(page, itemApp, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料发货分析-按品类"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
