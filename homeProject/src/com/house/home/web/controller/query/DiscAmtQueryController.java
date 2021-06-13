package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.FixDuty;
import com.house.home.service.basic.XtcsService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.project.FixDutyService;
import com.house.home.service.query.DiscAmtQueryService;

@Controller
@RequestMapping("/admin/discAmtQuery")
public class DiscAmtQueryController extends BaseController {

    @Autowired
    private DiscAmtQueryService discAmtQueryService;
    
    @Autowired
    private FixDutyService fixDutyService;
    @Autowired
    XtcsService xtcsService;
    
    @Autowired
    private CzybmService czybmService;
    
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response) {
        
        return new ModelAndView("admin/query/discAmtQuery/discAmtQuery_list")
                .addObject("canViewAllCustomers", canViewAllCustomers(request))
                .addObject("canViewPrjRiskFund", canViewPrjRiskFund(request));
    }
    
    private String canViewAllCustomers(HttpServletRequest request) {
        UserContext userContext = getUserContext(request);
        
        return czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0576", "查看所有客户") ? "1" : "0";
    }
    
    private String canViewPrjRiskFund(HttpServletRequest request) {
        UserContext userContext = getUserContext(request);
        
        return czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0576", "查看工程风险金") ? "1" : "0";
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        // 前端（非查看所有客户权限）查询时，客户状态仅为合同施工状态
        if (canViewAllCustomers(request).equals("0")) {
            customer.setStatus("4");
        } else {
            
            // 有查看所有客户的权限，但没有选择客户状态时只查询合同施工与归档状态记录
            if (StringUtils.isBlank(customer.getStatus())) {
                customer.setStatus("4,5");
            }
        }
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        discAmtQueryService.findPageBySql(page, customer, getUserContext(request));

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goDetail")
    public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,
            String code, String tab) {
        
        return new ModelAndView("admin/query/discAmtQuery/discAmtQuery_detail")
                .addObject("code", code)
                .addObject("tab", tab)
                .addObject("canViewPrjRiskFund", canViewPrjRiskFund(request));
    }
    
    @RequestMapping("/goExtraGiftAmountJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getExtraGiftAmountJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        discAmtQueryService.findExtraGiftAmountPageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goDiscAmountExpenseJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getDiscAmountExpenseJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        customer.setGiftInDisc(xtcsService.getQzById("giftInDisc"));
        discAmtQueryService.findDiscAmountExpensePageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goFrontendRiskFundExpenseJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getFrontendRiskFundExpenseJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        discAmtQueryService.findFrontendRiskFundExpensePageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goPrjRiskFundExpenseJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getPrjRiskFundExpenseJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        discAmtQueryService.findPrjRiskFundExpensePageBySql(page, customer);

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            Customer customer){
        
        Page<Map<String, Object>>page= newPage(request);
        page.setPageSize(-1);
        
        discAmtQueryService.findPageBySql(page, customer, getUserContext(request));
        getExcelList(request);
        
        ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                "优惠额度查询_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    /**
	 * 跳转到查看FixDuty页面
	 * @return
	 */
	@RequestMapping("/goViewFix")
	public ModelAndView goViewFix(HttpServletRequest request, HttpServletResponse response,FixDuty fixDuty){
		logger.debug("跳转到查看FixDuty页面");
		Page<Map<String, Object>> page = newPageForJqGrid(request);
		fixDutyService.findPageBySql(page, fixDuty);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/query/discAmtQuery/discAmtQuery_viewFix")
				.addObject("map", map);
	}
}
