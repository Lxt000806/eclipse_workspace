package com.house.home.web.controller.insales;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseDelay;
import com.house.home.service.insales.PurchaseDelayService;


@Controller      
@RequestMapping("/admin/purchaseDelay")
public class PurchaseDelayController extends BaseController{
	private static final Logger logger =LoggerFactory.getLogger(PurchaseDelayController.class);
	
	@Autowired
	private PurchaseDelayService purchaseDelayService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PurchaseDelay purchaseDelay) throws Exception{
		Page<Map<String, Object>> page= this.newPageForJqGrid(request);
		purchaseDelayService.findPageBySql(page, purchaseDelay);
		return new WebPage<Map<String,Object>>(page);
	}

	

}
	

