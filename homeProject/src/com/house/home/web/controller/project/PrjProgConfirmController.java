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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgConfirm;
import com.house.home.service.project.PrjProgConfirmService;

@Controller      
@RequestMapping("/admin/prjProgConfirm")
public class PrjProgConfirmController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PrjProgController.class);

	@Autowired
	PrjProgConfirmService prjProgConfirmService;
	
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
			HttpServletResponse response,PrjProgConfirm prjProgConfirm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgConfirmService.findPageBySql(page, prjProgConfirm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConfirmJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgConfirm prjProgConfirm) throws Exception {
		UserContext uc=getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgConfirmService.findConfirmPageBySql(page, prjProgConfirm,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrjConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjConfirmJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProgConfirm prjProgConfirm) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgConfirmService.findPrjConfirmPageBySql(page,prjProgConfirm);
		return new WebPage<Map<String,Object>>(page);
	}
	

	/**
	 *PrjProg导出工地验收Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doConfirmExcel")
	public void doConfirmExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjProgConfirm prjProgConfirm){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc= getUserContext(request);
		page.setPageSize(-1);
		prjProgConfirmService.findConfirmPageBySql(page, prjProgConfirm,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地验收_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
}
