package com.house.home.web.controller.insales;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.insales.PurchaseAppDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import com.house.home.entity.insales.PurchaseAppDetail;
import org.springframework.web.bind.annotation.ResponseBody;
import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/purchaseAppDetail")
public class PurchaseAppDetailController extends BaseController{
	@Autowired
	private  PurchaseAppDetailService purchaseAppDetailService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,PurchaseAppDetail purchaseAppDetail) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		purchaseAppDetailService.findPageBySql(page, purchaseAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}

}
