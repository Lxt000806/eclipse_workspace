package com.house.home.web.controller.commi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.service.commi.CustCommiProvideAnalysisService;

@Controller
@RequestMapping("/admin/custCommiProvideAnalysis")
public class CustCommiProvideAnalysisController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustCommiProvideAnalysisController.class);

	@Autowired
	private CustCommiProvideAnalysisService commiProvideAnalysisService;

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
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiProvideAnalysisService.findPageBySql(page, commiCustStakeholder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustCommiProvideAnalysis列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date date = DateUtil.getPreMonth(1, 1);
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMM");
		String preMon = sFormat.format(date);
		return new ModelAndView("admin/commi/custCommiProvideAnalysis/custCommiProvideAnalysis_list")
		.addObject("preMon", preMon);
	}
	
	/**
	 * 跳转到查看CustCommiProvideAnalysis页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			CommiCustStakeholder commiCustStakeholder){
		logger.debug("跳转到查看CustCommiProvideAnalysis页面");
		return new ModelAndView("admin/commi/custCommiProvideAnalysis/custCommiProvideAnalysis_view")
				.addObject("commiCustStakeholder", commiCustStakeholder)
				.addObject("costRight", getUserContext(request).getCostRight());
	}
	
	/**
	 *CustCommiProvideAnalysis导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiCustStakeholder commiCustStakeholder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiProvideAnalysisService.findPageBySql(page, commiCustStakeholder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"楼盘提成发放总额分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
