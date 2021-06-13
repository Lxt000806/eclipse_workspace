
package com.house.home.web.controller.finance;

import java.util.Date;
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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.entity.finance.PrjProvide;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.finance.PrjProvideService;

@Controller
@RequestMapping("/admin/prjProvide")
public class PrjProvideController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjProvideController.class);

	@Autowired
	private PrjProvideService prjProvideService;
	@Autowired 
	private CzybmService czybmService;
	/**
	 * 查询JqGrid表格数据
	 * 
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response, PrjProvide prjProvide) throws Exception {	
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProvideService.findPageBySql(page, prjProvide);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 项目经理结算新增查询 
	 * 
	 */
	@RequestMapping("/goJqGrid_toPrjProCheck")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid_toPrjProCheck(HttpServletRequest request,HttpServletResponse response,PrjCheck prjCheck) throws Exception{
		Page<Map<String, Object>> page =this.newPageForJqGrid(request);
		prjProvideService.goJqGrid_toPrjProCheck(page,prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 项目经理结算明细查询 
	 * 
	 */
	@RequestMapping("/goJqGrid_toPrjProDetail")
	@ResponseBody
	public WebPage<Map<String, Object>> goJqGrid_toPrjProDetail(HttpServletRequest request,HttpServletResponse response,PrjCheck prjCheck) throws Exception{
		Page<Map<String, Object>> page =this.newPageForJqGrid(request);
		prjProvideService.goJqGrid_toPrjProDetail(page, prjCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 项目经理提成领取打开list界面 
	 * 
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/prjProvide/prjProvide_list");
	}
	/**
	 * 项目经理提成领取打开save界面 
	 * 
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvide){
		prjProvide.setDate(new Date());
		prjProvide.setAppCZY(this.getUserContext(request).getCzybh());
		//prjProvide.setStatus("1");
		resetPrjProvide(prjProvide);
		return new ModelAndView("admin/finance/prjProvide/prjProvide_save").addObject("prjProvide", prjProvide);
	}
	/**
	 * 项目经理提成领取打开update界面 
	 *
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvideTe){
		logger.debug("项目经理结算编辑开始");
		PrjProvide prjProvide=null;
		if (StringUtils.isNotBlank(prjProvideTe.getNo())) {
			prjProvide = prjProvideService.get(PrjProvide.class,prjProvideTe.getNo());
			prjProvide.setStatusName(prjProvideTe.getStatusName());
			resetPrjProvide(prjProvide);
		}
		return new ModelAndView("admin/finance/prjProvide/prjProvide_update").addObject("prjProvide",prjProvide);
	}
	/**
	 * 查看项目经理提成领取
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvideTe){
		logger.debug("项目经理查看开始");
		PrjProvide prjProvide =null;
		if (StringUtils.isNotBlank(prjProvideTe.getNo())) {
			prjProvide = prjProvideService.get(PrjProvide.class,prjProvideTe.getNo());
			prjProvide.setStatusName(prjProvideTe.getStatusName());
			resetPrjProvide(prjProvide);
		}
		return new ModelAndView("/admin/finance/prjProvide/prjProvide_view").addObject("prjProvide", prjProvide);
	}
	/**
	 * 打开项目经理提成领取审核
	 * 
	 */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvideTe){
		logger.debug("项目经理审核开始");
		PrjProvide prjProvide = null;
		if (StringUtils.isNotBlank(prjProvideTe.getNo())) {
			prjProvide = prjProvideService.get(PrjProvide.class,prjProvideTe.getNo());
			prjProvide.setStatusName(prjProvideTe.getStatusName());
			prjProvide.setConfirmCZY(this.getUserContext(request).getCzybh().trim());
			prjProvide.setConfirmDate(DateUtil.getNow());
			resetPrjProvide(prjProvide);
		}
		return new ModelAndView("admin/finance/prjProvide/prjProvide_check").addObject("prjProvide", prjProvide).
				addObject("appDescr", "系统管理员");
	}
	/**
	 * 打开项目经理提成领取反审核
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/goCheckReturn")
	public ModelAndView goCheckReturn(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvideTe){
		logger.debug("项目经理反审核开始");
		PrjProvide prjProvide =null;
		if (StringUtils.isNotBlank(prjProvideTe.getNo())) {
			prjProvide = prjProvideService.get(PrjProvide.class,prjProvideTe.getNo());
			prjProvide.setStatusName(prjProvideTe.getStatusName());
			prjProvide.setConfirmCZY(this.getUserContext(request).getCzybh().trim());
			prjProvide.setConfirmDate(DateUtil.getNow());
			resetPrjProvide(prjProvide);
		}
		return new ModelAndView("admin/finance/prjProvide/prjProvide_checkReturn").addObject("prjProvide", prjProvide);
	}
	/**
	 * 项目经理提成领取结算打开save界面 
	 *
	 */
	@RequestMapping("/goSavePrj")
	public ModelAndView goWorkAdd(HttpServletRequest request,HttpServletResponse response,String arr){
		PrjCheck prjCheck = new PrjCheck();
		if (StringUtils.isNotBlank(arr)) {
			prjCheck.setAllDetailInfo(arr);
			System.out.println(arr);
		}
		return new ModelAndView("admin/finance/prjProvide/prjProvide_check_save").addObject("prjCheck",prjCheck);
	} 
	/**
	 * 项目经理提成领取新增  
	 * 
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvide){
		logger.debug("项目经理提成领取保存开始");
		try {
			prjProvide.setM_umState("A");
			prjProvide.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String no = prjProvideService.getSeqNo("tPrjProvide");
			prjProvide.setNo(no);
			Result result = this.prjProvideService.doPrjProvideForProc(prjProvide);	
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response,"添加项目经理提成领取记录 失败 ");
		}
	}
	/**
	 *  项目经理提成领取更新
	 * 
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, PrjProvide prjProvide){
		logger.debug("项目经理提成领取更新开始");
		try {
			prjProvide.setM_umState("M");
			prjProvide.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = prjProvideService.doPrjProvideForProc(prjProvide);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "更新项目经理提成领取记录失败 ");
		}
	}
	/**项目经理提成领取审核*/
	@RequestMapping("/doCheckPass")
	public void doCheck(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvide){
		logger.debug("项目经理提成领取审核开始");
		try {
			prjProvide.setStatus("2");
			prjProvide.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProvide.setExpired("F");
			Result result = prjProvideService.doPrjProvideCheckForProc(prjProvide);
			ServletUtils.outPrintSuccess(request, response,"操作成功");
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response,"项目经理提成领取审核失败");
		    System.out.println(e.getMessage());
		}
	}
	@RequestMapping("/doCheckCancel")
	public void doCheckCancel(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvide){
		logger.debug("项目经理审核取消开始");
		try {
			prjProvide.setStatus("3");
			prjProvide.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProvide.setExpired("F");
			Result result = prjProvideService.doPrjProvideCheckForProc(prjProvide);
			ServletUtils.outPrintSuccess(request, response,"操作成功");
/*			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response,"项目经理提成领取审核取消失败");
		}
	}	
	@RequestMapping("/doCheckBack")
	public void doCheckBack(HttpServletRequest request,HttpServletResponse response,PrjProvide prjProvide){
		logger.debug("保存反审核开始");
		try {
			prjProvide.setStatus("4");
			prjProvide.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProvide.setExpired("F");
			Result result = prjProvideService.doPrjProvideCheckForProc(prjProvide);
			ServletUtils.outPrintSuccess(request, response,"操作成功");
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ServletUtils.outPrintFail(request, response, "项目经理提成领取反审核失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, PrjProvide prjProvide) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjProvideService.findPageBySql(page,prjProvide);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"项目经理提成领取_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	//公用申请人审核人员描述
	public void resetPrjProvide(PrjProvide prjProvide){
		if(prjProvide!=null){
			if (StringUtils.isNotBlank(prjProvide.getAppCZY())) {
				Czybm czybm = czybmService.get(Czybm.class,prjProvide.getAppCZY()); 
				prjProvide.setAppCZYDescr(czybm.getZwxm());
			}
			if (StringUtils.isNotBlank(prjProvide.getConfirmCZY())) {
				Czybm czybm = czybmService.get(Czybm.class,prjProvide.getConfirmCZY());
				prjProvide.setConfirmCZYDescr(czybm.getZwxm());
			}
		}	
	}
}
