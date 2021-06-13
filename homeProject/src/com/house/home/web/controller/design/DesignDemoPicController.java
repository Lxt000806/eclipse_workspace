package com.house.home.web.controller.design;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.design.DesignDemoPicService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/designDemoPic")
public class DesignDemoPicController extends BaseController{
	@Autowired
	private  DesignDemoPicService designDemoPicService;

}
