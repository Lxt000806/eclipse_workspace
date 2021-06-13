package com.house.home.web.controller.basic;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BankPos;
import com.house.home.service.basic.BankPosService;

@Controller
@RequestMapping("/admin/bankPos")
public class BankPosController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(BankPosController.class);

	@Autowired
	private BankPosService bankPosService;

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
			HttpServletResponse response, BankPos bankPos) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		bankPosService.findPageBySql(page, bankPos);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * BankPos列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/bankPos/bankPos_list");
	}

	/**
	 * BankPos查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/bankPos/bankPos_code");
	}

	/**
	 * 跳转到新增BankPos页面
	 * 
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("跳转到新增BankPos页面");
		
		return new ModelAndView("admin/basic/bankPos/bankPos_save");
	}

	/**
	 * 跳转到修改BankPos页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String code) {
		logger.debug("跳转到修改BankPos页面");
		Map<String,Object> map=bankPosService.getBankPosDetail(code);
		return new ModelAndView("admin/basic/bankPos/bankPos_update")
				.addObject("bankPos", map);
	}
	/**
	 * 跳转到修改BankPos页面
	 * 
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, String code) {
		logger.debug("跳转到复制BankPos页面");
		Map<String,Object> map=bankPosService.getBankPosDetail(code);
		return new ModelAndView("admin/basic/bankPos/bankPos_copy")
				.addObject("bankPos", map);
	}
	/**
	 * 跳转到查看BankPos页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String code) {
		logger.debug("跳转到查看BankPos页面");
		Map<String,Object> map=bankPosService.getBankPosDetail(code);
		return new ModelAndView("admin/basic/bankPos/bankPos_view")
				.addObject("bankPos", map);
	}

	/**
	 * 添加BankPos
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, BankPos bankPos) {
		logger.debug("添加BankPos开始");
			try {
				bankPos.setLastUpdate(new Date());
				bankPos.setLastUpdatedBy(getUserContext(request).getCzybh());
				bankPos.setExpired("F");
				this.bankPosService.save(bankPos);
				ServletUtils.outPrintSuccess(request, response);
			} catch (Exception e) {
				ServletUtils.outPrintFail(request, response, "编号已存在，添加失败！");
			}
	}

	/**
	 * 修改BankPos
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, BankPos bankPos) {
		logger.debug("修改BankPos开始");
		
		try {
			if (!"T".equals(bankPos.getExpired())) {
				bankPos.setExpired("F");
			}
			bankPos.setLastUpdate(new Date());
			bankPos.setActionLog("EDIT");
			bankPos.setLastUpdatedBy(getUserContext(request).getCzybh());
			bankPosService.doUpdate(bankPos);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * BankPos导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, BankPos bankPos) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		bankPosService.findPageBySql(page, bankPos);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"收款Pos管理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

}
