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
import com.house.home.entity.basic.EmpCertification;
import com.house.home.service.basic.EmpCertificationService;

@Controller
@RequestMapping("/admin/empCertification")
public class EmpCertificationController extends BaseController{

	@Autowired
	private EmpCertificationService certificationService;
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,EmpCertification empCertification) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		certificationService.findPageBySql(page,empCertification);
		return new WebPage<Map<String,Object>>(page);
	}
}
