package com.house.home.web.controller.finance;

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
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;
import com.house.home.service.finance.WorkQltFeeService;

@Controller
@RequestMapping("/admin/workQltFee")
public class WorkQltFeeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkQltFeeController.class);

	@Autowired
	private WorkQltFeeService workQltFeeService ;

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
			HttpServletResponse response, Worker worker) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workQltFeeService.findPageBySql(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid2(HttpServletRequest request,
			HttpServletResponse response, WorkQltFeeTran workQltFeeTran) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workQltFeeService.findDetailBySql(page, workQltFeeTran);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WorkQltFee列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/finance/workQltFee/workQltFee_list");
	}
	/**
	 * WorkQltFee列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request,
			HttpServletResponse response,WorkQltFeeTran workQltFeeTran) throws Exception {
		
		return new ModelAndView("admin/finance/workQltFee/workQltFee_detailQuery").addObject("workQltFee", workQltFeeTran);
	}
	
	/**
	 * WorkQltFee列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,WorkQltFeeTran workQltFeeTran) throws Exception {
		workQltFeeTran.setDate(new Date());
		workQltFeeTran.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/finance/workQltFee/workQltFee_update").addObject("workQltFeeTran", workQltFeeTran);
	}
	
	/**
	 * 添加WorkQltFeeTran
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WorkQltFeeTran workQltFeeTran){
		logger.debug("添加WorkQltFeeTran开始");
		try{
			if("1".equals(workQltFeeTran.getType())&&workQltFeeTran.getQualityFee()+workQltFeeTran.getTryFee()<0){
				ServletUtils.outPrintFail(request, response, "质保金余额不足，无法取出！");
			}else if("2".equals(workQltFeeTran.getType())&&workQltFeeTran.getAccidentFee()+workQltFeeTran.getTryFee()<0){
					ServletUtils.outPrintFail(request, response, "意外险余额不足，无法取出！");	
			}else{
				workQltFeeTran.setDate(new Date());
				workQltFeeTran.setLastUpdate(new Date());
				workQltFeeTran.setLastUpdatedBy(getUserContext(request).getCzybh());
				workQltFeeTran.setExpired("F");
				workQltFeeTran.setActionLog("ADD");
				if("1".equals(workQltFeeTran.getType())){
					workQltFeeTran.setAftFee(workQltFeeTran.getTryFee()+workQltFeeTran.getQualityFee());
				}else{
					workQltFeeTran.setAftFee(workQltFeeTran.getTryFee()+workQltFeeTran.getAccidentFee());
				}
				this.workQltFeeService.save(workQltFeeTran);
				this.workQltFeeService.doUpdate(workQltFeeTran);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加WorkQltFeeTran失败");
		}
	}
	/**
	 * WorkQltFeeTran导出Excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, Worker worker) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workQltFeeService.findPageBySql(page, worker);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"工人质保金管理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}

}
