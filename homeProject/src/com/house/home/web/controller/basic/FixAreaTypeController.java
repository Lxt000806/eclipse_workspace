package com.house.home.web.controller.basic;

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
import com.house.home.entity.basic.FixAreaType;
import com.house.home.service.basic.FixAreaTypeService;

@Controller
@RequestMapping(value = "admin/fixAreaType")
public class FixAreaTypeController extends BaseController{
    
    @Autowired
    private FixAreaTypeService fixAreaTypeService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(){
        
        return new ModelAndView("admin/basic/fixAreaType/fixAreaType_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest req, FixAreaType fixAreaType){
        Page<Map<String, Object>> page = this.newPageForJqGrid(req);
        fixAreaTypeService.findPageBySql(page, fixAreaType);
        return new WebPage<Map<String, Object>>(page);
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, FixAreaType fixAreaType) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        fixAreaTypeService.findPageBySql(page, fixAreaType);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "装修区域类型_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    @RequestMapping(value = "/goSave")
    public ModelAndView goSave(){
        
        return new ModelAndView("admin/basic/fixAreaType/fixAreaType_save");
    }
    
    @RequestMapping(value = "/doSave")
    public void doSave(HttpServletRequest req, HttpServletResponse resp, FixAreaType fixAreaType){
        if(fixAreaTypeService.get(FixAreaType.class, fixAreaType.getCode()) != null){
            ServletUtils.outPrintFail(req, resp, "编号已存在，请修改");
            return;
        }
        if(fixAreaTypeService.saveCheckDescrIsExist(fixAreaType.getDescr())){
            ServletUtils.outPrintFail(req, resp, "名称已存在，请修改");
            return;
        }
        try{
            fixAreaType.setActionLog("ADD");
            fixAreaType.setExpired("F");
            fixAreaType.setLastUpdate(new Date());
            fixAreaType.setLastUpdatedBy(getUserContext(req).getCzybh());
            fixAreaTypeService.save(fixAreaType);
            ServletUtils.outPrintSuccess(req, resp, "保存成功");
        }catch(Exception e){
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp, "发生错误，请重试");
        }
        
    }
    
    @RequestMapping(value = "/goView")
    public ModelAndView goView(String code){
        FixAreaType fixAreaType = fixAreaTypeService.get(FixAreaType.class, code);
        return new ModelAndView("admin/basic/fixAreaType/fixAreaType_view")
                    .addObject("fixAreaType", fixAreaType);
    }
    
    @RequestMapping(value = "/goUpdate")
    public ModelAndView goUpdate(String code){
        FixAreaType fixAreaType = fixAreaTypeService.get(FixAreaType.class, code);
        return new ModelAndView("admin/basic/fixAreaType/fixAreaType_update")
                    .addObject("fixAreaType", fixAreaType);
    }
    
    @RequestMapping(value = "/doUpdate")
    public void doUpdate(HttpServletRequest req, HttpServletResponse resp, FixAreaType fixAreaType){
        if(fixAreaTypeService.updateCheckDescrIsExist(fixAreaType.getCode(), fixAreaType.getDescr())){
            ServletUtils.outPrintFail(req, resp, "名称已存在");
            return;
        }
        try{
            fixAreaType.setActionLog("EDIT");
            fixAreaType.setLastUpdate(new Date());
            fixAreaType.setLastUpdatedBy(getUserContext(req).getCzybh());
            fixAreaTypeService.update(fixAreaType);
            ServletUtils.outPrintSuccess(req, resp, "保存成功");
        }catch(Exception e){
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp, "发生错误，请重试");
        }
    }
}
