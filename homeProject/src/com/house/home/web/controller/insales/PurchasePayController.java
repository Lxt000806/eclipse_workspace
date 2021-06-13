package com.house.home.web.controller.insales;

import java.util.Map;

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
import com.house.home.entity.insales.PurchasePay;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.service.insales.PurchasePayService;

@Controller      
@RequestMapping("/admin/purchasePay")
public class PurchasePayController extends BaseController{

	private static final Logger logger =LoggerFactory.getLogger(SalesInvoice.class);
	
	@Autowired
	private	PurchasePayService purchasePayService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,PurchasePay purchasePay) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchasePayService.findPageBySql(page, purchasePay);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
}
