package com.house.home.web.controller.query;

import java.util.Date;
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
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntInstallConService;

@Controller
@RequestMapping("/admin/intInstallCon")
public class IntInstallConController extends BaseController {
    
    @Autowired
    private IntInstallConService intInstallConService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("admin/query/intInstallCon/intInstallCon_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            Customer customer) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        intInstallConService.findPageBySql(page, customer, getUserContext(request));

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goDetail")
    public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,
            Customer customer) {
        
        return new ModelAndView("admin/query/intInstallCon/intInstallCon_detail")
                .addObject("customer", customer);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, Customer customer) {

            Page<Map<String, Object>> page = newPage(request);
            UserContext userContext = getUserContext(request);
            page.setPageSize(-1);
            
            intInstallConService.findPageBySql(page, customer, userContext);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "集成安装跟踪_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
}
