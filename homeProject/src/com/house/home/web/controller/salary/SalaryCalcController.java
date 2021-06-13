package com.house.home.web.controller.salary;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.salary.SalaryDataAdjustBean;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryDataAdjust;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryScheme;
import com.house.home.service.salary.SalaryCalcService;

@Controller
@RequestMapping("/admin/salaryCalc")
public class SalaryCalcController extends BaseController {
	
	@Autowired
	private SalaryCalcService salaryCalcService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryCalcService.findPageBySql(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getMainPageData")
	@ResponseBody
	public List<Map<String, Object>> getMainPageData(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryCalcService.getMainPageData(salaryData);
		
		return list;
	}
	
	@RequestMapping("/getPaymentDetailPageData")
	@ResponseBody
	public List<Map<String, Object>> getPaymentDetailPageData(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = salaryCalcService.getPaymentDetailPageData(salaryData);
		
		return list;
	}
	
	@RequestMapping("/getPaymentDetail")
	@ResponseBody
	public WebPage<Map<String, Object>> getPaymentDetail(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryCalcService.getPaymentDetail(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSalaryChgJqgrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goSalaryChgJqgrid(HttpServletRequest request ,
			HttpServletResponse response, SalaryData salaryData) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		salaryCalcService.getSalaryChgByJqgrid(page, salaryData);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		Integer calcMon = salaryCalcService.getCalcMon();
		Integer scheme = salaryCalcService.getSalaryScheme();
		
		salaryData.setSalaryMon(calcMon);
		salaryData.setSalaryScheme(scheme);
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_list")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goUpdateCalcCondition")
	public ModelAndView goUpdateCalcCondition(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme !=null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}		
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_updateCalcCondition")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goCalc")
	public ModelAndView goCalc(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme !=null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}
		
		String cmpEmpNumInfo = salaryCalcService.getCmpEmpCount(salaryData.getSalaryScheme());
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_calc")
		.addObject("salaryData", salaryData).addObject("cmpEmpNumInfo", cmpEmpNumInfo);
	}
	
