package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.project.PrjJob;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjJobService;
import com.house.home.service.project.SendNoticeService;
@Controller
@RequestMapping("/admin/sendNotice")
public class SendNoticeController extends BaseController {
	@Autowired
	private SendNoticeService sendNoticeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PrjJobService prjJobService;
	/**
	 * 查询sendNoticeService表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setItemRight(getUserContext(request).getItemRight());
		sendNoticeService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/project/sendNotice/sendNotice_list");
	}
	
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject map = JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/project/sendNotice/sendNotice_view")
				.addObject("map", map);
	}
	
	@RequestMapping("/goNotice")
	public ModelAndView goNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray rets = (JSONArray) JSONArray.parse(request.getParameter("rets"));
		return new ModelAndView("admin/project/sendNotice/sendNotice_notice")
				.addObject("rets",rets);
	}
	
	@RequestMapping("/goItemAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		sendNoticeService.goItemAppJqGrid(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *发货通知保存
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSendNotice")
	public void doSendNotice(HttpServletRequest request ,
			HttpServletResponse response , PrjJob prjJob){
		logger.debug("发货通知保存");
		try{
			prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result =sendNoticeService.doSendNotice(prjJob);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "发货通知失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		customer.setItemRight(getUserContext(request).getItemRight());
		sendNoticeService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"发货通知_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goAddNotice")
	public ModelAndView goAddNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/project/sendNotice/sendNotice_addNotice");
	}
	
	@RequestMapping(value = "/prjTypeByItemType1Auth/{type}/{pCode}") //获取任务类型
	@ResponseBody
	public JSONObject getPrjTypeByitemType1Auth(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Map<String,Object>> regionList = sendNoticeService.findPrjTypeByItemType1Auth(type,pCode,uc);
		return this.out(regionList, true);
	}
	
	@RequestMapping("/doAddNotice")
	public void doAddNotice(HttpServletRequest request ,
			HttpServletResponse response , PrjJob prjJob){
		logger.debug("发货通知保存");
		try{
			if(prjJobService.existPrjJob(prjJob.getCustCode(), prjJob.getJobType(),"1,2,3,4") != null){
				ServletUtils.outPrintFail(request, response, "存在非取消的任务单,无法重复提交");
				return;
			}
			Date nowDate = new Date();
			String no = sendNoticeService.getSeqNo("tPrjJob");
			prjJob.setNo(no);
			prjJob.setStatus("4");
			prjJob.setAppCzy(getUserContext(request).getCzybh());
			prjJob.setDate(nowDate);
			prjJob.setDealCzy(getUserContext(request).getCzybh());
			prjJob.setPlanDate(nowDate);
			prjJob.setDealDate(nowDate);
			prjJob.setEndCode("1");
			prjJob.setLastUpdate(nowDate);
			prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjJob.setExpired("F");
			prjJob.setActionLog("ADD");
			prjJobService.save(prjJob);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "发货通知失败");
		}
	}
}
