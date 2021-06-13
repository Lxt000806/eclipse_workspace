package com.house.home.web.controller.query;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemHasBeenShippedService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/itemHasBeenShipped")
public class ItemHasBeenShippedController extends BaseController {
    
    @Autowired
    private ItemHasBeenShippedService itemHasBeenShippedService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response, Customer customer) {
        
        customer.setDateFrom(DateUtil.startOfTheMonth(DateUtil.getToday()));
        customer.setDateTo(DateUtil.getToday());
        
        return new ModelAndView("admin/query/itemHasBeenShipped/itemHasBeenShipped_list")
                .addObject("customer", customer);
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
            HttpServletResponse response, Customer customer) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        itemHasBeenShippedService.findPageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, Customer customer) {

            Page<Map<String, Object>> page = newPage(request);
            page.setPageSize(-1);
            
            itemHasBeenShippedService.findPageBySql(page, customer);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "材料已发货楼盘明细_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
    
}
