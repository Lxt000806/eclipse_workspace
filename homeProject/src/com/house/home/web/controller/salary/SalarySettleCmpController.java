package com.house.home.web.controller.salary;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalarySettleCmp;
import com.house.home.service.salary.SalarySettleCmpService;

@Controller
@RequestMapping("/admin/salarySettleCmp")
public class SalarySettleCmpController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalarySettleCmpController.class);
	
	@Autowired
	private SalarySettleCmpService salarySettleCmpService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/salary/salarySettleCmp/salarySettleCmp_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, SalarySettleCmp salarySettleCmp) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        salarySettleCmpService.findPageBySql(page, salarySettleCmp);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("跳转到新增薪酬结算企业页面");
        
        return new ModelAndView("admin/salary/salarySettleCmp/salarySettleCmp_save");
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, SalarySettleCmp salarySettleCmp) {

        try {
            
            List<SalarySettleCmp> cmps =
                    salarySettleCmpService.findSalarySettleCmpsByDescr(salarySettleCmp.getDescr());
            
            for (SalarySettleCmp cmp : cmps) {
                if (cmp.getDescr().equals(salarySettleCmp.getDescr())) {
                    
                    ServletUtils.outPrintFail(request, response, "新增薪酬结算企业失败：此薪酬结算企业名称已存在");
                    return;
                }
            }
                        
            salarySettleCmp.setLastUpdate(new Date());
            salarySettleCmp.setLastUpdatedBy(getUserContext(request).getCzybh());
            salarySettleCmp.setExpired("F");
            salarySettleCmp.setActionLog("ADD");
            
            salarySettleCmpService.save(salarySettleCmp);

            ServletUtils.outPrintSuccess(request, response, "新增薪酬结算企业成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "新增薪酬结算企业失败：程序异常");
        }

    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到编辑薪酬结算企业页面");

        SalarySettleCmp salarySettleCmp = null;
        if (pk != null) {
            salarySettleCmp = salarySettleCmpService.get(SalarySettleCmp.class, pk);
        }
        
        if (salarySettleCmp == null) {
            salarySettleCmp = new SalarySettleCmp();
        }
        
        return new ModelAndView("admin/salary/salarySettleCmp/salarySettleCmp_update")
            .addObject("salarySettleCmp", salarySettleCmp);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, SalarySettleCmp salarySettleCmp) {

        try {

            if (salarySettleCmp.getPk() == null) {
                ServletUtils.outPrintFail(request, response, "更新薪酬结算企业失败：薪酬结算企业ID为空");
                return;
            }
            
            List<SalarySettleCmp> cmps =
                    salarySettleCmpService.findSalarySettleCmpsByDescr(salarySettleCmp.getDescr());
            
            for (SalarySettleCmp cmp : cmps) {
                if (cmp.getPk().intValue() != salarySettleCmp.getPk().intValue()
                        && cmp.getDescr().equals(salarySettleCmp.getDescr())) {
                    
                    ServletUtils.outPrintFail(request, response, "更新薪酬结算企业失败：此薪酬结算企业名称已存在");
                    return;
                }
            }
                        
            salarySettleCmp.setLastUpdate(new Date());
            salarySettleCmp.setLastUpdatedBy(getUserContext(request).getCzybh());
            salarySettleCmp.setActionLog("EDIT");
            
            salarySettleCmpService.update(salarySettleCmp);

            ServletUtils.outPrintSuccess(request, response, "薪酬结算企业更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "薪酬结算企业更新失败：程序异常");
        }

    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到查看薪酬结算企业页面");

        SalarySettleCmp salarySettleCmp = null;
        if (pk != null) {
            salarySettleCmp = salarySettleCmpService.get(SalarySettleCmp.class, pk);
        }
        
        if (salarySettleCmp == null) {
            salarySettleCmp = new SalarySettleCmp();
        }
        
        return new ModelAndView("admin/salary/salarySettleCmp/salarySettleCmp_view")
            .addObject("salarySettleCmp", salarySettleCmp);
    }
    
    @RequestMapping("/goEmpList")
    public ModelAndView goEmpList(HttpServletRequest request, HttpServletResponse response, Integer pk) {
        logger.debug("跳转到查看薪酬结算企业人员名单页面");
        
        return new ModelAndView("admin/salary/salarySettleCmp/salarySettleCmp_empList")
            .addObject("pk", pk);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response,
            SalarySettleCmp salarySettleCmp) {

            Page<Map<String, Object>> page = newPage(request);
            page.setPageSize(-1);
            
            salarySettleCmpService.findPageBySql(page, salarySettleCmp);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "薪酬结算企业_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }
}
