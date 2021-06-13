package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.basic.CustGiftItemService;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/custGiftItem")
public class CustGiftItemController extends BaseController{
	@Autowired
	private  CustGiftItemService custGiftItemService;

}
