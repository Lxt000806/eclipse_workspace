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
import com.house.home.entity.insales.ItemReq;

import com.house.home.service.basic.CzybmService;
import com.house.home.service.query.ItemReqQueryService;
@Controller
@RequestMapping("/admin/itemReqQuery")
public class ItemReqQueryController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ItemReqQueryController.class);

	@Autowired
	private ItemReqQueryService itemReqQueryService;
	@Autowired
	private CzybmService czybmService;
	/**
	 * ItemReq列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,ItemReq itemReq) throws Exception {
		UserContext uc=this.getUserContext(request);
		itemReq.setCostRight(uc.getCostRight().trim());
		if(czybmService.hasGNQXByCzybh(uc.getCzybh(), "0507", "查看客户联系方式")){
			itemReq.setPhoneRight("1");
		}
		return new ModelAndView("admin/query/itemReqQuery/itemReqQuery_list");
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
			HttpServletResponse response,ItemReq itemReq) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemReq.setItemRight(uc.getItemRight().trim());
		itemReqQueryService.findPageBySql(page, itemReq);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReq itemReq){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemReq.setItemRight(uc.getItemRight().trim());
		itemReqQueryService.findPageBySql(page, itemReq);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料需求查询"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
