package com.house.home.web.controller.salary;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.salary.SalaryEmpAttendance;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.SalaryMonService;

@Controller
@RequestMapping("/admin/salaryMon")
public class SalaryMonController extends BaseController {

	@Autowired
	private SalaryMonService salaryMonService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/salary/salaryMon/salaryMon_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, SalaryMon salaryMon) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        salaryMonService.findPageBySql(page, salaryMon);
        
        return new WebPage<Map<String, Object>>(page);
    }

    @RequestMapping("/getSalaryMon")
    @ResponseBody
    public String getSalaryMon(HttpServletRequest request,
            HttpServletResponse response, Integer sm) {
        
    	SalaryMon salaryMon = salaryMonService.get(SalaryMon.class, sm);
        
        return salaryMon.getStatus();
    }
}
