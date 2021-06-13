package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemBalAdjHeader;
import com.house.home.service.insales.ItemBalAdjDetailService;

@Controller      
@RequestMapping("/admin/itemBalAdjDetail")
public class ItemBalAdjDetailController  extends BaseController{
	
	@Autowired
	private ItemBalAdjDetailService itemBalAdjDetailService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response,ItemBalAdjDetail itemBalAdjDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBalAdjDetailService.findPageBySql(page, itemBalAdjDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request, HttpServletResponse response,ItemBalAdjDetail itemBalAdjDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBalAdjDetailService.detailQuery(page, itemBalAdjDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSupplierJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSupplerJqGrid(HttpServletRequest request, HttpServletResponse response,ItemBalAdjDetail itemBalAdjDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBalAdjDetailService.findSupplierPageBySql(page, itemBalAdjDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 仓库调整明细导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailQueryExcel")
	public void doDetailQueryExcel(HttpServletRequest request, HttpServletResponse response, ItemBalAdjDetail itemBalAdjDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		UserContext uc=getUserContext(request);
		page.setPageSize(-1);
		itemBalAdjDetailService.detailQuery(page, itemBalAdjDetail) ;
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"仓库调整明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
