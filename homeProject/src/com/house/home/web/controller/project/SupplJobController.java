package com.house.home.web.controller.project;

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
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.SupplJob;
import com.house.home.service.project.SupplJobService;

@Controller
@RequestMapping("/admin/supplJob")
public class SupplJobController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SupplJobController.class);

	@Autowired
	private SupplJobService supplJobService;

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
			HttpServletResponse response, SupplJob supplJob) throws Exception {

		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		supplJob.setSupplCode(this.getUserContext(request).getEmnum());
		supplJobService.findPageBySql(page, supplJob);
		return new WebPage<Map<String, Object>>(page);
	}

	/**
	 * SupplJob列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/supplier/supplJob/supplJob_list");
	}

	/**
	 * 跳转到接收SupplJob页面
	 * 
	 * @return
	 */
	@RequestMapping("/goRecv")
	public ModelAndView goRecv(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) {
		logger.debug("跳转到接收SupplJob页面");
		
		String buildPass = supplJobService.getBuildPassByCustCode(supplJob.getCustCode());
		supplJob.setBuildPass(buildPass);
		
		return new ModelAndView("admin/supplier/supplJob/supplJob_recv")
				.addObject("supplJob", supplJob);
	}

	/**
	 * 跳转到查看SupplJob页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SupplJob supplJob) {
		logger.debug("跳转到查看SupplJob页面");
		
		String buildPass = supplJobService.getBuildPassByCustCode(supplJob.getCustCode());
        supplJob.setBuildPass(buildPass);
		
		return new ModelAndView("admin/supplier/supplJob/supplJob_view")
				.addObject("supplJob", supplJob);
	}
	
	/**
	 * 跳转到退回SupplJob页面
	 * 
	 * @return
	 */
	@RequestMapping("/goReturn")
	public ModelAndView goReturn(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) {
		logger.debug("跳转到退回SupplJob页面");
		return new ModelAndView("admin/supplier/supplJob/supplJob_return")
				.addObject("supplJob", supplJob);
	}
	
	/**
	 * 跳转到完成SupplJob页面
	 * 
	 * @return
	 */
	@RequestMapping("/goComplete")
	public ModelAndView goComplete(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) {
		logger.debug("跳转到完成SupplJob页面");
		String downLoadPath=FileUploadUtils.DOWNLOAD_URL+PrjProgNewUploadRule.FIRST_LEVEL_PATH;
		return new ModelAndView("admin/supplier/supplJob/supplJob_complete")
				.addObject("supplJob", supplJob).addObject("url", downLoadPath);
	}
	/**
	 * 跳转到接收SupplJob页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) {
		logger.debug("跳转到编辑SupplJob页面");
		return new ModelAndView("admin/supplier/supplJob/supplJob_update")
				.addObject("supplJob", supplJob);
	}
	/**
	 * 接收
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */

	@RequestMapping("/doRecv")
	public void doRecv(HttpServletRequest request,
			HttpServletResponse response,String no,Integer pk, String recvDate,String supplRemarks,String planDate) {
		supplJobService.doExec("recv", no, pk, recvDate, supplRemarks, planDate,this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 完成
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */

	@RequestMapping("/doComplete")
	public void doComplete(HttpServletRequest request,
			HttpServletResponse response,String no,Integer pk, String recvDate,String supplRemarks,String planDate) {
		supplJobService.doExec("complete", no, pk, recvDate, supplRemarks, planDate, this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 退回
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */

	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request,
			HttpServletResponse response,String no,Integer pk, String recvDate,String supplRemarks,String planDate) {
		supplJobService.doExec("return", no, pk, recvDate, supplRemarks, planDate, this.getUserContext(request).getCzybh());
	}
	/**
	 * 橱柜出货导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doCupBoardExcel")
	public void doCupBoardExcel(HttpServletRequest request,
			HttpServletResponse response, SupplJob supplJob) {
		supplJob.setSupplCode(this.getUserContext(request).getEmnum());
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplJobService.findCupboardPageBySql(page, supplJob);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"橱柜出货_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	/**
	 * 跳转到橱柜出货列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCupboardSend")
	public ModelAndView goCupboardSend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/supplier/supplJob/supplJob_cupboardSend");
	}
	/**
	 * 橱柜出货Grid
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCupboardJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goCupboardJqGrid(HttpServletRequest request,
			HttpServletResponse response, SupplJob supplJob) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		supplJob.setSupplCode(this.getUserContext(request).getEmnum());
		supplJobService.findCupboardPageBySql(page, supplJob);
		return new WebPage<Map<String, Object>>(page);
	}
	/**
	 * 编辑
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */

	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) {
		supplJobService.doUpdate(supplJob);
	}
	/**
	 * 跳转到查看图片页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewPhoto")
	public ModelAndView goViewPhoto(HttpServletRequest request,
			HttpServletResponse response,SupplJob supplJob) throws Exception {
		return new ModelAndView("admin/supplier/supplJob/supplJob_viewPhoto").addObject("supplJob", supplJob);
	}
	
	/**
	 * 橱柜出货导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, SupplJob supplJob) {
		supplJob.setSupplCode(this.getUserContext(request).getEmnum());
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplJobService.findPageBySql(page, supplJob);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"任务处理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	
}
