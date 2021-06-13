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
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.SetMainItemPrice;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustCheck;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.service.project.CustCheckService;
@Controller
@RequestMapping("/admin/CustCheck")
public class CustCheckController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CustCheckController.class);
	
	@Autowired
	private CustCheckService custCheckService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param custCheck
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustCheck custCheck) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		custCheckService.findPageBySql(page, custCheck);
		
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 工地结算列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/custCheck/custCheck_list");
	}
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		custCheck.setAppDate(new Date());
		return new ModelAndView("admin/project/custCheck/custCheck_add").addObject("custCheck", custCheck);
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_detail").addObject("custCheck", custCheck);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustCheck custCheck){
		logger.debug("新增开始");
		try{
			custCheck.setIsSalaryConfirm("0");
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setExpired("F");
			custCheck.setLastUpdate(new Date());
			custCheck.setActionLog("ADD");
			custCheckService.save(custCheck);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			System.out.println(e);
			ServletUtils.outPrintFail(request, response, "添加失败");
		}
	}
	
	@RequestMapping("/DoRcvMain")
	public void doRcvMain(HttpServletRequest request,HttpServletResponse response,String custCode) throws Exception{
		logger.debug("主材接收开始");
		try{
			CustCheck custCheck = custCheckService.get(CustCheck.class, custCode);
			if(!custCheck.getMainStatus().equals("1")){
				ServletUtils.outPrintSuccess(request, response, "主材结算状态不是申请状态,不允许接收");
				return;
			}
			custCheck.setMainStatus("2");
			custCheck.setMainRcvDate(new Date());
			custCheck.setMainRcvCzy(getUserContext(request).getCzybh());
			custCheck.setLastUpdate(new Date());
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setActionLog("EDIT");
			custCheckService.update(custCheck);
			ServletUtils.outPrintSuccess(request, response, "主材接收成功");
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "主材接收失败");
		}
	}
	@RequestMapping("/goUpdateMainRmk")
	public ModelAndView goUpdateMainRmk(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_mainRMK").addObject("custCheck", custCheck);
	}
	@RequestMapping("/DoRcvSoft")
	public void DoRcvSoft(HttpServletRequest request,HttpServletResponse response,String custCode) throws Exception{
		logger.debug("软装接收开始");
		try{
			CustCheck custCheck = custCheckService.get(CustCheck.class, custCode);
			if(!custCheck.getSoftSatus().equals("1")){
				ServletUtils.outPrintSuccess(request, response, "软装结算状态不是申请状态,不允许接收");
				return;
			}
			custCheck.setSoftSatus("2");
			custCheck.setSoftRcvDate(new Date());
			custCheck.setSoftRcvCzy(getUserContext(request).getCzybh());
			custCheck.setLastUpdate(new Date());
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setActionLog("EDIT");
			custCheckService.update(custCheck);
			ServletUtils.outPrintSuccess(request, response, "软装接收成功");
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "软装接收失败");
		}
	}
	@RequestMapping("/goUpdateSoftRmk")
	public ModelAndView goUpdateSoftRmk(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_softRMK").addObject("custCheck", custCheck);
	}
	@RequestMapping("/DoRcvInt")
	public void DoRcvInt(HttpServletRequest request,HttpServletResponse response,String custCode) throws Exception{
		logger.debug("集成接收开始");
		try{
			CustCheck custCheck = custCheckService.get(CustCheck.class, custCode);
			if(!custCheck.getIntStatus().equals("1")){
				ServletUtils.outPrintSuccess(request, response, "集成结算状态不是申请状态,不允许接收");
				return;
			}
			custCheck.setIntStatus("2");
			custCheck.setIntRcvDate(new Date());
			custCheck.setIntRcvCzy(getUserContext(request).getCzybh());
			custCheck.setLastUpdate(new Date());
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setActionLog("EDIT");
			custCheckService.update(custCheck);
			ServletUtils.outPrintSuccess(request, response, "集成接收成功");
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "集成接收失败");
		}
	}
	@RequestMapping("/goUpdateIntRmk")
	public ModelAndView goUpdateIntRmk(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_intRMK").addObject("custCheck", custCheck);
	}
	@RequestMapping("/DoRcvFin")
	public void DoRcvFin(HttpServletRequest request,HttpServletResponse response,String custCode) throws Exception{
		logger.debug("财务接收开始");
		try{
			CustCheck custCheck = custCheckService.get(CustCheck.class, custCode);
			if(!custCheck.getFinStatus().equals("1")){
				ServletUtils.outPrintSuccess(request, response, "财务结算状态不是申请状态,不允许接收");
				return;
			}
			custCheck.setFinStatus("2");
			custCheck.setFinRcvDate(new Date());
			custCheck.setFinRcvCzy(getUserContext(request).getCzybh());
			custCheck.setLastUpdate(new Date());
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setActionLog("EDIT");
			custCheckService.update(custCheck);
			ServletUtils.outPrintSuccess(request, response, "财务接收成功");
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "财务接收失败");
		}
	}
	@RequestMapping("/goUpdateFinRmk")
	public ModelAndView goUpdateFinRmk(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_finRMK").addObject("custCheck", custCheck);
	}
	@RequestMapping("/goUpdateFinCpl")
	public ModelAndView goUpdateFinCpl(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		return new ModelAndView("admin/project/custCheck/custCheck_finCpl").addObject("custCheck", custCheck);
	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,CustCheck custCheck) throws Exception{
		logger.debug(custCheck.getType()+"备注开始");
		try{
			CustCheck cc = custCheckService.get(CustCheck.class, custCheck.getCustCode());
			if(custCheck.getType().equals("Main")){
				cc.setMainRemark(custCheck.getMainRemark());
			}else if(custCheck.getType().equals("Soft")){
				cc.setSoftRemark(custCheck.getSoftRemark());
			}else if(custCheck.getType().equals("Fin")){
				cc.setFinRemark(custCheck.getFinRemark());
			}else if(custCheck.getType().equals("Int")){
				cc.setIntRemark(custCheck.getIntRemark());
			}
			cc.setLastUpdate(new Date());
			cc.setLastUpdatedBy(getUserContext(request).getCzybh());
			cc.setActionLog("EDIT");
			custCheckService.update(cc);
			ServletUtils.outPrintSuccess(request, response);
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "备注失败");
		}	
	}
	@RequestMapping("/doUpdateFinCpl")
	public void doUpdateFinCpl(HttpServletRequest request,HttpServletResponse response,String custCode) throws Exception{
		logger.debug("财务结算完成开始");
		try{
			CustCheck custCheck = custCheckService.get(CustCheck.class, custCode);
			if(custCheck.getFinStatus().equals("3")){
				ServletUtils.outPrintSuccess(request, response, "财务结算已完成,无需重复提交");
				return;
			}
			custCheck.setFinCplCzy(getUserContext(request).getCzybh());
			custCheck.setFinCplDate(new Date());
			custCheck.setFinStatus("3");
			custCheck.setLastUpdate(new Date());
			custCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			custCheck.setActionLog("EDIT");
			custCheckService.update(custCheck);
			Customer customer = null;
			customer = custCheckService.get(Customer.class,custCode);
			if(customer.getProjectMan()!=null){
				PersonMessage personMessage = new PersonMessage();
				personMessage.setMsgType("14");
				personMessage.setMsgText(customer.getAddress()+"楼盘:工地结算已经完成，请到结算中心领取资料。");
				personMessage.setMsgRelNo(custCode);
				personMessage.setMsgRelCustCode(custCode);
				personMessage.setCrtDate(new Date());
				personMessage.setSendDate(new Date());
				personMessage.setRcvType("1");
				personMessage.setRcvCzy(customer.getProjectMan());
				personMessage.setIsPush("1");
				personMessage.setPushStatus("0");
				personMessage.setRcvStatus("0");
				custCheckService.save(personMessage);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}	
	}	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustCheck custCheck){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custCheckService.findPageBySql(page, custCheck);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地结算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
