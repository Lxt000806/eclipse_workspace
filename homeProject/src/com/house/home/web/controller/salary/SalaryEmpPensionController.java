package com.house.home.web.controller.salary;

import java.io.InputStream;
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
import com.house.home.bean.salary.SalaryDataAdjustBean;
import com.house.home.bean.salary.SalaryEmpPensionBean;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpPension;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.basic.XtdmService;
import com.house.home.service.salary.SalaryEmpPensionService;
import com.house.home.service.salary.SalaryMonService;

@Controller
@RequestMapping("/admin/salaryEmpPension")
public class SalaryEmpPensionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SalaryEmpPensionController.class);

	@Autowired
	private SalaryEmpPensionService salaryEmpPensionService;
	@Autowired
	private SalaryMonService salaryMonService;
	@Autowired
	private XtdmService xtdmService;

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
			HttpServletResponse response, SalaryEmpPension salaryEmpPension) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salaryEmpPensionService.findPageBySql(page, salaryEmpPension);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * SalaryEmpPension??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,SalaryEmpPension salaryEmpPension) throws Exception {
		
		Date date = DateUtil.startOfTheDay(new Date()); // ??????
		Date addDate = DateUtil.addDate(DateUtil.startOfTheMonth(new Date()),14);
		
		if(addDate.getTime() > date.getTime()){
			date = DateUtil.addMonth(DateUtil.startOfTheMonth(new Date()), -1);
		}
		salaryEmpPension.setDateFrom(date);
		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_list")
				.addObject("salaryEmpPension", salaryEmpPension);
	}
	/**
	 * SalaryEmpPension??????code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_code");
	}
	/**
	 * ???????????????SalaryEmpPension??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????SalaryEmpPension??????");
		SalaryEmpPension salaryEmpPension = null;
		if (StringUtils.isNotBlank(id)){
			salaryEmpPension = salaryEmpPensionService.get(SalaryEmpPension.class, Integer.parseInt(id));
			salaryEmpPension.setPk(null);
		}else{
			salaryEmpPension = new SalaryEmpPension();
		}
		salaryEmpPension.setM_umState("A");
		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_save")
			.addObject("salaryEmpPension", salaryEmpPension);
	}
	/**
	 * ???????????????SalaryEmpPension??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????SalaryEmpPension??????");
		SalaryEmpPension salaryEmpPension = salaryEmpPensionService.get(SalaryEmpPension.class, pk);
		salaryEmpPension.setM_umState("M");
		SalaryMon salaryMon = new SalaryMon();
		
		if(salaryEmpPension.getBeginMon() != null){
			salaryMon = salaryEmpPensionService.get(SalaryMon.class, salaryEmpPension.getBeginMon());
		}
		Integer maxCheckedMon = 0;
		List<Map<String, Object>> list = salaryMonService.getCheckedMon();
		if(list != null && list.size() > 0 && list.get(0).get("SalaryMon") != null){
			maxCheckedMon = Integer.parseInt(list.get(0).get("SalaryMon").toString());
		}
		
		SalaryEmp salaryEmp = salaryEmpPensionService.get(SalaryEmp.class, salaryEmpPension.getSalaryEmp());
		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_save")
			.addObject("salaryEmpPension", salaryEmpPension)
			.addObject("salaryEmp", salaryEmp).addObject("salaryMon", salaryMon)
			.addObject("maxCheckedMon", maxCheckedMon).addObject("nowMon", DateUtil.DateToString(new Date(), "yyyyMM"));
	}
	
	/**
	 * ???????????????SalaryEmpPension??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????SalaryEmpPension??????");
		SalaryEmpPension salaryEmpPension = salaryEmpPensionService.get(SalaryEmpPension.class, pk);
		salaryEmpPension.setM_umState("V");
		SalaryEmp salaryEmp = salaryEmpPensionService.get(SalaryEmp.class, salaryEmpPension.getSalaryEmp());
		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_save")
			.addObject("salaryEmpPension", salaryEmpPension)
			.addObject("salaryEmp", salaryEmp);
	}
	
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????SalaryEmpPension??????");

		return new ModelAndView("admin/salary/salaryEmpPension/salaryEmpPension_import");
	}
	
	/**
	 * ??????SalaryEmpPension
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SalaryEmpPension salaryEmpPension){
		logger.debug("??????SalaryEmpPension??????");
		try{
			salaryEmpPension.setLastUpdate(new Date());
			salaryEmpPension.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpPension.setExpired("F");
			salaryEmpPension.setActionLog("ADD");
			
			List<Map<String, Object>> existsMonList=this.salaryEmpPensionService.isExistsMon(salaryEmpPension);
			if(existsMonList!=null && existsMonList.size()>0){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????");
				return;
			}
			
			//???????????????????????????????????????
			SalaryMon salaryMon= salaryEmpPensionService.get(SalaryMon.class, salaryEmpPension.getBeginMon());
			
			if(salaryMon != null && "3".equals(salaryMon.getStatus())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
				return;
			}
			
			this.salaryEmpPensionService.save(salaryEmpPension);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????SalaryEmpPension??????");
		}
	}
	
	/**
	 * ??????SalaryEmpPension
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SalaryEmpPension salaryEmpPension){
		logger.debug("??????SalaryEmpPension??????");
		try{
			salaryEmpPension.setLastUpdate(new Date());
			salaryEmpPension.setLastUpdatedBy(getUserContext(request).getCzybh());
			salaryEmpPension.setActionLog("EDIT");
			salaryEmpPension.setExpired("F");
			
			List<Map<String, Object>> existsMonList=this.salaryEmpPensionService.isExistsMon(salaryEmpPension);
			if(existsMonList!=null && existsMonList.size()>0){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????");
				return;
			}
			
			//???????????????????????????????????????
			SalaryMon salaryMon= salaryEmpPensionService.get(SalaryMon.class, salaryEmpPension.getBeginMon());
			if("3".equals(salaryMon.getStatus())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
				return;
			}
			
			this.salaryEmpPensionService.update(salaryEmpPension);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????SalaryEmpPension??????");
		}
	}
	
	/**
	 * ??????SalaryEmpPension
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????SalaryEmpPension??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "SalaryEmpPension??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SalaryEmpPension salaryEmpPension = salaryEmpPensionService.get(SalaryEmpPension.class, Integer.parseInt(deleteId));
				if(salaryEmpPension == null)
					continue;
				//???????????????????????????????????????
				SalaryMon salaryMon= salaryEmpPensionService.get(SalaryMon.class, salaryEmpPension.getBeginMon());
				if("3".equals(salaryMon.getStatus())){
					ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
					return;
				}
				salaryEmpPensionService.delete(salaryEmpPension);
			}
		}
		logger.debug("??????SalaryEmpPension IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *SalaryEmpPension??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SalaryEmpPension salaryEmpPension){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		salaryEmpPensionService.findPageBySql(page, salaryEmpPension);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/loadExcel")
	@ResponseBody
	public Map<String, Object> loadExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserContext uc=this.getUserContext(request);
		Map<String, Object> map=new HashMap<String, Object>();
		ExcelImportUtils<SalaryEmpPensionBean> eUtils=new ExcelImportUtils<SalaryEmpPensionBean>();
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
		titleList.add("??????");
		titleList.add("????????????");
		try {
			map.put("hasInvalid", false);
			List<SalaryEmpPensionBean> result=eUtils.importExcel(in, SalaryEmpPensionBean.class,titleList);
			List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
			for(SalaryEmpPensionBean salaryEmpPensionBean:result){
				Map<String,Object> data=new HashMap<String, Object>();
				SalaryEmp salaryEmp = new SalaryEmp();
				if(StringUtils.isNotBlank(salaryEmpPensionBean.getSalaryEmp())){
					data.put("salaryemp", salaryEmpPensionBean.getSalaryEmp());
					salaryEmp = salaryEmpPensionService.get(SalaryEmp.class, salaryEmpPensionBean.getSalaryEmp());
					if(salaryEmp != null){
						data.put("salaryempname", salaryEmp.getEmpName());
					} else {
						data.put("isinvaliddescr", "???????????????????????????");
						map.put("hasInvalid", true);
					}
				} else {
					data.put("isinvaliddescr", "????????????");
					map.put("hasInvalid", true);
				}
				data.put("typedescr", salaryEmpPensionBean.getTypeDescr());
				data.put("amount", salaryEmpPensionBean.getAmount());
				data.put("beginmon", salaryEmpPensionBean.getBeginMon());
				data.put("endmon", salaryEmpPensionBean.getBeginMon());
				SalaryEmpPension salaryEmpPension = new SalaryEmpPension();
				if(StringUtils.isNotBlank(salaryEmpPensionBean.getTypeDescr())){
					Xtdm xtdm = xtdmService.getByIdAndNote("SALPENSIONTYPE", salaryEmpPensionBean.getTypeDescr());
					if(xtdm != null){
						data.put("type", xtdm.getCbm());
						salaryEmpPension.setType(xtdm.getCbm());
					} else {
						data.put("isinvaliddescr", "???????????????????????????");
						map.put("hasInvalid", true);
					}
				} else {
					data.put("isinvaliddescr", "??????????????????");
					map.put("hasInvalid", true);
				}
			
				salaryEmpPension.setSalaryEmp(salaryEmpPensionBean.getSalaryEmp());
				salaryEmpPension.setBeginMon(salaryEmpPensionBean.getBeginMon());
				salaryEmpPension.setEndMon(salaryEmpPensionBean.getBeginMon());
				List<Map<String,Object>> existsMon = salaryEmpPensionService.isExistsMon(salaryEmpPension);
				if(existsMon != null && existsMon.size()>0){
					data.put("isinvaliddescr", "???????????????????????????????????????????????????");
					map.put("hasInvalid", true);
				}
				
				List<Map<String,Object>> checkMon = salaryEmpPensionService.isCheckMon(salaryEmpPension);
				if(checkMon != null && checkMon.size()>0){
					data.put("isinvaliddescr", "???????????????????????????");
					map.put("hasInvalid", true);
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
			map.put("returnInfo", "???????????????????????????????????????????????????????????????????????????????????????");
			map.put("hasInvalid", true);
			return map;
		}
	}

	@RequestMapping("/doImportSalaryEmpPension")
	public void doImportSalaryEmpPension(HttpServletRequest request,HttpServletResponse response,
			SalaryEmpPension salaryEmpPension){
		logger.debug("??????????????????");
		try {
			salaryEmpPension.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.salaryEmpPensionService.doImportSalaryEmpPension(salaryEmpPension) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
}
