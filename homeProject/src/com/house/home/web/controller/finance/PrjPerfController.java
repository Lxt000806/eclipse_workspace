package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.entity.finance.PrjPerfDetail;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.finance.PrjPerfService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/prjPerf")
public class PrjPerfController extends BaseController{
	@Autowired
	private  PrjPerfService prjPerfService;
	@Autowired
	private XtcsService xtcsService;
	

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PrjPerf prjPerf) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjPerfService.findPageBySql(page, prjPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getCountPrjPerJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getCountPrjPerJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PrjPerf prjPerf) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjPerfService.getCountPrjPerJqGrid(page, prjPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getReportJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getReportJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PrjPerf prjPerf) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjPerf.setFreeBaseItem(xtcsService.getQzById("FreeBaseItem"));
		prjPerfService.getReportJqGrid(page, prjPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,PrjPerf prjPerf){
		
		return new ModelAndView("admin/finance/prjPerf/prjPerf_code").addObject("prjPerf",prjPerf);
	}
	
	/**
	 * 根据id查询PrjPerf详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPrjPerf")
	@ResponseBody
	public JSONObject getSoftPerf(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		PrjPerf prjPerf= prjPerfService.get(PrjPerf.class, id);
		if(prjPerf == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(prjPerf, true);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/finance/prjPerf/prjPerf_list");
	}
	
	@RequestMapping("/goCount")
	public ModelAndView goCount(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		return new ModelAndView("admin/finance/prjPerf/prjPerf_count").addObject("no",no)
				.addObject("m_status","A");
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		PrjPerf prjPerf= new PrjPerf();
		prjPerf = prjPerfService.get(PrjPerf.class, no);
		
		return new ModelAndView("admin/finance/prjPerf/prjPerf_count").addObject("no",no)
				.addObject("m_status","V").addObject("prjPerf", prjPerf);
	}
	
	@RequestMapping("/goReport")
	public ModelAndView goReport(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		
		return new ModelAndView("admin/finance/prjPerf/prjPerf_report").addObject("no",no);
	}
	
	@RequestMapping("/goAddPeriod")
	public ModelAndView goAddPeriod(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int y = DateUtil.getYear(new Date());
		int m = DateUtil.getMonth(new Date())+1;
		return new ModelAndView("admin/finance/prjPerf/prjPerf_addPeriod").addObject("y",y)
				.addObject("m",m);
	}
	
	@RequestMapping("/goUpdatePeriod")
	public ModelAndView goUpdatePeriod(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		PrjPerf prjPerf = null;
		prjPerf = prjPerfService.get(PrjPerf.class, no);
		
		return new ModelAndView("admin/finance/prjPerf/prjPerf_updatePeriod").addObject("prjPerf",prjPerf);
	}
	
	@RequestMapping("/goPrjPerfDetail")
	public ModelAndView goPrjPerfDetail(HttpServletRequest request,
			HttpServletResponse response,Map<String , Object> map) throws Exception {
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/prjPerf/prjPerf_prjPerfDetail").addObject("map",jsonObject);
	
	}
	
	@RequestMapping("/goUpdatePrjPerfDetail")
	public ModelAndView goUpdatePrjPerfDetail(HttpServletRequest request,
			HttpServletResponse response,Map<String , Object> map) throws Exception {
		
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/finance/prjPerf/prjPerf_updatePrjPerfDetail").addObject("map",jsonObject);
	
	}
	
	/**
	 *  新增统计周期
	 * @param request
	 * @param response
	 * @param softPerf
	 */
	@RequestMapping("/doSavePeriod")
	public void doSavePeriod(HttpServletRequest request ,
			HttpServletResponse response ,PrjPerf prjPerf){
		logger.debug("新增统计周期");
		try{
			prjPerf.setM_umState("A");
			prjPerf.setLastUpdate(new Date());
			prjPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjPerf.setExpired("F");
			prjPerf.setActionLog("Add");
			
			Result result = this.prjPerfService.savePrjPerf(prjPerf);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			} else {
				ServletUtils.outPrintFail(request, response, "新增统计周期失败");
			}
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/doUpdatePeriod")
	public void doUpdatePeriod(HttpServletRequest request ,
			HttpServletResponse response ,PrjPerf prjPerf){
		logger.debug("编辑统计周期");
		try{
			prjPerf.setM_umState("M");
			prjPerf.setLastUpdate(new Date());
			prjPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjPerf.setExpired("F");
			prjPerf.setActionLog("EDIT");

			Result result = this.prjPerfService.updatePrjPerf(prjPerf);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			} else {
				ServletUtils.outPrintFail(request, response, "新增统计周期失败");
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改统计周期失败");
		}
	}
	
	@RequestMapping("/doCount")
	public void doCount(HttpServletRequest request, HttpServletResponse response,String no, String calcType){
		logger.debug("生成业绩开始");
		
		this.prjPerfService.doCalcPerf(no, this.getUserContext(request).getCzybh(), calcType);
		
		ServletUtils.outPrintSuccess(request, response,"生成数据成功");
	}
	
	@RequestMapping("/doSaveCount")
	public void doSaveCount(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("计算完成开始");
		
		this.prjPerfService.doSaveCount(no);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doSaveCountBack")
	public void doSaveCountBack(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("退回开始开始");
		
		this.prjPerfService.doSaveCountBack(no);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doPerfChg")
	public void doPerfChg(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("设置增减开始");
		
		this.prjPerfService.doPerfChg(no,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doUpdatePrjPerfDetail")
	public void doUpdatePrjPerfDetail(HttpServletRequest request, HttpServletResponse response,PrjPerfDetail prjPerfDetail){
		logger.debug("编辑业绩明细开始");
		PrjPerfDetail ppd = new PrjPerfDetail();
		try {
			ppd = prjPerfService.get(PrjPerfDetail.class, prjPerfDetail.getPk());
			ppd.setDesignFee(prjPerfDetail.getDesignFee()==null ? 0:prjPerfDetail.getDesignFee());
			ppd.setBasePlan(prjPerfDetail.getBasePlan()==null ? 0:prjPerfDetail.getBasePlan());
			ppd.setLongFee(prjPerfDetail.getLongFee()==null ? 0:prjPerfDetail.getLongFee());
			ppd.setManageFee(prjPerfDetail.getManageFee()==null ? 0:prjPerfDetail.getManageFee());
			ppd.setSetAdd(prjPerfDetail.getSetAdd()==null ? 0:prjPerfDetail.getSetAdd());
			ppd.setSetMinus(prjPerfDetail.getSetMinus()==null ? 0:prjPerfDetail.getSetMinus());
			ppd.setMainPlan(prjPerfDetail.getMainPlan()==null ? 0:prjPerfDetail.getMainPlan());
			ppd.setIntPlan(prjPerfDetail.getIntPlan()==null ? 0:prjPerfDetail.getIntPlan());
			ppd.setCupPlan(prjPerfDetail.getCupPlan()==null ? 0:prjPerfDetail.getCupPlan());
			ppd.setSoftPlan(prjPerfDetail.getSoftPlan()==null ? 0:prjPerfDetail.getSoftPlan());
			ppd.setServPlan(prjPerfDetail.getServPlan()==null ? 0:prjPerfDetail.getServPlan());
			ppd.setFurnPlan(prjPerfDetail.getFurnPlan()==null ? 0:prjPerfDetail.getFurnPlan());
			ppd.setBaseDisc(prjPerfDetail.getBaseDisc()==null ? 0:prjPerfDetail.getBaseDisc());
			ppd.setContractFee(prjPerfDetail.getContractFee()==null ? 0:prjPerfDetail.getContractFee());
			ppd.setBaseChg(prjPerfDetail.getBaseChg()==null ? 0:prjPerfDetail.getBaseChg());
			ppd.setManageChg(prjPerfDetail.getManageChg()==null ? 0:prjPerfDetail.getManageChg());
			ppd.setDesignChg(prjPerfDetail.getDesignChg()==null ? 0:prjPerfDetail.getDesignChg());
			ppd.setChgDisc(prjPerfDetail.getChgDisc()==null ? 0:prjPerfDetail.getChgDisc());
			ppd.setMainChg(prjPerfDetail.getMainChg()==null ? 0:prjPerfDetail.getMainChg());
			ppd.setIntChg(prjPerfDetail.getIntChg()==null ? 0:prjPerfDetail.getIntChg());
			ppd.setSoftChg(prjPerfDetail.getSoftChg()==null ? 0:prjPerfDetail.getSoftChg());
			ppd.setFurnChg(prjPerfDetail.getFurnChg()==null ? 0:prjPerfDetail.getFurnChg());
			ppd.setServChg(prjPerfDetail.getServChg()==null ? 0:prjPerfDetail.getServChg());
			ppd.setCheckAmount(prjPerfDetail.getCheckAmount()==null ? 0:prjPerfDetail.getCheckAmount());
			ppd.setCheckPerf(prjPerfDetail.getCheckPerf()==null ? 0:prjPerfDetail.getCheckPerf());
			ppd.setProvideCard(prjPerfDetail.getProvideCard()==null ? 0:prjPerfDetail.getProvideCard());
			ppd.setProvideAmount(prjPerfDetail.getProvideAmount()==null ? 0:prjPerfDetail.getProvideAmount());
			ppd.setPerfPerc(prjPerfDetail.getPerfPerc()==null ? 0:prjPerfDetail.getPerfPerc());
			ppd.setPerfDisc(prjPerfDetail.getPerfDisc()==null ? 0:prjPerfDetail.getPerfDisc());
			ppd.setDelayDay(prjPerfDetail.getDelayDay()==null ? 0:prjPerfDetail.getDelayDay());
			ppd.setBaseDeduction(prjPerfDetail.getBaseDeduction()==null ? 0:prjPerfDetail.getBaseDeduction());
			ppd.setItemDeduction(prjPerfDetail.getItemDeduction()==null ? 0:prjPerfDetail.getItemDeduction());
			ppd.setSoftTokenAmount(prjPerfDetail.getSoftTokenAmount()==null ? 0:prjPerfDetail.getSoftTokenAmount());
			ppd.setGift(prjPerfDetail.getGift()==null ? 0:prjPerfDetail.getGift());
			ppd.setTax(prjPerfDetail.getTax()==null ? 0:prjPerfDetail.getTax());
			ppd.setTaxChg(prjPerfDetail.getTaxChg()==null ? 0:prjPerfDetail.getTaxChg());
			ppd.setSceneDesignerCheck(prjPerfDetail.getSceneDesignerCheck() == null
			        ? 0 : prjPerfDetail.getSceneDesignerCheck());
			ppd.setNoSceneDesignerCheck(prjPerfDetail.getNoSceneDesignerCheck() == null
			        ? 0 : prjPerfDetail.getNoSceneDesignerCheck());
			ppd.setIsModified("1");
			ppd.setLastUpdate(new Date());
			ppd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppd.setActionLog("Edit");
			ppd.setBasePersonalPlan(prjPerfDetail.getBasePersonalPlan()==null ? 0:prjPerfDetail.getBasePersonalPlan());
			ppd.setBasePersonalChg(prjPerfDetail.getBasePersonalChg()==null ? 0:prjPerfDetail.getBasePersonalChg());
			this.prjPerfService.update(ppd);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"编辑失败");
		}
		
			
	}
	
	@RequestMapping("/checkStatus")
	@ResponseBody
	public String checkStatus(HttpServletRequest request,HttpServletResponse response,
			String no){
		PrjPerf prjPerf = null ;
		prjPerf = prjPerfService.get(PrjPerf.class, no);
		if(prjPerf != null){
			return prjPerf.getStatus();
		}
		return "";
	}
	
	@RequestMapping("/getNotify")
	public void getNotify(HttpServletRequest request,HttpServletResponse response,
			String no,String beginDate,String status){
		PrjPerf prjPerf = new PrjPerf();
		prjPerf=prjPerfService.get(PrjPerf.class, no);
		if(!status.equals(prjPerf.getStatus())){
			ServletUtils.outPrintFail(request, response,"状态发生改变，请刷新数据!");
		}
		if(StringUtils.isNotBlank(prjPerfService.getNotify(beginDate))){
			ServletUtils.outPrintFail(request, response,prjPerfService.getNotify(beginDate));
		}
		ServletUtils.outPrintSuccess(request, response,"成功");
	}
	
	@RequestMapping("/doExcel")
	 public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjPerf prjPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjPerfService.findPageBySql(page, prjPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"结算工地业绩统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	
	@RequestMapping("/doCountExcel")
	 public void doCountExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjPerf prjPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjPerfService.getCountPrjPerJqGrid(page, prjPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"结算工地业绩计算_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	
	@RequestMapping("/doPrjExcel")
	 public void doPrjExcel(HttpServletRequest request ,HttpServletResponse response,
			PrjPerf prjPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjPerf.setFreeBaseItem(xtcsService.getQzById("FreeBaseItem"));
		prjPerfService.getReportJqGrid(page, prjPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"结算工地业绩明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
		
}
