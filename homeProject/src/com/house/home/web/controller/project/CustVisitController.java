package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustVisit;
import com.house.home.service.project.CustVisitService;

@Controller
@RequestMapping("/admin/custVisit")
public class CustVisitController extends BaseController{
	@Autowired
	private  CustVisitService custVisitService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustVisit custVisit) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custVisitService.findPageBySql(page, custVisit);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 新建回访，客户查询
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> searchJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		// 无法兼容switch时，调用该块
		/*if ("1".equals(customer.getVisitType().trim())) {
			custVisitService.findSearchCust1BySql(page, customer);
		}else if ("4".equals(customer.getVisitType().trim())) {
			custVisitService.findSearchCust3BySql(page, customer);
		}else {
			custVisitService.findSearchCust2BySql(page, customer);
		}*/
		
		char visitType = customer.getVisitType().trim().charAt(0);
		switch (visitType) {
		case '1':
			custVisitService.findSearchCust1BySql(page, customer);
			break;
		case '4':
			custVisitService.findSearchCust3BySql(page, customer);
			break;
		default:
			custVisitService.findSearchCust2BySql(page, customer);
			break;
		}
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO 查询客户问题列表
	 * @author	created by zb
	 * @date	2018-8-3--上午11:13:23
	 * @param request
	 * @param response
	 * @param custVisit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustProblemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCustProblemJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustVisit custVisit) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custVisitService.findCustProblemByNo(page, custVisit.getNo());
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO 明细列表分页查询
	 * @author	created by zb
	 * @date	2018-8-6--上午11:44:25
	 * @param request
	 * @param response
	 * @param custVisit
	 * @param custProblem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailListJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustVisit custVisit, CustProblem custProblem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custVisitService.findDetailListPageBySql(page, custVisit, custProblem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custVisit/custVisit_list");
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/project/custVisit/custVisit_save");
	}
	
	/**
	 * @Description: TODO 跳转客户搜索页面
	 * @author	created by zb
	 * @date	2018-8-3--上午11:03:34
	 * @param request
	 * @param response
	 * @param visitType
	 * @param arr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCustSelect")
	public ModelAndView goCustSelect(HttpServletRequest request, 
			HttpServletResponse response, String visitType, String arr) throws Exception {
		/*获取月初月末*/
		Date beginD = DateUtil.startOfTheMonth(new Date());
		Date endD = DateUtil.endOfTheMonth(new Date());
		return new ModelAndView("admin/project/custVisit/custVisit_custSelect")
			.addObject("visitType", visitType)
			.addObject("arr", arr)
			.addObject("beginDate", beginD)
			.addObject("endDate", endD);
	}
	
	/**
	 * 跳转到“回访处理”页面
	 * @author	created by zb
	 * @date	2018-7-18--下午3:28:21
	 * @param request
	 * @param response
	 * @param custVisit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, 
			HttpServletResponse response, CustVisit custVisit) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//获取sql查询结果第一个数据到map中
		Map<String, Object> map = custVisitService.findPageBySql(page, custVisit).getResult().get(0);
		map.put("custtype", map.get("custtype").toString().trim());
		//通过code对工程信息进行查询
		Map<String, Object> map2 = custVisitService.findPrjItemByCode(page, custVisit.getCustCode()).getResult().get(0);
		
		return new ModelAndView("admin/project/custVisit/custVisit_update")
			.addObject("custVisit", map)
			.addObject("customer", map2);
	}
	
	/**
	 * @Description: TODO 跳转到custProblem编辑页面
	 * @author	created by zb
	 * @date	2018-8-3--上午11:02:25
	 * @param request
	 * @param response
	 * @param custProblem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/custProblem")
	public ModelAndView goCustProblem(HttpServletRequest request, 
			HttpServletResponse response, CustProblem custProblem) throws Exception {
		
		return new ModelAndView("admin/project/custVisit/custVisit_custProblem")
			.addObject("custProblem", custProblem);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, 
			HttpServletResponse response, CustVisit custVisit) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//获取sql查询结果第一个数据到map中
		Map<String, Object> map = custVisitService.findPageBySql(page, custVisit).getResult().get(0);
		map.put("custtype", map.get("custtype").toString().trim());
		//通过code对工程信息进行查询
		Map<String, Object> map2 = custVisitService.findPrjItemByCode(page, custVisit.getCustCode()).getResult().get(0);
		
		return new ModelAndView("admin/project/custVisit/custVisit_update")
			.addObject("custVisit", map)
			.addObject("customer", map2)
			.addObject("m_umState", "V");
	}
	
	/**
	 * @Description: TODO 跳转明细列表
	 * @author	created by zb
	 * @date	2018-8-6--上午10:48:39
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailList")
	public ModelAndView goDetailList(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/project/custVisit/custVisit_detailList");
	}
	
	/**
	 * 执行客户回访存储过程
	 * @author	created by zb
	 * @date	2018-7-17--下午4:54:01
	 * @param request
	 * @param response
	 * @param custVisit
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,CustVisit custVisit){
		logger.debug("回访新增保存");
		try {
			custVisit.setM_umState("A");
			custVisit.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*客户表是否有加入信息*/
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "客户表无明细");
				return;
			}
			/*执行存储过程*/
			Result result = this.custVisitService.doSave(custVisit);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增回访失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,CustVisit custVisit){
		logger.debug("回访新增保存");
		try {
			custVisit.setM_umState("M");
			custVisit.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custVisit.setVisitCZY(this.getUserContext(request).getCzybh());
			custVisit.setVisitDate(new Date());
			/*执行存储过程*/
			Result result = this.custVisitService.doUpdate(custVisit);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增回访失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除custVisit开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "回访编号不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					CustVisit custVisit = this.custVisitService.get(CustVisit.class, deleteId);
					custVisit.setExpired("T");			
					this.custVisitService.update(custVisit);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustVisit custVisit){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custVisitService.findPageBySql(page, custVisit);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"客户回访信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doExcelDetail")
	public void doExcelDetail(HttpServletRequest request, 
			HttpServletResponse response, CustVisit custVisit,CustProblem custProblem){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custVisitService.findDetailListPageBySql(page, custVisit, custProblem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"客户回访明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
