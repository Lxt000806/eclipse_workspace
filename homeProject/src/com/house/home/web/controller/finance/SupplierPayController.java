package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.finance.SupplierPay;
import com.house.home.service.finance.SupplierPayService;
@Controller
@RequestMapping("/admin/supplierPay")
public class SupplierPayController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkQltFeeController.class);
	
	@Autowired
	private SupplierPayService supplierPayService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SupplierPay supplierPay) throws Exception{
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SupplierPay supplierPay) throws Exception{
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findDetailPageBySql(page, supplierPay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/supplierPay/supplierPay_list");
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, String m_umState) throws Exception {
		logger.debug("跳转到新增页面");
		Map<String, Object> supplierPay = new HashMap<String, Object>();
		supplierPay.put("m_umState", m_umState);
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", supplierPay);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, SupplierPay supplierPay) throws Exception {
		logger.debug("跳转到编辑页面");
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplierPay.getM_umState());
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", map);
	}
	
	@RequestMapping("/goReview")
	public ModelAndView goReview(HttpServletRequest request,
			HttpServletResponse response, SupplierPay supplierPay) throws Exception {
		logger.debug("跳转到审核页面");
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplierPay.getM_umState());
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", map);
	}
	
	@RequestMapping("/goUnreview")
	public ModelAndView goUnreview(HttpServletRequest request,
			HttpServletResponse response, SupplierPay supplierPay) throws Exception {
		logger.debug("跳转到反审核页面");
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplierPay.getM_umState());
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", map);
	}
	
	@RequestMapping("/goCashierSign")
	public ModelAndView goCashierSign(HttpServletRequest request,
			HttpServletResponse response, SupplierPay supplierPay) throws Exception {
		logger.debug("跳转到出纳签字页面");
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplierPay.getM_umState());
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", map);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SupplierPay supplierPay) throws Exception {
		logger.debug("跳转到查看页面");
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplierPayService.findPageBySql(page, supplierPay);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", supplierPay.getM_umState());
		return new ModelAndView("admin/finance/supplierPay/supplierPay_add")
			.addObject("supplierPay", map);
	}
	
	/**
	 * 跳转到录入付款总金额页面
	 */
	@RequestMapping("/goTotalPayAmount")
	public ModelAndView goTotalPayAmount(HttpServletRequest request,
			HttpServletResponse response, Double paidAmount, Double totalRemainAmount) throws Exception {
		logger.debug("跳转到录入付款总金额页面");
		return new ModelAndView("admin/finance/supplierPay/supplierPay_totalPayAmount")
			.addObject("paidAmount", paidAmount)
			.addObject("totalRemainAmount", totalRemainAmount);
	}
	
	//保存
	@RequestMapping("/doSave")
	@ResponseBody
	public void doSave(HttpServletRequest request, HttpServletResponse response,
			SupplierPay supplierPay) {
		logger.debug("保存开始");
		try {
			if (!StringUtils.isNotBlank(supplierPay.getM_umState())) {
				ServletUtils.outPrintFail(request, response, "无操作标识");
				return;
			}
			String detailJson = request.getParameter("detailJson");
			if("[]".equals(detailJson)){
				ServletUtils.outPrintFail(request, response, "detail表无数据");
				return;
			}
			Result result = this.supplierPayService.doSave(supplierPay);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	// 根据付款总金额分配本次付款
	@RequestMapping("/doSetPaidAmount")
	@ResponseBody
	public Map<String,Object> doSetPaidAmount(HttpServletRequest request, HttpServletResponse response,
			SupplierPay supplierPay) {
		logger.debug("根据付款总金额分配本次付款");
		Map<String,Object> map = new HashMap<String, Object>();
		Result result = new Result();
		try {
			List<Map<String,Object>> list = supplierPayService.doSetPaidAmount(supplierPay);
			if (list.size()>0) {
				result.setCode(list.get(0).get("ret").toString());
				result.setInfo(list.get(0).get("errmsg").toString());
			}
			map.put("result", result);
			map.put("list", list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", result);
			return map;
		}
	}
	
	// 根据结算单号获取供应商和预付款余额
	@RequestMapping("/getSplInfo")
	@ResponseBody
	public Map<String,Object> getSplInfo(HttpServletRequest request, HttpServletResponse response,
			SupplierPay supplierPay) {
		logger.debug("根据结算单号获取供应商和预付款余额");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map = supplierPayService.getSplInfo(supplierPay);
		if (null != map) {
			map.put("success", true);
		}
		return map;
	}
	
	// 付款金额是否大于应付余额 保存时用到
	@RequestMapping("/isPaidGreaterThenRemain")
	@ResponseBody
	public Boolean isPaidGreaterThenRemain(HttpServletRequest request, HttpServletResponse response,
			SupplierPay supplierPay) {
		logger.debug("付款金额是否大于应付余额 保存时用到");
		Map<String, Object> map = new HashMap<String, Object>();
		Double sumPaidAmount = new Double(0);
		Double sumRemainAmount = new Double(0);
		map = supplierPayService.getSumPaidAmount(supplierPay);
		if (null != map) {
			sumPaidAmount = Double.valueOf(map.get("SumPaidAmount").toString());
		}
		map = supplierPayService.getSumRemainAmount(supplierPay);
		if (null != map) {
			sumRemainAmount = Double.valueOf(map.get("SumRemainAmount").toString());
		}
		if (Math.abs(sumPaidAmount + supplierPay.getPaidAmount()) > Math.abs(sumRemainAmount)) {
			return true;
		}
		return false;
	}
	
	//	付款金额是否大于应付余额 审核通过时用到 
	@RequestMapping("/isPaidGreaterThenRemain2")
	@ResponseBody
	public Boolean isPaidGreaterThenRemain2(HttpServletRequest request, HttpServletResponse response,
			SupplierPay supplierPay) {
		logger.debug("付款金额是否大于应付余额 审核通过时用到");
		Map<String, Object> map = new HashMap<String, Object>();
		Double sumPaidAmount = new Double(0);
		Double sumRemainAmount = new Double(0);
		map = supplierPayService.getSumPaidAmount2(supplierPay);
		if (null != map) {
			sumPaidAmount = Double.valueOf(map.get("SumPaidAmount").toString());
		}
		map = supplierPayService.getSumRemainAmount(supplierPay);
		if (null != map) {
			sumRemainAmount = Double.valueOf(map.get("SumRemainAmount").toString());
		}
		if (Math.abs(sumPaidAmount + supplierPay.getPaidAmount()) > Math.abs(sumRemainAmount)) {
			return true;
		}
		return false;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SupplierPay supplierPay){
		supplierPay.setItemType1(this.getUserContext(request).getItemRight().trim()); //设置材料权限
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplierPayService.findPageBySql(page, supplierPay);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"供应商付款管理-"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
