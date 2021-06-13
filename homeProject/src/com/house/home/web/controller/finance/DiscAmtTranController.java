package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.DiscAmtTran;
import com.house.home.service.design.CustomerService;
import com.house.home.service.finance.DiscAmtTranService;
@Controller
@RequestMapping("/admin/discAmtTran")
public class DiscAmtTranController extends BaseController{
	@Autowired
	private  DiscAmtTranService discAmtTranService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, DiscAmtTran discAmtTran) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		discAmtTranService.findPageBySql(page, discAmtTran);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, DiscAmtTran discAmtTran) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		discAmtTranService.findDiscAmtTranPageBySql(page, discAmtTran);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/finance/discAmtTran/discAmtTran_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, DiscAmtTran discAmtTran) throws Exception {
		return new ModelAndView("admin/finance/discAmtTran/discAmtTran_save")
			.addObject("discAmtTran", discAmtTran);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, DiscAmtTran discAmtTran) throws Exception {
		DiscAmtTran dat = this.discAmtTranService.get(DiscAmtTran.class, discAmtTran.getPk());
		if (StringUtils.isNotBlank(dat.getCustCode())) {
			Customer customer = this.discAmtTranService.get(Customer.class, dat.getCustCode());
			if (null != customer) {
				dat.setCustName(customer.getDescr());
			}
		}
		dat.setM_umState(discAmtTran.getM_umState());
		return new ModelAndView("admin/finance/discAmtTran/discAmtTran_save")
			.addObject("discAmtTran", dat);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, DiscAmtTran discAmtTran) throws Exception {
		DiscAmtTran dat = this.discAmtTranService.get(DiscAmtTran.class, discAmtTran.getPk());
		if (StringUtils.isNotBlank(dat.getCustCode())) {
			Customer customer = this.discAmtTranService.get(Customer.class, dat.getCustCode());
			if (null != customer) {
				dat.setCustName(customer.getDescr());
			}
		}
		dat.setM_umState(discAmtTran.getM_umState());
		return new ModelAndView("admin/finance/discAmtTran/discAmtTran_save")
			.addObject("discAmtTran", dat);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			DiscAmtTran discAmtTran){
		logger.debug("新增保存");
		try {
			discAmtTran.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.discAmtTranService.doSave(discAmtTran);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			DiscAmtTran discAmtTran){
		logger.debug("编辑保存");
		try {
			discAmtTran.setActionLog("EDIT");
			discAmtTran.setLastUpdate(new Date());
			discAmtTran.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.discAmtTranService.update(discAmtTran);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	@RequestMapping("/getMaxDiscAmount")
	@ResponseBody
	public Map<String, Object> getMaxDiscAmount(String custCode){
		Map<String, Object> map = new HashMap<String, Object>();
		Customer customer = new Customer();
		Double designRiskFund = 0.0;  
		if(StringUtils.isNotBlank(custCode)){
			customer = discAmtTranService.get(Customer.class, custCode);
			map = customerService.getMaxDiscByCustCode(custCode);
			CustType custType = new CustType();
			if(customer != null){
				if (map != null) {
					double designerMaxDiscAmount = customer.getDesignerMaxDiscAmount();
					double directorMaxDiscAmount = customer.getDirectorMaxDiscAmount();
					double lpexpense = discAmtTranService.getLpExpense(custCode);
					
					map.put("DesignerMaxDiscAmount", designerMaxDiscAmount);// 设计师最高优惠额度
					map.put("DirectorMaxDiscAmount", directorMaxDiscAmount);// 总监最高优惠额度
					custType = customerService.get(CustType.class, customer.getCustType());	
					if("4".equals(customer.getStatus()) || "5".equals(customer.getStatus())){
						designRiskFund = customer.getDesignRiskFund() == null? 0.0:customer.getDesignRiskFund();
					} else {
						designRiskFund = custType.getDesignRiskFund();
					}
					map.put("DesignRiskFund", designRiskFund);// 设计师风控基金
					map.put("LpExpense",lpexpense);
				}
			}
		}
		
		return map;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, DiscAmtTran discAmtTran){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		discAmtTranService.findPageBySql(page, discAmtTran);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"优惠额度变动记录_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
