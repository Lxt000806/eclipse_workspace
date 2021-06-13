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
import com.house.home.entity.salary.SalarySchemeType;
import com.house.home.service.salary.SalarySchemeTypeService;

@Controller
@RequestMapping("/admin/salarySchemeType")
public class SalarySchemeTypeController extends BaseController {

	@Autowired
	private SalarySchemeTypeService salarySchemeTypeService;

    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        return new ModelAndView("admin/salary/salarySchemeType/salarySchemeType_list");
    }

    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, SalarySchemeType salarySchemeType) {
        
        Page<Map<String, Object>> page = newPageForJqGrid(request);
        salarySchemeTypeService.findPageBySql(page, salarySchemeType);
        
        return new WebPage<Map<String, Object>>(page);
    }

}
