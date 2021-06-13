package com.house.home.web.controller.finance;

import java.util.Date;
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
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.finance.SupplCheckConfirmService;
@Controller
@RequestMapping("/admin/supplCheckConfirm")
public class SupplCheckConfirmController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkQltFeeController.class);
	
	@Autowired
	private SupplCheckConfirmService supplCheckConfirmService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Purchase purchase) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if (!StringUtils.isNotBlank(purchase.getItemType1())) {
			purchase.setItemType1(this.getUserContext(request).getItemRight());
		}
		supplCheckConfirmService.findPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 获取采购费用明细
	 * @author	created by zb
	 * @date	2019-4-4--下午3:32:15
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurFeeDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPurFeeDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Purchase purchase) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplCheckConfirmService.findPurFeeDetailPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("跳转到主页面");
		String itemRight = "'"+this.getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_list")
			.addObject("itemRight", itemRight);
	}
	
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request,
			HttpServletResponse response, Purchase purchase) throws Exception {
		logger.debug("跳转到审核页面");
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplCheckConfirmService.findPageBySql(page, purchase);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_win")
			.addObject("supplCheckConfirm", map)
			.addObject("m_umState", "C")
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	/**
	 * 反审核
	 * @author	created by zb
	 * @date	2019-5-29--下午2:54:14
	 */
	@RequestMapping("/goRetCheck")
	public ModelAndView goRetCheck(HttpServletRequest request,
			HttpServletResponse response, Purchase purchase) throws Exception {
		logger.debug("跳转到反审核页面");
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplCheckConfirmService.findPageBySql(page, purchase);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_win")
			.addObject("supplCheckConfirm", map)
			.addObject("m_umState", "R")
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Purchase purchase) throws Exception {
		logger.debug("跳转到查看页面");
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		supplCheckConfirmService.findPageBySql(page, purchase);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_win")
			.addObject("supplCheckConfirm", map)
			.addObject("m_umState", purchase.getM_umState())
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	/**
	 * 系统计算其它费用
	 * @author	created by zb
	 * @date	2019-5-29--下午5:12:45
	 */
	@RequestMapping("/genSupplOtherFee")
	public void genSupplOtherFee(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("系统计算其它费用");
		try {
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			if(!StringUtils.isNotBlank(purchase.getNo())){
				ServletUtils.outPrintFail(request, response, "采购单号为空");
				return;
			}
			Result result = this.supplCheckConfirmService.genSupplOtherFee(purchase);
			if (result.isSuccess()){
				Page<Map<String,Object>> page= this.newPageForJqGrid(request);
				Purchase p = new Purchase();
				p.setNo(purchase.getNo());
				supplCheckConfirmService.findPageBySql(page, p);
				Map<String, Object> map = page.getResult().get(0);
				ServletUtils.outPrintSuccess(request, response, "计算成功", map);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "计算失败");
		}
	}
	//保存
	@RequestMapping("/doSave")
	@ResponseBody
	public void doSave(HttpServletRequest request, HttpServletResponse response,
			Purchase purchase) {
		logger.debug("保存开始");
		try {
			if (!StringUtils.isNotBlank(purchase.getM_umState())) {
				ServletUtils.outPrintFail(request, response, "无操作标识");
				return;
			}
			Purchase p = this.supplCheckConfirmService.get(Purchase.class, purchase.getNo());
			if (null == p) {
				ServletUtils.outPrintFail(request, response, "不存在该数据");
				return;
			}
			p.setLastUpdate(new Date());
			p.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			p.setExpired("F");
			p.setActionLog("Edit");
			if ("B".equals(purchase.getM_umState())) {//退回
				p.setSplStatus("3");
				p.setCheckConfirmCZY(getUserContext(request).getCzybh());
				p.setCheckConfirmDate(new Date());
				p.setCheckConfirmRemarks(purchase.getCheckConfirmRemarks());
				this.supplCheckConfirmService.update(p);
			} else if("P".equals(purchase.getM_umState())) {//审核通过
				p.setPayRemark(purchase.getPayRemark());
				p.setSplStatus("2");
				p.setOtherCostAdj(purchase.getOtherCostAdj());
				p.setProjectOtherCost(purchase.getProjectOtherCost());
				p.setCheckConfirmCZY(getUserContext(request).getCzybh());
				p.setCheckConfirmDate(new Date());
				p.setCheckConfirmRemarks(purchase.getCheckConfirmRemarks());
				Double otherCostAdj = purchase.getOtherCostAdj() != null ? purchase.getOtherCostAdj() : 0;
				Double othercost  = p.getOtherCost() != null ? p.getOtherCost() : 0;
				Double amount = p.getAmount() != null ? p.getAmount() : 0;
				Double firstAmount = p.getFirstAmount() != null ? p.getFirstAmount() : 0;
				Double secondAmount = p.getSecondAmount() != null ? p.getSecondAmount() : 0;
				p.setRemainAmount((double) Math.round(amount + othercost + otherCostAdj - firstAmount - secondAmount));
				ItemApp itemApp = this.supplCheckConfirmService.get(ItemApp.class, purchase.getIANo());
				if (null != itemApp) {
					itemApp.setOtherCostAdj(purchase.getOtherCostAdj());
					itemApp.setProjectOtherCost(purchase.getProjectOtherCost());
					this.supplCheckConfirmService.update(itemApp);
				}
				this.supplCheckConfirmService.update(p);
			} else if ("R".equals(purchase.getM_umState())) {//反审核
				p.setSplStatus("1");
				p.setCheckConfirmCZY(null);
				p.setCheckConfirmDate(null);
				p.setCheckConfirmRemarks(null);
				p.setPayRemark(purchase.getPayRemark());
				this.supplCheckConfirmService.update(p);
			} else {
				ServletUtils.outPrintFail(request, response, "操作标识符错误");
				return;
			}
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		supplCheckConfirmService.findPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"供应商结算审核_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goItemView")
	public ModelAndView goItemView(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		logger.debug("跳转到审核页面");
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
//		supplCheckConfirmService.findPageBySql(page, purchase);
//		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_itemDetail")
//			.addObject("supplCheckConfirm", map)
			.addObject("custCode", custCode)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 * 查看材料需求
	 * @param request
	 * @param response
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemReqView")
	public ModelAndView goItemReqView(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		logger.debug("跳转到审核页面");
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		Customer customer = new Customer();
		customer.setCode(custCode);
//		supplCheckConfirmService.findPageBySql(page, purchase);
//		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/finance/supplCheckConfirm/supplCheckConfirm_itemReqDetail")
//			.addObject("supplCheckConfirm", map)
			.addObject("custCode", custCode)
			.addObject("customer",customer)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 * 是否能跳转到反审核页面
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoRetCheckBefore")
	public void getGoShortageBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			List<Map<String, Object>> list = supplCheckConfirmService.isSupplierChecked(no);
			if(list != null && list.size()>0){
				ServletUtils.outPrintFail(request, response, "供应商结算已审核，不允许进行反审核！");
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "点击失败");
			e.printStackTrace();
		}
	}
}
