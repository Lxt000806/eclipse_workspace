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
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.JobDepart;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.JobDepartService;

@Controller
@RequestMapping(value = "/admin/jobDepart")
public class JobDepartController extends BaseController{
    
    @Autowired
    private JobDepartService jobDepartService;
    
    @Autowired
    private Department2Service department2Service;

    @RequestMapping(value = "/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goJqGrid(HttpServletRequest req, JobDepart jobDepart){
        Page<Map<String, Object>> page = this.newPageForJqGrid(req);
        jobDepartService.findPageBySql(page, jobDepart);
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping(value = "/goList")
    public ModelAndView goList(){
        
        return new ModelAndView("admin/basic/jobDepart/jobDepart_list");
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, JobDepart jobDepart) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        jobDepartService.findPageBySql(page, jobDepart);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "任务部门对应表_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(){
        
        return new ModelAndView("admin/basic/jobDepart/jobDepart_save");
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest req, HttpServletResponse resp, JobDepart jobDepart){
        try{
            jobDepart.setExpired("F");
            jobDepart.setActionLog("ADD");
            jobDepart.setLastUpdate(new Date());
            jobDepart.setLastUpdatedBy(getUserContext(req).getCzybh());
            jobDepartService.save(jobDepart);
            ServletUtils.outPrintSuccess(req, resp, "保存成功");
        }catch(Exception e){
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp, "发生异常，请重试！");
        }
    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(Integer pk){
        JobDepart jobDepart = jobDepartService.get(JobDepart.class, pk);
        Department2 department2 = department2Service.get(Department2.class, jobDepart.getDepartment2());
        jobDepart.setDepartment1(department2.getDepartment1());
        return new ModelAndView("admin/basic/jobDepart/jobDepart_update")
                   .addObject("jobDepart", jobDepart);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest req, HttpServletResponse resp, JobDepart jobDepart){
        try{
            jobDepart.setActionLog("EDIT");
            jobDepart.setLastUpdate(new Date());
            jobDepart.setLastUpdatedBy(getUserContext(req).getCzybh());
            jobDepartService.update(jobDepart);
            ServletUtils.outPrintSuccess(req, resp, "保存成功");
        }catch(Exception e){
            e.printStackTrace();
            ServletUtils.outPrintFail(req, resp, "发生异常，请重试！");
        }
    }
    
    @RequestMapping("goView")
    public ModelAndView goView(Integer pk){
        JobDepart jobDepart = jobDepartService.get(JobDepart.class, pk);
        Department2 department2 = department2Service.get(Department2.class, jobDepart.getDepartment2());
        jobDepart.setDepartment1(department2.getDepartment1());
        return new ModelAndView("admin/basic/jobDepart/jobDepart_view")
                   .addObject("jobDepart", jobDepart);
    }
}
