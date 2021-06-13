package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.FixDuty;
import com.house.home.service.project.FixDutyService;

@Controller
@RequestMapping("/admin/fixDuty")
public class FixDutyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FixDutyController.class);

	@Autowired
	private FixDutyService fixDutyService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixDuty fixDuty) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixDutyService.findPageBySql(page, fixDuty);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * FixDuty列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/fixDuty/fixDuty_list");
	}
	/**
	 * 跳转到查看FixDuty页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看FixDuty页面");
		String m_umState=request.getParameter("m_umState").toString();
		String content=request.getParameter("content").toString();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		jsonObject.put("m_umState", m_umState);
		jsonObject.put("content", content);
		return new ModelAndView("admin/project/fixDuty/fixDuty_view")
				.addObject("map", jsonObject);
	}
	
    @RequestMapping("/goDutyHistory")
    public ModelAndView goDutyHistory(HttpServletRequest request, HttpServletResponse response,
            FixDuty fixDuty) {

        return new ModelAndView("admin/project/fixDuty/fixDuty_history").addObject(fixDuty);
    }
	
	/**
	 * 跳转到查看FixDuty页面
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	/**
	 * 跳转到确认退回FixDuty页面
	 * @return
	 */
	@RequestMapping("/goRetCfm")
	public ModelAndView goRetCfm(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	/**
	 * 跳转到审批FixDuty页面
	 * @return
	 */
	@RequestMapping("/goManageCfm")
	public ModelAndView goManageCfm(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	/**
	 * 跳转到反审批FixDuty页面
	 * @return
	 */
	@RequestMapping("/goManageCb")
	public ModelAndView goManageCb(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	/**
	 * 跳转到取消FixDuty页面
	 * @return
	 */
	@RequestMapping("/goCancel")
	public ModelAndView goCancel(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	/**
	 * 跳转到新增责任人员页面
	 * @return
	 */
	@RequestMapping("/goManAdd")
	public ModelAndView goManAdd(HttpServletRequest request, HttpServletResponse response){
		String m_umState=request.getParameter("m_umState").toString();
		String no=request.getParameter("no").toString();
		String custCode = request.getParameter("custCode").toString();
		String confirmAmount = request.getParameter("confirmAmount").toString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("projectMan", request.getParameter("projectMan").toString());
		jsonObject.put("projectManDescr", request.getParameter("projectManDescr").toString());
		jsonObject.put("appWorkerCode", request.getParameter("appWorkerCode").toString());
		jsonObject.put("appManType", request.getParameter("appManType").toString());
		jsonObject.put("appManDescr", request.getParameter("appManDescr").toString());
		jsonObject.put("leftDesignRiskFund", request.getParameter("leftDesignRiskFund").toString());
		jsonObject.put("leftPrjManRiskFund", request.getParameter("leftPrjManRiskFund").toString());
		jsonObject.put("designMan", request.getParameter("designMan").toString());
		jsonObject.put("isAddDesign", request.getParameter("isAddDesign").toString());
		
		jsonObject.put("fromPage", request.getParameter("fromPage").toString());
		jsonObject.put("totalDiscBalance", request.getParameter("totalDiscBalance").toString());
		jsonObject.put("discBalance", request.getParameter("discBalance").toString());
		
		jsonObject.put("designDutyCount", fixDutyService.countDesignDuties(custCode));
		
		return new ModelAndView("admin/project/fixDuty/fixDuty_addMan")
				.addObject("m_umState", m_umState).addObject("no",no).addObject("map", jsonObject)
				.addObject("custCode", custCode).addObject("confirmAmount", confirmAmount);
	}
	/**
	 * 跳转到修改责任人员页面
	 * @return
	 */
	@RequestMapping("/goManUpdate")
	public ModelAndView goManUpdate(HttpServletRequest request, HttpServletResponse response){
		String m_umState=request.getParameter("m_umState").toString();
		String no=request.getParameter("no").toString();
		String custCode = request.getParameter("custCode").toString();
		String confirmAmount = request.getParameter("confirmAmount").toString();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		if(jsonObject != null){
			jsonObject.put("projectMan", request.getParameter("projectMan").toString());
			jsonObject.put("projectManDescr", request.getParameter("projectManDescr").toString());
			jsonObject.put("appWorkerCode", request.getParameter("appWorkerCode").toString());
			jsonObject.put("appManType", request.getParameter("appManType").toString());
			jsonObject.put("appManDescr", request.getParameter("appManDescr").toString());
			jsonObject.put("appManDescr", request.getParameter("appManDescr").toString());
			jsonObject.put("leftDesignRiskFund", request.getParameter("leftDesignRiskFund").toString());
			jsonObject.put("leftPrjManRiskFund", request.getParameter("leftPrjManRiskFund").toString());
			jsonObject.put("designMan", request.getParameter("designMan").toString());
			jsonObject.put("isAddDesign", request.getParameter("isAddDesign").toString());
			
			jsonObject.put("fromPage", request.getParameter("fromPage").toString());
			jsonObject.put("totalDiscBalance", request.getParameter("totalDiscBalance").toString());
	        jsonObject.put("discBalance", request.getParameter("discBalance").toString());
			jsonObject.put("designDutyCount", fixDutyService.countDesignDuties(custCode));
		}
		
		return new ModelAndView("admin/project/fixDuty/fixDuty_addMan")
				.addObject("map", jsonObject).addObject("m_umState", m_umState).addObject("no",no).addObject("custCode",custCode)
				.addObject("custCode", custCode).addObject("confirmAmount", confirmAmount);
	}
	/**
	 * 跳转到明细查询页面
	 * @return
	 */
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/fixDuty/fixDuty_detailQuery");
	}
	/**
	 *FixDuty导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailQueryExcel")
	public void doDetailQueryExcel(HttpServletRequest request, 
			HttpServletResponse response, FixDuty fixDuty){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		fixDutyService.goDetailQueryJqGrid(page, fixDuty);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"定责人员_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *FixDuty导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, FixDuty fixDuty){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		fixDutyService.goDetailJqGrid(page, fixDuty);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"定责项目_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查询goDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixDuty fixDuty) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixDutyService.goDetailJqGrid(page, fixDuty);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goManJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goManJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goManJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixDuty fixDuty) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixDutyService.goManJqGrid(page, fixDuty);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 已存在工资单的定责单不能反审批。
	 * @author	created by zb
	 * @date	2019-7-25--下午5:24:00
	 */
	@RequestMapping("/isWorkCostDetail")
	@ResponseBody
	public Map<String, Object> isWorkCostDetail(HttpServletRequest request,HttpServletResponse response,String no) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isHad", false);
		Boolean isHad = fixDutyService.isWorkCostDetail(no);
		map.put("isHad", isHad);
		return map;
	}
	/**
	 * 更新状态
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doUpdateOneStatus")
	public String updateOneStatus(HttpServletRequest request,
			HttpServletResponse response,FixDuty fixDuty) {
		fixDuty.setLastUpdatedBy(getUserContext(request).getCzybh());
		fixDuty.setEmpCode(getUserContext(request).getEmnum());
		Long num=fixDutyService.updateOneStatus(fixDuty);
		if(num>0 && "goConfirm".equals(fixDuty.getM_umState())){
			try {	
				this.fixDutyService.doSaveProc(fixDuty);
			} catch (Exception e) {
				
			}
		}
		return num.toString();
	}
	/**
	 * 批量更新状态
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/updateMultyStatus")
	public void updateMultyStatus(HttpServletRequest request,
			HttpServletResponse response,FixDuty fixDuty) {
		fixDuty.setLastUpdatedBy(getUserContext(request).getCzybh());
		fixDuty.setEmpCode(getUserContext(request).getEmnum());
		Result result = fixDutyService.updateMultyStatus(fixDuty);
		if(result.isSuccess()){
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		}else{
			ServletUtils.outPrintFail(request, response,"发放失败，错误信息："+result.getInfo());
		}
	}
	/**
	 * 取消
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/doCancel")
	public String cancel(HttpServletRequest request,
			HttpServletResponse response,FixDuty fixDuty) {
		fixDuty.setLastUpdatedBy(getUserContext(request).getCzybh());
		return fixDutyService.cancel(fixDuty);
	}
	/**
	 * 保存，审核，审核取消，反审核，出纳签字
	 * @param request
	 * @param response
	 * @param workCost
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,FixDuty fixDuty){
		logger.debug("保存");		
		try {
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.fixDutyService.doSaveProc(fixDuty);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doSaveForProc")
	public void doSaveForProc(HttpServletRequest request,HttpServletResponse response,FixDuty fixDuty){
		logger.debug("保存");		
		try {	
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			if(null!=fixDuty.getNo()&&""!=fixDuty.getNo()){
				fixDuty.setM_umState("M");
			}else{
				fixDuty.setM_umState("A");
			}
			fixDuty.setFromWays("E");
			fixDuty.setStatus("1");
			String xmlData = fixDuty.getDetailJson();
			Result result = this.fixDutyService.saveForProc(fixDuty,xmlData);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailQueryJqGrid(HttpServletRequest request,
			HttpServletResponse response, FixDuty fixDuty) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		fixDutyService.goDetailQueryJqGrid(page, fixDuty);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转到发放页面
	 * @return
	 */
	@RequestMapping("/goProvide")
	public ModelAndView goProvide(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("admin/project/fixDuty/fixDuty_provide");
	}
	
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request,HttpServletResponse response,FixDuty fd){
		logger.debug("保存");		
		try {	
			FixDuty fixDuty = fixDutyService.get(FixDuty.class, fd.getNo());
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			fixDuty.setStatus("3");
			fixDuty.setCfmDate(null);
			fixDuty.setCfmMan(null);
			fixDuty.setCfmMaterial(fixDuty.getMaterial());
			fixDuty.setCfmOfferPrj(fixDuty.getOfferPrj());
			fixDuty.setActionLog("Edit");
			fixDuty.setLastUpdate(new Date());
			fixDuty.setCfmReturnRemark(fd.getCfmReturnRemark());
			fixDutyService.update(fixDuty);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "已退回复核");
		}
	}
	
	@RequestMapping("/doToaudit")
	public void doToaudit(HttpServletRequest request,HttpServletResponse response,FixDuty fd){
		logger.debug("保存");		
		try {
			FixDuty fixDuty = fixDutyService.get(FixDuty.class, fd.getNo());
			List<Map<String, Object>> list = fixDutyService.checkHasFixDuty(fixDuty);
//			if(list.size()>0){
//				ServletUtils.outPrintFail(request, response, "已存在申请和取消以外状态的定责单,不能提交");
//				return;
//			}
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			fixDuty.setStatus("2");
			fixDuty.setActionLog("Edit");
			fixDuty.setLastUpdate(new Date());
			fixDutyService.update(fixDuty);
			
			Date nowDate = new Date();
			Customer customer = fixDutyService.get(Customer.class,fixDuty.getCustCode());
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("1");
			personMessage.setMsgText(customer.getAddress()+":有定责单需要确认。");
			personMessage.setMsgRelNo(fd.getNo());
			personMessage.setMsgRelCustCode(customer.getCode());
			personMessage.setCrtDate(nowDate);
			personMessage.setSendDate(nowDate);
			personMessage.setRcvType("1");
			personMessage.setRcvCzy(customer.getProjectMan());
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setProgmsgType("8");
			fixDutyService.save(personMessage);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "已退回复核");
		}
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		FixDuty fixDuty = new FixDuty();
		fixDuty.setM_umStatus("A");
		fixDuty.setAppCzy(this.getUserContext(request).getCzybh());
		fixDuty.setAppManType("2");
		fixDuty.setStatus("1");
		return new ModelAndView("admin/project/fixDuty/fixDuty_save").addObject("fixDuty", fixDuty);
	}
	@RequestMapping("/goAddDesignDuty")
	public ModelAndView goAddDesignDuty(HttpServletRequest request, HttpServletResponse response){
		FixDuty fixDuty = new FixDuty();
		fixDuty.setM_umStatus("AD");
		fixDuty.setAppCzy(this.getUserContext(request).getCzybh());
		fixDuty.setAppManType("2");
		fixDuty.setStatus("7");
		return new ModelAndView("admin/project/fixDuty/fixDuty_addDesignDuty").addObject("fixDuty", fixDuty);
	}
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,FixDuty fd){
		FixDuty fixDuty = fixDutyService.get(FixDuty.class, fd.getNo());
		if(fixDuty!=null){
			fixDuty.setM_umState("M");
			fixDuty.setAddress(fd.getAddress());
			fixDuty.setAppManType("2");
			fixDuty.setWorkerName(fd.getWorkerName());
			fixDuty.setWorkType12(fd.getWorkType12());
			fixDuty.setAppWorkerCode(fd.getWorkerCode());
			fixDuty.setAppCzy(this.getUserContext(request).getCzybh());
			fixDuty.setActionLog("Edit");
			fixDuty.setLastUpdate(new Date());
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		}
		return new ModelAndView("admin/project/fixDuty/fixDuty_save").addObject("fixDuty", fixDuty);
	}
	
	@RequestMapping("/goDetailAdd")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("workType12", request.getParameter("workType12").toString());
		jsonObject.put("custCode", request.getParameter("custCode").toString());
		jsonObject.put("m_umState", request.getParameter("m_umState").toString());
		jsonObject.put("fromPage", request.getParameter("fromPage").toString());
		return new ModelAndView("admin/project/fixDuty/fixDuty_detailSave").addObject("fixDutyDetail", jsonObject);
	}
	
	@RequestMapping("/goDetailUpdate")
	public ModelAndView goDetailUpdate(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("workType12", request.getParameter("workType12").toString());
		jsonObject.put("m_umState", request.getParameter("m_umState").toString());
		jsonObject.put("qty", request.getParameter("qty").toString());
		jsonObject.put("remarks", request.getParameter("remarks").toString());
		jsonObject.put("baseCheckItemCode", request.getParameter("baseCheckItemCode").toString());
		jsonObject.put("baseCheckItemDescr", request.getParameter("baseCheckItemDescr").toString());
		jsonObject.put("offerPri", request.getParameter("offerPri").toString());
		jsonObject.put("material", request.getParameter("material").toString());
		jsonObject.put("custCode", request.getParameter("custCode").toString());
	    jsonObject.put("fromPage", request.getParameter("fromPage").toString());
        jsonObject.put("baseItemCode", request.getParameter("baseItemCode").toString());
        jsonObject.put("baseItemDescr", request.getParameter("baseItemDescr").toString());
		return new ModelAndView("admin/project/fixDuty/fixDuty_detailSave").addObject("fixDutyDetail", jsonObject);
	}
	
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("m_umState", request.getParameter("m_umState").toString());
		jsonObject.put("qty", request.getParameter("qty").toString());
		jsonObject.put("remarks", request.getParameter("remarks").toString());
		jsonObject.put("baseCheckItemCode", request.getParameter("baseCheckItemCode").toString());
		jsonObject.put("baseCheckItemDescr", request.getParameter("baseCheckItemDescr").toString());
		jsonObject.put("fromPage", request.getParameter("fromPage").toString());
		jsonObject.put("baseItemCode", request.getParameter("baseItemCode").toString());
		jsonObject.put("baseItemDescr", request.getParameter("baseItemDescr").toString());
		return new ModelAndView("admin/project/fixDuty/fixDuty_detailSave").addObject("fixDutyDetail", jsonObject);
	}				   
	
	@RequestMapping("/getConfirmAmount")
	@ResponseBody
	public double getConfirmAmount(HttpServletRequest request,HttpServletResponse response,
			FixDuty fixDuty){
		logger.debug("ajax获取数据");  
		
		double confirmAmount = fixDutyService.getConfirmAmount(fixDuty.getCustCode(),fixDuty.getWorkerCode());
		
		return confirmAmount;
	}	
	
	/**
	 *FixDuty list导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, FixDuty fixDuty){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		fixDutyService.findPageBySql(page, fixDuty);;
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地定责管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/getOtherRiskFund")
	@ResponseBody
	public Map<String, Object>  getOtherRiskFund(HttpServletRequest request,HttpServletResponse response,
			String custCode,String no){
		logger.debug("ajax获取数据");  
		Map<String, Object> map = fixDutyService.getOtherRiskFund(custCode,no);
		return map;
	}
	
	@RequestMapping("/summarizeDiscounts")
	@ResponseBody
	public Map<String, Object> summarizeDiscounts(String custCode, String fixDutyNo) {
	    return fixDutyService.summarizeDiscounts(custCode, fixDutyNo);
	}
	
	@RequestMapping("/doSaveDeignDuty")
	public void doSaveDeignDuty(HttpServletRequest request,HttpServletResponse response,FixDuty fixDuty){
		logger.debug("保存");		
		try {	
			fixDuty.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.fixDutyService.doSaveDeignDuty(fixDuty);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
}
