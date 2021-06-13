package com.house.home.web.controller.supplier;

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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.GiftApp;
import com.house.home.service.insales.GiftAppService;

@Controller
@RequestMapping("/admin/supplierGiftApp")
public class SupplierGiftAppController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierGiftAppController.class);
    
    @Autowired
    private GiftAppService giftAppService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/supplier/giftApp/giftApp_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, GiftApp giftApp) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        
        giftApp.setOutType("1");
        giftApp.setSupplCode(getUserContext(request).getEmnum());
        
        giftAppService.findPageBySql(page, giftApp, getUserContext(request));
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goGiftAppDetailJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goGiftAppDetailJqGrid(
            HttpServletRequest request, HttpServletResponse response,
            GiftApp giftApp) {
        
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        giftAppService.findPageBySqlGiftAppDetail(page, giftApp);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goReceive")
    public ModelAndView goReceive(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        GiftApp giftApp = new GiftApp();
        if (StringUtils.isNotBlank(no)) {
            giftApp = giftAppService.get(GiftApp.class, no);
        }
        
        if (giftApp != null && StringUtils.isNotBlank(giftApp.getCustCode())) {
            Customer customer = giftAppService.get(Customer.class, giftApp.getCustCode());
            
            if (customer != null) {
                giftApp.setAddress(customer.getAddress());
            }
        }

        return new ModelAndView("admin/supplier/giftApp/giftApp_receive")
            .addObject("giftApp", giftApp);
    }
    
    @RequestMapping("/doReceive")
    public void doReceive(HttpServletRequest request,
            HttpServletResponse response, GiftApp giftApp) {
        logger.debug("供应商接收开始");
        
        try {
            GiftApp originalGiftApp = null;
            
            if (StringUtils.isNotBlank(giftApp.getNo())) {
                originalGiftApp = giftAppService.get(GiftApp.class, giftApp.getNo());
            } else {
                throw new RuntimeException("供应商接收时单号不能为空");
            }
            
            if (originalGiftApp == null) {
                ServletUtils.outPrintFail(request, response, "接收失败：未查询到此条记录");
                return;
            }
            
            if (StringUtils.isNotBlank(originalGiftApp.getSplStatus())
                    && !originalGiftApp.getSplStatus().equals("0")
                    && !originalGiftApp.getSplStatus().equals("1")) {
                
                ServletUtils.outPrintFail(request, response, "接收失败：未接收与接收退回状态的记录才能进行接收操作");
                return;
            }
            
            originalGiftApp.setSplStatus("2");
            
            if (StringUtils.isNotBlank(giftApp.getSplRemark())) {
                originalGiftApp.setSplRemark(giftApp.getSplRemark());
            }
            
            originalGiftApp.setSplRcvDate(new Date());
            originalGiftApp.setSplRcvCzy(getUserContext(request).getCzybh());
            originalGiftApp.setLastUpdate(new Date());
            originalGiftApp.setLastUpdatedBy(getUserContext(request).getCzybh());
            originalGiftApp.setActionLog("EDIT");
            
            giftAppService.update(originalGiftApp);
            
            ServletUtils.outPrintSuccess(request, response, "接收成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        GiftApp giftApp = new GiftApp();
        if (StringUtils.isNotBlank(no)) {
            giftApp = giftAppService.get(GiftApp.class, no);
        }
        
        if (giftApp != null && StringUtils.isNotBlank(giftApp.getCustCode())) {
            Customer customer = giftAppService.get(Customer.class, giftApp.getCustCode());
            
            if (customer != null) {
                giftApp.setAddress(customer.getAddress());
            }
        }

        return new ModelAndView("admin/supplier/giftApp/giftApp_update")
            .addObject("giftApp", giftApp);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, GiftApp giftApp) {
        logger.debug("供应商修改开始");
        
        try {
            GiftApp originalGiftApp = null;
            
            if (StringUtils.isNotBlank(giftApp.getNo())) {
                originalGiftApp = giftAppService.get(GiftApp.class, giftApp.getNo());
            } else {
                throw new RuntimeException("供应商修改时单号不能为空");
            }
            
            if (originalGiftApp == null) {
                ServletUtils.outPrintFail(request, response, "修改失败：未查询到此条记录");
                return;
            }
            
            if (StringUtils.isNotBlank(originalGiftApp.getSplStatus())
                    && !originalGiftApp.getSplStatus().equals("2")) {
                
                ServletUtils.outPrintFail(request, response, "修改失败：已接收状态的记录才能进行修改操作");
                return;
            }
                        
            if (StringUtils.isNotBlank(giftApp.getSplRemark())) {
                originalGiftApp.setSplRemark(giftApp.getSplRemark());
            }
            
            originalGiftApp.setLastUpdate(new Date());
            originalGiftApp.setLastUpdatedBy(getUserContext(request).getCzybh());
            originalGiftApp.setActionLog("EDIT");
            
            giftAppService.update(originalGiftApp);
            
            ServletUtils.outPrintSuccess(request, response, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
    }
    
    @RequestMapping("/goReturn")
    public ModelAndView goReturn(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        GiftApp giftApp = new GiftApp();
        if (StringUtils.isNotBlank(no)) {
            giftApp = giftAppService.get(GiftApp.class, no);
        }
        
        if (giftApp != null && StringUtils.isNotBlank(giftApp.getCustCode())) {
            Customer customer = giftAppService.get(Customer.class, giftApp.getCustCode());
            
            if (customer != null) {
                giftApp.setAddress(customer.getAddress());
            }
        }

        return new ModelAndView("admin/supplier/giftApp/giftApp_return")
            .addObject("giftApp", giftApp);
    }
    
    @RequestMapping("/doReturn")
    public void doReturn(HttpServletRequest request,
            HttpServletResponse response, GiftApp giftApp) {
        logger.debug("供应退回开始");
        
        try {
            GiftApp originalGiftApp = null;
            
            if (StringUtils.isNotBlank(giftApp.getNo())) {
                originalGiftApp = giftAppService.get(GiftApp.class, giftApp.getNo());
            } else {
                throw new RuntimeException("供应商退回时单号不能为空");
            }
            
            if (originalGiftApp == null) {
                ServletUtils.outPrintFail(request, response, "退回失败：未查询到此条记录");
                return;
            }
            
            if (StringUtils.isNotBlank(originalGiftApp.getSplStatus())
                    && !originalGiftApp.getSplStatus().equals("2")) {
                
                ServletUtils.outPrintFail(request, response, "退回失败：已接收状态的记录才能进行退回操作");
                return;
            }
            
            originalGiftApp.setSplStatus("1");
                        
            if (StringUtils.isNotBlank(giftApp.getSplRemark())) {
                originalGiftApp.setSplRemark(giftApp.getSplRemark());
            }
            
            originalGiftApp.setLastUpdate(new Date());
            originalGiftApp.setLastUpdatedBy(getUserContext(request).getCzybh());
            originalGiftApp.setActionLog("EDIT");
            
            giftAppService.update(originalGiftApp);
            
            ServletUtils.outPrintSuccess(request, response, "退回成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
    }
    
    @RequestMapping("/goSend")
    public ModelAndView goSend(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        GiftApp giftApp = new GiftApp();
        if (StringUtils.isNotBlank(no)) {
            giftApp = giftAppService.get(GiftApp.class, no);
        }
        
        if (giftApp != null && StringUtils.isNotBlank(giftApp.getCustCode())) {
            Customer customer = giftAppService.get(Customer.class, giftApp.getCustCode());
            
            if (customer != null) {
                giftApp.setAddress(customer.getAddress());
                giftApp.setCustMobile(customer.getMobile1());
            }
        }

        return new ModelAndView("admin/supplier/giftApp/giftApp_send")
            .addObject("giftApp", giftApp);
    }
    
    @RequestMapping("/doSend")
    public void doSend(HttpServletRequest request,
            HttpServletResponse response, GiftApp giftApp) {
        logger.debug("供应发货开始");
        
        try {
            GiftApp originalGiftApp = null;
            
            if (StringUtils.isNotBlank(giftApp.getNo())) {
                originalGiftApp = giftAppService.get(GiftApp.class, giftApp.getNo());
            } else {
                throw new RuntimeException("供应商发货时单号不能为空");
            }
            
            if (originalGiftApp == null) {
                ServletUtils.outPrintFail(request, response, "发货失败：未查询到此条记录");
                return;
            }
            
            if (StringUtils.isNotBlank(originalGiftApp.getSplStatus())
                    && !originalGiftApp.getSplStatus().equals("2")) {
                
                ServletUtils.outPrintFail(request, response, "发货失败：已接收状态的记录才能进行发货操作");
                return;
            }
            
            originalGiftApp.setM_umState("p");
            originalGiftApp.setSendCzy(getUserContext(request).getCzybh());
            originalGiftApp.setSendDate(new Date());
            
            originalGiftApp.setLastUpdate(new Date());
            originalGiftApp.setLastUpdatedBy(getUserContext(request).getCzybh());
            originalGiftApp.setActionLog("EDIT");
            
            giftAppService.doGiftAppSendBySuppForProc(originalGiftApp);
            
            ServletUtils.outPrintSuccess(request, response, "发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
    }
    
    @RequestMapping("/goView")
    public ModelAndView goview(HttpServletRequest request,
            HttpServletResponse response, String no) {
        
        GiftApp giftApp = new GiftApp();
        if (StringUtils.isNotBlank(no)) {
            giftApp = giftAppService.get(GiftApp.class, no);
        }
        
        if (giftApp != null && StringUtils.isNotBlank(giftApp.getCustCode())) {
            Customer customer = giftAppService.get(Customer.class, giftApp.getCustCode());
            
            if (customer != null) {
                giftApp.setAddress(customer.getAddress());
                giftApp.setCustMobile(customer.getMobile1());
            }
        }

        return new ModelAndView("admin/supplier/giftApp/giftApp_view")
            .addObject("giftApp", giftApp);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
