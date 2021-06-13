package com.house.home.web.controller.design;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.CustTag;
import com.house.home.entity.design.CustTagGroup;
import com.house.home.service.design.CustTagService;

@Controller
@RequestMapping("/admin/custTag")
public class CustTagController extends BaseController {

    @Autowired
    private CustTagService custTagService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response) {

        return new ModelAndView("admin/design/custTag/custTag_list");
    }
    
    @RequestMapping("/goJqGridList")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            CustTagGroup custTagGroup) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        custTagService.findCustTagGroupPageBySql(page, custTagGroup);

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goCustTagJqGridList")
    @ResponseBody
    public WebPage<Map<String, Object>> goCustTagJqGridList(
            HttpServletRequest request, HttpServletResponse response,
            CustTag custTag) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        custTagService.findCustTagPageBySql(page, custTag);

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
            HttpServletResponse response) {

        return new ModelAndView("admin/design/custTag/custTag_save");
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, CustTagGroup custTagGroup) {
        
        try {
            Result result = custTagService.doSave(custTagGroup, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
        
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, Integer pk) {
        
        CustTagGroup custTagGroup = new CustTagGroup();
        if (pk != null) {            
            custTagGroup = custTagService.get(CustTagGroup.class, pk);
        }

        return new ModelAndView("admin/design/custTag/custTag_update")
        .addObject("custTagGroup", custTagGroup);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, CustTagGroup custTagGroup) {
        
        try {
            Result result = custTagService.doUpdate(custTagGroup, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "保存失败，程序异常");
        }
        
    }
    
    @RequestMapping("/checkTagUsage")
    public void checkTagUsage(HttpServletRequest request,
            HttpServletResponse response, Integer tagPk) {
        
        try {
            
            if (tagPk != null) {              
                Integer count = custTagService.countTagUsage(tagPk);
                ServletUtils.outPrintSuccess(request, response, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "查询异常");
        }
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request,
            HttpServletResponse response, Integer pk) {
        
        CustTagGroup custTagGroup = new CustTagGroup();
        if (pk != null) {            
            custTagGroup = custTagService.get(CustTagGroup.class, pk);
        }

        return new ModelAndView("admin/design/custTag/custTag_view")
        .addObject("custTagGroup", custTagGroup);
    }
    
}
