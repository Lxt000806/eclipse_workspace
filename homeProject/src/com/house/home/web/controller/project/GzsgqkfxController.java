package com.house.home.web.controller.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.impl.WorkerSignPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.project.GzsgqkfxService;

@Controller
@RequestMapping("/admin/workTypeConstructDetail")
public class GzsgqkfxController extends BaseController{
	
	@Autowired
	private GzsgqkfxService gzsgqkfxService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Customer customer,String isSign ) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		gzsgqkfxService.findPageBySql(page,customer,isSign,orderBy,direction);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goHasArrJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getHasArrJqGrid(HttpServletRequest request ,
			HttpServletResponse response,String workType12,Customer customer,String isSign ) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findHasArrPageBySql(page,workType12,customer,isSign);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doHasArrExcel")
	public void doHasArrExcel(HttpServletRequest request ,HttpServletResponse response,
			String workType12,Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findHasArrPageBySql(page,workType12,customer,isSign);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种已安排工人表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goBuilderRepJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getBuilderRepJqGrid(HttpServletRequest request ,
			HttpServletResponse response,String workType12 ,Customer customer,String isSign) throws Exception{
		
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findBuilderRepPageBySql(page,workType12,customer,isSign);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doBuilderRepExcel")
	public void doBuilderRepExcel(HttpServletRequest request ,HttpServletResponse response,
			String workType12,Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findBuilderRepPageBySql(page,workType12,customer,isSign);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种停工报备表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	                  
	@RequestMapping("/goCompleteJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getCompleteJqGrid(HttpServletRequest request ,
			HttpServletResponse response,String workType12,Customer customer,String isSign,String level,String onTimeCmp) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findCompletePageBySql(page,workType12,customer,isSign,level,onTimeCmp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doCompleteExcel")
	public void doCompleteExcel(HttpServletRequest request ,HttpServletResponse response,
			String workType12,Customer customer,String isSign,String level,String onTimeCmp){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findCompletePageBySql(page,workType12,customer,isSign,level, onTimeCmp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种施工完成情况表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Customer customer ) throws Exception{
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findDetailPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkSignPicJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goWorkSignPicJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String no) throws Exception{
		
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.getWorkSignPicBySql(page,no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goOndoJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getOndoJqGrid(HttpServletRequest request ,
			HttpServletResponse response,String workType12 ,Customer customer,String isSign) throws Exception{
		
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findOndoPageBySql(page,workType12,customer,isSign);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doOndoExcel")
	public void doOndoExcel(HttpServletRequest request ,HttpServletResponse response,
			String workType12,Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findOndoPageBySql(page,workType12,customer,isSign);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种在建工地情况表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goHasConfJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getHasConfJqGrid(HttpServletRequest request ,
			HttpServletResponse response,String workType12 ,Customer customer,String isSign) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gzsgqkfxService.findHasConfPageBySql(page,workType12,customer,isSign);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doHasConfExcel")
	public void doHasConfExcel(HttpServletRequest request ,HttpServletResponse response,
			String workType12,Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findHasConfPageBySql(page,workType12,customer,isSign);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种已验收情况表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheDay(new Date()));
		
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_list").addObject("customer",customer);
	}
	
	@RequestMapping("/goViewHasArr")
	public ModelAndView goViewHasArr(HttpServletRequest request,
			HttpServletResponse response,String workType12,Customer customer,String isSign) throws Exception {
		try {
			customer.setPrjRegionCodeDescr(gzsgqkfxService.findPrjRegionDescr(customer.getPrjRegionCode()).get("descr").toString());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_hasArr")
		.addObject("workType12",workType12)
		.addObject("customer",customer).addObject("isSign",isSign);
	}
	
	@RequestMapping("/goViewComplete")
	public ModelAndView goViewComplete(HttpServletRequest request,
			HttpServletResponse response,String workType12,Customer customer,String isSign) throws Exception {
		try {
			customer.setPrjRegionCodeDescr(gzsgqkfxService.findPrjRegionDescr(customer.getPrjRegionCode()).get("descr").toString());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_complete").addObject("workType12",workType12)
				.addObject("customer",customer).addObject("isSign",isSign);
	}
	
	@RequestMapping("/goViewDetail")
	public ModelAndView goViewDetail(HttpServletRequest request,
			HttpServletResponse response,String workerCode,String department2) throws Exception {
		
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_detail").addObject("workerCode",workerCode)
				.addObject("department2", department2);
	}
	
	@RequestMapping("/goViewOndo")
	public ModelAndView goViewOndo(HttpServletRequest request,
			HttpServletResponse response,String workType12,Customer customer,String isSign) throws Exception {
		try {
			customer.setPrjRegionCodeDescr(gzsgqkfxService.findPrjRegionDescr(customer.getPrjRegionCode()).get("descr").toString());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_ondo").addObject("workType12",workType12)
				.addObject("customer",customer).addObject("isSign",isSign);
	}
	
	@RequestMapping("/goViewHasConf")
	public ModelAndView goViewHasConf(HttpServletRequest request,
			HttpServletResponse response,String workType12,Customer customer,String isSign) throws Exception {
		try {
			customer.setPrjRegionCodeDescr(gzsgqkfxService.findPrjRegionDescr(customer.getPrjRegionCode()).get("descr").toString());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_hasConf").addObject("workType12",workType12)
				.addObject("customer",customer).addObject("isSign",isSign);
	}
	
	@RequestMapping("/goViewBuilderRep")
	public ModelAndView goViewBuilderRep(HttpServletRequest request,
			HttpServletResponse response,String workType12,Customer customer,String isSign) throws Exception {
		try {
			customer.setPrjRegionCodeDescr(gzsgqkfxService.findPrjRegionDescr(customer.getPrjRegionCode()).get("descr").toString());
		} catch (Exception e) {
		}
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_builderRep").addObject("workType12",workType12)
				.addObject("customer",customer).addObject("isSign",isSign);
	}
	/**
	 * 签到明细
	 * @author	created by zb
	 * @date	2020-2-24--下午6:01:34
	 * @param request
	 * @param response
	 * @param custCode
	 * @param workType12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewSignDetail")
	public ModelAndView goViewSignDetail(HttpServletRequest request,
			HttpServletResponse response, String custCode, String workType12) throws Exception {
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_signDetail")
			.addObject("custCode", custCode)
			.addObject("workType12",workType12);
	}
	
	@RequestMapping("/goViewChart")
	public ModelAndView goViewChart(HttpServletRequest request,
			HttpServletResponse response,String tableData) throws Exception {
		
		JSONArray array = JSONArray.fromObject(tableData);
		
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_viewChar")
			.addObject("tableData",array);
	}
	
	@RequestMapping("/goViewPic")
	public ModelAndView goViewPic(HttpServletRequest request,
			HttpServletResponse response,String no, String custCode) throws Exception {
		
		String url = getWorkerSignPicViewPath(request, custCode);
		
		return new ModelAndView("admin/project/gzsgqkfx/gzsgqkfx_view_workerSignPic")
			.addObject("no", no)
			.addObject("url", url);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		gzsgqkfxService.findPageBySql(page, customer,isSign,"","");
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"各工种施工情况分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}
	
	public static String getWorkerSignPicViewPath(HttpServletRequest request, String custCode){
		
		String path = FileUploadUtils.DOWNLOAD_URL
				+WorkerSignPicUploadRule.FIRST_LEVEL_PATH+custCode+"/";
		
		return path;
	
	}
	
}
