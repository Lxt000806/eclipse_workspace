package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.basic.CustGiftService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/custGift")
public class CustGiftController extends BaseController{
	@Autowired
	private  CustGiftService custGiftService;

}
