/**
 * 
 */
package com.house.home.web.controller.basic;

import java.util.Date;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.service.basic.XtcsService;

/**
 * 
 * @author lenovo-l821
 * 
 */
@Controller
@RequestMapping("/admin/xtcs")
public class XtcsController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(XtcsController.class);

	@Autowired
	private XtcsService xtcsService;

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, Xtcs xtcs) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		xtcsService.findPageBySql(page, xtcs);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * 跳转到xtcs页面
	 * 
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/xtcs/xtcs_list");
	}

	/**
	 * 跳转到查看xtcs信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Xtcs xtcs) {
		logger.debug("跳转到查看xtcs页面");
		Xtcs sy = xtcsService.get(Xtcs.class, xtcs.getId());
/*		sy.setId(xtcs.getId());
		sy.setQz(xtcs.getQz());
		sy.setSm(xtcs.getSm());
		sy.setSmE(xtcs.getSmE());*/
		return new ModelAndView("admin/basic/xtcs/xtcs_view").addObject("xtc",
				sy);
	}

	/**
	 * 跳转到修改xtcs页面
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到修改系统参数页面");
		Xtcs xtcs = null;
		if (StringUtils.isNotBlank(id)) {
			xtcs = xtcsService.get(Xtcs.class, id);
		} else {
			xtcs = new Xtcs();
		}
		return new ModelAndView("admin/basic/xtcs/xtcs_update").addObject(
				"xtcs", xtcs);
	}

	/**
	 * 开始修改员工
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, Xtcs xtcs) {
		logger.debug("修改系统参数开始");
		try {
			xtcsService.update(xtcs);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改系统参数失败");
			e.printStackTrace();
		}
	}

	/**
	 * xtcs导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, Xtcs xtcs) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		xtcsService.findPageBySql(page, xtcs);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"系统参数_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}

}
