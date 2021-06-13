package com.house.home.web.controller.query;

import java.util.Date;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.DesignPicPrg;
import com.house.home.service.basic.Department1Service;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.SignInService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.query.DesignConService;

@Controller
@RequestMapping("/admin/designCon")
public class DesignConController extends BaseController {
    
    @Autowired
    private DesignConService designConService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private SignInService signInService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private Department1Service department1Service;
    
    @Autowired
    private Department2Service department2Service;
    
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) {
        
        UserContext userContext = getUserContext(request);
        String custRight = userContext.getCustRight();
        String sql = "";
        
        if (custRight.equals("3")) {
            sql = "select code, desc1 from tDepartment1 a where a.Expired = 'F'";
        } else if (custRight.equals("2")) {
            sql = "select distinct code, desc1 from tDepartment1 a "
                    + "inner join tCZYDept b on a.Code = b.Department1 and b.CZYBH = '"
                    + userContext.getCzybh() + "'";
        }
        
        return new ModelAndView("admin/query/designCon/designCon_list")
                .addObject("czybh", userContext.getCzybh())
                .addObject("sql", sql);
    }
    
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response, Customer customer) {
        
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        designConService.findPageBySql(page, customer, getUserContext(request));

        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/goView")
    public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, String> params) {
        
        Customer customer = null;
        Map<String, Object> info = null;
        
        if (StringUtils.isNotBlank(params.get("custCode"))) {
            String code = params.get("custCode");
            customer = customerService.get(Customer.class, code);
            info = designConService.getDesignConInfoByCustCode(code, getUserContext(request));
        }

        return new ModelAndView("admin/query/designCon/designCon_view")
                .addObject("params", params)
                .addObject("customer", customer == null ? new Customer() : customer)
                .addObject("info", info);
    }

    @RequestMapping("/goConJqGrid")
    @ResponseBody
    public WebPage<Map<String, Object>> goConJqGrid(HttpServletRequest request,
            HttpServletResponse response, Customer customer) {
        
        Page<Map<String, Object>> page = this.newPageForJqGrid(request);
        designConService.findConPageBySql(page, customer);
        
        return new WebPage<Map<String, Object>>(page);
    }
    
    @RequestMapping("/viewSignIn")
    public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response,
            int pk) {

        SignIn signIn = signInService.get(SignIn.class, pk);
        Customer customer = customerService.get(Customer.class, signIn.getCustCode());
        Employee employee = employeeService.get(Employee.class, signIn.getSignCzy());
        Department1 department1 = department1Service.get(Department1.class, employee.getDepartment1());
        Department2 department2 = department2Service.get(Department2.class, employee.getDepartment2());
        
        signIn.setCustAddress(customer.getAddress());
        signIn.setDepartment1(department1.getDesc1());
        signIn.setDepartment2(department2 == null ? "" : department2.getDesc1());

        if (signIn.getCustScore() != null) {
            signIn.setCustScoreSignIn(signIn.getCustScore().toString() + "星");
        }
        
        return new ModelAndView("admin/basic/signIn/signIn_detail")
                .addObject("signIn", signIn);
    }
    
    @RequestMapping("/viewCustDoc")
    public ModelAndView goDesignDocView(HttpServletRequest request, HttpServletResponse response,
            CustDoc custDoc) {
        
        Customer customer = null;
        DesignPicPrg designPicPrg = null;
        
        if (StringUtils.isNotBlank(custDoc.getCustCode())) {
            customer = customerService.get(Customer.class, custDoc.getCustCode());
            designPicPrg = designConService.get(DesignPicPrg.class, custDoc.getCustCode());
        }
        
        String url = FileUploadUtils.DOWNLOAD_URL;

        return new ModelAndView("admin/query/designCon/designCon_viewCustDoc")
                .addObject("custDoc", custDoc)
                .addObject("customer", customer)
                .addObject("designPicPrg", designPicPrg == null ? new DesignPicPrg() : designPicPrg)
                .addObject("url", url);
    }

    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest request, HttpServletResponse response, Customer customer) {

        Page<Map<String, Object>> page = this.newPage(request);
        page.setPageSize(-1);

        getExcelList(request);
        designConService.findPageBySql(page, customer, getUserContext(request));
        
        ServletUtils.flushExcelOutputStream(request, response, page.getResult(), "设计进度跟踪_"
                + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"), columnList, titleList, sumList);
    }

}
