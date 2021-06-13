package com.house.home.web.controller.finance;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.finance.IntPerf;
import com.house.home.entity.finance.PayCheckOut;
import com.house.home.entity.finance.WHCheckOut;
import com.house.home.entity.insales.GiftApp;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.finance.PayCheckOutService;

@Controller
@RequestMapping("/admin/payCheckOut")
public class PayCheckOutController extends BaseController{
    
    private static final Logger logger = LoggerFactory.getLogger(PayCheckOutController.class);
    
    @Autowired
    public PayCheckOutService payCheckOutService;
    
    /**
     * 收入记账主界面
     * 
     * @param request
     * @param response
     * @param payCheckOut
     * @return
     * @throws Exception
     */
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
            HttpServletResponse response,PayCheckOut payCheckOut) throws Exception {
        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        payCheckOutService.findPageBySql(page, payCheckOut);
        
        return new WebPage<Map<String,Object>>(page);
    }
    
    @RequestMapping("/goDetailJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
            HttpServletResponse response,PayCheckOut payCheckOut) throws Exception {
        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        payCheckOutService.findDetailPageBySql(page, payCheckOut);
        
        return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * 新增付款单记账查询页面
     * 
     * @param request
     * @param response
     * @param custPay
     * @param payCheckOut
     * @param customer
     * @return
     * @throws Exception
     */
    @RequestMapping("/goCustPayJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> goCustPayJqGrid(HttpServletRequest request, HttpServletResponse response, 
            CustPay custPay,PayCheckOut payCheckOut, Customer customer) throws Exception{
        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        payCheckOutService.findCustPayPageBySql(page, custPay, payCheckOut, customer);
        return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * 收入记账管理列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, 
            HttpServletResponse response) throws Exception{
        
        return new ModelAndView("/admin/finance/payCheckOut/payCheckOut_list");
    }
    
    /**
     * 跳转到"收入记账管理--新增账单"页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request, 
            HttpServletResponse response, PayCheckOut payCheckOut) throws Exception{
        logger.debug("跳转到'收入记账管理--新增账单'页面");
        
        // 根据存储在request中的操作员编号查询当前操作OA的员工
        Employee employee = payCheckOutService.get(Employee.class, 
                this.getUserContext(request).getEmnum());
        
        payCheckOut.setStatus("1");
        payCheckOut.setDate(new Date());
        payCheckOut.setAppCZY(this.getUserContext(request).getEmnum());
        payCheckOut.setAppCZYDescr(employee.getNameChi());
        return new ModelAndView("/admin/finance/payCheckOut/payCheckOut_save")
            .addObject("payCheckOut", payCheckOut);
    }
    /**
     * 跳转到"收入记账管理--修改账单"页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, 
            HttpServletResponse response, PayCheckOut payCheckOut) throws Exception{
        logger.debug("跳转到'收入记账管理--修改账单'页面");
        Map<String, Object> map=payCheckOutService.findMapBySql(payCheckOut);
        if("C".equals(payCheckOut.getM_umState())){
        	map.put("confirmCZY", getUserContext(request).getCzybh());
        	map.put("confirmDate", new Date());
        	map.put("checkDate", new Date());
        	map.put("confirmCZYDescr",
        			payCheckOutService.get(Employee.class,this.getUserContext(request).getEmnum()).getNameChi());
        }
        map.put("m_umState", payCheckOut.getM_umState());
        return new ModelAndView("/admin/finance/payCheckOut/payCheckOut_save")
            .addObject("payCheckOut", map);
    }
    /**
     * 跳转到"新增付款单记账"页面
     * 
     * @param request
     * @param response
     * @param arr
     * @return
     * @throws Exception
     */
    @RequestMapping("/goAdd")
    public ModelAndView goAdd(HttpServletRequest request, 
            HttpServletResponse response, String arr) throws Exception{
        logger.debug("跳转到'新增付款单记账'页面");
        PayCheckOut payCheckOut = new PayCheckOut();
        payCheckOut.setAllDetailInfo(arr);
        
        return new ModelAndView("/admin/finance/payCheckOut/payCheckOut_add")
            .addObject("payCheckOut", payCheckOut);
    }
    
    @RequestMapping("/goPrint")
    public ModelAndView goPrint(HttpServletRequest request ,
            HttpServletResponse response ,String no) throws Exception{
        
        return new ModelAndView("admin/finance/payCheckOut/payCheckOut_print").addObject("no", no);
            
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,HttpServletResponse response,
            PayCheckOut payCheckOut ){
        logger.debug("收入记账保存开始");
        try {
            payCheckOut.setLastUpdate(new Date());
            payCheckOut.setLastUpdateBy(this.getUserContext(request).getCzybh());
//            payCheckOut.setExpired("F");
//            payCheckOut.setActionLog("Add");
            String detailJson = request.getParameter("detailJson");
            payCheckOut.setDetailJson(detailJson);
            Result result = this.payCheckOutService.savePayCheckOut(payCheckOut);
            
            if (result.isSuccess()){
                ServletUtils.outPrintSuccess(request, response,"保存成功");
            }else{
                ServletUtils.outPrintFail(request, response,result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "收入记账保存失败");
        }
    }
    
    @RequestMapping("/doCheck")
    public void doCheck(HttpServletRequest request,HttpServletResponse response,
            PayCheckOut payCheckOut ){
        logger.debug("收入记账审核开始");
        try {
            payCheckOut.setLastUpdateBy(this.getUserContext(request).getCzybh());
            Result result = this.payCheckOutService.checkPayCheckOut(payCheckOut);
            if (result.isSuccess()){
                ServletUtils.outPrintSuccess(request, response,"保存成功");
            }else{
                ServletUtils.outPrintFail(request, response,result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "收入记账保存失败");
        }
    }
	/**
	 * 主页面excel
	 * @param request
	 * @param response
	 * @param intPerf
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			PayCheckOut payCheckOut){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		payCheckOutService.findPageBySql(page, payCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"收入记账_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
