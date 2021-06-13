package com.house.home.web.controller.insales;

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
import com.house.home.entity.insales.BaseItemTempDetail;
import com.house.home.service.insales.BaseItemTempDetailService;

@Controller
@RequestMapping("/admin/baseItemTempDetail")
public class BaseItemTempDetailController extends BaseController {
	@Autowired
	private BaseItemTempDetailService baseItemTempDetailService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemTempDetail baseItemTempDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemTempDetailService.findPageBySql(page, baseItemTempDetail);
		return new WebPage<Map<String,Object>>(page);
	}
}
