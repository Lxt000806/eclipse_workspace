package com.house.home.web.controller.item;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.service.basic.ItemSetService;
import com.house.home.service.basic.MainItemSetService;

@Controller
@RequestMapping("/admin/mainItemSet")
public class MainItemSetController extends BaseController { 
	
	@Autowired
	private ItemSetService itemSetService;
	
	@Autowired
	private MainItemSetService mainItemSetService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response,
            ItemSet itemSet) {

        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, ItemSet itemSet) {

        itemSet.setItemType1("ZC");

        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        itemSetService.findPageBySql(page, itemSet);
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goDetailJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, ItemSetDetail itemSetDetail) {

        Page<Map<String, Object>> page = newPageForJqGrid(request);
        mainItemSetService.findDetailPageBySql(page, itemSetDetail);

        return new WebPage<Map<String, Object>>(page);
    }   

    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("跳转到新增主材套餐包页面");
        
        ItemSet itemSet = new ItemSet();
        
        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_save")
                .addObject("itemSet", itemSet);
    }
	
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id) {
        logger.debug("跳转到编辑主材套餐包页面");
        
        ItemSet itemSet = null;
        if (StringUtils.isNotBlank(id)) {
            itemSet = itemSetService.get(ItemSet.class, id);
        } else {
            itemSet = new ItemSet();
        }
        
        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_update")
                .addObject("itemSet", itemSet);
    }
	
    @RequestMapping("/goView")
    public ModelAndView goview(HttpServletRequest request, HttpServletResponse response, String id) {
        logger.debug("跳转到查看主材套餐包页面");
        
        ItemSet itemSet = null;
        if (StringUtils.isNotBlank(id)) {
            itemSet = itemSetService.get(ItemSet.class, id);
        } else {
            itemSet = new ItemSet();
        }
        
        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_view")
                .addObject("itemSet", itemSet);
    }
	
    @RequestMapping("/goAddDetail")
    public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response,
            String postData) {
        
        logger.debug("跳转到新增主材套餐包明细页面");
        
        JSONObject object = JSON.parseObject(postData);

        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_addDetail")
                .addObject("map", object);
    }

    @RequestMapping("/goUpdateDetail")
    public ModelAndView goaddUpdate(HttpServletRequest request, HttpServletResponse response,
            String postData) {
        
        logger.debug("跳转到修改主材套餐包明细页面");
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_updateDetail")
                .addObject("map", object);
    }
	
    @RequestMapping("/goViewDetail")
    public ModelAndView goaddview(HttpServletRequest request, HttpServletResponse response,
            String postData) {
        
        logger.debug("跳转到查看主材套餐包明细页面");
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/mainItemSet/mainItemSet_viewDetail")
                .addObject("map", object);
    }

    @RequestMapping("/goJqGridDetailadd")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGridadd(HttpServletRequest request,
            HttpServletResponse response, ItemSetDetail itemSetDetail) throws Exception {
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        itemSetService.findPageBySqlDetailadd(page, itemSetDetail);
        return new WebPage<Map<String, Object>>(page);
    }
	
    @RequestMapping("/doSave")
    public void doPurchaseSave(HttpServletRequest request, HttpServletResponse response,
            ItemSet itemSet) {
        
        logger.debug("主材套餐包新增开始");
        
        try {
            
            itemSet.setM_umState("A");
            itemSet.setLastUpdate(new Date());
            itemSet.setLastUpdatedBy(getUserContext(request).getCzybh());
            itemSet.setExpired("F");
            itemSet.setActionLog("ADD");

            Result result = mainItemSetService.doSaveByProcedure(itemSet);
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, "保存成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败：程序异常");
        }
    }
	
    @RequestMapping("/doUpdate")
    public void doitemSetUpdate(HttpServletRequest request, HttpServletResponse response,
            ItemSet itemSet) {
        
        logger.debug("主材套餐包编辑开始");
        
        try {

            itemSet.setM_umState("M");
            itemSet.setLastUpdate(new Date());
            itemSet.setLastUpdatedBy(getUserContext(request).getCzybh());
            itemSet.setActionLog("EDIT");

            Result result = mainItemSetService.doSaveByProcedure(itemSet);

            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, "保存成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            ServletUtils.outPrintFail(request, response, "保存失败：程序异常");
        }
    }

    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, ItemSet itemSet) {

        Page<Map<String, Object>> page = newPage(request);
        page.setPageSize(-1);

        itemSet.setItemType1("ZC");
        itemSetService.findPageBySql(page, itemSet);
        getExcelList(request);

        ServletUtils.flushExcelOutputStream(request, response, page.getResult(), "主材套餐包_"
                + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"), columnList, titleList,
                sumList);

    }
	
}
