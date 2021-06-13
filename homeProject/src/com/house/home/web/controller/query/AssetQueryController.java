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
import com.house.home.bean.basic.AssetType;
import com.house.home.bean.query.AssetQuery;
import com.house.home.service.query.AssetQueryService;

@Controller
@RequestMapping("/admin/assetQuery")
public class AssetQueryController extends BaseController {

    @Autowired
    private AssetQueryService assetQueryService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response) {

        return new ModelAndView("admin/query/assetQuery/assetQuery_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            AssetQuery assetQuery) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        assetQueryService.findPageBySql(page, assetQuery, getUserContext(request));

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            AssetQuery assetQuery){
        
        Page<Map<String, Object>>page= newPage(request);
        page.setPageSize(-1);
        
        assetQueryService.findPageBySql(page, assetQuery, getUserContext(request));
        getExcelList(request);
        
        ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                "固定资产查询_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
}
