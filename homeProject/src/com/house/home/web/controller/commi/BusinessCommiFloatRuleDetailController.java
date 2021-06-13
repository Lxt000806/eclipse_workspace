package com.house.home.web.controller.commi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.commi.BusinessCommiFloatRuleDetail;
import com.house.home.service.commi.BusinessCommiFloatRuleDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/admin/businessCommiFloatRuleDetail")
public class BusinessCommiFloatRuleDetailController extends BaseController{
	@Autowired
	private  BusinessCommiFloatRuleDetailService businessCommiFloatRuleDetailService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,BusinessCommiFloatRuleDetail businessCommiFloatRuleDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		businessCommiFloatRuleDetailService.findPageBySql(page, businessCommiFloatRuleDetail);
		return new WebPage<Map<String,Object>>(page);
	}
}
