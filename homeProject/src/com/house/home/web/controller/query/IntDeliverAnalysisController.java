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
import com.house.home.entity.project.SupplJob;
import com.house.home.service.query.IntDeliverAnalysisService;

@Controller
@RequestMapping("/admin/intDeliverAnalysis")
public class IntDeliverAnalysisController extends BaseController {
    
    @Autowired
    private IntDeliverAnalysisService intDeliverAnalysisService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("admin/query/intDeliverAnalysis/intDeliverAnalysis_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            SupplJob supplJob) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        intDeliverAnalysisService.findPageBySql(page, supplJob, getUserContext(request));

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, SupplJob supplJob) {

            Page<Map<String, Object>> page = newPage(request);
            UserContext userContext = getUserContext(request);
            page.setPageSize(-1);
            
            intDeliverAnalysisService.findPageBySql(page, supplJob, userContext);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "集成出货分析_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
}
