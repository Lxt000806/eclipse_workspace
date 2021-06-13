package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.design.CustomerService;
import com.house.home.web.controller.insales.PurchaseController;

@Controller      
@RequestMapping("/admin/softNotAppQuery")
public class SoftNotAppQueryController extends BaseController{
	private static final Logger logger =LoggerFactory.getLogger(PurchaseController.class);
	
	@Autowired
	private CustomerService customerService ;
	
	/**
	 *软装未下单查询列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,Customer customer ) throws Exception{
		logger.debug("跳转到软装未下单客户信息页面");
		
		customer.setItemType1("RZ");
		return new ModelAndView("admin/query/softNotAppQuery/softNotAppQuery_list")
		.addObject("customer",customer);
	}
	
	/**
	 *查看软装未下单明细表
	 * @param  request
	 * @param  response
	 * @return 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest  request,HttpServletResponse response,
			Customer ct){
		logger.debug("跳转到软装未下单，明细表");
		Customer customer =null;
		customer= customerService.get(Customer.class, ct.getCode());
		customer.setDesignManDescr(ct.getDesignMan());
		customer.setMobile2(ct.getMobile2());
		customer.setItemType2(ct.getItemType2());
		return new ModelAndView("admin/query/softNotAppQuery/softNotAppQuery_view")
		.addObject("customer", customer);
	}
	
	@RequestMapping("/goAppSoftRemark")
	public ModelAndView goAppSoftRemark(HttpServletRequest  request,HttpServletResponse response,
			String custCode, String appSoftRemark){
		logger.debug("跳转到未下单跟踪备注录入窗口");
		return new ModelAndView("admin/query/softNotAppQuery/softNotAppQuery_appSoftRemark")
		.addObject("custCode", custCode)
		.addObject("appSoftRemark", appSoftRemark);
	}
	
	/**
	 * 未下单跟踪备注录入保存
	 * @author	created by zb
	 * @date	2018-12-8--上午10:06:13
	 * @param request
	 * @param response
	 * @param appSoftRemark
	 * @param custCode
	 */
	@RequestMapping("/doAppSoftRemarkSave")
	public void doAppSoftRemarkSave(HttpServletRequest  request,HttpServletResponse response,
			String appSoftRemark, String custCode) {
		logger.debug("未下单跟踪备注录入保存");
		if (StringUtils.isNotBlank(custCode)) {
			Customer customer = this.customerService.get(Customer.class, custCode);
			if (null != customer) {
				try {
					customer.setAppSoftRemark(appSoftRemark);
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(getUserContext(request).getCzybh());
					this.customerService.update(customer);
					ServletUtils.outPrintSuccess(request, response, "保存成功");
				} catch (Exception e) {
					e.printStackTrace();
					ServletUtils.outPrintFail(request, response, "保存失败");
				}
			} else {
				ServletUtils.outPrintFail(request, response, "无该客户信息");
			}
		} else {
			ServletUtils.outPrintFail(request, response, "无客户编号");
		}
	}
	
	/**
	 * 导出软装未下单客户信息Excel
	 * 
	 * */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer  customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		customerService.findSoftNotAppQueryPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装未下单客户信息表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
