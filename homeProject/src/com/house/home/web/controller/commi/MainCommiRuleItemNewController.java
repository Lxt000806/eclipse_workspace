package com.house.home.web.controller.commi;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.commi.MainCommiRuleItemNewService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/mainCommiRuleItemNew")
public class MainCommiRuleItemNewController extends BaseController{
	@Autowired
	private  MainCommiRuleItemNewService mainCommiRuleItemNewService;

}
