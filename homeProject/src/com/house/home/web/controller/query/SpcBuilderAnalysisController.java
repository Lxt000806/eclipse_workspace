package com.house.home.web.controller.query;

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
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.service.query.SpcBuilderAnalysisService;
@Controller
@RequestMapping("/admin/spcBuilderAnalysis")
public class SpcBuilderAnalysisController extends BaseController {
	@Autowired
	private SpcBuilderAnalysisService  spcBuilderAnalysisService;
	/**
	 * 查询spcBuilderAnalysisqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response, SpcBuilder spcBuilder) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		spcBuilderAnalysisService.findPageBySql(page, spcBuilder);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,SpcBuilder spcBuilder) throws Exception {
			spcBuilder.setDateTo(DateUtil.getNow());
		return new ModelAndView("admin/query/spcBuilderAnalysis/spcBuilderAnalysis_list")
			.addObject("spcBuilder", spcBuilder);
	}
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request, HttpServletResponse response, SpcBuilder spcBuilder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		spcBuilderAnalysisService.findPageBySql(page, spcBuilder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"专盘数据分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,SpcBuilder spcBuilder){
			/*String postData = request.getParameter("postData");
			if(StringUtils.isNotEmpty(postData)){
				JSONObject obj = JSONObject.parseObject(postData);
				purchase.setSupplierDepartment2(obj.getString("supplierDepartment2"));
			}*/
		return new ModelAndView("admin/query/spcBuilderAnalysis/spcBuilderAnalysis_detail")
		.addObject("spcBuilder", spcBuilder);		
	}

	@RequestMapping("/goSpcBuilderAnalysisDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSpcBuilderAnalysisDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response,SpcBuilder spcBuilder) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		spcBuilderAnalysisService.findSpcBuilderAnalysisDetailPageBySql(page, spcBuilder);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 明细数据导出
	 * @param request
	 * @param response
	 * @param spcBuilder
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSpcBuilderAnalysisDetailExcel")
	public  void doSpcBuilderAnalysisDetailExcel(HttpServletRequest request, HttpServletResponse response, SpcBuilder spcBuilder){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		String sExcelName="专盘数据分析_";
		if ("1".equals(spcBuilder.getSpcBuilderAnalysisType())) { //下定信息
			sExcelName=sExcelName+"下定信息 ";
		}
		if ("2".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //退订信息
			sExcelName=sExcelName+"退订信息";
		}
		if ("3".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //有效合同信息
			sExcelName=sExcelName+"有效合同信息";
		}
		if ("4".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //有家开工信息
			sExcelName=sExcelName+"有家开工信息";
		}
		if ("5".equals(spcBuilder.getSpcBuilderAnalysisType())) {  //项目经理翻单信息
			sExcelName=sExcelName+"项目经理翻单信息";
		}
		spcBuilderAnalysisService.findSpcBuilderAnalysisDetailPageBySql(page, spcBuilder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				sExcelName+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
