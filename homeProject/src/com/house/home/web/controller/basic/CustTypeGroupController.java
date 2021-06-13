package com.house.home.web.controller.basic;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustTypeGroup;
import com.house.home.service.basic.CustTypeGroupService;

@Controller
@RequestMapping("/admin/custTypeGroup")
public class CustTypeGroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustTypeGroupController.class);
    
    @Autowired
    private CustTypeGroupService custTypeGroupService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        custTypeGroupService.findGroupPageBySql(page, custTypeGroup);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goGroupDetailJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goGroupDetailJqGrid(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        custTypeGroupService.findGroupDetailPageBySql(page, custTypeGroup);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {
        
        LOGGER.debug("跳转到客户类型分组新增界面");
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_save")
            .addObject("custTypeGroup", custTypeGroup);
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request, HttpServletResponse response,
            CustTypeGroup custTypeGroup) {
        
        try {

            custTypeGroup.setLastUpdate(new Date());
            custTypeGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
            custTypeGroup.setExpired("F");
            custTypeGroup.setActionLog("ADD");
            
            Result result = custTypeGroupService.saveByProcedure(custTypeGroup);

            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败：程序异常！");
        }
    }
    
    @RequestMapping("/goCopy")
    public ModelAndView goCopy(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {
        
        LOGGER.debug("跳转到客户类型分组复制界面");
        
        CustTypeGroup ctg = null;
        
        if (StringUtils.isNotBlank(custTypeGroup.getNo())) {
            ctg = custTypeGroupService.get(CustTypeGroup.class, custTypeGroup.getNo());
        }
        
        ctg = ctg != null ? ctg : new CustTypeGroup();
        
        ctg.setM_umState(custTypeGroup.getM_umState());
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_save")
            .addObject("custTypeGroup", ctg);
    }
    
    @RequestMapping("/doCopy")
    public void doCopy(HttpServletRequest request, HttpServletResponse response,
            CustTypeGroup custTypeGroup) {
        
        try {

            custTypeGroup.setLastUpdate(new Date());
            custTypeGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
            custTypeGroup.setExpired("F");
            custTypeGroup.setActionLog("ADD");
            
            Result result = custTypeGroupService.saveByProcedure(custTypeGroup);

            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败：程序异常！");
        }
    }
    
    @RequestMapping("/addGroupDetail")
    public ModelAndView addGroupDetail(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_detail_add")
            .addObject("map", object);
    }

    @RequestMapping("/updateGroupDetail")
    public ModelAndView updateGroupDetail(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_detail_update")
            .addObject("map", object);
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {
        
        LOGGER.debug("跳转到客户类型分组编辑界面");
        
        CustTypeGroup ctg = null;
        
        if (StringUtils.isNotBlank(custTypeGroup.getNo())) {
            ctg = custTypeGroupService.get(CustTypeGroup.class, custTypeGroup.getNo());
        }
        
        ctg = ctg != null ? ctg : new CustTypeGroup();
        
        ctg.setM_umState(custTypeGroup.getM_umState());
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_save")
            .addObject("custTypeGroup", ctg);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request, HttpServletResponse response,
            CustTypeGroup custTypeGroup) {
        
        try {

            custTypeGroup.setLastUpdate(new Date());
            custTypeGroup.setLastUpdatedBy(getUserContext(request).getCzybh());
            custTypeGroup.setActionLog("EDIT");
            
            Result result = custTypeGroupService.saveByProcedure(custTypeGroup);

            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败：程序异常！");
        }
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request,
            HttpServletResponse response, CustTypeGroup custTypeGroup) {
        
        LOGGER.debug("跳转到客户类型分组查看界面");
        
        CustTypeGroup ctg = null;
        
        if (StringUtils.isNotBlank(custTypeGroup.getNo())) {
            ctg = custTypeGroupService.get(CustTypeGroup.class, custTypeGroup.getNo());
        }
        
        ctg = ctg != null ? ctg : new CustTypeGroup();
        
        ctg.setM_umState(custTypeGroup.getM_umState());
        
        return new ModelAndView("admin/basic/custTypeGroup/custTypeGroup_save")
            .addObject("custTypeGroup", ctg);
    }
}