	@RequestMapping("/goSalaryChgList")
	public ModelAndView goSalaryChgList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme != null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_salaryChgList")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goSalaryChgUpdate")
	public ModelAndView goSalaryChgUpdate(HttpServletRequest request ,
			HttpServletResponse response,SalaryDataAdjust salaryDataAdjust){
		
		SalaryEmp salaryEmp = new SalaryEmp();
		Department1 department1 = new Department1();
		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryDataAdjust.getPk() != null){
			salaryDataAdjust = salaryCalcService.get(SalaryDataAdjust.class, salaryDataAdjust.getPk());
			if(salaryDataAdjust != null && StringUtils.isNotBlank(salaryDataAdjust.getSalaryEmp())){
				salaryEmp = salaryCalcService.get(SalaryEmp.class, salaryDataAdjust.getSalaryEmp());
				salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryDataAdjust.getSalaryScheme());
			}
		}
		if(salaryEmp != null && StringUtils.isNotBlank(salaryEmp.getDepartment1())){
			department1 = salaryCalcService.get(Department1.class, salaryEmp.getDepartment1());
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_salaryChgUpdate")
		.addObject("salaryDataAdjust", salaryDataAdjust).addObject("salaryEmp", salaryEmp)
		.addObject("department1", department1).addObject("salaryScheme", salaryScheme);
	}
	
	@RequestMapping("/goSalaryChg")
	public ModelAndView goSalaryChg(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){

		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme != null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_salaryChg")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goSalaryChgImport")
	public ModelAndView goSalaryChgImport(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){

		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme != null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_salaryChgImport")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goAddSchemeEmp")
	public ModelAndView goAddSchemeEmp(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_salaryEmp")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goSchemeEmpList")
	public ModelAndView goSchemeEmpList(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		SalaryScheme salaryScheme = new SalaryScheme();
		
		if(salaryData.getSalaryScheme() != null){
			salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
			if(salaryScheme !=null){
				salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
			}
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_schemeEmpList")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goViewCalcDescr")
	public ModelAndView goViewCalcDescr(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		if(salaryData.getPk() != null){
			salaryData = salaryCalcService.get(SalaryData.class, salaryData.getPk());
			if(salaryData!= null){
				salaryData.setCalcDescr(salaryData.getCheckInfo());
			}
		} else if(StringUtils.isNotBlank(salaryData.getDescribe())){
			salaryData.setCalcDescr(salaryData.getDescribe());
		}

		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_viewCalcDescr")
		.addObject("salaryData", salaryData);
	}
	
	@RequestMapping("/goViewDetail")
	public ModelAndView goViewDetail(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		salaryData.setIsRptShow("1");
		List<Map<String, Object>> list = salaryCalcService.getMainPageData(salaryData);
		String detailJson = "";
		if(list != null && list.size() > 0){
			for(Entry<String, Object> entry : list.get(0).entrySet()){
				if("".equals(detailJson)){
					detailJson += "{\""+entry.getKey()+"\":\""+entry.getValue()+"\"";
				} else {
					detailJson += ",\""+entry.getKey()+"\":\""+entry.getValue()+"\"";
				}
			}
		}
		detailJson += "}";
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_detail")
		.addObject("salaryData", salaryData).addObject("detailValue",detailJson);
	}
	
	
	@RequestMapping("/goPaymentQuery")
	public ModelAndView goPaymentQuery(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		String json = "[]";
		List<Map<String, Object>> subreportData = salaryCalcService.getPaymentSubreport(salaryData);
		
		if(subreportData != null){
			json = JSONArray.fromObject(subreportData).toString();
		}
		
		return new ModelAndView("admin/salary/salaryCalc/salaryCalc_paymentQuery")
		.addObject("salaryData", salaryData).addObject("subreportData", json);
	}
	
	@RequestMapping("/doCalc")
	public void doCalc(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		try{
			SalaryScheme salaryScheme = new SalaryScheme();
			
			if(salaryData.getSalaryScheme() != null){
				salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
				if(salaryScheme !=null){
					salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
				}
			}
			salaryData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = salaryCalcService.doCalc(salaryData);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "计算薪酬失败");
		}
		
	}
	
	@RequestMapping("/doChgEmpSalary")
	public void doChgEmpSalary(HttpServletRequest request ,
			HttpServletResponse response,SalaryDataAdjust salaryDataAdjust){
		
		try{
			salaryDataAdjust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = salaryCalcService.doChgEmpSalary(salaryDataAdjust);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "计算薪酬失败");
		}
	}
	
	@RequestMapping("/doChgEmpSalaryUpdate")
	public void doChgEmpSalaryUpdate(HttpServletRequest request ,
			HttpServletResponse response,SalaryDataAdjust salaryDataAdjust){
		
		try{
			salaryDataAdjust.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = salaryCalcService.doChgEmpSalaryUpdate(salaryDataAdjust);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "薪酬调整失败");
		}
	}
	
	@RequestMapping("/doDelSalaryChg")
	public void doDelSalaryChg(HttpServletRequest request ,
			HttpServletResponse response ,String pks){
		logger.debug("工人工地安排删除");
		try{
		
			salaryCalcService.doDelSalaryChg(pks);
			ServletUtils.outPrintSuccess(request, response);

		} catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	@RequestMapping("/getSalaryStatusCtrl")
	@ResponseBody
	public Map<String, Object> getSalaryStatusCtrl(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		List<Map<String, Object>> resultList = salaryCalcService.getSalaryStatusCtrl(salaryData);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if(resultList != null && resultList.size()>0){
			resultMap = resultList.get(0);
		}else{
			resultMap.put("schemeInfo", "");
			resultMap.put("status", "");
		}

		return resultMap;
	}
	
	@RequestMapping("/doCheck")
	public void doCheck(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		try{
			SalaryScheme salaryScheme = new SalaryScheme();
			
			if(salaryData.getSalaryScheme() != null){
				salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
				if(salaryScheme !=null){
					salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
				}
			}
			salaryData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = salaryCalcService.doCheck(salaryData);
			
			if (result.isSuccess()){
				
				Integer calcMon = salaryCalcService.getCalcMon();

				ServletUtils.outPrintSuccess(request, response, calcMon);
				return;
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
				
				return;
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "薪酬结算失败");
		}
		
	}
	
	@RequestMapping("/doCheckReturn")
	public void doCheckReturn(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		try{
			SalaryScheme salaryScheme = new SalaryScheme();
			
			if(salaryData.getSalaryScheme() != null){
				salaryScheme = salaryCalcService.get(SalaryScheme.class, salaryData.getSalaryScheme());
				if(salaryScheme !=null){
					salaryData.setSalarySchemeType(salaryScheme.getSalarySchemeType());
				}
			}
			salaryData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = salaryCalcService.doCheckReturn(salaryData);
			
			if (result.isSuccess()){

				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "薪酬结算退回失败");
		}
		
	}
	
	@RequestMapping("/doSchemeEmpSave")
	public void doSchemeEmpSave(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		try{
			salaryData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			salaryData.setM_umState("M");
			Result result = salaryCalcService.doSchemeEmpSave(salaryData);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "薪酬结算退回失败");
		}
		
	}
	
	@RequestMapping("/doImportSalaryChg")
	public void doImportSalaryChg(HttpServletRequest request,HttpServletResponse response,
			SalaryData salaryData){
		logger.debug("导入薪酬调整数据开始");
		try {
			salaryData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.salaryCalcService.doImportSalaryChg(salaryData) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"导入成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "导入薪酬调整失败");
		}
	}
	
	@RequestMapping("/doSalaryChgExcel")
	public void doSalaryChgExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryData salaryData){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		salaryCalcService.getSalaryChgByJqgrid(page, salaryData);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬调整列表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryData salaryData){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = salaryCalcService.getMainPageData(salaryData);
		page.setResult(list);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"员工薪酬明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SalaryDataAdjustBean> eUtils=new ExcelImportUtils<SalaryDataAdjustBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		String empCode = "";
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
			// 如果是普通 表单参数
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("身份证号");
		titleList.add("金额");
		titleList.add("备注");
		try {
			map.put("hasInvalid", false);
			List<SalaryDataAdjustBean> result=eUtils.importExcel(in, SalaryDataAdjustBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryDataAdjustBean salaryDataAdjustBean:result){
				Map<String,Object> data=new HashMap<String, Object>();

				if(StringUtils.isNotBlank(salaryDataAdjustBean.getIdNum())){
					data.put("idnum", salaryDataAdjustBean.getIdNum());
					empCode = salaryCalcService.getSalaryEmpByIdNum(salaryDataAdjustBean.getIdNum());
					if(StringUtils.isNotBlank(empCode)){
						data.put("salaryemp", empCode);
					} else {
						data.put("isinvaliddescr", "身份证号无对应的员工");
						map.put("hasInvalid", true);
					}
				}
				
				data.put("idnum", salaryDataAdjustBean.getIdNum());
				data.put("adjustamount", salaryDataAdjustBean.getAdjustValue());
				data.put("remarks", salaryDataAdjustBean.getRemarks());
			
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "数据加载完成");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo", "导入失败，请检查是否缺少列：身份证号、金额、备注");
			map.put("hasInvalid", true);
			return map;
		}
	}
	
	@RequestMapping("/isLastCheckedCtrl")
	@ResponseBody
	public String isLastCheckedCtrl(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		String result = salaryCalcService.IsLastCheckedCtrl(salaryData);
		
		ServletUtils.outPrintSuccess(request, response, result);

		return null;
	}
	
	@RequestMapping("/hasFirstCalcTime")
	@ResponseBody
	public String hasFirstCalcTime(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		String result = salaryCalcService.getFirstCalcTime(salaryData.getSalaryMon());
		
		ServletUtils.outPrintSuccess(request, response, result);

		return null;
	}
	
	@RequestMapping("/getSalaryStatus")
	@ResponseBody
	public String getSalaryStatus(HttpServletRequest request ,
			HttpServletResponse response,SalaryData salaryData){
		
		return salaryCalcService.getSalaryStatus(salaryData);
		
	}
	
	@RequestMapping("/doPaymentExcel")
	public void doPaymentExcel(HttpServletRequest request ,HttpServletResponse response,
			SalaryData salaryData){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		List<Map<String, Object>> list = salaryCalcService.getPaymentDetailPageData(salaryData);;
		page.setResult(list);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"薪酬分账_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}






















