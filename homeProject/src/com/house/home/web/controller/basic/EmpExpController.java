package com.house.home.web.controller.basic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.EmpExp;
import com.house.home.service.basic.EmpExpService;

@Controller
@RequestMapping("/admin/empExp")
public class EmpExpController extends BaseController{

	@Autowired
	private EmpExpService empExpService;
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,EmpExp empExp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		empExpService.findPageBySql(page,empExp);
		return new WebPage<Map<String,Object>>(page);
	}
}
