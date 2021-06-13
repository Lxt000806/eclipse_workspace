package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.insales.ItemPlanLog;
import com.house.home.service.insales.PdcyfxService;


@Controller
@RequestMapping("/admin/pdcyfx")
public class PdcyfxController extends BaseController { 
	@Autowired
	private PdcyfxService pdcyfxService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customerDa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemBatchHeader itemBatchHeader) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		pdcyfxService.findPageBySql(page, itemBatchHeader);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 盘点差异分析查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemBatchHeader itemBatchHeader) throws Exception {
		return new ModelAndView("admin/insales/pdcyfx/pdcyfx_list").addObject("itemBatchHeader", itemBatchHeader);
	}
	
		
}
