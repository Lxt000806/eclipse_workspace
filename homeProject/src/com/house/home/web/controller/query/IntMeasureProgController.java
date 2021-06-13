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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntMeasureProgService;
@Controller
@RequestMapping("/admin/intMeasureProg")
public class IntMeasureProgController extends BaseController {
	@Autowired
	private IntMeasureProgService intMeasureProgService;
	/**
	 * 查询intMeasureProg表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intMeasureProgService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/intMeasureProg/intMeasureProg_list");
	}
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intMeasureProgService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成测量进度表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
