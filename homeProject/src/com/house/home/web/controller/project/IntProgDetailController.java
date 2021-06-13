package com.house.home.web.controller.project;

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
import com.house.home.entity.project.IntProgDetail;
import com.house.home.service.project.IntProgDetailService;

@Controller
@RequestMapping("/admin/intProgDetail")
public class IntProgDetailController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(IntProgDetailController.class);

	@Autowired
	private IntProgDetailService intProgDetailService;

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
			HttpServletResponse response, IntProgDetail intProgDetail)
			throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		intProgDetailService.findPageBySql(page, intProgDetail);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * IntProgDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(
				"admin/project/intProgDetail/intProgDetail_list");
	}

	/**
	 * IntProgDetail查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(
				"admin/project/intProgDetail/intProgDetail_code");
	}

	/**
	 * 跳转到新增IntProgDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, IntProgDetail intProgDetail,String isReturn,String showZrf,String cantInstall) {
		logger.debug("跳转到新增IntProgDetail页面");
		intProgDetail.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		intProgDetail.setDate(new Date());
		System.out.println("showZrf"+showZrf);
		return new ModelAndView("admin/project/custIntProg/custIntProg_save")
				.addObject("intProgDetail", intProgDetail).addObject("isReturn", isReturn)
				.addObject("showZrf", showZrf)
				.addObject("cantInstall", cantInstall);
	}

	/**
	 * 跳转到修改IntProgDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,IntProgDetail intProgDetail,String isReturn,String showZrf,String cantInstall) {
		logger.debug("跳转到修改IntProgDetail页面");
		System.out.println(intProgDetail.getResPart()+" h "+intProgDetail.getType());
		return new ModelAndView("admin/project/custIntProg/custIntProg_detail_view")
		.addObject("intProgDetail", intProgDetail).addObject("isReturn", isReturn)
		.addObject("showZrf", showZrf)
		.addObject("cantInstall", cantInstall);
	}

	/**
	 * 跳转到查看IntProgDetail页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String id) {
		logger.debug("跳转到查看IntProgDetail页面");
		IntProgDetail intProgDetail = intProgDetailService.get(
				IntProgDetail.class, Integer.parseInt(id));

		return new ModelAndView(
				"admin/project/intProgDetail/intProgDetail_detail").addObject(
				"intProgDetail", intProgDetail);
	}

	/**
	 * 添加IntProgDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, IntProgDetail intProgDetail) {
		logger.debug("添加IntProgDetail开始");
		try {
			this.intProgDetailService.save(intProgDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "添加IntProgDetail失败");
		}
	}

	/**
	 * 修改IntProgDetail
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, IntProgDetail intProgDetail) {
		logger.debug("修改IntProgDetail开始");
		try {
			intProgDetail.setLastUpdate(new Date());
			intProgDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.intProgDetailService.update(intProgDetail);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改IntProgDetail失败");
		}
	}

	/**
	 * 删除IntProgDetail
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request,
			HttpServletResponse response, String deleteIds) {
		logger.debug("删除IntProgDetail开始");
		if (StringUtils.isBlank(deleteIds)) {
			ServletUtils.outPrintFail(request, response,
					"IntProgDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for (String deleteId : deleteIdList) {
			if (deleteId != null) {
				IntProgDetail intProgDetail = intProgDetailService.get(
						IntProgDetail.class, Integer.parseInt(deleteId));
				if (intProgDetail == null)
					continue;
				intProgDetail.setExpired("T");
				intProgDetailService.update(intProgDetail);
			}
		}
		logger.debug("删除IntProgDetail IDS={} 完成", deleteIdList);
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}

	/**
	 * IntProgDetail导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, IntProgDetail intProgDetail) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intProgDetailService.findPageBySql(page, intProgDetail);
		getExcelList(request);
		 
		ServletUtils.flushExcelOutputStream(
				request,
				response,
				page.getResult(),
				"集成进度明细_"
						+ DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

	/**
	 * 从tXTDM根据cbm查note
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDescr")
	@ResponseBody
	public Map<String, Object> findDescr(HttpServletRequest request,
			HttpServletResponse response, String cbm, String id)
			throws Exception {
			Map<String, Object> map = intProgDetailService.findDescr(cbm, id);
		return map;

	}
}
