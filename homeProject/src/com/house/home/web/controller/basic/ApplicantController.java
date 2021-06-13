package com.house.home.web.controller.basic;

import java.util.Calendar;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Applicant;
import com.house.home.entity.basic.ResponseResult;
import com.house.home.service.basic.ApplicantService;

@Controller
@RequestMapping("admin/applicant")
public class ApplicantController extends BaseController{
    
    @Autowired
    private ApplicantService applicantService;

    @RequestMapping("/goList")
    public ModelAndView goList(){
        
        return new ModelAndView("admin/basic/applicant/applicant_list");
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response,Applicant applicant) throws Exception {
        
        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        applicant.setLastUpdatedBy(getUserContext(request).getCzybh());
        applicantService.findPageBySql(page, applicant);
        return new WebPage<Map<String,Object>>(page);
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, Applicant applicant) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        applicant.setLastUpdatedBy(getUserContext(req).getCzybh());
        applicantService.findPageBySql(page, applicant);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "应聘人员信息_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    /**
     * 跳转到新增应聘人员信息界面
     */
    @RequestMapping("/goSave")
    public ModelAndView goSave(Integer pk){
        
        Applicant applicant = null;
        if(pk == null){
            applicant = new Applicant();
        }else{
            applicant = applicantService.get(Applicant.class, pk);
        }
        
        return new ModelAndView("admin/basic/applicant/applicant_save")
                   .addObject("applicant", applicant);
    }
    
    /**
     * 跳转到编辑应聘人员信息界面
     */
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(Integer pk){
        Applicant applicant = applicantService.get(Applicant.class, pk);
        
        return new ModelAndView("admin/basic/applicant/applicant_update")
                   .addObject("applicant", applicant);
    }
    
    /**
     * 跳转到查看应聘人员信息界面
     */
    @RequestMapping("/goView")
    public ModelAndView goView(Integer pk){
        Applicant applicant = applicantService.get(Applicant.class, pk);
        
        //计算年龄
        if(applicant.getBirth() != null){
            try {
                int age = Applicant.calcAge(applicant.getBirth());
                applicant.setAge(age);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return new ModelAndView("admin/basic/applicant/applicant_view")
                    .addObject("applicant", applicant);
    }
    
    /**
     * 新增应聘人员信息
     */
    @RequestMapping("/doSave")
    @ResponseBody
    public ResponseResult doSave(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 Applicant applicant){
        
        ResponseResult respResult = new ResponseResult();
        try{
            //检查身份证号是否已存在
            String idNum = applicant.getIdNum();
            if(applicant.isCheckIdNumExist() && idNum != null){
                boolean idNumExist = applicantService.getByIdNum(idNum).size() != 0;
                if(idNumExist){//身份证号存在
                    respResult.setSuccess(false);
                    respResult.setErrorcode("idNumExist");
                    return respResult;
                }
            }
            applicant.setPk(null);
            //设置出生日期默认为1899-12-30
            if(applicant.getBirth() == null){
                Calendar calendar = Calendar.getInstance();
                calendar.set(1899, 11, 30);
                applicant.setBirth(calendar.getTime());
            }
            applicant.setLastUpdatedBy(getUserContext(req).getCzybh());
            applicant.setLastUpdate(new Date());
            applicant.setActionLog("ADD");
            applicant.setExpired("F");
            applicantService.save(applicant);
            respResult.setSuccess(true);
            return respResult;
        }catch(Exception e){
            e.printStackTrace();
            respResult.setSuccess(false);
            respResult.setErrorcode("exception");
            return respResult;
        }
    }
    
    /**
     * 修改应聘人员信息
     */
    @RequestMapping("/doUpdate")
    @ResponseBody
    public ResponseResult doUpdate(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   Applicant applicant){
        
        ResponseResult respResult = new ResponseResult();
        String idNum = applicant.getIdNum();
        try{
            if(applicant.isCheckIdNumExist() && idNum != null){
                //检查身份证号是否和原本的一样
                Applicant oldApplicant = applicantService.get(Applicant.class, applicant.getPk());
                if(oldApplicant.getIdNum() != null && !oldApplicant.getIdNum().equals(idNum)){
                    //身份证号和原本的不一样，检查身份证号是否已存在
                    boolean idNumExist = applicantService.getByIdNum(idNum).size() != 0;
                    if(idNumExist){//身份证号存在
                        respResult.setSuccess(false);
                        respResult.setErrorcode("idNumExist");
                        return respResult;
                    }
                }
            }
            //设置出生日期默认为1899-12-30
            if(applicant.getBirth() == null){
                Calendar calendar = Calendar.getInstance();
                calendar.set(1899, 11, 30);
                applicant.setBirth(calendar.getTime());
            }
            applicant.setLastUpdatedBy(getUserContext(req).getCzybh());
            applicant.setLastUpdate(new Date());
            applicant.setActionLog("EDIT");
            applicantService.update(applicant);
            respResult.setSuccess(true);
            return respResult;
        }catch(Exception e){
            e.printStackTrace();
            respResult.setSuccess(false);
            respResult.setErrorcode("exception");
            return respResult;
        }
    }
    
    @RequestMapping("/getApplicant")
    public JSONObject getApplicant(HttpServletRequest request,HttpServletResponse response,String id){
        if(StringUtils.isEmpty(id)){
            return this.out("传入的id为空",false);
        }
        Applicant applicant = applicantService.get(Applicant.class, Integer.parseInt(id));
        if(applicant==null){
            return this.out("系统中不存在code="+id+"的应聘人员的信息",false);
        }
            Map<String,Object> map = applicantService.findMapByCode(id);
            BeanConvertUtil.mapToBean(map, applicant);
            //resetCustomer(customer);
            return this.out(applicant, true);   
    }
    
    @RequestMapping("/goCode")
    public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response){
        String status = request.getParameter("status");
        if (status=="2") {
            status="2";
        }
        return new ModelAndView("admin/basic/applicant/applicant_code").addObject("status", status);
    }
}
