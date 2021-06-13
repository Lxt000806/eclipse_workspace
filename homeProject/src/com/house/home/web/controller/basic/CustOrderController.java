package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustOrder;
import com.house.home.service.basic.CustOrderService;

@Controller
@RequestMapping("/admin/custOrder")
public class CustOrderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustOrderController.class);

	/**
	 * 
	 */
	@Autowired
	private CustOrderService custOrderService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param advert
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,	HttpServletResponse response, CustOrder custOrder) 
	throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custOrderService.goJqGrid(page, custOrder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,	HttpServletResponse response, CustOrder custOrder){
		logger.info("跳转到客户预约管理主页");
		return new ModelAndView("admin/basic/custOrder/custOrder_list").addObject("data", custOrder);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, CustOrder custOrder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custOrderService.goJqGrid(page, custOrder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客户预约--"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
}
