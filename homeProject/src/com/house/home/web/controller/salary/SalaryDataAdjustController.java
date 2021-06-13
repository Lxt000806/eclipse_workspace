package com.house.home.web.controller.salary;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.salary.SalaryDataAdjustService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/salaryDataAdjust")
public class SalaryDataAdjustController extends BaseController{
	@Autowired
	private  SalaryDataAdjustService salaryDataAdjustService;

}
