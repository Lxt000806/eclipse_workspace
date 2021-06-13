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
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.service.insales.SalesInvoiceDetailService;

@Controller      
@RequestMapping("/admin/salesInvoiceDetail")
public class SalesInvoiceDetailController extends BaseController{
	private static final Logger logger =LoggerFactory.getLogger(SalesInvoice.class);

	@Autowired
	private SalesInvoiceDetailService salesInvoiceDetailService;
		
	
	/** 
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goImportSaleJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getImportSaleJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SalesInvoiceDetail salesInvoiceDetail) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceDetailService.findImportSalePageBySql(page, salesInvoiceDetail);
		return new WebPage<Map<String,Object>>(page);
	}

}






