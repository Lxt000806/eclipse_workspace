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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;

@Controller
@RequestMapping("/admin/outEmployee")
public class OutEmployeeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OutEmployeeController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private CzybmService czybmService;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) {
        
        return new ModelAndView("admin/basic/outEmployee/outEmployee_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {
        UserContext uc = getUserContext(request);
             
        if (czybmService.hasGNQXByCzybh(uc.getCzybh(), "9044", "查看全部")) {
            employee.setViewAllOutEmps(true);
        }
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        employeeService.findPageBySql(page, employee, uc);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("跳转到新增外部人员页面");

        Employee employee = new Employee();
        employee.setJoinDate(DateUtil.getNow());
        
        return new ModelAndView("admin/basic/outEmployee/outEmployee_save")
            .addObject("employee", employee);
    }
    
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {

        try {
            
            if (StringUtils.isNotBlank(employee.getIdnum())
                    && employeeService.existsIdNumExceptNumber(employee.getIdnum(), null, "outEmployee")) {

                ServletUtils.outPrintFail(request, response, "新增外部人员信息失败：外部人员身份证号码重复");
                return;
            }
            
            employee.setM_umState("A");
            employee.setNumber(employeeService.getSeqNo("tEmployee"));
            employee.setType("3");
            employee.setLastUpdate(new Date());
            employee.setLastUpdatedBy(getUserContext(request).getCzybh());
            employee.setActionLog("ADD");
            employee.setExpired("F");
            
            Result result = employeeService.doEmpforInfo(employee);
            if ("1".equals(result.getCode())) {
                ServletUtils.outPrintSuccess(request, response, "新增外部人员信息成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "新增外部人员信息失败：程序异常");
        }

    }
    
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String number) {
        logger.debug("跳转到编辑外部人员页面");

        Employee employee = null;
        if (StringUtils.isNotBlank(number)) {
            employee = employeeService.get(Employee.class, number);
        }
        
        if (employee == null) {
            employee = new Employee();
        }
        
        return new ModelAndView("admin/basic/outEmployee/outEmployee_update")
            .addObject("employee", employee);
    }
    
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {

        try {

            if (StringUtils.isBlank(employee.getNumber())) {
                ServletUtils.outPrintFail(request, response, "更新外部人员信息失败：员工编号为空");
                return;
            }
            
            if (StringUtils.isNotBlank(employee.getIdnum())
                    && employeeService.existsIdNumExceptNumber(employee.getIdnum(), employee.getNumber(), "outEmployee")) {

                ServletUtils.outPrintFail(request, response, "更新外部人员信息失败：外部人员身份证号码重复");
                return;
            }
            
            employee.setM_umState("M");
            employee.setType("3");
            employee.setLastUpdate(new Date());
            employee.setLastUpdatedBy(getUserContext(request).getCzybh());
            employee.setActionLog("EDIT");
            
            Result result = employeeService.doEmpforInfo(employee);
            if ("1".equals(result.getCode())) {
                ServletUtils.outPrintSuccess(request, response, "外部人员信息更新成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "外部人员信息更新失败：程序异常");
        }

    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, String number) {
        logger.debug("跳转到查看外部人员页面");

        Employee employee = null;
        if (StringUtils.isNotBlank(number)) {
            employee = employeeService.get(Employee.class, number);
        }
        
        if (employee == null) {
            employee = new Employee();
        }
        
        return new ModelAndView("admin/basic/outEmployee/outEmployee_view")
            .addObject("employee", employee);
    }
    
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, Employee employee) {

            Page<Map<String, Object>> page = newPage(request);
            UserContext uc = getUserContext(request);
            page.setPageSize(-1);
            
            if (czybmService.hasGNQXByCzybh(uc.getCzybh(), "9044", "查看全部")) {
                employee.setViewAllOutEmps(true);
            }
            
            employeeService.findPageBySql(page, employee, uc);
            getExcelList(request);
            
            ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
                    "外部人员信息_"+ DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
                    columnList, titleList, sumList);

    }

}
