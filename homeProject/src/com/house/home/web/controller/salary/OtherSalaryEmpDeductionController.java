package com.house.home.web.controller.salary;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.excel.ExcelImportUtils;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.design.SalaryEmpDeductionBean;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.FinSalaryEmpDeductionService;
import com.house.home.service.salary.OtherSalaryEmpDeductionService;
import com.house.home.service.salary.SalaryEmpService;

@Controller
@RequestMapping("/admin/otherSalaryEmpDeduction")
public class OtherSalaryEmpDeductionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(OtherSalaryEmpDeductionController.class);

	@Autowired
	private OtherSalaryEmpDeductionService otherSalaryEmpDeductionService;
	
	@Autowired
	private FinSalaryEmpDeductionService finSalaryEmpDeductionService;
	
	@Autowired
	private SalaryEmpService salaryEmpService;

	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, SalaryEmpDeduction otherSalaryEmpDeduction) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		otherSalaryEmpDeductionService.findPageBySql(page, otherSalaryEmpDeduction);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * OtherSalaryEmpDeduction??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_list");
	}
	/**
	 * OtherSalaryEmpDeduction??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_code");
	}
	/**
	 * ???????????????OtherSalaryEmpDeduction??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????OtherSalaryEmpDeduction??????");
		SalaryEmpDeduction otherSalaryEmpDeduction = null;
		if (StringUtils.isNotBlank(id)){
			otherSalaryEmpDeduction = otherSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
			otherSalaryEmpDeduction.setPk(null);
		}else{
			otherSalaryEmpDeduction = new SalaryEmpDeduction();
		}
		
		//?????????????????????
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMM");
		Date date=new Date();
		String salaryMon=simpleDateFormat.format(date);
		otherSalaryEmpDeduction.setSalaryMon(Integer.parseInt(salaryMon));
		
		otherSalaryEmpDeduction.setM_umState("A");
		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_save")
			.addObject("otherSalaryEmpDeduction", otherSalaryEmpDeduction);
	}
	/**
	 * ???????????????OtherSalaryEmpDeduction??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????OtherSalaryEmpDeduction??????");
		SalaryEmpDeduction otherSalaryEmpDeduction = otherSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
		otherSalaryEmpDeduction.setM_umState("M");
		SalaryEmp salaryEmp = otherSalaryEmpDeductionService.get(SalaryEmp.class, otherSalaryEmpDeduction.getSalaryEmp());
		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_save")
				.addObject("otherSalaryEmpDeduction", otherSalaryEmpDeduction)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * ???????????????OtherSalaryEmpDeduction??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????OtherSalaryEmpDeduction??????");
		SalaryEmpDeduction otherSalaryEmpDeduction = otherSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(id));
		otherSalaryEmpDeduction.setM_umState("V");
		SalaryEmp salaryEmp = otherSalaryEmpDeductionService.get(SalaryEmp.class, otherSalaryEmpDeduction.getSalaryEmp());
		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_save")
				.addObject("otherSalaryEmpDeduction", otherSalaryEmpDeduction)
				.addObject("salaryEmp", salaryEmp);
	}
	
	/**
	 * ????????????Excel????????????
	 * @return
	 */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????Excel????????????");
		return new ModelAndView("admin/salary/otherSalaryEmpDeduction/otherSalaryEmpDeduction_import");
	}
	
	/**
	 * ??????OtherSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpDeduction otherSalaryEmpDeduction){
		logger.debug("??????OtherSalaryEmpDeduction??????");
		try{
			otherSalaryEmpDeduction.setDeductDate(new Date());
			otherSalaryEmpDeduction.setDeductType1("2");
			otherSalaryEmpDeduction.setLastUpdate(new Date());
			otherSalaryEmpDeduction.setLastUpdatedBy(getUserContext(request).getCzybh());
			otherSalaryEmpDeduction.setExpired("F");
			otherSalaryEmpDeduction.setActionLog("ADD");
			this.otherSalaryEmpDeductionService.save(otherSalaryEmpDeduction);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????OtherSalaryEmpDeduction??????");
		}
	}
	
	/**
	 * ??????OtherSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpDeduction otherSalaryEmpDeduction){
		logger.debug("??????OtherSalaryEmpDeduction??????");
		try{
			otherSalaryEmpDeduction.setDeductType1("2");
			otherSalaryEmpDeduction.setLastUpdate(new Date());
			otherSalaryEmpDeduction.setLastUpdatedBy(getUserContext(request).getCzybh());
			otherSalaryEmpDeduction.setExpired("F");
			otherSalaryEmpDeduction.setActionLog("EDIT");
			this.otherSalaryEmpDeductionService.update(otherSalaryEmpDeduction);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????OtherSalaryEmpDeduction??????");
		}
	}
	
	/**
	 * ??????OtherSalaryEmpDeduction
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????OtherSalaryEmpDeduction??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "OtherSalaryEmpDeduction??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpDeduction otherSalaryEmpDeduction = otherSalaryEmpDeductionService.get(SalaryEmpDeduction.class, Integer.parseInt(deleteId));
				if(otherSalaryEmpDeduction == null)
					continue;
				//???????????????????????????????????????
				SalaryMon salaryMon= otherSalaryEmpDeductionService.get(SalaryMon.class, otherSalaryEmpDeduction.getSalaryMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
					return;
				}
				otherSalaryEmpDeductionService.delete(otherSalaryEmpDeduction);
			}
		}
		logger.debug("??????OtherSalaryEmpDeduction IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *OtherSalaryEmpDeduction??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmpDeduction otherSalaryEmpDeduction){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		otherSalaryEmpDeductionService.findPageBySql(page, otherSalaryEmpDeduction);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * excel????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SalaryEmpDeductionBean> eUtils=new ExcelImportUtils<SalaryEmpDeductionBean>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List fileList = upload.parseRequest(request);
		Iterator it = fileList.iterator();
		List<String> titleList=new ArrayList<String>();
		InputStream in=null;
		while (it.hasNext()){
			FileItem obit = (FileItem) it.next();
				// ??????????????? ????????????
				String fieldName = obit.getFieldName();
				String fieldValue = obit.getString();
				if ("file".equals(fieldName)){
					in=obit.getInputStream();
				}
		}
		titleList.add("??????");
		titleList.add("????????????");
		titleList.add("????????????");
		titleList.add("??????");
		try {		
			List<SalaryEmpDeductionBean> result=eUtils.importExcel(in,SalaryEmpDeductionBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpDeductionBean salaryEmpDeductionBean:result){
				
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("isinvalid","1");
				data.put("isinvaliddescr", "??????");
				data.put("amount", salaryEmpDeductionBean.getAmount()==null?0:salaryEmpDeductionBean.getAmount());
				data.put("remark", salaryEmpDeductionBean.getRemark());
				if(StringUtils.isNotBlank(salaryEmpDeductionBean.getEmpName())){
					data.put("empname", salaryEmpDeductionBean.getEmpName());
					List<Map<String,Object>> empList=salaryEmpService.getEmpByParam("1", salaryEmpDeductionBean.getEmpName(),"");
					if (empList!=null && empList.size()>0){
						Map<String,Object> empMap=empList.get(0);
						data.put("salaryemp", empMap.get("EmpCode").toString());
					}else{
						data.put("isinvalid","0");
						data.put("isinvaliddescr", "????????????????????????");
					}	
				}else{
					data.put("isinvalid","0");
					data.put("isinvaliddescr", "????????????????????????");
				}
				
				if(StringUtils.isNotBlank(salaryEmpDeductionBean.getDeductType2())){
					List<Map<String,Object>> deductTypeList=finSalaryEmpDeductionService.getDeductType2(salaryEmpDeductionBean.getDeductType2());
					if(deductTypeList!=null && deductTypeList.size()>0){
						data.put("deducttype2descr", salaryEmpDeductionBean.getDeductType2());
						Map<String,Object> deductTypeMap=deductTypeList.get(0);
						data.put("deducttype2", deductTypeMap.get("Code").toString());
					}else{
						data.put("deducttype2descr", "??????");
						data.put("deducttype2", "299");
					}
				}
				datas.add(data);
			}
			map.put("success", true);
			map.put("returnInfo", "??????????????????");
			map.put("datas", datas);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			map.put("success", false);
			map.put("returnInfo","???????????????????????????????????????,??????????????????????????????????????????!");
			map.put("hasInvalid", true);
			return map;
		}
	}

	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param custTypeItem
	 */
	@RequestMapping("/doSaveBatch")
	@ResponseBody
	public void doSaveBatch(HttpServletRequest request,HttpServletResponse response,SalaryEmpDeduction finSalaryEmpDeduction){
		logger.debug("????????????????????????");
		try {
			finSalaryEmpDeduction.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			finSalaryEmpDeduction.setDeductType1("2");
			Result result = this.finSalaryEmpDeductionService.doSaveBatch(finSalaryEmpDeduction);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
}
